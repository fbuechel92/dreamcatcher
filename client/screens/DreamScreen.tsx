import React from 'react';
import { Image, StyleSheet, Text, TextInput, TouchableOpacity, View } from 'react-native';

export default function DreamScreen() {
    
    const [dreamText, setDreamText] = React.useState('');
    
    return(
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

    );
}

const styles = StyleSheet.create({
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
        fontSize:28,
        fontWeight: 'bold',
        color: '#8A2BE2',
        fontFamily: 'Bradley Hand'
    },
    input: {
        width: '80%',
        height: 100,
        borderWidth: 1,
        borderColor: '#8A2BE2',
        borderRadius: 10,
        padding: 10,
        marginTop: 20,
        textAlignVertical: 'top',
        fontSize: 16,
    },
    inputLabel: {
        fontSize: 20,
        fontWeight: '600',
        color: '#8A2BE2',
        marginBottom: 10,
        fontFamily: 'Bradley Hand'
    },
});