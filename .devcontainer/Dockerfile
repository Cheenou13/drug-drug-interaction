# Use the official Maven image from the Docker Hub
FROM maven:latest

# Install Node.js and dos2unix
RUN apt-get update && \
    apt-get install -y curl dos2unix && \
    curl -fsSL https://deb.nodesource.com/setup_current.x | bash - && \
    apt-get install -y nodejs

# Verify installations
RUN node --version
RUN npm --version
RUN mvn --version
RUN dos2unix --version
