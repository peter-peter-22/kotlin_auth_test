import OptionalPage from "~/components/pages/Optional"
import type {ApiResponse} from "~/api"
import * as api from "~/api"
import {useLoaderData} from "react-router";

export function meta() {
    return [
        {title: "Optional"},
    ];
}

export async function clientLoader(): Promise<ApiResponse> {
    return await api.get_optional()
}

export function HydrateFallback() {
    return "loading"
}

export default function Page() {
    const loaderData = useLoaderData<ApiResponse>()
    return <OptionalPage user={loaderData}/>;
}
