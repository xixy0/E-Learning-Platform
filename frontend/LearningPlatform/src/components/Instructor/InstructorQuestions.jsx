import React, { useEffect, useState } from 'react'
import toast from 'react-hot-toast';
import { useNavigate, useParams } from 'react-router-dom';
import api from '../../services/api';


function InstructorQuestions() {
    const { quizId } = useParams();
    const [questions, setQuestions] = useState([]);
    const navigate = useNavigate();


    useEffect(() => {
        fetchQuestions();
    }, []);

    const fetchQuestions = async () => {
        try {
            const response = await api.get(`/quiz/getQuestions/${quizId}`);
            setQuestions(response.data);
        } catch (error) {
            console.error("Error fetching questions:", error);
            toast.error("Failed to fetch questions");
            setQuestions([]);
        }
    };


    const handleDelete = async (questionId) => {
        try {
            await api.delete(`/questions/deleteQuestion/${questionId}`);
            fetchQuestions();
        } catch (error) {
            console.error("Error deleting question:", error);
            toast.error("Error deleting question");
        }
    }

    const handleEdit = (questionId) => {
        navigate(`/editQuestion/${questionId}`);
    }

    return (
        <div className="min-h-screen bg-gray-50 p-6">
            <div className="max-w-5xl mx-auto bg-white shadow-xl rounded-2xl p-6">
                <div className='flex justify-between items-center'>
                    <h2 className="text-2xl font-bold text-gray-800  pb-3">
                        Question Details
                    </h2>
                </div>

                {questions.length > 0 ? (
                    <div className="overflow-x-auto">
                        <table className="w-full border-collapse bg-white">
                            <thead className="bg-blue-100 text-gray-700 uppercase text-sm">
                                <tr>
                                    <th className="py-3 px-6 text-left">Question Id</th>
                                    <th className="py-3 px-6 text-middle w-1/3">Question</th>
                                    <th className="py-3 px-6 text-left">Options</th>
                                    <th className="py-3 px-6 text-left">Answer</th>
                                    <th className="py-3 px-6 text-middle">Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {questions.map((question) => (
                                    <tr
                                        key={question.questionId}
                                        className="border-b hover:bg-gray-50 transition-colors"
                                    >
                                        <td className="py-3 px-6">
                                            {question.questionId || "N/A"}
                                        </td>
                                        <td className="py-3 px-6 text-sm">
                                            {question.questionText || "N/A"}
                                        </td>
                                        <td className="flex-col py-3 px-6 text-sm">
                                            <p>Option 1 :</p> <p className=' font-medium'>{question.option1}</p>
                                            <p>Option 2 :</p> <p className=' font-medium'>{question.option2}</p>
                                            <p>Option 3 :</p> <p className=' font-medium'>{question.option3}</p>
                                            <p>Option 4 :</p> <p className=' font-medium'>{question.option4}</p>
                                            
                                        </td>

                                        <td className="py-3 px-6 text-blue-600 font-medium">
                                            {question.correctAnswer}
                                        </td>
                                        <td className="flex gap-1 py-3 px-6 text-white">
                                            <button
                                                className='bg-red-500 hover:bg-red-700 text-white text-xs font-medium px-2 py-1.5 rounded-md transition duration-200'
                                                onClick={() => handleDelete(question.questionId)} >
                                                Delete
                                            </button>
                                            <button
                                                className='bg-gray-500 hover:bg-gray-700 text-white text-xs font-medium px-1 py-1,5 rounded-md transition duration-200'
                                                onClick={() => handleEdit(question.questionId)}>
                                                Edit Question
                                            </button>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                ) : (
                    <div className="text-center py-10 text-gray-500">
                        <p className="text-lg">No questions added</p>
                    </div>
                )}
            </div>
        </div>
    )
}

export default InstructorQuestions