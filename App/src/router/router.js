import {nextTick} from "@vue/runtime-core";
import {createRouter, createWebHistory} from "vue-router";
import {routes} from "./routes";


const router = createRouter({
    mode: "history",
    history: createWebHistory(),
    scrollBehavior(to, from, savedPosition) {
        if (to.hash) {
            return { selector: to.hash };
        } else if (savedPosition) {
            return savedPosition;
        } else {
            return { x: 0, y: 0 };
        }
    },
    routes,
});

router.beforeEach((to, from, next) => {
    const loggedIn = localStorage.getItem('user');
    if (to.matched.some((record) => record.meta.requiresAuth)) {
        if (!loggedIn) {
            next({
                path: "/welcome",
            });
        } else {
            next();
        }
    } else {
        next();
    }
});

const DEFAULT_DESCRIPTION = '';
const APP_TITLE = "Basketball Simulator";

router.afterEach((to, from) => {
    nextTick(() => {
        document.title = to.meta.title(to) + " — " + APP_TITLE;
        const desc = document.querySelector('head meta[name="description"]');
        const content = to.meta.description(to) || DEFAULT_DESCRIPTION;
        desc.setAttribute('content', content);
    });
});

export default router;