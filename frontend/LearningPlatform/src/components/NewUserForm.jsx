import { useState } from "react";
import api from "../services/api";
import { toast } from "react-hot-toast";
import { useNavigate } from "react-router-dom";

function NewUserForm() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    firstName: "",
    middleName: "",
    lastName: "",
    userDOB: "",
    gender: "",
    phoneNum: "",
    address: "",
    email: "",
    username: "",
    password: "",
    confirmpassword: "",
    role: "STUDENT",
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleAddUser = async () => {
    if (formData.password !== formData.confirmpassword) {
      toast.error("Passwords do not match");
      return;
    }

    try {
      const payload = { ...formData };
      delete payload.confirmpassword;

      await api.post("/users/register", payload);
      toast.success("User Added Successfully!");
      navigate("/userdetails", { state: { refresh: true } });

      setFormData({
        firstName: "",
        middleName: "",
        lastName: "",
        userDOB: "",
        gender: "",
        phoneNum: "",
        address: "",
        email: "",
        username: "",
        password: "",
        confirmpassword: "",
        role: "STUDENT",
      });
    } catch (err) {
      console.error("Failed to add user:", err);
      toast.error("Failed to add user");
    }
  };

  return (
    <div className="flex justify-center items-center min-h-screen bg-gray-50">
      <div className="w-full max-w-3xl bg-white shadow-md rounded-lg p-8">
        <h3 className="text-2xl font-semibold text-gray-800 mb-6">
          Add New User
        </h3>

        <form
          className="space-y-5"
          onSubmit={(e) => {
            e.preventDefault();
            handleAddUser();
          }}
        >
          {/* --- Name Fields --- */}
          <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
            <label htmlFor="firstName">First Name</label>
            <input
              required
              name="firstName"
              value={formData.firstName}
              onChange={handleInputChange}
              placeholder="First Name"
              className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <label htmlFor="middleName">Middle Name</label>
<input
              name="middleName"
              value={formData.middleName}
              onChange={handleInputChange}
              placeholder="Middle Name"
              className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <label htmlFor="lastName">Last Name</label>
            <input
              required
              name="lastName"
              value={formData.lastName}
              onChange={handleInputChange}
              placeholder="Last Name"
              className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          {/* --- DOB and Gender --- */}
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
            <label htmlFor="userDOB">Date Of Birth</label>
            <input
              type="date"
              name="userDOB"
              value={formData.userDOB}
              onChange={handleInputChange}
              required
              className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
           
            <label htmlFor="gender">Gender</label>
            <select
              name="gender"
              value={formData.gender}
              onChange={handleInputChange}
              required
              className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              <option value="">Select Gender</option>
              <option value="Male">Male</option>
              <option value="Female">Female</option>
              <option value="Other">Other</option>
            </select>
          </div>

          {/* --- Phone --- */}
            <label htmlFor="phoneNum">Phone Number</label>
          <input
            required
            name="phoneNum"
            type="tel"
            pattern="[0-9]{10}"
            title="Enter a valid 10-digit phone number"
            value={formData.phoneNum}
            onChange={handleInputChange}
            placeholder="Phone Number"
            className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
          />

          {/* --- Address --- */}
            <label htmlFor="address">Address</label>
          <textarea
            required
            name="address"
            value={formData.address}
            onChange={handleInputChange}
            placeholder="Address"
            className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            rows="3"
          ></textarea>

          {/* --- Username + Passwords --- */}
            <label htmlFor="username">Username</label>
          <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
            <input
              type="text"
              required
              placeholder="Username"
              name="username"
              value={formData.username}
              onChange={handleInputChange}
              className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <label htmlFor="password">Password</label>
            <input
              type="password"
              name="password"
              value={formData.password}
              onChange={handleInputChange}
              placeholder="Password"
              required
              pattern="(?=.*\d)(?=.*[a-z]).{5,}"
              title="Min 5 characters with letters and numbers"
              className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <label htmlFor="confirmpassword">Confirm Password</label>
            <input
              type="password"
              name="confirmpassword"
              value={formData.confirmpassword}
              onChange={handleInputChange}
              placeholder="Confirm Password"
              required
              className={`border rounded-lg px-3 py-2 focus:outline-none focus:ring-2 ${formData.password &&
                formData.confirmpassword &&
                formData.password !== formData.confirmpassword
                ? "border-red-500 focus:ring-red-400"
                : "border-gray-300 focus:ring-blue-500"
                }`}
            />
          </div>

          {/* --- Buttons --- */}
          <div className="flex justify-end gap-4 pt-4">
            <button
              type="button"
              onClick={() => navigate("/login")}
              className="px-5 py-2 border border-gray-300 text-gray-600 rounded-lg hover:bg-gray-100 transition"
            >
              Cancel
            </button>
            <button
              type="submit"
              className="px-5 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition"
            >
              Add User
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default NewUserForm;
