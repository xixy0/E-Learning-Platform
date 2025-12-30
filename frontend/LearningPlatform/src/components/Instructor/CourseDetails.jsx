import React, { useEffect, useState } from 'react'
import { Link, useNavigate, useParams } from 'react-router-dom';
import api from '../../services/api';

function CourseDetails() {
  const { courseId } = useParams();
  const [details, setDetails] = useState({});
  const navigate = useNavigate();

  useEffect(() => {
    fetchCourseDetails();
  }, [])

  const fetchCourseDetails = async () => {
    try {
      const { data } = await api.get(`/course/getCourseById/${courseId}`)
      setDetails(data);
    } catch (error) {
      toast.error(
        "Failed to get course: " +
        (error?.response?.data?.message ||
          error.response?.statusText ||
          error.message)
      );
    }
  }

  const handleEdit = () => {
    navigate(`/editCourse/${courseId}`)
  }

  return (
    <React.Fragment>
      <div className="min-h-screen bg-gradient-to-br from-blue-50 to-blue-100 flex flex-col items-center py-10">
        <div className="bg-white shadow-xl rounded-2xl p-8 w-full max-w-md border border-gray-200">
          <h1 className="text-3xl  font-semibold text-blue-700 mb-6 text-center">
            Course Details
          </h1>

          <div className="space-y-4 text-gray-700">
            <div className="flex justify-between border-b pb-2">
              <span className="font-semibold text-blue-700">Course ID:</span>
              <span>{courseId}</span>
            </div>

            <div className="flex justify-between border-b pb-2">
              <span className="font-semibold text-blue-700">Title:</span>
              <span>
                {details.courseTitle}
              </span>
            </div>

            <div className="flex justify-between border-b pb-2">
              <span className="font-semibold text-blue-700">Description:</span>
              <span>
                {details.courseDescription}
              </span>
            </div>

            <div className="flex justify-between border-b pb-2">
              <span className="font-semibold text-blue-700">Category:</span>
              <span>
                {details.courseCategory}
              </span>
            </div>

            <div className="flex justify-between border-b pb-2">
              <span className="font-semibold text-blue-700">Instructor:</span>
              <span>
                {details.instructorName}
              </span>
            </div>

            <div className="flex justify-between border-b pb-2">
              <span className="font-semibold text-blue-700">Number of Enrollements:</span>
              <span>
                {details.numberOfStudentsEnrolled}
              </span>
            </div>
          </div>
          <div className="mt-8 text-center">
            <button
              onClick={() => handleEdit(courseId)}
              className='bg-blue-500 font-medium text-sm text-white rounded-lg px-4 py-2 hover:bg-blue-700 transition'
            >
              Update Course
            </button>
          </div>
        </div>
      </div>
    </React.Fragment>
  )
}

export default CourseDetails