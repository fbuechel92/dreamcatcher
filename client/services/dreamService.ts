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