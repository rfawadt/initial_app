# Team Roles and Branch Plan

This split is meant for real collaboration on the final repository.
It is balanced by feature size, screen complexity, shared resources, and integration work.
Do not use it to misrepresent who did work they did not actually do.

## Principles

1. Each person owns a clear feature area with minimal overlap.
2. Shared files are assigned to one owner to reduce merge conflicts.
3. Equal contribution is measured by real implementation, integration, testing, and PR work, not identical file counts.
4. Everyone should make their own branch, commits, PR description, and test notes for the files they actually handled.

## Recommended Merge Order

1. Core and data foundation
2. Login, profile, and organizer shell
3. Home and search flows
4. Discover, favourites, and notifications
5. Event detail, RSVP, and update flows

## Ownership Split

### 1. Ayaan Arif Aziz

Primary area: foundation, data, navigation, and event form base

Files:
- `build.gradle`
- `settings.gradle`
- `gradle.properties`
- `gradlew`
- `gradlew.bat`
- `gradle/wrapper/**`
- `app/src/main/AndroidManifest.xml`
- `app/src/main/java/com/tokens/campusevents/CampusEventsApplication.java`
- `app/src/main/java/com/tokens/campusevents/MainActivity.java`
- `app/src/main/java/com/tokens/campusevents/data/**`
- `app/src/main/java/com/tokens/campusevents/ui/organizer/CreateEventFragment.java`
- `app/src/main/java/com/tokens/campusevents/ui/organizer/CreateEventViewModel.java`
- `app/src/main/java/com/tokens/campusevents/ui/organizer/EditEventFragment.java`
- `app/src/main/res/layout/activity_main.xml`
- `app/src/main/res/layout/fragment_create_event.xml`
- `app/src/main/res/layout/fragment_edit_event.xml`
- `app/src/main/res/navigation/nav_graph.xml`
- `app/src/main/res/menu/bottom_nav_menu.xml`
- `app/src/main/res/values/**`

Balancing responsibility:
- own first bootstrap PR
- keep Gradle sync/build green while other branches are merged

### 2. Bilal Awan

Primary area: home feed, search flow, and shared discovery UI

Files:
- `app/src/main/java/com/tokens/campusevents/ui/home/**`
- `app/src/main/java/com/tokens/campusevents/ui/search/**`
- `app/src/main/java/com/tokens/campusevents/ui/adapter/CategoryChipAdapter.java`
- `app/src/main/java/com/tokens/campusevents/ui/adapter/FeaturedEventAdapter.java`
- `app/src/main/res/layout/fragment_home.xml`
- `app/src/main/res/layout/fragment_search.xml`
- `app/src/main/res/layout/fragment_filter_sheet.xml`
- `app/src/main/res/layout/item_category_chip.xml`
- `app/src/main/res/layout/item_event_featured.xml`
- `app/src/main/res/drawable/bg_search_bar.xml`
- `app/src/main/res/drawable/bg_chip.xml`
- `app/src/main/res/drawable/bg_chip_selected.xml`
- `app/src/main/res/drawable/ic_search.xml`
- `app/src/main/res/drawable/ic_filter.xml`
- `app/src/main/res/drawable/ic_home.xml`

Balancing responsibility:
- verify home and search screens on emulator after merge
- resolve shared UI regressions in chips and featured cards

### 3. Shehryar Asif

Primary area: discover, favourites, notifications, and reusable list cards

Files:
- `app/src/main/java/com/tokens/campusevents/ui/discover/**`
- `app/src/main/java/com/tokens/campusevents/ui/favorites/**`
- `app/src/main/java/com/tokens/campusevents/ui/notifications/**`
- `app/src/main/java/com/tokens/campusevents/ui/adapter/EventCardAdapter.java`
- `app/src/main/java/com/tokens/campusevents/ui/adapter/ScheduleBlockAdapter.java`
- `app/src/main/java/com/tokens/campusevents/ui/adapter/NotificationAdapter.java`
- `app/src/main/res/layout/fragment_discover.xml`
- `app/src/main/res/layout/fragment_favorites.xml`
- `app/src/main/res/layout/fragment_notifications.xml`
- `app/src/main/res/layout/item_event_card.xml`
- `app/src/main/res/layout/item_schedule_block.xml`
- `app/src/main/res/layout/item_notification.xml`
- `app/src/main/res/drawable/bg_card.xml`
- `app/src/main/res/drawable/bg_card_elevated.xml`
- `app/src/main/res/drawable/bg_conflict_banner.xml`
- `app/src/main/res/drawable/bg_notification_card.xml`
- `app/src/main/res/drawable/ic_notification.xml`
- `app/src/main/res/drawable/ic_discover.xml`
- `app/src/main/res/drawable/ic_favorites.xml`

