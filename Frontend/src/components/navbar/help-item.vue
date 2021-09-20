<template>
  <nav-item :icon="icon" :page="page" :tooltip="tooltip"></nav-item>
</template>

<script lang="ts">
  import NavItem from "./nav-item.vue";

  import { defineComponent } from "vue";
  export default defineComponent({
    name: "help-item",
    components: { NavItem },
    props: {
      icon: String,
      tooltip: String,
      page: String,
    },
  });
</script>

<style>
  .help-buttons .nav-container {
    margin-bottom: 4px;
  }

  .help-buttons .nav-item {
    font-size: 11px;
    padding: 4px;
  }
</style>
