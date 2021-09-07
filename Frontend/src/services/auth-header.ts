import { AuthTokenHeader } from "../assets/types";


/**
 *
 * @returns {AuthTokenHeader} authorisation token stored in the response header after a successful login.
 */
export default function authHeader(): AuthTokenHeader {
    const user = JSON.parse(localStorage.getItem('user'));

    if (user && user.access_token) {
        return { Authorization: 'Bearer ' + user.access_token };
    } else {
        return {};
    }
}
