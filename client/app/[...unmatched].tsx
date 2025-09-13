import { useEffect } from "react";
import { router } from "expo-router";

export default function UnmatchedRoute() {
  useEffect(() => {
    // Redirect to root (home) screen
    router.replace("/");
  }, []);

  return null;
}