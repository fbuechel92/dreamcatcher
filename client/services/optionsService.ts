import { BASE_URL } from './config';

//api call from DreamScreen
export const fetchOptions = async () => {

    const [moodResponse, sleepResponse] = await Promise.all([
        fetch(`${BASE_URL}/options/moods`),
        fetch(`${BASE_URL}/options/sleep-qualities`)
    ]);

    const moods = await moodResponse.json();
    const sleepQualities = await sleepResponse.json();

    return { moods, sleepQualities };
};