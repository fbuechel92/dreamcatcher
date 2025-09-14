import { BASE_URL } from './config';

//api call from ArchiveScreen
export const fetchUserDreams = async (accessToken: string) => {
    const response = await fetch(`${BASE_URL}/dreams`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization' : `Bearer ${accessToken}`,
        }
    });
    if (!response.ok) throw new Error('Failed to fetch dreams');
    return await response.json();
}

//api call from DreamScreen
export const saveDream = async(
    accessToken: string, 
    visitor: string, 
    plot: string, 
    location: string, 
    mood: string, 
    sleepQuality: string, 
    additionalInfo: string 
) => {
    const response = await fetch(`${BASE_URL}/dreams`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization' : `Bearer ${accessToken}`,
                },
                body: JSON.stringify({
                    visitor: visitor,
                    plot: plot,
                    location: location,
                    mood: mood,
                    sleepQuality: sleepQuality,
                    additionalInfo: additionalInfo,
                }),
    });
    if (!response.ok) throw new Error('Failed to save dream');
    return await response.json();
}
