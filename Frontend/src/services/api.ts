import axios, { AxiosResponse, Method } from "axios";
import { BASE_URL } from "./endpoints";
import { ApiErrorResponse, ApiResponse, StringAnyMap, StringStringMap } from "../assets/types.js";
import authHeader from "./auth-header";


export async function makeRequest(endpoint: string, method: Method,
                                  params: StringAnyMap = {}, body: any = {},
                                  auth: boolean = false): Promise<ApiResponse> {
    const config: StringAnyMap = {
        method: method, url: endpoint,
        params: params, data: body,
    };
    if (auth === true)
        config["headers"] = authHeader();
    return api.request(config).then(
        (response: AxiosResponse) => {
            return response.data;
        },
        (error: ApiErrorResponse) => {
            return error.response.data;
        }
    );
}

const defaultHeaders: StringStringMap = {
    "Accept": "application/json",
    "Content-Type": "application/json;charset=UTF-8",
};
export const api = axios.create({
    baseURL: BASE_URL,
    timeout: 20000,
    headers: defaultHeaders
});
