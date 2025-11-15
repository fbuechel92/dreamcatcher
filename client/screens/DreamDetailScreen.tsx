import { useLocalSearchParams } from 'expo-router';
import React, { useEffect, useState } from 'react';
import { ActivityIndicator, ImageBackground, ScrollView, StyleSheet, Text, View } from 'react-native';
import { useAuth } from '../contexts/AuthContext';
import { DreamAnalysis, fetchDreamAnalysis, fetchUserDreams } from '../services/dreamService';

interface Dream {
    dreamId: number;
    visitor: string;
    plot: string;
    location: string;
    mood: any;
    sleepQuality: any;
    additionalInfo: string;
    createdAt: string;
}

export default function DreamDetailScreen() {
    const auth = useAuth();
    const accessToken = auth?.accessToken;
    const { dreamId } = useLocalSearchParams();

    const [dream, setDream] = useState<Dream | null>(null);
    const [analysis, setAnalysis] = useState<DreamAnalysis | null>(null);
    const [isLoadingAnalysis, setIsLoadingAnalysis] = useState(true);

    useEffect(() => {
        if (!accessToken || !dreamId) return;

        // Fetch dream details
        const fetchDream = async () => {
            try {
                const dreams = await fetchUserDreams(accessToken);
                const foundDream = dreams.find((d: Dream) => d.dreamId === Number(dreamId));
                setDream(foundDream);
            } catch (error) {
                console.error('Error fetching dream:', error);
            }
        };

        fetchDream();
    }, [accessToken, dreamId]);

    useEffect(() => {
        if (!accessToken || !dreamId) return;

        // Poll for analysis
        const pollAnalysis = async () => {
            try {
                const result = await fetchDreamAnalysis(accessToken, Number(dreamId));
                if (result) {
                    setAnalysis(result);
                    setIsLoadingAnalysis(false);
                } else {
                    // Still analyzing, poll again in 5 seconds
                    setTimeout(pollAnalysis, 5000);
                }
            } catch (error) {
                console.error('Error fetching analysis:', error);
                setIsLoadingAnalysis(false);
            }
        };

        pollAnalysis();
    }, [accessToken, dreamId]);

    if (!dream) {
        return (
            <View style={styles.loadingContainer}>
                <ActivityIndicator size="large" color="#fff" />
            </View>
        );
    }

    return (
        <ImageBackground 
            source={require('../assets/images/nightsky_milky.png')} 
            style={styles.backgroundImage}
            resizeMode="cover"
        >
            <ScrollView style={styles.container}>
                {/* Upper Section - Dream Submission */}
                <View style={styles.upperSection}>
                    <Text style={styles.sectionTitle}>Your Dream</Text>
                    
                    <View style={styles.dateContainer}>
                        <Text style={styles.dateText}>
                            {new Date(dream.createdAt).toLocaleDateString('en-US', { 
                                weekday: 'long', 
                                year: 'numeric', 
                                month: 'long', 
                                day: 'numeric' 
                            })}
                        </Text>
                    </View>

                    <View style={styles.infoRow}>
                        <Text style={styles.label}>Visitor:</Text>
                        <Text style={styles.value}>{dream.visitor}</Text>
                    </View>

                    <View style={styles.infoRow}>
                        <Text style={styles.label}>Location:</Text>
                        <Text style={styles.value}>{dream.location}</Text>
                    </View>

                    <View style={styles.infoRow}>
                        <Text style={styles.label}>Mood:</Text>
                        <Text style={styles.value}>{dream.mood}</Text>
                    </View>

                    <View style={styles.infoRow}>
                        <Text style={styles.label}>Sleep Quality:</Text>
                        <Text style={styles.value}>{dream.sleepQuality}</Text>
                    </View>

                    <View style={styles.plotContainer}>
                        <Text style={styles.label}>Plot:</Text>
                        <Text style={styles.plotText}>{dream.plot}</Text>
                    </View>

                    {dream.additionalInfo && (
                        <View style={styles.plotContainer}>
                            <Text style={styles.label}>Additional Info:</Text>
                            <Text style={styles.plotText}>{dream.additionalInfo}</Text>
                        </View>
                    )}
                </View>

                {/* Lower Section - Dream Analysis */}
                <View style={styles.lowerSection}>
                    <Text style={styles.sectionTitle}>AI Dream Analysis</Text>

                    {isLoadingAnalysis ? (
                        <View style={styles.loadingAnalysis}>
                            <ActivityIndicator size="large" color="#FFD700" />
                            <Text style={styles.loadingText}>Analyzing your dream...</Text>
                        </View>
                    ) : analysis ? (
                        <>
                            <View style={styles.analysisCard}>
                                <Text style={styles.analysisLabel}>Dream Title</Text>
                                <Text style={styles.analysisText}>{analysis.dreamTitle}</Text>
                            </View>

                            <View style={styles.analysisCard}>
                                <Text style={styles.analysisLabel}>Dream Theme</Text>
                                <Text style={styles.analysisText}>{analysis.dreamTheme}</Text>
                            </View>

                            <View style={styles.analysisCard}>
                                <Text style={styles.analysisLabel}>Interpretation</Text>
                                <Text style={styles.analysisText}>{analysis.interpretation}</Text>
                            </View>

                            <View style={styles.analysisCard}>
                                <Text style={styles.analysisLabel}>Implications</Text>
                                <Text style={styles.analysisText}>{analysis.implications}</Text>
                            </View>

                            <Text style={styles.theoryText}>
                                Based on: {analysis.theoryName}
                            </Text>
                        </>
                    ) : (
                        <Text style={styles.errorText}>Analysis not available</Text>
                    )}
                </View>
            </ScrollView>
        </ImageBackground>
    );
}

