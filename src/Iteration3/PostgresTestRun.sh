#!/bin/bash

echo "🔨 Compiling PostgresTest.java..."
javac -cp "lib/*" -d bin src/database/PostgresTest.java

if [ $? -eq 0 ]; then
    echo "🚀 Running PostgresTest..."
    java -cp "lib/*:bin" database.PostgresTest
else
    echo "❌ Compilation failed."
fi

