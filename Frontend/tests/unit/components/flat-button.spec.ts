import FlatButton from "@/components/widgets/buttons/flat-button.vue";
import { mount } from "@vue/test-utils";
import { app } from "../constants";
import { mockRouter } from "../mocks";

describe("Flat Button Component", () => {
  const getWrapper = (type: string, text: string) => {
    // @ts-ignore
    const wrapper = mount(FlatButton, {
      attachTo: app,
      props: {
        type,
        text,
      },
      global: {
        mocks: {
          $route: {
            path: "/welcome",
            name: "welcome",
          },
          $router: mockRouter,
        },
      },
    });
    expect(wrapper.text()).toEqual(text);
    if (type.length > 0) {
      expect(wrapper.attributes()["type"]).toEqual(type);
    }
    return wrapper;
  };

  it("Type and text", async () => {
    const wrapper = getWrapper("button", "TEST BUTTON");
  });

  it("No Type and text", async () => {
    const wrapper = getWrapper("", "TEST BUTTON");
  });

  it("Type and no text", async () => {
    const wrapper = getWrapper("button", "");
  });

  it("No type and no text", async () => {
    const wrapper = getWrapper("", "");
    wrapper.unmount();
  });

  it("Enable and disable test", async () => {
    const wrapper = getWrapper("button", "TEST BUTTON");
    wrapper.vm.disable();
    expect(wrapper.classes()).toContain("disabled");
    expect(wrapper.classes().length).toEqual(4);
    wrapper.vm.enable();
    expect(wrapper.classes().length).toEqual(3);
  });
});
