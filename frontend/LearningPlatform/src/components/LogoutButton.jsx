import { useContext } from "react";
import toast from "react-hot-toast";
import { useAuth } from "../context/AuthContext";

function LogoutButton() {
  
  const {logout} = useAuth();


  return (
    <div>
      <button
        className="p-2 rounded-full hover:bg-blue-100 transition focus:outline-none focus:ring-2 focus:ring-blue-300"
        onClick={logout}
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 24 24"
          strokeWidth={1.5}
          stroke="currentColor"
          className="size-6"
        >
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            d="M15.75 9V5.25A2.25 2.25 0 0 0 13.5 3h-6a2.25 2.25 0 0 0-2.25 2.25v13.5A2.25 2.25 0 0 0 7.5 21h6a2.25 2.25 0 0 0 2.25-2.25V15m3 0 3-3m0 0-3-3m3 3H9"
          />
        </svg>
      </button>
    </div>
  );
}

export default LogoutButton;
