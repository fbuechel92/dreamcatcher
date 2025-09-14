//api call from DreamScreen
export const fetchOptions = async () => {

    const [moodResponse, sleepResponse] = await Promise.all([
        fetch('http://localhost:8080/options/moods'),
        fetch('http://localhost:8080/options/sleep-qualities')
    ]);

    const moods = await moodResponse.json();
    const sleepQualities = await sleepResponse.json();

    return { moods, sleepQualities };
};