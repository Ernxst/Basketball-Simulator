import MockAdapter from "axios-mock-adapter";
import { api } from "../../src/services/api";
import { FULL_USER_LOGIN_ENDPOINT } from "../../src/services/endpoints";
import {
    incorrectPassword,
    invalidLoginUsername,
    validLoginPassword,
    validLoginUsername, validRegisterPassword,
    validRegisterUsername
} from "./constants.js";


export const mockRouter = {
    currentRoute: {
        path: "",
        name: "",
        query: "",
        hash: "",
        params: {}
    },
    push({ name, path, params, hash }: { name: string, path: string, params: string, hash: string }) {
        this.currentRoute.name = name;
        this.currentRoute.params = params;
        this.currentRoute.path = path;
        this.currentRoute.hash = hash;
    },
    replace({ name, path, params, hash }: { name: string, path: string, params: string, hash: string }) {
        this.push({ name, path, params, hash });
    },
    go() {
    }
};

export const mockApi = new MockAdapter(api, {});

mockApi.onPost(FULL_USER_LOGIN_ENDPOINT,
    { username: validLoginUsername, password: validLoginPassword })
    .reply(201, {
        username: validLoginUsername, token: "aaaa"
    });

mockApi.onPost(FULL_USER_LOGIN_ENDPOINT,
    { username: validRegisterUsername, password: validRegisterPassword })
    .reply(201, {
        username: validRegisterUsername, token: "aaaa"
    });

mockApi.onPost(FULL_USER_LOGIN_ENDPOINT,
    { username: validLoginUsername, password: incorrectPassword })
    .reply(401, {
        error: "Login failed, either your username or password was incorrect, please try again.",
    });

mockApi.onPost(FULL_USER_LOGIN_ENDPOINT,
    { username: invalidLoginUsername, password: incorrectPassword })
    .reply(401, {
        error: `Login failed, the username ${invalidLoginUsername} does not exist; are you trying to sign up?`,
    });

