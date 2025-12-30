import React, { useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom';
import api from '../../services/api';
import toast from 'react-hot-toast';

function EditCourse() {

    const navigate = useNavigate();
    const { courseId } = useParams();


    const [formData, setFormData] = useState({
        courseTitle: "",
        courseDescription: "",
        courseCategory: "",
        imageUrl: ""
    });

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };


    const handleEditCourse = async () => {
        try {
            const payload = { ...formData };

            console.log(payload)

            await api.post(
                `/course/updateCourse/${courseId}`,
                payload);

            toast.success("Course updated");
            navigate(-1, { state: { refresh: true } });
            setFormData({
                courseTitle: "",
                courseDescription: "",
                courseCategory: "",
                imageUrl: ""

            });
        } catch (err) {
            console.error("Failed to update Course:", err);
            toast.error("Failed to update Course");
        }
    };

    return (
        <div className="flex justify-center items-center  min-h-screen  bg-gradient-to-br from-red-50 to-blue-100  p-5">
            <div className=" w-full max-w-4xl bg-white shadow-md rounded-lg p-8">
                <h3 className="text-2xl font-semibold text-blue-600 mb-6 text-center">
                    Update Course
                </h3>

                <form
                    className="space-y-5"
                    onSubmit={(e) => {
                        e.preventDefault();
                        handleEditCourse();
                    }}
                >

                    <div className="grid grid-cols-1 gap-4">
                        <div className="flex flex-col">
                            <label className="text-sm font-medium text-gray-700 mb-1">
                                Course Title
                            </label>
                            <input
                                name="courseTitle"
                                value={formData.courseTitle}
                                onChange={handleInputChange}
                                placeholder="Course Title"
                                className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                            />
                        </div>
                        <div className="flex flex-col">
                            <label className="text-sm font-medium text-gray-700 mb-1">
                                Course Descriptipon
                            </label>
                            <textarea
                                name="courseDescription"
                                value={formData.courseDescription}
                                onChange={handleInputChange}
                                rows="4"
                                className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                                required
                            ></textarea>
                        </div>
                        <label className="text-sm font-medium text-gray-700 mb-1">
                            Course Category
                        </label>
                        <input
                            name="courseCategory"
                            value={formData.courseCategory}
                            onChange={handleInputChange}
                            placeholder="Course Category"
                            className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                        />
                        <label className="text-sm font-medium text-gray-700 mb-1">
                            Course Image Url
                        </label>
                        <input
                            name="imageUrl"
                            value={formData.imageUrl}
                            onChange={handleInputChange}
                            placeholder="Image Url"
                            className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                        />


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


export default EditCourse