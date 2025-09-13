import WelcomeScreen from "../screens/WelcomeScreen";
import MainScreen from "../screens/MainScreen";
import React from "react";
import { useAuth } from "../contexts/AuthContext";

export default function App() {
  const [isLoggedIn, setIsLoggedIn] = React.useState(false);

  //event handlers
  const handleLoginSuccess = () => setIsLoggedIn(true);
  const handleLogout = () => setIsLoggedIn(false);

  //based on isLoggedIn, we will show either main or welcome screen
  return isLoggedIn ? (
    <MainScreen onLogout={handleLogout} />
  ) : (
    <WelcomeScreen onLoginSuccess={handleLoginSuccess} />
  );
}