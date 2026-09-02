"""
Scraper do Campeonato Brasileiro Série A & Probabilidades UFMG
Extrai dados estruturados da tabela, rodadas, artilharia (Globo Esporte)
e probabilidades matemáticas (Departamento de Matemática da UFMG).

Requisitos:
    pip install requests beautifulsoup4
"""

import json
import re
import requests
from bs4 import BeautifulSoup

HEADERS = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"
}

URL_GE = "https://ge.globo.com/futebol/brasileirao-serie-a/"
URL_UFMG = "https://www.mat.ufmg.br/futebol/serie-a/"

def scrape_ge_standings():
    """Raspa a tabela de classificação oficial do Globo Esporte."""
    print("Raspando Tabela do Globo Esporte...")
    response = requests.get(URL_GE, headers=HEADERS)
    if response.status_code != 200:
        print(f"Erro ao acessar {URL_GE}: {response.status_code}")
        return []

    soup = BeautifulSoup(response.text, "html.parser")
    standings = []

    # Localiza as linhas da tabela
    rows = soup.select(".tabela__times .tabela__equipe")
    stats_rows = soup.select(".tabela__pontos-linhas")

    for idx, row in enumerate(rows):
        team_name = row.select_one(".tabela__equipe-nome")
        shield = row.select_one(".tabela__escudo")
        name = team_name.text.strip() if team_name else f"Time {idx+1}"
        shield_url = shield.get("src", "") if shield else ""

        stats = stats_rows[idx].select("td") if idx < len(stats_rows) else []
        
        # P, J, V, E, D, GP, GC, SG, %
        points = int(stats[0].text.strip()) if len(stats) > 0 and stats[0].text.strip().isdigit() else 0
        played = int(stats[1].text.strip()) if len(stats) > 1 and stats[1].text.strip().isdigit() else 0
        won = int(stats[2].text.strip()) if len(stats) > 2 and stats[2].text.strip().isdigit() else 0
        drawn = int(stats[3].text.strip()) if len(stats) > 3 and stats[3].text.strip().isdigit() else 0
        lost = int(stats[4].text.strip()) if len(stats) > 4 and stats[4].text.strip().isdigit() else 0
        gp = int(stats[5].text.strip()) if len(stats) > 5 and stats[5].text.strip().isdigit() else 0
        gc = int(stats[6].text.strip()) if len(stats) > 6 and stats[6].text.strip().isdigit() else 0
        sg = int(stats[7].text.strip()) if len(stats) > 7 and stats[7].text.strip().lstrip('-').isdigit() else (gp - gc)

        standings.append({
            "position": idx + 1,
            "team": name,
            "logoUrl": shield_url,
            "points": points,
            "played": played,
            "won": won,
            "drawn": drawn,
            "lost": lost,
            "goalsFor": gp,
            "goalsAgainst": gc,
            "goalDifference": sg
        })

    return standings

def scrape_ufmg_probabilities():
    """Raspa probabilidades matemáticas do Departamento de Matemática da UFMG."""
    print("Raspando Probabilidades da UFMG...")
    response = requests.get(URL_UFMG, headers=HEADERS)
    if response.status_code != 200:
        print(f"Erro ao acessar {URL_UFMG}: {response.status_code}")
        return []

    soup = BeautifulSoup(response.text, "html.parser")
    probabilities = []

    # Procura tabelas de probabilidades (Campeão, Libertadores, Rebaixamento)
    prob_table = soup.select_one("table")
    if prob_table:
        for row in prob_table.select("tr")[1:]:
            cols = [td.text.strip().replace("%", "").replace(",", ".") for td in row.select("td")]
            if len(cols) >= 5:
                probabilities.append({
                    "team": cols[0],
                    "points": cols[1],
                    "championPct": float(cols[2]) if cols[2].replace('.', '', 1).isdigit() else 0.0,
                    "libertadoresPct": float(cols[3]) if cols[3].replace('.', '', 1).isdigit() else 0.0,
                    "relegationPct": float(cols[4]) if cols[4].replace('.', '', 1).isdigit() else 0.0
                })

    return probabilities

def main():
    print("Iniciando extração do Brasileirão Série A...")
    standings = scrape_ge_standings()
    probabilities = scrape_ufmg_probabilities()

    data = {
        "updatedAt": "2024-11-20T12:00:00Z",
        "league": "Campeonato Brasileiro Série A",
        "standings": standings,
        "probabilities": probabilities
    }

    with open("brasileirao_data.json", "w", encoding="utf-8") as f:
        json.dump(data, f, ensure_ascii=False, indent=2)

    print("Arquivo 'brasileirao_data.json' gerado com sucesso!")

if __name__ == "__main__":
    main()
