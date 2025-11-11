import React from 'react'
import { NavLink, useLocation, useParams } from 'react-router-dom';

function InstructorSideBar() {

    const location = useLocation();
    const isActive = (path) => location.pathname === path;
    const {courseId} = useParams();
    const linkClasses = (path) =>
        `flex items-center gap-3 px-4 py-3 rounded-xl text-[15px] font-medium transition-all duration-200
    ${isActive(path)
            ? "bg-primary text-blue-700 shadow-md"
            : "text-gray-700 hover:bg-primary/10 hover:text-primary"
        }`;
    return (
        <aside className="w-64 h-full  bg-blue-200 shadow-md border-r border-gray-100 flex flex-col">
            {/* Header */}
            <div className="p-6 border-b border-gray-200">
                <h1 className="text-xl font-semibold text-primary">Course Dashboard</h1>
            </div>

            {/* Navigation */}
            <nav className="flex-1 px-4 py-6 space-y-2">
                <NavLink to={`/courseDetails/${courseId}/overview`} className={linkClasses(`/courseDetails/${courseId}/overview`)}>
                    Course Details
                </NavLink>

                <NavLink
                    to={`/courseDetails/${courseId}/studentEnrolled`}
                    className={linkClasses(`/courseDetails/${courseId}/studentEnrolled`)}
                >
                    Student Enrolled
                </NavLink>

                <NavLink to={`/courseDetails/${courseId}/lessons`}
                    className={linkClasses(`/courseDetails/${courseId}/lessons`)}>
                    Lesson
                </NavLink>

                <NavLink to={`/courseDetails/${courseId}/quiz`}
                    className={linkClasses(`/courseDetails/${courseId}/quiz`)}>
                    Quiz
                </NavLink>

                <NavLink to={`/courseDetails/${courseId}/assignments`}
                    className={linkClasses(`/courseDetails/${courseId}/assignments`)}>
                    Assignment
                </NavLink>

            </nav>
        </aside>
    )
}

export default InstructorSideBar