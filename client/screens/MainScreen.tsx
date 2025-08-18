import React from 'react';
import { StyleSheet, Text, ImageBackground, View, TouchableOpacity } from 'react-native';
import { router } from 'expo-router';
import AuthButton from '../components/AuthButton';

interface MainScreenProps {
  onLogout: () => void;
}

export default function MainScreen({ onLogout }: MainScreenProps) {
  
  const handleCatchDream = () => {
    router.push('/dream');
  };

  const handleArchive = () => {
    router.push('/archive');
  };

  const handleProfile = () => {
    router.push('/profile');
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
          <TouchableOpacity style={styles.logoutButton} onPress={onLogout}>
            <Text style={styles.logoutText}>Sign Out</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.logoutButton} onPress={handleProfile}>
            <Text style={styles.logoutText}>Profile</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.lowerSection}>
          <View style={styles.buttonContainer}>  
            
            <TouchableOpacity style={styles.touchableButton} onPress={handleCatchDream}>
              <Text style={styles.buttonText}>Catch Dream</Text>
            </TouchableOpacity>

            <TouchableOpacity style={styles.touchableButton} onPress={handleArchive}>
              <Text style={styles.buttonText}>Archive</Text>
            </TouchableOpacity>

            <TouchableOpacity style={styles.touchableButton}>
              <Text style={styles.buttonText}>Trends</Text>
            </TouchableOpacity>

          </View>
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
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 40,
  },
  lowerSection: {
    backgroundColor: 'transparent',
  },
  title: {
    textAlign: 'center',
    color: 'rgb(255,255,255)',
    fontSize: 30,
    fontWeight: 'bold',
    fontFamily: 'Bradley Hand',
  },
  buttonContainer: {
    backgroundColor: 'transparent',
  },
  buttonRow: {
    backgroundColor: 'transparent',
  },
  touchableButton: {
    marginVertical: 8,
    padding: 15,
    backgroundColor: 'rgba(255, 255, 255, 0.2)', // Milky white background
    borderColor: 'rgba(255, 255, 255, 0.6)',
    borderWidth: 1,
    borderRadius: 25,
    alignItems: 'center',
  },
  buttonText: {
    color: 'white',
    fontWeight: 'bold',
  },
  logoutButton: {
    backgroundColor: 'rgba(255, 255, 255, 0.1)',
    borderColor: 'rgba(255, 255, 255, 0.3)',
    borderWidth: 1,
    borderRadius: 8,
    paddingHorizontal: 20,
    paddingVertical: 8,
  },
  logoutText: {
    color: 'white',
    fontSize: 14,
    fontWeight: '500',
  },
});