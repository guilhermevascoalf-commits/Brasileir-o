package com.example.data

import com.example.model.LeagueOverview
import com.example.model.Match
import com.example.model.MatchResult
import com.example.model.Scorer
import com.example.model.Standing
import com.example.model.Team
import com.example.model.TeamProbabilities

object BrasileiraoDataProvider {

    val teams = listOf(
        Team(
            id = "botafogo",
            name = "Botafogo",
            shortName = "Botafogo",
            code = "BOT",
            primaryColor = 0xFF000000,
            secondaryColor = 0xFFFFFFFF,
            stadium = "Nilton Santos",
            city = "Rio de Janeiro",
            state = "RJ",
            logoUrl = "https://s.sde.globo.com/media/organizations/2019/02/04/botafogo-svg.svg"
        ),
        Team(
            id = "palmeiras",
            name = "Palmeiras",
            shortName = "Palmeiras",
            code = "PAL",
            primaryColor = 0xFF006437,
            secondaryColor = 0xFFFFFFFF,
            stadium = "Allianz Parque",
            city = "São Paulo",
            state = "SP",
            logoUrl = "https://s.sde.globo.com/media/organizations/2019/02/04/palmeiras-svg.svg"
        ),
        Team(
            id = "flamengo",
            name = "Flamengo",
            shortName = "Flamengo",
            code = "FLA",
            primaryColor = 0xFFC3281E,
            secondaryColor = 0xFF000000,
            stadium = "Maracanã",
            city = "Rio de Janeiro",
            state = "RJ",
            logoUrl = "https://s.sde.globo.com/media/organizations/2018/04/10/Flamengo-2018.svg"
        ),
        Team(
            id = "fortaleza",
            name = "Fortaleza",
            shortName = "Fortaleza",
            code = "FOR",
            primaryColor = 0xFF002F6C,
            secondaryColor = 0xFFE00000,
            stadium = "Castelão",
            city = "Fortaleza",
            state = "CE",
            logoUrl = "https://s.sde.globo.com/media/organizations/2021/09/19/Fortaleza_2021_svg.svg"
        ),
        Team(
            id = "internacional",
            name = "Internacional",
            shortName = "Inter",
            code = "INT",
            primaryColor = 0xFFE51D24,
            secondaryColor = 0xFFFFFFFF,
            stadium = "Beira-Rio",
            city = "Porto Alegre",
            state = "RS",
            logoUrl = "https://s.sde.globo.com/media/organizations/2018/03/11/internacional.svg"
        ),
        Team(
            id = "sao_paulo",
            name = "São Paulo",
            shortName = "São Paulo",
            code = "SAO",
            primaryColor = 0xFFDA291C,
            secondaryColor = 0xFF000000,
            stadium = "MorumBIS",
            city = "São Paulo",
            state = "SP",
            logoUrl = "https://s.sde.globo.com/media/organizations/2018/03/11/sao-paulo.svg"
        ),
        Team(
            id = "bahia",
            name = "Bahia",
            shortName = "Bahia",
            code = "BAH",
            primaryColor = 0xFF005DAA,
            secondaryColor = 0xFFED1C24,
            stadium = "Arena Fonte Nova",
            city = "Salvador",
            state = "BA",
            logoUrl = "https://s.sde.globo.com/media/organizations/2018/03/11/bahia.svg"
        ),
        Team(
            id = "cruzeiro",
            name = "Cruzeiro",
            shortName = "Cruzeiro",
            code = "CRU",
            primaryColor = 0xFF003882,
            secondaryColor = 0xFFFFFFFF,
            stadium = "Mineirão",
            city = "Belo Horizonte",
            state = "MG",
            logoUrl = "https://s.sde.globo.com/media/organizations/2021/02/13/cruzeiro_2021.svg"
        ),
        Team(
            id = "atletico_mg",
            name = "Atlético-MG",
            shortName = "Atlético-MG",
            code = "CAM",
            primaryColor = 0xFF000000,
            secondaryColor = 0xFFFFFFFF,
            stadium = "Arena MRV",
            city = "Belo Horizonte",
            state = "MG",
            logoUrl = "https://s.sde.globo.com/media/organizations/2018/03/10/atletico-mg.svg"
        ),
        Team(
            id = "vasco",
            name = "Vasco da Gama",
            shortName = "Vasco",
            code = "VAS",
            primaryColor = 0xFF000000,
            secondaryColor = 0xFFFFFFFF,
            stadium = "São Januário",
            city = "Rio de Janeiro",
            state = "RJ",
            logoUrl = "https://s.sde.globo.com/media/organizations/2021/09/04/vasco_SVG.svg"
        ),
        Team(
            id = "corinthians",
            name = "Corinthians",
            shortName = "Corinthians",
            code = "COR",
            primaryColor = 0xFF000000,
            secondaryColor = 0xFFFFFFFF,
            stadium = "Neo Química Arena",
            city = "São Paulo",
            state = "SP",
            logoUrl = "https://s.sde.globo.com/media/organizations/2019/09/30/Corinthians.svg"
        ),
        Team(
            id = "gremio",
            name = "Grêmio",
            shortName = "Grêmio",
            code = "GRE",
            primaryColor = 0xFF0D80BF,
            secondaryColor = 0xFF000000,
            stadium = "Arena do Grêmio",
            city = "Porto Alegre",
            state = "RS",
            logoUrl = "https://s.sde.globo.com/media/organizations/2018/03/12/gremio.svg"
        ),
        Team(
            id = "vitoria",
            name = "Vitória",
            shortName = "Vitória",
            code = "VIT",
            primaryColor = 0xFFE30613,
            secondaryColor = 0xFF000000,
            stadium = "Barradão",
            city = "Salvador",
            state = "BA",
            logoUrl = "https://s.sde.globo.com/media/organizations/2024/04/09/vitoria-2024.svg"
        ),
        Team(
            id = "juventude",
            name = "Juventude",
            shortName = "Juventude",
            code = "JUV",
            primaryColor = 0xFF00843D,
            secondaryColor = 0xFFFFFFFF,
            stadium = "Alfredo Jaconi",
            city = "Caxias do Sul",
            state = "RS",
            logoUrl = "https://s.sde.globo.com/media/organizations/2021/04/29/Juventude-2021-01.svg"
        ),
        Team(
            id = "fluminense",
            name = "Fluminense",
            shortName = "Fluminense",
            code = "FLU",
            primaryColor = 0xFF8A1538,
            secondaryColor = 0xFF006633,
            stadium = "Maracanã",
            city = "Rio de Janeiro",
            state = "RJ",
            logoUrl = "https://s.sde.globo.com/media/organizations/2018/03/11/fluminense.svg"
        ),
        Team(
            id = "criciuma",
            name = "Criciúma",
            shortName = "Criciúma",
            code = "CRI",
            primaryColor = 0xFFFFD700,
            secondaryColor = 0xFF000000,
            stadium = "Heriberto Hülse",
            city = "Criciúma",
            state = "SC",
            logoUrl = "https://s.sde.globo.com/media/organizations/2024/03/26/criciuma-svg.svg"
        ),
        Team(
            id = "bragantino",
            name = "Red Bull Bragantino",
            shortName = "Bragantino",
            code = "RBB",
            primaryColor = 0xFFDA291C,
            secondaryColor = 0xFFFFFFFF,
            stadium = "Nabi Abi Chedid",
            city = "Bragança Paulista",
            state = "SP",
            logoUrl = "https://s.sde.globo.com/media/organizations/2020/01/01/RedBullBragantino.svg"
        ),
        Team(
            id = "athletico_pr",
            name = "Athletico-PR",
            shortName = "Athletico",
            code = "CAP",
            primaryColor = 0xFFC8102E,
            secondaryColor = 0xFF000000,
            stadium = "Ligga Arena",
            city = "Curitiba",
            state = "PR",
            logoUrl = "https://s.sde.globo.com/media/organizations/2019/09/09/Athletico-PR-2019.svg"
        ),
        Team(
            id = "cuiaba",
            name = "Cuiabá",
            shortName = "Cuiabá",
            code = "CUI",
            primaryColor = 0xFF006838,
            secondaryColor = 0xFFFFD100,
            stadium = "Arena Pantanal",
            city = "Cuiabá",
            state = "MT",
            logoUrl = "https://s.sde.globo.com/media/organizations/2018/12/26/Cuiaba_EC.svg"
        ),
        Team(
            id = "atletico_go",
            name = "Atlético-GO",
            shortName = "Atlético-GO",
            code = "ACG",
            primaryColor = 0xFFDA291C,
            secondaryColor = 0xFF000000,
            stadium = "Antônio Accioly",
            city = "Goiânia",
            state = "GO",
            logoUrl = "https://s.sde.globo.com/media/organizations/2020/07/02/atletico-go-2020.svg"
        )
    )

