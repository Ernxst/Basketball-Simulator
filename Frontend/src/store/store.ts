import { InjectionKey } from "vue";
import {
  createLogger,
  createStore,
  Store,
  useStore as baseUseStore
} from "vuex";
import createPersistedState from "vuex-persistedstate";
import { auth, AuthState } from "./modules/auth.module";
import { league, LeagueState } from "./modules/league.module";
import { media, MediaState } from "./modules/media.module";

// Plug in logger when in development environment
const debug = process.env.NODE_ENV !== "production";
const plugins = debug ? [createLogger({})] : [];

// Plug in session storage based persistence
plugins.push(createPersistedState({ storage: window.localStorage }));

export type State = AuthState & LeagueState & MediaState;
export const key: InjectionKey<Store<State>> = Symbol();

export const store = createStore<State>({
  strict: process.env.NODE_ENV !== "production",
  plugins,
  modules: {
    auth,
    media,
    league,
  },
});

export function useStore() {
  return baseUseStore(key);
}
