<template>
    <page>
        <template v-slot:sidebarView>
            <home-sidebar></home-sidebar>
        </template>

        <h1>Play</h1>
        <p>Vue.js 3 & Webpack Boilerplate.</p>
        <sim-calendar></sim-calendar>
    </page>
</template>

<script>
import Page from "../../components/layout/Page.vue";
import SimCalendar from "./calendar/sim-calendar.vue";
import HomeSidebar from "./sidebar/home-sidebar.vue";


export default {
    name: "Home",
    components: { SimCalendar, HomeSidebar, Page },
};
</script>

<style scoped>
</style>