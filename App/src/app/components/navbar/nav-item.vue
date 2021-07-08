<template>
    <li ref="item" class="nav-container centred">
        <router-link :to="page">
            <span class="nav-item centred material-icons" v-on:mouseenter="trigger"
                  v-on:mouseleave="resetHover">{{ icon }}</span>
            <tooltip ref="tooltip" :text="tooltip"></tooltip>
        </router-link>
    </li>
</template>

<script>
import Tooltip from "../widgets/tooltip.vue";


export default {
    name: "nav-item",
    components: { Tooltip },
    props: {
        icon: String,
        tooltip: String,
        page: String,
    },
    methods: {
        enable() {
            this.$refs.item.classList.add("active-nav-item");
        },
        disable() {
            this.$refs.item.classList.remove("active-nav-item");
        },
        trigger() {
            this.$refs.tooltip.enable();
        },
        resetHover() {
            if (this.$refs.tooltip !== null)
                this.$refs.tooltip.disable();
        }
    }
};
</script>

<style scoped>
.nav-container {
    position: relative;
    margin-bottom: 8px;
}

.nav-item {
    list-style: none;
    padding: 5px;
    cursor: pointer;
    transition: .1s ease-in-out all;
    border-radius: var(--nav-button-radius);
    color: #FFF;
    font-weight: 400;
    font-size: 16px;
    zoom: 1.4;
}

.nav-item:hover, .active-nav-item {
    box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
}

.nav-item:hover {
    background: var(--pale-blue);
}

.active-nav-item {
    pointer-events: none;
}

.active-nav-item, .active-nav-item * {
    cursor: default;
}

.active-nav-item .nav-item {
    background: var(--turqoise);
}
</style>