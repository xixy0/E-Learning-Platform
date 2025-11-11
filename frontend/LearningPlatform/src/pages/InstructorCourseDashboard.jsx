import React from 'react'
import InstructorSideBar from '../components/Instructor/InstructorSideBar'
import { Outlet, useParams } from 'react-router-dom'

function InstructorCourseDashboard() {
  
  return (
   <div className="flex min-h-screen  bg-gradient-to-br from-blue-50 to-blue-100">
      {/* Sidebar always visible */}
      <div className="w-64">
       <InstructorSideBar />
      </div>

      {/* Main content area */}
      <main className="flex-1 p-8 overflow-y-auto">
        <Outlet /> 
        {/* Outlet renders the child route’s content here */}
      </main>
    </div>
  )
}

export default InstructorCourseDashboard