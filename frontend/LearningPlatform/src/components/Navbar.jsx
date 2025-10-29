
import { Link } from "react-router-dom";
import LogoutButton from "./LogoutButton";
import { useAuth } from "../context/AuthContext";

function Navbar() {
 
  const{isLoggedIn} = useAuth();

  return (
    <nav className="fixed top-0 left-0 w-full z-50 bg-white/80 backdrop-blur-md shadow-md">
      <div className="max-w-7xl mx-auto flex items-center justify-between px-6 py-3">
        
        {/* Left Section */}
        <div className="flex items-center space-x-4">
          <div className="dropdown">
            <button
              tabIndex={0}
              className="btn btn-ghost p-2 rounded-lg hover:bg-gray-100"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                className="h-6 w-6 text-gray-600"
                fill="none"
                viewBox="0 0 24 24"
                strokeWidth={2}
                stroke="currentColor"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  d="M4 6h16M4 12h16M4 18h16"
                />
              </svg>
            </button>
            <ul
              tabIndex={0}
              className="menu menu-md dropdown-content bg-white text-gray-800 rounded-box "
            >
              {isLoggedIn && (
                <li>
                  <Link
                    to="/userdetails"
                    className="hover:bg-blue-100 rounded-lg"
                  >
                    Dashboard
                  </Link>
                </li>
              )}
              <li>
                <Link to="/about" className="hover:bg-blue-100 rounded-lg">
                  About
                </Link>
              </li>
            </ul>
          </div>
        </div>

        {/* Center Section */}
        <div className="text-center">
          <Link
            to="/"
            className="text-2xl font-semibold text-blue-700 hover:text-blue-800 transition-colors"
          >
            LearnIT.<span className="text-blue-500">.</span>
          </Link>
        </div>

        {/* Right Section */}
        <div className="flex items-center space-x-3">
          {!isLoggedIn && (
            <div className="tooltip tooltip-left" data-tip="Login">
              <Link
                to="/login"
                className="btn btn-ghost btn-circle hover:bg-blue-100"
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  strokeWidth={1.8}
                  stroke="currentColor"
                  className="w-6 h-6 text-gray-700"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    d="M15.75 6a3.75 3.75 0 1 1-7.5 0 3.75 3.75 0 0 1 7.5 0ZM4.501 20.118a7.5 7.5 0 0 1 14.998 0A17.933 17.933 0 0 1 12 21.75c-2.676 0-5.216-.584-7.499-1.632Z"
                  />
                </svg>
              </Link>
            </div>
          )}
          {isLoggedIn && <LogoutButton />}
        </div>
      </div>
    </nav>
  );
}

export default Navbar;
