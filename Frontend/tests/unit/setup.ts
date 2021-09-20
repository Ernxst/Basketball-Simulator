import { app } from "./constants";

const windowScroll = window.scrollTo;
const windowAlert = window.alert;
const windowPrompt = window.prompt;
const windowConfirm = window.confirm;

global.beforeAll(async () => {
  jest.setTimeout(45000);
  prompt =
    global.scrollTo =
    scrollTo =
    alert =
    window.scrollTo =
    window.alert =
    window.prompt =
      jest.fn();
  confirm = window.confirm = () => {
    return true;
  };

  window.document.body.appendChild(app);
  const description = document.createElement("meta");
  description.setAttribute("name", "description");
  document.head.appendChild(description);
});

global.afterAll(async () => {
  window.scrollTo = scrollTo = windowScroll;
  window.alert = alert = windowAlert;
  window.prompt = prompt = windowPrompt;
  window.confirm = confirm = windowConfirm;
});

global.beforeEach(() => {});

global.afterEach(() => {});
