import React from 'react';
import { TextInput, TouchableOpacity, StyleSheet, Text, ImageBackground, View } from 'react-native';

export default function ArchiveScreen() {

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
});