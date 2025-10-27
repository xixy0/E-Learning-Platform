import React, { useEffect, useState } from 'react'
import api from '../services/api';

function MainPage() {
    const [isLoggedIn, setIsLoggedIn] = useState(!!localStorage.getItem("token"));
    const [courses, setCourses] = useState([]);
    const [searchData, setSearchData] = useState("");
    const [filteredCourses, setFilteredCourses] = useState([]);

    useEffect(() => {
        fetchCourses();
    }, [])

   useEffect(() => {
    const updateLoginStatus = () => {
      setIsLoggedIn(!!localStorage.getItem("token"));
    };

    window.addEventListener("authChange", updateLoginStatus);

    return () => {
      window.removeEventListener("authChange", updateLoginStatus);
    };
  }, []);

    const fetchCourses = async () => {
        await api
            .get("/course/getAll")
            .then((response) => setCourses(response.data))
            .then(setFilteredCourses(courses))
            .catch((error) => {
                console.error("Error fetching courses:", error);
                setCourses([]);
            });
    };

    const filterData = () => {
        const newData = courses.filter(
            (course) =>
                course.courseTitle
                    .toLowerCase()
                    .includes(searchData.toLocaleLowerCase()) ||
                course.courseCategory
                    .toLowerCase()
                    .includes(searchData.toLocaleLowerCase) ||
                course.instructorName
                    .toLowerCase()
                    .includes(searchData.toLocaleLowerCase)
        );
        setFilteredCourses(newData);
    };

    const handleEnroll = aync(course) =>{
        const response = await  
    }

    return (
        <div>
            <div>
                <input
                    type="text"
                    value={searchData}
                    name="search"
                    placeholder="Search by course name, category or instructor"
                    onChange={(e) => setSearchData(e.target.value)}
                    className="flex-1 bg-white p-3 border rounded-lg shadow-sm focus:ring-1 focus:ring-black-700 focus:outline-none"

                />
                <button
                    onClick={filterData}
                    className="px-5 py-3 bg-green-700 text-white font-semibold rounded-lg shadow hover:bg-green-600 transition !important"
                >
                    Search
                </button>
            </div>

            {filteredCourses && filteredCourses.length > 0 ?
                (filteredCourses.map((course) => (
                    <div key={course.courseId}>
                        <h3>{course.courseTitle}</h3>
                        <p>{course.courseDescription}</p>
                        <p>{course.instructorName}</p>
                        <p>{course.numberOfStudentsEnrolled}</p>
                        {isLoggedIn &&(
                            <button onClick={handleEnroll(course)}>Enroll</button>
                        )}
                    </div>
                ))) :
                (<p>No courses to display</p>)}
        </div>
    )
}

export default MainPage