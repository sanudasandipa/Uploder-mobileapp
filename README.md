# 📱 Cloud Uploader Mobile App

A modern Android mobile application built with Kotlin to access your Cloud File Uploader service. This app provides a beautiful, intuitive interface for managing your cloud storage directly from your mobile device.

## ✨ Features

### 🎯 **Core Functionality**
- **📤 File Upload** - Upload any file type from your device to cloud storage
- **📥 File Download** - Download files directly to your device
- **🗑️ File Management** - Delete files with confirmation dialogs
- **📊 Storage Analytics** - Real-time storage usage with visual progress bars
- **🔗 File Sharing** - Create and manage shareable links for your files

### 🎨 **Modern UI/UX**
- **Material Design 3** - Latest Material Design components and principles
- **Dark/Light Theme** - Automatic theme switching based on system preference
- **Responsive Layout** - Optimized for all screen sizes and orientations
- **Smooth Animations** - Polished transitions and micro-interactions
- **Progress Tracking** - Real-time upload/download progress indicators

### 🚀 **Technical Excellence**
- **MVVM Architecture** - Clean separation of concerns with ViewModel and LiveData
- **Retrofit Networking** - Robust HTTP client with automatic retries
- **Coroutines** - Asynchronous operations with proper error handling
- **Navigation Component** - Type-safe navigation between screens
- **ViewBinding** - Null-safe view references and improved performance

## 🏗️ **Architecture Overview**

```
app/
├── data/
│   ├── api/           # Retrofit API interfaces and clients
│   ├── model/         # Data classes and response models
│   └── repository/    # Data layer abstraction
├── ui/
│   ├── adapter/       # RecyclerView adapters
│   ├── fragment/      # UI fragments (Files, Storage)
│   ├── viewmodel/     # ViewModels for UI logic
│   └── MainActivity   # Main activity with navigation
└── CloudUploaderApplication.kt  # Application class
```

## 🛠️ **Setup Instructions**

### 1. **Prerequisites**
- Android Studio Arctic Fox (2020.3.1) or newer
- Android SDK API level 24+ (Android 7.0)
- Kotlin 1.9.10+
- Your Cloud File Uploader server running and accessible

### 2. **Configuration**
Update the server URL in `CloudUploaderApplication.kt`:
```kotlin
companion object {
    const val BASE_URL = "http://YOUR_SERVER_IP/"  // Replace with your server
    const val API_BASE_URL = "${BASE_URL}api/"
}
```

### 3. **Build and Run**
```bash
# Clone or download the project
# Open in Android Studio
# Sync Gradle files
# Run on device or emulator
```

## 📋 **API Integration**

The app integrates seamlessly with your existing Cloud File Uploader API:

### **Supported Endpoints**
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/files` | List all uploaded files |
| POST | `/api/upload` | Upload new files |
| GET | `/api/download/{filename}` | Download specific file |
| DELETE | `/api/delete/{filename}` | Delete file from server |
| GET | `/api/storage` | Get storage usage statistics |
| POST | `/api/share` | Create shareable file links |
| GET | `/api/shares/{filename}` | Get existing shares for file |
| DELETE | `/api/share/{shareId}` | Delete share link |

### **Request/Response Format**
All API responses follow this format:
```json
{
  "success": true,
  "message": "Operation completed successfully",
  "data": { /* relevant data */ },
  "error": null
}
```

## 📱 **User Interface**

### **Files Screen**
- **Grid Layout** - Beautiful card-based file display
- **File Icons** - Contextual icons based on file type (image, video, document, etc.)
- **Quick Actions** - Download, Share, and Delete buttons on each file
- **Pull-to-Refresh** - Swipe down to refresh files list
- **Empty State** - Helpful guidance when no files are uploaded
- **Upload FAB** - Floating action button for easy file uploads

### **Storage Screen**
- **Usage Overview** - Visual progress bar showing space utilization
- **Detailed Stats** - Total space, free space, file count, max file size
- **Real-time Updates** - Automatically refreshes after file operations
- **Clean Design** - Card-based layout with clear visual hierarchy

### **Upload/Download Experience**
- **Progress Indicators** - Real-time progress bars with percentage
- **Background Operations** - Non-blocking file operations
- **Error Handling** - Clear error messages and retry options
- **Success Feedback** - Confirmation messages and visual feedback

## 🔧 **Configuration Options**

### **Network Settings**
```kotlin
// In ApiClient.kt
private val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)    // Connection timeout
    .readTimeout(60, TimeUnit.SECONDS)       // Read timeout
    .writeTimeout(60, TimeUnit.SECONDS)      // Write timeout
    .build()
