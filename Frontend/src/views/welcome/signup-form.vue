<template>
  <form class="signup-form centred" @submit.prevent="signup">
    <text-input
      id="signup-username"
      ref="username"
      :no-spaces="true"
      :required="true"
      autocomplete="username"
      icon="person"
      label="Username"
      placeholder="JohnDoe123"
      type="text"
      v-model="user.username"
    >
    </text-input>
    <text-input
      id="signup-password"
      ref="password"
      :no-spaces="true"
      :required="true"
      autocomplete="new-password"
      icon="lock"
      label="Password"
      placeholder="Password"
      type="password"
      v-model="user.password"
    >
    </text-input>
    <text-input
      id="signup-repeat"
      ref="repeat-password"
      :no-spaces="true"
      :required="true"
      icon="repeat"
      label="Repeat Password"
      placeholder="Repeat Password"
      type="password"
      v-model="user['repeat-password']"
    >
    </text-input>
    <flat-button text="Sign Up" @click.prevent="signup"></flat-button>
  </form>
</template>

<script lang="ts">
  import FlatButton from "@/components/widgets/buttons/flat-button.vue";
  import TextInput from "@/components/widgets/text-input/text-input.vue";

  import { defineComponent } from "vue";
  export default defineComponent({
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
            ref: "username",
          };
        }
        if (this.user.password.length < 5) {
          return {
            message: "Your password must be at least five characters long.",
            ref: "password",
          };
        }
        if (this.user.password !== this.user["repeat-password"]) {
          return {
            message: "The two entered passwords do not match.",
            ref: "password",
          };
        }
        return { message: "valid", ref: null };
      },
      async signup() {
        const messageAndField = this.validInputs();
        if (messageAndField["message"] === "valid") {
          this.loading = true;
          this.$store
            .dispatch("auth/register", this.user)
            .then(() => {
              this.$nextTick(() => {
                this.$router.push({
                  name: "league-select",
                  params: { username: this.user.username },
                });
              });
            })
            .catch((error: string) => {
              this.loading = false;
              alert(error);
              this.clearSensitiveInputs();
            });
        } else {
          alert("Sign up failed. " + messageAndField["message"]);
          this.clearSensitiveInputs();
          await this.$nextTick(() => {
            this.$refs[messageAndField["ref"]].clearInput();
          });
        }
      },
    },
  });
</script>

<style>
  .signup-form {
    flex-direction: column;
    width: 100%;
  }

  .signup-form .text-input {
    margin-bottom: 16px;
  }

  .signup-form .flat-button,
  .signup-form .text-input {
    flex: 1;
    width: 100%;
  }
</style>
