<template>
  <main class="select-save full-page noselect">
    <section class="header-section">
      <h1 class="title">Select League</h1>
      <p class="subtitle">
        Welcome back <b>{{ $route.params.username }}</b
        >. {{ labelText() }}
      </p>
    </section>
    <section class="league-container">
      <league-card
        v-for="league in leagues"
        :leagueID="league['league_id']"
        :current-date="league['current_date']"
        :current-season="league['current_season']"
        :last-played="league['last_played']"
        :league-name="league['league_name']"
        :losses="league['losses']"
        :team-state="league['team_state']"
        :icon_id="league['icon_id']"
        :team-name="league['team_name']"
        :wins="league['wins']"
        @newLeague="newLeague"
        @show="newLeague"
        v-bind:key="league"
      >
      </league-card>
    </section>
    <flat-button
      class="logout-btn"
      text="Log out"
      @click="logout"
    ></flat-button>
  </main>
  <new-league-dialog ref="dialog"></new-league-dialog>
</template>

<script lang="ts">
  import ConstantsService from "@/services/constants.service";
  import FlatButton from "@/components/widgets/buttons/flat-button.vue";
  import NewLeagueDialog from "./dialog/new-league-dialog.vue";
  import LeagueCard from "./league-card.vue";

  import { defineComponent } from "vue";

  export default defineComponent({
    name: "SelectSave",
    components: { NewLeagueDialog, LeagueCard, FlatButton },
    data() {
      return {
        leagues: [],
        maxLeagues: 0,
      };
    },
    computed: {
      numOfLeagues() {
        let count = 0;
        for (const league of this.leagues) {
          if (league["league_id"] !== undefined) {
            count += 1;
          }
        }
        return count;
      },
    },
    beforeCreate() {
      ConstantsService.maxTeamsInLeague().then((maxLeagues) => {
        this.maxLeagues = maxLeagues;
      });
      this.$store.dispatch("league/fetchLeagueSaves").then(() => {
        this.leagues = this.$store.getters["league/leagueSaves"];
      });
    },
    methods: {
      newLeague() {
        this.$refs.dialog.show();
      },
      labelText() {
        if (this.numOfLeagues === 0)
          return "You have no existing leagues, click a save slot below to create a new league.";
        const base = "Select a league to continue with below. ";
        if (this.numOfLeagues === this.maxLeagues)
          return (
            base +
            `You have reached the maximum number of leagues (${this.maxLeagues}), you will have to delete an existing save to create a new league.`
          );
        return base + " Click an empty save slot to create a new league.";
      },
      logout() {
        this.$store.dispatch("auth/logout").then((_) => {
          this.$router.go();
        });
      },
    },
  });
</script>

<style scoped>
  .select-save {
    padding: 32px 24px;
    overflow-y: auto;
    max-height: 100vh;
    align-items: flex-start;
  }

  .header-section,
  .league-container {
    width: 100%;
  }

  .header-section .title {
    padding-bottom: 12px;
    margin-bottom: 12px;
    border-bottom: 2px solid #fff;
    font-size: 42px;
    text-transform: uppercase;
    width: 100%;
    margin-top: 0;
  }

  .header-section .subtitle {
    margin: 0;
  }

  .league-container {
    display: grid;
    grid-row-gap: 32px;
    grid-template-rows: 1fr 1fr 1fr;
    padding-top: 32px;
    padding-bottom: 32px;
  }

  .logout-btn {
    padding: 1px 28px;
    width: 100%;
    margin: 0;
  }

  @media (min-width: 1200px) {
    .select-save {
      padding: 48px 96px;
    }

    .logout-btn {
      margin-left: auto;
      width: unset;
    }

    .league-container {
      grid-column-gap: 32px;
      grid-template-columns: 1fr 1fr 1fr;
      grid-template-rows: unset;
      grid-row-gap: unset;
      height: -webkit-fill-available;
    }
  }
</style>
