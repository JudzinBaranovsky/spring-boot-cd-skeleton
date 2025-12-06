package org.bananalaba.teamsports.ingest;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bananalaba.springcdtemplate.model.TeamMatch;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JdbcTeamMatchHistoryStorage implements TeamMatchHistoryStorage {

    @NonNull
    private final JdbcOperations jdbc;

    @Override
    public void save(final @NonNull List<TeamMatch> history) {
        jdbc.batchUpdate("""
            insert into team_match_history
            ("date", "country", "homeScore", "awayTeam", "awayScore", "city", "homeTeam", "tournament")
            values
            (?, ?, ?, ?, ?, ?, ?, ?)
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
            ps.setString(8, item.getTournament().name());
        }

        @Override
        public int getBatchSize() {
            return items.size();
        }

    }

}
