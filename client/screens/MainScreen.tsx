import React from 'react';
import { StyleSheet, Text, TouchableOpacity, View } from 'react-native';

export default function MainScreen() {
  return (
    <View style={styles.container}>
      <Text style={styles.title}>Dreamcatcher
        
        <View style={styles.buttonContainer}>  
          <TouchableOpacity style={styles.button}>
            <Text style={styles.buttonText}>Catch Dream</Text>
          </TouchableOpacity>

          <TouchableOpacity style={styles.button}>
            <Text style={styles.buttonText}>Archive</Text>
          </TouchableOpacity>
          
          <TouchableOpacity style={styles.button}>
            <Text style={styles.buttonText}>Trends</Text>
          </TouchableOpacity>
        </View>

      </Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#FFFFFF',
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
    flex: 1,
    flexDirection: 'column',
    justifyContent: 'space-evenly',
    alignItems: 'flex-start',
    paddingHorizontal: 20,
  },
  button: {
    backgroundColor: '#8A2BE2',
    paddingVertical: 15,
    paddingHorizontal: 20,
    borderRadius: 10,
},
  buttonText: {
    color: '#FFFFFF',
    fontSize: 16,
    fontWeight: 'bold',
    textAlign: 'center',
  },
});