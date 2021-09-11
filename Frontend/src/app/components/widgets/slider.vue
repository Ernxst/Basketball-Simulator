<template>
    <div class="slider-container noselect centred">
        <p class="slider-label">{{ label }}</p>
        <div class="slider-inner centred">
            <span class="min value centred" v-if="show_limits">{{ min }}</span>
            <div class="slider centred">
                <input type="range" :min="min" :max="max" class="slider" :id="id" ref="slider"
                       :step="step" :value="modelValue" @input="update">
                <span class="current-value noselect centred" ref="value">{{ modelValue }}</span>
            </div>
            <span class="max value centred" v-if="show_limits">{{ max }}</span>
        </div>
    </div>
</template>

<script>
export default {
    name: "slider",
    props: {
        min: { default: 0, type: Number },
        max: { default: 100, type: Number },
        modelValue: { default: 50, type: Number },
        step: { default: 1, type: Number },
        show_limits: { default: false, type: Boolean },
        id: String,
        label: String,
    },
    computed: {
        range() {
            return this.max - this.min;
        },
    },
    beforeUpdate() {
        this.setPos(this.modelValue);
    },
    methods: {
        update(e) {
            const value = e.target.value;
            this.$emit('update:modelValue', value);
            this.setPos(value);
        },
        setPos(value) {
            const percentage = (value - this.min) / this.range;
            const width = this.$refs.slider.clientWidth;
            const labelWidth = this.$refs.value.clientWidth;
            this.$refs.value.style.left = `${(percentage * (width - labelWidth))}px`;
        },
    },
    mounted() {
        window.addEventListener('resize', () => {
            this.setPos(this.modelValue);
        });
    }
};
</script>

<style scoped>
.slider-container, .slider {
    width: 100%;
    flex-direction: column;
    position: relative;
}

.slider-label {
    text-align: left;
    margin-top: 0;
    margin-bottom: 16px;
    line-height: 90%;
    font-size: 16px;
    color: #555;
}

.slider-inner, .slider-label, .slider {
    width: 100%;
}

.value {
    padding: 4px;
    border-radius: var(--button-radius);
    color: var(--black);
    background: #D5D5D5;
}

.min {
    margin-right: 6px;
}

.max {
    margin-left: 6px;
}

.slider input[type=range] {
    -webkit-appearance: none;
    height: 4px;
    border-radius: var(--button-radius);
    outline: none;
    width: 100%;
    background: #D3D3D3;
    background-image: -webkit-gradient(linear, 20% 0%, 20% 100%, color-stop(0%, #49A362), color-stop(100%, #49A362));
    background-image: -webkit-linear-gradient(left, #49A362 0%, #49A362 100%);
    background-image: -moz-linear-gradient(left, #49A362 0%, #49A362 100%);
    background-image: -o-linear-gradient(to right, #49A362 0%, #49A362 100%);
    background-image: linear-gradient(to right, #49A362 0%, #49A362 100%);
    background-repeat: no-repeat;
}

input[type=range]:focus {
    outline: none;
}

input[type=range]::-webkit-slider-runnable-track {
    width: 100%;
    height: 4px;
    cursor: pointer;
    box-shadow: none;
    background: transparent;
    border-radius: 0;
    border: none;
}

input[type=range]::-webkit-slider-thumb {
    box-shadow: none;
    border: 4px solid #49A362;
    height: 16px;
    width: 16px;
    border-radius: 2px;
    background: #212121;
    cursor: pointer;
    -webkit-appearance: none;
    margin-top: -6px;
}

input[type=range]:focus::-webkit-slider-runnable-track {
    background: transparent;
}

input[type=range]::-moz-range-track {
    width: 100%;
    height: 4px;
    cursor: pointer;
    box-shadow: none;
    background: transparent;
    border-radius: 0;
    border: none;
}

input[type=range]::-moz-range-thumb {
    box-shadow: none;
    border: 4px solid #49A362;
    height: 16px;
    width: 16px;
    border-radius: 2px;
    background: #FFFFFF;
    cursor: pointer;
}

input[type=range]::-ms-track {
    width: 100%;
    height: 4px;
    cursor: pointer;
    background: transparent;
    border-color: transparent;
    color: transparent;
}

input[type=range]::-ms-fill-lower {
    background: transparent;
    border: none;
    border-radius: 0;
    box-shadow: none;
}

input[type=range]::-ms-fill-upper {
    background: transparent;
    border: none;
    border-radius: 0;
    box-shadow: none;
}

input[type=range]::-ms-thumb {
    box-shadow: none;
    border: 4px solid #49A362;
    height: 16px;
    width: 16px;
    border-radius: 2px;
    background: #FFFFFF;
    cursor: pointer;
}

input[type=range]:focus::-ms-fill-lower {
    background: transparent;
}

input[type=range]:focus::-ms-fill-upper {
    background: transparent;
}

.current-value {
    position: absolute;
    left: -4px;
    top: calc(100% + 10px);
    color: var(--flat-green);
    box-shadow: 0 0 4px rgba(0, 0, 0, .22);
    padding: 5px;
    z-index: 40;
}
</style>
