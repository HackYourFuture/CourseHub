import '../App.css'
import Header from '../components/Header'
import { Link } from 'react-router'

function Login() {
    return (
        <div className="min-h-screen bg-gray-50">
            <Header />
            <div className="flex justify-center items-center mt-16">
                <div className="bg-white shadow-lg rounded-xl p-8 w-full max-w-md">
                    <h2 className="text-2xl font-bold text-center text-blue-600 mb-6">Log In</h2>
                    <form className="space-y-5">
                        <div>
                            <label className="block text-gray-700 font-medium mb-2" htmlFor="email">Email</label>
                            <input
                                id="email"
                                type="email"
                                className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                                placeholder="Enter your email"
                                required
                            />
                        </div>
                        <div>
                            <label className="block text-gray-700 font-medium mb-2" htmlFor="password">Password</label>
                            <input
                                id="password"
                                type="password"
                                className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                                placeholder="Enter your password"
                                required
                            />
                        </div>
                        <button
                            type="submit"
                            className="w-full bg-blue-600 text-white py-2 rounded-lg font-semibold hover:bg-blue-700 transition"
                        >
                            Log In
                        </button>
                    </form>
                    <div className="text-center mt-6">
                        <span className="text-gray-600">Don't have an account?</span>
                        <Link to="/register" className="text-blue-600 font-medium ml-2 hover:underline">
                            Register
                        </Link>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Login