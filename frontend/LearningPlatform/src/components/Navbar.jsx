import { Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import { useState } from "react";
import LogoutButton from "./LogoutButton";

function Navbar() {
  const { isLoggedIn, user } = useAuth();
  const [menuOpen, setMenuOpen] = useState(false);
  

  return (
    <nav className="sticky top-0 z-50 bg-white/90 backdrop-blur-md shadow-sm">
      <div className="max-w-7xl mx-auto px-6 py-3 flex items-center justify-between">

        {/* Left: Brand */}
        <Link
          to="/"
          className="text-2xl font-semibold text-blue-700 hover:text-blue-800 transition-colors"
        >
          LearnIT<span className="text-blue-500">.</span>
        </Link>


        <div className="hidden md:flex items-center space-x-6 text-gray-700 font-medium">
          
          {isLoggedIn && user?.role?.toUpperCase().includes("STUDENT") &&   (
            <Link to="/student" className="hover:text-blue-600 transition">
              Dashboard
            </Link>
          )}
          {isLoggedIn && user?.role?.toUpperCase().includes("STUDENT") && (
            <Link to="/mycourses" className="hover:text-blue-600 transition">
              My Courses
            </Link>
          )}

          {isLoggedIn && user?.role?.toUpperCase().includes("INSTRUCTOR") && (
            <Link to="/instructor" className="hover:text-blue-600 transition">
              Courses
            </Link>
          )}
          <Link to="/about" className="hover:text-blue-600 transition">
            About
          </Link>

          {!isLoggedIn ? (
            <Link
              to="/login"
              className="px-4 py-2 rounded-md bg-blue-600 text-white hover:bg-blue-700 transition"
            >
              Login
            </Link>
          ) : (
            <div className="flex items-center space-x-2">
              <span className="text-gray-600 font-medium">
                Hi, {user ? user.firstName : "User"}
              </span>
              <LogoutButton />
            </div>
          )}
        </div>

        {/* Mobile Menu Button */}
        <button
          className="md:hidden p-2 rounded-md hover:bg-gray-100"
          onClick={() => setMenuOpen((prev) => !prev)}
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            strokeWidth="2"
            stroke="currentColor"
            className="w-6 h-6 text-gray-700"
          >
            {menuOpen ? (
              <path strokeLinecap="round" strokeLinejoin="round" d="M6 18L18 6M6 6l12 12" />
            ) : (
              <path strokeLinecap="round" strokeLinejoin="round" d="M4 6h16M4 12h16M4 18h16" />
            )}
          </svg>
        </button>
      </div>

      {/* Mobile Dropdown Menu */}
      {menuOpen && (
        <div className="md:hidden bg-white shadow-md border-t border-gray-200">
          <ul className="flex flex-col px-6 py-4 space-y-3 text-gray-700 font-medium">
            {(isLoggedIn && user.role === "STUDENT") && (
              <Link to="/student" onClick={() => setMenuOpen(false)}>
                Dashboard
              </Link>
            )
            }
            {isLoggedIn && user?.role?.toUpperCase().includes("STUDENT") && (
              <Link to="/mycourses" onClick={() => setMenuOpen(false)}>
                My Courses
              </Link>
            )}

            {isLoggedIn && user?.role?.toUpperCase().includes("INSTRUCTOR") && (
              <Link to="/instructor" onClick={() => setMenuOpen(false)}>
                Courses
              </Link>
            )}

            <Link to="/about" onClick={() => setMenuOpen(false)}>
              About
            </Link>
            {!isLoggedIn ? (
              <Link
                to="/login"
                className="text-blue-600"
                onClick={() => setMenuOpen(false)}
              >
                Login
              </Link>
            ) : (
              <LogoutButton />
            )}
          </ul>
        </div>
      )}
    </nav>
  );
}

export default Navbar;
