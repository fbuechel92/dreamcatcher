import React from 'react';
import { Image, StyleSheet, Text, TouchableOpacity, View } from 'react-native';

export default function DreamScreen() {
    return(
        <View style={styles.container}>
            <Text style={styles.title}>
                Let's catch your dream!
            </Text>
        </View>

    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
    },
    title: {
        fontSize:20,
        fontWeight: 'bold',
        color: '#8A2BE2',
        fontFamily: 'Bradley Hand'
    }
});