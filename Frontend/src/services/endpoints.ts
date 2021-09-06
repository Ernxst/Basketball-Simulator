const getBaseUrl = (environment: string): string => {
    const PORT: number = 8100;
    return (environment == 'production') ? 'https://basketball-simulator-app.herokuapp.com/' :
        `http://localhost:${PORT}/`;
};
export const BASE_URL: string = getBaseUrl('development');

export const RELATIVE_USER_ENDPOINT: string = "user/";
export const RELATIVE_USER_LOGIN_ENDPOINT: string = RELATIVE_USER_ENDPOINT + "login";
export const RELATIVE_USER_REGISTER_ENDPOINT: string = RELATIVE_USER_ENDPOINT + "register";

export const FULL_USER_ENDPOINT: string = BASE_URL + RELATIVE_USER_ENDPOINT;
export const FULL_USER_LOGIN_ENDPOINT: string = BASE_URL + RELATIVE_USER_LOGIN_ENDPOINT;
export const FULL_USER_REGISTER_ENDPOINT: string = BASE_URL + RELATIVE_USER_REGISTER_ENDPOINT;
