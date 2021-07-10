import axios, { AxiosResponse, Method } from "axios";
import { BASE_URL } from "./endpoints";
import { ApiResponse } from "../assets/types.js";


export async function makeRequest(endpoint: string, method: Method,
                                  params: any = {}, body: any = {}): Promise<ApiResponse> {
    return api.request({
        method: method,
        url: endpoint,
        params,
        data: body,
    }).then(
        (response: AxiosResponse) => {
            if (response && response.status === 200) {
                return response.data;
            }
            return {
                status: response.status,
                statusText: response.statusText,
            };
        },
        (error: Error) => {
            return {
                status: 500,
                statusText: error.message
            }
        }
    );
}

export const api = axios.create({
    baseURL: BASE_URL,
    timeout: 1000,
    headers: {
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Methods": "POST, GET, OPTIONS, DELETE, PUT",
        "Access-Control-Max-Age": "1000",
        "Access-Control-Allow-Headers": "x-requested-with, Content-Type, origin, authorization, accept, client-security-token",
        Accept: "application/json",
        "Content-Type": "application/json;charset=UTF-8",
    },
});