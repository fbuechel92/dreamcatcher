import WelcomeScreen from "../screens/WelcomeScreen";
import MainScreen from "../screens/MainScreen";
import React from "react";
import { useAuth } from "../contexts/AuthContext";

export default function App() {
  const { setAccessToken } = useAuth();

  // Handler for logout
  const handleLogout = () => {
    setAccessToken(null);
  };

  // Handler for login success (after Auth0 login)
  const handleLoginSuccess = (token: string) => {
    setAccessToken(token);
  };

  return useAuth().isLoggedIn ? (
    <MainScreen onLogout={handleLogout} />
  ) : (
    <WelcomeScreen onLoginSuccess={handleLoginSuccess} />
  );
}