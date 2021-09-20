<template>
  <router-view v-slot="{ Component }">
    <transition>
      <div class="background-container">
        <img
          v-if="background !== ''"
          :src="background"
          alt="Background"
          class="background-img centred"
        />
        <component :is="Component" />
      </div>
    </transition>
  </router-view>
</template>

<script lang="ts">
  import { defineComponent } from "vue";
  export default defineComponent({
    name: "App",
    data() {
      return {
        background: "",
      };
    },
    beforeCreate() {
      this.$store.dispatch("media/fetchBackgrounds").then((_: null) => {
        this.setBackground();
      });
    },
    watch: {
      $route(to, from) {
        this.setBackground();
        if (to.hash) {
          this.$nextTick(() => {
            this.scrollToId(to.hash.slice(1));
          });
        }
      },
    },
    methods: {
      setBackground() {
        const src = this.$route.name;
        this.background = this.$store.getters["media/backgrounds"][src] || "";
      },
      scrollToId(id: string) {
        try {
          const element = document.getElementById(id);
          if (!element) return;
          window.scrollTo({
            top: element.offsetTop - 72,
            behavior: "smooth",
          });
        } catch {
          location.hash = id;
        }
      },
    },
  });
</script>

<style scoped>
  .background-container::after {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    content: "";
    background: linear-gradient(0deg, #00000088 40%, #ffffff44 100%);
    width: 100%;
    height: 100%;
    z-index: -1;
  }
</style>
