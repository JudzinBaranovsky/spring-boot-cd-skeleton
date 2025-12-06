package org.bananalaba.teamsports.ingest;

import java.util.List;

import org.bananalaba.springcdtemplate.model.TeamMatch;

public interface TeamMatchHistoryStorage {

    void save(final List<TeamMatch> history);

}
