<template>
    <section class="team-info-form centred">
        <div class="container centred" ref="team-name-container">
            <autocomplete-input id="team-name-input" :object="leagueParams" :suggestions="teamNames"
                                icon="groups" key-name="team_name" label="Team Name" placeholder="Lakers"
                                type="text"></autocomplete-input>
        </div>
        <div class="container centred" ref="state-container">
            <autocomplete-input id="team-state-input" :object="leagueParams" :suggestions="teamStates"
                                icon="place" key-name="team_state" label="Team State"
                                placeholder="Los Angeles" type="text"></autocomplete-input>
        </div>
        <div class="button-outer centred" v-on:mouseenter="trigger"
             v-on:mouseleave="resetHover">
            <flat-button text="Create" :class="buttonClass" style="--button-bg: var(--flat-green)"
                         v-on:click.prevent="$emit('createLeague')">
            </flat-button>
            <tooltip ref="tooltip" :text="errorMessage" delay="300"></tooltip>
        </div>
        <slot></slot>
    </section>
</template>

<script>
import ConstantsService from "../../../../services/constants.service.ts";
import FlatButton from "../../../components/widgets/buttons/flat-button.vue";
import AutocompleteInput from "../../../components/widgets/text-input/autocomplete-input.vue";
import Tooltip from "../../../components/widgets/tooltip.vue";


export default {
    name: "team-info",
    components: { Tooltip, FlatButton, AutocompleteInput },
    props: {
        leagueParams: {}
    },
    computed: {
        buttonClass() {
            return this.validInputs ? "confirm" : "confirm disabled";
        },
        validInputs() {
            const teamName = this.leagueParams['team_name'];
            const teamState = this.leagueParams['team_state'];
            return this.teamNames.includes(teamName) && this.teamStates.includes(teamState);
        },
        errorMessage() {
            const teamName = this.leagueParams['team_name'];
            const teamState = this.leagueParams['team_state'];
            if (teamName.length < 3 || !this.teamNames.includes(teamName)) {
                return "Please select a team name from the dropdown.";
            } else if (!this.teamStates.includes(teamState)) {
                return "Please select a team state from the dropdown.";
            }
            this.resetHighlights();
            return "";
        }
    },
    data() {
        return {
            teamNames: [],
            teamStates: [],
        };
    },
    beforeCreate() {
        ConstantsService.teamNames().then((names) => {
            this.teamNames = names;
        });
        ConstantsService.teamStates().then((states) => {
            this.teamStates = states;
        });
    },
    methods: {
        highlight(elem) {
            this.resetHighlights();
            if (elem !== undefined)
                elem.classList.add("highlighted");
        },
        resetHighlights() {
            for (const [name, el] of Object.entries(this.$refs)) {
                if (name.includes("container"))
                    el.classList.remove('highlighted');
            }
        },
        trigger() {
            this.$refs.tooltip.enable();
            const teamName = this.leagueParams['team_name'];
            const teamState = this.leagueParams['team_state'];
            if (teamName.length < 3 || !this.teamNames.includes(teamName)) {
                this.highlight(this.$refs['team-name-container']);
            } else if (!this.teamStates.includes(teamState)) {
                this.highlight(this.$refs['state-container']);
            }
        },
        resetHover() {
            this.resetHighlights();
            if (this.$refs.tooltip !== null)
                this.$refs.tooltip.disable();
        }
    }
};
</script>

<style scoped>

</style>
