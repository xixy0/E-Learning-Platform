import React, { useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom';
import api from '../../services/api';
import toast from 'react-hot-toast';

function AddAssignment() {

  const navigate = useNavigate();
  const { courseId } = useParams();

  const [formData, setFormData] = useState({
    assignmentTitle: "",
    assignmentDescription: "",
    file: null,
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleFileChange = (e) => {
    setFormData({ ...formData, file: e.target.files[0] });
  };

  const handleAddAssignment = async () => {
    try {
      const payload = { ...formData };

      await api.post(
        `/assignment/addAssignment/${courseId}`,
        payload,
        {
          headers: { "Content-Type": "multipart/form-data" },
        });

      toast.success("Assignement added Successfully!");
      navigate(-1, { state: { refresh: true } });
      setFormData({
        assignmentTitle: "",
        assignmentDescription: "",
        file: null
      });
    } catch (err) {
      console.error("Failed to add Assignment:", err);
      toast.error("Failed to add Assignment");
    }
  };

  return (
    <div className="flex justify-center items-center  min-h-screen  bg-gradient-to-br from-red-50 to-blue-100  p-5">
      <div className=" w-full max-w-4xl bg-white shadow-md rounded-lg p-8">
        <h3 className="text-2xl font-semibold text-blue-600 mb-6 text-center">
          Add Assignment
        </h3>

        <form
          className="space-y-5"
          onSubmit={(e) => {
            e.preventDefault();
            handleAddAssignment();
          }}
        >

          <div className="grid grid-cols-1 gap-4">
            <div className="flex flex-col">
              <label className="text-sm font-medium text-gray-700 mb-1">
                Assignment Title
              </label>
              <input
                name="assignmentTitle"
                value={formData.assignmentTitle}
                onChange={handleInputChange}
                placeholder="Assignment Title"
                className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                required
              />
            </div>
            <div className="flex flex-col">
              <label className="text-sm font-medium text-gray-700 mb-1">
                Assignmet Descriptipon
              </label>
              <textarea
                name="assignmentDescription"
                value={formData.assignmentDescription}
                onChange={handleInputChange}
                rows="4"
                className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                required
              ></textarea>
            </div>
            <div>
              <label className="block text-gray-700 font-medium mb-2">
                Upload Assignment PDF
              </label>
              <input
                type="file"
                accept=".pdf"
                onChange={handleFileChange}
                className="w-full max-w-xl text-gray-700 border border-black rounded-lg"
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
      </div >
    </div >
  );
}


export default AddAssignment