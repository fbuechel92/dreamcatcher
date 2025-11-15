import { router } from 'expo-router';
import React, { useEffect, useState } from 'react';
import { FlatList, ImageBackground, StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { useAuth } from '../contexts/AuthContext';
import { fetchDreamAnalysis, fetchUserDreams } from '../services/dreamService';

interface Dream {
    dreamId: number;
    visitor: string;
    plot: string;
    location: string;
    mood: any;
    sleepQuality: any;
    additionalInfo: string;
    createdAt: string;
    dreamTitle?: string; // From analysis
}

export default function ArchiveScreen() {

    const auth = useAuth();
    const accessToken = auth?.accessToken;

    const[dreams, setDreams] = useState<Dream[]>([]);
    const[dreamTitles, setDreamTitles] = useState<{[key: number]: string}>({});

    useEffect(() => {
        if (!accessToken) return;
        
        const fetchDreams = async () => {
            try{
                const data = await fetchUserDreams(accessToken);
                setDreams(data);
                
                // Fetch titles for all dreams
                data.forEach(async (dream: Dream) => {
                    try {
                        const analysis = await fetchDreamAnalysis(accessToken, dream.dreamId);
                        if (analysis) {
                            setDreamTitles(prev => ({
                                ...prev,
                                [dream.dreamId]: analysis.dreamTitle
                            }));
                        } else {
                            setDreamTitles(prev => ({
                                ...prev,
                                [dream.dreamId]: 'Analysis in progress...'
                            }));
                        }
                    } catch (error) {
                        console.error(`Error fetching analysis for dream ${dream.dreamId}:`, error);
                    }
                });
            } catch (error){
                console.error('Error fetching dreams:', error);
            }
        };
        fetchDreams();
    }, []);

    const openDreamDetail = (dream: Dream) => {
        router.push({
            pathname: '/dream-detail',
            params: { dreamId: dream.dreamId }
        });
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
                        Dream Archive
                    </Text>
                </View>

                <View style={styles.lowerSection}>
                    <FlatList
                        data={dreams}
                        keyExtractor={(item) => item.dreamId.toString()}
                        showsVerticalScrollIndicator={false}
                        renderItem={({item}) => (
                            <TouchableOpacity 
                                style={styles.dreamCard} 
                                onPress={() => openDreamDetail(item)}
                                activeOpacity={0.8}
                            >

                                <View style={styles.cardHeader}>
                                    <View style={styles.dateContainer}>
                                        <Text style={styles.dateText}>
                                            {new Date(item.createdAt).toLocaleDateString()}
                                        </Text>
                                    </View>
                                    <View style={styles.moodBadge}>
                                        <Text style={styles.moodText}>{item.mood}</Text>
                                    </View>
                                </View>

                                <Text style={styles.dreamTitle}>
                                    {dreamTitles[item.dreamId] || 'Loading title...'}
                                </Text>
                                
                                <View style={styles.cardFooter}>
                                    <Text style={styles.locationText}>📍 {item.location}</Text>
                                </View>
                                
                            </TouchableOpacity>
                        )}
                        contentContainerStyle={styles.listContainer}
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
        justifyContent: 'flex-start',
        marginHorizontal: 30, 
        paddingTop: 25,
    },
    dreamCard: {
        backgroundColor: 'rgba(255, 255, 255, 0.15)',
        borderRadius: 20,
        padding: 10,
        marginVertical: 10,
        borderColor: 'rgba(255, 255, 255, 0.3)',
        borderWidth: 1,
        shadowColor: '#000',
        shadowOffset: {
            width: 0,
            height: 2,
        },
        shadowOpacity: 0.25,
        shadowRadius: 3.84,
        elevation: 5,
    },
    cardHeader: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        marginBottom: 12,
    },
    dateContainer: {
        backgroundColor: 'rgba(255, 255, 255, 0.2)',
        paddingHorizontal: 12,
        paddingVertical: 6,
        borderRadius: 15,
    },
    dateText: {
        color: 'white',
        fontSize: 13,
        fontWeight: '600',
    },
    moodBadge: {
        backgroundColor: 'rgba(255, 215, 0, 0.3)',
        paddingHorizontal: 10,
        paddingVertical: 4,
        borderRadius: 12,
        borderColor: 'rgba(255, 215, 0, 0.6)',
        borderWidth: 1,
    },
    moodText: {
        color: '#FFD700',
        fontSize: 13,
        fontWeight: '600',
    },
    dreamTitle: {
        color: 'white',
        fontSize: 18,
        marginBottom: 10,
    },
    dreamPreview: {
        color: 'white',
        fontSize: 16,
        lineHeight: 22,
        marginBottom: 12,
    },
    cardFooter: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
    },
    locationText: {
        color: 'rgba(255, 255, 255, 0.8)',
        fontSize: 14,
        fontWeight: '500',
    },
    listContainer: {
        paddingBottom: 20,
    },
});