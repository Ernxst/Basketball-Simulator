// @ts-ignore
import axios from 'axios';
import { User } from "../assets/types";


const PORT = 8100;
const BASE_URL = `http://localhost:${PORT}/user/`;


class AuthService {
    /**
     * Log the given user into the backend and store the user in local storage.
     * @param {User} user
     * @returns
     */
    login(user: User) {
        return axios
            .post(BASE_URL + 'login', {
                username: user.username,
                password: user.password
            }, {
                headers: {
                    "Access-Control-Allow-Origin": "*",
                    "Access-Control-Allow-Methods": "POST, GET, OPTIONS, DELETE, PUT",
                    "Access-Control-Max-Age": "1000",
                    "Access-Control-Allow-Headers": "x-requested-with, Content-Type, origin, authorization, accept, client-security-token"
                }
            })
            .then((response: { data: { accessToken: String; }; }) => {
                if (response.data.accessToken) {
                    localStorage.setItem('user', JSON.stringify(response.data));
                }
                return response.data;
            });
    }

    logout(): void {
        localStorage.removeItem('user');
    }

    /**
     *
     * @param {User} user
     */
    register(user: User) {
        return axios.post(BASE_URL + 'register', {
            username: user.username,
            password: user.password
        });
    }
}

export default new AuthService();