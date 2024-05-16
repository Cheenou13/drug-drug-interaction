#!/bin/bash

# Navigate to the project directory
cd "$(dirname "$0")"

# Clean and install the project
mvn clean install

# Run the Spring Boot application
mvn spring-boot:run
