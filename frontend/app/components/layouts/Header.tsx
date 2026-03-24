import {NavLink} from "react-router";

export default function Header(){
    return <nav style={{display:"flex",gap:10}}>
        <NavLink to="/" end>
            Home
        </NavLink>
        <NavLink to="/login" end>
            Login
        </NavLink>
        <NavLink to="/register" end>
            Register
        </NavLink>
        <NavLink to="/private" end>
            Private
        </NavLink>
        <NavLink to="/optional" end>
            Optional
        </NavLink>
        <NavLink to="/logout" end>
            Logout
        </NavLink>
    </nav>
}