<template>
    <div class="autocomplete-text-input">
        <text-input ref="input" :id="id" :type="type" :icon="icon" :maxlength="maxlength" :placeholder="placeholder"
                    :label="label" :no-spaces="noSpaces" :on-enter="onSubmit" :disallowed-keys="disallowedKeys"
                    autocomplete="off" :required="required" :object="object" :key-name="keyName" :name="`${id}-search`"
                    @keyup.down='down' @keyup.up='up'></text-input>
        <ul class="listbox centred" ref="listbox" v-show="matches.length > 0">
            <li v-for="(suggestion, index) in matches" class="listbox-item" @keyup.down='down' @keyup.up='up'
                v-bind:class="{'active-suggestion': isActive(index)}" @click="suggestionClick(index)">
                {{ suggestion }}
            </li>

        </ul>
    </div>
</template>

<script>
import TextInput from "./text-input.vue";

export default {
    name: "autocomplete-input",
    components: { TextInput },
    props: {
        suggestions: {
            type: Array,
            required: true,
        },
        id: {
            type: String,
            required: true
        },
        type: {
            type: String,
            default: "text",
        },
        icon: String,
        maxlength: {
            type: Number,
            default: 32
        },
        placeholder: String,
        label: { type: String, default: "" },
        noSpaces: { type: Boolean, default: false },
        onEnter: {
            type: Function, default: () => {
            }
        },
        disallowedKeys: {
            type: Array,
            default: [";", "[", "]", "{", "}", "\"",
                "£", "$", "%", "^", "&", "*", "_", "+", "/",
                "=", "|", "\\", "~", "`", ":", "!", "±", "§"],
        },
        autocomplete: { type: String, default: "off" },
        required: { type: Boolean, default: false },
        object: Object,
        keyName: String,
    },
    computed: {
        matches() {
            const input = this.object[this.keyName].toLowerCase();
            if (input.length === 0)
                return [];
            const matches = this.suggestions.filter((str) => {
                const suggestion = str.toLowerCase();
                return suggestion.startsWith(input) && suggestion !== input;
            });
            matches.sort();
            this.current = 0;
            return matches;
        },
    },
    data() {
        return {
            current: 0,
            open: false,
        };
    },
    methods: {
        up() {
            this.current--;
        },
        down() {
            this.current++;
        },
        isActive(index) {
            return index === this.current;
        },
        suggestionClick(index) {
            this.object[this.keyName] = this.matches[index % this.matches.length];
            this.reset();
        },
        focus() {
            this.$refs.input.focus();
        },
        reset() {
            this.open = false;
            this.current = 0;
        },
        toggleDelete() {
            this.$refs.input.toggleDelete();
        },
        clearInput() {
            this.$refs.input.clearInput();
        },
        onSubmit() {
            this.object[this.keyName] = this.matches[this.current % this.matches.length];
            this.reset();
            this.onEnter();
        },
    }
};
</script>

<style>
.autocomplete-text-input {
    position: relative;
}

.autocomplete-text-input .listbox {
    top: calc(100% + 4px);
    position: absolute;
    width: 100%;
    left: 0;
    right: 0;
    padding: 6px;
    border-radius: var(--input-radius);
    margin: 0;
    flex-direction: column;
    background: #FFF;
    z-index: 9;
    box-shadow: 0 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23);
}

.autocomplete-text-input .listbox-item {
    width: 100%;
    padding: 6px;
    color: #000;
    text-align: left;
    border-radius: var(--input-radius);
    justify-content: flex-start;
    cursor: pointer;
    list-style: none;
    margin: 1px;
}

@media (prefers-color-scheme: dark) {
    .autocomplete-text-input .listbox {
        background-color: var(--black);
    }

    .autocomplete-text-input .listbox-item {
        color: #FFF;
    }
}

.autocomplete-text-input .listbox .listbox-item:hover, .autocomplete-text-input .listbox .listbox-item:active,
.autocomplete-text-input .listbox .listbox-item:focus {
    background: var(--pale-blue);
    color: #FFF;
}

.autocomplete-text-input .listbox .active-suggestion {
    background: var(--dark-blue);
    color: #FFF;
}
</style>