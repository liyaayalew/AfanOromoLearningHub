# Afan Oromo Learning Hub - Android Application

## 📱 Application Overview
Afan Oromo Learning Hub is an Android application designed to demonstarte how this kind of apps help users learn the Afan Oromo language through interactive lessons, vocabulary practice, and comprehension activities.

---

## 👥 Contributors
-  Eyasu Ephrem 
-  Liya Mekbib 
-  Matewos Belachew 
-  Tigist Kassa 
-  Yonas Woldegebreal
-  Nyaliep Choul

---

## 🎯 Features
- **User Authentication**: Login and signup with secure credential management
- **Interactive Lessons**: Structured lessons with progressive difficulty levels
- **Progress Tracking**: Monitor learning streak and words learned

## 🏗️ Architecture
- **Language**: Java
- **Architecture**: Model-View-Presenter (MVP) pattern
- **Database**: Room Persistence Library (SQLite wrapper)
- **UI Framework**: Android XML layouts with Material Design components
- **Build System**: Gradle with Kotlin DSL

## 📁 Project Structure
```
app/src/main/
├── java/com/example/afan_oromo_learning/
│   ├── adapters/           # RecyclerView adapters
│   │   ├── LessonAdapter.java       # Handles lesson list display
│   │   └── VocabularyAdapter.java   # Handles vocabulary list display
│   │
│   ├── auth/              # Authentication screens
│   │   ├── LoginActivity.java       # User login
│   │   ├── SignupActivity.java      # User registration
│   │   └── PermissionActivity.java  # Permission request screen
│   │
│   ├── database/          # Room database and data access
│   │   ├── AppDatabase.java   # Main database class
│   │   ├── LessonDao.java     # Lesson data operations
│   │   ├── ProgressDao.java   # User progress tracking
│   │   └── UserDao.java       # User data operations
│   │
│   ├── home/              # Main dashboard and navigation
│   │   ├── HomeActivity.java         # Main container with bottom navigation
│   │   └── DashboardFragment.java    # Dashboard UI and logic
│   │
│   ├── lessons/          # Lesson management
│   │   ├── LessonActivity.java           # Individual lesson view
│   │   ├── LessonListActivity.java       # List of all lessons
│   │   ├── LessonCompletionActivity.java # Lesson completion screen
│   │   ├── LessonManager.java            # Lesson business logic
│   │   └── VocabularyActivity.java       # Vocabulary list view
│   │
│   ├── models/           # Data model classes
│   │   ├── Lesson.java      # Lesson data structure
│   │   ├── Progress.java    # Progress tracking model
│   │   ├── User.java        # User data model
│   │   └── Vocabulary.java  # Vocabulary item model
│   │
│   ├── practice/         # Practice activities
│   │   ├── PracticeHubActivity.java        # Main practice hub
│   │   ├── ListeningPracticeActivity.java  # Listening exercises
│   │   ├── SpeakingPracticeActivity.java   # Speaking exercises
│   │   └── VocabularyPracticeActivity.java # Vocabulary practice
│   │
│   ├── profile/          # User profile and settings
│   │   ├── ProfileActivity.java   # User profile view
│   │   └── SettingsActivity.java  # App settings
│   │
│   ├── services/         # Background services
│   │   └── SyncService.java  # Data synchronization service
│   │
│   ├── splash/           # Splash screen
│   │   └── SplashActivity.java  # App launch screen
│   │
│   ├── utils/            # Utility classes
│   │   ├── PermissionManager.java   # Handles Android permissions
│   │   ├── SharedPrefManager.java   # Shared preferences wrapper
│   │   ├── FileDownloader.java      # File download utilities
│   │   └── NetworkUtils.java        # Network connectivity checks
│   │
│   ├── AppController.java  # Application class
│   └── MainActivity.java   # App entry point
│
├── res/                 # Resources
│   ├── drawable/       # Vector graphics and shapes
│   ├── layout/         # XML layout files
│   │   ├── activity_*.xml          # Activity layouts
│   │   ├── fragment_*.xml          # Fragment layouts
│   │   └── item_*.xml              # RecyclerView item layouts
│   │
│   ├── menu/           # Bottom navigation menu
│   ├── mipmap/         # App icons (multiple densities)
│   ├── values/         # Strings, colors, themes, dimensions
│   │   ├── strings.xml     # Text strings
│   │   ├── colors.xml      # Color definitions
│   │   ├── themes.xml      # App themes
│   │   ├── dimens.xml      # Dimension values
│   │   └── arrays.xml      # Array resources
│   │
│   ├── values-night/   # Dark theme resources
│   └── xml/            # Configuration files
│       ├── backup_rules.xml         # Auto-backup rules
│       └── data_extraction_rules.xml # Data extraction rules
│
└── assets/             # Static data files
    ├── lessons.json         # Lesson content data
    └── vocabulary.json      # Vocabulary data
```

