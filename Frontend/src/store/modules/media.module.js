import MediaService from '../../services/media.service.js';


const state = () => ({
    "backgrounds": {},
    "team-icons": {},
    "icons": {}
});

const getters = {
    backgrounds: (state) => {
        return state.backgrounds;
    },
};

export const media = {
    namespaced: true,
    state,
    getters,
    actions: {
        fetchBackgrounds({ commit }) {
            MediaService.fetchBackgrounds().then((backgrounds) => {
                commit("setBackgrounds", backgrounds);
            });
        }
    },
    mutations: {
        setBackgrounds(state, backgrounds) {
            state.backgrounds = backgrounds;
        }
    }
};