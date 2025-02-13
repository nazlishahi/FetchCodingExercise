# Fetch Rewards Coding Challenge

This is a coding challenge project that fetches a list of items from a given JSON endpoint and displays them in a RecyclerView, following MVVM architecture and clean coding principles.

## 📌 Features

- Fetches item data from a remote JSON source.
- Displays item information in a structured format using a `RecyclerView`.
- Implements MVVM architecture for better separation of concerns.
- Uses Coroutines for asynchronous operations.
- Handles errors, supports device rotation and day/night modes.

## 🛠️ Tech Stack

- **Language**: Kotlin
- **Architecture**: MVVM + Clean Architecture
- **Business Logic:** ViewModel, LiveData, UseCase, Repository
- **UI Components**: RecyclerView, Activity, Fragment, Jetpack navigation component
- **Networking**: Retrofit, OkHttpClient
- **Dependency Injection**: Hilt
- **Concurrency**: Coroutines
- **Unit Testing**: JUnit, Mockito

## 📡 API Source

The app fetches country data from:

```
https://fetch-hiring.s3.amazonaws.com/hiring.json
```

## 🚀 Setup & Run

1. Clone the repository:
   ```sh
   git clone https://github.com/nazlishahi/FetchCodingExercise.git
   ```
2. Open the project in Android Studio.
3. Sync Gradle and build the project.
4. Run the app on an emulator or device.

## 🧪 Testing

Run unit tests with:

```sh
./gradlew test
```
## 🧹 Code Formatting (ktlint)
This project includes ktlint for Kotlin code formatting. To check and fix formatting issues, run:
```
./gradlew ktlintCheck   # To check for linting issues
./gradlew ktlintFormat  # To automatically fix formatting issues
```
Following the ktlint rules ensures a consistent coding style throughout the project.

## 📸 Screenshots and recording
<p>
  <img src="https://github.com/user-attachments/assets/87a94645-0bde-45e0-8889-913ed60a3a21" width="200"/>
&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
  
  <img src="https://github.com/user-attachments/assets/35f7f1c4-e9e4-4971-a9ce-cfd1e9e7796e" width="200"/>
&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp   
  
  <img src="https://github.com/user-attachments/assets/a9ac31a0-3407-4e5f-a870-651c0cc598c8" width="200"/>
</p>


[https://github.com/user-attachments/assets/cf06e462-1008-4223-8e1c-8ec33d0658ad](https://github.com/user-attachments/assets/cf06e462-1008-4223-8e1c-8ec33d0658ad)

## [📜](https://github.com/user-attachments/assets/08ac4112-64f3-4857-bbc1-0cfde894b49e📜) License

This project is for coding evaluation purposes.

---

### ✨ Contributions & Feedback

Feel free to fork, open issues, or suggest improvements!

---
