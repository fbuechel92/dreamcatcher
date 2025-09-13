import React, { createContext, useContext, useState, ReactNode, useEffect } from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';

interface AuthContextType {
  accessToken: string | null;
  setAccessToken: (token: string | null) => void;
  user: any;
  setUser: (user: any) => void;
  isLoggedIn: boolean;
}

export const AuthContext = createContext<AuthContextType>({
  accessToken: null,
  setAccessToken: () => {},
  user: null,
  setUser: () => {},
  isLoggedIn: false,
});

export function AuthProvider({ children }: { children: ReactNode }) {
  const [accessToken, setAccessTokenState] = useState<string | null>(null);
  const [user, setUser] = useState<any>(null);
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  // Load token from storage on mount
  useEffect(() => {
    AsyncStorage.getItem('accessToken').then(token => {
      if (token) {
        setAccessTokenState(token);
        setIsLoggedIn(true);
      }
    });
  }, []);

  // Persist token and update login state
  const setAccessToken = async (token: string | null) => {
    setAccessTokenState(token);
    if (token) {
      await AsyncStorage.setItem('accessToken', token);
      setIsLoggedIn(true);
    } else {
      await AsyncStorage.removeItem('accessToken');
      setIsLoggedIn(false);
    }
  };

  return (
    //making authentication type available to child components
    <AuthContext.Provider value={{ accessToken, setAccessToken, user, setUser, isLoggedIn }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  return useContext(AuthContext);
}