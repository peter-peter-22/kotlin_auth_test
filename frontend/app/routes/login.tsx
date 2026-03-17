import LoginPage from "~/components/pages/Login"

export function meta() {
    return [
        { title: "Login" },
    ];
}

export default function Page() {
    return <LoginPage />;
}
