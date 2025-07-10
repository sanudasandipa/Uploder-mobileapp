#!/bin/bash

# Cloud Uploader Mobile App - Build Script
echo "🚀 Cloud Uploader Mobile App - Build Setup"
echo "=========================================="

# Check if we're in the right directory
if [ ! -f "settings.gradle.kts" ]; then
    echo "❌ Error: Please run this script from the project root directory"
    exit 1
fi

echo "📱 Setting up Android project..."

# Make gradlew executable
if [ -f "gradlew" ]; then
    chmod +x gradlew
    echo "✅ Made gradlew executable"
else
    echo "⚠️  gradlew not found - will be created on first build"
fi

# Check if Android SDK is available
if [ -z "$ANDROID_HOME" ]; then
    echo "⚠️  ANDROID_HOME not set. Please install Android Studio and set ANDROID_HOME"
    echo "   Example: export ANDROID_HOME=/path/to/android/sdk"
else
    echo "✅ Android SDK found at: $ANDROID_HOME"
fi

echo ""
echo "🔧 Project Setup Complete!"
echo ""
echo "📋 Next Steps:"
echo "1. Open Android Studio"
echo "2. Open this project folder"
echo "3. Update server URL in CloudUploaderApplication.kt:"
echo "   const val BASE_URL = \"http://YOUR_SERVER_IP/\""
echo "4. Sync Gradle files"
echo "5. Run on device or emulator"
echo ""
echo "🌐 Your server should be running at: http://140.245.238.153"
echo "📱 Happy coding!"
