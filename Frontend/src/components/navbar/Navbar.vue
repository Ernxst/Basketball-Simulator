<template>
  <nav class="navbar centred" role="navigation">
    <ul class="nav-buttons centred">
      <nav-item
        v-for="page in pages"
        :ref="page.name"
        :icon="page.icon"
        :page="page.name"
        :tooltip="page.tooltip"
      ></nav-item>
    </ul>
    <div class="spacer"></div>
    <ul class="help-buttons centred">
      <help-item icon="apps" page="/" tooltip="View other saves."></help-item>
      <help-item icon="help" page="/" tooltip="Help"></help-item>
      <help-item
        icon="logout"
        page="/"
        tooltip="Sign out and return to the main menu."
      ></help-item>
    </ul>
  </nav>
</template>

<script lang="ts">
  import HelpItem from "./help-item.vue";
  import NavItem from "./nav-item.vue";

  import { defineComponent } from "vue";
  export default defineComponent({
    name: "navbar",
    components: { HelpItem, NavItem },
    computed: {
      pageNames() {
        return this.pages.map((a) => a.name);
      },
      pages() {
        return [
          {
            name: "play",
            tooltip: "",
            icon: "sports_basketball",
          },
          {
            name: "team",
            tooltip: "Manage your team.",
            icon: "groups",
          },
          {
            name: "insights",
            tooltip: "View the rest of the league.",
            icon: "leaderboard",
          },
          {
            name: "search",
            tooltip: "Search the league for players.",
            icon: "search",
          },
          {
            name: "settings",
            tooltip: "Manage game settings.",
            icon: "settings",
          },
        ];
      },
    },
    methods: {
      toggle() {
        for (const page of this.pageNames) {
          if (this.$route.name.includes(page)) {
            this.$refs[page].enable();
          } else {
            this.$refs[page].disable();
          }
        }
      },
    },
    mounted() {
      this.toggle();
    },
  });
</script>

<style scoped>
  .navbar {
    width: 100%;
    max-width: var(--navbar-width);
    min-height: 100%;
    background: var(--dark-blue);
    flex-direction: column;
  }

  .nav-buttons,
  .help-buttons {
    margin-top: 0;
    padding: 16px;
    flex-direction: column;
    width: 100%;
  }

  .spacer {
    flex-grow: 1;
  }

  .help-buttons {
    margin-bottom: 24px;
  }
</style>
