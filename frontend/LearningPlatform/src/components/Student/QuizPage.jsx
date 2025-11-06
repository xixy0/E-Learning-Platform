import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import toast from "react-hot-toast";
import api from "../../services/api";
import { useAuth } from "../../context/AuthContext";

function QuizPage() {
  const{user} = useAuth();
  const { quizId } = useParams(); 
  const [questions, setQuestions] = useState(null);
  const [answers, setAnswers] = useState({});
  const [submitted, setSubmitted] = useState(false);
  const [score, setScore] = useState(null);

  // Fetch quiz details when the page loads
  useEffect(() => {
    const fetchQuiz = async () => {
      try {
        const response = await api.get(`/quiz/getQuestions/${quizId}`);
        setQuestions(response.data);
      } catch (error) {
        toast.error(
          "Failed to load quiz: " +
            (error.response?.data?.message ||
              error.response?.statusText ||
              error.message)
        );
      }
    };
    fetchQuiz();
  }, [quizId]);

  // Handle answer change
  const handleAnswerChange = (questionId, selectedOption) => {
    setAnswers((prev) => ({
      ...prev,
      [questionId]: selectedOption,
    }));
  };

  // ✅ Submit handler (sends answers to backend)
  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!questions) return;

    const payload = {
      quizId: quizId,
      studentId: user.userId,
      answers: answers, // {questionId: "userAnswer"}
    };

    try {
      const response = await api.post("/submissions/addSubmission", payload);
      setScore(response.data.score);
      setSubmitted(true);
      toast.success("Quiz submitted successfully!");
    } catch (error) {
      toast.error(
        "Failed to submit quiz: " +
          (error.response?.data?.message ||
            error.response?.statusText ||
            error.message)
      );
    }
  };

  if (!questions) return <p className="text-center mt-10">Loading quiz...</p>;

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-blue-100 flex flex-col items-center py-10">
      <div className="bg-white shadow-xl rounded-2xl p-8 w-full max-w-3xl border border-gray-200">
        <h1 className="text-3xl font-semibold text-blue-700 mb-6 text-center">
          Quiz
        </h1>

        {!submitted ? (
          <form onSubmit={handleSubmit} className="space-y-6">
            {questions.map((q) => (
              <div
                key={q.questionId}
                className="p-4 border border-gray-200 rounded-lg shadow-sm hover:shadow-md transition"
              >
                <p className="font-medium mb-3 text-gray-800">{q.questionText}</p>
                <div className="space-y-2">
                  {[q.option1, q.option2, q.option3, q.option4].map((option, i) => (
                    <label key={i} className="flex items-center space-x-2 cursor-pointer">
                      <input
                        type="radio"
                        name={`question-${q.questionId}`}
                        value={option}
                        checked={answers[q.questionId] === option}
                        onChange={() => handleAnswerChange(q.questionId, option)}
                        className="text-blue-600 focus:ring-blue-500"
                        required
                      />
                      <span className="text-gray-700">{option}</span>
                    </label>
                  ))}
                </div>
              </div>
            ))}

            <button
              type="submit"
              className="w-full mt-6 bg-blue-600 text-white py-2 rounded-lg font-semibold hover:bg-blue-700 transition duration-200"
            >
              Submit Quiz
            </button>
          </form>
        ) : (
          <div className="text-center">
            <h2 className="text-2xl font-semibold text-green-600">
              🎉 You scored {score.toFixed(2)}%
            </h2>
            <p className="text-gray-600 mt-2">
              Great job! Your results have been recorded.
            </p>
          </div>
        )}
      </div>
    </div>
  );
}

export default QuizPage;