Balancing responsibility:
- run student-flow smoke test for discover, favourites, and notifications
- fix list-item rendering issues inside owned screens

### 4. Abdul Moeed

Primary area: event detail, RSVP, and update delivery flows

Files:
- `app/src/main/java/com/tokens/campusevents/ui/eventdetail/**`
- `app/src/main/java/com/tokens/campusevents/ui/rsvp/**`
- `app/src/main/java/com/tokens/campusevents/ui/organizer/PostUpdateFragment.java`
- `app/src/main/java/com/tokens/campusevents/ui/adapter/UpdateAdapter.java`
- `app/src/main/res/layout/fragment_event_detail.xml`
- `app/src/main/res/layout/fragment_rsvp.xml`
- `app/src/main/res/layout/fragment_post_update.xml`
- `app/src/main/res/layout/item_update.xml`
- `app/src/main/res/drawable/bg_seat_progress.xml`
- `app/src/main/res/drawable/bg_update_type.xml`
- `app/src/main/res/drawable/bg_update_type_selected.xml`
- `app/src/main/res/drawable/ic_calendar.xml`
- `app/src/main/res/drawable/ic_location.xml`
- `app/src/main/res/drawable/ic_people.xml`
- `app/src/main/res/drawable/ic_share.xml`
- `app/src/main/res/drawable/ic_warning.xml`

Balancing responsibility:
- test home to event detail to RSVP navigation end-to-end
- validate all update and seat-availability states

### 5. Rana Fawad Tahir

Primary area: login, profile, organizer dashboard, and attendee management

Files:
- `app/src/main/java/com/tokens/campusevents/LoginActivity.java`
- `app/src/main/java/com/tokens/campusevents/ui/profile/**`
- `app/src/main/java/com/tokens/campusevents/ui/organizer/OrganizerDashboardFragment.java`
- `app/src/main/java/com/tokens/campusevents/ui/organizer/OrganizerViewModel.java`
- `app/src/main/java/com/tokens/campusevents/ui/organizer/RsvpListFragment.java`
- `app/src/main/java/com/tokens/campusevents/ui/adapter/OrganizerEventAdapter.java`
- `app/src/main/java/com/tokens/campusevents/ui/adapter/RsvpListAdapter.java`
- `app/src/main/res/layout/activity_login.xml`
- `app/src/main/res/layout/fragment_profile.xml`
- `app/src/main/res/layout/fragment_organizer_dashboard.xml`
- `app/src/main/res/layout/fragment_rsvp_list.xml`
- `app/src/main/res/layout/item_organizer_event.xml`
- `app/src/main/res/layout/item_rsvp_attendee.xml`
- `app/src/main/res/drawable/bg_button_primary.xml`
- `app/src/main/res/drawable/bg_button_secondary.xml`
- `app/src/main/res/drawable/bg_button_accent.xml`
- `app/src/main/res/drawable/bg_input.xml`
- `app/src/main/res/drawable/ic_back.xml`
- `app/src/main/res/drawable/ic_add.xml`
- `app/src/main/res/drawable/ic_edit.xml`
- `app/src/main/res/drawable/ic_profile.xml`

Balancing responsibility:
- test login to profile to organizer mode switching
- verify organizer dashboard and attendee list flows after merge

## Branch Names

- `ayaan/foundation-data-forms`
- `bilal/home-search-ui`
- `shehryar/discover-favorites-notifications`
- `abdul/event-rsvp-updates`
- `fawad/login-profile-organizer`

## Branch Workflow

1. Create the final GitHub repository once.
2. Push one clean baseline branch or `main` with the current fixed project.
3. Each teammate creates only their own feature branch from latest `main`.
4. Each teammate edits only the files in their ownership area unless the group explicitly agrees otherwise.
5. Open one PR per ownership area, or split into two smaller PRs if the changes are too large.
6. Rebase or pull latest `main` before opening the PR.

## PR Checklist

- `./gradlew assembleDebug` passes
- owned screens open on emulator
- no unrelated file edits
- PR description lists changed files, test steps, and screenshots if useful

## Notes

- If two people need the same shared file, assign one owner and have the other coordinate through that owner.
- If you want contributions to be equal in a defensible way, make the work equal in reality: implementation, fixes, testing, and review.
