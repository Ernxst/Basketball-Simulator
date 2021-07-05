import axios from 'axios';

const PORT = 8100;
const BASE_URL = `http://localhost:${PORT}/user/`;

class AuthService {
    login(user) {
        return axios
            .post(BASE_URL + 'login', {
                username: user.username,
                password: user.password
            })
            .then(response => {
                if (response.data.accessToken) {
                    localStorage.setItem('user', JSON.stringify(response.data));
                }
                return response.data;
            });
    }

    logout() {
        localStorage.removeItem('user');
    }

    register(user) {
        return axios.post(BASE_URL + 'register', {
            username: user.username,
            password: user.password
        });
    }
}

export default new AuthService();