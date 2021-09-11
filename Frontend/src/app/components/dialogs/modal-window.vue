<template>
    <div class="modal-window centred noselect" ref="container" v-show="visible">
        <div class="content centred">
            <span class="close-icon material-icons" v-on:click="close">close</span>
            <slot></slot>
        </div>
    </div>
</template>

<script>
export default {
    name: "modal-window",
    data() {
        return {
            visible: false,
        };
    },
    methods: {
        enableScroll() {
            const body = document.body;
            const scrollY = body.style.top;
            body.style.position = "";
            body.style.top = "";
            window.scrollTo(0, parseInt(scrollY || "0") * -1);
        },
        disableScroll() {
            const scrollY = document.documentElement.style.getPropertyValue("--scroll-y");
            const body = document.body;
            body.style.position = "fixed";
            body.style.top = `-${scrollY}`;
        },
        show() {
            this.visible = true;
            this.disableScroll();
        },
        confirm() {
            this.close();
        },
        cancel() {
            this.close();
        },
        close() {
            this.visible = false;
            this.enableScroll();
        },
    },
    mounted() {
        window.addEventListener("scroll", () => {
            document.documentElement.style.setProperty(
                "--scroll-y",
                `${window.scrollY}px`
            );
        });
    },
    beforeUnmount() {
        this.enableScroll();
    },
};
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
    background: #FFF;
    flex-direction: column;
    padding: 24px 48px;
    z-index: 30;
}

.modal-window .content .close-icon {
    color: rgba(0, 0, 0, .44);
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
