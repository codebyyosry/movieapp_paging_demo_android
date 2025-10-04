MovieDemo App 🎬 | Offline-First Movie Browser
A modern, offline-first Android application designed to demonstrate best practices using Jetpack Compose and Clean Architecture. This app fetches and displays popular movies from a REST API, providing a seamless user experience with powerful features like paging, local caching, and persistent favorites.

✨ Features
MovieDemo is built with a strong focus on performance and maintainability, incorporating the following key features:

Offline-First Data Flow: Guarantees data availability by using a Room Database as the Single Source of Truth (SSOT).

Infinite Scrolling: Efficiently loads large datasets using Paging 3 for smooth, lazy-loaded infinite scrolling.

Favorites Persistence: Users can toggle favorite movies, with status updates persisted locally in Room and immediately reflected in the UI.

Modern UI: A beautiful and fully responsive user interface built entirely with Jetpack Compose.

Enhanced UX: Includes a skeleton loading UI for better perceived performance while data is being fetched.

Robust State Management: Uses the MVVM/MVI pattern with a custom Resource wrapper to handle Loading, Success, and Error states consistently.

🏗️ Architecture: Clean & Feature-Based
This project adheres to a Clean Architecture methodology, organized into feature-based modules to ensure high separation of concerns and testability.

|

| Layer | Responsibility | Components |
| Presentation | Handles UI logic, state management, and user interaction. | Composables, Screens, ViewModel (State, Event, Effect). |
| Domain | Contains the core business logic, independent of external frameworks. | UseCases, Models, Mappers. |
| Data | Manages data retrieval from local (Room) and remote (Retrofit) sources. | Repository implementations, DAOs, API Services. |

Key Patterns
Single Source of Truth (SSOT): The Room database is the canonical data source. Network updates are always written to Room, and the UI observes Room for changes.

MVI Pattern:

Event: User actions or lifecycle events trigger an action in the ViewModel.

State: Represents the entire UI state at any given moment.

Effect: One-time events (e.g., showing a Snackbar, triggering navigation).

🛠️ Libraries & Tools
| Library | Purpose |
| Jetpack Compose | Declarative UI toolkit. |
| Coil | Fast and memory-efficient asynchronous image loading for Compose. |
| Paging 3 | Handles pagination and efficient lazy data loading. |
| Room | SQLite abstraction for offline data persistence. |
| Retrofit | Type-safe HTTP client for API calls. |
| Hilt | Dependency Injection framework for simplified scoping and lifecycle. |
| Navigation Compose | Handles in-app navigation between composable destinations. |
| Kotlin Coroutines | Asynchronous programming and concurrency. |
| Skeleton (Shimmer) | Implements the skeleton loading UI effect for better UX. |
| BuildConfig | Secure management of API keys and base URLs. |

🚀 Data Flow Diagram (High Level)
App Start: The UI requests data from the ViewModel.

ViewModel: Triggers a Use Case to get movies from the Repository.

Repository: Checks the Room DB (SSOT) first.

Network Check: If the data is stale or needed for paging, the Repository uses Retrofit to fetch the latest data from the API.

Cache Update: Network data is immediately saved back into the Room DB.

UI Update: Paging 3 observes the Room DB and emits fresh PagingData to the UI, which recomposes instantly.

⚙️ Setup
To build and run this application, you must provide your TMDB API key.

Clone the repository:

git clone [https://github.com/yourusername/moviedemo.git](https://github.com/yourusername/moviedemo.git)
cd moviedemo



Create local.properties:
In the root directory of the project, create a file named local.properties.

Add API Configuration:
Paste the following lines into your local.properties, replacing the placeholders with your actual values:

# Your API Key from The Movie Database (TMDB)
MOVIE_API_KEY="your_api_key_here"

# Base URL for the TMDB API
MOVIE_BASE_URL="[https://api.themoviedb.org/3/](https://api.themoviedb.org/3/)"



Run:
Open the project in Android Studio and run on an emulator or physical device.

📜 License
This project is licensed under the MIT License - see the LICENSE file for details.