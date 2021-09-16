<template>
    <form class="login-form centred" v-on:submit.prevent="login">
        <text-input id="login-username" ref="username" :maxlength="255" :no-spaces="true" :object="user"
                    autocomplete="username" icon="person" key-name="username" label="Username"
                    placeholder="Username" type="text">
        </text-input>
        <text-input id="login-password" ref="password" :no-spaces="true" :object="user"
                    autocomplete="current-password" icon="lock" key-name="password" label="Password"
                    placeholder="Password" type="password">
        </text-input>
        <flat-button text="Log In" v-on:click.prevent="login"></flat-button>
    </form>
</template>

<script>
import FlatButton from "../../components/widgets/buttons/flat-button.vue";
import TextInput from "../../components/widgets/text-input/text-input.vue";


export default {
    name: "login-form",
    components: { FlatButton, TextInput },
    data() {
        return {
            loading: false,
            user: {
                username: "",
                password: "",
            },
        };
    },
    methods: {
        activate() {
            this.clearInputs();
            this.$refs.username.focus();
        },
        clearInputs() {
            this.$refs.username.clearInput();
            this.$refs.password.clearInput();
        },
        clearSensitiveInputs() {
            this.$refs.password.clearInput();
            this.$refs.username.focus();
        },
        login() {
            if (this.user.username !== "" &&
                this.user.password !== "") {

                this.loading = true;
                this.$store.dispatch("auth/login", this.user).then(
                    // On success
                    () => {
                        this.$nextTick(() => {
                            this.$router.push(
                                {
                                    name: "league-select",
                                    params: { "username": this.user.username }
                                });
                        });
                    },
                    // On failure
                    (error) => {
                        this.loading = false;
                        alert(error.error);
                        this.clearSensitiveInputs();
                    }
                );
            } else {
                this.clearSensitiveInputs();
            }
        },
    },
};
</script>

<style>
.login-form {
    flex-direction: column;
    width: 100%;
}

.login-form .text-input {
    margin-bottom: 16px;
}

.login-form .flat-button,
.login-form .text-input {
    flex: 1;
    width: 100%;
}
</style>
