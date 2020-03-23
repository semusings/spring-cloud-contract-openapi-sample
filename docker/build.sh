#!/bin/bash

export PROJECT_NAME="${PROJECT_NAME:-example}"
echo "Setting project name to [${PROJECT_NAME}]"
echo "Running the build"
./mvnw clean install