import React, {useEffect, useState} from "react";
import {createBrowserRouter, RouterProvider} from 'react-router'
import HomePage from './pages/HomePage'
import Login from './pages/Login'
import type {User} from './types/User';
import Layout from "./components/PageLayout";

function App() {
    const [user, setUser] = useState<User | null>(null);

    const router = createBrowserRouter([
            {
                element: <Layout user={user} setUser={setUser}/>,
                children: [
                    {
                        path: '/',
                        element: <HomePage user={user} setUser={setUser}/>
                    },
                    {
                        path: '/login',
                        element: <Login user={user} setUser={setUser}/>
                    }]
            }

        ])
    ;

    useEffect(() => {
        // Try to load user from localStorage on app start
        const storedUser = localStorage.getItem("user");
        if (storedUser) {
            setUser(JSON.parse(storedUser));
        }
    }, []);

    return (
        <RouterProvider router={router}/>
    );
}

export default App;
