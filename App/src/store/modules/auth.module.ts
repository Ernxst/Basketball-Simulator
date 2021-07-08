import AuthService from './../../services/auth.service';
import { User } from "../../assets/types";
// @ts-ignore
import { Commit } from "vuex";


interface State {
    status: { loggedIn: boolean },
    user: User | null
}

const user = JSON.parse(localStorage.getItem('user'));
const initialState = user
    ? { status: { loggedIn: true }, user }
    : { status: { loggedIn: false }, user: null };

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
                (user: { accessToken: string }) => {
                    commit('loginSuccess', user);
                    return Promise.resolve(user);
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
                (response: { data: any; }) => {
                    commit('registerSuccess');
                    return Promise.resolve(response.data);
                },
                (error: Error) => {
                    commit('registerFailure');
                    return Promise.reject(error);
                }
            );
        }
    },
    mutations: {
        loginSuccess(state: State, user: User) {
            state.status.loggedIn = true;
            state.user = user;
        },
        loginFailure(state: State) {
            state.status.loggedIn = false;
            state.user = null;
        },
        logout(state: State) {
            state.status.loggedIn = false;
            state.user = null;
        },
        registerSuccess(state: State) {
            state.status.loggedIn = false;
        },
        registerFailure(state: State) {
            state.status.loggedIn = false;
        }
    }
};