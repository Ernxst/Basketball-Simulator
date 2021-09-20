import { ApiErrorResponse } from "@/@types/api_response";
import { StringAnyMap } from "@/@types/maps";
import axios, { AxiosResponse, Method } from "axios";
import authHeader from "./auth-header";
import { BASE_URL } from "./endpoints";

type fnParams = {
  endpoint: string;
  method: Method;
  params?: StringAnyMap;
  body?: any;
  auth?: boolean;
};

export async function makeRequest<Type>({
  endpoint,
  method,
  params = {},
  body = {},
  auth = true,
}: fnParams): Promise<Type> {
  const config: StringAnyMap = {
    method: method,
    url: endpoint,
    params: params,
    data: body,
  };
  if (auth === true) config["headers"] = authHeader();
  return api.request(config).then(
    (response: AxiosResponse) => {
      return response.data;
    },
    (error: ApiErrorResponse) => {
      return error.response.data;
    }
  );
}

export const api = axios.create({
  baseURL: BASE_URL,
  timeout: 20000,
  headers: {
    Accept: "application/json",
    "Content-Type": "application/json;charset=UTF-8",
  },
});
