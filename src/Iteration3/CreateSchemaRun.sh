#!/bin/bash

echo "🔨 Compiling CreateSchema.java..."
javac -cp "lib/*" -d bin src/database/CreateSchema.java

if [ $? -eq 0 ]; then
    echo "🚀 Running CreateSchema..."
    java -cp "lib/*:bin" database.CreateSchema
else
    echo "❌ Compilation failed."
fi

