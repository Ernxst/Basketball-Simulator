<template>
  <footer class="noselect">
    <span class="name">Ernest Badu</span>
  </footer>
</template>

<script lang="ts">
  import { defineComponent } from "vue";
  export default defineComponent({
    name: "page-footer",
  });
</script>

<style scoped>
  footer {
    position: fixed;
    z-index: 10;
    background: var(--dark-blue);
    right: 0;
    bottom: 0;
    padding: 4px 8px;
    box-shadow: 0 -14px 28px rgba(0, 0, 0, 0.25),
      0 10px 10px rgba(0, 0, 0, 0.22);
    cursor: default;
    font-size: 10px;
  }

  .name {
    color: #fff;
    font-size: 10px;
    font-weight: 700;
  }
</style>
