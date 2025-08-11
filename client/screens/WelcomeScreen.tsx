import React from 'react';
import { StyleSheet, Text, ImageBackground, View, TouchableOpacity } from 'react-native';
import { router } from 'expo-router';
import AuthButton from '../components/AuthButton';

export default function MainScreen() {
  
  const handleSuccessfulLogin = () => {
    router.replace('/');
  };
  
  return (
    <ImageBackground 
      source={require('../assets/images/nightsky_milky.png')} 
      style={styles.backgroundImage}
      resizeMode="cover"
    >
      <View style={styles.container}>

        <View style={styles.upperSection}>
            <Text style={styles.title}>Dreamcatcher</Text>
        </View>

        <View style={styles.lowerSection}>
            <AuthButton onLoginSuccess={handleSuccessfulLogin}/>
        </View>
        
      </View>
    </ImageBackground>
  );
}

const styles = StyleSheet.create({
  backgroundImage: {
    flex: 1,
  },
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: 'transparent',
  },
    upperSection: {
    backgroundColor: 'transparent',
  },
  lowerSection: {
    backgroundColor: 'transparent',
    justifyContent: "center",
    flex: 1,
    marginBottom: 100
  },
  title: {
    textAlign: 'center',
    marginBottom: 40,
    color: 'rgb(255,255,255)',
    fontSize: 30,
    fontWeight: 'bold',
    fontFamily: 'Bradley Hand',
  },
});