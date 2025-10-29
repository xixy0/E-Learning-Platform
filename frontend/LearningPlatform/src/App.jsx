import React from 'react'
import LoginPage from './components/LoginPage'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Navbar from './components/NavBar'
import { AuthProvider } from './context/AuthContext'
import MainPage from './components/MainPage'


function App() {
  return (
    <React.Fragment>
      <BrowserRouter>
        <AuthProvider>
          <Navbar />
          <Routes>
            <Route path="/" element={<MainPage />} />
            <Route path="/login" element={<LoginPage />} />
          </Routes>
        </AuthProvider>
      </BrowserRouter>
    </React.Fragment>
  )
}

export default App