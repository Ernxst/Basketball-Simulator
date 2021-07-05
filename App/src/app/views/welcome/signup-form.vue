<template>
    <form class="signup-form centred" v-on:submit.prevent="signup">
        <text-input id="signup-username" ref="username" :no-spaces="true"
                    :object="user" :required="true" autocomplete="username"
                    icon="person" key-name="username" label="Username"
                    placeholder="JohnDoe123" type="text">
        </text-input>
        <text-input id="signup-password" ref="password" :no-spaces="true"
                    :object="user" :required="true" autocomplete="new-password"
                    icon="lock" key-name="password" label="Password"
                    placeholder="Password" type="password">
        </text-input>
        <text-input id="signup-repeat" ref="repeat-password" :no-spaces="true" :object="user"
                    :required="true" icon="repeat" key-name="repeat-password"
                    label="Repeat Password" placeholder="Repeat Password" type="password">
        </text-input>
        <flat-button text="Sign Up" v-on:click.prevent="signup"></flat-button>
    </form>
</template>

<script>
import FlatButton from "../../components/widgets/buttons/flat-button.vue";
import TextInput from "../../components/widgets/text-input/text-input.vue";

export default {
    name: "signup-form",
    components: { FlatButton, TextInput },
    data() {
        return {
            loading: false,
            user: {
                username: "",
                password: "",
                "repeat-password": "",
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
            this.$refs["repeat-password"].clearInput();
        },
        clearSensitiveInputs() {
            this.$refs.password.clearInput();
            this.$refs["repeat-password"].clearInput();
        },
        validInputs() {
            for (const [key, field] of Object.entries(this.user)) {
                if (field === "") {
                    return {
                        message: "Please ensure no fields are left blank.",
                        ref: key,
                    };
                }
            }
            if (this.user.username.length < 3) {
                return {
                    message: "Your username must be at least three characters long.",
                    ref: "username"
                };
            }
            if (this.user.password.length < 5) {
                return {
                    message: "Your password must be at least five characters long.",
                    ref: "password"
                };
            }
            if (this.user.password !== this.user["repeat-password"]) {
                return {
                    message: "The two entered passwords do not match.",
                    ref: "password"
                };
            }
            return { message: "valid", ref: null };
        },
        async signup() {
            const messageAndField = this.validInputs();
            if (messageAndField["message"] === "valid") {
                this.loading = true;
                this.$store.dispatch("auth/register", this.user).then(
                    (data) => {
                        this.$store.dispatch("auth/login", this.user).then(data => {
                            this.loading = false;
                            this.$router.push(
                                {
                                    name: "team-select",
                                    params: { "username": this.user.username }
                                });
                        });
                    },
                    (error) => {
                        const message = (error.response &&
                            error.response.data &&
                            error.response.data.message) ||
                            error.message ||
                            error.toString();
                        this.loading = false;
                        alert(message);
                        this.clearSensitiveInputs();
                    }
                );
            } else {
                alert("Sign up failed. " + messageAndField["message"]);
                this.clearSensitiveInputs();
                await this.$nextTick(() => {
                    this.$refs[messageAndField["ref"]].clearInput();
                });
            }
        },
    }
};
</script>

<style>
.signup-form {
    flex-direction: column;
    width: 100%;
}

.signup-form .text-input {
    margin-bottom: 12px;
}

.signup-form .flat-button, .signup-form .text-input {
    flex: 1;
    width: 100%;
}

@media (max-width: 640px) {
    .signup-form .text-input label {
        color: #FFF !important;
        text-shadow: -1px -1px 0 var(--blue), 1px -1px 0 var(--blue), -1px 1px 0 var(--blue), 1px 1px 0 var(--blue);
    }
}
</style>