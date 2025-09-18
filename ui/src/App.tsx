import React, {useEffect, useState} from "react";
import {createBrowserRouter, RouterProvider} from 'react-router'
import HomePage from './pages/HomePage'
import Login from './pages/Login'
import type {User} from './types/User';

function App() {
    const [user, setUser] = useState<User | null>(null);

    const router = createBrowserRouter([
        {
            path: '/',
            element: <HomePage user={user} setUser={setUser}/>
        },
        {
            path: '/login',
            element: <Login user={user} setUser={setUser}/>
        },
    ]);

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
