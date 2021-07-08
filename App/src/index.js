import { createApp } from 'vue';
import App from "./app/App.vue";

import './assets/main.css';
import router from "./router/router.js";
import { store } from "./store/store.js";


const app = createApp(App);
app.use(router);
app.use(store);
app.mount("#app");