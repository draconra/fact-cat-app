#!/bin/bash

# Extract versionCode
        versionCode=$(grep "versionCode" app/build.gradle.kts | awk '{print $2}')

# Extract versionName
        versionName=$(grep "versionName" app/build.gradle.kts | awk '{print $2}' | tr -d '"')

# Print the extracted values
        echo "VERSION_CODE=$versionCode"
echo "VERSION_NAME=$versionName"