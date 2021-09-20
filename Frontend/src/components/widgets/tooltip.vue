<template>
  <span :class="tooltipClass" v-show="text !== ''">
    {{ text }}
    <slot></slot>
  </span>
</template>

<script lang="ts">
  import { defineComponent } from "vue";

  export default defineComponent({
    name: "tooltip",
    props: {
      text: {
        type: String,
        default: "",
      },
      show: {
        type: Boolean,
        default: false,
      },
      delay: {
        type: Number,
        default: 500,
      },
    },
    computed: {
      tooltipClass() {
        return `${this.hovered ? "tooltip-active" : ""} tooltip centred`;
      },
    },
    data() {
      return {
        hovered: false,
        timeout: null,
      };
    },
    created() {
      this.hovered = this.show;
    },
    methods: {
      enable() {
        this.disable();
        this.timeout = setTimeout(() => {
          this.hovered = true;
        }, this.delay);
      },
      disable() {
        clearTimeout(this.timeout);
        this.timeout = null;
        this.hovered = false;
      },
    },
  });
</script>

<style scoped>
  .tooltip {
    max-width: 0;
    overflow: hidden;
    position: absolute;
    background: var(--black);
    color: #fff;
    font-size: 12px;
    left: 66.7%;
    top: 0;
    bottom: 0;
    margin: auto auto auto 50%;
    border-radius: var(--nav-button-radius);
    transition: 0.2s ease-in-out max-width;
    white-space: nowrap;
    z-index: 10;
    font-weight: 500;
    box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
  }

  .tooltip-active {
    max-width: 25vw;
    padding: 4px 8px;
    cursor: default;
    z-index: 100;
  }
</style>
