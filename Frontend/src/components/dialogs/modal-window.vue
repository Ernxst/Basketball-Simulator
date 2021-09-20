<template>
  <div
    class="modal-window centred noselect"
    v-show="visible"
    @show="show"
    @close="close"
    @cancel="close"
    @confirm="close"
  >
    <div class="content centred">
      <slot></slot>
    </div>
  </div>
</template>
<script lang="ts">
  import { defineComponent, onBeforeUnmount, onMounted, ref } from "vue";

  export default defineComponent({
    name: "modal-window",
    setup() {
      const visible = ref(false);

      function show() {
        visible.value = true;
        disableScroll();
      }
      function close() {
        visible.value = false;
        enableScroll();
      }
      function enableScroll() {
        const scrollY = document.body.style.top;
        document.body.style.position = "";
        document.body.style.top = "";
        window.scrollTo(0, parseInt(scrollY || "0") * -1);
      }
      function disableScroll() {
        const scrollY =
          document.documentElement.style.getPropertyValue("--scroll-y");
        document.body.style.position = "fixed";
        document.body.style.top = `-${scrollY}`;
      }

      onMounted(() => {
        window.addEventListener("scroll", () => {
          document.documentElement.style.setProperty(
            "--scroll-y",
            `${window.scrollY}px`
          );
        });
      });

      onBeforeUnmount(() => {
        enableScroll();
      });
      return {
        visible,
        show,
        close,
      };
    },
  });
</script>

<style scoped>
  .modal-window {
    background: rgba(0, 0, 0, 0.67);
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    z-index: 20;
    padding: 24px;
    overflow-y: auto;
  }

  .modal-window .content {
    position: relative;
    border-radius: var(--card-radius);
    background: #fff;
    flex-direction: column;
    padding: 24px 48px;
    z-index: 30;
  }

  .modal-window .content .close-icon {
    color: rgba(0, 0, 0, 0.44);
    font-weight: 800;
    position: absolute;
    right: 16px;
    top: 16px;
    cursor: pointer;
    z-index: 31;
  }

  .modal-window .content .close-icon:hover {
    color: var(--flat-red);
  }

  @media (max-width: 1200px) {
    .modal-window .content {
      padding: 12px;
    }

    .modal-window .content .close-icon {
      top: 8px;
      right: 8px;
    }
  }
</style>
