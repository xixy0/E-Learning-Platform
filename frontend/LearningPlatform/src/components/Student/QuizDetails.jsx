import React, { useEffect, useState } from "react";
import toast from "react-hot-toast";
import { useNavigate } from "react-router-dom";
import api from "../../services/api";

function QuizDetails() {
  const [courses, setCourses] = useState([]);
  const [quizzes, setQuizzes] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetchCourses();
  }, []);

  useEffect(() => {
    if (courses.length > 0) {
      fetchQuiz();
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

  const fetchQuizFromCourse = async (courseId) => {
    const { data } = await api.get(`/course/getAllQuiz/${courseId}`);
    return data;
  };

  const fetchQuiz = async () => {
    try {
      const allQuizzes = [];
      for (const course of courses) {
        const data = await fetchQuizFromCourse(course.courseId);
        allQuizzes.push(...data);
      }
      setQuizzes(allQuizzes);
    } catch (error) {
      toast.error(
        "Failed to fetch quizzes: " +
          (error?.response?.data?.message ||
            error.response?.statusText ||
            error.message)
      );
    }
  };

  const handleQuiz = (quizId) => {
    navigate(`/userQuizPage/${quizId}`);
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-blue-100 flex flex-col items-center py-10">
      <div className="bg-white shadow-xl rounded-2xl p-8 w-full max-w-4xl border border-gray-200">
        <h1 className="text-3xl font-semibold text-blue-700 mb-6 text-center">
          Available Quizzes
        </h1>

        {quizzes.length > 0 ? (
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            {quizzes.map((quiz) => (
              <div
                key={quiz.quizId}
                className="p-5 border border-gray-200 rounded-xl shadow-sm hover:shadow-lg transition bg-gradient-to-br from-blue-50 to-white"
              >
                <h3 className="text-xl font-semibold text-gray-800 mb-2">
                  {quiz.quizTitle}
                </h3>
                <p className="text-sm text-gray-600 mb-1">
                  <span className="font-semibold text-blue-700">Total Marks:</span>{" "}
                  {quiz.totalMarks}
                </p>
                <p className="text-sm text-gray-600 mb-1">
                  <span className="font-semibold text-blue-700">Course ID:</span>{" "}
                  {quiz.courseId}
                </p>
                <p className="text-sm text-gray-600 mb-4">
                  <span className="font-semibold text-blue-700">Created On:</span>{" "}
                  {new Date(quiz.timestamp).toLocaleString()}
                </p>
                <button
                  onClick={() => handleQuiz(quiz.quizId)}
                  className="w-full bg-blue-600 text-white py-2 rounded-lg font-semibold hover:bg-blue-700 transition duration-200"
                >
                  Take Quiz
                </button>
              </div>
            ))}
          </div>
        ) : (
          <p className="text-center text-gray-600 mt-4">
            No quizzes available or no enrolled courses.
          </p>
        )}
      </div>
    </div>
  );
}

export default QuizDetails;
