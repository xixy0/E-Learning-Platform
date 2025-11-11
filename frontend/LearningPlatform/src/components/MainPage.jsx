import React, { useEffect, useState } from "react";
import api from "../services/api";
import { useAuth } from "../context/AuthContext";
import toast from "react-hot-toast";

function MainPage() {
  const [courses, setCourses] = useState([]);
  const [searchData, setSearchData] = useState("");
  const [filteredCourses, setFilteredCourses] = useState([]);
  const { isLoggedIn , user } = useAuth();

 

  useEffect(() => {
    fetchCourses();
  }, []);

  const fetchCourses = async () => {
    try {
      const response = await api.get("/course/getAll");
      setCourses(response.data);
      setFilteredCourses(response.data);
    } catch (error) {
      toast.error("Failed to load courses.");
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
      toast.error("Enrollment failed");
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-red-50 to-blue-100 py-10 px-6">
      <div className="max-w-screen mx-auto bg-white shadow-2xl rounded-2xl p-8 border border-blue-100">
        <h1 className="text-2xl font-semibold mb-6 text-center text-blue-700">
           Courses
        </h1>

        
        <div className="flex flex-col  items-center justify-center w-full max-w-4xl mx-auto sm:flex-row  gap-3 mb-8">
          <input
            type="text"
            value={searchData}
            onChange={(e) => setSearchData(e.target.value)}
            placeholder="Search by course title, category or instructor"
            className="flex-1 border border-gray-300 rounded-lg px-4 py-2 focus:ring-2 focus:ring-blue-500 focus:outline-none"
          />
          <button
            onClick={filterData}
            className="px-5 py-2 bg-blue-600 text-white font-semibold rounded-lg hover:bg-blue-700 transition"
          >
            Search
          </button>
        </div>

        
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
          {filteredCourses.length > 0 ? (
            filteredCourses.map((course) => (
              <div
                key={course.courseId}
                className="bg-white border border-gray-200 rounded-xl p-5 shadow hover:shadow-lg transition duration-200"
              >
                <img
                  src={course.imageUrl || "https://via.placeholder.com/150"}
                  alt={course.courseTitle}
                  className="w-full h-40 object-cover rounded-lg mb-3"
                />
                <h3 className="text-lg font-semibold text-gray-800 mb-1">
                  {course.courseTitle}
                </h3>
                <p className="text-gray-600 text-sm mb-2">
                  {course.courseDescription}
                </p>
                <p className="text-sm text-gray-500 mb-1">
                  <span className="font-semibold text-gray-700">
                    Instructor:
                  </span>{" "}
                  {course.instructorName}
                </p>
                <p className="text-sm text-gray-500 mb-4">
                  <span className="font-semibold text-gray-700">
                    Enrolled:
                  </span>{" "}
                  {course.numberOfStudentsEnrolled}
                </p>

                {isLoggedIn && user?.role?.toUpperCase().includes("STUDENT") && (
                  <button
                    onClick={() => handleEnroll(course.courseId)}
                    className="w-full bg-green-600 text-white py-2 rounded-lg font-semibold hover:bg-green-700 transition"
                  >
                    Enroll
                  </button>
                )}
              </div>
            ))
          ) : (
            <p className="col-span-full text-center text-gray-600">
              No courses found.
            </p>
          )}
        </div>
      </div>
    </div>
  );
}

export default MainPage;

