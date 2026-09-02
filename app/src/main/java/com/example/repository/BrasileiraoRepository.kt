package com.example.repository

import com.example.data.BrasileiraoDataProvider
import com.example.model.LeagueOverview
import com.example.model.Match
import com.example.model.MatchResult
import com.example.model.Scorer
import com.example.model.Standing
import com.example.model.Team
import com.example.model.TeamProbabilities
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

class BrasileiraoRepository {

    private val _matches = MutableStateFlow<List<Match>>(BrasileiraoDataProvider.initialMatches)
    val matches: StateFlow<List<Match>> = _matches.asStateFlow()

    // Key: matchId, Value: Pair(homeScore, awayScore)
    private val _simulatedScores = MutableStateFlow<Map<String, Pair<Int, Int>>>(emptyMap())
    val simulatedScores: StateFlow<Map<String, Pair<Int, Int>>> = _simulatedScores.asStateFlow()

    private val _standings = MutableStateFlow<List<Standing>>(BrasileiraoDataProvider.initialStandings)
    val standings: StateFlow<List<Standing>> = _standings.asStateFlow()

    private val _probabilities = MutableStateFlow<List<TeamProbabilities>>(BrasileiraoDataProvider.probabilities)
    val probabilities: StateFlow<List<TeamProbabilities>> = _probabilities.asStateFlow()

    private val _scorers = MutableStateFlow<List<Scorer>>(BrasileiraoDataProvider.topScorers)
    val scorers: StateFlow<List<Scorer>> = _scorers.asStateFlow()

    init {
        recalculateStandings()
    }

    fun getTeams(): List<Team> = BrasileiraoDataProvider.teams

    fun getTeam(id: String): Team = BrasileiraoDataProvider.getTeam(id)

    fun updateSimulatedScore(matchId: String, homeScore: Int?, awayScore: Int?) {
        val current = _simulatedScores.value.toMutableMap()
        if (homeScore == null || awayScore == null) {
            current.remove(matchId)
        } else {
            current[matchId] = Pair(homeScore.coerceIn(0, 20), awayScore.coerceIn(0, 20))
        }
        _simulatedScores.value = current
        recalculateStandings()
    }

    fun clearAllSimulations() {
        _simulatedScores.value = emptyMap()
        recalculateStandings()
    }

    fun simulatePresetHomeWins(round: Int) {
        val current = _simulatedScores.value.toMutableMap()
        val roundMatches = _matches.value.filter { it.round == round && !it.isFinished }
        for (m in roundMatches) {
            current[m.id] = Pair(2, 0)
        }
        _simulatedScores.value = current
        recalculateStandings()
    }

    fun simulatePresetDraws(round: Int) {
        val current = _simulatedScores.value.toMutableMap()
        val roundMatches = _matches.value.filter { it.round == round && !it.isFinished }
        for (m in roundMatches) {
            current[m.id] = Pair(1, 1)
        }
        _simulatedScores.value = current
        recalculateStandings()
    }

    fun simulatePresetRandom(round: Int) {
        val current = _simulatedScores.value.toMutableMap()
        val roundMatches = _matches.value.filter { it.round == round && !it.isFinished }
        for (m in roundMatches) {
            val home = Random.nextInt(0, 4)
            val away = Random.nextInt(0, 4)
            current[m.id] = Pair(home, away)
        }
        _simulatedScores.value = current
        recalculateStandings()
    }

    fun simulateAllRemainingRounds() {
        val current = _simulatedScores.value.toMutableMap()
        val upcoming = _matches.value.filter { !it.isFinished }
        for (m in upcoming) {
            val hProb = BrasileiraoDataProvider.probabilities.find { it.teamId == m.homeTeamId }?.championPct ?: 0.0
            val aProb = BrasileiraoDataProvider.probabilities.find { it.teamId == m.awayTeamId }?.championPct ?: 0.0
            
            val (hScore, aScore) = when {
                hProb > aProb + 10 -> Pair(2, 0)
                aProb > hProb + 10 -> Pair(0, 2)
                else -> Pair(1, 1)
            }
            current[m.id] = Pair(hScore, aScore)
        }
        _simulatedScores.value = current
        recalculateStandings()
    }

