import { useNavigate } from "react-router-dom";
import toast from "react-hot-toast";

function LogoutButton() {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("token");

    toast.success("Successfully Logged Out!");
    window.dispatchEvent(new Event("authChange"));

    setTimeout(() => {
      navigate("/mainpage");
    }, 200);
  };

  return (
    <div className="tooltip tooltip-left" data-tip="Logout">
      <button
        className="btn btn-ghost btn-error btn-circle mr-2 hover:text-error-content"
        onClick={handleLogout}
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
