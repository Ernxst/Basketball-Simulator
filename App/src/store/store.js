import {createStore} from "vuex";
import createPersistedState from "vuex-persistedstate";
import {auth} from "./modules/auth.module.js";

export const store = createStore({
    strict: process.env.NODE_ENV !== 'production',
    plugins: [createPersistedState({
        storage: window.localStorage,
    })],
    modules: {
        auth
    },
});