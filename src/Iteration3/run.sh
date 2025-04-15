#!/bin/bash

echo "🔨 Compiling all Java files..."
javac -cp "lib/*" -d bin $(find src -name "*.java")

if [ $? -eq 0 ]; then
    echo "🚀 Running Main..."
    java -cp "lib/*:bin" Main
else
    echo "❌ Compilation failed."
fi
