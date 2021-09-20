import { User } from "@/@types/user";
import AuthService from "@/services/auth.service";
import {
  getTokenFromStorage,
  getUsernameFromStorage,
  removeJwtToken,
  setJwtToken
} from "@/services/jwt.service";
import { ActionTree, Commit, GetterTree, MutationTree } from "vuex";

export interface AuthState {
  status: { loggedIn: boolean };
  token: string | null;
}

const token = getTokenFromStorage();
const state: AuthState = token
  ? { status: { loggedIn: true }, token }
  : { status: { loggedIn: false }, token: null };

const getters = <GetterTree<AuthState, any>>{
  loggedIn: (state: AuthState) => {
    return getUsernameFromStorage() !== undefined;
  },
};

const actions = <ActionTree<AuthState, any>>{
  login({ commit }: { commit: Commit }, user: User) {
    return AuthService.login(user)
      .then((token: string) => {
        if (token) {
          commit(`authSuccess`, token);
          return Promise.resolve(token);
        }
        commit(`authFailure`);
        return Promise.reject(token);
      })
      .catch((error: string) => {
        commit(`authFailure`);
        return Promise.reject(error);
      });
  },

  logout({ commit }: { commit: Commit }) {
    AuthService.logout();
    commit("setCurrentLeagueID", 0, { root: true });
    commit("logout");
  },
  register({ commit }: { commit: Commit }, user: User) {
    return AuthService.register(user)
      .then((token: string) => {
        if (token) {
          commit(`authSuccess`, token);
          return Promise.resolve(token);
        }
        commit(`authFailure`);
        return Promise.reject(token);
      })
      .catch((error: string) => {
        commit(`authFailure`);
        return Promise.reject(error);
      });
  },
};

const mutations = <MutationTree<AuthState>>{
  authSuccess(state: AuthState, token: string) {
    setJwtToken(token);
    state.status.loggedIn = true;
    state.token = token;
  },
  authFailure(state: AuthState) {
    state.status.loggedIn = false;
    state.token = null;
  },
  logout(state: AuthState) {
    removeJwtToken();
    state.status.loggedIn = false;
    state.token = null;
  },
};

export const auth = {
  namespaced: true,
  state,
  getters,
  actions,
  mutations,
};
