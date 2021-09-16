import { StringAnyMap } from "./../../assets/types";
import leagueService from "../../services/league.service";

interface State {
    leagueSaves: StringAnyMap[];
    currentLeagueID: Number;
}

const state = () => ({
    leagueSaves: [] as StringAnyMap[],
    currentLeague: null as Number,
});

const getters = {
    leagueSaves: (state: State) => {
        return state.leagueSaves;
    },
};

export const league = {
    namespaced: true,
    state,
    getters,
    actions: {
        fetchLeagueSaves({
            commit,
            getters,
        }: {
            commit: Function;
            getters: any;
        }) {
            leagueService.fetchLeagueSaves().then((saves) => {
                commit("setLeagueSaves", saves);
            });
        },
        newLeague(
            {
                commit,
                getters,
            }: {
                commit: Function;
                getters: any;
            },
            params: StringAnyMap
        ) {
            leagueService.generateLeague(params).then((leagueID) => {
                commit("setCurrentLeagueID", leagueID);
            });
        },
    },
    mutations: {
        setLeagueSaves(state: State, leagueSaves: Array<StringAnyMap>) {
            state.leagueSaves = leagueSaves;
        },
        setCurrentLeagueID(state: State, leagueID: Number) {
            state.currentLeagueID = leagueID;
        },
    },
};
