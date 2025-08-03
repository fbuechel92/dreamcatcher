import React from 'react';
import { StyleSheet, Text, ImageBackground, View, TextInput, TouchableOpacity } from 'react-native';
import { Picker } from '@react-native-picker/picker';

export default function DreamScreen() {
    
    interface PickerOption {
        label: string;
        value: string;
    }

    interface Question {
        key: string;
        label: string;
        type: 'text' | 'picker';
    }

    const [currentStep, setCurrentStep] = React.useState(0);
    const [dreamData, setDreamData] = React.useState({
        visitor: '',
        plot: '',
        location: '',
        mood: '',
        sleepQuality: '',
        anything: '',
    });

    const [moodOptions, setMoodOptions] = React.useState<PickerOption[]>([]);
    const [sleepQualityOptions, setSleepQualityOptions] = React.useState<PickerOption[]>([]);

    // Fetch options on component mount
    React.useEffect(() => {
        const fetchOptions = async () => {
            try {
                const [moodResponse, sleepResponse] = await Promise.all([
                    fetch('http://localhost:8080/options/moods'),
                    fetch('http://localhost:8080/options/sleep-qualities')
                ]);

                const moods = await moodResponse.json();
                const sleepQualities = await sleepResponse.json();

                setMoodOptions(moods.map((mood: string) => ({ label: mood, value: mood })));
                setSleepQualityOptions(sleepQualities.map((quality: string) => ({ label: quality, value: quality })));
            } catch (error) {
                console.error('Error fetching options:', error);
            }
        };

        fetchOptions();
    }, []);

    const questions: Question[] = [
        { key: 'visitor', label: 'Who was in your dream?', type: 'text' },
        { key: 'plot', label: 'What happened in your dream?', type: 'text' },
        { key: 'location', label: 'Where did your dream take place?', type: 'text' },
        { key: 'mood', label: 'How did the dream make you feel?', type: 'picker' },
        { key: 'sleepQuality', label: 'How well did you sleep?', type: 'picker' },
        { key: 'anything', label: 'Anything else you remember?', type: 'text' },
    ];

    const getPickerOptions = (): PickerOption[] => {
        if (currentQuestion.key === 'mood') return moodOptions;
        if (currentQuestion.key === 'sleepQuality') return sleepQualityOptions;
        return [];
    };

    const renderInput = () => {
        if (currentQuestion.type === 'picker') {
            return (
                <View style={styles.pickerContainer}>
                    <Picker
                        selectedValue={currentValue}
                        onValueChange={updateDreamData}
                        style={styles.picker}
                        itemStyle={styles.pickerItem}
                    >
                        <Picker.Item label="Select an option..." value="" />
                        {getPickerOptions().map((option) => (
                            <Picker.Item 
                                key={option.value} 
                                label={option.label} 
                                value={option.value} 
                            />
                        ))}
                    </Picker>
                </View>
            );
        }

        return (
            <TextInput
                style={styles.input}
                value={currentValue}
                onChangeText={updateDreamData}
                multiline={true}
                numberOfLines={4}
                placeholder="Share your dream..."
                placeholderTextColor="rgba(255, 255, 255, 0.5)"
            />
        );
    }

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

    const saveDream = async() => {
        const mock_user_id = "1"; // to be removed later

        try {
            const response = await fetch(`http://localhost:8080/users/${mock_user_id}/dreams`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    visitor: dreamData.visitor,
                    plot: dreamData.plot,
                    location: dreamData.location,
                    mood: dreamData.mood,
                    sleepQuality: dreamData.sleepQuality,
                    additionalInfo: dreamData.anything,
                }),
            });

            if (response.ok) {
                setDreamData({
                    visitor: '',
                    plot: '',
                    location: '',
                    mood: '',
                    sleepQuality: '',
                    anything: '',
                });
                setCurrentStep(0);
            }
        } catch (error) {
            console.error('Error saving dream:', error);
        }
    }

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
                    {renderInput()}
                </View>

                    <View style={[
                        styles.saveButton,
                    ]}>

                        {currentStep === questions.length -1 && (
                            <TouchableOpacity style={[styles.button, styles.submitButton]} onPress={saveDream}>
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
        color: 'white', 
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
        fontWeight: '500',
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
    },
    pickerContainer: {
    width: '80%',
    borderWidth: 1,
    borderColor: 'rgba(255, 255, 255, 0.6)',
    borderRadius: 10,
    backgroundColor: 'rgba(255, 255, 255, 0.1)',
    marginTop: 20,
    overflow: 'hidden',
    },
    picker: {
        color: 'white',
        backgroundColor: 'transparent',
        height: 150, // iOS picker needs more height
    },
    pickerItem: {
        color: 'white',
        fontSize: 16,
        height: 150,
    },
});