    private fun recalculateStandings() {
        val baseStandings = BrasileiraoDataProvider.initialStandings.associateBy { it.team.id }
        val simScores = _simulatedScores.value

        // Accumulators for each team
        data class TeamStats(
            val team: Team,
            var points: Int,
            var played: Int,
            var won: Int,
            var drawn: Int,
            var lost: Int,
            var goalsFor: Int,
            var goalsAgainst: Int,
            var form: MutableList<MatchResult>,
            val originalPosition: Int
        )

        val statsMap = baseStandings.mapValues { (_, standing) ->
            TeamStats(
                team = standing.team,
                points = standing.points,
                played = standing.played,
                won = standing.won,
                drawn = standing.drawn,
                lost = standing.lost,
                goalsFor = standing.goalsFor,
                goalsAgainst = standing.goalsAgainst,
                form = standing.form.toMutableList(),
                originalPosition = standing.position
            )
        }.toMutableMap()

        // Apply simulated match results
        for (match in _matches.value) {
            if (!match.isFinished && simScores.containsKey(match.id)) {
                val (hScore, aScore) = simScores[match.id]!!
                val homeStats = statsMap[match.homeTeamId]
                val awayStats = statsMap[match.awayTeamId]

                if (homeStats != null && awayStats != null) {
                    homeStats.played += 1
                    awayStats.played += 1

                    homeStats.goalsFor += hScore
                    homeStats.goalsAgainst += aScore

                    awayStats.goalsFor += aScore
                    awayStats.goalsAgainst += hScore

                    when {
                        hScore > aScore -> {
                            homeStats.points += 3
                            homeStats.won += 1
                            homeStats.form.add(0, MatchResult.WIN)
                            if (homeStats.form.size > 5) homeStats.form.removeLast()

                            awayStats.lost += 1
                            awayStats.form.add(0, MatchResult.LOSS)
                            if (awayStats.form.size > 5) awayStats.form.removeLast()
                        }
                        hScore < aScore -> {
                            awayStats.points += 3
                            awayStats.won += 1
                            awayStats.form.add(0, MatchResult.WIN)
                            if (awayStats.form.size > 5) awayStats.form.removeLast()

                            homeStats.lost += 1
                            homeStats.form.add(0, MatchResult.LOSS)
                            if (homeStats.form.size > 5) homeStats.form.removeLast()
                        }
                        else -> {
                            homeStats.points += 1
                            homeStats.drawn += 1
                            homeStats.form.add(0, MatchResult.DRAW)
                            if (homeStats.form.size > 5) homeStats.form.removeLast()

                            awayStats.points += 1
                            awayStats.drawn += 1
                            awayStats.form.add(0, MatchResult.DRAW)
                            if (awayStats.form.size > 5) awayStats.form.removeLast()
                        }
                    }
                }
            }
        }

        // Sort by CBF tiebreakers: Points > Wins > Goal Difference > Goals For
        val sortedList = statsMap.values.sortedWith(
            compareByDescending<TeamStats> { it.points }
                .thenByDescending { it.won }
                .thenByDescending { it.goalsFor - it.goalsAgainst }
                .thenByDescending { it.goalsFor }
                .thenBy { it.team.name }
        )

        val newStandings = sortedList.mapIndexed { index, s ->
            val newPosition = index + 1
            // Position change: positive means climbed up (original pos was larger number)
            val posChange = s.originalPosition - newPosition
            Standing(
                team = s.team,
                position = newPosition,
                points = s.points,
                played = s.played,
                won = s.won,
                drawn = s.drawn,
                lost = s.lost,
                goalsFor = s.goalsFor,
                goalsAgainst = s.goalsAgainst,
                goalDifference = s.goalsFor - s.goalsAgainst,
                form = s.form,
                positionChange = posChange
            )
        }

        _standings.value = newStandings
    }

    fun getLeagueOverview(): LeagueOverview {
        val st = _standings.value
        val totalGoals = st.sumOf { it.goalsFor }
        val matchesPlayed = _matches.value.count { it.isFinished } + _simulatedScores.value.size
        return LeagueOverview(
            totalMatches = 380,
            playedMatches = matchesPlayed,
            totalGoals = totalGoals,
            homeWins = 182,
            awayWins = 96,
            draws = 92,
            currentRound = 34
        )
    }
}
