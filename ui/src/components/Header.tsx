import {Link} from 'react-router'

function Header() {
    return (
        <header className="bg-white shadow-md py-4 px-8 flex items-center justify-between">
            <div className="flex items-center gap-8">
                <Link to="/"
                      className="text-2xl font-bold text-blue-600 hover:text-blue-800 transition">CourseHub</Link>
                <nav className="flex gap-6">
                    <Link to="/" className="text-gray-700 font-medium hover:text-blue-600 transition">All Courses</Link>
                    <Link to="/my-courses" className="text-gray-700 font-medium hover:text-blue-600 transition">My
                        Courses</Link>
                </nav>
            </div>
            <Link
                to="/login"
                className="bg-blue-600 text-white px-5 py-2 rounded-full font-semibold shadow hover:bg-blue-700 transition"
            >
                Log In
            </Link>
        </header>
    )
}

export default Header
