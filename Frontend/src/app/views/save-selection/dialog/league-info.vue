<template>
    <section class="league-info-form centred">
        <div class="container centred" ref="name-container">
            <text-input id="league_name" ref="league_name" :no-spaces="false"
                        :object="leagueParams" autocomplete="off"
                        icon="sports_basketball" key-name="league_name" label="League Name"
                        placeholder="My League" type="text"></text-input>
        </div>
        <div class="container centred" ref="date-container">
            <start-date-picker :league-params="leagueParams" ref="date-picker"></start-date-picker>
        </div>
        <slider ref="slider" :min="minNumOfTeams" :max="maxNumOfTeams" label="Number of Teams"
                id="teams-slider" :start="minNumOfTeams" :object="leagueParams" attribute="num_of_teams"></slider>
        <div class="button-outer centred" v-on:mouseenter="trigger"
             v-on:mouseleave="resetHover">
            <flat-button text="Next" :class="buttonClass" style="--button-bg: var(--flat-green)"
                         v-on:click.prevent="$emit('next')">
            </flat-button>
            <tooltip ref="tooltip" :text="errorMessage" :delay="300"></tooltip>
        </div>
    </section>
</template>

<script>
import ConstantsService from "../../../../services/constants.service.ts";
import FlatButton from "../../../components/widgets/buttons/flat-button.vue";
import Slider from "../../../components/widgets/slider.vue";
import TextInput from "../../../components/widgets/text-input/text-input.vue";
import Tooltip from "../../../components/widgets/tooltip.vue";
import StartDatePicker from "./start-date-picker.vue";


export default {
    name: "league-info",
    components: { Tooltip, StartDatePicker, Slider, FlatButton, TextInput },
    props: {
        leagueParams: {}
    },
    data() {
        return {
            minNumOfTeams: 0,
            maxNumOfTeams: 0,
        };
    },
    computed: {
        buttonClass() {
            return this.validInputs ? "next" : "next disabled";
        },
        validInputs() {
            const name = this.leagueParams['league_name'];
            const date = this.leagueParams['start_date'];
            return name.length >= 3 && date !== null;
        },
        errorMessage() {
            if (this.leagueParams['league_name'].length < 3) {
                return "The league name must be at least 3 characters long.";
            } else if (this.leagueParams['start_date'] === null) {
                return "Please select the date your league was founded.";
            }
            this.resetHighlights();
            return "";
        }
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
            if (this.leagueParams['league_name'].length < 3) {
                this.highlight(this.$refs['name-container']);
            } else if (this.leagueParams['start_date'] === null) {
                this.highlight(this.$refs['date-container']);
            }
        },
        resetHover() {
            this.resetHighlights();
            if (this.$refs.tooltip !== null)
                this.$refs.tooltip.disable();
        },
        reset() {
            this.$refs['date-picker'].reset();
        }
    }
};
</script>

<style>
.league-info-form .slider-container {
    margin-top: 4px;
    margin-bottom: 52px;
    padding-left: 8px;
    padding-right: 8px;
}
</style>
