# LUMS Events - Color & Content Update Guide

## Color Scheme Changes
Replace ALL instances across ALL .tsx files:

### Old Colors → New Colors
- `#6C3AED` (purple) → `#FF6B35` (coral orange)
- `#5B2FD6` (dark purple) → `#E85A2A` (dark coral)
- `#0D0D0D` (background) → `#0F0F0F` (darker black)
- `#1A1A1A` (cards) → `#1E1E1E` (dark gray)
- `#A0A0A0` (text gray) → `#9CA3AF` (lighter gray)
- `#2A2A2A` (borders) → `#2A2A2A` (keep same)

## Event Names Changed
- "Kaavish Concert" → "Spotify Concert"
- "Spring Music Fest" → "Cricket Fest 2026"
- "Tech Summit 2026" → "TEDx LUMS"
- "Sports Gala" → "Food Carnival"
- "Tech Talk" → "Gaming Tournament"
- "Business Seminar" → "Art Exhibition"
- "Basketball Tournament" → "Startup Pitch Night"
- "Spring Festival" → "Dance Battle"
- "Food Fest" → "Comedy Show"

## Random Names (replace existing)
- Ahmed Khan → Moeed
- Sara Ali → Zainab  
- Hassan Raza → Ibrahim
- Fatima Shah → Ayesha
- Usman Malik → Hamza
- Any other names → Laiba, Bilal, Sana, Ali, Hira

## Files to Update (in order of priority)

### Critical Visual Files
1. `/src/app/components/BottomNav.tsx`
2. `/src/app/screens/HomeScreen.tsx` ✓ DONE
3. `/src/app/screens/EventDetailScreen.tsx`
4. `/src/app/screens/SplashScreen.tsx`
5. `/src/app/screens/WelcomeScreen.tsx`

### Auth Screens
6. `/src/app/screens/SignInScreen.tsx`
7. `/src/app/screens/CreateAccountScreen.tsx`

### Main App Screens
8. `/src/app/screens/SearchScreen.tsx`
9. `/src/app/screens/FavouritesScreen.tsx`
10. `/src/app/screens/ProfileScreen.tsx`
11. `/src/app/screens/RSVPScreen.tsx`

### Organizer Screens
12. `/src/app/screens/OrganizerDashboardScreen.tsx`
13. `/src/app/screens/CreateEventScreen.tsx`
14. `/src/app/screens/EventAnalyticsScreen.tsx`
15. `/src/app/screens/CCAApprovalScreen.tsx`
16. `/src/app/screens/SendUpdateScreen.tsx`

### New Feature Screens
17. `/src/app/screens/MyDayScheduleScreen.tsx`
18. `/src/app/screens/NotificationsScreen.tsx`
19. `/src/app/screens/BuddyMatchingScreen.tsx`
20. `/src/app/screens/EventMapScreen.tsx`
21. `/src/app/screens/EateryPerksScreen.tsx`

## Search & Replace Commands
Use these exact replacements in your code editor:

```
Find: #6C3AED
Replace: #FF6B35

Find: #5B2FD6
Replace: #E85A2A

Find: #0D0D0D
Replace: #0F0F0F

Find: #1A1A1A
Replace: #1E1E1E

Find: #A0A0A0
Replace: #9CA3AF
```

## Implementation Status
- [x] HomeScreen.tsx - Colors and events updated
- [ ] All other screens pending
