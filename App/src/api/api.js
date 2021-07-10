import axios from "axios";


const PORT = 8100;
const BASE_URL = `http://localhost:${PORT}/`;

async function makeHttpRequest(URL, method, params = {}) {
    const options = {
        method: method.toUpperCase(),
        url: URL,
        baseURL: BASE_URL,
        headers: {
            "Access-Control-Allow-Origin": "*",
            "Access-Control-Allow-Methods": "POST, GET, OPTIONS, DELETE, PUT",
            "Access-Control-Max-Age": "1000",
            "Access-Control-Allow-Headers": "x-requested-with, Content-Type, origin, authorization, accept, client-security-token",
            Accept: "application/json",
            "Content-Type": "application/json;charset=UTF-8",
        },
        data: params,
    };
    const response = await axios(options);
    if (response && response.status === 200) {
        return await response.data;
    }
    return {
        status: response.status,
        statusText: response.statusText,
    };
}

const api = {};

export default api;