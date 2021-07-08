import MediaService from '../../services/media.service';
import { routes } from "../../router/routes";


interface State {
    backgrounds: Record<string, string>,
    "team-icons": Record<string, string>,
    "icons": Record<string, string>,
}

const state = () => ({
    "backgrounds": {},
    "team-icons": {},
    "icons": {}
});

const getters = {
    backgrounds: (state: State) => {
        return state.backgrounds;
    },
};

export const media = {
    namespaced: true,
    state,
    getters,
    actions: {
        fetchBackgrounds({ commit, getters }: { commit: Function, getters: any }) {
            const names = [];
            for (const route of routes) {
                if (route.name) {
                    names.push(route.name);
                }
            }
            // Only fetch images if not currently stored to reduce bandwidth.
            const backgrounds = getters["media/backgrounds"];
            if (backgrounds && Object.values(backgrounds).length < names.length)
                MediaService.fetchBackgrounds(names).then((backgrounds) => {
                    commit("setBackgrounds", backgrounds);
                });
        }
    },
    mutations: {
        setBackgrounds(state: State, backgrounds: Record<string, string>,) {
            state.backgrounds = backgrounds;
        }
    }
};