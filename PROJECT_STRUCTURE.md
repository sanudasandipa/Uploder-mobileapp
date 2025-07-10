# 📂 Project Structure

```
CloudUploader/
├── 📱 app/
│   ├── 🔧 build.gradle.kts          # App-level build configuration
│   ├── 🛡️ proguard-rules.pro        # ProGuard rules for release builds
│   └── 📁 src/main/
│       ├── 📄 AndroidManifest.xml    # App permissions and components
│       ├── 💻 java/com/clouduploader/app/
│       │   ├── 🚀 CloudUploaderApplication.kt      # Application class
│       │   ├── 📊 data/
│       │   │   ├── 🌐 api/
│       │   │   │   ├── ApiClient.kt                # Retrofit client setup
│       │   │   │   └── CloudApiService.kt          # API endpoints interface
│       │   │   ├── 📋 model/
│       │   │   │   └── Models.kt                   # Data classes
│       │   │   └── 🗄️ repository/
│       │   │       └── CloudRepository.kt          # Data layer
│       │   └── 🎨 ui/
│       │       ├── 📱 MainActivity.kt               # Main activity
│       │       ├── 🔄 adapter/
│       │       │   └── FilesAdapter.kt             # RecyclerView adapter
│       │       ├── 📄 fragment/
│       │       │   ├── FilesFragment.kt            # Files list screen
│       │       │   └── StorageFragment.kt          # Storage info screen
│       │       └── 🧠 viewmodel/
│       │           └── CloudViewModel.kt           # UI logic
│       └── 🎨 res/
│           ├── 🖼️ drawable/                        # Vector icons
│           ├── 📐 layout/                          # XML layouts
│           ├── 🍽️ menu/                            # Navigation menus
│           ├── 📱 mipmap-*/                        # App icons
│           ├── 🚢 navigation/                      # Navigation graph
│           ├── 🎨 values/                          # Colors, strings, themes
│           └── ⚙️ xml/                             # Configuration files
├── 🔧 build.gradle.kts              # Project-level build configuration
├── ⚙️ gradle.properties             # Gradle configuration
├── 📋 settings.gradle.kts           # Project settings
├── 🚀 setup.sh                     # Setup script
└── 📖 README.md                    # Comprehensive documentation
```

## 🎯 Key Components

### 📊 **Data Layer**
- **ApiClient**: Retrofit HTTP client with logging and timeouts
- **CloudApiService**: Type-safe API endpoints matching your server
- **Models**: Data classes with proper JSON serialization
- **CloudRepository**: Single source of truth for data operations

### 🎨 **UI Layer**
- **MainActivity**: Navigation host with bottom navigation
- **FilesFragment**: File list with upload, download, delete, share
- **StorageFragment**: Storage analytics with visual progress
- **FilesAdapter**: Efficient RecyclerView with ViewBinding

### 🧠 **Business Logic**
- **CloudViewModel**: MVVM architecture with LiveData
- **Coroutines**: Asynchronous operations with proper error handling
- **Navigation**: Type-safe navigation between screens

### 🎨 **Design System**
- **Material Design 3**: Modern components and theming
- **Vector Icons**: Scalable icons for all file types
- **Responsive Layouts**: Works on all screen sizes
- **Progress Indicators**: Real-time upload/download progress

## 🚀 **Features Implemented**

✅ **File Upload** - Any file type with progress tracking  
✅ **File Download** - Background downloads with progress  
✅ **File Management** - Delete with confirmation dialogs  
✅ **Storage Analytics** - Real-time usage statistics  
✅ **File Sharing** - Create and copy shareable links  
✅ **Pull-to-Refresh** - Swipe to refresh file lists  
✅ **Error Handling** - Comprehensive error management  
✅ **Offline Support** - Graceful handling of network issues  
✅ **Modern UI** - Material Design 3 with smooth animations  
✅ **Type Safety** - Kotlin with proper null safety  

## 📱 **Server Integration**

The app connects to your existing server at `http://140.245.238.153` and uses all available API endpoints:

- **GET /api/files** - List files
- **POST /api/upload** - Upload files
- **GET /api/download/{filename}** - Download files
- **DELETE /api/delete/{filename}** - Delete files
- **GET /api/storage** - Storage information
- **POST /api/share** - Create share links
- **GET /api/shares/{filename}** - List shares
- **DELETE /api/share/{shareId}** - Delete shares

## 🛠️ **Technology Stack**

- **Language**: Kotlin 100%
- **Architecture**: MVVM with Repository pattern
- **UI**: Material Design 3 Components
- **Networking**: Retrofit + OkHttp + Gson
- **Async**: Kotlin Coroutines + Flow
- **Navigation**: Navigation Component
- **Binding**: ViewBinding for type safety
- **Build**: Gradle with Kotlin DSL

This is a production-ready mobile app that provides a beautiful, intuitive interface for your cloud storage service! 🎉
