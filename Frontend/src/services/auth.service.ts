import { User } from "@/@types/user";
import { makeRequest } from "./api";
import {
  RELATIVE_USER_LOGIN_ENDPOINT,
  RELATIVE_USER_REGISTER_ENDPOINT
} from "./endpoints";

type AuthResponse = {
  username?: string;
  token?: string;
  error?: string;
};

const authenticate = async (endpoint: string, user: User): Promise<string> => {
  const authResponse: AuthResponse = await makeRequest<{
    username: string;
    token: string;
  }>({
    endpoint: endpoint,
    method: "POST",
    body: {
      username: user.username,
      password: user.password,
    },
    auth: false,
  });
  if (authResponse.token) return authResponse.token;
  return Promise.reject(authResponse.error);
};
class AuthService {
  async login(user: User): Promise<string> {
    return authenticate(RELATIVE_USER_LOGIN_ENDPOINT, user);
  }

  logout(): void {}

  async register(user: User): Promise<string> {
    return authenticate(RELATIVE_USER_REGISTER_ENDPOINT, user);
  }
}

export default new AuthService();
