import { AxiosResponse } from "axios";


export type User = { username: string; password: string; };

export type AuthTokenHeader = { Authorization: string } | {};

export type ApiResponse = { [key: string]: string } | { status: string; statusText: string };

export type ApiErrorResponse = Error & { response: AxiosResponse };

export interface StringStringMap {
    [key: string]: string;
}

export interface StringAnyMap {
    [key: string]: any;
}
