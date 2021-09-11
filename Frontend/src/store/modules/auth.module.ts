import AuthService from './../../services/auth.service';
import { User } from "../../assets/types";
// @ts-ignore
import { Commit } from "vuex";
import { getTokenFromStorage } from "../../services/jwt.service";


interface State {
    status: { loggedIn: boolean },
    token: string | null
}

const token = getTokenFromStorage();
const initialState = token
    ? { status: { loggedIn: true }, token }
    : { status: { loggedIn: false }, token: null };

export const auth = {
    namespaced: true,
    state: initialState,
    actions: {
        /**
         *
         * @param commit
         * @param user
         */
        login({ commit }: { commit: Commit }, user: User): Promise<any> {
            return AuthService.login(user).then(
                (response: { username: string, token: string }) => {
                    if (response.token) {
                        commit('loginSuccess', response.token);
                        return Promise.resolve(response);
                    }
                    commit('loginFailure');
                    return Promise.reject(response);
                },
                (error: Error) => {
                    commit('loginFailure');
                    return Promise.reject(error);
                }
            );
        },
        logout({ commit }: { commit: Commit }) {
            AuthService.logout();
            commit('logout');
        },
        /**
         *
         * @param commit
         * @param user
         */
        register({ commit }: { commit: Commit }, user: User): Promise<any> {
            return AuthService.register(user).then(
                (response: { username: string, token: string }) => {
                    if (response.token) {
                        commit('registerSuccess', response.token);
                        return Promise.resolve(response);
                    }
                    commit('registerFailure');
                    return Promise.reject(response);
                },
                (error: Error) => {
                    commit('registerFailure');
                    return Promise.reject(error);
                }
            );
        }
    },
    mutations: {
        loginSuccess(state: State, token: string) {
            state.status.loggedIn = true;
            state.token = token;
        },
        loginFailure(state: State) {
            state.status.loggedIn = false;
            state.token = null;
        },
        logout(state: State) {
            state.status.loggedIn = false;
            state.token = null;
        },
        registerSuccess(state: State, token : string) {
            state.status.loggedIn = true;
            state.token = token;
        },
        registerFailure(state: State) {
            state.status.loggedIn = false;
        }
    }
};
