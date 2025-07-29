import React from 'react';
import { StyleSheet, Text, ImageBackground, View, TouchableOpacity } from 'react-native';
import { router } from 'expo-router';

export default function MainScreen() {
  
  const handleCatchDream = () => {
    router.push('/dream');
  };

  const handleArchive = () => {
    router.push('/archive');
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
  },
  lowerSection: {
    backgroundColor: 'transparent',
  },
  title: {
    textAlign: 'center',
    marginBottom: 40,
    fontFamily: 'Bradley Hand',
    color: 'rgb(255,255,255)',
    fontSize: 30,
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
});