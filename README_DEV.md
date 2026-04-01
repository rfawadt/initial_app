# Campus Events — Developer Guide

## Project Structure

```
CampusEvents/
├── app/
│   ├── build.gradle              # App dependencies (Material, Navigation, Lifecycle)
│   ├── src/main/
│   │   ├── AndroidManifest.xml   # Activities: LoginActivity (launcher), MainActivity
│   │   ├── java/com/tokens/campusevents/
│   │   │   ├── CampusEventsApplication.java
│   │   │   ├── LoginActivity.java        # Role selection (Student/Organizer)
│   │   │   ├── MainActivity.java         # Bottom nav + NavHostFragment
│   │   │   ├── data/
│   │   │   │   ├── model/
│   │   │   │   │   ├── Event.kt          # Event data class + EventStatus enum
│   │   │   │   │   ├── EventCategory.kt  # Category enum with display names
│   │   │   │   │   ├── EventUpdate.kt    # Update model + UpdateType enum
│   │   │   │   │   ├── Notification.kt   # Notification model + type enum
│   │   │   │   │   ├── RsvpStatus.kt     # RSVP enum + Rsvp data class
│   │   │   │   │   └── User.kt           # User model with saved/followed sets
│   │   │   │   └── repository/
│   │   │   │       ├── EventRepository.kt  # Event CRUD, search, filter
│   │   │   │       ├── MockData.kt         # 8 sample events + mock users/rsvps
│   │   │   │       └── UserRepository.kt   # RSVP mgmt, favorites, follows
│   │   │   └── ui/
│   │   │       ├── adapter/               # 7 RecyclerView adapters
│   │   │       ├── home/                  # Home feed (US-01, US-03)
│   │   │       ├── search/                # Search + filter (US-04, US-05, US-06)
│   │   │       ├── discover/              # My Day schedule (US-28)
│   │   │       ├── favorites/             # Saved + My Events (US-16)
│   │   │       ├── profile/               # Profile + mode switch (US-07)
│   │   │       ├── eventdetail/           # Event detail (US-09, US-11, US-19)
│   │   │       ├── rsvp/                  # RSVP flow (US-12, US-15, US-22)
│   │   │       ├── notifications/         # Notifications list
│   │   │       └── organizer/             # Dashboard, Create, Edit, RSVP list, Post update
│   │   └── res/
│   │       ├── layout/           # 22 XML layouts matching Figma
│   │       ├── drawable/         # Icons + shape backgrounds
│   │       ├── menu/             # bottom_nav_menu.xml
│   │       ├── navigation/       # nav_graph.xml (all destinations + actions)
│   │       └── values/           # colors, strings, themes, dimens
├── build.gradle                  # Project-level (AGP 8.2)
├── settings.gradle
└── gradle.properties
```

## Architecture

- **MVVM** — ViewModel + LiveData per feature module
- **Fragment-based navigation** — Single Activity with NavHostFragment
- **BottomNavigationView** — 5 tabs: Home, Search, Discover, Favourites, Profile
- **Mock data** — All data lives in `MockData.kt`, no real backend

## Key Design Decisions

| Decision | Rationale |
|----------|-----------|
| Dark theme default | Matches Figma wireframes |
| In-memory data | No backend needed for halfway checkpoint |
| Singleton repositories | Shared state across fragments without DI |
| ViewBinding enabled | Type-safe view access |

## How to Run

1. Open the `CampusEvents/` folder in Android Studio Hedgehog+
2. Sync Gradle (it will download dependencies)
3. Run on emulator or device (minSdk 24)
4. Login screen → select Student or Organizer → Sign In

## Feature Map (Halfway Checkpoint)

| User Story | Screen | Status |
|-----------|--------|--------|
| US-01 Unified feed | Home | Done |
| US-03 Today view | Home (category chip) | Done |
| US-04 Search by keyword | Search | Done |
| US-05 Filter by category | Search + Home | Done |
| US-06 Filter by location | Filter sheet | Done |
| US-07 Follow organizers | Event Detail | Done |
| US-09 Detailed event page | Event Detail | Done |
| US-11 Venue details | Event Detail | Done |
| US-12 RSVP Going/Interested | RSVP | Done |
| US-15 Cancel RSVP | RSVP | Done |
| US-16 My Events dashboard | Favourites tab | Done |
| US-19 View updates | Event Detail | Done |
| US-21 Share event | Event Detail + Cards | Done |
| US-22 Capacity enforcement | RSVP (Going check) | Done |
| US-28 My Day schedule | Discover tab | Done |
| ORG-01 Create event | Create Event | Done |
| ORG-02 Edit/cancel event | Edit Event | Done |
| ORG-03 View RSVP list | RSVP List | Done |
| ORG-04 Post updates | Post Update | Done |
