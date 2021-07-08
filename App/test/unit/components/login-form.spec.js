import { mount } from "@vue/test-utils";
import LoginForm from "../../../src/app/views/welcome/login-form.vue";
import { store } from "../../../src/store/store.js";
import { app, invalidUsername, mockRouter, validLoginPassword, validLoginUsername } from "../constants";


describe("Login Form", () => {
    beforeAll(async () => {
        // TODO - Create valid user with validLoginUsername
    });
    afterAll(async () => {
        // TODO - Delete valid user with validLoginUsername
    });
    const test = (description, username, password, success, message) => {
        it(description, async (done) => {
            const routerSpy = spyOn(mockRouter, "push");
            const alertSpy = spyOn(window, "alert");

            const wrapper = mount(LoginForm, {
                attachTo: app,
                global: {
                    plugins: [store], // TODO - Mock store
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
            if (success) {
                expect(routerSpy).toBeCalled();
            } else {
                if (message !== "") {
                    expect(alertSpy).toBeCalledWith(message);
                }
                expect(passwordInput.text()).toBe("");
            }
            wrapper.unmount();
            done();
        });
    };

    /**
     * Dispatch to store should not be made if a field is left empty.
     */
    test("no username, no password", "", "", false, "");
    test("valid username, no password", validLoginUsername, "",
        false, "");
    test("invalid username, no password", invalidUsername, "",
        false, "");
    test("no username, some password", "", "aaa", false,
        "");
    test("valid username, correct password", validLoginUsername, validLoginPassword,
        true, "");
    test("valid username incorrect password", validLoginUsername, "aaa",
        false,
        "Login failed, either your username or password was incorrect, please try again.");
    test("invalid username incorrect password", invalidUsername, "aaa",
        false,
        `Login failed, the username ${invalidUsername} does not exist; are you trying to sign up?`);

});
