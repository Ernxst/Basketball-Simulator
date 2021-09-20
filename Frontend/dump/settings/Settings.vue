<template>
  <page>
    <template v-slot:sidebarView>
      <settings-sidebar></settings-sidebar>
    </template>
    <h1>Settings</h1>
  </page>
</template>

<script lang="ts">
  import Page from "@/components/layout/Page.vue";
  import SettingsSidebar from "./sidebar/settings-sidebar.vue";

  import { defineComponent } from "vue";
  export default defineComponent({
    name: "Settings",
    components: { SettingsSidebar, Page },
  });
</script>

<style scoped>
</style>
