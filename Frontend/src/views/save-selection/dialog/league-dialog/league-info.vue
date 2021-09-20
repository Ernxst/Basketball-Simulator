<template>
  <section class="league-info-form centred">
    <div class="param-container centred" ref="nameContainer">
      <text-input
        id="league_name"
        ref="league_name"
        :no-spaces="false"
        autocomplete="off"
        icon="sports_basketball"
        label="League Name"
        placeholder="My League"
        type="text"
        v-model="name"
      ></text-input>
    </div>
    <div class="param-container centred" ref="dateContainer">
      <start-date-picker v-model="date" ref="datePicker"></start-date-picker>
    </div>
    <slider
      ref="slider"
      id="teams-slider"
      label="Number of Teams"
      :min="minTeams"
      :max="maxTeams"
      v-model.number="teams"
    ></slider>
    <div
      class="button-outer centred"
      @mouseenter="trigger"
      @mouseleave="resetHover"
    >
      <div class="flat-button-container centred">
        <flat-button
          text="Next"
          :class="buttonClass"
          style="--button-bg: var(--flat-green)"
          @click.prevent="$emit('next')"
        >
        </flat-button>
        <div class="inner-container">
          <tooltip ref="tooltip" :text="errorMessage" :delay="300"></tooltip>
        </div>
      </div>
    </div>
  </section>
</template>

<script lang="ts">
  import FlatButton from "@/components/widgets/buttons/flat-button.vue";
  import Slider from "@/components/widgets/slider.vue";
  import TextInput from "@/components/widgets/text-input/text-input.vue";
  import Tooltip from "@/components/widgets/tooltip.vue";
  import ConstantsService from "@/services/constants.service";
  import { computed, defineComponent, onBeforeMount, PropType, ref } from "vue";
  import StartDatePicker from "./start-date-picker.vue";

  export default defineComponent({
    name: "league-info",
    components: { Tooltip, StartDatePicker, Slider, FlatButton, TextInput },
    props: {
      minNumOfTeams: { type: Number, required: true, default: 0 },
      maxNumOfTeams: { type: Number, required: true, default: 50 },
      leagueName: { type: String, required: true, default: "" },
      startDate: {
        type: [Date, null] as PropType<Date | null>,
        default: null,
      },
      numOfTeams: { type: Number, required: true, default: 0 },
    },
    emits: [
      "update:minNumOfTeams",
      "update:maxNumOfTeams",
      "update:leagueName",
      "update:startDate",
      "update:numOfTeams",
      "next",
    ],
    setup(props, { emit }) {
      const minTeams = computed({
        get() {
          return props.minNumOfTeams;
        },
        set(value: number) {
          emit("update:minNumOfTeams", value);
        },
      });
      const maxTeams = computed({
        get() {
          return props.maxNumOfTeams;
        },
        set(value: number) {
          emit("update:maxNumOfTeams", value);
        },
      });
      const name = computed({
        get() {
          return props.leagueName;
        },
        set(value: string) {
          emit("update:leagueName", value);
        },
      });
      const date = computed({
        get() {
          return props.startDate;
        },
        set(value: Date | null) {
          emit("update:startDate", value);
        },
      });
      const teams = computed({
        get() {
          return props.numOfTeams;
        },
        set(value: number) {
          emit("update:numOfTeams", value);
        },
      });

      const setConstants = async () => {
        minTeams.value = await ConstantsService.minTeamsInLeague();
        maxTeams.value = await ConstantsService.maxTeamsInLeague();
        teams.value = minTeams.value;
      };
      onBeforeMount(setConstants);

      const buttonClass = computed(() => {
        return validInputs.value ? "next" : "next disabled";
      });
      const validName = computed(() => {
        return name.value.length >= 3;
      });
      const validDate = computed(() => {
        return date.value !== null;
      });
      const validInputs = computed(() => {
        return validName.value && validDate.value;
      });
      const errorMessage = computed(() => {
        if (!validName.value) {
          return "The league name must be at least 3 characters long.";
        } else if (!validDate.value) {
          return "Please select the date your league was founded.";
        }
        resetHighlights();
        return "";
      });

      type Elem = HTMLElement | null;
      const tooltip = ref<typeof Tooltip | null>(null);
      const slider = ref<typeof Slider | null>(null);
      const nameContainer = ref<Elem>(null);
      const dateContainer = ref<Elem>(null);
      const datePicker = ref<typeof StartDatePicker | null>(null);

      function highlight(elem: Elem) {
        resetHighlights();
        if (elem !== null) elem.classList.add("highlighted");
      }
      function resetHighlights() {
        nameContainer.value?.classList.remove("highlighted");
        dateContainer.value?.classList.remove("highlighted");
      }
      function trigger() {
        tooltip.value?.enable();
        if (!validName.value) {
          highlight(nameContainer.value);
        } else if (!validDate.value) {
          highlight(dateContainer.value);
        }
      }
      function resetHover() {
        resetHighlights();
        if (tooltip.value !== null) tooltip.value?.disable();
      }
      function reset() {
        name.value = "";
        date.value = null;
        teams.value = minTeams.value;
        datePicker.value?.reset();
      }
      return {
        minTeams,
        maxTeams,
        name,
        date,
        teams,
        buttonClass,
        errorMessage,
        nameContainer,
        dateContainer,
        tooltip,
        slider,
        datePicker,
        trigger,
        resetHover,
        reset,
      };
    },
  });
</script>

<style>
  .league-info-form .slider-container {
    margin-top: 4px;
    margin-bottom: 52px;
    padding-left: 8px;
    padding-right: 8px;
  }
</style>
