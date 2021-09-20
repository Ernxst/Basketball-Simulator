import { store } from "@/store/store";
import { nextTick } from "@vue/runtime-core";
import { createRouter, createWebHistory } from "vue-router";
import { routes } from "./routes";

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  if (to.meta.requiresAuth && !store.getters["auth/loggedIn"]) {
    next({
      path: "/welcome",
      query: { redirect: to.fullPath },
    });
  } else {
    next();
  }
});

const DEFAULT_DESCRIPTION = "";
const APP_TITLE = "Basketball Simulator";

router.afterEach((to, from) => {
  nextTick(() => {
    const meta: any = to.meta;
    document.title = meta.title(to) + " — " + APP_TITLE;
    const desc = document.querySelector('head meta[name="description"]');
    const content: string = meta.description(to) || DEFAULT_DESCRIPTION;
    if (desc) desc.setAttribute("content", content);
  });
});

export default router;
