import React, { useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom';
import api from '../../services/api';
import toast from 'react-hot-toast';

function AddQuestions() {
    const navigate = useNavigate();
    const { quizId } = useParams();


    const [question, setQuestion] = useState({
        quizId: `${quizId}`,
        questionText: "",
        option1: "",
        option2: "",
        option3: "",
        option4: "",
        correctAnswer: ""
    });


    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setQuestion({ ...question, [name]: value });
    };



    const handleAddQuestion = async () => {
        try {
            const payload = { ...question };

            await api.post(
                `/questions/addQuestion`,
                payload);

            toast.success("Question added");
            navigate(-1, { state: { refresh: true } });
            setQuestion({
                quizId: `${quizId}`,
                questionText: "",
                option1: "",
                option2: "",
                option3: "",
                option4: "",
                correctAnswer: ""

            });
        } catch (err) {
            console.error("Failed to add Question:", err);
            toast.error("Failed to add Question");
        }
    };


    return (
        <div className="flex justify-center items-center  min-h-screen  bg-gradient-to-br from-red-50 to-blue-100  p-5">
            <div className=" w-full max-w-4xl bg-white shadow-md rounded-lg p-8">
                <h3 className="text-2xl font-semibold text-blue-600 mb-6 text-center">
                    Add Question
                </h3>

                <form
                    className="space-y-5"
                    onSubmit={(e) => {
                        e.preventDefault();
                        handleAddQuestion();
                    }}
                >

                    <div className="grid grid-cols-1 gap-4">
                        <div className="flex flex-col">
                            <label className="text-sm font-medium text-gray-700 mb-1">
                                Quiz Text
                            </label>
                            <textarea
                                name="questionText"
                                value={question.questionText}
                                onChange={handleInputChange}
                                placeholder="Question Text"
                                className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                            />
                        </div>

                        <label className="text-sm font-medium text-gray-700 mb-1">
                            Option 1
                        </label>
                        <input
                            name="option1"
                            value={question.option1}
                            onChange={handleInputChange}
                            placeholder="Option 1"
                            className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                        />
                        <label className="text-sm font-medium text-gray-700 mb-1">
                            Option 2
                        </label>
                        <input
                            name="option2"
                            value={question.option2}
                            onChange={handleInputChange}
                            placeholder="Option 2"
                            className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                        />
                        <label className="text-sm font-medium text-gray-700 mb-1">
                            Option 3
                        </label>
                        <input
                            name="option3"
                            value={question.option3}
                            onChange={handleInputChange}
                            placeholder="Option 3"
                            className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                        />
                        <label className="text-sm font-medium text-gray-700 mb-1">
                            Option 4
                        </label>
                        <input
                            name="option4"
                            value={question.option4}
                            onChange={handleInputChange}
                            placeholder="Option 4"
                            className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                        />
                        <label className="text-sm font-medium text-gray-700 mb-1">
                            Correct Answer
                        </label>
                        <input
                            name="correctAnswer"
                            value={question.correctAnswer}
                            onChange={handleInputChange}
                            placeholder="Correct Answer"
                            className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                        />
                    </div>


                    {/* --- Buttons --- */}
                    <div className="flex justify-end gap-4 pt-4">
                        <button
                            type="button"
                            onClick={() => navigate(-1)}
                            className="px-5 py-2 border border-gray-300 text-gray-600 rounded-lg hover:bg-gray-100 transition"
                        >
                            Cancel
                        </button>
                        <button
                            type="submit"
                            className="px-5 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition"
                        >
                            Submit
                        </button>
                    </div>
                </form>
            </div >
        </div >
    );
}

export default AddQuestions