import WelcomeScreen from "../screens/WelcomeScreen";
import MainScreen from "../screens/MainScreen";
import React from "react";

export default function App() {
  const [isLoggedIn, setIsLoggedIn] = React.useState(false);

  const handleLoginSuccess = () => setIsLoggedIn(true);
  const handleLogout = () => setIsLoggedIn(false);

  return isLoggedIn ? (
    <MainScreen onLogout={handleLogout} />
  ) : (
    <WelcomeScreen onLoginSuccess={handleLoginSuccess} />
  );
}