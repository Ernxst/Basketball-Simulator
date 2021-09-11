<template>
    <button ref="button" :type="type" class="flat-button noselect centred">
        <span>{{ text }}</span>
        <slot></slot>
    </button>
</template>

<script>
export default {
    name: "flat-button",
    props: {
        type: String,
        text: String,
    },
    methods: {
        enable() {
            this.$refs.button.classList.remove("disabled");
        },

        disable() {
            this.$refs.button.classList.add("disabled");
        }
    }
};
</script>

<style scoped>
.flat-button {
    --button-bg: var(--black);
    position: relative;
    background: var(--button-bg);
    outline: 0;
    border: 0;
}

.flat-button span {
    padding: 9px 18px;
    font-size: 16px;
    color: #FFF;
    font-weight: 700;
    z-index: 2;
    text-transform: uppercase;
}

.flat-button {
    transition: .1s ease-in-out all;
    border-radius: var(--button-radius);
    cursor: pointer;
}

.flat-button:hover {
    box-shadow: 0 6px 12px -1px rgba(0, 0, 0, .33);
    filter: brightness(66%);
}

.disabled {
    pointer-events: none;
    filter: brightness(33%);
}
</style>
