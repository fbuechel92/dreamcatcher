import { auth0Config } from './auth0';
import { BASE_URL } from './config';

//api call from AuthButton
export const fetchUserInfo = async (accessToken: string) => {
    const response = await fetch(`https://${auth0Config.domain}/userinfo`, {
        headers: { 'Authorization': `Bearer ${accessToken}` }
    });
    if (!response.ok) throw new Error('Failed to fetch user info');
    return await response.json();
};

//api call from AuthButton
export const createOrCheckUser = async (accessToken: string, email: string) => {
    const response = await fetch(`${BASE_URL}/auth`, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${accessToken}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email })
    });
    if (!response.ok) throw new Error('Failed to check or create user');
    return await response.json();
};