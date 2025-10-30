import React from 'react'
import { useAuth } from '../context/AuthContext'


function StudentDetails() {
    const {user} = useAuth();
  return (
    <React.Fragment>
        <div>
         <p>{user.firstName}{""}
            {user.middleName ?(user.middleName):""}{" "}
            {user.lastName}
        </p>
        <p>{user.email}</p>
        <p></p>
        </div>
    </React.Fragment>
  )
}

export default StudentDetails