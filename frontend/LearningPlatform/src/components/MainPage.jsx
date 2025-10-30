import React, { useEffect, useState } from "react";
import api from "../services/api";
import { useAuth } from "../context/AuthContext";
import toast from "react-hot-toast";

function MainPage() {
  const [courses, setCourses] = useState([]);
  const [searchData, setSearchData] = useState("");
  const [filteredCourses, setFilteredCourses] = useState([]);
  const { isLoggedIn } = useAuth();

  useEffect(() => {
    fetchCourses();
  }, []);

  const fetchCourses = async () => {
    try {
      const response = await api.get("/course/getAll");
      setCourses(response.data);
      setFilteredCourses(response.data);
    } catch (error) {
      console.error("Error fetching courses:", error);
      setCourses([]);
      setFilteredCourses([]);
    }
  };

  const filterData = () => {
    const newData = courses.filter(
      (course) =>
        course.courseTitle.toLowerCase().includes(searchData.toLowerCase()) ||
        course.courseCategory.toLowerCase().includes(searchData.toLowerCase()) ||
        course.instructorName.toLowerCase().includes(searchData.toLowerCase())
    );
    setFilteredCourses(newData);
  };

  const handleEnroll = async (courseId) => {
    try {
      await api.post(`/student/enroll/${courseId}`);
      toast.success("Successfully Enrolled!");
    } catch (error) {
      toast.error(
        "Enrollment failed: " +
          (error?.response?.data?.message ||
            error.response?.statusText ||
            error.message)
      );
    }
  };

  return (
    <div className="min-h-screen bg-gray-100 py-10 px-6">
      {/* Search Section */}
      <div className="max-w-4xl mx-auto mb-8 flex flex-col sm:flex-row items-center gap-3">
        <input
          type="text"
          value={searchData}
          name="search"
          placeholder="Search by course name, category or instructor"
          onChange={(e) => setSearchData(e.target.value)}
          className="flex-1 bg-white text-gray-900 px-4 py-2 border border-gray-300 rounded-lg shadow-sm focus:ring-2 focus:ring-blue-500 focus:outline-none"
        />
        <button
          onClick={filterData}
          className="px-5 py-2 bg-blue-600 text-white font-semibold rounded-lg shadow hover:bg-blue-700 transition"
        >
          Search
        </button>
      </div>

      {/* Course List */}
      <div className="max-w-6xl mx-auto grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        {filteredCourses.length > 0 ? (
          filteredCourses.map((course) => (
            <div
              key={course.courseId}
              className="bg-white rounded-2xl shadow-md p-5 border border-gray-200 hover:shadow-lg transition duration-200"
            >
              <h3 className="text-xl font-semibold text-gray-800 mb-2">
                {course.courseTitle}
              </h3>
              <p className="text-gray-600 mb-3">{course.courseDescription}</p>
              <p className="text-sm text-gray-500">
                <span className="font-semibold text-gray-700">Instructor:</span>{" "}
                {course.instructorName}
              </p>
              <p className="text-sm text-gray-500 mb-4">
                <span className="font-semibold text-gray-700">
                  Students Enrolled:
                </span>{" "}
                {course.numberOfStudentsEnrolled}
              </p>

              {isLoggedIn && (
                <button
                  onClick={() => handleEnroll(course.courseId)}
                  className="mt-auto w-full bg-green-600 text-white py-2 rounded-lg font-semibold hover:bg-green-700 transition"
                >
                  Enroll
                </button>
              )}
            </div>
          ))
        ) : (
          <p className="col-span-full text-center text-gray-600">
            No courses to display.
          </p>
        )}
      </div>
    </div>
  );
}

export default MainPage;
