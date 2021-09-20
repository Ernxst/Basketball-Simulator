import { AuthTokenHeader } from "@/@types/auth_token_header";
import { getTokenFromStorage } from "./jwt.service";

/**
 *
 * @returns {AuthTokenHeader} authorisation token stored in the response header after a successful login.
 */
export default function authHeader(): AuthTokenHeader {
  const token = getTokenFromStorage();
  if (token) {
    return { Authorization: "Bearer " + token };
  }
  return {};
}
