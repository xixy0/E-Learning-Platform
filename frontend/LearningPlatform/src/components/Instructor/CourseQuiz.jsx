import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom';
import api from '../../services/api';
import toast from 'react-hot-toast';

function CourseQuiz() {
  const { courseId } = useParams();
  const [quizez, setQuizez] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetchQuiz();
  }, [])

  const fetchQuiz = async () => {
    try {
      const { data } = await api.get(`/course/getAllQuiz/${courseId}`);
      setQuizez(data);
    } catch (error) {
      toast.error(
        "Failed to delete course: " +
        (error?.response?.data?.message ||
          error.response?.statusText ||
          error.message)
      );
    }
  }

  const handleEdit = (quizId) => {
    navigate(`/editQuiz/${quizId}`)
  }

  const handleAdd = () => {
    navigate(`/addQuiz/${courseId}`)
  }
  const handleAddQuestion = (quizId) => {
    navigate(`/addQuestions/${quizId}`)
  }

  const handleViewQuestion = (quizId) =>{
    navigate(`/viewQuestions/${quizId}`)
  }

  return (
    <div className="min-h-screen bg-gray-50 p-6">
      <div className="max-w-5xl mx-auto bg-white shadow-xl rounded-2xl p-6">
        <div className='flex justify-between items-center'>
          <h2 className="text-2xl font-bold text-gray-800 mb-6 pb-3">
            Quiz Details
          </h2>
          <button
            onClick={() => handleAdd()}
            className='bg-blue-500 hover:bg-blue-700 text-white text-sm font-semibold px-4 py-2 rounded-lg shadow transition duration-200'
          >+ Add Quiz</button></div>

        {quizez.length > 0 ? (
          <div className="overflow-x-auto">
            <table className="w-full border-collapse bg-white">
              <thead className="bg-blue-100 text-gray-700 uppercase text-sm">
                <tr>
                  <th className="py-3 px-6 text-left">Quiz Id</th>
                  <th className="py-3 px-6 text-left">Quiz Title</th>
                  <th className="py-3 px-6 text-left">Total Questions</th>
                  <th className="py-3 px-6 text-left">Timestamp</th>
                  <th className="py-3 px-6 text-middle">Actions</th>
                  
                </tr>
              </thead>
              <tbody>
                {quizez.map((quiz) => (
                  <tr
                    key={quiz.quizId}
                    className="border-b hover:bg-gray-50 transition-colors"
                  >
                    <td className="py-3 px-6">
                      {quiz.quizId || "N/A"}
                    </td>

                    <td className="py-3 px-6 font-medium text-blue-600">
                      {quiz.quizTitle}
                    </td>

                    <td className="py-3 px-6 text-gray-600">
                      {quiz.totalMarks}
                    </td>

                    <td className="py-3 px-6 text-gray-600">
                      {new Date(quiz.timestamp).toLocaleDateString()}
                    </td>

                    <td className="flex py-3 gap-1 px-6 text-white">
                      <button
                        className='bg-gray-500 hover:bg-gray-700 text-white text-xs font-medium px-3 py-1.5 rounded-md transition duration-200'
                        onClick={() => handleEdit(quiz.quizId)}>
                        Edit Quiz
                      </button>
                        <button
                        className='bg-blue-500 hover:bg-blue-700 text-white text-xs font-medium px-3 py-1.5 rounded-md transition duration-200'
                        onClick={() => handleAddQuestion(quiz.quizId)}>
                        Add Questions
                      </button>
                        <button
                        className='bg-green-500 hover:bg-green-700 text-white text-xs font-medium px-3 py-1.5 rounded-md
                         transition duration-200'
                        onClick={() => handleViewQuestion(quiz.quizId)}>
                        View Questions
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

export default CourseQuiz