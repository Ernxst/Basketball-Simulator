import { StringAnyMap } from "@/@types/maps";
import leagueService from "@/services/league.service";
import { ActionTree, GetterTree, MutationTree } from "vuex";

export interface LeagueState {
  leagueSaves: StringAnyMap[];
  currentLeagueID: Number;
}

const state: LeagueState = {
  leagueSaves: [],
  currentLeagueID: 0,
};

const getters = <GetterTree<LeagueState, any>>{
  leagueSaves: (state: LeagueState) => {
    return state.leagueSaves;
  },
};

const actions = <ActionTree<LeagueState, any>>{
  fetchLeagueSaves({ commit }: { commit: Function }) {
    leagueService.fetchLeagueSaves().then((saves: StringAnyMap[]) => {
      commit("setLeagueSaves", saves);
    });
  },
  newLeague({ commit }: { commit: Function }, params: StringAnyMap) {
    leagueService.generateLeague(params).then((leagueID: number) => {
      commit("setCurrentLeagueID", leagueID);
    });
  },
};

const mutations = <MutationTree<LeagueState>>{
  setLeagueSaves(state: LeagueState, leagueSaves: StringAnyMap[]) {
    state.leagueSaves = leagueSaves;
  },
  setCurrentLeagueID(state: LeagueState, leagueID: number) {
    state.currentLeagueID = leagueID;
  },
};

export const league = {
  namespaced: true,
  state,
  getters,
  actions,
  mutations,
};
