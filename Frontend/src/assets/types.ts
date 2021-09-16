import { AxiosResponse } from "axios";


export type User = { username: string; password: string; };

export type AuthTokenHeader = { Authorization: string } | {};

export type ApiResponse = number | StringAnyMap | { status: string; statusText: string } | String;

export type ApiErrorResponse = Error & { response: AxiosResponse };

export type Response = ApiResponse | ApiErrorResponse;

export interface StringStringMap {
    [key: string]: string;
}

export interface StringAnyMap {
    [key: string]: any;
}
