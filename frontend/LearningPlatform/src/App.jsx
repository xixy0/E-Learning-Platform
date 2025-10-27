import React from 'react'
import LoginPage from './components/LoginPage'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Navbar from './components/NavBar'


function App() {
  return (
    <React.Fragment>
      <BrowserRouter>
      <Navbar/>
      <Routes>
        <Route path = "/login" element={<LoginPage/>}/>
      </Routes>
      </BrowserRouter>
    </React.Fragment>
    )
}

export default App