<template>
  <div class="page full-page">
    <sidebar>
      <slot name="sidebarView"></slot>
    </sidebar>
    <main class="view">
      <slot></slot>
    </main>
    <page-footer></page-footer>
  </div>
</template>

<script lang="ts">
  import Sidebar from "@/sidebar/Sidebar.vue";
  import PageFooter from "./PageFooter.vue";

  import { defineComponent } from "vue";
  export default defineComponent({
    name: "Page",
    components: { Sidebar, PageFooter },
  });
</script>

<style>
  .page {
    display: inline-flex;
    justify-content: flex-start;
  }

  .page > .view {
    width: 100%;
    min-height: 100%;
    max-height: 100%;
    padding: 24px 48px;
    overflow-y: scroll;
    height: 100vh;
  }
</style>
