import React, { useState } from 'react'
import { useAuth } from '../context/AuthContext'
import { Link } from 'react-router-dom';
import SubmissionReports from './SubmissionReports';


function StudentDetails() {
  const { user } = useAuth();
  
  return (
    <React.Fragment>
      <div>
        <p>{user.userId}</p>
        <p>{user.firstName}{" "}
          {user.middleName ? (user.middleName) : ""}{" "}
          {user.lastName}
        </p>
        <p>{user.email}</p>
        <button>
          <Link to = "/editProfile">
          Edit Profile
          </Link>
        </button>
      </div>

      

    </React.Fragment>
  )
}

export default StudentDetails