<template>
    <div ref="sidebar" class="sidebar noselect centred">
        <navbar></navbar>
        <aside class="content-sidebar centred">
            <team-info></team-info>
            <div class="content">
                <slot></slot>
            </div>
        </aside>
        <button class="sidebar-toggler centred" v-on:click="toggle" v-on:mouseenter="triggerTooltip"
                v-on:mouseleave="resetTooltip">
            <span class="toggler-icon material-icons centred">arrow_back_ios</span>
            <tooltip ref="tooltip" text="Minimise the sidebar"></tooltip>
        </button>
    </div>
</template>

<script>
import Navbar from "../navbar/Navbar.vue";
import Tooltip from "../widgets/tooltip.vue";
import TeamInfo from "./team-info.vue";


export default {
    name: "Sidebar",
    components: { Tooltip, TeamInfo, Navbar },
    data() {
        return {
            minimised: false,
            autoMinimised: false,
        };
    },
    methods: {
        resetTooltip() {
            if (this.$refs.tooltip !== null)
                this.$refs.tooltip.disable();
        },
        triggerTooltip() {
            this.$refs.tooltip.enable();
        },
        toggle() {
            this.$refs.sidebar.classList.toggle("minimised");
            this.minimised = !this.minimised;
        },
        scaleSidebar() {
            const width = window.innerWidth || document.documentElement.clientWidth ||
                document.body.clientWidth;
            if (width <= 900) {
                if (!this.minimised) {
                    this.toggle();
                    this.autoMinimised = true;
                }
            } else {
                if (this.minimised && this.autoMinimised) {
                    this.toggle();
                    this.autoMinimised = false;
                }
            }
        }
    },
    mounted() {
        this.scaleSidebar();
        window.addEventListener('resize', this.scaleSidebar);
    },
    beforeUnmount() {
        window.removeEventListener('resize', this.scaleSidebar);
    }
};
</script>

<style scoped>
.sidebar {
    min-width: var(--max-sidebar-width);
    max-width: var(--max-sidebar-width);
    height: 100vh;
    transition-property: box-shadow, max-width, min-width, width;
    box-shadow: 0 16px 38px -12px rgba(0, 0, 0, .56), 0 4px 25px 0 rgba(0, 0, 0, .12), 0 8px 10px -5px rgba(0, 0, 0, .2);
    position: relative;
}

.sidebar, .sidebar-toggler, .sidebar-toggler > * {
    transition-duration: var(--sidebar-transition-duration);
    transition-timing-function: ease-in-out;
    transition-delay: 0s;
    backface-visibility: hidden;
}

.sidebar > * {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
}

.minimised {
    min-width: var(--navbar-width);
    max-width: var(--navbar-width);
}

.content-sidebar {
    min-height: 100%;
    flex-direction: column;
    align-items: flex-start;
    width: calc(100% - var(--navbar-width));
    overflow: hidden;
    background: var(--blue);
    left: var(--navbar-width);
    justify-content: flex-start;
}

.content-sidebar > * {
    min-width: calc(var(--max-sidebar-width) - var(--navbar-width) - 32px);
    max-width: calc(var(--max-sidebar-width) - var(--navbar-width) - 32px);
    margin: 16px;
}

.sidebar-toggler {
    background: var(--turqoise);
    border-radius: 50%;
    position: absolute;
    top: unset;
    bottom: 28px;
    left: calc(var(--max-sidebar-width) - 12px);
    width: 24px;
    height: 24px;
    box-shadow: 0 0 0 1px rgba(9, 30, 66, .08), 0 2px 4px 1px rgba(9, 30, 66, .08);
    cursor: pointer;
    z-index: 10;
    transition-property: left;
    border: none;
}

.toggler-icon {
    color: #FFF;
    zoom: 0.5;
    font-weight: 900;
    transition-property: transform;
    transform: translateX(4px);
}

.sidebar-toggler:hover {
    background: var(--pale-blue);
}

.minimised .sidebar-toggler {
    left: calc(var(--navbar-width) - 12px);
}

.minimised .sidebar-toggler .toggler-icon {
    transform: rotate(180deg) translateX(4px);
}

.content {
    flex: 1;
    align-items: flex-start;
}
</style>