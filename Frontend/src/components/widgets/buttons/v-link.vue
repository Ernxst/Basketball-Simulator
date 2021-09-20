<template>
  <router-link v-if="href !== ''" :to="href"
    >{{ text }}
    <slot></slot>
  </router-link>

  <slot v-else></slot>
</template>

<script lang="ts">
  import { defineComponent } from "vue";
  export default defineComponent({
    name: "v-link",
    props: {
      href: {
        type: String,
        default: "",
      },
      text: {
        type: String,
        default: "",
      },
    },
  });
</script>

<style scoped></style>
