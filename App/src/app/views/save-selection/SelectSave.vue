<template>
    <div class="select-save full-page centred">
        <p>Hello</p>
        <flat-button v-on:click="logout" text="Log out"></flat-button>
    </div>
</template>

<script>
import FlatButton from "../../components/widgets/buttons/flat-button.vue";

export default {
    name: "SelectSave",
    components: { FlatButton },
    methods: {
        logout() {
            this.$store.dispatch("auth/logout").then(_ => {
                this.$router.go();
            });
        }
    }
};
</script>

<style scoped>
.select-save {
    flex-direction: column;
    padding: 24px;
}
</style>