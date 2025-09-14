import { auth0Config } from './auth0';

//api call from AuthButton
export const fetchUserInfo = async (accessToken: string) => {
    const response = await fetch(`https://${auth0Config.domain}/userinfo`, {
        headers: { 'Authorization': `Bearer ${accessToken}` }
    });
    if (!response.ok) throw new Error('Failed to fetch user info');
    return await response.json();
};

//api call from AuthButton
export const checkUserExists = async (accessToken: string) => {
    const response = await fetch('http://localhost:8080/user/exists', {
        headers: { 'Authorization': `Bearer ${accessToken}` }
    });
    console.log('checkUserExists status:', response.status, 'ok:', response.ok);
    if (!response.ok) throw new Error('Failed to check user existence');
    return await response.json();
};

//api call from AuthButton
export const createUser = async (accessToken: string, email: string) => {
    const response = await fetch('http://localhost:8080/auth', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${accessToken}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email })
    });
    if (!response.ok) throw new Error('Failed to create user');
    return await response.json();
};