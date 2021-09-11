const getBaseUrl = (environment: string): string => {
    const PORT: number = 8100;
    return (environment === 'production') ? 'https://basketball-simulator-web.herokuapp.com/' :
        `http://localhost:${PORT}/`;
};
export const BASE_URL: string = getBaseUrl(process.env.NODE_ENV);

export const RELATIVE_USER_ENDPOINT: string = "users/";
export const RELATIVE_USER_LOGIN_ENDPOINT: string = RELATIVE_USER_ENDPOINT + "login";
export const RELATIVE_USER_REGISTER_ENDPOINT: string = RELATIVE_USER_ENDPOINT + "register";

export const FULL_USER_ENDPOINT: string = BASE_URL + RELATIVE_USER_ENDPOINT;
export const FULL_USER_LOGIN_ENDPOINT: string = BASE_URL + RELATIVE_USER_LOGIN_ENDPOINT;
export const FULL_USER_REGISTER_ENDPOINT: string = BASE_URL + RELATIVE_USER_REGISTER_ENDPOINT;

export const RELATIVE_CONSTANTS_ENDPOINT: string = "constants/";
export const RELATIVE_TEAM_NAMES_ENDPOINT: string = RELATIVE_CONSTANTS_ENDPOINT + "team_names";
export const RELATIVE_TEAM_STATES_ENDPOINT: string = RELATIVE_CONSTANTS_ENDPOINT + "team_states";
export const RELATIVE_MIN_TEAMS_ENDPOINT: string = RELATIVE_CONSTANTS_ENDPOINT + "min_teams";
export const RELATIVE_MAX_TEAMS_ENDPOINT: string = RELATIVE_CONSTANTS_ENDPOINT + "max_teams";
export const RELATIVE_MAX_LEAGUES_ENDPOINT: string = RELATIVE_CONSTANTS_ENDPOINT + "max_leagues";
