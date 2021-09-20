import { store } from "@/store/store";
import Welcome from "@/views/welcome/Welcome.vue";
import { mount } from "@vue/test-utils";
import { mockRouter } from "../mocks";

const loginTitle = "Sign In";
const loginSubtitle = "Enter your username and password below to sign in.";
const loginInfoText = "Don't have an account? Create one now";

const signupTitle = "Sign Up";
const signupSubtitle = "Fill out the details below to register a new account.";
const signupInfoText = "Already have an existing account? Sign in now";

const testRedirect = async (
  url: string,
  expectedTitle: string,
  expectedSubtitle: string,
  expectedInfoText: string
) => {
  const wrapper = mount(Welcome, {
    global: {
      plugins: [store],
      mocks: {
        $route: {
          path: "/welcome",
          redirectedFrom: {
            path: url,
          },
        },
        $router: mockRouter,
      },
    },
  });

  const title = wrapper.find(".form-card > h1");
  const subtitle = wrapper.find(".form-card > p");

  expect(wrapper.vm.title).toBe(expectedTitle);
  expect(wrapper.vm.title).toEqual(title.element.innerHTML);
  expect(wrapper.vm.subtitle).toBe(expectedSubtitle);
  expect(wrapper.vm.subtitle).toEqual(subtitle.element.innerHTML);
  expect(wrapper.vm.infoText).toBe(expectedInfoText);
};

const tabSwitch = async () => {
  const wrapper = mount(Welcome, {
    global: {
      plugins: [store],
      stubs: ["FooterLogo"],
      mocks: {
        $route: {
          path: "/welcome",
        },
        $router: mockRouter,
      },
    },
  });

  const title = wrapper.find(".form-card > h1");
  const subtitle = wrapper.find(".form-card > p");
  const link = wrapper.find(".inline-link");

  await link.trigger("click");
  expect(wrapper.vm.title).toBe(signupTitle);
  expect(wrapper.vm.title).toEqual(title.element.innerHTML);
  expect(wrapper.vm.subtitle).toBe(signupSubtitle);
  expect(wrapper.vm.subtitle).toEqual(subtitle.element.innerHTML);
  expect(wrapper.vm.infoText).toBe(signupInfoText);

  await link.trigger("click");
  expect(wrapper.vm.title).toBe(loginTitle);
  expect(wrapper.vm.title).toEqual(title.element.innerHTML);
  expect(wrapper.vm.subtitle).toBe(loginSubtitle);
  expect(wrapper.vm.subtitle).toEqual(subtitle.element.innerHTML);
  expect(wrapper.vm.infoText).toBe(loginInfoText);
};

describe("Welcome View", () => {
  it("Test tab switching", (done) => {
    tabSwitch().then(() => {
      done();
    });
  });
  it("The welcome view should open the sign up form first if redirecting from /register", (done) => {
    testRedirect("/register", signupTitle, signupSubtitle, signupInfoText).then(
      () => {
        done();
      }
    );
  });
  it("The welcome view should open the sign up form first if redirecting from /sign-up", (done) => {
    testRedirect("/sign-up", signupTitle, signupSubtitle, signupInfoText).then(
      () => {
        done();
      }
    );
  });

  it("The welcome view should open the login form first by default", (done) => {
    testRedirect("/login", loginTitle, loginSubtitle, loginInfoText).then(
      () => {
        done();
      }
    );
  });
});
