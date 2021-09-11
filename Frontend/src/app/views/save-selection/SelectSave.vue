<template>
    <main class="select-save full-page noselect">
        <section class="header-section">
            <h1 class="title">Select League</h1>
            <p class="subtitle">Welcome back <b>{{ $route.params.username }}</b>. {{ labelText() }}</p>
        </section>
        <section class="league-container">
            <league-card v-for="league in leagues" :current-date="league['current-date']"
                         :current-season="league['current-season']" :last-played="league['last-played']"
                         :league-name="league['league-name']" :losses="league['losses']"
                         :team-city="league['team-city']" :team-icon="league['team-icon']"
                         :team-name="league['team-name']" :wins="league['wins']"
                         @newLeague="newLeague">
            </league-card>
        </section>
        <flat-button class="logout-btn" text="Log out" v-on:click="logout"></flat-button>
    </main>
    <new-league-dialog ref="dialog"></new-league-dialog>
</template>

<script>
import ConstantsService from "../../../services/constants.service.ts";
import FlatButton from "../../components/widgets/buttons/flat-button.vue";
import NewLeagueDialog from "./dialog/new-league-dialog.vue";
import LeagueCard from "./league-card.vue";


export default {
    name: "SelectSave",
    components: { NewLeagueDialog, LeagueCard, FlatButton },
    computed: {
        storedLeagues() {
            return [{
                "league-name": "MyLeague",
                "team-city": "Los Angeles",
                "team-name": "Lakers",
                "team-icon": "",
                "current-date": Date.now(),
                "current-season": 2,
                "last-played": Date.now(),
                "wins": 21,
                "losses": 20
            }];
        },
        leagues() {
            return this.storedLeagues.concat([{}, {}]);
        }
    },
    beforeCreate() {
        ConstantsService.maxTeamsInLeague().then((maxLeagues) => {
            this.maxLeagues = maxLeagues;
        });
    },
    data() {
        return {
            maxLeagues: 0,
        };
    },
    methods: {
        newLeague() {
            this.$refs.dialog.show();
        },
        labelText() {
            if (this.storedLeagues.length === 0)
                return "You have no existing leagues, click a save slot below to create a new league.";
            const base = "Select a league to continue with below. ";
            if (this.storedLeagues.length === this.maxLeagues)
                return base + `You have reached the maximum number of leagues (${this.maxLeagues}), you will have to delete an existing save to create a new league.`;
            return base + " Click an empty save slot to create a new league.";
        },
        logout() {
            this.$store.dispatch("auth/logout").then(_ => {
                this.$router.go();
            });
        }
    }
};
</script>

<style scoped>
.select-save {
    padding: 32px 24px;
    overflow-y: auto;
    max-height: 100vh;
}

.header-section, .league-container {
    width: 100%;
}

.header-section .title {
    padding-bottom: 12px;
    margin-bottom: 12px;
    border-bottom: 2px solid #FFF;
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
    margin-left: auto;
}

@media (min-width: 1200px) {
    .select-save {
        padding: 48px 96px;
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
