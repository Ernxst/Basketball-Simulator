<template>
  <button ref="button" :type="type" class="flat-button noselect centred">
    <span>{{ text }}</span>
  </button>
</template>

<script lang="ts">
  import { defineComponent, ref } from "vue";

  type buttonType = "button" | "submit" | "reset";

  export default defineComponent({
    name: "flat-button",
    props: {
      type: {
        type: String as () => buttonType,
        default: "button",
      },
      text: { type: String, default: "" },
    },
    setup() {
      const button = ref<HTMLButtonElement | null>(null);

      function enable() {
        button.value?.classList.remove("disabled");
      }
      function disable() {
        button.value?.classList.add("disabled");
      }
      return { enable, disable, button };
    },
  });
</script>

<style scoped>
  .flat-button {
    --button-bg: var(--black);
    position: relative;
    background: var(--button-bg);
    outline: 0;
    border: 0;
  }

  .flat-button span {
    padding: 9px 18px;
    font-size: 16px;
    color: #fff;
    font-weight: 700;
    z-index: 2;
    text-transform: uppercase;
  }

  .flat-button {
    transition: 0.1s ease-in-out all;
    border-radius: var(--button-radius);
    cursor: pointer;
  }

  .flat-button:hover {
    box-shadow: 0 6px 12px -1px rgba(0, 0, 0, 0.33);
    filter: brightness(66%);
  }

  .disabled {
    pointer-events: none;
    filter: brightness(33%);
  }
</style>
