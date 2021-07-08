<template>
    <div class="welcome full-page centred">
        <div class="form-card centred">
            <h1>{{ title }}</h1>
            <p>{{ subtitle }}</p>
            <div ref="login" class="login container centred">
                <login-form ref="login-form"></login-form>
            </div>
            <div ref="signup" class="signup container centred">
                <signup-form ref="signup-form"></signup-form>
            </div>
            <a class="inline-link" v-on:click="toggle">{{ infoText }}</a>
        </div>
    </div>
</template>

<script>
import FlatButton from "../../components/widgets/buttons/flat-button.vue";
import LoginForm from "./login-form.vue";
import SignupForm from "./signup-form.vue";


export default {
    name: "Welcome",
    components: { FlatButton, SignupForm, LoginForm },
    data() {
        return {
            activeTab: this.$refs.login,
        };
    },
    computed: {
        title() {
            return (this.activeTab === this.$refs.login) ? "Sign In" : "Sign Up";
        },
        subtitle() {
            return (this.activeTab === this.$refs.login) ?
                "Enter your username and password below to sign in." :
                "Fill out the details below to register a new account.";
        },
        infoText() {
            return (this.activeTab === this.$refs.login) ?
                "Don't have an account? Create one now" :
                "Already have an existing account? Sign in now";
        },
        loggedIn() {
            return this.$store.state.auth.status.loggedIn;
        },
    },
    created() {
        if (this.loggedIn) {
            this.$router.push({ name: "play" });
        }
    },
    methods: {
        toggle() {
            this.activeTab.classList.remove("active-tab");
            this.activeTab = (this.activeTab === this.$refs.login) ?
                this.$refs.signup : this.$refs.login;
            this.activate();
        },
        activate() {
            this.activeTab.classList.add("active-tab");
            const activeForm = (this.activeTab === this.$refs.login) ?
                this.$refs["login-form"] :
                this.$refs["signup-form"];
            activeForm.activate();
        }
    },
    mounted() {
        this.activeTab = this.$refs.login;
        if (this.$route.redirectedFrom !== undefined) {
            const redirect = this.$route.redirectedFrom.path;
            if (redirect === "/sign-up" || redirect === "/register") {
                this.activeTab = this.$refs.signup;
            }
        }
        this.activate();
    }
};
</script>

<style>
.welcome {
    flex-direction: column;
    padding: 24px;
}

h1 {
    margin-bottom: 0;
}

.form-card {
    flex-direction: column;
    overflow: hidden;
    z-index: 1;
}

.form-card > h1, .form-card > p {
    margin-right: auto;
}

.form-card p {
    font-size: 15px;
}

.container {
    display: none;
    width: 100%;
}

.active-tab {
    display: flex;
}

.form-card > a {
    margin-top: 16px;
    text-align: center;
}

.text-input label, .text-input input {
    color: #FFF;
}

@media (min-width: 768px) {
    .form-card {
        padding: 36px;
    }
}

@media (min-width: 812px) {
    .form-card {
        padding: 48px;
    }
}

@media (min-width: 900px) {
    .form-card {
        padding: 64px;
        backdrop-filter: blur(10px);
        background-clip: padding-box;
        background: rgba(255, 255, 255, .2);
        border: 1px solid rgba(255, 255, 255, .3);
        border-radius: 8px;
    }

    .text-input .input-container {
        background: rgba(254, 254, 254, 0.075);
        border: 2px solid rgba(254, 254, 254, 0.3);
    }
}

@media (min-width: 1024px) {
    .form-card {
        padding: 64px 80px;
    }
}

@media (min-width: 1600px) {
    .form-card {
        padding: 72px 90px;
    }
}
</style>