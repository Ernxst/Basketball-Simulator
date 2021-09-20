import { AxiosResponse } from "axios";

export type ApiErrorResponse = Error & { response: AxiosResponse };
