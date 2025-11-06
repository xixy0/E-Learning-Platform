import React, { useEffect, useState } from "react";
import toast from "react-hot-toast";
import api from "../../services/api";

function SubmissionReports() {
  const [submissions, setSubmissions] = useState([]);

  useEffect(() => {
    fetchSubmissions();
  }, []);

  const fetchSubmissions = async () => {
    try {
      const response = await api.get("/users/getSubmissions");
      setSubmissions(response.data);
    } catch (error) {
      toast.error(
        error?.response?.data?.message ||
          error?.response?.statusText ||
          error?.message
      );
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 p-6">
      <div className="max-w-5xl mx-auto bg-white shadow-xl rounded-2xl p-6">
        <h2 className="text-2xl font-bold text-gray-800 mb-6 border-b pb-3">
          📊 Submission Reports
        </h2>

        {submissions.length > 0 ? (
          <div className="overflow-x-auto">
            <table className="w-full border-collapse bg-white">
              <thead className="bg-blue-100 text-gray-700 uppercase text-sm">
                <tr>
                  <th className="py-3 px-6 text-left">Quiz Name</th>
                  <th className="py-3 px-6 text-left">Score</th>
                  <th className="py-3 px-6 text-left">Timestamp</th>
                </tr>
              </thead>
              <tbody>
                {submissions.map((submission) => (
                  <tr
                    key={submission.submissionId}
                    className="border-b hover:bg-gray-50 transition-colors"
                  >
                    <td className="py-3 px-6">
                      {submission.quizId || "N/A"}
                    </td>
                    <td className="py-3 px-6 font-medium text-blue-600">
                      {submission.score ?? 0}
                    </td>
                    <td className="py-3 px-6 text-gray-600">
                      {new Date(submission.timestamp).toLocaleString()}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        ) : (
          <div className="text-center py-10 text-gray-500">
            <p className="text-lg">No submissions available</p>
          </div>
        )}
      </div>
    </div>
  );
}

export default SubmissionReports;
