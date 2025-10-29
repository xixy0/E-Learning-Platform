import React from 'react'
import { useAuth } from '../context/AuthContext'


function StudentDetails() {
    const {userData} = useAuth();
  return (
    <React.Fragment>
        <div>
         <p>{userData.firstName}{""}
            {userData.middleName ?(userData.middleName):""}{" "}
            {userData.lastName}
        </p>
        <p>{userData.email}</p>
        <p></p>
        </div>
    </React.Fragment>
  )
}

export default StudentDetails