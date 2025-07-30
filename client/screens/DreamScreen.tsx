import React from 'react';
import { StyleSheet, Text, ImageBackground, View, TextInput, TouchableOpacity } from 'react-native';

export default function DreamScreen() {
    
    const [currentStep, setCurrentStep] = React.useState(0);
    const [dreamData, setDreamData] = React.useState({
        visitor: '',
        plot: '',
        location: '',
        mood: '',
        sleepQuality: '',
        anything: '',
    });

    const questions = [
        { key: 'visitor', label: 'Who was in your dream?' },
        { key: 'plot', label: 'What happened in your dream?' },
        { key: 'location', label: 'Where did your dream take place?' },
        { key: 'mood', label: 'How did the dream make you feel?' },
        { key: 'sleepQuality', label: 'How well did you sleep?' },
        { key: 'anything', label: 'Anyting else you remember?' },
    ];

    const handleNext = () => {
        if (currentStep < questions.length - 1) {
            setCurrentStep(currentStep + 1);
        }
    };

    const handlePrevious = () => {
        if (currentStep > 0) {
            setCurrentStep(currentStep - 1);
        }
    };

    const updateDreamData = (value: string) => {
        setDreamData(prev => ({
            ...prev,
            [questions[currentStep].key]: value
        }));
    };

    const currentQuestion = questions[currentStep];
    const currentValue = dreamData[currentQuestion.key as keyof typeof dreamData];

    return(

        <ImageBackground 
              source={require('../assets/images/nightsky_milky.png')} 
              style={styles.backgroundImage}
              resizeMode="cover"
        >

            <View style={styles.container}>
            
                <View style={styles.upperSection}>
                    <Text style={styles.title}>
                        Catching Dreams
                    </Text>
                    <Text style={styles.stepIndicator}>
                        {currentStep + 1} of {questions.length}
                    </Text>
                </View>

                <View style={styles.lowerSection}>
                    <Text style={styles.inputLabel}>{currentQuestion.label}</Text>
                    <TextInput
                        style={styles.input}
                        value={currentValue}
                        onChangeText={updateDreamData}
                        multiline={true}
                        numberOfLines={4}
                        placeholder="Share your dream..."
                        placeholderTextColor="rgba(255, 255, 255, 0.5)"
                    />
                </View>

                    <View style={[
                        styles.saveButton,
                    ]}>

                        {currentStep === questions.length -1 && (
                            <TouchableOpacity style={[styles.button, styles.submitButton]}>
                                <Text style={styles.buttonText}>Save Dream</Text>
                            </TouchableOpacity>
                        )}
                    </View>
                    
                    <View style={[
                        styles.navigationButtons,
                        currentStep === 0 && { justifyContent: 'flex-end' },
                    ]}>
                        {currentStep > 0 && (
                            <TouchableOpacity style={styles.button} onPress={handlePrevious}>
                                <Text style={styles.buttonText}>Previous</Text>
                            </TouchableOpacity>
                        )}
                        
                        {currentStep < questions.length -1 && (
                            <TouchableOpacity style={styles.button} onPress={handleNext}>
                                <Text style={styles.buttonText}>Next</Text>
                            </TouchableOpacity>
                        )}
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
    title: {
        textAlign: 'center',
        fontSize:28,
        fontWeight: 'bold',
        color: 'rgb(255,255,255)',
        fontFamily: 'Bradley Hand'
    },
    input: {
        width: '80%',
        height: 100,
        borderWidth: 1,
        borderColor: 'rgba(255, 255, 255, 0.6)',
        borderRadius: 10,
        padding: 10,
        marginTop: 20,
        textAlignVertical: 'top',
        fontSize: 16,
    },
    inputLabel: {
        fontSize: 20,
        fontWeight: '600',
        color: 'rgb(255,255,255)',
        marginBottom: 10,
        fontFamily: 'Bradley Hand'
    },
    stepIndicator: {
        textAlign: 'center',
        fontSize: 16,
        color: 'rgba(255, 255, 255, 0.8)',
        fontFamily: 'Bradley Hand',
        marginTop: 10,
    },
    navigationButtons: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        width: '100%',
        marginTop: 30,
        paddingHorizontal: 18,
    },
    saveButton:{
        flexDirection: 'row',
        justifyContent: 'center',
        marginBottom: 100,
    },
    button: {
        backgroundColor: 'rgba(255, 255, 255, 0.2)',
        paddingHorizontal: 20,
        paddingVertical: 12,
        borderRadius: 25,
        borderWidth: 1,
        borderColor: 'rgba(255, 255, 255, 0.6)',
    },
    submitButton: {
        backgroundColor: 'rgba(100, 150, 255, 0.3)',
        justifyContent: 'center',
        alignItems: 'center',
    },
    buttonText: {
        color: 'white',
        fontSize: 16,
        fontWeight: '600',
        fontFamily: 'Bradley Hand',
    },
});