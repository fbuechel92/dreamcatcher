import { BASE_URL } from './config';

export interface DreamAnalysis {
  dreamAnalysisId: number;
  userId: string;
  theoryId: number;
  theoryName: string;
  dreamTitle: string;
  dreamTheme: string;
  interpretation: string;
  implications: string;
  createdAt: string;
  updatedAt: string;
}

//api call from ArchiveScreen
export const fetchUserDreams = async (accessToken: string) => {
    try {
        console.log('Fetching dreams from:', `${BASE_URL}/dreams`);
        console.log('Access token:', accessToken ? 'Present' : 'Missing');
        console.log('Access token value:', accessToken); // Log the actual token
        
        const response = await fetch(`${BASE_URL}/dreams`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization' : `Bearer ${accessToken}`,
            }
        });
        
        console.log('Response status:', response.status);
        console.log('Response ok:', response.ok);
        
        if (!response.ok) {
            const errorText = await response.text();
            console.log('Error response:', errorText);
            throw new Error(`Failed to fetch dreams: ${response.status} - ${errorText}`);
        }
        
        return await response.json();
    } catch (error) {
        console.error('Fetch dreams error:', error);
        throw error;
    }
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

//api call from ArchiveScreen
export const fetchDreamAnalysis = async (accessToken: string, dreamId: number): Promise<DreamAnalysis | null> => {
  const response = await fetch(`${BASE_URL}/dreams/${dreamId}/analysis`, {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${accessToken}`,
    },
  });

  if (response.status === 202) {
    // Still analyzing - return null
    return null;
  }

  if (!response.ok) {
    throw new Error('Failed to fetch dream analysis');
  }

  return await response.json();
};