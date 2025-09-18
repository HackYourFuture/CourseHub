import {useEffect, useState} from 'react'
import '../App.css'
import Header from '../components/Header'
import { useConfig } from '../ConfigContext';

function HomePage() {
    const { backendUrl } = useConfig();
    const [courses, setCourses] = useState([])

    useEffect(() => {
        fetch(`${backendUrl}/courses`)
            .then(res => res.json())
            .then(data => setCourses(data.courses))
            .catch(err => console.error('Error fetching courses:', err))
    }, [])

    return (
        <div className="min-h-screen bg-gray-50">
            <Header/>
            <div className="max-w-5xl mx-auto mt-10 pb-5">
                <h1 className="text-4xl font-extrabold mb-8 text-gray-800">All Courses</h1>
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
        </div>
    );
}

export default HomePage
