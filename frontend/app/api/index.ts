import {client} from "~/lib/axios";

export type ApiResponse = string

async function post_login(username: string, password: string) {
    await client.post("/api/login", {username, password});
}

async function post_register(username: string, password: string) {
    await client.post("/api/register", {username, password});
}

async function get_optional(): Promise<ApiResponse> {
    const res = await client.get("/api/optional");
    return res.data
}

async function get_public(): Promise<ApiResponse> {
    const res = await client.get("/api/public");
    return res.data
}

async function get_private(): Promise<ApiResponse> {
    const res = await client.post("/api/private");
    return res.data
}

export { post_login, get_public, get_private, get_optional,post_register };