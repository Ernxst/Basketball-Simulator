import { StringAnyMap } from "@/@types/maps";
import { makeRequest } from "./api";
import {
  RELATIVE_GET_LEAGUE_SAVES_ENDPOINT,
  RELATIVE_NEW_LEAGUE_ENDPOINT
} from "./endpoints";

type SavesResponse = {
  saves?: StringAnyMap[];
  error?: string;
};

type GenerateLeagueResponse = {
  league_id?: number;
  error?: string;
};

class LeagueService {
  async fetchLeagueSaves(): Promise<StringAnyMap[]> {
    const savesResponse: SavesResponse = await makeRequest<{
      saves: StringAnyMap[];
    }>({
      endpoint: RELATIVE_GET_LEAGUE_SAVES_ENDPOINT,
      method: "GET",
    });
    const saves = savesResponse.saves;
    if (saves) return saves;
    return Promise.reject(savesResponse.error);
  }

  async generateLeague(params: StringAnyMap): Promise<number> {
    params["start_date"] = new Date(params["start_date"]).toLocaleDateString(
      "en-CA"
    );
    const leagueIdResponse: GenerateLeagueResponse = await makeRequest<{
      league_id: number;
    }>({
      endpoint: RELATIVE_NEW_LEAGUE_ENDPOINT,
      method: "POST",
      body: params,
    });
    const leagueID = leagueIdResponse.league_id;
    if (leagueID) return leagueID;
    return Promise.reject(leagueIdResponse.error);
  }
}

export default new LeagueService();
