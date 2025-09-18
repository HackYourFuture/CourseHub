import React, {useEffect, useState} from "react";
import {createBrowserRouter, RouterProvider} from 'react-router';
import AllCourses from './pages/AllCourses';
import Login from './pages/Login';
import Register from './pages/Register';
import type {User} from './types/User';
import Layout from "./components/PageLayout";
import MyCourses from "./pages/MyCourses";

function App() {
    const [user, setUser] = useState<User | null | undefined>(undefined);

    useEffect(() => {
        const storedUser = localStorage.getItem("user");
        if (storedUser) {
            setUser(JSON.parse(storedUser));
        } else {
            setUser(null);
            localStorage.removeItem("user");
        }
    }, []);

    if (user === undefined) {
        return null; // Wait for user to be loaded before rendering
    }

    const router = createBrowserRouter([{
        element: <Layout user={user} setUser={setUser}/>,
        children: [
            {path: '/', element: <AllCourses/>},
            {path: '/my-courses', element: <MyCourses user={user}/>},
            {path: '/login', element: <Login setUser={setUser}/>},
            {path: '/register', element: <Register setUser={setUser}/>}
        ]
    }]);

    return <RouterProvider router={router}/>;
}

export default App;
