<template>
  <form class="login-form centred" @submit.prevent="login">
    <text-input
      id="login-username"
      ref="username"
      :maxlength="255"
      :no-spaces="true"
      autocomplete="username"
      icon="person"
      label="Username"
      placeholder="Username"
      type="text"
      v-model="user.username"
    >
    </text-input>
    <text-input
      id="login-password"
      ref="password"
      :no-spaces="true"
      autocomplete="current-password"
      icon="lock"
      label="Password"
      placeholder="Password"
      type="password"
      v-model="user.password"
    >
    </text-input>
    <flat-button text="Log In" @click.prevent="login"></flat-button>
  </form>
</template>

<script lang="ts">
  import FlatButton from "@/components/widgets/buttons/flat-button.vue";
  import TextInput from "@/components/widgets/text-input/text-input.vue";

  import { defineComponent } from "vue";
  export default defineComponent({
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
        const username = this.user.username;
        if (this.user.username !== "" && this.user.password !== "") {
          this.loading = true;
          this.$store
            .dispatch("auth/login", this.user)
            .then(() => {
              this.$nextTick(() => {
                this.$router.push({
                  name: "league-select",
                  params: { username: username },
                });
              });
            })
            .catch((error: string) => {
              this.loading = false;
              alert(error);
              this.clearSensitiveInputs();
            });
        } else {
          this.clearSensitiveInputs();
        }
      },
    },
  });
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
