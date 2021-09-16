import { getTokenFromStorage, getUsernameFromToken } from "../services/jwt.service.ts";


export const routes = [
    {
        path: "/",
        redirect: (route) => {
            const token = getTokenFromStorage();
            return token
                ? `/${getUsernameFromToken(token)}/select`
                : "/welcome";
        },
    },
    {
        path: "/welcome",
        component: () =>
            import(
                /* webpackChunkName: "welcome", webpackPrefetch: true */ "../app/views/welcome/Welcome.vue"
            ),
        name: "welcome",
        meta: {
            title: (route) => {
                return "Welcome";
            },
            description: (route) => {
                return "";
            },
            requiresAuth: false,
        },
    },
    {
        path: "/:username/select",
        component: () =>
            import(
                /* webpackChunkName: "league-select", webpackPrefetch: true */ "../app/views/save-selection/SelectSave.vue"
            ),
        name: "league-select",
        meta: {
            title: (route) => {
                return "Select Save";
            },
            description: (route) => {
                return "";
            },
            requiresAuth: true,
        },
    },
    {
        path: "/:username/:league_id/play",
        component: () =>
            import(
                /* webpackChunkName: "play", webpackPrefetch: true */ "../app/views/home/Home.vue"
            ),
        name: "play",
        meta: {
            title: (route) => {
                return "Welcome";
            },
            description: (route) => {
                return "";
            },
            requiresAuth: true,
        },
    },
    {
        path: "/:username/:league_id/search",
        component: () =>
            import(
                /* webpackChunkName: "search", webpackPrefetch: true */ "../app/views/search/Search.vue"
            ),
        name: "search",
        meta: {
            title: (route) => {
                return "Search";
            },
            description: (route) => {
                return "";
            },
            requiresAuth: true,
        },
    },
    {
        path: "/:username/:league_id/insights",
        component: () =>
            import(
                /* webpackChunkName: "insights", webpackPrefetch: true */ "../app/views/insights/Insights.vue"
            ),
        name: "insights",
        meta: {
            title: (route) => {
                return "Insights";
            },
            description: (route) => {
                return "";
            },
            requiresAuth: true,
        },
    },
    {
        path: "/:username/:league_id/team",
        component: () =>
            import(
                /* webpackChunkName: "team", webpackPrefetch: true */ "../app/views/team/Team.vue"
            ),
        name: "team",
        meta: {
            title: (route) => {
                return "Team";
            },
            description: (route) => {
                return "";
            },
            requiresAuth: true,
        },
    },
    {
        path: "/:username/:league_id/settings",
        component: () =>
            import(
                /* webpackChunkName: "settings", webpackPrefetch: true */ "../app/views/settings/Settings.vue"
            ),
        name: "settings",
        meta: {
            title: (route) => {
                return "Settings";
            },
            description: (route) => {
                return "";
            },
            requiresAuth: true,
        },
    },
    {
        path: "/:catchAll(.*)",
        component: () =>
            import(
                /* webpackChunkName: "not-found", webpackPrefetch: true */ "../app/views/NotFound.vue"
            ),
        meta: {
            title: (route) => {
                return "Not Found";
            },
            description: (route) => {
                return "That page doesn't exist!";
            },
            requiresAuth: false,
        },
    },
];
