import { createStore } from "vuex";
import createPersistedState from "vuex-persistedstate";
import { auth } from "./modules/auth.module.ts";
import { media } from "./modules/media.module.ts";
import { league } from "./modules/league.module.ts";

export const store = createStore({
    strict: process.env.NODE_ENV !== "production",
    plugins: [
        createPersistedState({
            storage: window.localStorage,
        }),
    ],
    modules: {
        auth,
        media,
        league,
    },
});
