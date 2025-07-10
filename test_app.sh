#!/bin/bash

echo "🚀 Testing Cloud Uploader App"
echo "=============================="

# Check if any devices are connected
echo "📱 Checking for connected devices..."
DEVICES=$(adb devices | grep -c "device$")

if [ $DEVICES -eq 0 ]; then
    echo "⚠️  No devices found. Please:"
    echo "   1. Connect a physical Android device with USB debugging enabled"
    echo "   2. OR start an Android Virtual Device (AVD) from Android Studio"
    echo "   3. Then run this script again"
    echo
    echo "To check for devices: adb devices"
    exit 1
fi

echo "✅ Found $DEVICES device(s)"
echo

# Build and install the app
echo "🔨 Building the app..."
./gradlew clean assembleDebug

if [ $? -ne 0 ]; then
    echo "❌ Build failed. Check the error messages above."
    exit 1
fi

echo "✅ Build successful!"
echo

echo "📦 Installing app on device..."
./gradlew installDebug

if [ $? -ne 0 ]; then
    echo "❌ Installation failed. This could be due to:"
    echo "   - Device compatibility issues"
    echo "   - USB debugging not enabled"
    echo "   - Insufficient storage"
    echo "   - Previous installation conflicts"
    echo
    echo "Try manually uninstalling the app first:"
    echo "   adb uninstall com.clouduploader.app"
    exit 1
fi

echo "✅ App installed successfully!"
echo

echo "🚀 Launching app..."
adb shell am start -n com.clouduploader.app/.ui.MainActivity

if [ $? -eq 0 ]; then
    echo "✅ App launched successfully!"
    echo
    echo "📋 Monitoring app logs (press Ctrl+C to stop)..."
    echo "----------------------------------------------------"
    adb logcat -c  # Clear previous logs
    adb logcat | grep -E "(CloudUploader|com.clouduploader.app|AndroidRuntime)"
else
    echo "❌ Failed to launch app. Check device compatibility."
fi
