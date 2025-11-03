import React from 'react'
import StudentSidebar from '../components/StudentSidebar'
import { Outlet } from 'react-router-dom';

function StudentDashboard() {
   return (
    <div className="flex min-h-screen bg-gray-50">
      {/* Sidebar always visible */}
      <div className="w-64">
        <StudentSidebar />
      </div>

      {/* Main content area */}
      <main className="flex-1 p-8 overflow-y-auto">
        <Outlet /> 
        {/* Outlet renders the child route’s content here */}
      </main>
    </div>
  );
}

export default StudentDashboard