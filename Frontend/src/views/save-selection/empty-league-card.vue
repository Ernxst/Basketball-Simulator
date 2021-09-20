<template>
  <div class="empty-card centred">
    <p class="bg-text">EMPTY SLOT</p>
    <span class="add-icon material-icons" @click="$emit('newLeague')">add</span>
  </div>
</template>

<script lang="ts">
  import { defineComponent } from "vue";
  export default defineComponent({
    name: "empty-league-card",
  });
</script>

<style scoped>
  .empty-card {
    position: relative;
    width: 100%;
    height: 100%;
    flex-direction: column;
    background: var(--black);
    border-radius: inherit;
  }

  p {
    font-weight: 700;
  }

  .add-icon {
    font-size: 72px;
    border-radius: 50%;
    color: #fff;
    background: var(--flat-green);
  }

  .add-icon:hover {
    filter: brightness(70%);
  }
</style>
