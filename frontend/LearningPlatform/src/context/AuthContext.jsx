import { createContext, useState, useEffect, useContext } from "react";
import { useNavigate } from "react-router-dom";
import toast from "react-hot-toast";
import api from "../services/api";


export const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [isLoggedIn, setIsLoggedIn] = useState(!!localStorage.getItem("token"));
  const [user, setUser] = useState(null);
  const navigate = useNavigate();

  const fetchLoggedInUser = async () => {
    const token = localStorage.getItem("token");
    if (!token) return;
    try {
      const response = await api.get("/users/getLoggedInUser");
      const userData = response.data;
      setUser(userData);
      localStorage.setItem("authUser", JSON.stringify(userData));
    } catch (error) {
      console.error("Error getting logged-in user", error);
      logout();
    }
  };

  useEffect(() => {
    if (localStorage.getItem("token")) {
      fetchLoggedInUser();
    }
  }, []);

  useEffect(() => {
    const updateAuth = () => {
      setIsLoggedIn(!!localStorage.getItem("token"));
      const storedUser = localStorage.getItem("authUser");
      setUser(storedUser ? JSON.parse(storedUser) : null);
    };

    window.addEventListener("authChange", updateAuth);
    return () => window.removeEventListener("authChange", updateAuth);
  }, []);



  const login = (token) => {
    try{
    localStorage.setItem("token", token);
    setIsLoggedIn(true);
    fetchLoggedInUser();

    window.dispatchEvent(new Event("authChange"));
    toast.success("Successfully Logged In!");
    navigate(`${userData.role.toLowerCase()}`);
    }catch(error){
      "Login failed :"+
      (error?.response?.data?.message || 
        error?.response?.statusText ||
        error?.message
      )
    }
  };


  const logout = () => {
    localStorage.removeItem("token");
    setIsLoggedIn(false);

    window.dispatchEvent(new Event("authChange"));
    toast.success("Successfully Logged Out!");
    navigate("/");
  };

  const value = { isLoggedIn, user, login, logout };

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
}


export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
};