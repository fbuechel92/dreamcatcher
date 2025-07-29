import React, { useState, useEffect } from 'react';
import { TextInput, TouchableOpacity, StyleSheet, Text, ImageBackground, View, FlatList } from 'react-native';

interface Dream {
    dreamId: number;
    visitor: string;
    plot: string;
    location: string;
    mood: string;
    sleepQuality: string;
    additionalInfo: string;
}

export default function ArchiveScreen() {

    const[dreams, setDreams] = useState<Dream[]>([]);
    const mock_user_id = "1"; //to be removed later

    useEffect(() => {
        fetchUserDreams();
    }, []);

    const fetchUserDreams = async () => {
        try {
            const response = await fetch(`http://localhost:8080/users/${mock_user_id}/dreams`);
            const data = await response.json();
            setDreams(data);
        } catch (error) {
            console.error('Error fetching dreams:', error);
        }
    }

    return(
        <ImageBackground 
                source={require('../assets/images/nightsky_milky.png')} 
                style={styles.backgroundImage}
                resizeMode="cover"
        >

            <View style={styles.container}>

                <View style={styles.upperSection}>
                    <Text style={styles.title}>
                        Revisiting Dreams
                    </Text>
                </View>

                <View style={styles.lowerSection}>
                    <FlatList
                        data={dreams}
                        keyExtractor={(item) => item.dreamId.toString()}
                        renderItem={({item}) => (
                            <View style={styles.dreamItem}>
                                <Text style={styles.dreamText}>{item.plot}</Text>
                            </View>
                        )}
                    />
                </View>
            </View>
        
        </ImageBackground>
    )
}

const styles = StyleSheet.create({
    backgroundImage: {
        flex: 1,
    },
    title: {
        textAlign: 'center',
        fontSize:28,
        fontWeight: 'bold',
        color: 'rgb(255,255,255)',
        fontFamily: 'Bradley Hand'
    },
    container: {
        flex: 1,
    },
    upperSection: {
        paddingTop: 20,
        paddingLeft: 20,
        paddingBottom: 50,
    },
    lowerSection: {
        flex: 1,
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'flex-start',
        paddingTop: 200,
    },
    dreamItem: {
        backgroundColor: 'rgba(255, 255, 255, 0.15)',
        borderRadius: 15,
        padding: 15,
        marginVertical: 8,
        marginHorizontal: 20,
        borderColor: 'rgba(255, 255, 255, 0.3)',
        borderWidth: 1,
    },
    dreamText: {
        color: 'white',
        fontSize: 16,
        fontFamily: 'Bradley Hand',
        lineHeight: 22,
    },
});