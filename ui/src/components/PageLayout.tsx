import {Outlet} from "react-router";
import Header from "./Header";
import {User} from "../types/User";

function Layout({user, setUser}: { user: User | null, setUser: (user: User | null) => void }) {
    return (
        <div className="min-h-screen bg-gray-50">
            <Header user={user} setUser={setUser}/>
            <main>
                <Outlet/>
            </main>
        </div>
    )
}

export default Layout