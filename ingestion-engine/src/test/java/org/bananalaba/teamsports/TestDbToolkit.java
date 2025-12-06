package org.bananalaba.teamsports;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bananalaba.springcdtemplate.model.TeamMatch;
import org.bananalaba.springcdtemplate.model.TeamMatch.TournamentType;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDbToolkit {

    @NonNull
    private final JdbcOperations jdbc;
    private final RowMapper<TeamMatch> rowMapper = new TeamMatchRowMapper();

    public void clear() {
        jdbc.execute("delete from team_match_history");
    }

    public void createTables() {
        jdbc.execute("""
            create table if not exists team_match_history (
                "date" text,
                "country" text,
                "homeScore" int,
                "awayTeam" text,
                "awayScore" int,
                "city" text,
                "homeTeam" text,
                "tournament" text
            );
            """
        );

        jdbc.execute("""
            create table if not exists team_metrics (
                "team" text,
                "key" text,
                "value" real
            );
            """
        );
    }

    public List<TeamMatch> findAllTeamMatches() {
        return jdbc.query("select * from team_match_history", rowMapper);
    }

    private class TeamMatchRowMapper implements RowMapper<TeamMatch> {

        @Override
        public TeamMatch mapRow(ResultSet rs, int rowNum) throws SQLException {
            return TeamMatch.builder()
                .date(LocalDate.parse(rs.getString("date")))
                .country(rs.getString("country"))
                .homeScore(rs.getInt("homeScore"))
                .awayTeam(rs.getString("awayTeam"))
                .awayScore(rs.getInt("awayScore"))
                .city(rs.getString("city"))
                .homeTeam(rs.getString("homeTeam"))
                .tournament(TournamentType.valueOf(rs.getString("tournament")))
                .build();
        }

    }

}
