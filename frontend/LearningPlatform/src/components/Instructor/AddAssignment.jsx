import React from 'react'

function AddAssignment() {
  const { user, setUser } = useAuth();
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    assignmentTitle: "",
    assignmentDescription: "",
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleUpdateUser = async () => {
    try {
      const payload = { ...formData };
      const { data: updatedUser } = await api.post(`/assignment/addAssignment/${courseId}`, payload);
      setUser(updatedUser);
      toast.success("Assignement added Successfully!");
      navigate("/", { state: { refresh: true } });

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
        newpassword: "",
      });
    } catch (err) {
      console.error("Failed to add Assignment:", err);
      toast.error("Failed to add Assignment");
    }
  };

  return (
    <div className="flex justify-center items-center min-h-screen  bg-gradient-to-br from-red-50 to-blue-100  p-5">
      <div className="w-full max-w-3xl bg-white shadow-md rounded-lg p-8">
        <h3 className="text-2xl font-semibold text-blue-600 mb-6 text-center">
          Add Assignment
        </h3>

        <form
          className="space-y-5"
          onSubmit={(e) => {
            e.preventDefault();
            handleUpdateUser();
          }}
        >
          {/* --- Name Fields --- */}
          <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
            <div className="flex flex-col">
              <label className="text-sm font-medium text-gray-700 mb-1">
                First Name
              </label>
              <input
                name="assignmentTitle"
                value={formData.assignmentTitle}
                onChange={handleInputChange}
                placeholder="Assignment Title"
                className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>

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

            <div className="flex flex-col">
              <label className="text-sm font-medium text-gray-700 mb-1">
                Last Name
              </label>
              <input
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
                placeholder="Username"
                name="username"
                value={formData.username}
                onChange={handleInputChange}
                className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>

            <div className="flex flex-col">
              <label className="text-sm font-medium text-gray-700 mb-1">
                Old Password
              </label>
              <input
                type="password"
                name="password"
                value={formData.password}
                onChange={handleInputChange}
                placeholder="Old Password"
                pattern="(?=.*\d)(?=.*[a-z]).{5,}"
                title="Min 5 characters with letters and numbers"
                className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>

            <div className="flex flex-col">
              <label className="text-sm font-medium text-gray-700 mb-1">
                New Password
              </label>
              <input
                type="password"
                name="newpassword"
                value={formData.newpassword}
                onChange={handleInputChange}
                placeholder="New Password"
                className={`border rounded-lg px-3 py-2 focus:outline-none focus:ring-2 ${formData.password &&
                    formData.newpassword &&
                    formData.password === formData.newpassword
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
              onClick={() => navigate(-1)}
              className="px-5 py-2 border border-gray-300 text-gray-600 rounded-lg hover:bg-gray-100 transition"
            >
              Cancel
            </button>
            <button
              type="submit"
              className="px-5 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition"
            >
              Submit
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}


export default AddAssignment