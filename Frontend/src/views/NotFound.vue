<template>
  <div ref="container" class="not-found centred">
    <div class="content centred">
      <h1 class="centred">Oops!</h1>
      <h3 ref="subtitle" class="centred">404 - Page Not Found</h3>
      <p ref="text" class="centred">
        The page you're looking for may have had its name changed, be
        temporarily unavailable, been removed or may not exist at all.
      </p>
      <vlink-button href="/" text="Back to safety"></vlink-button>
    </div>
  </div>
</template>

<script lang="ts">
  import FlatButton from "@/components/widgets/buttons/flat-button.vue";
  import VlinkButton from "@/components/widgets/buttons/vlink-button.vue";
  import { defineComponent, ref, onMounted, onBeforeMount } from "vue";

  export default defineComponent({
    name: "NotFound",
    components: { VlinkButton, FlatButton },
    setup() {
      const container = ref<HTMLElement | null>(null);
      const subtitle = ref<HTMLElement | null>(null);
      const text = ref<HTMLElement | null>(null);

      function resize() {
        if (!container.value || !subtitle.value || !text.value) return;
        let maxWidth = "75%";
        if (container.value.offsetWidth > 900) {
          maxWidth = (subtitle.value.offsetWidth * 1.25).toString() + "px";
        }
        text.value.style.maxWidth = maxWidth;
      }

      onMounted(() => {
        resize();
        window.addEventListener("resize", resize);
      });

      onBeforeMount(() => {
        window.removeEventListener("resize", resize);
      });

      return {
        container,
        subtitle,
        text,
      };
    },
  });
</script>

<style scoped>
  .not-found {
    flex-direction: column;
    min-height: 100vh;
    max-height: 100vh;
  }

  .content {
    flex-direction: column;
    margin-top: auto;
    margin-bottom: auto;
    padding-top: 16px;
  }

  h1 {
    font-weight: 900;
    font-size: 20vw;
    margin-top: 0;
    margin-bottom: 16px;
  }

  h3 {
    margin-top: 0;
    margin-bottom: 0;
    font-weight: 800;
    font-size: calc(1rem + 2vw);
    text-transform: uppercase;
  }

  p {
    text-align: center;
  }
</style>
