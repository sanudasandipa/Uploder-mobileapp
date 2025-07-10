# Android App "Not Supported" Troubleshooting Guide

## Common Causes and Solutions

### 1. **Architecture Compatibility Issues**
The app might not support your device's architecture (ARM, x86, etc.).

**Solution**: I've updated your `build.gradle.kts` to support multiple architectures:
```kotlin
ndk {
    abiFilters += listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
}
```

### 2. **Minimum SDK Version Too High**
Your app was set to `minSdk = 24` (Android 7.0), which might be too high for older devices.

**Solution**: I've lowered it to `minSdk = 21` (Android 5.0) for better compatibility.

### 3. **Missing Permissions or Features**
Some devices may not support required features.

**Solution**: I've updated the manifest to make camera features optional:
```xml
<uses-feature android:name="android.hardware.camera" android:required="false" />
```

## Testing Steps

### Step 1: Check Connected Devices
```bash
./run_app.sh devices
```

### Step 2: Build and Install
```bash
./run_app.sh build
```

### Step 3: Monitor Logs
```bash
./run_app.sh logs
```

## Virtual Device Setup

If you don't have a physical device, create a virtual device in Android Studio:

1. **Open Android Studio**
2. **Go to Tools > AVD Manager**
3. **Create Virtual Device**
4. **Choose a device definition** (e.g., Pixel 4)
5. **Select API Level 21-34** for maximum compatibility
6. **Choose x86_64 architecture** for faster emulation
7. **Start the emulator**

## Alternative Testing Methods

### 1. Using Android Studio
1. Open the project in Android Studio
2. Wait for sync to complete
3. Click the "Run" button (green triangle)
4. Select your target device

### 2. Manual Installation
```bash
# Build the APK
./gradlew assembleDebug

# Install manually
adb install app/build/outputs/apk/debug/app-debug.apk

# Start the app
adb shell am start -n com.clouduploader.app/.ui.MainActivity
```

### 3. Check Device Compatibility
```bash
# Check device API level
adb shell getprop ro.build.version.sdk

# Check device architecture
adb shell getprop ro.product.cpu.abi
```

## Common Error Messages and Solutions

### "App not installed"
- **Cause**: Signature conflicts or insufficient storage
- **Solution**: Uninstall previous versions, clear cache

### "App can't run on this device"
- **Cause**: Architecture or API level mismatch
- **Solution**: Check device specs, rebuild with correct config

### "Package file is invalid"
- **Cause**: Corrupted APK
- **Solution**: Clean build: `./gradlew clean assembleDebug`

## Debugging Commands

```bash
# Check device logs
adb logcat | grep CloudUploader

# Clear app data
adb shell pm clear com.clouduploader.app

# Force stop app
adb shell am force-stop com.clouduploader.app

# Reinstall app
adb uninstall com.clouduploader.app
./gradlew installDebug
```

## Additional Fixes Applied

1. **Lowered minimum SDK** from 24 to 21
2. **Added multi-architecture support**
3. **Made hardware features optional**
4. **Added screen size support declarations**
5. **Added packaging options** to avoid conflicts
6. **Added vector drawable support**

Try running the app now with the updated configuration!