## 🚀 Prerequisites
- **Android Studio** (Latest version recommended)
- **Android SDK** (API 24 minimum, API 34 target)
- **Java Development Kit** (JDK 11 or higher)
- **Gradle** (Wrapper included)

## 🔧 Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/black-try-AtoZ/AfanOromoLearningHub.git
cd AfanOromoLearningHub
```

### 2. Open in Android Studio
- Open Android Studio
- Select "Open an existing project"
- Navigate to the project directory
- Wait for Gradle sync to complete

### 3. Configure SDK
- Ensure you have Android SDK installed (API 24-34)
- Set up Android Virtual Device (AVD) or connect physical device

### 4. Build the Project
```bash
# Clean build
./gradlew clean

# Build debug APK
./gradlew assembleDebug

# Install on connected device
./gradlew installDebug
```

## 📱 Building the APK

### Debug Build
```bash
./gradlew assembleDebug
```
Output: `app/build/outputs/apk/debug/app-debug.apk`

### Release Build
```bash
./gradlew assembleRelease
```
Output: `app/build/outputs/apk/release/app-release.apk`

## 🎨 UI/UX Features
- **Material Design**: Consistent with Google's design guidelines
- **Responsive Layouts**: Adapts to different screen sizes
- **Bottom Navigation**: Easy access to main sections
- **Card-based Design**: Clean, modern interface
- **Progress Indicators**: Visual feedback for user progress

## 📊 Data Management
- **Local Storage**: Room database for offline access
- **Shared Preferences**: User settings and progress
- **Assets**: JSON files for initial lesson data

## 🔐 Permissions
The app requires the following permissions:
- `NOTIFICATION`: For potential future online features
- `RECORD_AUDIO`: For speaking practice exercises

## 📝 Development Notes

### Code Style
- Follow Android Java coding conventions
- Use meaningful variable and method names
- Add comments for complex logic
- Keep methods focused and single-purpose

### Testing
- Unit tests in `app/src/test/java/`

## 🔮 Future Enhancements
1. **Cloud Sync**: User progress synchronization
2. **Social Features**: Leaderboards and friend challenges
3. **Advanced Analytics**: Detailed learning insights
4. **More Languages**: Additional language support
5. **Gamification**: Badges and achievement system

## **Updated Future Enhancements Section:**

---

## 🔮 Future Enhancements (Based on Current Implementation)

### **High Priority (Core Features Missing)**
1. **Onboarding Flow** - Missing onboarding activities:
   - `LanguageSelectionActivity.java` - Language preference setup
   - `LearnerTypeActivity.java` - Learning style assessment
   - `GoalSelectionActivity.java` - Learning goals setup
   - `LevelAssessmentActivity.java` - Initial skill assessment

2. **Lesson Content Management** - Enhance current implementation:
   - Audio playback integration for lessons
   - Progress saving and synchronization
   - Lesson completion tracking

3. **User Authentication** - Complete the flow:
   - Proper API/database integration for login/signup
   - Password reset functionality
   - Session management

### **Medium Priority (Feature Enhancements)**
4. **Audio Features** - Add missing audio capabilities:
   - Voice recording for speaking practice
   - Audio playback controls
   - Pronunciation feedback system

5. **Progress System** - Enhanced tracking:
   - Streak tracking visualization
   - Achievement badges
   - Learning statistics dashboard

6. **Offline Support** - Complete offline capabilities:
   - Download lessons for offline access
   - Sync when online
   - Storage management

### **Low Priority (Nice-to-Have Features)**
7. **Social Features** - Community integration:
   - User profiles with avatars
   - Leaderboards
   - Friend challenges

8. **Advanced Analytics** - Learning insights:
   - Detailed progress reports
   - Learning pattern analysis
   - Personalized recommendations

9. **Content Expansion** - More learning materials:
   - Additional lesson categories
   - Cultural immersion content
   - Interactive quizzes

10. **Accessibility** - Improved accessibility:
    - Screen reader support
    - High contrast mode
    - Text size adjustments

### **Platform Expansion**
11. **Multi-platform Support**:
    - Tablet-optimized layouts
    - Wear OS companion app
    - Web dashboard for progress tracking

12. **Localization** - Support more languages:
    - Additional UI language translations
    - RTL layout support
    - Localized content


---

*Last Updated: January 2026*
