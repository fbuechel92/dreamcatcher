import React from 'react';
import { Image, StyleSheet, Text } from 'react-native';
import { Layout, Button } from '@ui-kitten/components';
import { router } from 'expo-router';

export default function MainScreen() {
  
  const handleCatchDream = () => {
    router.push('/dream');
  };
  
  return (
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
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
  },
  upperSection: {
  },
  lowerSection: {
  },
  title: {
    textAlign: 'center',
    marginBottom: 40,
  },
  buttonContainer: {
  },
  buttonRow: {
  },
  button: {
    marginVertical: 8,
  },
  buttonText: {
  },
  buttonIcon: {
  },
});