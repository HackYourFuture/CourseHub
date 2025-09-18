import "../App.css";
import {Link, useNavigate} from "react-router";
import {useMutation} from "@tanstack/react-query";
import React, {useState} from "react";
import type {User} from "../types/User";
import {useConfig} from '../ConfigContext';

async function loginRequest(credentials: { emailAddress: string; password: string }, backendUrl: string) {
    try {
        const response = await fetch(`${backendUrl}/login`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(credentials),
            credentials: "include", // ensures cookies/session are sent
        });

        if (!response) {
            throw new Error("Failed to login: Network error");
        }

        if (!response.ok) {
            let errorMessage = `Failed to login: ${response.status})`;
            try {
                const data = await response.json();
                if (data && (data.errorMessage || data.message)) {
                    errorMessage = data.errorMessage || data.message;
                }
            } catch {
                // If the response is not JSON, keep the generic error message
            }
            throw new Error(errorMessage);
        }
        return await response.json() as User;
    } catch (err: any) {
        if (err instanceof TypeError) {
            throw new Error("Network error");
        }
        throw err;
    }
}

export default function Login({setUser}: { user: User | null, setUser: (user: User | null) => void }) {
    const [emailAddress, setEmailAddress] = useState("");
    const [password, setPassword] = useState("");
    const {backendUrl} = useConfig();
    const navigate = useNavigate();

    const {mutate, isPending, error} = useMutation({
        mutationFn: (creds: { emailAddress: string; password: string }) => loginRequest(creds, backendUrl),
        onSuccess: (userData) => {
            localStorage.setItem("user", JSON.stringify(userData));
            setUser(userData);
            navigate("/");
        },
    });

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        mutate({emailAddress, password});
    };

    return (
        <div className="flex justify-center items-center mt-16">
            <div className="bg-white shadow-lg rounded-xl p-8 w-full max-w-md">
                <h2 className="text-2xl font-bold text-center text-blue-600 mb-6">
                    Log In
                </h2>
                <form className="space-y-5" onSubmit={handleSubmit}>
                    <div>
                        <label
                            className="block text-gray-700 font-medium mb-2"
                            htmlFor="email"
                        >
                            Email
                        </label>
                        <input
                            id="email"
                            type="email"
                            className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                            placeholder="Enter your email"
                            required
                            value={emailAddress}
                            onChange={(e) => setEmailAddress(e.target.value)}
                        />
                    </div>
                    <div>
                        <label
                            className="block text-gray-700 font-medium mb-2"
                            htmlFor="password"
                        >
                            Password
                        </label>
                        <input
                            id="password"
                            type="password"
                            className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                            placeholder="Enter your password"
                            required
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                    </div>
                    {error && (
                        <div className="text-red-600 text-center">
                            {(error as Error).message}
                        </div>
                    )}
                    <button
                        type="submit"
                        disabled={isPending}
                        className="w-full bg-blue-600 text-white py-2 rounded-lg font-semibold
                hover:bg-blue-700 transition disabled:opacity-50 disabled:cursor-not-allowed"
                    >
                        {isPending ? "Logging in..." : "Log In"}
                    </button>
                </form>
                <div className="text-center mt-6">
                    <span className="text-gray-600">Don't have an account?</span>
                    <Link
                        to="/register"
                        className="text-blue-600 font-medium ml-2 hover:underline"
                    >
                        Register
                    </Link>
                </div>
            </div>
        </div>
    );
}
