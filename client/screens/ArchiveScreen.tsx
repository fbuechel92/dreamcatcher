import React, { useState, useEffect } from 'react';
import { TextInput, TouchableOpacity, StyleSheet, Text, ImageBackground, View, FlatList, Modal, ScrollView } from 'react-native';

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

export default function ArchiveScreen() {

    const[dreams, setDreams] = useState<Dream[]>([]);
    const[selectedDream, setSelectedDream] = useState<Dream | null>(null);
    const[modalVisible, setModalVisible] = useState(false);
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

    const openDream = (dream: Dream) => {
        setSelectedDream(dream);
        setModalVisible(true);
    }

    const closeDream = () => {
        setModalVisible(false);
        setSelectedDream(null);
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
                                onPress={() => openDream(item)}
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

                                <Text style={styles.dreamPreview} numberOfLines={3}>
                                    {item.plot}
                                </Text>
                                
                                <View style={styles.cardFooter}>
                                    <Text style={styles.locationText}>📍 {item.location}</Text>
                                </View>
                                
                            </TouchableOpacity>
                        )}
                        contentContainerStyle={styles.listContainer}
                    />
                </View>

                <Modal
                    animationType="slide"
                    transparent={true}
                    visible={modalVisible}
                    onRequestClose={closeDream}
                >

                    <View style={styles.modalOverlay}>
                        <View style={styles.modalContent}>
                            <TouchableOpacity style={styles.closeButton} onPress={closeDream}>
                                <Text style={styles.closeButtonText}>×</Text>
                            </TouchableOpacity>
                            
                            {selectedDream && (
                                <ScrollView style={styles.dreamDetailContent}>
                                    <Text style={styles.modalTitle}>Dream Details</Text>
                                    
                                    <View style={styles.detailSection}>
                                        <Text style={styles.detailLabel}>Story:</Text>
                                        <Text style={styles.detailText}>{selectedDream.plot}</Text>
                                    </View>
                            
                                    <View style={styles.detailSection}>
                                        <Text style={styles.detailLabel}>Visitor:</Text>
                                        <Text style={styles.detailText}>{selectedDream.visitor}</Text>
                                    </View>
                                    
                                    <View style={styles.detailSection}>
                                        <Text style={styles.detailLabel}>Location:</Text>
                                        <Text style={styles.detailText}>{selectedDream.location}</Text>
                                    </View>
                                    
                                    <View style={styles.detailSection}>
                                        <Text style={styles.detailLabel}>Mood:</Text>
                                        <Text style={styles.detailText}>{selectedDream.mood}</Text>
                                    </View>
                            
                                    <View style={styles.detailSection}>
                                        <Text style={styles.detailLabel}>Sleep Quality:</Text>
                                        <Text style={styles.detailText}>{selectedDream.sleepQuality}</Text>
                                    </View>
                                    
                                    {selectedDream.additionalInfo && (
                                        <View style={styles.detailSection}>
                                            <Text style={styles.detailLabel}>Additional Notes:</Text>
                                            <Text style={styles.detailText}>{selectedDream.additionalInfo}</Text>
                                        </View>
                                    )}
                                </ScrollView>
                            )}
                        </View>
                    </View>
                </Modal>
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
        lineHeight: 22,
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
    readMoreText: {
        color: 'rgba(255, 255, 255, 0.6)',
        fontSize: 13,
        fontWeight: '500',
        fontStyle: 'italic',
    },
    listContainer: {
        paddingBottom: 20,
    },
    modalOverlay: {
        flex: 1,
        backgroundColor: 'rgba(0, 0, 0, 0.8)',
        justifyContent: 'center',
        alignItems: 'center',
    },
    modalContent: {
        backgroundColor: 'rgba(108, 155, 172, 0.95)',
        borderRadius: 20,
        padding: 20,
        margin: 20,
        maxHeight: '80%',
        width: '90%',
        borderColor: 'rgba(255, 255, 255, 0.3)',
        borderWidth: 1,
    },
    closeButton: {
        position: 'absolute',
        top: 15,
        right: 20,
        zIndex: 1,
        padding: 5,
    },
    closeButtonText: {
        color: 'white',
        fontSize: 30,
        fontWeight: 'bold',
    },
    dreamDetailContent: {
        marginTop: 30,
    },
    modalTitle: {
        color: 'white',
        fontSize: 24,
        fontWeight: 'bold',
        textAlign: 'center',
        marginBottom: 20,
    },
    detailSection: {
        marginBottom: 15,
    },
    detailLabel: {
        color: '#FFD700',
        fontSize: 16,
        fontWeight: 'bold',
        marginBottom: 5,
    },
    detailText: {
        color: 'white',
        fontSize: 16,
        lineHeight: 22,
    },
});