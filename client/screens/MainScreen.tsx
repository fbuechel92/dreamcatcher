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
            <Image source={require('../assets/images/dreamcatcher.png')} style={styles.buttonIcon} />
            <Button style={styles.button} onPress={handleCatchDream}>
              <Text style={styles.buttonText}>Catch Dream</Text>
            </Button>
          </Layout>

          <Layout style={styles.buttonRow}>
            <Image source={require('../assets/images/dreamcatcher.png')} style={styles.buttonIcon} />
            <Button style={styles.button}>
              <Text style={styles.buttonText}>Archive</Text>
            </Button>
          </Layout>

          <Layout style={styles.buttonRow}>
            <Image source={require('../assets/images/dreamcatcher.png')} style={styles.buttonIcon} />
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
    backgroundColor: '#FFFFFF',
    flexDirection: 'column',
  },
  upperSection: {
    paddingTop: 20,
    paddingLeft: 20,
    paddingBottom: 50,
  },
  lowerSection: {
    flex: 1,
    flexDirection: 'column',
    justifyContent: 'flex-start',
    paddingHorizontal: 20,
  },
  title: {
    fontSize:28,
    fontWeight: 'bold',
    color: '#8A2BE2',
    fontFamily: 'Bradley Hand'
  },
  buttonContainer: {
    flexDirection: 'column',
  },
  buttonRow: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 15,
    backgroundColor: '#FFFFFF',
    borderRadius: 12, //rounded corners
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
  },
  button: {
    backgroundColor: 'transparent',
    paddingVertical: 18, //enlarge click/button area vertically
    borderRadius: 12, //to match the rounded corners of the buttonRow
    flex: 1,
},
  buttonText: {
    color: '#8A2BE2',
    fontSize: 18,
    fontWeight: '600',
    textAlign: 'center',
    fontFamily: 'System'
  },
  buttonIcon: {
    width: 70,
    height: 70,
    marginBottom: 5,
  },
});