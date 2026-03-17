import HomePage from "~/components/pages/Home"
import type {ApiResponse} from "~/api"
import * as api from "~/api"
import {useLoaderData} from "react-router";

export function meta() {
    return [
        {title: "Home"},
    ];
}

export async function clientLoader(): Promise<ApiResponse> {
    return await api.get_public()
}

export function HydrateFallback() {
    return "loading"
}

export default function Page() {
    const loaderData = useLoaderData<ApiResponse>()
    return <HomePage data={loaderData}/>;
}