    val teamMap = teams.associateBy { it.id }

    fun getTeam(id: String): Team = teamMap[id] ?: Team(
        id = id,
        name = id.replace("_", " ").capitalize(),
        shortName = id.take(4).uppercase(),
        code = id.take(3).uppercase(),
        primaryColor = 0xFF2C3E50,
        secondaryColor = 0xFFBDC3C7,
        stadium = "Estádio Principal",
        city = "Brasil",
        state = "BR"
    )

    val initialStandings = listOf(
        Standing(getTeam("botafogo"), 1, 68, 33, 20, 8, 5, 52, 26, 26, listOf(MatchResult.WIN, MatchResult.WIN, MatchResult.DRAW, MatchResult.WIN, MatchResult.DRAW)),
        Standing(getTeam("palmeiras"), 2, 64, 33, 19, 7, 7, 54, 27, 27, listOf(MatchResult.WIN, MatchResult.LOSS, MatchResult.WIN, MatchResult.WIN, MatchResult.DRAW)),
        Standing(getTeam("fortaleza"), 3, 63, 33, 18, 9, 6, 47, 32, 15, listOf(MatchResult.WIN, MatchResult.WIN, MatchResult.DRAW, MatchResult.WIN, MatchResult.WIN)),
        Standing(getTeam("flamengo"), 4, 59, 33, 17, 8, 8, 51, 37, 14, listOf(MatchResult.WIN, MatchResult.DRAW, MatchResult.WIN, MatchResult.LOSS, MatchResult.WIN)),
        Standing(getTeam("internacional"), 5, 59, 33, 16, 11, 6, 46, 28, 18, listOf(MatchResult.WIN, MatchResult.WIN, MatchResult.WIN, MatchResult.DRAW, MatchResult.WIN)),
        Standing(getTeam("sao_paulo"), 6, 57, 33, 17, 6, 10, 47, 34, 13, listOf(MatchResult.WIN, MatchResult.WIN, MatchResult.DRAW, MatchResult.LOSS, MatchResult.WIN)),
        Standing(getTeam("cruzeiro"), 7, 47, 33, 13, 8, 12, 38, 35, 3, listOf(MatchResult.WIN, MatchResult.LOSS, MatchResult.DRAW, MatchResult.LOSS, MatchResult.WIN)),
        Standing(getTeam("bahia"), 8, 46, 33, 13, 7, 13, 43, 42, 1, listOf(MatchResult.LOSS, MatchResult.LOSS, MatchResult.LOSS, MatchResult.DRAW, MatchResult.LOSS)),
        Standing(getTeam("vasco"), 9, 43, 33, 12, 7, 14, 36, 49, -13, listOf(MatchResult.LOSS, MatchResult.WIN, MatchResult.WIN, MatchResult.LOSS, MatchResult.DRAW)),
        Standing(getTeam("atletico_mg"), 10, 42, 33, 10, 12, 11, 42, 47, -5, listOf(MatchResult.LOSS, MatchResult.DRAW, MatchResult.LOSS, MatchResult.LOSS, MatchResult.DRAW)),
        Standing(getTeam("corinthians"), 11, 41, 33, 10, 11, 12, 39, 41, -2, listOf(MatchResult.WIN, MatchResult.WIN, MatchResult.WIN, MatchResult.WIN, MatchResult.DRAW)),
        Standing(getTeam("gremio"), 12, 39, 33, 11, 6, 16, 38, 42, -4, listOf(MatchResult.LOSS, MatchResult.DRAW, MatchResult.WIN, MatchResult.LOSS, MatchResult.WIN)),
        Standing(getTeam("vitoria"), 13, 38, 33, 11, 5, 17, 35, 48, -13, listOf(MatchResult.WIN, MatchResult.WIN, MatchResult.WIN, MatchResult.DRAW, MatchResult.LOSS)),
        Standing(getTeam("fluminense"), 14, 37, 33, 10, 7, 16, 28, 36, -8, listOf(MatchResult.DRAW, MatchResult.LOSS, MatchResult.LOSS, MatchResult.WIN, MatchResult.WIN)),
        Standing(getTeam("criciuma"), 15, 37, 33, 9, 10, 14, 38, 48, -10, listOf(MatchResult.LOSS, MatchResult.DRAW, MatchResult.LOSS, MatchResult.DRAW, MatchResult.WIN)),
        Standing(getTeam("juventude"), 16, 37, 33, 9, 10, 14, 40, 52, -12, listOf(MatchResult.WIN, MatchResult.LOSS, MatchResult.LOSS, MatchResult.LOSS, MatchResult.DRAW)),
        Standing(getTeam("bragantino"), 17, 36, 33, 8, 12, 13, 34, 40, -6, listOf(MatchResult.DRAW, MatchResult.DRAW, MatchResult.LOSS, MatchResult.LOSS, MatchResult.DRAW)),
        Standing(getTeam("athletico_pr"), 18, 34, 33, 9, 7, 17, 35, 41, -6, listOf(MatchResult.LOSS, MatchResult.WIN, MatchResult.LOSS, MatchResult.LOSS, MatchResult.LOSS)),
        Standing(getTeam("cuiaba"), 19, 29, 33, 6, 11, 16, 25, 41, -16, listOf(MatchResult.DRAW, MatchResult.DRAW, MatchResult.LOSS, MatchResult.LOSS, MatchResult.DRAW)),
        Standing(getTeam("atletico_go"), 20, 26, 33, 6, 8, 19, 24, 50, -26, listOf(MatchResult.DRAW, MatchResult.WIN, MatchResult.LOSS, MatchResult.LOSS, MatchResult.LOSS))
    )

