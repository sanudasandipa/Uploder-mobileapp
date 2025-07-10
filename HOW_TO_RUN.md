# 🚀 How to Run Your Cloud Uploader Mobile App

## 📋 Prerequisites

### 1. **Install Android Studio**
- Download from: https://developer.android.com/studio
- Install with default settings
- This includes Android SDK, emulator, and build tools

### 2. **Setup Android SDK** (if not using Android Studio)
- Download Android SDK command line tools
- Set `ANDROID_HOME` environment variable
- Install required SDK platforms and build tools

## 🏃‍♂️ **Running the App**

### **Option 1: Using Android Studio (Recommended)**

1. **Open Android Studio**
2. **Import Project**:
   - Click "Open an existing Android Studio project"
   - Navigate to `/workspaces/Uploder-mobileapp`
   - Click "OK"

3. **Sync Project**:
   - Android Studio will automatically prompt to sync
   - Click "Sync Now" if prompted
   - Wait for sync to complete

4. **Configure Server URL**:
   ```kotlin
   // In app/src/main/java/com/clouduploader/app/CloudUploaderApplication.kt
   const val BASE_URL = "http://140.245.238.153/"  // Your server URL
   ```

5. **Run the App**:
   - Connect Android device via USB (with USB debugging enabled)
   - OR start Android emulator
   - Click the green "Run" button (▶️)
   - Select your device/emulator
   - App will build and install automatically

### **Option 2: Command Line Build**

1. **Set Android SDK Path**:
   ```bash
   export ANDROID_HOME=/path/to/your/android/sdk
   export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools
   ```

2. **Build the App**:
   ```bash
   cd /workspaces/Uploder-mobileapp
   ./gradlew assembleDebug
   ```

3. **Install on Device**:
   ```bash
   # Connect Android device and enable USB debugging
   ./gradlew installDebug
   ```

## 📱 **Device Setup**

### **Physical Device**
1. Enable "Developer Options" on your Android device:
   - Go to Settings > About Phone
   - Tap "Build Number" 7 times
   - Go back to Settings > Developer Options
   - Enable "USB Debugging"

2. Connect device via USB
3. Allow USB debugging when prompted

### **Android Emulator**
1. In Android Studio: Tools > AVD Manager
2. Create Virtual Device
3. Choose device (e.g., Pixel 6)
4. Download system image (API 24+ recommended)
5. Start emulator

## 🌐 **Server Configuration**

Make sure your cloud server is running and accessible:

1. **Server Status**: http://140.245.238.153
2. **API Base**: http://140.245.238.153/api/

The app will connect to these endpoints:
- `GET /api/files` - List files
- `POST /api/upload` - Upload files
- `GET /api/download/{filename}` - Download files
- `DELETE /api/delete/{filename}` - Delete files
- `GET /api/storage` - Storage info
- `POST /api/share` - Create shares

## 🔧 **Build Commands**

```bash
# Clean build
./gradlew clean

# Build debug APK
./gradlew assembleDebug

# Build release APK (for production)
./gradlew assembleRelease

# Run tests
./gradlew test

# Install on connected device
./gradlew installDebug

# Uninstall from device
./gradlew uninstallDebug

# Check for lint issues
./gradlew lint
```

## 📦 **APK Location**

After building, APK files are located at:
- **Debug**: `app/build/outputs/apk/debug/app-debug.apk`
- **Release**: `app/build/outputs/apk/release/app-release.apk`

## 🐛 **Troubleshooting**

### **Common Issues**

1. **SDK Not Found**:
   ```bash
   export ANDROID_HOME=/path/to/android/sdk
   ```

2. **Gradle Build Failed**:
   ```bash
   ./gradlew clean
   ./gradlew build --refresh-dependencies
   ```

3. **Device Not Detected**:
   ```bash
   adb devices  # Should list your device
   adb kill-server && adb start-server  # Restart ADB
   ```

4. **Network Connection**:
   - Ensure server is running: http://140.245.238.153
   - Check firewall settings
   - Verify device is on same network (for local servers)

### **Build Logs**
Check detailed build logs:
```bash
./gradlew build --info --debug
```

## ✅ **Verification**

Once the app is running:

1. **Files Tab**: Should load your uploaded files
2. **Storage Tab**: Should show storage statistics  
3. **Upload**: Tap + button to upload files
4. **Download**: Tap download on any file
5. **Share**: Create shareable links
6. **Delete**: Remove files with confirmation

## 🎯 **Next Steps**

1. **Test Core Features**:
   - Upload various file types
   - Download files to device
   - Create and share links
   - Monitor storage usage

2. **Customize the App**:
   - Update colors in `res/values/colors.xml`
   - Modify app name in `res/values/strings.xml`
   - Change app icon in `res/mipmap-*` folders

3. **Deploy to Users**:
   - Build signed release APK
   - Distribute via Google Play Store or direct download

## 🏆 **Success!**

Your Cloud Uploader mobile app is now ready to use! Enjoy managing your cloud storage from your Android device with a beautiful, modern interface.

**Happy file managing! 📱☁️**
