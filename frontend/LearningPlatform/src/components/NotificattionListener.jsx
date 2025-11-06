import React, { useEffect, useRef } from "react";
import { useAuth } from "../context/AuthContext";
import toast from "react-hot-toast";
import { EventSourcePolyfill } from "event-source-polyfill";

export default function NotificationListener() {
  const { user } = useAuth();
  const eventSourceRef = useRef(null);

  useEffect(() => {
    if (!user || !localStorage.getItem("token")) {
      console.warn("User not logged in — skipping SSE setup.");
      return;
    }

    if (eventSourceRef.current) return;

    const token = localStorage.getItem("token");
    console.log("Connecting to SSE with token:", token);

    const eventSource = new EventSourcePolyfill(
      "http://localhost:8081/api/notifications/subscribe",
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        withCredentials: true,
      }
    );

    eventSource.addEventListener("notification", (event) => {
      const notification = JSON.parse(event.data);
      console.log("🔔 Notification:", notification);
      toast.success(notification.message || notification.title || "New notification!");
    });

    eventSource.onerror = (err) => {
      console.error("❌ SSE error:", err);
      setTimeout(() => {
        window.location.reload(); // or reinitialize EventSource
      }, 5000);
    };

    eventSourceRef.current = eventSource;

    return () => {
      eventSource.close();
      eventSourceRef.current = null;
    };
  }, [user]);

  return null;
}
