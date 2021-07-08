export type User = { username: string; password: string; };

export type AuthTokenHeader = { Authorization: string } | {};

export interface StringStringMap {
    [key: string]: string;
}

export interface StringAnyMap {
    [key: string]: any;
}
