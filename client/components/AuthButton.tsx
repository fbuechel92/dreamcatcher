import React from 'react';
import { View, Text, TouchableOpacity, Alert, StyleSheet } from 'react-native';
import * as AuthSession from 'expo-auth-session';
import * as WebBrowser from 'expo-web-browser';
import { auth0Config } from '../services/auth0';

// Complete the auth session
WebBrowser.maybeCompleteAuthSession();

interface User {
  name?: string;
  email?: string;
  sub?: string;
}

export default function AuthButton() {
  const [user, setUser] = React.useState<User | null>(null);

  // First, let's see what redirect URI is being generated
  const redirectUri = AuthSession.makeRedirectUri({
    scheme: 'exp'
  });
  
  console.log('Redirect URI:', redirectUri);

  // Configure the auth request
  const [request, result, promptAsync] = AuthSession.useAuthRequest(
  {
    clientId: auth0Config.clientId,
    scopes: ['openid', 'profile', 'email'],
    responseType: AuthSession.ResponseType.Token,
    redirectUri: redirectUri, // Use the variable instead of inline
    extraParams: {
      audience: `https://${auth0Config.domain}/api/v2/`,
    },
  },
  {
    authorizationEndpoint: `https://${auth0Config.domain}/authorize`,
  }
);


  React.useEffect(() => {
    if (result) {
      if (result.type === 'success') {
        // Get user info
        fetch(`https://${auth0Config.domain}/userinfo`, {
          headers: {
            Authorization: `Bearer ${result.params.access_token}`,
          },
        })
        .then(response => response.json())
        .then(userInfo => {
          setUser(userInfo);
          Alert.alert('Success', `Welcome ${userInfo.name}!`);
        })
        .catch(error => {
          console.error('User info error:', error);
          Alert.alert('Error', 'Failed to get user info');
        });
      } else if (result.type === 'error') {
        Alert.alert('Error', 'Login failed');
      }
    }
  }, [result]);

  const login = () => {
    promptAsync();
  };

  const logout = async () => {
    await WebBrowser.openAuthSessionAsync(
      `https://${auth0Config.domain}/v2/logout?client_id=${auth0Config.clientId}&returnTo=${encodeURIComponent(redirectUri)}`,
      redirectUri
    );
    
    setUser(null);
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
  container: { padding: 20 },
  button: { backgroundColor: '#007bff', padding: 15, borderRadius: 5 },
  buttonText: { color: 'white', textAlign: 'center', fontSize: 16 },
});