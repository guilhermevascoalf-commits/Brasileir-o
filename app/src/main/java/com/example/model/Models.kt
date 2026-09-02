package com.example.model

data class Team(
    val id: String,
    val name: String,
    val shortName: String,
    val code: String,
    val primaryColor: Long,
    val secondaryColor: Long,
    val stadium: String,
    val city: String,
    val state: String,
    val logoUrl: String = ""
)

data class Standing(
    val team: Team,
    val position: Int,
    val points: Int,
    val played: Int,
    val won: Int,
    val drawn: Int,
    val lost: Int,
    val goalsFor: Int,
    val goalsAgainst: Int,
    val goalDifference: Int,
    val form: List<MatchResult> = emptyList(),
    val positionChange: Int = 0 // > 0 gained positions, < 0 dropped, 0 same
) {
    val winPercentage: Float
        get() = if (played > 0) (points.toFloat() / (played * 3)) * 100f else 0f
}

enum class MatchResult {
    WIN, DRAW, LOSS
}

enum class TableZone {
    LIBERTADORES_GROUP,    // 1-4 (G4)
    LIBERTADORES_QUALIFIERS,// 5-6 (G6)
    SULAMERICANA,          // 7-12
    NEUTRAL,               // 13-16
    RELEGATION             // 17-20 (Z4)
}

fun getZoneForPosition(position: Int): TableZone {
    return when (position) {
        in 1..4 -> TableZone.LIBERTADORES_GROUP
        in 5..6 -> TableZone.LIBERTADORES_QUALIFIERS
        in 7..12 -> TableZone.SULAMERICANA
        in 13..16 -> TableZone.NEUTRAL
        else -> TableZone.RELEGATION
    }
}

data class Match(
    val id: String,
    val round: Int,
    val homeTeamId: String,
    val awayTeamId: String,
    val homeScore: Int? = null,
    val awayScore: Int? = null,
    val isFinished: Boolean = false,
    val date: String,
    val time: String,
    val stadium: String,
    val broadcast: String = "Premiere"
)

data class TeamProbabilities(
    val teamId: String,
    val championPct: Double,
    val libertadoresPct: Double,
    val sulamericanaPct: Double,
    val relegationPct: Double
)

data class Scorer(
    val id: String,
    val name: String,
    val teamId: String,
    val goals: Int,
    val penalties: Int = 0,
    val assists: Int = 0,
    val matches: Int = 0,
    val position: String = "Atacante"
)

data class LeagueOverview(
    val totalMatches: Int,
    val playedMatches: Int,
    val totalGoals: Int,
    val homeWins: Int,
    val awayWins: Int,
    val draws: Int,
    val currentRound: Int
)
