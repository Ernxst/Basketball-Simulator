<template>
  <modal-window ref="modal" v-show="visible">
    <section class="new-league-dialog centred">
      <div class="header-container centred">
        <span class="tagline">Step {{ stage + 1 }} of 2</span>
        <h2>Create New League</h2>
        <p>{{ text }}</p>
      </div>
      <div class="form centred">
        <league-info
          :class="leagueClass"
          @next="stage = 1"
          ref="leagueDialog"
          v-model:min-num-of-teams.number="minNumOfTeams"
          v-model:max-num-of-teams.number="maxNumOfTeams"
          v-model:league-name="leagueName"
          v-model:num-of-teams.number="numOfTeams"
          v-model:start-date="startDate"
        ></league-info>
        <team-info
          :class="teamClass"
          ref="teamDialog"
          @createLeague="submit"
          v-model:team-name="teamName"
          v-model:team-state="teamState"
        >
          <flat-button
            text="Back"
            class="back"
            @click.prevent="stage = 0"
          ></flat-button>
        </team-info>
        <flat-button
          text="Cancel"
          style="--button-bg: var(--flat-red)"
          @click.prevent="hide"
        ></flat-button>
      </div>
    </section>
  </modal-window>
</template>

<script lang="ts">
  import ModalWindow from "@/components/dialogs/modal-window.vue";
  import FlatButton from "@/components/widgets/buttons/flat-button.vue";
  import { useStore } from "@/store/store";
  import { computed, defineComponent, ref } from "vue";
  import { useRouter } from "vue-router";
  import LeagueInfo from "./league-dialog/league-info.vue";
  import TeamInfo from "./team-dialog/team-info.vue";

  export default defineComponent({
    name: "new-league-dialog",
    components: { TeamInfo, LeagueInfo, ModalWindow, FlatButton },
    emits: ["show", "close", "cancel", "confirm"],
    setup(props, { emit }) {
      const router = useRouter();
      const store = useStore();

      const stage = ref(0);
      const visible = ref(false);
      const minNumOfTeams = ref<number>(0);
      const maxNumOfTeams = ref<number>(50);

      const leagueName = ref<string>("");
      const startDate = ref<Date | null>(null);
      const numOfTeams = ref<number>(0);
      const teamState = ref<string>("");
      const teamName = ref<string>("");

      type LeagueDialog = typeof LeagueInfo & { reset: Function };
      const leagueDialog = ref<LeagueDialog | null>(null);
      const teamDialog = ref<typeof TeamInfo | null>(null);

      const leagueClass = computed(() => {
        return stage.value === 1 ? "stage" : "stage active-tab";
      });
      const teamClass = computed(() => {
        return stage.value === 0 ? "stage" : "stage active-tab";
      });
      const activeTab = computed(() => {
        return stage.value === 0 ? leagueDialog : teamDialog;
      });
      const text = computed(() => {
        return stage.value === 0
          ? "Fill out the details below to create a new league."
          : "Fill out the details below to create your team.";
      });

      function submit() {
        const params = {
          league_name: leagueName.value,
          start_date: startDate.value,
          num_of_teams: numOfTeams.value,
          team_name: teamName.value,
          team_state: teamState.value,
        };
        console.log({ params });
        store.dispatch("league/newLeague", params).then(() => {
          hide();
          router.push({
            name: "play",
            params: {
              league_id: store.getters["league/currentLeagueID"],
            },
          });
        });
      }
      function show() {
        ("nld show");
        reset();
        visible.value = true;
        emit("show");
      }
      function hide() {
        reset();
        visible.value = false;
        emit("cancel");
      }
      function reset() {
        stage.value = 0;
        leagueDialog.value?.reset();
        teamDialog.value?.reset();
      }

      return {
        stage,
        minNumOfTeams,
        maxNumOfTeams,
        leagueName,
        startDate,
        numOfTeams,
        teamState,
        teamName,
        leagueClass,
        teamClass,
        activeTab,
        leagueDialog,
        teamDialog,
        text,
        visible,
        submit,
        show,
        hide,
        reset,
      };
    },
  });
</script>

<style>
  .new-league-dialog {
    flex-direction: column;
    width: 25vw;
    height: fit-content;
    z-index: 10;
  }

  .new-league-dialog > *,
  .new-league-dialog .form > *,
  .new-league-dialog .button-outer,
  .new-league-dialog .flat-button,
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

  .new-league-dialog .flat-button-container,
  .new-league-dialog .flat-button,
  .new-league-dialog .text-input,
  .new-league-dialog .autocomplete-text-input {
    width: 100%;
  }

  .new-league-dialog .flat-button {
    margin-bottom: 8px;
  }

  .new-league-dialog .button-outer {
    height: fit-content;
  }

  .new-league-dialog .button-outer .flat-button-container {
    position: relative;
  }

  .new-league-dialog .button-outer .flat-button-container .inner-container {
    position: absolute;
    top: 0;
    width: fit-content;
    height: 100%;
    left: calc(100% + 12px);
  }

  .new-league-dialog
    .button-outer
    .flat-button-container
    .inner-container
    .tooltip {
    width: 100%;
    position: relative;
    margin: 0;
    height: 100%;
    left: 0;
  }

  .new-league-dialog
    .button-outer
    .flat-button
    .inner-container
    .tooltip-active {
    left: 0;
    margin: 0;
  }

  .new-league-dialog .param-container {
    margin-bottom: 12px;
    border: 2px solid transparent;
    border-radius: var(--card-radius);
    padding: 6px;
  }

  .new-league-dialog .param-container.highlighted {
    animation: blink 0.67s;
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
