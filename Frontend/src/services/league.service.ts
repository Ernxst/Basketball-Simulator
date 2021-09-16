import { StringAnyMap } from './../assets/types';
import { resolvePromiseFromApi } from "./api";
import { RELATIVE_GET_LEAGUE_SAVES_ENDPOINT, RELATIVE_NEW_LEAGUE_ENDPOINT } from "./endpoints";

class LeagueService {
    async fetchLeagueSaves(): Promise<Response> {
        return resolvePromiseFromApi(
            RELATIVE_GET_LEAGUE_SAVES_ENDPOINT,
            "GET",
            {},
            {},
            true,
            "saves"
        );
    }

    async generateLeague(params : StringAnyMap): Promise<Response> {
        params["start_date"] = new Date(params["start_date"]).toLocaleDateString("en-CA");
        return resolvePromiseFromApi(
            RELATIVE_NEW_LEAGUE_ENDPOINT,
            "POST",
            {},
            params,
            true,
            "league_id"
        );
    }
}

export default new LeagueService();
