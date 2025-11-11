import React, { useEffect, useState } from 'react'
import toast from 'react-hot-toast';
import { useNavigate, useParams } from 'react-router-dom';
import api from '../../services/api';


function CourseLessons() {
  const { courseId } = useParams();
  const [lessons, setLessons] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetchLessons();
  }, [])

  const fetchLessons = async () => {
    try {
      const { data } = await api.get(`/course/getLessons/${courseId}`);
      setLessons(data);
    } catch (error) {
      toast.error(
        "Failed to delete course: " +
        (error?.response?.data?.message ||
          error.response?.statusText ||
          error.message)
      );
    }
  }

  const handleLesson = (courseId) => {
    navigate(`/userLessons/${courseId}`);
  }

  const handleEdit = (lessonId) => {
    navigate(`/editLesson`)
  }

  const handleAdd = () =>{
      navigate()
  }
  
  return (
    <div className="min-h-screen bg-gray-50 p-6">
      <div className="max-w-5xl mx-auto bg-white shadow-xl rounded-2xl p-6">
        <div className='flex justify-between items-center'>
        <h2 className="text-2xl font-bold text-gray-800  pb-3">
          Lesson Details
        </h2>
         <button
         onClick={()=>handleAdd(courseId)}
         className='bg-blue-500 hover:bg-blue-700 text-white text-sm font-semibold px-4 py-2 rounded-lg shadow transition duration-200'
         >+ Add Lesson</button></div>

        {lessons.length > 0 ? (
          <div className="overflow-x-auto">
            <table className="w-full border-collapse bg-white">
              <thead className="bg-blue-100 text-gray-700 uppercase text-sm">
                <tr>
                  <th className="py-3 px-6 text-left">Lesson Id</th>
                  <th className="py-3 px-6 text-left">Lesson Title</th>
                  <th className="py-3 px-6 text-left">Description</th>
                  <th className="py-3 px-6 text-left">Urls</th>
                  <th className="py-3 px-6 text-left">Actions</th>
                </tr>
              </thead>
              <tbody>
                {lessons.map((lesson) => (
                  <tr
                    key={lesson.userId}
                    className="border-b hover:bg-gray-50 transition-colors"
                  >
                    <td className="py-3 px-6">
                      {lesson.lessonId || "N/A"}
                    </td>
                    <td className="py-3 px-6 font-medium text-blue-600">
                      {lesson.lessonTitle}
                    </td>
                    <td className="py-3 px-6 text-gray-600">
                      {lesson.lessonDescription}
                    </td>
                    <td className="py-3 px-6 text-white">
                      <button
                        className='bg-blue-500 hover:bg-blue-700 text-white text-sm font-medium px-4 py-2 rounded-lg transition duration-200'
                        onClick={() => handleLesson(courseId)} >
                        View URLs
                      </button>
                    </td>
                    <td className="py-3 px-6 text-white">
                      <button 
                      className='bg-gray-500 hover:bg-gray-700 text-white text-sm font-medium px-4 py-2 rounded-lg transition duration-200'
                      onClick={() => handleEdit(lessons.lessonId)}>
                        Edit Lesson
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        ) : (
          <div className="text-center py-10 text-gray-500">
            <p className="text-lg">No lessons added</p>
          </div>
        )}
      </div>
    </div>
  )
}

export default CourseLessons