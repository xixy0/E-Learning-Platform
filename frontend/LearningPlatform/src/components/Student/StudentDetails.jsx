import React from "react";
import { Link } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";

function StudentDetails() {
  const { user } = useAuth();

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-blue-100 flex flex-col items-center py-10">
      <div className="bg-white shadow-xl rounded-2xl p-8 w-full max-w-md border border-gray-200">
        <h1 className="text-3xl font-semibold text-blue-700 mb-6 text-center">
          Student Profile
        </h1>

        <div className="space-y-4 text-gray-700">
          <div className="flex justify-between border-b pb-2">
            <span className="font-semibold text-blue-700">User ID:</span>
            <span>{user.userId}</span>
          </div>

          <div className="flex justify-between border-b pb-2">
            <span className="font-semibold text-blue-700">Name:</span>
            <span>
              {user.firstName}{" "}
              {user.middleName ? user.middleName + " " : ""}
              {user.lastName}
            </span>
          </div>

          <div className="flex justify-between border-b pb-2">
            <span className="font-semibold text-blue-700">Email:</span>
            <span>{user.email}</span>
          </div>
        </div>

        <div className="mt-8 text-center">
          <Link
            to="/editProfile"
            className="inline-block bg-blue-600 text-white px-6 py-2 rounded-lg font-semibold hover:bg-blue-700 transition duration-200"
          >
            Update Profile
          </Link>
        </div>
      </div>
    </div>
  );
}

export default StudentDetails;
