import {
    RELATIVE_MAX_LEAGUES_ENDPOINT,
    RELATIVE_MAX_TEAMS_ENDPOINT,
    RELATIVE_MIN_TEAMS_ENDPOINT,
    RELATIVE_TEAM_NAMES_ENDPOINT,
    RELATIVE_TEAM_STATES_ENDPOINT
} from "./endpoints";
import { makeRequest } from "./api";


class ConstantsService {
    async maxLeaguesPerAccount(): Promise<number | Array<string>> {
        return resolvePromiseFromApi(RELATIVE_MAX_LEAGUES_ENDPOINT);
    }

    async minTeamsInLeague(): Promise<number | Array<string>> {
        return resolvePromiseFromApi(RELATIVE_MIN_TEAMS_ENDPOINT);
    }

    async maxTeamsInLeague(): Promise<number | Array<string>> {
        return resolvePromiseFromApi(RELATIVE_MAX_TEAMS_ENDPOINT);
    }

    async teamNames(): Promise<number | Array<string>> {
        return resolvePromiseFromApi(RELATIVE_TEAM_NAMES_ENDPOINT);
    }

    async teamStates(): Promise<number | Array<string>> {
        return resolvePromiseFromApi(RELATIVE_TEAM_STATES_ENDPOINT);
    }
}

const resolvePromiseFromApi = (endpoint: string):
    Promise<number | Array<string>> => {
    return makeRequest(endpoint, "GET", {}, {}, true)
        .then((response: number | { [key: string]: Array<string> }) => {
            return new Promise((resolve, _) => {
                const data: Array<string> | number = (typeof response === 'number') ? response :
                    response.team_states ?? response.team_names;
                resolve(data);
            });
        }, (response: { error: string }) => {
            return new Promise((_, reject) => {
                reject(response.error);
            });
        });
};

export default new ConstantsService();
