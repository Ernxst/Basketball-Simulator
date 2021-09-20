<template>
  <div class="search-sidebar">
    <p>Fill out search criteria</p>
    <player-info-card :search-info="searchInfo"></player-info-card>
    <stats-card :search-info="searchInfo"></stats-card>
    <flat-button
      class="search-btn"
      text="Search"
      v-on:click="search"
    ></flat-button>
  </div>
</template>

<script lang="ts">
  import FlatButton from "@/components/widgets/buttons/flat-button.vue";
  import PlayerInfoCard from "./sidebar-cards/player-info-card.vue";
  import StatsCard from "./sidebar-cards/stats-card.vue";

  import { defineComponent } from "vue";
  export default defineComponent({
    name: "search-sidebar",
    components: { StatsCard, PlayerInfoCard, FlatButton },
    data() {
      return {
        searchInfo: {
          "first-name": "",
          "last-name": "",
          position: "",
          "min-height": "",
          "max-height": "",
          "min-overall": 65,
          "max-overall": 99,
          team: "",
        },
      };
    },
    methods: {
      search() {},
    },
  });
</script>

<style scoped>
  .search-sidebar {
  }

  .search-sidebar > *:not(:last-child) {
    margin-bottom: 18px;
  }

  .search-sidebar > p {
    margin-top: 0;
    cursor: default;
  }

  .search-btn {
    width: 100%;
  }
</style>