    val probabilities = listOf(
        TeamProbabilities("botafogo", championPct = 68.4, libertadoresPct = 100.0, sulamericanaPct = 0.0, relegationPct = 0.0),
        TeamProbabilities("palmeiras", championPct = 25.1, libertadoresPct = 100.0, sulamericanaPct = 0.0, relegationPct = 0.0),
        TeamProbabilities("fortaleza", championPct = 6.3, libertadoresPct = 100.0, sulamericanaPct = 0.0, relegationPct = 0.0),
        TeamProbabilities("flamengo", championPct = 0.2, libertadoresPct = 100.0, sulamericanaPct = 0.0, relegationPct = 0.0),
        TeamProbabilities("internacional", championPct = 0.0, libertadoresPct = 99.8, sulamericanaPct = 0.2, relegationPct = 0.0),
        TeamProbabilities("sao_paulo", championPct = 0.0, libertadoresPct = 98.6, sulamericanaPct = 1.4, relegationPct = 0.0),
        TeamProbabilities("cruzeiro", championPct = 0.0, libertadoresPct = 28.5, sulamericanaPct = 71.5, relegationPct = 0.0),
        TeamProbabilities("bahia", championPct = 0.0, libertadoresPct = 18.2, sulamericanaPct = 81.6, relegationPct = 0.2),
        TeamProbabilities("vasco", championPct = 0.0, libertadoresPct = 4.8, sulamericanaPct = 89.1, relegationPct = 0.5),
        TeamProbabilities("atletico_mg", championPct = 0.0, libertadoresPct = 6.2, sulamericanaPct = 86.4, relegationPct = 0.8),
        TeamProbabilities("corinthians", championPct = 0.0, libertadoresPct = 3.1, sulamericanaPct = 82.3, relegationPct = 1.6),
        TeamProbabilities("gremio", championPct = 0.0, libertadoresPct = 0.8, sulamericanaPct = 76.5, relegationPct = 6.4),
        TeamProbabilities("vitoria", championPct = 0.0, libertadoresPct = 0.2, sulamericanaPct = 58.2, relegationPct = 22.4),
        TeamProbabilities("fluminense", championPct = 0.0, libertadoresPct = 0.1, sulamericanaPct = 48.0, relegationPct = 27.5),
        TeamProbabilities("criciuma", championPct = 0.0, libertadoresPct = 0.0, sulamericanaPct = 42.1, relegationPct = 32.8),
        TeamProbabilities("juventude", championPct = 0.0, libertadoresPct = 0.0, sulamericanaPct = 36.4, relegationPct = 41.2),
        TeamProbabilities("bragantino", championPct = 0.0, libertadoresPct = 0.0, sulamericanaPct = 24.2, relegationPct = 52.6),
        TeamProbabilities("athletico_pr", championPct = 0.0, libertadoresPct = 0.0, sulamericanaPct = 11.5, relegationPct = 72.8),
        TeamProbabilities("cuiaba", championPct = 0.0, libertadoresPct = 0.0, sulamericanaPct = 0.0, relegationPct = 98.4),
        TeamProbabilities("atletico_go", championPct = 0.0, libertadoresPct = 0.0, sulamericanaPct = 0.0, relegationPct = 99.9)
    )

