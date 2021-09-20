<template>
  <div class="date-picker centred noselect">
    <p class="header">
      Date Founded
      <tooltip text="The date the league was founded."></tooltip>
    </p>
    <flat-button class="button" :text="buttonText" @click="show"></flat-button>
    <div class="picker-container centred" v-show="visible">
      <DatePicker
        v-model="date"
        :attributes="attributes"
        ref="datePicker"
        color="green"
        :max-date="maxDate"
      ></DatePicker>
      <span class="close-icon material-icons" @click="hide">close</span>
    </div>
  </div>
</template>

<script lang="ts">
  import FlatButton from "@/components/widgets/buttons/flat-button.vue";
  import Tooltip from "@/components/widgets/tooltip.vue";
  import { Calendar, DatePicker } from "v-calendar";
  import { computed, defineComponent, PropType, ref } from "vue";

  export default defineComponent({
    name: "start-date-picker",
    components: { Tooltip, FlatButton, Calendar, DatePicker },
    props: {
      modelValue: {
        type: [Date, null] as PropType<Date | null>,
        default: null,
        required: true,
      },
    },
    emits: ["update:modelValue"],
    setup(props, { emit }) {
      const date = computed({
        get() {
          return props.modelValue;
        },
        set(value) {
          emit("update:modelValue", value);
          hide();
        },
      });
      const maxDate = computed(() => {
        // TODO: Return current date if it is before 29th Oct of that year
        const currentDate = Date.now();
        return currentDate;
      });
      const buttonText = computed(() => {
        if (props.modelValue != null) return props.modelValue.toDateString();
        return "No Date Selected";
      });

      const visible = ref(false);
      const attributes = ref([
        {
          highlight: true,
          dot: true,
          bar: true,
          content: "green",
          dates: new Date(),
        },
      ]);
      type DatePickerComponent = typeof DatePicker & { move: Function };
      const datePicker = ref<DatePickerComponent | null>(null);

      function show() {
        visible.value = true;
      }
      function hide() {
        visible.value = false;
      }
      function reset() {
        datePicker.value?.move(new Date());
      }

      return {
        buttonText,
        attributes,
        visible,
        maxDate,
        date,
        show,
        hide,
        reset,
      };
    },
  });
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
    font-size: 16px;
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
