#!/bin/bash
set -e
cd /mnt/c/Users/varun/IdeaProjects/Library_Management
git config --global --add safe.directory /mnt/c/Users/varun/IdeaProjects/Library_Management
git init
git config user.name "VarunG7"
git config user.email "varun@users.noreply.github.com"
git add .
git commit -m "Initial commit: Library Management microservices architecture" || true
git branch -M main
git remote remove origin 2>/dev/null || true
git remote add origin https://github.com/VarunG7/api-gateway_demo.git
git remote -v
git log -1 --oneline
