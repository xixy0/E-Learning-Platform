import React, { useEffect, useState } from 'react'
import api from '../services/api';
import toast from 'react-hot-toast';
import { useNavigate } from 'react-router-dom';

function QuizDetails() {

  const [courses, setCourses] = useState([]);
  const [quizez, setQuizez] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetchCourses();
  }, [])

  useEffect(() => {
    if (courses.length > 0) {
      fetchQuiz();
    }
  }, [courses])

  const fetchCourses = async () => {
    try {
      const response = await api.get("/users/viewEnrolledCourses");
      setCourses(response.data);
    } catch (error) {
      toast.error(
        "Failed: " +
        (error?.response?.data?.message ||
          error.response?.statusText ||
          error.message)
      );
    }
  }
  const fetchQuizFromCourse = async (courseId) => {
    const { data } = await api.get(`/course/getAllQuiz/${courseId}`);
    return data;
  }

 const fetchQuiz = async () => {
  try {
    const allQuizzes = [];
    for (const course of courses) {
      const data = await fetchQuizFromCourse(course.courseId);
      allQuizzes.push(...data);
    }
    setQuizez(allQuizzes);
  } catch (error) {
    toast.error(
      "Failed: " +
        (error?.response?.data?.message ||
          error.response?.statusText ||
          error.message)
    );
  }
};

  const handleQuiz = async (quizId) => {
    try {
      navigate(`/quizDetails/${quizId}`);
    }
    catch (error) {
      toast.error(
        "Failed: " +
        (error?.response?.data?.message ||
          error.response?.statusText ||
          error.message)
      )
    }
  }

  return (
    <React.Fragment>
      <div>
        <h1>Quiz Details</h1>
        {quizez.length > 0 ? (
          quizez.map((quiz) => (
            <div key={quiz.quizId}>
              <h3>{quiz.quizTitle}</h3>
              <p>{quiz.totalMarks}</p>
              <p>{quiz.courseId}</p>
              <p>{quiz.timestamp}</p>
              <button onClick={() => handleQuiz(quiz.quizId)}>Take Quiz</button>
            </div>
          ))
        ) : (<p>No course enrolled</p>)}

      </div>
    </React.Fragment>
  )
}

export default QuizDetails