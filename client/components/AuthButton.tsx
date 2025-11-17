import React from 'react';
import { View, Text, TouchableOpacity, Alert, StyleSheet } from 'react-native';
import * as AuthSession from 'expo-auth-session';
import * as WebBrowser from 'expo-web-browser';
import { auth0Config } from '../services/auth0';
import { useAuth } from '../contexts/AuthContext';
import { fetchUserInfo, createOrCheckUser } from '../services/userService';

// Complete the auth session
WebBrowser.maybeCompleteAuthSession();

interface User {
  name?: string;
  email?: string;
  sub?: string;
}

interface AuthButtonProps {
  onLoginSuccess?: (token: string) => void;
  onLogout?: () => void;
}

export default function AuthButton({ onLoginSuccess, onLogout }: AuthButtonProps) {
  const { setAccessToken } = useAuth();
  const [user, setUser] = React.useState<User | null>(null);

  // First, let's see what redirect URI is being generated
  const redirectUri = AuthSession.makeRedirectUri({
    scheme: 'dreamcatcher',
    path: 'callback'
  });
  console.log(redirectUri);

  // Configure the auth request
  const [request, result, promptAsync] = AuthSession.useAuthRequest(
  {
    clientId: auth0Config.clientId,
    scopes: ['openid', 'profile', 'email'],
    responseType: AuthSession.ResponseType.Token,
    redirectUri: redirectUri, // Use the variable instead of inline
    extraParams: {
      audience: `https://${auth0Config.domain}/api/v2/`,
      prompt: 'login',
    },
  },
  {
    authorizationEndpoint: `https://${auth0Config.domain}/authorize`,
  }
);


React.useEffect(() => {
const handleAuth = async () => {
  if (result && result.type === 'success') {
    const accessToken = result.params.access_token;

    console.log('Access Token:', accessToken);
    console.log('Token length:', accessToken?.length);

    setAccessToken(accessToken);

    try {
      // Get user info from Auth0
      const userInfo = await fetchUserInfo(accessToken);
      setUser(userInfo);

      // Directly call the backend to check and create the user
      await createOrCheckUser(accessToken, userInfo.email);

      onLoginSuccess?.(accessToken);
    } catch (error) {
      console.error('Auth error:', error);
      Alert.alert('Error', 'Authentication failed');
    }
  } else if (result && result.type === 'error') {
    Alert.alert('Error', 'Login failed');
  }
};

  handleAuth();
}, [result, onLoginSuccess]);
   

  const login = () => {
    promptAsync();
  };

  const logout = async () => {    
    setUser(null);
    onLogout?.();
    Alert.alert('Success', 'Logged out');
  };

  return (
    <View style={styles.container}>
      {user ? (
        <View>
          <Text>Welcome, {user.name || 'User'}!</Text>
          <TouchableOpacity style={styles.button} onPress={logout}>
            <Text style={styles.buttonText}>Logout</Text>
          </TouchableOpacity>
        </View>
      ) : (
        <TouchableOpacity 
          style={styles.button} 
          onPress={login}
          disabled={!request}
        >
          <Text style={styles.buttonText}>Login with Auth0</Text>
        </TouchableOpacity>
      )}
    </View>
  );
}



const styles = StyleSheet.create({
  container: { 
    padding: 20,
    alignItems: 'center',
  },
  button: { 
    backgroundColor: 'rgba(255, 255, 255, 0.2)',
    borderColor: 'rgba(255, 255, 255, 0.6)',
    borderWidth: 1,
    borderRadius: 25,
    paddingHorizontal: 30,
    paddingVertical: 15,
    alignItems: 'center',
    minWidth: 200,
  },
  buttonText: { 
    color: 'white', 
    textAlign: 'center', 
    fontSize: 18,
  },
  logoutButton: {
    backgroundColor: 'rgba(255, 100, 100, 0.3)',
    borderColor: 'rgba(255, 100, 100, 0.6)',
    borderWidth: 1,
    borderRadius: 25,
    paddingHorizontal: 25,
    paddingVertical: 12,
    alignItems: 'center',
  },
});