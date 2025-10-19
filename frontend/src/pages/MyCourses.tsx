import {useEffect, useState} from 'react'
import '../App.css'
import type {Course} from '../types/Course';
import {useConfig} from '../ConfigContext';
import {User} from "../types/User";
import {Navigate} from "react-router";

function MyCourses({user}: { user: User | null }) {
    if (!user) {
        return <Navigate to="/login" replace/>;
    }
    const [courses, setCourses] = useState<Course[]>([]);
    const [error, setError] = useState<string | null>(null);
    const {backendUrl} = useConfig();

    useEffect(() => {
        if (!user) {
            setError("No user authenticated to find courses for");
            return;
        }
        const fetchCourses = async () => {
            try {
                const res = await fetch(`${backendUrl}/students/${user.userId}/courses`, {
                    credentials: "include",
                });
                if (!res.ok) {
                    setError("Error while loading courses");
                    setCourses([]);
                } else {
                    const data = await res.json();
                    setCourses(Array.isArray(data.courses) ? data.courses : []);
                    setError(null);
                }
            } catch (err) {
                console.error("Error fetching My Courses:", err);
                setError("Error while loading courses");
                setCourses([]);
            }
        };
        fetchCourses();
    }, [backendUrl, user]);

    if (error) {
        return (
            <div className="max-w-5xl mx-auto mt-10 pb-5">
                <h1 className="text-4xl font-extrabold mb-8 text-gray-800">My Courses</h1>
                <div className="text-lg text-gray-600">{error}</div>
            </div>
        );
    }

    if (courses.length === 0) {
        return (
            <div className="max-w-5xl mx-auto mt-10 pb-5">
                <h1 className="text-4xl font-extrabold mb-8 text-gray-800">My Courses</h1>
                <div className="text-lg text-gray-600"> You are not enrolled in any courses yet.</div>
            </div>
        );
    }

    return (
        <div className="max-w-5xl mx-auto mt-10 pb-5">
            <h1 className="text-4xl font-extrabold mb-8 text-gray-800">My Courses</h1>
            <div className="overflow-x-auto">
                <table className="table-auto min-w-full bg-white shadow-xl border border-gray-100">
                    <thead>
                    <tr>
                        <th className="px-6 py-4 text-left font-semibold text-gray-600 bg-gray-50 rounded-tl-2xl">Name</th>
                        <th className="px-6 py-4 text-left font-semibold text-gray-600 bg-gray-50">Description</th>
                        <th className="px-6 py-4 text-left font-semibold text-gray-600 bg-gray-50">Instructor</th>
                        <th className="px-6 py-4 text-left font-semibold text-gray-600 bg-gray-50">Start Date</th>
                        <th className="px-6 py-4 text-left font-semibold text-gray-600 bg-gray-50">End Date</th>
                        <th className="px-6 py-4 text-left font-semibold text-gray-600 bg-gray-50 rounded-tr-2xl">Max
                            Students
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    {courses.map((course, idx) => (
                        <tr
                            key={course.id}
                            className={`border-t transition ${idx % 2 === 0 ? 'bg-gray-50' : 'bg-white'} hover:bg-blue-50`}
                        >
                            <td className="px-6 py-4 text-gray-700">{course.name}</td>
                            <td className="px-6 py-4 text-gray-700">{course.description}</td>
                            <td className="px-6 py-4 text-gray-700">{course.instructor}</td>
                            <td className="px-6 py-4 text-gray-700">{course.startDate}</td>
                            <td className="px-6 py-4 text-gray-700">{course.endDate}</td>
                            <td className="px-6 py-4 text-gray-700">{course.maxEnrollments}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default MyCourses
