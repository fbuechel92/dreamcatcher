import React from 'react';
import { useState } from 'react';
import { StyleSheet, Text, ImageBackground, View, TextInput } from 'react-native';

export default function ProfileScreen() {
  
  const [email, setEmail] = useState('');
  const [name, setName] = useState('');
  const [gender, setGender] = useState('');
  const [birthdate, setBirthdate] = useState('');
  const [country, setCountry] = useState('');
  const [occupation, setOccupation] = useState('');

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
          <TextInput
            style={styles.input}
            placeholder="Name"
            value={name}
            onChangeText={setName}
          />
          <TextInput
            style={styles.input}
            placeholder="Email"
            value={email}
            onChangeText={setEmail}
            keyboardType="email-address"
          />
          <TextInput
            style={styles.input}
            placeholder="Gender"
            value={gender}
            onChangeText={setGender}
          />
          <TextInput
            style={styles.input}
            placeholder="Birthdate (YYYY-MM-DD)"
            value={birthdate}
            onChangeText={setBirthdate}
          />
          <TextInput
            style={styles.input}
            placeholder="Country"
            value={country}
            onChangeText={setCountry}
          />
          <TextInput
            style={styles.input}
            placeholder="Occupation"
            value={occupation}
            onChangeText={setOccupation}
          />
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
  input: {
    backgroundColor: 'rgba(255,255,255,0.8)',
    borderRadius: 8,
    padding: 10,
    marginBottom: 12,
  },
  saveButton: {
    backgroundColor: '#4B0082',
    padding: 14,
    borderRadius: 8,
    alignItems: 'center',
    marginTop: 20,
  },
  saveButtonText: {
    color: 'white',
    fontWeight: 'bold',
    fontSize: 16,
  },
});