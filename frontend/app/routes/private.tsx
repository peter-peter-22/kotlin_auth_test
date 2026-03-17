import PrivatePage from "~/components/pages/Private"
import type {ApiResponse} from "~/api"
import * as api from "~/api"
import {useLoaderData} from "react-router";

export function meta() {
    return [
        {title: "Private"},
    ];
}

export async function clientLoader(): Promise<ApiResponse> {
    return await api.get_private()
}

export function HydrateFallback() {
    return "loading"
}

export default function Page() {
    const loaderData = useLoaderData<ApiResponse>()
    return <PrivatePage user={loaderData}/>;
}
