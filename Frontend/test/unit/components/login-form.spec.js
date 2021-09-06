import { flushPromises, mount } from "@vue/test-utils";
import LoginForm from "../../../src/app/views/welcome/login-form.vue";
import { store } from "../../../src/store/store.js";
import { app, incorrectPassword, invalidLoginUsername, validLoginPassword, validLoginUsername } from "../constants.js";
import { mockRouter } from "../mocks.ts";


describe("Login Form", () => {
    const testLogin = async (username, password, success, message) => {
        const routerSpy = spyOn(mockRouter, "push");
        const alertSpy = spyOn(window, "alert");

        const wrapper = mount(LoginForm, {
            attachTo: app,
            global: {
                plugins: [store],
                mocks: {
                    $router: mockRouter
                }
            },
        });

        const usernameInput = wrapper.find("#login-username");
        await usernameInput.setValue(username);
        const passwordInput = wrapper.find("#login-password");
        await passwordInput.setValue(password);

        expect(wrapper.vm.user.username).toEqual(username);
        expect(wrapper.vm.user.password).toEqual(password);

        await wrapper.vm.login();
        await flushPromises();

        if (success) {
            expect(routerSpy).toBeCalled();
        } else {
            if (message !== "") {
                expect(alertSpy).toBeCalledWith(message);
            }
            expect(passwordInput.text()).toBe("");
        }
        wrapper.unmount();
    };

    /**
     * Dispatch to store should not be made if a field is left empty.
     */
    it("No username, no password", (done) => {
        testLogin("", "", false, "").then(() => {
            done();
        });
    });


    it("Invalid username, no password", (done) => {
        testLogin("doesntexist", "", false, "").then(() => {
            done();
        });
    });

    it("Valid username, no password", (done) => {
        testLogin(validLoginUsername, "", false, "").then(() => {
            done();
        });
    });

    it("No username, some password", (done) => {
        testLogin("", "password", false, "").then(() => {
            done();
        });
    });

    it("Valid username, correct password", (done) => {
        testLogin(validLoginUsername, validLoginPassword, true, "").then(() => {
            done();
        });
    });

    it("Valid username, incorrect password", (done) => {
        testLogin(validLoginUsername, incorrectPassword, false,
            "Login failed, either your username or password was incorrect, please try again.").then(() => {
            done();
        });
    });

    it("Invalid username", (done) => {
        testLogin(invalidLoginUsername, incorrectPassword, false,
            `Login failed, the username ${invalidLoginUsername} does not exist; are you trying to sign up?`).then(() => {
            done();
        });
    });
});
