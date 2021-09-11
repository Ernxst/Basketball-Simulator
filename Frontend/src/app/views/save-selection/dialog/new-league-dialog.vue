<template>
    <modal-window ref="modal">
        <section class="new-league-dialog centred">
            <div class="header-container centred">
                <span class="tagline">Step {{ stage + 1 }} of 2</span>
                <h2>Create New League</h2>
                <p>{{ text }}</p>
            </div>
            <div class="form centred">
                <league-info :league-params="leagueParams" :class="leagueClass" @next="next"
                             ref="league-info" :min-teams="minNumOfTeams" :max-teams="maxNumOfTeams"></league-info>
                <team-info :league-params="leagueParams" :class="teamClass" @createLeague="submit">
                    <flat-button text="Back" class="back" v-on:click.prevent="back"></flat-button>
                </team-info>
                <flat-button text="Cancel" style="--button-bg: var(--flat-red)"
                             v-on:click.prevent="cancel"></flat-button>
            </div>
        </section>
    </modal-window>
</template>

<script>
import ConstantsService from "../../../../services/constants.service.ts";
import ModalWindow from "../../../components/dialogs/modal-window.vue";
import FlatButton from "../../../components/widgets/buttons/flat-button.vue";
import LeagueInfo from "./league-info.vue";
import TeamInfo from "./team-info.vue";


export default {
    name: "new-league-dialog",
    components: { TeamInfo, LeagueInfo, ModalWindow, FlatButton },
    computed: {
        leagueClass() {
            return this.stage === 1 ? "stage" : "stage active-tab";
        },
        teamClass() {
            return this.stage === 0 ? "stage" : "stage active-tab";
        },
        activeTab() {
            return this.stage === 0 ? this.$refs['league-info'] : this.$refs['team-info'];
        },
        text() {
            return this.stage === 0 ? "Fill out the details below to create a new league." :
                "Fill out the details below to create your team.";
        }
    },
    data() {
        return {
            stage: 0,
            minNumOfTeams: 0,
            maxNumOfTeams: 0,
            leagueParams: {
                league_name: "",
                start_date: null,
                num_of_teams: 0,
                team_state: "",
                team_name: "",
            },
        };
    },
    beforeCreate() {
        ConstantsService.minTeamsInLeague().then((minTeams) => {
            this.minNumOfTeams = minTeams;
            this.leagueParams["num_of_teams"] = minTeams;
        });
        ConstantsService.maxTeamsInLeague().then((maxTeams) => {
            this.maxNumOfTeams = maxTeams;
        });
    },
    methods: {
        back() {
            this.stage = 0;
        },
        next() {
            this.stage = 1;
        },
        submit() {
            console.log(this.leagueParams);
            this.reset();
            this.$refs.modal.confirm();
        },
        show() {
            this.reset();
            this.$refs.modal.show();
        },
        cancel() {
            this.reset();
            this.$refs.modal.cancel();
        },
        reset() {
            this.stage = 0;
            this.leagueParams = {
                league_name: "",
                start_date: null,
                num_of_teams: this.minNumOfTeams,
                team_state: "",
                team_name: "",
            };
            this.$refs['league-info'].reset();
        }
    },
};
</script>

<style>
.new-league-dialog {
    flex-direction: column;
    width: 25vw;
    height: fit-content;
    z-index: 10;
}

.new-league-dialog > *, .new-league-dialog .form > *,
.new-league-dialog .button-outer, .new-league-dialog .flat-button,
.new-league-dialog .param-container {
    width: 100%;
    flex-direction: column;
}

.new-league-dialog .header-container > * {
    text-align: left;
    width: 100%;
    margin-top: 0;
}

.new-league-dialog .header-container h2 {
    color: var(--black);
    margin-bottom: 12px;
}

.new-league-dialog .header-container p {
    color: var(--header-color);
}

.new-league-dialog .form .stage {
    display: none;
}

.new-league-dialog .form .stage.active-tab {
    display: flex;
}

.new-league-dialog .form .stage .text-input {
    margin-bottom: 12px;
}

.new-league-dialog .flat-button,
.new-league-dialog .text-input,
.new-league-dialog .autocomplete-text-input {
    width: 100%;
}

.new-league-dialog .button-outer {
    position: relative;
    height: fit-content;
}

.new-league-dialog .button-outer .tooltip {
    left: calc(50% + 12px);
    height: inherit;
}

.new-league-dialog .flat-button {
    margin-bottom: 8px;
}

.new-league-dialog .param-container {
    border: 2px solid transparent;
    border-radius: var(--card-radius);
    padding: 6px;
}

.new-league-dialog .param-container.highlighted {
    animation: blink .67s;
    animation-iteration-count: infinite;
}

@keyframes blink {
    50% {
        border-color: var(--flat-red);
    }
}

@media (max-width: 1200px) {
    .new-league-dialog {
        max-width: 97%;
        width: 100%;
        max-height: 95vh;
        height: 100%;
        overflow-y: scroll;
        justify-content: flex-start;
    }
}
</style>
