create table if not exists team_match_history (
    "date" text,
    "country" text,
    "homeScore" int,
    "awayTeam" text,
    "awayScore" int,
    "city" text,
    "homeTeam" text,
    "tournament" text,
    primary key ("date", "awayTeam", "homeTeam")
);

create table if not exists team_metrics (
    "team" text,
    "key" text,
    "value" real,
    primary key ("team", "key")
);
