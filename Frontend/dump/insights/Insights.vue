<template>
  <page>
    <template v-slot:sidebarView>
      <insights-sidebar></insights-sidebar>
    </template>
    <h1>Insights</h1>
  </page>
</template>

<script lang="ts">
  import Page from "@/components/layout/Page.vue";
  import InsightsSidebar from "./sidebar/insights-sidebar.vue";

  import { defineComponent } from "vue";
  export default defineComponent({
    name: "Insights",
    components: { InsightsSidebar, Page },
  });
</script>

<style scoped>
</style>
