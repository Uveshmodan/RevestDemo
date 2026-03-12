# ProducBrowser

A Kotlin Multiplatform (KMP) product browser application for Android and iOS, built with Jetpack Compose Multiplatform. The app allows users to browse, search, and view details of products fetched from a remote API — with shared business logic across both platforms.

---

## Business Requirements

- Display a list of products fetched from a remote API
- Support real-time product search with debounce to avoid excessive API calls
- Show product details including image, title, price, rating, brand, and description
- Filter products by category
- Handle loading, error, and success states across all screens
- Search queries shorter than 2 characters are rejected with a user-friendly message
- Products with missing titles or invalid prices are filtered out before display
- Products are sorted by rating in descending order on search results

---

## Architecture Overview

The project follows **Clean Architecture** with a strict separation of layers:

```
UI Layer         →   Jetpack Compose Multiplatform screens & components
ViewModel Layer  →   State management using StateFlow + coroutines
Domain Layer     →   UseCases with business logic, Repository interfaces
Data Layer       →   Repository implementations, Ktor API client, DTOs
```

### Key patterns used

- **Repository pattern** — abstracts data sources behind an interface
- **UseCase pattern** — encapsulates business rules (validation, filtering, sorting)
- **Unidirectional data flow** — ViewModel exposes `StateFlow<UiState>`, UI only observes
- **Dependency Inversion** — all layers depend on interfaces, not concrete classes

### Tech stack

| Area | Library |
|---|---|
| UI | Jetpack Compose Multiplatform |
| Navigation | Jetbrains Navigation Compose |
| DI | Koin |
| Networking | Ktor |
| Serialization | kotlinx.serialization |
| Async | Kotlin Coroutines + Flow |
| Image loading | Coil |
| Testing | kotlin.test + kotlinx-coroutines-test |

### Module structure

```
composeApp/
  src/
    commonMain/
      data/
        remote/         ← Ktor API client, DTOs
        repository/     ← Repository implementations
      domain/
        model/          ← Domain models
        repository/     ← Repository interfaces
        usecase/        ← Business logic (GetProductsUseCase, SearchProductsUseCase)
      presentation/
        ui/             ← Compose screens
        viewmodel/      ← ViewModels with StateFlow
      di/               ← Koin modules
    androidMain/        ← Android entry point, Application class
    iosMain/            ← iOS entry point (MainViewController)
    commonTest/         ← Shared unit tests
```

---

## How to Build and Run

### Prerequisites

- Android Studio Meerkat or later
- Xcode 15 or later (for iOS)
- JDK 17
- Kotlin 2.x
- CocoaPods (for iOS dependencies)

### Android

1. Open the project in Android Studio
2. Wait for Gradle sync to complete
3. Select the `composeApp` run configuration
4. Choose an Android emulator or connected device
5. Click **Run**

Or via terminal:
```bash
./gradlew :composeApp:assembleDebug
```

### iOS

1. Open the project in Android Studio
2. Run the Kotlin framework build first:
```bash
./gradlew :composeApp:assembleXCFramework
```
3. Open `iosApp/iosApp.xcodeproj` in Xcode
4. Select a simulator (iPhone 16 or later recommended)
5. Press **Cmd + R** to build and run

Or run directly from Android Studio by selecting the `iosApp` run configuration with a simulator target.

---

## Trade-offs and Assumptions

**Passing objects through navigation**
Full `Product` objects are passed as navigation arguments using a custom `NavType` with JSON serialization. This avoids an extra API call on the detail screen but has a URL size limitation for very large objects. For production, passing only the product ID and re-fetching is safer.

**No local caching**
The app fetches fresh data on every launch with no offline support. A Room/SQLDelight cache layer would be the next step for production readiness.

**`ProductApiClient` as interface**
The API client was extracted to an interface to support dependency inversion and enable unit testing with a `FakeProductApiClient` — without needing any mocking library in KMP.

**Koin over other DI solutions**
Koin was chosen over Hilt because it is fully KMP-compatible. Hilt is Android-only and cannot be used in `commonMain`.

**Manual fakes over Mockative**
Unit tests use hand-written fake implementations instead of a mocking library. This avoids KSP configuration overhead and produces more readable tests that are easier to maintain.

**Error messages from API**
Error messages are surfaced directly from the exception message. In production these should be mapped to user-friendly strings per error type (4xx, 5xx, network).
