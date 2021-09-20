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

<script lang="ts">
  import Page from "@/components/layout/Page.vue";
  import SimCalendar from "./calendar/sim-calendar.vue";
  import HomeSidebar from "./sidebar/home-sidebar.vue";

  import { defineComponent } from "vue";
  export default defineComponent({
    name: "Home",
    components: { SimCalendar, HomeSidebar, Page },
  });
</script>

<style scoped>
</style>
