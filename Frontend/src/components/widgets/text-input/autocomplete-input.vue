<template>
  <div class="autocomplete-text-input">
    <text-input
      :id="id"
      ref="input"
      :disallowed-keys="disallowedKeys"
      :icon="icon"
      :label="label"
      :maxlength="maxlength"
      :name="`${id}-autocomplete`"
      :no-spaces="noSpaces"
      :on-enter="onSubmit"
      :placeholder="placeholder"
      :required="required"
      :type="type"
      autocomplete="off"
      v-model="value"
      @keyup.down="down"
      @keyup.up="up"
    ></text-input>
    <ul v-show="matches.length > 0" ref="listbox" class="listbox centred">
      <li
        :key="index"
        v-for="(suggestion, index) in matches"
        class="listbox-item"
        v-bind:class="{ 'active-suggestion': isActive(index) }"
        @click="suggestionClick(index)"
        @keyup.down="down"
        @keyup.up="up"
      >
        {{ suggestion }}
      </li>
    </ul>
  </div>
</template>

<script lang="ts">
  import { defineComponent } from "vue";
  import { computed, ref } from "vue";
  import TextInput from "./text-input.vue";

  export default defineComponent({
    name: "autocomplete-input",
    components: { TextInput },
    props: {
      suggestions: {
        type: Object as () => string[],
        required: true,
      },
      id: {
        type: String,
        required: true,
      },
      type: {
        type: String,
        default: "text",
      },
      icon: String,
      maxlength: {
        type: Number,
        default: 32,
      },
      placeholder: String,
      label: { type: String, default: "" },
      noSpaces: { type: Boolean, default: false },
      onEnter: {
        type: Function,
        default: () => {},
      },
      disallowedKeys: {
        type: Object as () => string[],
        default: [
          ";",
          "[",
          "]",
          "{",
          "}",
          '"',
          "£",
          "$",
          "%",
          "^",
          "&",
          "*",
          "_",
          "+",
          "/",
          "=",
          "|",
          "\\",
          "~",
          "`",
          ":",
          "!",
          "±",
          "§",
        ],
      },
      modelValue: { type: String, default: "" },
      autocomplete: { type: String, default: "off" },
      required: { type: Boolean, default: false },
    },
    emits: ["update:modelValue"],
    setup(props, { emit }) {
      const value = computed({
        get() {
          return props.modelValue;
        },
        set(newValue: string) {
          emit("update:modelValue", newValue);
        },
      });
      const input = ref<typeof TextInput | null>(null);

      const matches = computed(() => {
        const input = value.value.toLowerCase();
        if (input.length === 0) return [];
        const matches = props.suggestions.filter((str: string) => {
          const suggestion = str.toLowerCase();
          return suggestion.startsWith(input) && suggestion !== input;
        });
        matches.sort();
        current.value = 0;
        return matches;
      });
      const current = ref(0);
      const open = ref(false);

      function update(_: Event) {
        emit("update:modelValue", value.value);
      }

      function up() {
        current.value--;
      }
      function down() {
        current.value++;
      }
      function isActive(index: number) {
        return index === current.value;
      }
      function suggestionClick(index: number) {
        const computedMatches = matches.value;
        value.value = computedMatches[index % computedMatches.length];
        reset();
      }
      function focus() {
        input.value?.focus();
      }
      function reset() {
        open.value = false;
        current.value = 0;
      }
      function toggleDelete() {
        input.value?.toggleDelete();
      }
      function clearInput() {
        input.value?.clearInput();
      }
      function onSubmit() {
        const computedMatches = matches.value;
        value.value = computedMatches[current.value % computedMatches.length];
        reset();
        props.onEnter();
      }
      return {
        value,
        matches,
        update,
        isActive,
        up,
        down,
        suggestionClick,
        onSubmit,
        clearInput,
        toggleDelete,
        focus,
      };
    },
  });
</script>

<style>
  .autocomplete-text-input {
    position: relative;
  }

  .autocomplete-text-input .listbox {
    top: calc(100% + 4px);
    position: absolute;
    width: 100%;
    left: 0;
    right: 0;
    padding: 6px;
    border-radius: var(--input-radius);
    margin: 0;
    flex-direction: column;
    background: #fff;
    z-index: 9;
    box-shadow: 0 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23);
  }

  .autocomplete-text-input .listbox-item {
    width: 100%;
    padding: 6px;
    color: #000;
    text-align: left;
    border-radius: var(--input-radius);
    justify-content: flex-start;
    cursor: pointer;
    list-style: none;
    margin: 1px;
  }

  @media (prefers-color-scheme: dark) {
    .autocomplete-text-input .listbox {
      background-color: var(--black);
    }

    .autocomplete-text-input .listbox-item {
      color: #fff;
    }
  }

  .autocomplete-text-input .listbox .listbox-item:hover,
  .autocomplete-text-input .listbox .listbox-item:active,
  .autocomplete-text-input .listbox .listbox-item:focus {
    background: var(--pale-blue);
    color: #fff;
  }

  .autocomplete-text-input .listbox .active-suggestion {
    background: var(--dark-blue);
    color: #fff;
  }
</style>
