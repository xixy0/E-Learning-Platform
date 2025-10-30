import React from 'react'
import LoginPage from '../components/LoginPage'
import { Link } from 'react-router-dom'

function Login() {
    return (
        <React.Fragment>
            <div>
                <LoginPage />
                <p>
                    <Link to="/newUser" >
                        New User
                    </Link>
                </p>
            </div>
        </React.Fragment>
    )
}

export default Login