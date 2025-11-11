import React, { useState } from 'react'
import api from '../services/api';
import toast from 'react-hot-toast';
import { useAuth } from '../context/AuthContext';
import { Link } from 'react-router-dom';

function LoginPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const { login } = useAuth();


  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await api.post("/auth/authenticate", {
        username,
        password
      }
      );
      const token = response.data.token;
      login(token);
    } catch (error) {
      toast.error(
        "Login failed: " +
        (error.response?.data?.message ||
          error.response?.statusText ||
          error.message
        )
      )
    };
  }

  return (
    <div className="flex items-center justify-center min-h-screen bg-gradient-to-br from-red-50 to-blue-200">
      <div className="bg-white shadow-2xl rounded-2xl p-8 w-full max-w-sm border border-blue-100">
        <h1 className="text-2xl font-semibold mb-6 text-center text-blue-700">
          Login
        </h1>

        <form onSubmit={handleLogin} className="space-y-5">
          <div>
            <label className="input validator flex items-center gap-2 font-semibold bg-white text-black border rounded-lg px-3 py-2 focus-within:ring bg-black">
              <svg
                className="h-5 w-5 text-gray-400"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth="2"
                  d="M5.121 17.804A1 1 0 016 17h12a1 1 0 01.879.516l2.121 3.606A1 1 0 0120.121 23H3.879a1 1 0 01-.879-1.878l2.121-3.606zM12 2a5 5 0 100 10 5 5 0 000-10z"
                />
              </svg>
              <input
                type="text"
                required
                placeholder="Username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                //pattern="[a-z0-9._%+\-]+@[a-z0-9.\-]+\.[a-z]{2,}$"
                //minLength="5"
                maxLength="100"
                title="Enter a valid email"
                className="w-full outline-none"
              />
            </label>
          </div>

          <div>
            <label className="input validator flex items-center gap-2 bg-white font-semibold text-black border rounded-lg px-3 py-2 focus-within:ring bg-black">
              <svg
                className="h-5 w-5 text-gray-400"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth="2"
                  d="M12 11c.667 0 2 .333 2 2v3H10v-3c0-1.667 1.333-2 2-2zM4 11V9a8 8 0 0116 0v2m-2 0v8H6v-8"
                />
              </svg>
              <input
                type="password"
                required
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                minLength="5"
                pattern="(?=.*\d)(?=.*[a-z]).{5,}"
                title="Must be more than 5 characters, including at least one number"
                className="w-full outline-none"
              />
            </label>
          </div>

          <div>
            <button
              type="submit"
              className="w-full bg-blue-600 text-white py-2 rounded-lg font-semibold hover:bg-blue-700 transition duration-200"
            >
              Login
            </button>

            <div className="mt-6 text-center text-gray-700 text-sm">
              <p>
                New User?{" "}
                <Link
                  to="/newUser"
                  className="text-blue-600 font-semibold hover:underline hover:text-blue-700"
                >
                  Register
                </Link>
              </p>
            </div>

          </div>
        </form>
      </div>
    </div>
  );
}

export default LoginPage