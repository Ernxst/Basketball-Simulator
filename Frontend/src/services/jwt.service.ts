import jwtDecode, { JwtPayload } from "jwt-decode";


const TOKEN_KEY_NAME = "token";

const decodeToken = (token: string) => {
    return jwtDecode<JwtPayload>(token);
}

export const getTokenFromStorage = () : string => {
    return localStorage.getItem(TOKEN_KEY_NAME);
}

export const setJwtToken = (token: string) : void => {
    localStorage.setItem(TOKEN_KEY_NAME, token);
}

export const removeJwtToken = () : void => {
    localStorage.removeItem(TOKEN_KEY_NAME)
}

export const getUsernameFromToken = (token : string) : string => {
    const decoded = decodeToken(token);
    console.log(decoded)
    return decoded.sub;
}
