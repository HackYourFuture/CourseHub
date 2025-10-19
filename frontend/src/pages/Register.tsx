import "../App.css";
import { Link, useNavigate } from "react-router";
import { useMutation } from "@tanstack/react-query";
import React, { useState } from "react";
import type { User } from "../types/User";
import { useConfig } from "../ConfigContext";

async function registerRequest(
  credentials: {
    firstName: string;
    lastName: string;
    emailAddress: string;
    password: string;
  },
  backendUrl: string
) {
  try {
    const response = await fetch(`${backendUrl}/register`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(credentials),
      credentials: "include",
    });

    if (!response) {
      throw new Error("Failed to register: Network error");
    }

    if (!response.ok) {
      let errorMessage = `Failed to register: ${response.status}`;
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

export default function Register({ setUser }: { setUser: (user: User | null) => void }) {
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [emailAddress, setEmailAddress] = useState("");
  const [password, setPassword] = useState("");
  const {backendUrl} = useConfig();
  const navigate = useNavigate();

  const { mutate, isPending, error } = useMutation({
    mutationFn: (creds: {
      firstName: string;
      lastName: string;
      emailAddress: string;
      password: string;
    }) => registerRequest(creds, backendUrl),
    onSuccess: (userData: User) => {
      localStorage.setItem("user", JSON.stringify(userData));
      setUser(userData);
      navigate("/");
    },
  });

  function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    mutate({ firstName, lastName, emailAddress, password });
  }

  return (
    <div className="flex justify-center items-center mt-16">
      <div className="bg-white shadow-lg rounded-xl p-8 w-full max-w-md">
        <h2 className="text-2xl font-bold text-center text-blue-600 mb-6">
          Register
        </h2>
        <form className="space-y-5" onSubmit={handleSubmit}>
          <div>
            <label className="block text-gray-700 font-medium mb-2" htmlFor="firstName">
              First Name
            </label>
            <input
              id="firstName"
              type="text"
              className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Enter your first name"
              value={firstName}
              onChange={e => setFirstName(e.target.value)}
              required
            />
          </div>
          <div>
            <label className="block text-gray-700 font-medium mb-2" htmlFor="lastName">
              Last Name
            </label>
            <input
              id="lastName"
              type="text"
              className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Enter your last name"
              value={lastName}
              onChange={e => setLastName(e.target.value)}
              required
            />
          </div>
          <div>
            <label className="block text-gray-700 font-medium mb-2" htmlFor="email">
              Email
            </label>
            <input
              id="email"
              type="email"
              className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Enter your email"
              value={emailAddress}
              onChange={e => setEmailAddress(e.target.value)}
              required
            />
          </div>
          <div>
            <label className="block text-gray-700 font-medium mb-2" htmlFor="password">
              Password
            </label>
            <input
              id="password"
              type="password"
              className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Enter your password"
              value={password}
              onChange={e => setPassword(e.target.value)}
              required
            />
          </div>
          <button
            type="submit"
            disabled={isPending}
            className="w-full bg-blue-600 text-white font-semibold py-2 rounded-lg hover:bg-blue-700 transition duration-200"
          >
            {isPending ? "Registering..." : "Register"}
          </button>
          {error && (
            <div className="text-red-600 text-center mt-2">{error.message}</div>
          )}
          <div className="text-center mt-4">
            Already have an account? <Link to="/login" className="text-blue-600 hover:underline">Login</Link>
          </div>
        </form>
      </div>
    </div>
  );
}
