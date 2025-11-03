import React from 'react'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Navbar from './components/NavBar'
import { AuthProvider } from './context/AuthContext'
import MainPage from './components/MainPage'
import StudentDashboard from './pages/StudentDashboard'
import StudentDetails from './components/StudentDetails'
import Login from './pages/Login'
import NewUserForm from './components/NewUserForm'
import AppFooter from './components/AppFooter'
import EnrolledCourses from './components/EnrolledCourses'
import EditUserForm from './components/EditUserForm'
import SubmissionReports from './components/SubmissionReports'
import QuizDetails from './components/QuizDetails'
import QuizPage from './components/QuizPage'







function App() {
  return (
    <React.Fragment>
      <BrowserRouter>
        <AuthProvider>
          <Navbar />
          <Routes>
            <Route path="/" element={<MainPage />} />
            <Route path="/login" element={<Login />} />
            <Route path="/newUser" element={<NewUserForm />} />
            <Route path="/myCourses" element={<EnrolledCourses />} />
            <Route path="/editProfile" element={<EditUserForm />} />
            <Route path="/userQuizPage" element={<QuizPage />} />

            <Route  element={<StudentDashboard />}>
              <Route path="/student" element={<StudentDetails />} />
              <Route path="/userSubmissionDetails" element={<SubmissionReports />} />
              <Route path="/userQuizDetails" element={<QuizDetails />} />
            </Route>

          </Routes>
          <AppFooter />
        </AuthProvider>
      </BrowserRouter>
    </React.Fragment>
  )
}

export default App