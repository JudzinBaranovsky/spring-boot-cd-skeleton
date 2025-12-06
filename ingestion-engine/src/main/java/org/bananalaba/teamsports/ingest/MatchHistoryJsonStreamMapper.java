package org.bananalaba.teamsports.ingest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bananalaba.springcdtemplate.model.TeamMatch;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MatchHistoryJsonStreamMapper {

    @NonNull
    private final ObjectMapper jsonMapper;
    @Value("${soccer.api.stream.batchSize:1000}")
    private final int batchSize;

    public Stream<List<TeamMatch>> map(final @NonNull InputStream inputStream) {
        var jsonIterator = new JsonArrayIterator(inputStream);

        var spliterator = Spliterators.spliteratorUnknownSize(jsonIterator, Spliterator.ORDERED | Spliterator.NONNULL);
        return StreamSupport.stream(spliterator, false).filter(batch -> !batch.isEmpty());
    }

    @RequiredArgsConstructor
    private class JsonArrayIterator implements Iterator<List<TeamMatch>> {

        private final InputStream input;
        private JsonParser jsonParser;

        private boolean initialized = false;
        private boolean finished = false;

        @Override
        public boolean hasNext() {
            init();

            try {
                if (finished) {
                    return false;
                }

                var token = jsonParser.currentToken();
                if (token == null) {
                    token = jsonParser.nextToken();
                }

                if (token == JsonToken.END_ARRAY) {
                    close();
                    return false;
                }

                return true;
            } catch (IOException e) {
                throw new DataIngestionException("failed to parse JSON", e);
            }
        }

        private void init() {
            if (initialized) {
                return;
            }

            try {
                jsonParser = jsonMapper.getFactory().createParser(input);

                if (jsonParser.nextToken() != JsonToken.START_ARRAY) {
                    throw new DataIngestionException("Expected START_ARRAY token");
                }

                initialized = true;
            } catch (IOException e) {
                throw new DataIngestionException("failed to parse JSON", e);
            }
        }

        @Override
        public List<TeamMatch> next() {
            init();
            var batch = new ArrayList<TeamMatch>();
            try {
                while (batch.size() < batchSize) {
                    var token = jsonParser.nextToken();
                    if (token == JsonToken.END_ARRAY) {
                        close();
                        break;
                    }

                    if (token == JsonToken.START_OBJECT) {
                        TeamMatch match = jsonMapper.readValue(jsonParser, TeamMatch.class);
                        batch.add(match);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            log.info("got a batch of {} matches from JSON stream", batch.size());
            log.debug("the batch: {}", batch);
            return batch;
        }

        private void close() throws IOException {
            log.info("closing JSON parser stream");

            finished = true;
            jsonParser.close();
            input.close();
        }

    }

}
