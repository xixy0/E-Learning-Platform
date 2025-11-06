import React from 'react'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Navbar from './components/NavBar'
import { AuthProvider, useAuth } from './context/AuthContext'
import MainPage from './components/MainPage'
import NewUserForm from './components/Student/NewUserForm'
import EnrolledCourses from './components/Student/EnrolledCourses'
import EditUserForm from './components/Student/EditUserForm'
import QuizPage from './components/Student/QuizPage'
import StudentDetails from './components/Student/StudentDetails'
import SubmissionReports from './components/Student/SubmissionReports'
import QuizDetails from './components/Student/QuizDetails'
import Login from './pages/Login'
import StudentDashboard from "./pages/StudentDashboard";
import AppFooter from './components/AppFooter'
import NotificationListener from './components/NotificattionListener'
import { Toaster } from 'react-hot-toast'
import LessonUserDisplay from './pages/LessonUserDisplay'
import LessonUser from './components/Student/LessonUser'







function App() {

  return (
    <React.Fragment>
      <Toaster position="top-center" reverseOrder={false} />
      <BrowserRouter>
        <AuthProvider>
          <NotificationListener />
          <Navbar />
          <Routes>
            <Route path="/" element={<MainPage />} />
            <Route path="/login" element={<Login />} />
            <Route path="/newUser" element={<NewUserForm />} />
            <Route path="/myCourses" element={<EnrolledCourses />} />
            <Route path="/editProfile" element={<EditUserForm />} />
            <Route path="/userQuizPage/:quizId" element={<QuizPage />} />

            <Route element={<StudentDashboard />}>
              <Route path="/student" element={<StudentDetails />} />
              <Route path="/userSubmissionDetails" element={<SubmissionReports />} />
              <Route path="/userQuizDetails" element={<QuizDetails />} />
            </Route>

            <Route path="/userLessons/:courseId" element={<LessonUserDisplay />}>
              <Route path=":lessonId" element={<LessonUser />} />
            </Route>

          </Routes>
          <AppFooter />
        </AuthProvider>
      </BrowserRouter>
    </React.Fragment>
  )
}

export default App