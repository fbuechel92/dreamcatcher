import { BASE_URL } from './config';

//api call from profile screen
export const fetchAuth = async (accessToken: string) => {

    const response = await fetch(`${BASE_URL}/auth`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${accessToken}`,
        },
    });
    if(!response.ok) throw new Error('Failed to fetch Auth');
    return await response.json();
}

//api call from ProfileScreen
export const fetchProfile = async (accessToken: string) => {

    const response = await fetch(`${BASE_URL}/profile`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${accessToken}`,
        },
    });
    if (!response.ok) throw new Error('Failed to fetch profile info');
    return await response.json();
}

//api call from ProfileScreen
export const handleSave = async(
    accessToken: string,
    name: string,
    gender: string,
    birthdate: string,
    country: string,
    occupation: string
    ) => {

    const response = await fetch(`${BASE_URL}/profile`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization' : `Bearer ${accessToken}`,
        },
        body: JSON.stringify({
            name: name,
            gender: gender,
            birthdate: birthdate,
            country: country,
            occupation: occupation,
        }),
    });
    if (!response.ok) throw new Error('Error saving profile:');
    return await response.json();
}