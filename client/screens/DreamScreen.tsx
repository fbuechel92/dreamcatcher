import React from 'react';
import { StyleSheet, Text, ImageBackground, View, TextInput } from 'react-native';

export default function DreamScreen() {
    
    const [dreamText, setDreamText] = React.useState('');
    
    return(

        <ImageBackground 
              source={require('../assets/images/nightsky_milky.png')} 
              style={styles.backgroundImage}
              resizeMode="cover"
        >

            <View style={styles.container}>
            
                <View style={styles.upperSection}>
                    <Text style={styles.title}>
                        Dreamcatcher
                    </Text>
                </View>

                <View style={styles.lowerSection}>
                    <Text style={styles.inputLabel}>Who was in your dream?</Text>
                    <TextInput
                        style={styles.input}
                        value={dreamText}
                        onChangeText={setDreamText}
                        multiline={true}
                        numberOfLines={4}
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
});