#!/bin/bash

# Ensure the path is correct
BUILD_GRADLE_PATH="app/build.gradle.kts"

if [ ! -f "$BUILD_GRADLE_PATH" ]; then
  echo "Error: $BUILD_GRADLE_PATH not found!"
  exit 1
fi

# Extract versionCode
versionCode=$(grep "versionCode" $BUILD_GRADLE_PATH | awk '{print $2}')

# Extract versionName
versionName=$(grep "versionName" $BUILD_GRADLE_PATH | awk '{print $2}' | tr -d '"')

# Print the extracted values
echo "VERSION_CODE=$versionCode"
echo "VERSION_NAME=$versionName"