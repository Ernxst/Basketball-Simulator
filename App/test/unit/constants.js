export const invalidUsername = "";
export const validLoginUsername = "";
export const validLoginPassword = "";
export const validRegisterUsername = "";
export const validRegisterPassword = "";
export const takenUsername = "taken";

export const app = document.createElement("div");
app.id = "app";

export const mockRouter = {
    currentRoute: {
        path: "",
        name: "",
        query: "",
        hash: undefined,
        params: {}
    },
    push({ name, path, params, hash }) {
        this.currentRoute.name = name;
        this.currentRoute.params = params;
        this.currentRoute.path = path;
        this.currentRoute.hash = hash;
    },
    replace({ name, path, params, hash }) {
        this.push({ name, path, params, hash });
    },
    go() {
    }
};