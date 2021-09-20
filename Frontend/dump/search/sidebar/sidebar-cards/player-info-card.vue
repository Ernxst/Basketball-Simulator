<template>
  <div class="player-info-card centred sidebar-card">
    <autocomplete-input
      id="first-name-input"
      :object="searchInfo"
      :suggestions="allFirstNames"
      icon="person"
      key-name="first-name"
      label="First Name"
      placeholder="Kyrie"
      type="text"
    ></autocomplete-input>
    <autocomplete-input
      id="last-name-input"
      :object="searchInfo"
      :suggestions="allLastNames"
      icon="person"
      key-name="last-name"
      label="Last Name"
      placeholder="Irving"
      type="text"
    ></autocomplete-input>
  </div>
</template>

<script lang="ts">
  import AutocompleteInput from "@/components/widgets/text-input/autocomplete-input.vue";

  import { defineComponent } from "vue";
  export default defineComponent({
    name: "player-info-card",
    components: { AutocompleteInput },
    props: {
      searchInfo: Object,
    },
    computed: {
      allFirstNames() {
        return [
          "Kyrie",
          "LeBron",
          "Kevin",
          "Paul",
          "Kawhi",
          "Chris",
          "Devin",
          "Jayson",
          "Jaylen",
          "Nikola",
          "Luka",
          "Jamal",
          "Anthony",
          "Ben",
          "Joel",
          "Stephen",
          "Damian",
        ];
      },
      allLastNames() {
        return [
          "Irving",
          "James",
          "Durant",
          "George",
          "Leonard",
          "Paul",
          "Booker",
          "Tatum",
          "Brown",
          "Jokic",
          "Doncic",
          "Murray",
          "Davis",
          "Simmons",
          "Embiid",
          "Curry",
          "Lillard",
        ];
      },
    },
  });
</script>

<style>
  .search-sidebar .player-info-card {
    flex-direction: column;
    background: var(--pale-blue);
  }

  .search-sidebar .player-info-card > *:not(:last-child) {
    margin-bottom: 16px;
  }

  .search-sidebar .player-info-card .text-input label {
    color: #fff;
  }
</style>
