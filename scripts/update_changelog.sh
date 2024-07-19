#!/bin/bash

# Fetch the latest tags and commits
git fetch --tags

# Get the latest tag
LATEST_TAG=$(git describe --tags `git rev-list --tags --max-count=1`)

# Get the commits since the latest tag
COMMITS=$(git log $LATEST_TAG..HEAD --pretty=format:"%s")

# Update the CHANGELOG.md
echo "## $(date +"%Y-%m-%d")" >> CHANGELOG.md
echo "" >> CHANGELOG.md
echo "$COMMITS" >> CHANGELOG.md

# Add and commit the changes
git add CHANGELOG.md
git commit -m "Update CHANGELOG.md for $(date +"%Y-%m-%d")"
