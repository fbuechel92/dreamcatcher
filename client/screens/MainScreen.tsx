import React from 'react';
import { StyleSheet, Text, ImageBackground } from 'react-native';
import { Layout, Button } from '@ui-kitten/components';
import { router } from 'expo-router';

export default function MainScreen() {
  
  const handleCatchDream = () => {
    router.push('/dream');
  };
  
  return (
    <ImageBackground 
      source={require('../assets/images/nightsky_milky.png')} 
      style={styles.backgroundImage}
      resizeMode="cover"
    >
      <Layout style={styles.container}>
        
        <Layout style={styles.upperSection}>
          <Text style={styles.title}>Dreamcatcher</Text>
        </Layout>

        <Layout style={styles.lowerSection}>
          
          <Layout style={styles.buttonContainer}>  
            
            <Layout style={styles.buttonRow}>
              <Button style={styles.button} onPress={handleCatchDream}>
                <Text style={styles.buttonText}>Catch Dream</Text>
              </Button>
            </Layout>

            <Layout style={styles.buttonRow}>
              <Button style={styles.button}>
                <Text style={styles.buttonText}>Archive</Text>
              </Button>
            </Layout>

            <Layout style={styles.buttonRow}>
              <Button style={styles.button}>
                <Text style={styles.buttonText}>Trends</Text>
              </Button>
            </Layout>

          </Layout>
        </Layout>

      </Layout>
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
    color: '#FFFFFF',
    fontSize: 30,
  },
  buttonContainer: {
    backgroundColor: 'transparent',
  },
  buttonRow: {
    backgroundColor: 'transparent',
  },
  button: {
    marginVertical: 8,
    backgroundColor: 'rgba(255, 255, 255, 0.2)', // Milky white background
    borderColor: 'rgba(255, 255, 255, 0.6)',
    borderRadius: 25,
  },
  buttonText: {
  },
  buttonIcon: {
  },
});