import { useState } from "react";
import { toast } from "react-hot-toast";
import { useNavigate } from "react-router-dom";
import api from "../../services/api";

function NewUserForm() {
  const navigate = useNavigate();
  


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
      navigate("/login");

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
      toast.error("Failed to add user");
    }
  };

  return (
    <div className="flex justify-center items-center min-h-screen  bg-gradient-to-br from-red-50 to-blue-100  p-5">
      <div className="w-full max-w-4xl bg-white shadow-md rounded-lg p-8">
        <h3 className="text-2xl text-blue-600 font-semibold text-hite mb-6 text-center">
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
            {/* First Name */}
            <div className="flex flex-col">
              <label className="text-sm font-medium text-gray-700 mb-1">
                First Name
              </label>
              <input
                required
                name="firstName"
                value={formData.firstName}
                onChange={handleInputChange}
                placeholder="First Name"
                className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>

            {/* Middle Name */}
            <div className="flex flex-col">
              <label className="text-sm font-medium text-gray-700 mb-1">
                Middle Name
              </label>
              <input
                name="middleName"
                value={formData.middleName}
                onChange={handleInputChange}
                placeholder="Middle Name"
                className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>

            {/* Last Name */}
            <div className="flex flex-col">
              <label className="text-sm font-medium text-gray-700 mb-1">
                Last Name
              </label>
              <input
                required
                name="lastName"
                value={formData.lastName}
                onChange={handleInputChange}
                placeholder="Last Name"
                className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>
          </div>

          {/* --- DOB and Gender --- */}
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
            <div className="flex flex-col">
              <label className="text-sm font-medium text-gray-700 mb-1">
                Date Of Birth
              </label>
              <input
                type="date"
                name="userDOB"
                value={formData.userDOB}
                onChange={handleInputChange}
                required
                className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>

            <div className="flex flex-col">
              <label className="text-sm font-medium text-gray-700 mb-1">
                Gender
              </label>
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
          </div>

          {/* --- Phone --- */}
          <div className="flex flex-col">
            <label className="text-sm font-medium text-gray-700 mb-1">
              Phone Number
            </label>
            <input
              required
              name="phoneNum"
              type="tel"
              pattern="[0-9]{10}"
              title="Enter a valid 10-digit phone number"
              value={formData.phoneNum}
              onChange={handleInputChange}
              placeholder="Phone Number"
              className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          {/* --- Address --- */}
          <div className="flex flex-col">
            <label className="text-sm font-medium text-gray-700 mb-1">
              Address
            </label>
            <textarea
              required
              name="address"
              value={formData.address}
              onChange={handleInputChange}
              placeholder="Address"
              rows="3"
              className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            ></textarea>
          </div>

          {/* --- Email --- */}
          <div className="flex flex-col">
            <label className="text-sm font-medium text-gray-700 mb-1">
              Email
            </label>
            <input
              name="email"
              type="email"
              title="Enter a valid email"
              value={formData.email}
              onChange={handleInputChange}
              placeholder="Email"
              className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          {/* --- Username + Passwords --- */}
          <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
            <div className="flex flex-col">
              <label className="text-sm font-medium text-gray-700 mb-1">
                Username
              </label>
              <input
                type="text"
                required
                placeholder="Username"
                name="username"
                value={formData.username}
                onChange={handleInputChange}
                className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>

            <div className="flex flex-col">
              <label className="text-sm font-medium text-gray-700 mb-1">
                Password
              </label>
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
            </div>

            <div className="flex flex-col">
              <label className="text-sm font-medium text-gray-700 mb-1">
                Confirm Password
              </label>
              <input
                type="password"
                name="confirmpassword"
                value={formData.confirmpassword}
                onChange={handleInputChange}
                placeholder="Confirm Password"
                required
                className={`border rounded-lg px-3 py-2 focus:outline-none focus:ring-2 ${
                  formData.password &&
                  formData.confirmpassword &&
                  formData.password !== formData.confirmpassword
                    ? "border-red-500 focus:ring-red-400"
                    : "border-gray-300 focus:ring-blue-500"
                }`}
              />
            </div>
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
