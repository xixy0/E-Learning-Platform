import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import api from '../../services/api';
import toast from 'react-hot-toast';

function CourseAssignments() {
  const { courseId } = useParams();
  const [assignments, setAssignments] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetchAssignments();
  },[])

  const fetchAssignments = async () => {
    try {
      const { data } = await api.get(`/course/getAssignments/${courseId}`)
      setAssignments(data);
    } catch (error) {
      toast.error(
        "Failed to fetch assignments: " +
        (error?.response?.data?.message ||
          error.response?.statusText ||
          error.message)
      );
    }
  }

  const handleAdd = (courseId)=>{
    navigate(`/addAssignment/${courseId}`)
  }

  const handleEdit = (assignmentId)=>{
    navigate(`/editAssignment/${assignmentId}`)
  }

  const handleSubmission = (assignmentId)=>{
    
  }
  return (
    <div className="min-h-screen bg-gray-50 p-6">
      <div className="max-w-5xl mx-auto bg-white shadow-xl rounded-2xl p-6">
        <div className='flex justify-between items-center mb-6 border-b pb-6'>
        <h2 className="text-2xl font-bold text-gray-800">
          Assignment Details
        </h2>
         <button
         onClick={()=>handleAdd(courseId)}
         className='bg-blue-500 hover:bg-blue-700 text-white text-sm font-semibold px-4 py-2 rounded-lg shadow transition duration-200'
         >+ Add Assignment</button></div>
        {assignments.length > 0 ? (
          <div className="overflow-x-auto">
            <table className="w-full border-collapse bg-white">
              <thead className="bg-blue-100 text-gray-700 uppercase text-sm">
                
                <tr>
                  <th className="py-3 px-6 text-left">Assignment Id</th>
                  <th className="py-3 px-6 text-left">Assignment Title</th>
                  <th className="py-3 px-6 text-left">Description</th>
                  <th className="py-3 px-6 text-middle">Actions</th>
                  
                </tr>
                  
              </thead>
              <tbody>
                {assignments.map((assignment) => (
                  <tr
                    key={assignment.assignmentId}
                    className="border-b hover:bg-gray-50 transition-colors"
                  >
                    <td className="py-3 px-6">
                      {assignment.assignmentId || "N/A"}
                    </td>

                    <td className="py-3 px-6 font-medium text-blue-600">
                      {assignment.assignmentTitle}
                    </td>

                    <td className="py-3 px-6 text-gray-600">
                      {assignment.assignmentDescription}
                    </td>

                    <td className="flex gap-1 py-3 px-6 text-white">
                      <button
                        className='bg-gray-500 hover:bg-gray-700 text-white text-xs font-medium px-2 py-1.5 rounded-md transition duration-200'
                        onClick={() => handleEdit(assignment.assignmentId)}>
                        Edit Assignment
                      </button>
                      <button
                        className='bg-green-500 hover:bg-green-700 text-white text-xs font-medium px-2 py-1.5 rounded-md transition duration-200'
                        onClick={() => handleSubmission(assignment.assignmentId)}>
                        Submissions
                      </button>
                    </td>

                     
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        ) : (
          <div className="text-center py-10 text-gray-500">
            <p className="text-lg">No assignment added</p>
          </div>
        )}
      </div>
    </div>
  )
}

export default CourseAssignments