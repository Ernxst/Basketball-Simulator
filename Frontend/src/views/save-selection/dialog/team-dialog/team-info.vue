<template>
  <section class="team-info-form centred">
    <div class="param-container centred" ref="nameContainer">
      <autocomplete-input
        id="team-name-input"
        :suggestions="teamNames"
        icon="groups"
        label="Team Name"
        placeholder="Lakers"
        type="text"
        v-model="name"
      ></autocomplete-input>
    </div>
    <div class="param-container centred" ref="stateContainer">
      <autocomplete-input
        id="team-state-input"
        :suggestions="teamStates"
        v-model="state"
        icon="place"
        key-name="team_state"
        label="Team State"
        placeholder="Los Angeles"
        type="text"
      ></autocomplete-input>
    </div>
    <div
      class="button-outer centred"
      @mouseenter="trigger"
      @mouseleave="resetHover"
    >
      <div class="flat-button-container centred">
        <flat-button
          text="Create"
          :class="buttonClass"
          style="--button-bg: var(--flat-green)"
          @click.prevent="$emit('createLeague')"
        >
        </flat-button>
        <div class="inner-container">
          <tooltip ref="tooltip" :text="errorMessage" :delay="300"></tooltip>
        </div>
      </div>
    </div>
    <slot></slot>
  </section>
</template>

<script lang="ts">
  import ConstantsService from "@/services/constants.service";
  import FlatButton from "@/components/widgets/buttons/flat-button.vue";
  import AutocompleteInput from "@/components/widgets/text-input/autocomplete-input.vue";
  import Tooltip from "@/components/widgets/tooltip.vue";

  import { computed, defineComponent, onBeforeMount, ref } from "vue";
  export default defineComponent({
    name: "team-info",
    components: { Tooltip, FlatButton, AutocompleteInput },
    props: {
      teamName: { type: String, required: true, default: "" },
      teamState: { type: String, required: true, default: "" },
    },
    emits: ["createLeague", "update:teamName", "update:teamState"],
    setup(props, { emit }) {
      const teamNames = ref<string[]>([]);
      const teamStates = ref<string[]>([]);
      const name = computed({
        get() {
          return props.teamName;
        },
        set(value: string) {
          emit("update:teamName", value);
        },
      });

      const state = computed({
        get() {
          return props.teamState;
        },
        set(value: string) {
          emit("update:teamState", value);
        },
      });

      const setConstants = async () => {
        teamNames.value = await ConstantsService.teamNames();
        teamStates.value = await ConstantsService.teamStates();
      };
      onBeforeMount(setConstants);

      const buttonClass = computed(() => {
        return validInputs.value ? "confirm" : "confirm disabled";
      });
      const validName = computed(() => {
        return name.value.length >= 3 && teamNames.value.includes(name.value);
      });
      const validTeamState = computed(() => {
        return teamStates.value.includes(state.value);
      });
      const validInputs = computed(() => {
        return validName.value && validTeamState.value;
      });
      const errorMessage = computed(() => {
        if (!validName.value) {
          return "Please select a team name from the dropdown.";
        } else if (!validTeamState.value) {
          return "Please select a team state from the dropdown.";
        }
        resetHighlights();
        return "";
      });

      type Elem = HTMLElement | null;
      const tooltip = ref<typeof Tooltip | null>(null);
      const nameContainer = ref<Elem>(null);
      const stateContainer = ref<Elem>(null);

      function highlight(elem: Elem) {
        resetHighlights();
        if (elem !== null) elem.classList.add("highlighted");
      }
      function resetHighlights() {
        nameContainer.value?.classList.remove("highlighted");
        stateContainer.value?.classList.remove("highlighted");
      }
      function trigger() {
        tooltip.value?.enable();
        if (!validName.value) {
          highlight(nameContainer.value);
        } else if (!validTeamState.value) {
          highlight(stateContainer.value);
        }
      }
      function resetHover() {
        resetHighlights();
        if (tooltip.value !== null) tooltip.value?.disable();
      }
      function reset() {
        state.value = "";
        name.value = "";
      }
      return {
        teamNames,
        teamStates,
        name,
        state,
        buttonClass,
        errorMessage,
        nameContainer,
        stateContainer,
        tooltip,
        trigger,
        resetHover,
        reset,
      };
    },
  });
</script>

<style scoped></style>
