import React from "react";
import { Link, useLocation } from "react-router-dom";

function StudentSidebar() {
  const location = useLocation();
  const isActive = (path) => location.pathname === path;

  const linkClasses = (path) =>
    `flex items-center gap-3 px-4 py-3 rounded-xl text-[15px] font-medium transition-all duration-200
    ${
      isActive(path)
        ? "bg-primary text-blue-700 shadow-md"
        : "text-gray-700 hover:bg-primary/10 hover:text-primary"
    }`;

  return (
    <aside className="w-64 h-full  bg-blue-200 shadow-md border-r border-gray-100 flex flex-col">
      {/* Header */}
      <div className="p-6 border-b border-gray-200">
        <h1 className="text-xl font-semibold text-primary">Student Dashboard</h1>
      </div>

      {/* Navigation */}
      <nav className="flex-1 px-4 py-6 space-y-2">
        <Link to="/student" className={linkClasses("/student")}>
          Profile
        </Link>

        <Link
          to="/userSubmissionDetails"
          className={linkClasses("/userSubmissionDetails")}
        >
          Submission Reports
        </Link>

        <Link to="/userQuizDetails" className={linkClasses("/userQuizDetails")}>
          Quiz
        </Link>

        <Link to="/userAssignmentDetails" className={linkClasses("/userAssignmentDetails")}>
          Assignment
        </Link>
      </nav>
    </aside>
  );
}

export default StudentSidebar;
