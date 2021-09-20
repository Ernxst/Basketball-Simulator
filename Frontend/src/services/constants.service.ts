import { makeRequest } from "./api";
import {
  RELATIVE_MAX_LEAGUES_ENDPOINT,
  RELATIVE_MAX_TEAMS_ENDPOINT,
  RELATIVE_MIN_TEAMS_ENDPOINT,
  RELATIVE_TEAM_NAMES_ENDPOINT,
  RELATIVE_TEAM_STATES_ENDPOINT
} from "./endpoints";

type MaxLeaguesResponse =
  | number
  | {
      error: string;
    };

type MaxTeamsResponse =
  | number
  | {
      error: string;
    };

type MinTeamsResponse =
  | number
  | {
      error: string;
    };

type TeamStatesResponse = {
  team_states?: string[];
  error?: string;
};

type TeamNamesResponse = {
  team_names?: string[];
  error?: string;
};
class ConstantsService {
  async maxLeaguesPerAccount(): Promise<number> {
    const maxLeaguesPerAccountResponse: MaxLeaguesResponse =
      await makeRequest<number>({
        endpoint: RELATIVE_MAX_LEAGUES_ENDPOINT,
        method: "GET",
      });
    if (typeof maxLeaguesPerAccountResponse === "number")
      return maxLeaguesPerAccountResponse;
    return Promise.reject(maxLeaguesPerAccountResponse["error"]);
  }

  async minTeamsInLeague(): Promise<number> {
    const minTeamsInLeagueResponse: MinTeamsResponse =
      await makeRequest<number>({
        endpoint: RELATIVE_MIN_TEAMS_ENDPOINT,
        method: "GET",
      });
    if (typeof minTeamsInLeagueResponse === "number")
      return minTeamsInLeagueResponse;
    return Promise.reject(minTeamsInLeagueResponse["error"]);
  }

  async maxTeamsInLeague(): Promise<number> {
    const maxTeamsInLeagueResponse: MaxTeamsResponse =
      await makeRequest<number>({
        endpoint: RELATIVE_MAX_TEAMS_ENDPOINT,
        method: "GET",
      });
    if (typeof maxTeamsInLeagueResponse === "number")
      return maxTeamsInLeagueResponse;
    return Promise.reject(maxTeamsInLeagueResponse["error"]);
  }

  async teamNames(): Promise<string[]> {
    const teamNamesResponse: TeamNamesResponse = await makeRequest<{
      team_names: string[];
    }>({
      endpoint: RELATIVE_TEAM_NAMES_ENDPOINT,
      method: "GET",
    });
    const teamNames = teamNamesResponse.team_names;
    if (teamNames) return teamNames;
    return Promise.reject(teamNamesResponse.error);
  }

  async teamStates(): Promise<string[]> {
    const teamStatesResponse: TeamStatesResponse = await makeRequest<{
      team_states: string[];
    }>({
      endpoint: RELATIVE_TEAM_STATES_ENDPOINT,
      method: "GET",
    });
    const teamStates = teamStatesResponse.team_states;
    if (teamStates) return teamStates;
    return Promise.reject(teamStatesResponse.error);
  }
}

export default new ConstantsService();
