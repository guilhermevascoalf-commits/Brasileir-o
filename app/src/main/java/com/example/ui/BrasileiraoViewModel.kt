package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.LeagueOverview
import com.example.model.Match
import com.example.model.Scorer
import com.example.model.Standing
import com.example.model.Team
import com.example.model.TeamProbabilities
import com.example.repository.BrasileiraoRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppTab(val title: String) {
    STANDINGS("Tabela"),
    ROUNDS("Rodadas"),
    SIMULATOR("Simulador"),
    PROBABILITIES("Probabilidades"),
    STATS("Estatísticas")
}

enum class ProbabilitySort {
    CHAMPION, LIBERTADORES, SULAMERICANA, RELEGATION
}

enum class SimulationPreset {
    HOME_WINS, DRAWS, RANDOM, FAVORITES
}

class BrasileiraoViewModel(
    private val repository: BrasileiraoRepository = BrasileiraoRepository()
) : ViewModel() {

    private val _currentTab = MutableStateFlow(AppTab.STANDINGS)
    val currentTab: StateFlow<AppTab> = _currentTab.asStateFlow()

    private val _selectedRound = MutableStateFlow(34)
    val selectedRound: StateFlow<Int> = _selectedRound.asStateFlow()

    private val _selectedTeam = MutableStateFlow<Team?>(null)
    val selectedTeam: StateFlow<Team?> = _selectedTeam.asStateFlow()

    private val _probSort = MutableStateFlow(ProbabilitySort.CHAMPION)
    val probSort: StateFlow<ProbabilitySort> = _probSort.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    val standings: StateFlow<List<Standing>> = repository.standings
    val matches: StateFlow<List<Match>> = repository.matches
    val simulatedScores: StateFlow<Map<String, Pair<Int, Int>>> = repository.simulatedScores
    val scorers: StateFlow<List<Scorer>> = repository.scorers

    val sortedProbabilities: StateFlow<List<TeamProbabilities>> = combine(
        repository.probabilities,
        _probSort
    ) { probs, sort ->
        when (sort) {
            ProbabilitySort.CHAMPION -> probs.sortedByDescending { it.championPct }
            ProbabilitySort.LIBERTADORES -> probs.sortedByDescending { it.libertadoresPct }
            ProbabilitySort.SULAMERICANA -> probs.sortedByDescending { it.sulamericanaPct }
            ProbabilitySort.RELEGATION -> probs.sortedByDescending { it.relegationPct }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), repository.probabilities.value)

    fun selectTab(tab: AppTab) {
        _currentTab.value = tab
    }

    fun selectRound(round: Int) {
        if (round in 1..38) {
            _selectedRound.value = round
        }
    }

    fun nextRound() {
        if (_selectedRound.value < 38) {
            _selectedRound.value += 1
        }
    }

    fun previousRound() {
        if (_selectedRound.value > 1) {
            _selectedRound.value -= 1
        }
    }

    fun updateScore(matchId: String, homeScore: Int?, awayScore: Int?) {
        repository.updateSimulatedScore(matchId, homeScore, awayScore)
    }

    fun clearSimulations() {
        repository.clearAllSimulations()
    }

    fun applyPreset(preset: SimulationPreset, round: Int = _selectedRound.value) {
        when (preset) {
            SimulationPreset.HOME_WINS -> repository.simulatePresetHomeWins(round)
            SimulationPreset.DRAWS -> repository.simulatePresetDraws(round)
            SimulationPreset.RANDOM -> repository.simulatePresetRandom(round)
            SimulationPreset.FAVORITES -> repository.simulateAllRemainingRounds()
        }
    }

    fun setProbabilitySort(sort: ProbabilitySort) {
        _probSort.value = sort
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectTeam(team: Team?) {
        _selectedTeam.value = team
    }

    fun getTeam(id: String): Team = repository.getTeam(id)

    fun getLeagueOverview(): LeagueOverview = repository.getLeagueOverview()

    fun getTeamMatches(teamId: String): List<Match> {
        return matches.value.filter { it.homeTeamId == teamId || it.awayTeamId == teamId }
    }

    fun refreshData() {
        viewModelScope.launch {
            _isRefreshing.value = true
            delay(800) // Simulating network fetch
            _isRefreshing.value = false
        }
    }
}