    val topScorers = listOf(
        Scorer("1", "Pedro", "flamengo", goals = 11, penalties = 2, assists = 5, matches = 21),
        Scorer("2", "Estêvão", "palmeiras", goals = 12, penalties = 1, assists = 8, matches = 27),
        Scorer("3", "Pablo Vegetti", "vasco", goals = 10, penalties = 3, assists = 2, matches = 30),
        Scorer("4", "Yuri Alberto", "corinthians", goals = 10, penalties = 0, assists = 4, matches = 26),
        Scorer("5", "Luciano", "sao_paulo", goals = 9, penalties = 2, assists = 3, matches = 28),
        Scorer("6", "Hulk", "atletico_mg", goals = 10, penalties = 4, assists = 6, matches = 23),
        Scorer("7", "Alerrandro", "vitoria", goals = 9, penalties = 1, assists = 4, matches = 30),
        Scorer("8", "Luiz Henrique", "botafogo", goals = 7, penalties = 0, assists = 4, matches = 31),
        Scorer("9", "Igor Jesus", "botafogo", goals = 7, penalties = 1, assists = 2, matches = 18),
        Scorer("10", "Matheus Pereira", "cruzeiro", goals = 6, penalties = 0, assists = 7, matches = 30),
        Scorer("11", "Rafael Borré", "internacional", goals = 8, penalties = 1, assists = 3, matches = 22),
        Scorer("12", "Juan Martín Lucero", "fortaleza", goals = 8, penalties = 2, assists = 3, matches = 27)
    )

