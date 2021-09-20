<template>
  <div class="home-sidebar">
    <next-game-card></next-game-card>
  </div>
</template>

<script lang="ts">
  import NextGameCard from "./sidebar-cards/next-game-card.vue";

  import { defineComponent } from "vue";
  export default defineComponent({
    name: "home-sidebar",
    components: { NextGameCard },
  });
</script>

<style scoped>
  .home-sidebar {
  }
</style>
