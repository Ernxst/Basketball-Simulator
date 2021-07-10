// @ts-ignore
import axios from 'axios';
import { User } from "../assets/types";
import { RELATIVE_USER_LOGIN_ENDPOINT, RELATIVE_USER_REGISTER_ENDPOINT } from "./endpoints";
import { makeRequest } from "./api";


class AuthService {
    /**
     * Log the given user into the backend and store the user in local storage.
     * @param {User} user
     * @returns
     */
    login(user: User) {
        return makeRequest(RELATIVE_USER_LOGIN_ENDPOINT, "POST", {}, {
            username: user.username,
            password: user.password
        })
            .then(
                (response: { accessToken: string }) => {
                    if (response.accessToken) {
                        localStorage.setItem('user', JSON.stringify(response));
                    }
                    return response;
                }
            );
    }

    logout(): void {
        localStorage.removeItem('user');
    }

    /**
     *
     * @param {User} user
     */
    register(user: User) {
        return makeRequest(RELATIVE_USER_REGISTER_ENDPOINT, "POST", {}, {
            username: user.username,
            password: user.password
        });
    }
}

export default new AuthService();