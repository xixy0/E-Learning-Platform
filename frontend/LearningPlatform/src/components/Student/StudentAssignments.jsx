import React, { useEffect, useState } from 'react'
import api from '../../services/api';
import toast from 'react-hot-toast';

function StudentAssignments() {
    const [assignments, setAssignments] = useState([]);
    const [courses, setCourses] = useState([]);
    const fileInputs = React.useRef({});



    useEffect(() => {
        fetchCourses();
    }, [])

    useEffect(() => {
        if (courses.length > 0) {
            fetchAssignments();
        }
    }, [courses]);


    const fetchCourses = async () => {
        try {
            const response = await api.get("/users/viewEnrolledCourses");
            setCourses(response.data);
        } catch (error) {
            toast.error(
                "Failed to fetch courses: " +
                (error?.response?.data?.message ||
                    error.response?.statusText ||
                    error.message)
            );
        }
    };

    const fetchAssignmentFromCourse = async (courseId) => {
        const { data } = await api.get(`/course/getAssignments/${courseId}`);
        return data;
    };

    const fetchAssignments = async () => {
        try {
            const allAssignments = [];
            for (const course of courses) {
                const data = await fetchAssignmentFromCourse(course.courseId);
                allAssignments.push(...data);
            }
            setAssignments(allAssignments);
        } catch (error) {
            toast.error(
                "Failed to fetch assignments: " +
                (error?.response?.data?.message ||
                    error.response?.statusText ||
                    error.message)
            );
        }
    };

    const handleUploadPdf = async (assignmentId) => {
        const file = fileInputs.current[assignmentId]?.files[0];
        if (!file) {
            toast.error("Please select a file before uploading.");
            return;
        }
        const formData = new FormData();
        formData.append("file", file);
        try {

            await api.post(`/assignmentSubmissions/addAssignmentSubmission/${assignmentId}`,
                formData,
                {
                    headers: {
                        "Content-Type": "multipart/form-data",
                    },
                });
            toast.success("Assignment uploaded successfully!");
        } catch (error) {
            toast.error(
                "Failed to upload assignment: " +
                (error?.response?.data?.message ||
                    error.response?.statusText ||
                    error.message)
            );
        }
    }

    const handleDownloadPdf = () => {

    }

    return (
        <React.Fragment>
            <div className="min-h-screen bg-gradient-to-br from-blue-50 to-blue-100 flex flex-col items-center py-10">
                <div className="bg-white shadow-xl rounded-2xl p-8 w-full max-w-4xl border border-gray-200">
                    <h1 className="text-3xl font-semibold text-blue-700 mb-6 text-center">
                        Available Assignaments
                    </h1>

                    {assignments.length > 0 ? (
                        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                            {assignments.map((assignment) => (
                                <div
                                    key={assignment.assignmentId}
                                    className="p-5 border border-gray-200 rounded-xl shadow-sm hover:shadow-lg transition bg-gradient-to-br from-blue-50 to-white"
                                >
                                    <h3 className="text-xl font-semibold text-gray-800 mb-2">
                                        {assignment.assignmentTitle}
                                    </h3>
                                    <p className="text-sm text-gray-600 mb-1">
                                        <span className="font-semibold text-blue-700">Total Marks:</span>{" "}
                                        {assignment.assignmentDescription}
                                    </p>
                                    <p className="text-sm text-gray-600 mb-1">
                                        <span className="font-semibold text-blue-700">Course ID:</span>{" "}
                                        {assignment.courseId}
                                    </p>

                                    <input
                                        type="file"
                                        accept=".pdf"
                                        className="hidden"
                                        ref={(el) => (fileInputs.current[assignment.assignmentId] = el)}
                                    />

                                    <div className="flex flex-col gap-4 mt-4 w-full">
                                        <button
                                            onClick={() => handleDownloadPdf(assignment.assignmentId)}
                                            className="flex-1 bg-green-500 text-white py-2 rounded-lg font-semibold hover:bg-red-600 transition duration-200"
                                        >
                                            Download Assignment Pdf
                                        </button>

                                        <button
                                            onClick={() =>
                                                fileInputs.current[assignment.assignmentId]?.click()
                                            }
                                            className="flex-1 bg-gray-400 text-white py-2 rounded-lg font-semibold hover:bg-gray-500 transition duration-200"
                                        >
                                            Choose File
                                        </button>

                                        <button
                                            onClick={() => handleUploadPdf(assignment.assignmentId)}
                                            className="flex-1 bg-blue-500 text-white py-2 rounded-lg font-semibold hover:bg-blue-600 transition duration-200"
                                        >
                                            Upload Assignment
                                        </button>
                                    </div>
                                </div>
                            ))}
                        </div>
                    ) : (
                        <p className="text-center text-gray-600 mt-4">
                            No assignments available or no enrolled courses.
                        </p>
                    )}
                </div>
            </div>
        </React.Fragment>
    )
}

export default StudentAssignments