    val initialMatches: List<Match> = generateAllMatches()

    private fun generateAllMatches(): List<Match> {
        val matches = mutableListOf<Match>()

        // Rodada 33 (Recent / Finished)
        matches.addAll(listOf(
            Match("33_1", 33, "botafogo", "cuiaba", 0, 0, true, "09/11", "16:30", "Nilton Santos", "Premiere"),
            Match("33_2", 33, "palmeiras", "gremio", 1, 0, true, "08/11", "21:30", "Allianz Parque", "SporTV"),
            Match("33_3", 33, "fortaleza", "vasco", 3, 0, true, "09/11", "19:00", "Castelão", "Premiere"),
            Match("33_4", 33, "flamengo", "atletico_mg", 0, 0, true, "13/11", "20:00", "Maracanã", "TV Globo"),
            Match("33_5", 33, "internacional", "fluminense", 2, 0, true, "08/11", "19:00", "Beira-Rio", "Premiere"),
            Match("33_6", 33, "sao_paulo", "athletico_pr", 2, 1, true, "09/11", "21:00", "MorumBIS", "Premiere"),
            Match("33_7", 33, "cruzeiro", "criciuma", 2, 1, true, "09/11", "19:00", "Mineirão", "Premiere"),
            Match("33_8", 33, "juventude", "bahia", 2, 1, true, "09/11", "19:00", "Alfredo Jaconi", "Premiere"),
            Match("33_9", 33, "vitoria", "corinthians", 1, 2, true, "09/11", "16:30", "Barradão", "TV Globo"),
            Match("33_10", 33, "atletico_go", "bragantino", 0, 0, true, "09/11", "19:00", "Antônio Accioly", "Premiere")
        ))

        // Rodada 34 (Upcoming simulation round 1)
        matches.addAll(listOf(
            Match("34_1", 34, "corinthians", "cruzeiro", null, null, false, "20/11", "11:00", "Neo Química Arena", "Premiere"),
            Match("34_2", 34, "bragantino", "sao_paulo", null, null, false, "20/11", "16:30", "Nabi Abi Chedid", "Premiere"),
            Match("34_3", 34, "athletico_pr", "atletico_go", null, null, false, "20/11", "16:30", "Ligga Arena", "Rede Furacão"),
            Match("34_4", 34, "criciuma", "vitoria", null, null, false, "20/11", "16:30", "Heriberto Hülse", "Premiere"),
            Match("34_5", 34, "bahia", "palmeiras", null, null, false, "20/11", "18:00", "Fonte Nova", "Premiere"),
            Match("34_6", 34, "gremio", "juventude", null, null, false, "20/11", "19:00", "Arena do Grêmio", "Premiere"),
            Match("34_7", 34, "cuiaba", "flamengo", null, null, false, "20/11", "19:00", "Arena Pantanal", "Premiere"),
            Match("34_8", 34, "atletico_mg", "botafogo", null, null, false, "20/11", "21:30", "Independência", "TV Globo"),
            Match("34_9", 34, "vasco", "internacional", null, null, false, "21/11", "20:00", "São Januário", "SporTV"),
            Match("34_10", 34, "fluminense", "fortaleza", null, null, false, "22/11", "21:30", "Maracanã", "SporTV")
        ))

        // Rodada 35
        matches.addAll(listOf(
            Match("35_1", 35, "botafogo", "vitoria", null, null, false, "23/11", "19:30", "Nilton Santos", "Premiere"),
            Match("35_2", 35, "atletico_go", "palmeiras", null, null, false, "23/11", "19:30", "Antônio Accioly", "Premiere"),
            Match("35_3", 35, "juventude", "cuiaba", null, null, false, "23/11", "19:30", "Alfredo Jaconi", "Premiere"),
            Match("35_4", 35, "sao_paulo", "atletico_mg", null, null, false, "23/11", "21:30", "MorumBIS", "SporTV"),
            Match("35_5", 35, "internacional", "bragantino", null, null, false, "24/11", "16:00", "Beira-Rio", "Premiere"),
            Match("35_6", 35, "bahia", "athletico_pr", null, null, false, "24/11", "16:00", "Fonte Nova", "TV Globo"),
            Match("35_7", 35, "corinthians", "vasco", null, null, false, "24/11", "16:00", "Neo Química Arena", "TV Globo"),
            Match("35_8", 35, "flamengo", "fortaleza", null, null, false, "26/11", "20:00", "Castelão", "Premiere"),
            Match("35_9", 35, "cruzeiro", "gremio", null, null, false, "27/11", "21:00", "Mineirão", "Premiere"),
            Match("35_10", 35, "fluminense", "criciuma", null, null, false, "26/11", "19:00", "Maracanã", "Premiere")
        ))

        // Rodada 36
        matches.addAll(listOf(
            Match("36_1", 36, "palmeiras", "botafogo", null, null, false, "26/11", "21:30", "Allianz Parque", "TV Globo"),
            Match("36_2", 36, "atletico_mg", "juventude", null, null, false, "26/11", "21:30", "Arena MRV", "Premiere"),
            Match("36_3", 36, "gremio", "sao_paulo", null, null, false, "01/12", "16:00", "Arena do Grêmio", "TV Globo"),
            Match("36_4", 36, "flamengo", "internacional", null, null, false, "01/12", "16:00", "Maracanã", "TV Globo"),
            Match("36_5", 36, "cuiaba", "bahia", null, null, false, "30/11", "19:30", "Arena Pantanal", "Premiere"),
            Match("36_6", 36, "criciuma", "corinthians", null, null, false, "30/11", "19:30", "Heriberto Hülse", "Premiere"),
            Match("36_7", 36, "vasco", "atletico_go", null, null, false, "30/11", "21:30", "São Januário", "SporTV"),
            Match("36_8", 36, "bragantino", "cruzeiro", null, null, false, "01/12", "18:30", "Nabi Abi Chedid", "Premiere"),
            Match("36_9", 36, "athletico_pr", "fluminense", null, null, false, "01/12", "18:30", "Ligga Arena", "Rede Furacão"),
            Match("36_10", 36, "vitoria", "fortaleza", null, null, false, "01/12", "18:30", "Barradão", "Premiere")
        ))

        // Rodada 37
        matches.addAll(listOf(
            Match("37_1", 37, "internacional", "botafogo", null, null, false, "04/12", "20:00", "Beira-Rio", "TV Globo"),
            Match("37_2", 37, "cruzeiro", "palmeiras", null, null, false, "04/12", "21:30", "Mineirão", "TV Globo"),
            Match("37_3", 37, "fortaleza", "corinthians", null, null, false, "04/12", "20:00", "Castelão", "Premiere"),
            Match("37_4", 37, "criciuma", "flamengo", null, null, false, "04/12", "20:00", "Heriberto Hülse", "Premiere"),
            Match("37_5", 37, "sao_paulo", "juventude", null, null, false, "04/12", "20:00", "MorumBIS", "Premiere"),
            Match("37_6", 37, "bahia", "atletico_go", null, null, false, "04/12", "20:00", "Fonte Nova", "Premiere"),
            Match("37_7", 37, "vasco", "atletico_mg", null, null, false, "04/12", "20:00", "São Januário", "SporTV"),
            Match("37_8", 37, "fluminense", "cuiaba", null, null, false, "04/12", "20:00", "Maracanã", "Premiere"),
            Match("37_9", 37, "athletico_pr", "bragantino", null, null, false, "04/12", "20:00", "Ligga Arena", "Rede Furacão"),
            Match("37_10", 37, "vitoria", "gremio", null, null, false, "04/12", "20:00", "Barradão", "Premiere")
        ))

        // Rodada 38 (Final Round)
        matches.addAll(listOf(
            Match("38_1", 38, "botafogo", "sao_paulo", null, null, false, "08/12", "16:00", "Nilton Santos", "TV Globo"),
            Match("38_2", 38, "palmeiras", "fluminense", null, null, false, "08/12", "16:00", "Allianz Parque", "TV Globo"),
            Match("38_3", 38, "fortaleza", "internacional", null, null, false, "08/12", "16:00", "Castelão", "Premiere"),
            Match("38_4", 38, "flamengo", "vitoria", null, null, false, "08/12", "16:00", "Maracanã", "Premiere"),
            Match("38_5", 38, "atletico_mg", "athletico_pr", null, null, false, "08/12", "16:00", "Arena MRV", "Premiere"),
            Match("38_6", 38, "corinthians", "gremio", null, null, false, "08/12", "16:00", "Neo Química Arena", "Premiere"),
            Match("38_7", 38, "juventude", "cruzeiro", null, null, false, "08/12", "16:00", "Alfredo Jaconi", "Premiere"),
            Match("38_8", 38, "bragantino", "criciuma", null, null, false, "08/12", "16:00", "Nabi Abi Chedid", "Premiere"),
            Match("38_9", 38, "cuiaba", "vasco", null, null, false, "08/12", "16:00", "Arena Pantanal", "Premiere"),
            Match("38_10", 38, "atletico_go", "bahia", null, null, false, "08/12", "16:00", "Antônio Accioly", "Premiere")
        ))

        // Historical selected previous rounds sample
        // Rodada 32
        matches.addAll(listOf(
            Match("32_1", 32, "botafogo", "vasco", 3, 0, true, "05/11", "21:30", "Nilton Santos", "TV Globo"),
            Match("32_2", 32, "corinthians", "palmeiras", 2, 0, true, "04/11", "20:00", "Neo Química Arena", "SporTV"),
            Match("32_3", 32, "juventude", "fortaleza", 0, 3, true, "02/11", "18:30", "Alfredo Jaconi", "Premiere"),
            Match("32_4", 32, "cruzeiro", "flamengo", 0, 1, true, "06/11", "21:00", "Mineirão", "Premiere"),
            Match("32_5", 32, "internacional", "criciuma", 2, 0, true, "05/11", "21:30", "Beira-Rio", "Premiere"),
            Match("32_6", 32, "bahia", "sao_paulo", 0, 3, true, "05/11", "21:30", "Fonte Nova", "Premiere"),
            Match("32_7", 32, "atletico_mg", "atletico_go", 1, 0, true, "06/11", "21:00", "Arena MRV", "Premiere"),
            Match("32_8", 32, "fluminense", "gremio", 2, 2, true, "01/11", "21:00", "Maracanã", "SporTV"),
            Match("32_9", 32, "athletico_pr", "vitoria", 1, 2, true, "02/11", "18:30", "Ligga Arena", "Rede Furacão"),
            Match("32_10", 32, "bragantino", "cuiaba", 0, 0, true, "02/11", "16:00", "Nabi Abi Chedid", "Premiere")
        ))

        // Rodada 31
        matches.addAll(listOf(
            Match("31_1", 31, "bragantino", "botafogo", 0, 1, true, "26/10", "19:00", "Nabi Abi Chedid", "Premiere"),
            Match("31_2", 31, "palmeiras", "fortaleza", 2, 2, true, "26/10", "16:30", "Allianz Parque", "TV Globo"),
            Match("31_3", 31, "flamengo", "juventude", 4, 2, true, "26/10", "16:30", "Maracanã", "TV Globo"),
            Match("31_4", 31, "atletico_mg", "internacional", 1, 3, true, "26/10", "19:00", "Arena MRV", "Premiere"),
            Match("31_5", 31, "sao_paulo", "criciuma", 1, 1, true, "26/10", "21:00", "Heriberto Hülse", "Premiere"),
            Match("31_6", 31, "vasco", "bahia", 3, 2, true, "28/10", "21:00", "São Januário", "SporTV"),
            Match("31_7", 31, "athletico_pr", "cruzeiro", 3, 0, true, "26/10", "18:30", "Ligga Arena", "Rede Furacão"),
            Match("31_8", 31, "cuiaba", "corinthians", 0, 1, true, "28/10", "19:00", "Arena Pantanal", "Premiere"),
            Match("31_9", 31, "gremio", "atletico_go", 3, 1, true, "26/10", "16:30", "Arena do Grêmio", "Premiere"),
            Match("31_10", 31, "vitoria", "fluminense", 2, 1, true, "26/10", "16:30", "Barradão", "Premiere")
        ))

        return matches
    }
}
