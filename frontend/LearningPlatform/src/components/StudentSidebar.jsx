
import React from 'react'
import { Link, Links, useLocation } from 'react-router-dom';


function StudentSidebar() {

  const location = useLocation();
  const isActive = (path) => location.pathname === path;

  const linkClasses = (path) =>
    `flex block px-4 py-3 rounded-md text-md font-semibold transition-all duration-300 ${isActive(path)
      ? "bg-secondary text-white"
      : "hover:bg-secondary hover:text-white text-primary-content"
    }`;


  return (
    <React.Fragment>
      <div className="w-65 flex flex-col min-h-[calc(100vh-154px)] text-primary-content bg-gradient-to-b from-primary/80 to-secondary shadow-lg">
        <div className="flex justify-between items-center p-6">
          <h1 className="text-lg font-semibold">Admin Dashboard</h1>
        </div>
        <hr className="mx-4" />
        <div className="flex-1 mt-6 overflow-y-auto">
          <nav className="space-y-4 px-4">
            <Link
             to="/role_student" className={linkClasses("/role_student")}>
              StudentDetails
            </Link> 
            <Link to="/enrolledCourses" className={linkClasses("/userdetails")}>
              Enrolled Courses
            </Link>
            <Link
              to="/submissionDetails"
              className={linkClasses("/submissionDetails")}
            >
              Reports
            </Link>
            <Link to="/entities" className={linkClasses("/entities")}>
              Entities
            </Link>
          </nav>
        </div>
      </div>
    </React.Fragment>
  )
}

export default StudentSidebar