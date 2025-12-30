import React, { useEffect, useState } from 'react'
import { useAuth } from '../../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import api from '../../services/api';
import toast from 'react-hot-toast';



function InstructorCourse() {
    const [enrolledCourses, setEnrolledCourses] = useState([]);
    const [filteredCourses, setFilteredCourses] = useState([]);
    const [searchData, setSearchData] = useState("");
    const navigate = useNavigate();
    

    useEffect(() => {
        fetchCourses();
    }, []);

    const fetchCourses = async () => {
        try {
            const response = await api.get(`/users/viewCourses`);
            setEnrolledCourses(response.data);
            setFilteredCourses(response.data);
        } catch (error) {
            console.error("Error fetching courses:", error);
            toast.error("Failed to fetch courses");
            setEnrolledCourses([]);
            setFilteredCourses([]);
        }
    };

    const filterData = () => {
        const newData = enrolledCourses.filter(
            (course) =>
                course.courseTitle.toLowerCase().includes(searchData.toLowerCase()) ||
                course.courseCategory.toLowerCase().includes(searchData.toLowerCase()) ||
                course.instructorName.toLowerCase().includes(searchData.toLowerCase())
        );
        setFilteredCourses(newData);
    };

    const handleDelete = async (courseId) => {
        try {
            await api.delete(`/course/deleteCourse/${courseId}`)
            fetchCourses();
        } catch (error) {
            toast.error(
                "Failed to delete course: " +
                (error?.response?.data?.message ||
                    error.response?.statusText ||
                    error.message)
            );
        }
    };

    const handleAdd = () =>{
     navigate(`/addCourse`)   
    }

    const handleDetails = (courseId) => {
        navigate(`/courseDetails/${courseId}/overview`)
    }

    return (
        <div className=" min-h-screen bg-gradient-to-br from-red-50 to-blue-100  flex flex-col items-center py-10 px-4">
            <div className="relative bg-white shadow-xl rounded-2xl p-8 w-full max-w-6xl border border-gray-200">
                <h1 className="text-3xl font-semibold text-blue-700 text-center mb-8">
                    Your Courses
                </h1>
                <button
                    onClick={() => handleAdd()}
                    className="absolute top-8 right-8 bg-blue-500 hover:bg-blue-700 text-white text-sm font-semibold px-4 py-2 rounded-md shadow transition duration-200"
                >
                    Add Course
                </button>

                <div className="flex flex-col items-center justify-center w-full max-w-4xl mx-auto sm:flex-row  gap-3 mb-8">
                    <input
                        type="text"
                        value={searchData}
                        name="search"
                        placeholder="Search by course name, category, or instructor"
                        onChange={(e) => setSearchData(e.target.value)}
                        className="flex-1 bg-white text-gray-800 px-4 py-2 border border-gray-300 rounded-lg shadow-sm focus:ring-2 focus:ring-blue-400 focus:outline-none"
                    />
                    <button
                        onClick={filterData}
                        className="px-6 py-2 bg-blue-600 text-white font-semibold rounded-lg shadow hover:bg-blue-700 transition duration-200"
                    >
                        Search
                    </button>
                </div>


                {filteredCourses.length > 0 ? (
                    <div className="grid md:grid-cols-2 gap-6">
                        {filteredCourses.map((course) => (
                            <div
                                key={course.courseId}
                                className="p-6 bg-gray-50 border border-gray-200 rounded-xl shadow hover:shadow-md transition"
                            >
                                <h3 className="text-xl font-semibold text-blue-700 mb-2">
                                    {course.courseTitle}
                                </h3>
                                <p className="text-gray-700 mb-1">
                                    <span className="font-medium">Category:</span>{" "}
                                    {course.courseCategory}
                                </p>
                                <p className="text-gray-700 mb-1">
                                    <span className="font-medium">Instructor:</span>{" "}
                                    {course.instructorName}
                                </p>
                                <p className="text-gray-700 mb-3">
                                    <span className="font-medium">Students Enrolled:</span>{" "}
                                    {course.numberOfStudentsEnrolled}
                                </p>
                                <p className="text-gray-600 mb-4">{course.courseDescription}</p>

                                <div className="flex justify-between gap-4 mt-4">
                                    <button
                                        onClick={() => handleDetails(course.courseId)}
                                        className="flex-1 bg-blue-500 text-white py-2 rounded-lg font-semibold hover:bg-blue-600 transition duration-200"
                                    >
                                        Details
                                    </button>
                                    <button
                                        onClick={() => handleDelete(course.courseId)}
                                        className="flex-1 bg-red-500 text-white py-2 rounded-lg font-semibold hover:bg-red-600 transition duration-200"
                                    >
                                        Delete Course
                                    </button>

                                </div>

                            </div>
                        ))}
                    </div>
                ) : (
                    <p className="text-center text-gray-600 mt-6">
                        Not enrolled in any courses.
                    </p>
                )}

            </div>
        </div>
    )
}

export default InstructorCourse