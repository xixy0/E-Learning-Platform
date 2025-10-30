import React from 'react'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Navbar from './components/NavBar'
import { AuthProvider } from './context/AuthContext'
import MainPage from './components/MainPage'
import StudentDashboard from './pages/StudentDashboard'
import StudentDetails from './components/StudentDetails'
import Login from './pages/Login'
import NewUserForm from './components/NewUserForm'
import Footer from 'daisyui/components/footer'


function App() {
  return (
    <React.Fragment>
      <BrowserRouter>
        <AuthProvider>
          <Navbar />
          <Routes>
            <Route path="/" element={<MainPage />} />
            <Route path="/login" element={<Login />} />
            <Route path="/student" element={<StudentDashboard />} />
            <Route path="/role_student" element={<StudentDetails />} />
            <Route path="/newUser" element={<NewUserForm />} />
          </Routes>
          <Footer />
        </AuthProvider>
      </BrowserRouter>
    </React.Fragment>
  )
}

export default App