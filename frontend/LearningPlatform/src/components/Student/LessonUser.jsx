import React, { useEffect, useState } from "react";
import toast from "react-hot-toast";
import { useParams } from "react-router-dom";
import api from "../../services/api";

function LessonUser() {
    const { lessonId } = useParams();
    const [lesson, setLesson] = useState(null);

    useEffect(() => {
        fetchLesson();
    }, [lessonId]);

    const fetchLesson = async () => {
        try {
            const response = await api.get(`/lesson/getLessonById/${lessonId}`);
            setLesson(response.data);
        } catch (error) {
            toast.error(
                "Failed to load lesson details: " +
                (error?.response?.data?.message ||
                    error?.response?.statusText ||
                    error.message)
            );
        }
    };

    const getEmbedUrl = (videoUrl) => {
        if (!videoUrl) return "";
        let videoId = "";
        if (videoUrl.includes("youtube.com/watch?v=")) {
            videoId = videoUrl.split("v=")[1].split("&")[0];
        } else if (videoUrl.includes("youtu.be/")) {
            videoId = videoUrl.split("youtu.be/")[1].split("?")[0];
        }
        return `https://www.youtube.com/embed/${videoId}`;
    };

    if (!lesson)
        return (
            <p className="text-center text-gray-600 mt-10 animate-pulse">
                Loading lesson...
            </p>
        );

    return (
        <div className="min-h-screen  bg-gradient-to-br from-blue-50 to-blue-100 flex justify-center py-10 px-4">
            <div className="bg-white shadow-xl rounded-2xl p-8 w-full max-w-5xl border border-gray-200 relative">


                <div className="flex justify-between items-start mb-6">
                    <h1 className="text-3xl font-bold text-blue-700">
                        {lesson.lessonTitle}
                    </h1>

                    {lesson.pdfUrl && (
                        <a
                            href={lesson.pdfUrl}
                            download
                            className="bg-blue-600 text-white px-5 py-2 rounded-lg font-semibold shadow hover:bg-blue-700 transition duration-200"
                        >
                            Download PDF
                        </a>
                    )}
                </div>

                <p className="text-gray-700 text-lg mb-8 text-justify leading-relaxed">
                    {lesson.lessonDescription}
                </p>


                <div className="aspect-w-16 aspect-h-9 mb-8">
                    <iframe
                        src={getEmbedUrl(lesson.videoUrl)}
                        title={lesson.lessonTitle}
                        allowFullScreen
                        className="w-full h-96 rounded-xl border border-gray-300 shadow-md"
                    ></iframe>
                </div>
            </div>
        </div>
    );
}

export default LessonUser;
