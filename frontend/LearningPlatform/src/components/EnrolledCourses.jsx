import React, { useState } from 'react'

function EnrolledCourses() {

    const [enrolledCourses, setEnrolledCourses] = useState([]);
    const [filteredCourses,,setFilteredCourses] = useState([]);
    
    useEffect(() => {
        fetchCourses();
    }, [])

   
    const fetchCourses = async() => {
        try{
            const response = await api.get("/users/viewEnrolledCourses");
            setEnrolledCourses(response.data)
            setFilteredCourses(response.data);
        }
            catch(error) {
                console.error("Error fetching courses:", error);
                setEnrolledCourses([]);
                setFilteredCourses([]);
            };
    };

    const filterData = () => {
        const newData = enrolledCourses.filter(
            (course) =>
                course.courseTitle
                    .toLowerCase()
                    .includes(searchData.toLocaleLowerCase()) ||
                course.courseCategory
                    .toLowerCase()
                    .includes(searchData.toLocaleLowerCase()) ||
                course.instructorName
                    .toLowerCase()
                    .includes(searchData.toLocaleLowerCase())
        );
        setFilteredCourses(newData);
        
    };

      const handleUnEnroll = async(courseId) => {
        try {
            const response = await api.post(`/student/unenroll/${courseId}`);
            toast.success("Succesfully Unenrolled!");
        } catch (error) {
            toast.error(
                "Failed: " + (
                    error?.response?.data?.message ||
                    error.response?.statusText ||
                    error.message
                )
            );
        }
    };
    
    
    return (

        <React.Fragment>
          <div className='py-100 bg-gray-400'>
            <div>
                <input
                    type="text"
                    value={searchData}
                    name="search"
                    placeholder="Search by course name, category or instructor"
                    onChange={(e) => setSearchData(e.target.value)}
                    className="flex-1 bg-white text-black p-3 border-black rounded-lg shadow-sm focus:ring-1 focus:ring-black-700 focus:outline-none"

                />
                <button
                    onClick={filterData}
                    className="px-5 py-3 bg-green-700 text-white font-semibold rounded-lg shadow hover:bg-green-600 transition !important"
                >
                    Search
                </button>
            </div>
            
            {filteredCourses.length > 0 ?
                (filteredCourses.map((course) => (
                    <div key={course.courseId}>
                        <h3>{course.courseTitle}</h3>
                        <p>{course.courseDescription}</p>
                        <p>{course.instructorName}</p>
                        <p>{course.numberOfStudentsEnrolled}</p>
                        {isLoggedIn && (
                            <button onClick={()=>handleUnEnroll(course.courseId)}>Enroll</button>
                        )}
                    </div>
                ))) :
                (<p>Not enrolled in any course</p>)}
        </div>
    
        </React.Fragment>

    )
}

export default EnrolledCourses