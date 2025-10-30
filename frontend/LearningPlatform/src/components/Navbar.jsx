import { Link } from "react-router-dom";
import LogoutButton from "./LogoutButton";
import { useAuth } from "../context/AuthContext";
import { useState, useEffect, useRef } from "react";

function Navbar() {
  const { isLoggedIn } = useAuth();
  const [menuOpen, setMenuOpen] = useState(false);
  const dropdownRef = useRef(null);

  // Close menu when clicking outside
  useEffect(() => {
    const handleClickOutside = (event) => {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
        setMenuOpen(false);
      }
    };
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  return (
    <nav className="fixed top-0 left-0 w-full z-50 bg-white/80 backdrop-blur-md shadow-md">
      <div className="max-w-7xl mx-auto flex items-center justify-between px-6 py-3">
        
        {/* Left Section - Dropdown Menu */}
        <div className="relative" ref={dropdownRef}>
          <button
            onClick={() => setMenuOpen((prev) => !prev)}
            className="p-2 rounded-lg hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-300 transition"
            aria-label="Menu"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              className="h-6 w-6 text-gray-700"
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

          {/* Dropdown Content */}
          {menuOpen && (
            <ul className="absolute left-0 mt-2 w-40 bg-white border border-gray-200 rounded-lg shadow-lg py-2 animate-fade-in">
              {isLoggedIn && (
                <li>
                  <Link
                    to="/userdetails"
                    className="block px-4 py-2 text-gray-700 hover:bg-blue-50 rounded-md transition"
                    onClick={() => setMenuOpen(false)}
                  >
                    Dashboard
                  </Link>
                </li>
              )}
              <li>
                <Link
                  to="/about"
                  className="block px-4 py-2 text-gray-700 hover:bg-blue-50 rounded-md transition"
                  onClick={() => setMenuOpen(false)}
                >
                  About
                </Link>
              </li>
            </ul>
          )}
        </div>

        {/* Center Section - Brand */}
        <div className="text-center">
          <Link
            to="/"
            className="text-2xl font-semibold text-blue-700 hover:text-blue-800 transition-colors"
          >
            LearnIT<span className="text-blue-500">.</span>
          </Link>
        </div>

        {/* Right Section - Login / Logout */}
        <div className="flex items-center space-x-3">
          {!isLoggedIn && (
            <Link
              to="/login"
              className="p-2 rounded-full hover:bg-blue-100 transition focus:outline-none focus:ring-2 focus:ring-blue-300"
              title="Login"
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
          )}
          {isLoggedIn && <LogoutButton />}
        </div>
      </div>
    </nav>
  );
}

export default Navbar;
