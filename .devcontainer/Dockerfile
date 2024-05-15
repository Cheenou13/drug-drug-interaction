# Use the official Maven image from the Docker Hub
FROM maven:latest

# Install Node.js
RUN apt-get update && \
    apt-get install -y curl && \
    curl -fsSL https://deb.nodesource.com/setup_current.x | bash - && \
    apt-get install -y nodejs

# Verify installations
RUN node --version
RUN npm --version
RUN mvn --version


