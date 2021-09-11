<template>
    <div class="date-picker centred noselect">
        <p class="header">Date Founded
            <tooltip text="The date the league was founded."></tooltip>
        </p>
        <flat-button class="button" :text="buttonText" @click="toggle"></flat-button>
        <div class="picker-container centred" v-show="visible">
            <DatePicker v-model="leagueParams['start_date']" :attributes="attributes"
                        color="green" @input="hide" :max-date="Date.now()"></DatePicker>
            <span class="close-icon material-icons" v-on:click="hide">close</span>
        </div>
    </div>
</template>

<script>
import { Calendar, DatePicker } from "v-calendar";
import FlatButton from "../../../components/widgets/buttons/flat-button.vue";
import Tooltip from "../../../components/widgets/tooltip.vue";


export default {
    name: "start-date-picker",
    components: { Tooltip, FlatButton, Calendar, DatePicker },
    props: {
        leagueParams: {}
    },
    watch: {
        leagueParams: {
            handler(newVal, oldVal) {
                this.hide();
            },
            deep: true,
            immediate: true
        }
    },
    computed: {
        buttonText() {
            const selectedDate = this.leagueParams['start_date'];
            if (selectedDate)
                return selectedDate.toDateString();
            return "No Date Selected";
        }
    },
    data() {
        return {
            visible: false,
            attributes: [
                {
                    highlight: true, dot: true, bar: true,
                    content: 'green', dates: new Date(),
                    maxDate: Date.now()
                }
            ]
        };
    },
    methods: {
        toggle() {
            this.visible = !this.visible;
        },
        show() {
            this.visible = true;
        },
        hide() {
            this.visible = false;
        }
    }
};
</script>

<style scoped>
.date-picker {
    flex-direction: column;
    width: 100%;
    position: relative;
}

.header {
    text-align: left;
    margin-top: 0;
    margin-bottom: 8px;
    line-height: 90%;
    font-size: 14px;
    color: #555;
    width: 100%;
}

.button {
    margin-bottom: 12px;
}

.picker-container {
    position: absolute;
    z-index: 41;
}

.close-icon {
    background: var(--flat-red);
    color: #000;
    border-radius: 50%;
    position: absolute;
    bottom: 12px;
    right: 16px;
    padding: 2px;
    z-index: 42;
    cursor: pointer;
}

.close-icon:hover {
    filter: brightness(70%);
}
</style>
