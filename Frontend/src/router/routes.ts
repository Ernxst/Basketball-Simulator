import {
  getTokenFromStorage,
  getUsernameFromToken
} from "@/services/jwt.service";
import { RouteLocationNormalized } from "vue-router";

export const routes = [
  {
    path: "/",
    redirect: (route: RouteLocationNormalized) => {
      const token = getTokenFromStorage();
      return token ? `/${getUsernameFromToken(token)}/select` : "/welcome";
    },
  },
  {
    path: "/welcome",
    component: () => import("@/views/welcome/Welcome.vue"),
    name: "welcome",
    meta: {
      title: (route: RouteLocationNormalized) => {
        return "Welcome";
      },
      description: (route: RouteLocationNormalized) => {
        return "";
      },
      requiresAuth: false,
    },
  },
  {
    path: "/:username/select",
    component: () => import("@/views/save-selection/SelectSave.vue"),
    name: "league-select",
    meta: {
      title: (route: RouteLocationNormalized) => {
        return "Select Save";
      },
      description: (route: RouteLocationNormalized) => {
        return "";
      },
      requiresAuth: true,
    },
  },
  // {
  //     path: "/:username/:league_id/play",
  //     component: () => import("@/views/home/Home.vue"),
  //     name: "play",
  //     meta: {
  //         title: (route: RouteLocationNormalized) => {
  //             return "Welcome";
  //         },
  //         description: (route: RouteLocationNormalized) => {
  //             return "";
  //         },
  //         requiresAuth: true,
  //     },
  // },
  // {
  //     path: "/:username/:league_id/search",
  //     component: () => import("@/views/search/Search.vue"),
  //     name: "search",
  //     meta: {
  //         title: (route: RouteLocationNormalized) => {
  //             return "Search";
  //         },
  //         description: (route: RouteLocationNormalized) => {
  //             return "";
  //         },
  //         requiresAuth: true,
  //     },
  // },
  // {
  //     path: "/:username/:league_id/insights",
  //     component: () => import("@/views/insights/Insights.vue"),
  //     name: "insights",
  //     meta: {
  //         title: (route: RouteLocationNormalized) => {
  //             return "Insights";
  //         },
  //         description: (route: RouteLocationNormalized) => {
  //             return "";
  //         },
  //         requiresAuth: true,
  //     },
  // },
  // {
  //     path: "/:username/:league_id/team",
  //     component: () => import("@/views/team/Team.vue"),
  //     name: "team",
  //     meta: {
  //         title: (route: RouteLocationNormalized) => {
  //             return "Team";
  //         },
  //         description: (route: RouteLocationNormalized) => {
  //             return "";
  //         },
  //         requiresAuth: true,
  //     },
  // },
  // {
  //     path: "/:username/:league_id/settings",
  //     component: () => import("@/views/settings/Settings.vue"),
  //     name: "settings",
  //     meta: {
  //         title: (route: RouteLocationNormalized) => {
  //             return "Settings";
  //         },
  //         description: (route: RouteLocationNormalized) => {
  //             return "";
  //         },
  //         requiresAuth: true,
  //     },
  // },
  {
    path: "/:catchAll(.*)",
    component: () => import("@/views/NotFound.vue"),
    meta: {
      title: (route: RouteLocationNormalized) => {
        return "Not Found";
      },
      description: (route: RouteLocationNormalized) => {
        return "That page doesn't exist!";
      },
      requiresAuth: false,
    },
  },
];
