import { StringStringMap } from "@/@types/maps";
import { routes } from "@/router/routes";
import MediaService from "@/services/media.service";
import { ActionTree, GetterTree, MutationTree } from "vuex";

export interface MediaState {
  backgrounds: StringStringMap;
  team_icons: StringStringMap;
  icons: StringStringMap;
}

const state: MediaState = {
  backgrounds: {},
  team_icons: {},
  icons: {},
};

const getters = <GetterTree<MediaState, any>>{
  backgrounds: (state: MediaState) => {
    return state.backgrounds;
  },
  team_icons: (state: MediaState) => {
    return state.team_icons;
  },
  icons: (state: MediaState) => {
    return state.icons;
  },
};

const actions = <ActionTree<MediaState, any>>{
  fetchBackgrounds({ commit, getters }: { commit: Function; getters: any }) {
    const names = [];
    for (const route of routes) {
      if (route.name) {
        names.push(route.name);
      }
    }
    // Only fetch images if not currently stored to reduce bandwidth.
    const backgrounds = getters["media/backgrounds"];
    if (!backgrounds || Object.values(backgrounds).length < names.length)
      MediaService.fetchBackgrounds(names).then((backgrounds) => {
        commit("setBackgrounds", backgrounds);
      });
  },
};

const mutations = <MutationTree<MediaState>>{
  setBackgrounds(state: MediaState, backgrounds: StringStringMap) {
    state.backgrounds = backgrounds;
  },
};

export const media = {
  namespaced: true,
  state,
  getters,
  actions,
  mutations,
};
