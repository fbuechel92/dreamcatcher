#!/bin/sh

# Start Ollama in the background
/bin/ollama serve &

# Wait for Ollama to be ready
echo "Waiting for Ollama to start..."
sleep 5

# Pull the model if it doesn't exist
if ! ollama list | grep -q "mistral:7b-instruct-q2_K"; then
    echo "Pulling mistral:7b-instruct-q2_K..."
    ollama pull mistral:7b-instruct-q2_K
else
    echo "Model already exists"
fi

# Keep container running
wait