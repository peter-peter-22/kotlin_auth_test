import React, {useEffect} from "react";
import * as api from "~/api"

export default function Logout(){
    useEffect(()=>{
        api.logout()
            .then(()=>{console.log("logged out")})
            .catch((res:any)=>{
                if (res?.status===403)
                    console.warn("Already logged out")
                else
                    console.error(res)
            })
    },[])

    return "Logged out"
}