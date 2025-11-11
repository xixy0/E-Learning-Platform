import React, { useEffect, useState } from 'react'
import toast from 'react-hot-toast';
import { useParams } from 'react-router-dom';
import api from '../../services/api';

function CourseStudentEnrollments() {
  const { courseId } = useParams();
  const [enrolled, setEnrolled] = useState([]);

  useEffect(() => {
    fetchEnrolledStudents();
  },[])

  const fetchEnrolledStudents = async () => {
    try {
      const { data } = await api.get(`/course/enrollments/${courseId}`);
      setEnrolled(data);
    } catch (error) {
      toast.error(
        "Failed to fetch enrollments: " +
        (error?.response?.data?.message ||
          error.response?.statusText ||
          error.message)
      );
    }
  }

  
  return (
     <div className="min-h-screen bg-gray-50 p-6">
      <div className="max-w-5xl mx-auto bg-white shadow-xl rounded-2xl p-6">
        <h2 className="text-2xl font-bold text-gray-800 mb-6 border-b pb-3">
          Student Details
        </h2>

        {enrolled.length > 0 ? (
          <div className="overflow-x-auto">
            <table className="w-full border-collapse bg-white">
              <thead className="bg-blue-100 text-gray-700 uppercase text-sm">
                <tr>
                  <th className="py-3 px-6 text-left">Student Id</th>
                  <th className="py-3 px-6 text-left">Student Name</th>
                  <th className="py-3 px-6 text-left">Email</th>
                </tr>
              </thead>
              <tbody>
                {enrolled.map((student) => (
                  <tr
                    key={student.userId}
                    className="border-b hover:bg-gray-50 transition-colors"
                  >
                    <td className="py-3 px-6">
                      {student.userId || "N/A"}
                    </td>
                    <td className="py-3 px-6 font-medium text-blue-600">
                      {student.firstName}{" "}
                      {student.middleName ??" "}{" "}
                      {student.lastName}
                    </td>
                    <td className="py-3 px-6 text-gray-600">
                      {student.email}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        ) : (
          <div className="text-center py-10 text-gray-500">
            <p className="text-lg">No students enrolled</p>
          </div>
        )}
      </div>
    </div>
  )
}

export default CourseStudentEnrollments