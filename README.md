KMM Product Browser App

A fully cross-platform Kotlin Multiplatform Mobile (KMM) application built using Kotlin Compose Multiplatform (CMP) for Android and iOS. The app consumes product data from the public API:

📦 https://dummyjson.com/products

This project demonstrates clean architecture, networking with Ktor, shared UI using Compose Multiplatform, and shared business logic through KMM.

📘 Business Requirements

Revest is exploring an internal cross-platform mobile product catalog. The app must allow users to:

✔ 1. View product list

Display product title, price, and thumbnail.

✔ 2. View product details

Show title, description, brand, price, rating.

✔ 3. Search products

Implement API-based search using DummyJSON /products/search?q=.

✔ 4. (Optional) Filter products by category

DummyJSON provides /products/category/{category}.

🏗 Technical Requirements

🔹 Kotlin Multiplatform Mobile (Android + iOS)

🔹 Clean Architecture (Data → Domain → Presentation)

🔹 Compose Multiplatform for shared UI

🔹 Ktor Client for API requests

🔹 Kotlinx.serialization for JSON

🔹 StateFlow for ViewModel state management

🔹 One use case minimum (GetProducts, SearchProducts included)

🔹 At least one Unit Test (included)

🔹 DI: Manual dependency injection (simple + clean)

📁 Project Architecture Overview

KMMProductBrowser/
├── androidApp/                    # Android application (Compose UI host)
├── iosApp/                        # iOS app (Swift/Compose host)
└── shared/                        # Shared KMM module
├── data/                      # Data layer
│   ├── remote/                # Ktor API client + DTOs
│   └── repository/            # Repository implementation
├── domain/                    # Business logic
│   ├── model/                 # Domain models
│   └── usecase/               # Use cases
├── presentation/              # UI + ViewModel
│   ├── viewmodel/             # State management
│   └── ui/                    # Compose Screens
└── commonTest/                # Shared Unit Tests

🔗 API Integration

Endpoints used:

Feature

API Endpoint

Get all products

/products

Search products

/products/search?q=keyword

Filter by category (optional)

/products/category/{name}

Ktor + Serialization setup:

install(ContentNegotiation) {
json(Json { ignoreUnknownKeys = true })
}

🧠 Clean Architecture Summary

Data Layer

Ktor API client

DTOs & Mappers

Repository Implementation

Domain Layer

Pure Kotlin business models

Use cases: GetProductsUseCase, SearchProductsUseCase

Presentation Layer

ProductListViewModel using StateFlow

Shared composables using Compose Multiplatform

UI state modeled via immutable ProductListState

📱 UI Screens

Product List Screen

Search bar (real-time API search)

LazyColumn listing products

Loading & error states included

Product Detail Screen

Title, description, price, brand, rating

(Optional) Carousel of product images

🧪 Unit Tests

A shared test verifies business logic:

GetProductsUseCaseTest tests use-case correctness using a fake repository.

▶️ How to Run the Project

Android

Open project in Android Studio.

Let Gradle sync.

Run the androidApp configuration.

iOS

Run:

./gradlew :shared:assembleXCFramework

Open iosApp/iosApp.xcworkspace in Xcode.

Select a simulator and press Run.

Note: Compose Multiplatform for iOS is still evolving; Xcode integration is already configured in the project.

🧩 Assumptions & Trade-offs

Image loading: simple placeholder for CMP; full loader can be added (Kamel, Coil-Native, etc.)

ViewModel lifecycle: Android injects lifecycleScope; iOS uses internal KMM scope

Error messages simplified for assignment clarity

No caching layer added (SQLDelight optional)

Navigation is minimalistic; real app may use multiplatform navigation library