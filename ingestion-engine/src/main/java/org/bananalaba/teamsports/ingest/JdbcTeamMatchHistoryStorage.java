package org.bananalaba.teamsports.ingest;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bananalaba.springcdtemplate.model.TeamMatch;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JdbcTeamMatchHistoryStorage implements TeamMatchHistoryStorage {

    @NonNull
    private final JdbcOperations jdbc;

    @Override
    @Transactional
    public void save(final @NonNull List<TeamMatch> history) {
        log.info("saving {} matches", history.size());
        if (history.isEmpty()) {
            return;
        }

        jdbc.batchUpdate("""
            insert into team_match_history
            ("date", "country", "homeScore", "awayTeam", "awayScore", "city", "homeTeam", "tournament")
            values
            (?, ?, ?, ?, ?, ?, ?, ?)
            on conflict ("date", "awayTeam", "homeTeam") do update
            set "country" = excluded."country", "homeScore" = excluded."homeScore", "awayScore" = excluded."awayScore", "tournament" = excluded."tournament"
            """,
            new BatchSetter(history)
        );
    }

    @RequiredArgsConstructor
    private static class BatchSetter implements BatchPreparedStatementSetter {

        private final List<TeamMatch> items;

        @Override
        public void setValues(final PreparedStatement ps, final int i) throws SQLException {
            var item = items.get(i);
            ps.setString(1, item.getDate().toString());
            ps.setString(2, item.getCountry());
            ps.setInt(3, item.getHomeScore());
            ps.setString(4, item.getAwayTeam());
            ps.setInt(5, item.getAwayScore());
            ps.setString(6, item.getCity());
            ps.setString(7, item.getHomeTeam());
            ps.setString(8, item.getTournament());
        }

        @Override
        public int getBatchSize() {
            return items.size();
        }

    }

}
