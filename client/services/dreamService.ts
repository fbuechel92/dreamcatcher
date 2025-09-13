//api call from ArchiveScreen
export const fetchUserDreams = async (accessToken: string) => {
    const response = await fetch(`http://localhost:8080/dreams`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization' : `Bearer ${accessToken}`,
        }
    });
    const data = await response.json();
    return data;
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
    const response = await fetch(`http://localhost:8080/dreams`, {
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
    return response;
}
