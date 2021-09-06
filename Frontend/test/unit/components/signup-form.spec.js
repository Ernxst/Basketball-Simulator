import { flushPromises, mount } from "@vue/test-utils";
import SignupForm from "../../../src/app/views/welcome/signup-form.vue";
import { FULL_USER_REGISTER_ENDPOINT } from "../../../src/services/endpoints.ts";
import { store } from "../../../src/store/store.js";
import { app, takenUsername, validRegisterPassword, validRegisterUsername } from "../constants.js";
import { mockApi, mockRouter } from "../mocks.ts";


describe("Signup Form", () => {
    beforeAll(() => {
        mockApi.onPost(FULL_USER_REGISTER_ENDPOINT,
            { username: validRegisterUsername, password: validRegisterPassword })
            .reply(200, {
                username: validRegisterUsername, message: "Registration success", code: "OK"
            });

        mockApi.onPost(FULL_USER_REGISTER_ENDPOINT,
            { username: takenUsername, password: validRegisterPassword })
            .reply(200, {
                message: `The username ${takenUsername} is already in use, please try another. Did you mean to sign in?`,
                code: "UNAUTHORIZED"
            });
    });
    
    const testRegister = async (username, password, repeatPassword, success, message) => {
        const routerSpy = spyOn(mockRouter, "push");
        const alertSpy = spyOn(window, "alert");

        const wrapper = mount(SignupForm, {
            attachTo: app,
            global: {
                plugins: [store],
                mocks: {
                    $router: mockRouter
                }
            },
        });

        const usernameInput = wrapper.find("#signup-username");
        await usernameInput.setValue(username);

        const passwordInput = wrapper.find("#signup-password");
        await passwordInput.setValue(password);

        const repeatPasswordInput = wrapper.find("#signup-repeat");
        await repeatPasswordInput.setValue(repeatPassword);

        expect(wrapper.vm.user.username).toEqual(username);
        expect(wrapper.vm.user["repeat-password"]).toEqual(repeatPassword);
        expect(wrapper.vm.user.password).toEqual(password);

        await wrapper.vm.signup();
        await flushPromises();

        if (success) {
            expect(routerSpy).toBeCalled();
        } else {
            expect(alertSpy).toBeCalledWith(message);
            expect(passwordInput.text()).toBe("");
            expect(repeatPasswordInput.text()).toBe("");
        }
        wrapper.unmount();
    };

    it("No data entered", (done) => {
        testRegister("", "", "",
            false, "Sign up failed. Please ensure no fields are left blank.").then(() => {
            done();
        });
    });

    it("No username entered", (done) => {
        testRegister("", validRegisterPassword, validRegisterPassword,
            false, "Sign up failed. Please ensure no fields are left blank.").then(() => {
            done();
        });
    });

    it("No password entered", (done) => {
        testRegister(validRegisterUsername, "", validRegisterPassword, false,
            "Sign up failed. Please ensure no fields are left blank.").then(() => {
            done();
        });
    });

    it("No repeat password entered", (done) => {
        testRegister(validRegisterUsername, validRegisterPassword, "",
            false, "Sign up failed. Please ensure no fields are left blank.").then(() => {
            done();
        });
    });

    it("Short username (< 3 chars)", (done) => {
        testRegister("aa", validRegisterPassword, validRegisterPassword, false,
            "Sign up failed. Your username must be at least three characters long.").then(() => {
            done();
        });
    });

    it("Short password (< 5 chars)", (done) => {
        testRegister("username", "123", validRegisterPassword, false,
            "Sign up failed. Your password must be at least five characters long.").then(() => {
            done();
        });
    });

    it("Non-matching password repeat", (done) => {
        testRegister(validRegisterUsername, validRegisterPassword, "other",
            false, "Sign up failed. The two entered passwords do not match.").then(() => {
            done();
        });
    });

    it("Taken username", (done) => {
        testRegister(takenUsername, validRegisterPassword, "password",
            false,
            `The username ${takenUsername} is already in use, please try another. Did you mean to sign in?`).then(() => {
            done();
        });
    });

    it("Valid signup", (done) => {
        testRegister(validRegisterUsername, validRegisterPassword,
            validRegisterPassword, true, "Registration success").then(() => {
            done();
        });
    });
})
;
