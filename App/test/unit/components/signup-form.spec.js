import {mount} from "@vue/test-utils";
import SignupForm from "../../../src/app/views/welcome/signup-form.vue";
import {store} from "../../../src/store/store";
import {mockRouter, takenUsername, validRegisterPassword, validRegisterUsername} from "../constants.js";

describe("Signup Form", () => {
    beforeAll(async () => {
        // TODO - Create valid users with validRegisterUsername and takenUsername
    });
    afterAll(async () => {
        // TODO - Delete valid user with validRegisterUsername and takenUsername
    });

    const test = (description, username, password,
                  repeatPassword, success, message) => {
        it(description, async (done) => {
            const routerSpy = spyOn(mockRouter, "push");
            const alertSpy = spyOn(window, "alert");

            const wrapper = mount(SignupForm, {
                global: {
                    plugins: [store], // TODO - Mock store
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
            if (success) {
                expect(routerSpy).toBeCalled();
            } else {
                expect(alertSpy).toBeCalledWith(message);
                expect(repeatPasswordInput.text()).toBe("");
                expect(passwordInput.text()).toBe("");
            }
            wrapper.unmount();
            done();
        });
    };

    /**
     * Blank input field tests - dispatch to store should not be made if a field is left empty.
     */
    test("no data entered", "", "", "",
        false, "Please ensure no fields are left blank.");

    test("no username entered", "", "password",
        "password", false,
        "Please ensure no fields are left blank.");

    test("no password entered", "username",
        "", "password", false,
        "Please ensure no fields are left blank.");

    test("no repeat password entered", "username",
        "password", "",
        false, "Please ensure no fields are left blank.");

    /**
     * Invalid data
     */
    test("short username (< 3 chars)", "aa", "password",
        "password", false,
        "Your username must be at least three characters long.");

    test("short password (< 5 chars)", "username",
        "aaa", "aaaa", false,
        "Your password must be at least five characters long.");

    test("non-matching password repeat", "username", "password",
        "other", false, "The two entered passwords do not match.");

    /**
     * Taken data
     */

    test("taken username", takenUsername, "password",
        "password", false,
        `The username ${takenUsername} is already in use, please try another. Did you mean to sign in?`);

    /**
     * Valid data
     */
    test("valid signup", validRegisterUsername, validRegisterPassword,
        validRegisterPassword,  true, "");
});
