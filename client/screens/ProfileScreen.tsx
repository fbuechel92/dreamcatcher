import React from 'react';
import { useState, useEffect } from 'react';
import { StyleSheet, Text, ImageBackground, View, TextInput, TouchableOpacity } from 'react-native';
import { useAuth } from '../contexts/AuthContext'; 

export default function ProfileScreen() {
  
  const auth = useAuth();
  const accessToken = auth?.accessToken;

  const [email, setEmail] = useState('');
  const [name, setName] = useState('');
  const [gender, setGender] = useState('');
  const [birthdate, setBirthdate] = useState('');
  const [country, setCountry] = useState('');
  const [occupation, setOccupation] = useState('');

  //call auth info
  useEffect(() => {
      const fetchAuth = async () => {
        try {
          const response = await fetch('http://localhost:8080/auth', {
            method: 'GET',
            headers: {
              'Authorization': `Bearer ${accessToken}`,
            },
          });
          if (response.ok) {
            const data = await response.json();
            setName(data.name || '');
            setEmail(data.email || '');
          }
        } catch (error) {
          console.error('Error fetching auth info:', error);
        }
      };
      fetchAuth();
    }, [accessToken]);

  //call profile info
  useEffect(() => {
      const fetchProfile = async () => {
        try {
          const response = await fetch('http://localhost:8080/profile', {
            method: 'GET',
            headers: {
              'Authorization': `Bearer ${accessToken}`,
            },
          });
          if (response.ok) {
            const data = await response.json();
            setGender(data.gender || '');
            setBirthdate(data.birthdate || '');
            setCountry(data.country || '');
            setOccupation(data.occupation || '');
          }
        } catch (error) {
          console.error('Error fetching profile info:', error);
        }
      };
      fetchProfile();
    }, [accessToken]);

  const handleSave = async() => {
    try {
      const response = await fetch(`http://localhost:8080/profile`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization' : `Bearer ${accessToken}`,
        },
        body: JSON.stringify({
            gender: gender,
            birthdate: birthdate,
            country: country,
            occupation: occupation,
        }),
      });
    } catch (error) {
          console.error('Error saving profile:', error);
    }
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
          
          
          <View style={styles.inputWrapper}>
            <Text style={styles.label}>Email</Text>
            <TextInput
              style={styles.inputReadOnly}
              value={email}
              onChangeText={setEmail}
              keyboardType="email-address"
              editable={false}
            />
          </View>

          <View style={styles.inputWrapper}>
            <Text style={styles.label}>Name</Text>
            <TextInput
              style={styles.input}
              value={name}
              onChangeText={setName}
              editable={false}
            />
          </View>

          <View style={styles.inputWrapper}>
            <Text style={styles.label}>Gender</Text>
            <TextInput
              style={styles.input}
              value={gender}
              onChangeText={setGender}
            />
          </View>
          <View style={styles.inputWrapper}>
            <Text style={styles.label}>Birthdate</Text>
            <TextInput
              style={styles.input}
              placeholder="YYYY-MM-DD"
              value={birthdate}
              onChangeText={setBirthdate}
            />
          </View>
          <View style={styles.inputWrapper}>
            <Text style={styles.label}>Country</Text>
            <TextInput
              style={styles.input}
              value={country}
              onChangeText={setCountry}
            />
          </View>
          <View style={styles.inputWrapper}>
            <Text style={styles.label}>Occupation</Text>
            <TextInput
              style={styles.input}
              value={occupation}
              onChangeText={setOccupation}
            />
          </View>
          <TouchableOpacity style={styles.saveButton} onPress={handleSave}>
            <Text style={styles.saveButtonText}>Save Profile</Text>
          </TouchableOpacity>
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
    backgroundColor: 'transparent',
  },
  upperSection: {
    backgroundColor: 'transparent',
  },
  lowerSection: {
    backgroundColor: 'transparent',
    justifyContent: 'center',
    alignItems: 'center',
    flex: 1,
  },
  title: {
    textAlign: 'center',
    marginBottom: 40,
    color: 'rgb(255,255,255)',
    fontSize: 30,
    fontWeight: 'bold',
    fontFamily: 'Bradley Hand',
  },
  inputWrapper: {
    width: 300,
    alignItems: 'flex-start',
    marginBottom: 16,
  },
  input: {
    width: '100%',
    height: 48,
    borderWidth: 1,
    borderColor: 'rgba(255, 255, 255, 0.6)',
    borderRadius: 20,
    padding: 12,
    backgroundColor: 'rgba(255, 255, 255, 0.2)',
    color: 'white',
    fontSize: 16,
  },
  inputReadOnly: {
    width: '100%',
    height: 48,
    borderWidth: 1,
    borderColor: 'rgba(255, 255, 255, 0.6)',
    borderRadius: 20,
    padding: 12,
    backgroundColor: 'rgba(80, 80, 80, 0.3)',
    color: 'white',
    fontSize: 16,
  },
  saveButton: {
    backgroundColor: 'rgba(255, 255, 255, 0.2)',
    paddingHorizontal: 20,
    paddingVertical: 12,
    borderRadius: 25,
    borderWidth: 1,
    borderColor: 'rgba(255, 255, 255, 0.6)',
    alignItems: 'center',
    marginTop: 20,
    marginBottom: 100,
    flexDirection: 'row',
    justifyContent: 'center',
  },
  saveButtonText: {
    color: 'white',
    fontWeight: '600',
    fontSize: 16,
  },
  label: {
    color: 'rgba(255,255,255,0.7)',
    fontSize: 15,
    marginBottom: 4,
    marginLeft: 0,
  },
});