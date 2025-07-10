#!/bin/bash

echo "=== Cloud Uploader App Runner ==="
echo

# Function to check if a device is connected
check_device() {
    adb devices | grep -q "device$"
}

# Function to build and install the app
build_and_install() {
    echo "Building the app..."
    ./gradlew clean assembleDebug
    
    if [ $? -eq 0 ]; then
        echo "✅ Build successful!"
        
        if check_device; then
            echo "📱 Installing app on connected device..."
            ./gradlew installDebug
            
            if [ $? -eq 0 ]; then
                echo "✅ App installed successfully!"
                echo "🚀 Starting the app..."
                adb shell am start -n com.clouduploader.app/.ui.MainActivity
            else
                echo "❌ Failed to install app"
                exit 1
            fi
        else
            echo "⚠️ No device connected. Please connect a device or start an emulator."
            echo "APK location: app/build/outputs/apk/debug/app-debug.apk"
        fi
    else
        echo "❌ Build failed!"
        exit 1
    fi
}

# Function to check app logs
check_logs() {
    echo "📋 Checking app logs..."
    adb logcat -c  # Clear previous logs
    echo "Monitoring logs for Cloud Uploader app (press Ctrl+C to stop)..."
    adb logcat | grep -E "(CloudUploader|com.clouduploader.app)"
}

# Main menu
case "${1:-}" in
    "build")
        build_and_install
        ;;
    "logs")
        check_logs
        ;;
    "devices")
        echo "Connected devices:"
        adb devices
        ;;
    *)
        echo "Usage: $0 [build|logs|devices]"
        echo
        echo "Commands:"
        echo "  build   - Clean, build, and install the app"
        echo "  logs    - Show app logs"
        echo "  devices - List connected devices"
        echo
        echo "Running default: build"
        build_and_install
        ;;
esac
