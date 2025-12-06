package org.bananalaba.teamsports.ingest;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import org.bananalaba.springcdtemplate.model.TeamMatch;

public class InMemoryTeamMatchHistoryStorage implements TeamMatchHistoryStorage {

    @Getter
    private final List<TeamMatch> state = new ArrayList<>();

    @Override
    public void save(final @NonNull List<TeamMatch> history) {
        state.addAll(history);
    }

    public void reset() {
        state.clear();
    }

}
