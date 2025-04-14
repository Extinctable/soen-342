#!/bin/bash

echo "🔨 Compiling DropSchema.java..."
javac -cp "lib/*" -d bin src/database/DropSchema.java

if [ $? -eq 0 ]; then
    echo "🚀 Running DropSchema..."
    java -cp "lib/*:bin" database.DropSchema
else
    echo "❌ Compilation failed."
fi
