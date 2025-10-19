import {Link} from "react-router";
import {User} from "../types/User";

function Header({user, setUser}: {
    user: User | null;
    setUser: (user: User | null) => void;
}) {

    const logout = async () => {
        try {
            await fetch("http://localhost:8080/logout", {
                method: "POST",
                credentials: "include",
            });
        } catch (e) {
            // Optionally handle error
        }
        setUser(null);
        localStorage.removeItem("user");
    };

    return (
        <header className="bg-white shadow-md py-4 px-8 flex items-center justify-between">
            <div className="flex items-center gap-8">
                <Link to="/"
                      className="text-2xl font-bold text-blue-600 hover:text-blue-800 transition">CourseHub</Link>
                <nav className="flex gap-6">
                    <Link to="/" className="text-gray-700 font-medium hover:text-blue-600 transition">All Courses</Link>
                    {user && (
                        <Link to="/my-courses" className="text-gray-700 font-medium hover:text-blue-600 transition">
                            My Courses
                        </Link>
                    )}
                </nav>
            </div>
            <div>
                {user ? (
                    <div className="flex items-center gap-4">
                        <Link to="/profile" className="underline hover:text-blue-600 transition">
                            <span>
                                {user.firstName} {user.lastName} ({user.role})
                            </span>
                        </Link>
                        <button
                            onClick={logout}
                            className="bg-blue-600 text-white px-5 py-2 rounded-full font-semibold shadow hover:bg-blue-700 transition"
                        >
                            Log out
                        </button>
                    </div>
                ) : (
                    <div className="flex items-center gap-4">
                        <Link
                            to="/login"
                            className="bg-blue-600 text-white px-5 py-2 rounded-full font-semibold shadow hover:bg-blue-700 transition"
                        >
                            Log In
                        </Link>
                    </div>
                )}
            </div>
        </header>
    )
}

export default Header
