# Restaurant Management Application

## Overview
A comprehensive Android application for restaurant management that supports both staff and guest user roles. The application provides features for menu management, table reservations, order processing, and analytics.

**Version:** 1.0  
**Package:** com.restaurant.app  
**Minimum SDK:** 21 (Android 5.0 Lollipop)  
**Target SDK:** 36 (Android 14+)  
**Compile SDK:** 36

---

## Features

### For Staff Users
- **Menu Management**
  - Add, edit, and delete menu items
  - Upload menu item images
  - Set restock reminders for low inventory items
  - Mark items as restocked
  - Search and filter menu items

- **Reservation Management**
  - View all reservations
  - Create new reservations
  - Edit existing reservations
  - Delete reservations
  - Add notes to reservations
  - Specify reservation types (Birthday, Dinner, Custom)
  - Search reservations by guest name or party size

- **Table Management**
  - Add and manage restaurant tables
  - View table availability status
  - Edit table details (number, capacity, location)
  - Delete tables
  - Real-time status updates (Available/Occupied)

- **Order Management**
  - View all customer orders
  - Track order status
  - View order details including customer notes

- **Analytics & Statistics**
  - View total number of reservations
  - Track menu item count
  - Monitor table occupancy
  - View daily revenue statistics
  - Track restock reminders

- **Profile Management**
  - View staff profile information
  - Update profile details
  - Logout functionality

### For Guest Users
- **Menu Browsing**
  - View complete menu with images and prices
  - Search menu items
  - View item details

- **Order Creation**
  - Create orders from menu items
  - Add special notes to orders
  - Select quantity

- **Reservation System**
  - Create table reservations
  - Specify party size, date, and time
  - Add reservation notes
  - Choose reservation type
  - View reservation confirmation

- **Profile Management**
  - View profile information
  - Update guest details
  - Logout functionality

---


---

## Third-Party Resources & Dependencies

### 1. **Android Jetpack Libraries**

