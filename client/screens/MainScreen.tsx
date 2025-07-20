import React from 'react';
import { Image, StyleSheet, Text, TouchableOpacity, View } from 'react-native';

export default function MainScreen() {
  return (
    <View style={styles.container}>
      
      <View style={styles.upperSection}>
        <Text style={styles.title}>Dreamcatcher</Text>
      </View>

      <View style={styles.lowerSection}>
        
        <View style={styles.buttonContainer}>  
          
          <View style={styles.buttonRow}>
            <Image source={require('../assets/images/dreamcatcher.png')} style={styles.buttonIcon} />
            <TouchableOpacity style={styles.button}>
              <Text style={styles.buttonText}>Catch Dream</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.buttonRow}>
            <Image source={require('../assets/images/dreamcatcher.png')} style={styles.buttonIcon} />
            <TouchableOpacity style={styles.button}>
              <Text style={styles.buttonText}>Archive</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.buttonRow}>
            <Image source={require('../assets/images/dreamcatcher.png')} style={styles.buttonIcon} />
            <TouchableOpacity style={styles.button}>
              <Text style={styles.buttonText}>Trends</Text>
            </TouchableOpacity>
          </View>

        </View>
      </View>

    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#FFFFFF',
    flexDirection: 'column',
  },
  upperSection: {
    paddingTop: 60,
    paddingHorizontal: 20,
    paddingBottom: 20, // Remove flex: 1, just use padding
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
    position: 'absolute',
    top: 10,
    left: 20,
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