<template>
  <div ref="container" class="calendar-container centred noselect">
    <slot></slot>
  </div>
</template>

<script lang="ts">
  import { defineComponent } from "vue";
  export default defineComponent({
    name: "calendar-container",
  });
</script>

<style>
  .calendar-container {
    height: 100%;
  }

  .calendar-container > * {
    width: 100%;
  }
</style>