const styles = StyleSheet.create({
    backgroundImage: {
        flex: 1,
    },
    container: {
        flex: 1,
    },
    loadingContainer: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: 'rgba(0, 0, 0, 0.5)',
    },
    upperSection: {
        backgroundColor: 'rgba(255, 255, 255, 0.1)',
        padding: 20,
        paddingTop: 60,
    },
    lowerSection: {
        backgroundColor: 'rgba(0, 0, 0, 0.3)',
        padding: 20,
        paddingBottom: 40,
    },
    sectionTitle: {
        fontSize: 24,
        fontWeight: 'bold',
        color: 'white',
        marginBottom: 20,
        textAlign: 'center',
        fontFamily: 'Bradley Hand',
    },
    dateContainer: {
        backgroundColor: 'rgba(255, 255, 255, 0.15)',
        padding: 10,
        borderRadius: 10,
        marginBottom: 20,
    },
    dateText: {
        color: 'white',
        fontSize: 16,
        textAlign: 'center',
        fontWeight: '600',
    },
    infoRow: {
        flexDirection: 'row',
        marginBottom: 12,
        alignItems: 'center',
    },
    label: {
        color: 'rgba(255, 255, 255, 0.8)',
        fontSize: 16,
        fontWeight: 'bold',
        marginRight: 10,
        minWidth: 120,
    },
    value: {
        color: 'white',
        fontSize: 16,
        flex: 1,
    },
    plotContainer: {
        marginTop: 15,
    },
    plotText: {
        color: 'white',
        fontSize: 16,
        lineHeight: 24,
        marginTop: 5,
    },
    loadingAnalysis: {
        alignItems: 'center',
        paddingVertical: 40,
    },
    loadingText: {
        color: 'white',
        fontSize: 16,
        marginTop: 15,
        fontStyle: 'italic',
    },
    analysisCard: {
        backgroundColor: 'rgba(255, 255, 255, 0.1)',
        padding: 15,
        borderRadius: 15,
        marginBottom: 15,
        borderColor: 'rgba(255, 215, 0, 0.3)',
        borderWidth: 1,
    },
    analysisLabel: {
        color: '#FFD700',
        fontSize: 14,
        fontWeight: 'bold',
        marginBottom: 8,
        textTransform: 'uppercase',
    },
    analysisText: {
        color: 'white',
        fontSize: 16,
        lineHeight: 24,
    },
    theoryText: {
        color: 'rgba(255, 255, 255, 0.6)',
        fontSize: 14,
        textAlign: 'center',
        marginTop: 10,
        fontStyle: 'italic',
    },
    errorText: {
        color: 'rgba(255, 255, 255, 0.7)',
        fontSize: 16,
        textAlign: 'center',
        paddingVertical: 20,
    },
});