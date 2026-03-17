import React from "react";
import * as api from "~/api"

export default function Login(){
    const onSubmit= async (e: React.SubmitEvent<HTMLFormElement>)=>{
        e.preventDefault();
        const username:string=e.target.username.value
        const password:string=e.target.password.value
        console.log("Logging in")
        await api.post_login(username,password)
        console.log("Successfully logged in")
    }

    return <form onSubmit={onSubmit}>
        <input type="text" name="username" placeholder="Username" required />
        <input type="password" name="password" placeholder="Password" required />
        <button type="submit">Login</button>
    </form>
}