```

### **File Upload Settings**
The app automatically handles:
- **File Type Detection** - Automatic MIME type detection
- **Large File Support** - Efficient streaming for large files
- **Error Recovery** - Automatic retry on network failures
- **Progress Tracking** - Real-time upload progress

## 🛡️ **Permissions**

Required permissions in `AndroidManifest.xml`:
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
<uses-permission android:name="android.permission.READ_MEDIA_VIDEO" />
<uses-permission android:name="android.permission.READ_MEDIA_AUDIO" />
```

## 🔄 **File Operations**

### **Upload Process**
1. User taps upload FAB
2. File picker opens with all file types
3. User selects file(s)
4. App shows progress indicator
5. File uploads with real-time progress
6. Success confirmation and list refresh

### **Download Process**
1. User taps download button on file
2. App starts background download
3. Progress indicator shows completion
4. File saved to device Downloads folder
5. Success message with file location

### **Share Creation**
1. User taps share button on file
2. Confirmation dialog appears
3. App creates shareable link
4. Share URL displayed with copy option
5. Link copied to clipboard for easy sharing

## 🎨 **Theming and Customization**

### **Material Design 3**
- **Dynamic Colors** - Adapts to system theme
- **Rounded Corners** - Modern card-based design
- **Elevation** - Subtle shadows for depth
- **Typography** - Consistent text styling

### **Custom Colors**
```xml
<!-- In colors.xml -->
<color name="purple_500">#FF6200EE</color>  <!-- Primary brand color -->
<color name="purple_700">#FF3700B3</color>  <!-- Primary variant -->
<color name="teal_200">#FF03DAC5</color>    <!-- Secondary color -->
```

## 🐛 **Troubleshooting**

### **Common Issues**

1. **Connection Refused**
   - Check server URL in `CloudUploaderApplication.kt`
   - Ensure server is running and accessible
   - Verify network connectivity

2. **Upload Failures**
   - Check file size limits on server
   - Verify storage space availability
   - Check network stability

3. **Permission Denied**
   - Grant storage permissions in device settings
   - Update target SDK if using Android 11+

### **Debug Logging**
Enable detailed logging in `ApiClient.kt`:
```kotlin
private val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY  // Shows full request/response
}
```

## 🚀 **Future Enhancements**

### **Planned Features**
- **🔐 Authentication** - User login and secure access
- **📁 Folder Organization** - Create and manage folders
- **🔍 Search Functionality** - Search files by name/type
- **🔄 Sync Status** - Offline/online sync indicators
- **📷 Camera Integration** - Direct photo/video capture
- **🎯 Batch Operations** - Multi-select and batch actions
- **📊 Advanced Analytics** - Detailed usage statistics
- **🔔 Notifications** - Upload/download completion alerts

### **Technical Improvements**
- **🗃️ Local Caching** - Offline file metadata
- **⚡ Performance Optimization** - Image loading and list scrolling
- **🧪 Unit Testing** - Comprehensive test coverage
- **🔄 Background Sync** - Automatic file synchronization
- **📱 Tablet Support** - Optimized layouts for larger screens

## 📖 **Development Guide**

### **Adding New Features**
1. Create data models in `data/model/`
2. Add API endpoints in `data/api/`
3. Update repository in `data/repository/`
4. Create/update ViewModels in `ui/viewmodel/`
5. Build UI components in `ui/fragment/`

### **Testing**
```bash
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

### **Building Release APK**
```bash
# Build release APK
./gradlew assembleRelease

# APK location: app/build/outputs/apk/release/
```

## 🤝 **Contributing**

1. Fork the repository
2. Create feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open Pull Request

## 📄 **License**

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 **Acknowledgments**

- **Material Design** - Google's design system
- **Retrofit** - Type-safe HTTP client
- **Kotlin Coroutines** - Asynchronous programming
- **Android Architecture Components** - Modern app architecture
- **Your Cloud Server** - The backend that powers this app

---

## 📞 **Support**

For support and questions:
- Check the troubleshooting section above
- Review server logs for API errors
- Ensure proper network configuration
- Verify server URL and accessibility

**Happy file managing! 📱☁️**