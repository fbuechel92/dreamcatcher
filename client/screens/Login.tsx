import React from 'react';
import { View, Text, TouchableOpacity, Alert, StyleSheet } from 'react-native';
import auth0 from '../services/auth0';

// Define the user type
interface User {
  name?: string;
  email?: string;
  sub?: string;
}

export default function AuthButton() {
  const [user, setUser] = React.useState<User | null>(null);

  const login = async () => {
    try {
      const credentials = await auth0.webAuth.authorize({
        scope: 'openid profile email',
      });
      
      // Get user info
      const userInfo = await auth0.auth.userInfo({
        token: credentials.accessToken,
      });
      
      setUser(userInfo as User); 
      Alert.alert('Success', `Welcome ${userInfo.name}!`);
    } catch (error) {
      Alert.alert('Error', 'Login failed');
      console.log(error);
    }
  };

  const logout = async () => {
    try {
      await auth0.webAuth.clearSession();
      setUser(null);
      Alert.alert('Success', 'Logged out');
    } catch (error) {
      Alert.alert('Error', 'Logout failed');
    }
  };

  return (
    <View style={styles.container}>
      {user ? (
        <View>
          <Text>Welcome, {user.name}!</Text>
          <TouchableOpacity style={styles.button} onPress={logout}>
            <Text style={styles.buttonText}>Logout</Text>
          </TouchableOpacity>
        </View>
      ) : (
        <TouchableOpacity style={styles.button} onPress={login}>
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