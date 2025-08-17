import React from 'react';
import WelcomeScreen from "../screens/WelcomeScreen";
import MainScreen from "../screens/MainScreen";
import { AuthProvider } from '../contexts/AuthContext';

export default function App() {
  
  const [isLoggedIn, setIsLoggedIn] = React.useState(false);

  const handleLoginSuccess = () => {
    setIsLoggedIn(true);
  };

  const handleLogout = () => {
    setIsLoggedIn(false);
  };
  
  return(
    <AuthProvider>
      {isLoggedIn ? 
        <MainScreen onLogout={handleLogout} /> : 
        <WelcomeScreen onLoginSuccess={handleLoginSuccess} />
      }
    </AuthProvider>
  );
}