#### AndroidX AppCompat
- **Version:** 1.6.1
- **Purpose:** Backward compatibility support for newer Android features
- **License:** Apache License 2.0
- **Source:** [GitHub - AndroidX](https://github.com/androidx/androidx)
- **Documentation:** [AppCompat Library](https://developer.android.com/jetpack/androidx/releases/appcompat)

#### Material Components for Android
- **Version:** 1.13.0
- **Purpose:** Material Design UI components (buttons, cards, text fields, FABs)
- **License:** Apache License 2.0
- **Source:** [GitHub - Material Components Android](https://github.com/material-components/material-components-android)
- **Documentation:** [Material Design](https://material.io/develop/android)

#### ConstraintLayout
- **Version:** 2.1.4
- **Purpose:** Flexible layout system for responsive UI design
- **License:** Apache License 2.0
- **Documentation:** [ConstraintLayout Guide](https://developer.android.com/reference/androidx/constraintlayout/widget/ConstraintLayout)

#### Activity
- **Version:** 1.8.0
- **Purpose:** Activity lifecycle management and result APIs
- **License:** Apache License 2.0
- **Documentation:** [Activity Library](https://developer.android.com/jetpack/androidx/releases/activity)

#### Fragment
- **Version:** 1.8.9
- **Purpose:** Fragment lifecycle management for modular UI
- **License:** Apache License 2.0
- **Documentation:** [Fragment Guide](https://developer.android.com/guide/fragments)

#### Core
- **Version:** 1.17.0
- **Purpose:** Core Android utilities and helper classes
- **License:** Apache License 2.0
- **Documentation:** [AndroidX Core](https://developer.android.com/jetpack/androidx/releases/core)

### 2. **Room Database**

#### Room Runtime
- **Version:** 2.6.1
- **Purpose:** SQLite abstraction layer for local data persistence
- **License:** Apache License 2.0
- **Source:** [GitHub - Room](https://github.com/androidx/androidx/tree/androidx-main/room)
- **Documentation:** [Room Persistence Library](https://developer.android.com/training/data-storage/room)
- **Features Used:**
  - Entity definitions for MenuItem, Reservation, Table, Order
  - DAO (Data Access Objects) interfaces
  - LiveData integration for reactive UI updates
  - Database migrations support

#### Room Compiler
- **Version:** 2.6.1
- **Purpose:** Annotation processor for Room database code generation
- **License:** Apache License 2.0
- **Type:** Annotation Processor (compile-time only)

### 3. **Image Loading - Glide**

#### Glide
- **Version:** 4.13.0
- **Purpose:** Fast and efficient image loading and caching library
- **License:** BSD, MIT, and Apache License 2.0
- **Source:** [GitHub - Glide](https://github.com/bumptech/glide)
- **Documentation:** [Glide Documentation](https://bumptech.github.io/glide/)
- **Features Used:**
  - Loading images from URIs
  - Image caching (memory and disk)
  - Placeholder images
  - Automatic image resizing and transformation

#### Glide Compiler
- **Version:** 4.13.0
- **Purpose:** Annotation processor for Glide API generation
- **License:** BSD, MIT, and Apache License 2.0
- **Type:** Annotation Processor (compile-time only)

### 4. **Networking - Retrofit & OkHttp**

#### Retrofit
- **Version:** 3.0.0
- **Purpose:** Type-safe HTTP client for RESTful API communication
- **License:** Apache License 2.0
- **Source:** [GitHub - Retrofit](https://github.com/square/retrofit)
- **Documentation:** [Retrofit Documentation](https://square.github.io/retrofit/)
- **Features Used:**
  - RESTful API calls
  - Async/sync request handling
  - Custom headers and interceptors
  - Error handling

#### Retrofit Gson Converter
- **Version:** 3.0.0
- **Purpose:** JSON serialization/deserialization for Retrofit
- **License:** Apache License 2.0
- **Features Used:**
  - Automatic JSON to Java object conversion
  - Request/response body parsing

#### OkHttp Logging Interceptor
- **Version:** 5.3.2
- **Purpose:** HTTP request/response logging for debugging
- **License:** Apache License 2.0
- **Source:** [GitHub - OkHttp](https://github.com/square/okhttp)
- **Documentation:** [OkHttp Documentation](https://square.github.io/okhttp/)
- **Features Used:**
  - Request/response logging
  - Network debugging
  - HTTP interceptor chains

### 5. **JSON Parsing - Gson**

#### Google Gson
- **Version:** 2.10.1
- **Purpose:** JSON serialization and deserialization library
- **License:** Apache License 2.0
- **Source:** [GitHub - Gson](https://github.com/google/gson)
- **Documentation:** [Gson User Guide](https://github.com/google/gson/blob/master/UserGuide.md)
- **Features Used:**
  - JSON to Java object conversion
  - Java object to JSON conversion
  - Custom type adapters
  - Null value handling

### 6. **Testing Libraries**

#### JUnit
- **Version:** 4.13.2
- **Purpose:** Unit testing framework
- **License:** Eclipse Public License 1.0
- **Source:** [GitHub - JUnit4](https://github.com/junit-team/junit4)
- **Documentation:** [JUnit Documentation](https://junit.org/junit4/)

#### AndroidX Test - JUnit Extension
- **Version:** 1.1.5
- **Purpose:** Android-specific JUnit extensions for instrumented tests
- **License:** Apache License 2.0
- **Documentation:** [AndroidX Test](https://developer.android.com/training/testing/junit-runner)

#### Espresso Core
- **Version:** 3.5.1
- **Purpose:** UI testing framework for Android
- **License:** Apache License 2.0
- **Documentation:** [Espresso Testing](https://developer.android.com/training/testing/espresso)

### 7. **Build Tools**

#### Android Gradle Plugin
- **Version:** 8.13.2
- **Purpose:** Build system for Android applications
- **License:** Apache License 2.0
- **Documentation:** [Android Gradle Plugin](https://developer.android.com/build)

---

## API Integration

### Base URL
```
http://web.socem.plymouth.ac.uk/COMP2000/ReservationApi/api/
```

### Endpoints Used

1. **POST /DatabaseCreation**
   - Creates student database for the application
   - Body: `{ "studentid": "YOUR_STUDENT_ID" }`

2. **POST /Reservations**
   - Creates a new reservation
   - Body: Reservation object with guest details

3. **POST /Staff**
   - Staff registration endpoint
   - Body: Staff registration details

4. **POST /Users**
   - Guest user registration endpoint
   - Body: User registration details

5. **GET /Reservations**
   - Retrieves all reservations

6. **PUT /Reservations/{id}**
   - Updates existing reservation

7. **DELETE /Reservations/{id}**
   - Deletes a reservation

### Network Security Configuration
The application uses a custom network security configuration to allow cleartext HTTP traffic to the university API server:
- File: `res/xml/network_security_config.xml`
- Domain: `web.socem.plymouth.ac.uk`

---

## Design Patterns & Architecture

### 1. **MVVM (Model-View-ViewModel)**
- **Models:** Data classes for business logic (MenuItem, Reservation, Table, Order, User)
- **Views:** Activities and Fragments for UI
- **ViewModels:** Bridge between Model and View, manages UI-related data

### 2. **Repository Pattern**
- Room DAOs act as repositories for data access
- Abstraction layer between data sources and business logic

### 3. **Singleton Pattern**
- `AppDatabase` - Single database instance
- `ApiClient` - Single Retrofit instance

### 4. **Observer Pattern**
- LiveData for reactive UI updates
- Database changes automatically update UI

### 5. **Adapter Pattern**
- RecyclerView adapters for lists (MenuAdapter, ReservationAdapter, TableAdapter)

---

## Key Features Implementation

### Password Visibility Toggle
- All password fields include show/hide password functionality
- Eye icon button in TextInputLayout
- Toggles between password and visible text

### Image Handling
- Glide for efficient image loading
- Support for URI-based images
- Image preview in add/edit menu items (200x200dp)
- Default placeholder images

### Search Functionality
- Real-time search in Menu, Reservations, Tables
- Filter by name, price, guest name, party size
- SearchView with custom styling for dark theme

### Role-Based Access Control
- Staff and Guest user types
- Different UI and features based on user role
- SharedPreferences for user session management

### Dark Theme
- Custom color scheme for modern UI
- Material Design color tokens
- Consistent theming across all screens

### Responsive Design
- ScrollView for small screens
- ConstraintLayout for flexible layouts
- Bottom padding to avoid FAB overlap
- Material CardViews for elevated content

---

## Color Scheme

```xml
Primary: #1E88E5 (Blue)
Primary Dark: #1565C0 (Dark Blue)
Accent: #FFA726 (Orange)
Background Dark: #121212 (Almost Black)
Surface Dark: #1E1E1E (Dark Gray)
Text Primary Dark: #FFFFFF (White)
Text Secondary Dark: #B0B0B0 (Light Gray)
Error: #CF6679 (Red)
Success: #4CAF50 (Green)
```

---

## Permissions Required

### AndroidManifest.xml
```xml
<!-- Internet access for API calls -->
<uses-permission android:name="android.permission.INTERNET" />

<!-- Access network state -->
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

<!-- Read external storage for image selection (Android 12 and below) -->
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"
    android:maxSdkVersion="32" />

<!-- Read media images (Android 13+) -->
<uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
```

---

## Installation & Setup

### Prerequisites
- Android Studio Arctic Fox or later
- JDK 11 or later
- Android SDK 21 or higher
- Internet connection for API access
- Plymouth University VPN (for API access)

### Build Instructions

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd RestaurantApp
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an Existing Project"
   - Navigate to the RestaurantApp directory

3. **Sync Gradle**
   - Android Studio will automatically sync Gradle
   - Wait for dependencies to download

4. **Build the project**
   ```bash
   ./gradlew build
   ```

5. **Run on emulator or device**
   - Click "Run" in Android Studio
   - Select target device/emulator
   - Application will install and launch

### First-Time Setup

1. **Connect to Plymouth VPN**
   - Required to access the university API

2. **Initialize Database**
   - Launch the app
   - Use "Initialize Database" option
   - Enter your student ID (e.g., 10123456)
   - Wait for confirmation

3. **Register Users**
   - Register as Staff or Guest
   - Use valid student ID
   - Fill in all required fields

4. **Login**
   - Use registered credentials
   - Staff: Full access to all features
   - Guest: Limited access (menu, orders, reservations)

---

## Testing

### Unit Tests
- JUnit for business logic testing
- Location: `app/src/test/java/`

### Instrumented Tests
- Espresso for UI testing
- Location: `app/src/androidTest/java/`

### Manual Testing
- Test on multiple Android versions (API 21-36)
- Test on different screen sizes
- Test with/without network connection
- Test role-based access control

---

## Known Issues & Limitations

1. **API Dependency**
   - Requires Plymouth University VPN for API access
   - HTTP (not HTTPS) connection to university server
   - Network security config required for cleartext traffic

2. **Image Storage**
   - Images stored as URI strings in database
   - No cloud storage integration
   - Images may be lost if file is deleted from device

3. **Offline Support**
   - Limited offline functionality
   - API calls require active internet connection
   - Local database provides some offline capability

---

## Future Enhancements

1. **Push Notifications**
   - Reservation reminders
   - Order status updates
   - Table availability alerts

2. **Payment Integration**
   - Online payment for orders
   - Payment history tracking

3. **Real-time Updates**
   - WebSocket for live order updates
   - Real-time table status changes

4. **Advanced Analytics**
   - Revenue charts and graphs
   - Popular menu items tracking
   - Peak hours analysis

5. **Multi-language Support**
   - Internationalization (i18n)
   - Multiple language options

6. **Cloud Synchronization**
   - Firebase integration
   - Cross-device synchronization
   - Cloud image storage

---

## License

This project is developed as part of COMP2000 coursework at Plymouth University.

### Third-Party Licenses

All third-party libraries used in this project are licensed under open-source licenses:

- **Apache License 2.0:** AndroidX libraries, Material Design, Room, Retrofit, OkHttp, Gson
- **BSD License:** Glide
- **MIT License:** Glide
- **Eclipse Public License 1.0:** JUnit

Full license texts can be found in the respective library repositories.

---

## Credits

### Development
- **Student ID:** [YOUR_STUDENT_ID]
- **Course:** COMP2000 - Software Engineering
- **Institution:** Plymouth University
- **Academic Year:** 2025-2026

### API Provider
- **Plymouth University API:** COMP2000 Reservation API
- **Base URL:** http://web.socem.plymouth.ac.uk/COMP2000/ReservationApi/

### Resources
- Material Design Icons
- Android Developer Documentation
- Stack Overflow Community

---

## Contact & Support

For issues, questions, or contributions:

- **Email:** [your-email@students.plymouth.ac.uk]
- **Course:** COMP2000 Software Engineering
- **University:** Plymouth University

---

## Changelog

### Version 1.0 (January 2026)
- Initial release
- Staff and Guest user roles
- Menu management with image upload
- Reservation system with types and notes
- Table management
- Order creation
- Statistics dashboard
- Profile management
- Dark theme UI
- Material Design components
- API integration with Plymouth University server
- Local Room database for offline storage
- Search functionality across all modules
- Password visibility toggle
- Responsive layouts for all screen sizes

---

## Documentation References

- [Android Developer Guide](https://developer.android.com/guide)
- [Material Design Guidelines](https://material.io/design)
- [Room Persistence Library](https://developer.android.com/training/data-storage/room)
- [Retrofit Documentation](https://square.github.io/retrofit/)
- [Glide Documentation](https://bumptech.github.io/glide/)
- [MVVM Architecture](https://developer.android.com/topic/architecture)

---

**Last Updated:** January 9, 2026  
**Version:** 1.0  
**Build:** Release
