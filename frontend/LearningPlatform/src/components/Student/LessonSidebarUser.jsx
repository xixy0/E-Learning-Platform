import React, { useEffect, useState } from "react";
import toast from "react-hot-toast";
import { NavLink, useLocation } from "react-router-dom";
import api from "../../services/api";

function LessonSidebarUser({ courseId }) {
  const location = useLocation();
  const isActive = (path) => location.pathname === path;
  const [lessons, setLessons] = useState([]);

  useEffect(() => {
    fetchLessons();
  }, [courseId]);

  const fetchLessons = async () => {
    try {
      const response = await api.get(`/course/getLessons/${courseId}`);
      setLessons(response.data);
    } catch (error) {
      toast.error(
        "Failed to load lessons: " +
          (error.response?.data?.message ||
            error.response?.statusText ||
            error.message)
      );
    }
  };

  const linkClasses = (path) =>
    `block px-4 py-3 rounded-xl text-[15px] font-medium transition-all duration-200
    ${
      isActive(path)
        ? "bg-blue-100 text-blue-700 shadow-md"
        : "text-gray-700 hover:bg-blue-50 hover:text-blue-600"
    }`;

  return (
    <aside className="w-64 h-full bg-blue-200 shadow-md border-r border-gray-100 flex flex-col">
     
      <div className="p-6 border-b border-gray-200">
        <h1 className="text-xl font-semibold text-blue-700">Course Lessons</h1>
      </div>

      
      <nav className="flex-1 px-4 py-6 space-y-2 overflow-y-auto">
        {lessons.length > 0 ? (
          lessons.map((lesson) => (
            <NavLink
              key={lesson.lessonId}
              to={`/userLessons/${courseId}/${lesson.lessonId}`}
              className={linkClasses(`/userLessons/${courseId}/${lesson.lessonId}`)}
            >
              {lesson.lessonTitle}
            </NavLink>
          ))
        ) : (
          <p className="text-gray-600 text-center mt-4">No lessons found</p>
        )}
      </nav>
    </aside>
  );
}

export default LessonSidebarUser;
