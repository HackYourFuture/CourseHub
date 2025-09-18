import "../App.css";
import Header from "../components/Header";
import {Link, useNavigate} from "react-router";
import {useMutation} from "@tanstack/react-query";
import React, {useState} from "react";
import { useConfig } from '../ConfigContext';

async function loginRequest(credentials: { email: string; password: string }) {
    const { backendUrl } = useConfig();
    try {
        const response = await fetch(`${backendUrl}/login`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(credentials),
        });

        if (!response.ok) {
            const data = await response.json().catch(() => ({}));
            throw new Error(data.message || "Login failed");
        }

        return await response.json() as Promise<{ token: string }>;
    } catch {
        throw new Error("Network error");
    }
}

export default function Login() {
    const [emailAddress, setEmailAddress] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate(); // ✅ moved inside component

    const {mutate, isPending, error} = useMutation({
        mutationFn: loginRequest,
        onSuccess: (data) => {
            localStorage.setItem("token", data.token);
            navigate("/dashboard");
        },
    });

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        mutate({email: emailAddress, password});
    };

    return (
        <div className="min-h-screen bg-gray-50">
            <Header/>
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
        </div>
    );
}
