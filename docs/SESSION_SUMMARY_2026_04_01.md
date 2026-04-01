# Session Summary — April 1, 2026

**Repo:** rfawadt/initial_app  
**Commit:** `8881958`  
**Files changed:** 17  

---

## What Was Done

Fawad flagged 6 user stories marked **Done** in the README that were not actually wired in the UI, plus an organizer workflow gap where two screens (RSVP List, Post Update) were unreachable from the dashboard. All 7 issues were fixed in a single commit.

### P1 — Search Filtering (US-05, US-06)
- **Problem:** Category chip callback was empty; filter sheet Apply/Clear just dismissed without applying anything; no button to open the filter screen.
- **Fix:** Chip clicks now call `SearchViewModel.setCategory()` or `setFreeFilter()`. Added filter icon to search bar that navigates to `FilterBottomSheetFragment`. Apply reads selected chips and online/offline state, writes to `NavBackStackEntry.getSavedStateHandle()`, and `SearchFragment` observes the result.

### P1 — Follow Organiser (US-07)
- **Problem:** `toggleFollow()` existed in VM and repo, strings existed in `strings.xml`, but `EventDetailFragment` never wired a follow button.
- **Fix:** Added `btn_follow` TextView to `fragment_event_detail.xml` next to "Hosted by". Wired to `toggleFollow()` with observer that toggles label and style (Follow → Following).

### P1 — Cancel RSVP (US-15)
- **Problem:** `cancelRsvp()` existed in `RsvpViewModel` but the RSVP screen had no Cancel button.
- **Fix:** Added `btn_cancel_rsvp` to `fragment_rsvp.xml` (initially `GONE`). Shown only when user already has an active RSVP. Calls `viewModel.cancelRsvp()` then navigates back.

### P2 — My Events Upcoming/Past (US-16)
- **Problem:** `FavoritesFragment` had only Saved and My RSVPs tabs. Upcoming/Past strings existed but were unused. All RSVPs loaded together.
- **Fix:** Added Upcoming/Past sub-tab row to layout, visible when My RSVPs tab is active. `FavoritesViewModel` gained `loadUpcomingRsvpEvents()` and `loadPastRsvpEvents()` that parse `event.date` (format: `MMMM d, yyyy`) and compare to today.

### P2 — My Day Date Filter (US-03)
- **Problem:** `DiscoverViewModel.loadSchedule()` loaded all GOING RSVPs regardless of date.
- **Fix:** Added `getTodayDateString()` helper and filter: only events where `event.date == today` are shown. Added `evt_9` (Spring Career Fair, April 1 2026) to `MockData` with a user_1 GOING RSVP so the screen is non-empty.

### P2 — Home Feed (US-01)
- **Problem:** `EventRepository.getAllEvents()` only excluded `CANCELLED`; `ENDED` events still appeared.
- **Fix:** One-line change — also exclude `EventStatus.ENDED`.

### Organiser Workflow (ORG-03, ORG-04)
- **Problem:** `nav_rsvp_list` and `nav_post_update` destinations existed in the nav graph with correct actions declared, but `OrganizerDashboardFragment` never navigated to them. No UI entry points existed.
- **Fix:** Added "View RSVPs" and "Send Update" action buttons to each event card in `item_organizer_event.xml`. Expanded `OrganizerEventAdapter` to a 3-callback constructor. `OrganizerDashboardFragment` now navigates to both screens on button tap.

---

## Known Remaining Gaps

| Item | Status |
|------|--------|
| "Today" and "This Week" chip filters in Search | Not implemented — no date filter in `SearchViewModel` |
| Location filter in FilterBottomSheetFragment | Chips exist in UI but not wired to `SearchViewModel` |
| All MockData events are from 2025 | Except `evt_9` — date-based filters will mostly return empty |
| No backend / persistence | All data resets on app restart |
