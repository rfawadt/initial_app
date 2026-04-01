# Material Design 3 Integration Guide - LUMS Events

## Overview

Your LUMS Events app has been integrated with Material Design 3 (Material UI v7) while maintaining your custom coral orange color scheme and dark theme.

## What's Been Integrated

### 1. Theme Configuration (`/src/app/theme.ts`)

A custom MUI theme with your branding:
- **Primary Color**: #FF6B35 (coral orange)
- **Background**: #0F0F0F (dark) and #1E1E1E (cards)
- **Text**: White primary, #9CA3AF secondary
- **Border Radius**: 12px globally
- **Font**: Inter

### 2. Updated Screens

The following screens have been converted to use Material UI components:

- ✅ **App.tsx** - Added ThemeProvider and CssBaseline
- ✅ **SplashScreen** - Uses Box, Typography
- ✅ **WelcomeScreen** - Uses Button, Stack, Box
- ✅ **SignInScreen** - Uses TextField, Checkbox, Link
- ✅ **CreateAccountScreen** - Uses ToggleButtonGroup, TextField, InputAdornment
- ✅ **HomeScreen** - Uses Card, Chip, Badge, IconButton
- ✅ **BottomNav** - Uses BottomNavigation, BottomNavigationAction

### 3. Remaining Screens to Update

These screens still use Tailwind/custom styling and can be updated to MUI:
- EventDetailScreen
- RSVPScreen
- SearchScreen
- FavouritesScreen
- ProfileScreen
- OrganizerDashboardScreen
- CreateEventScreen
- EventAnalyticsScreen
- CCAApprovalScreen
- SendUpdateScreen
- MyDayScheduleScreen
- NotificationsScreen
- BuddyMatchingScreen
- EventMapScreen
- EateryPerksScreen

## Common Material UI Components Reference

### Layout
```tsx
<Box sx={{ bgcolor: 'background.default', p: 3 }}>
  {/* Box is the most flexible layout component */}
</Box>

<Stack direction="row" spacing={2}>
  {/* Stack for flex layouts with spacing */}
</Stack>
```

### Typography
```tsx
<Typography variant="h1" sx={{ color: 'text.primary' }}>
  Heading
</Typography>

<Typography variant="body1" sx={{ fontSize: '14px' }}>
  Body text
</Typography>
```

### Buttons
```tsx
<Button variant="contained" fullWidth>
  Primary Button
</Button>

<Button variant="outlined">
  Secondary Button
</Button>

<IconButton>
  <Heart size={20} />
</IconButton>
```

### Form Inputs
```tsx
<TextField
  fullWidth
  label="Email"
  type="email"
  variant="outlined"
  InputProps={{
    startAdornment: <InputAdornment position="start"><Mail /></InputAdornment>,
    endAdornment: <InputAdornment position="end"><Eye /></InputAdornment>
  }}
/>

<Checkbox 
  checked={value}
  onChange={(e) => setValue(e.target.checked)}
/>

<Switch
  checked={value}
  onChange={(e) => setValue(e.target.checked)}
/>
```

### Cards
```tsx
<Card sx={{ borderRadius: '16px' }}>
  <CardMedia component="img" image={imageUrl} />
  <CardContent>
    <Typography variant="h5">Title</Typography>
  </CardContent>
</Card>
```

### Chips & Badges
```tsx
<Chip label="Category" size="small" />

<Badge badgeContent={3} color="warning">
  <Bell size={20} />
</Badge>
```

### Navigation
```tsx
<BottomNavigation value={activeTab} onChange={handleChange}>
  <BottomNavigationAction label="Home" icon={<Home />} />
</BottomNavigation>
```

## Theme Colors Reference

Access theme colors using the `sx` prop:

```tsx
sx={{
  bgcolor: 'background.default',    // #0F0F0F
  bgcolor: 'background.paper',      // #1E1E1E
  color: 'primary.main',            // #FF6B35
  color: 'text.primary',            // #FFFFFF
  color: 'text.secondary',          // #9CA3AF
  borderColor: 'divider',           // #2E2E2E
}}
```

## Spacing System

Material UI uses an 8px spacing unit by default:

```tsx
sx={{
  p: 2,      // padding: 16px (2 * 8)
  px: 3,     // padding-left & right: 24px
  py: 1.5,   // padding-top & bottom: 12px
  m: 2,      // margin: 16px
  gap: 1,    // gap: 8px
}}
```

## Converting Tailwind to MUI

### Color Classes
- `bg-[#0F0F0F]` → `sx={{ bgcolor: 'background.default' }}`
- `text-white` → `sx={{ color: 'text.primary' }}`
- `text-[#9CA3AF]` → `sx={{ color: 'text.secondary' }}`
- `bg-[#FF6B35]` → `sx={{ bgcolor: 'primary.main' }}`

### Layout Classes
- `flex items-center justify-between` → `sx={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}`
- `w-full` → `fullWidth` prop or `sx={{ width: '100%' }}`
- `rounded-xl` → `sx={{ borderRadius: '12px' }}`
- `px-5 py-3` → `sx={{ px: 2.5, py: 1.5 }}`

### Interactive States
```tsx
sx={{
  '&:hover': {
    bgcolor: 'primary.light',
  },
  '&.Mui-selected': {
    color: 'primary.main',
  }
}}
```

## Best Practices

1. **Use `sx` prop for styling** - More powerful than className, with theme access
2. **Leverage theme values** - Use 'primary.main', 'text.secondary' instead of hardcoded colors
3. **Use Material UI components first** - They come with accessibility built-in
4. **Keep Lucide icons** - They work great with MUI (just wrap in IconButton when needed)
5. **Combine with Tailwind** - You can still use Tailwind utility classes when needed

## Example: Converting a Screen

### Before (Tailwind):
```tsx
<div className="bg-[#0D1117] p-5">
  <h1 className="text-white text-[24px] font-extrabold">Title</h1>
  <button className="bg-[#FF6B35] text-white py-4 rounded-xl">
    Click me
  </button>
</div>
```

### After (Material UI):
```tsx
<Box sx={{ bgcolor: 'background.default', p: 2.5 }}>
  <Typography variant="h2" sx={{ fontWeight: 800, fontSize: '24px' }}>
    Title
  </Typography>
  <Button variant="contained" fullWidth sx={{ py: 1.5 }}>
    Click me
  </Button>
</Box>
```

## Next Steps

1. Review the updated screens to understand the conversion pattern
2. Update remaining screens using the patterns shown
3. Test all interactive elements (buttons, forms, navigation)
4. Ensure consistent spacing and colors across all screens

## Resources

- [MUI Documentation](https://mui.com/material-ui/)
- [MUI Theming Guide](https://mui.com/material-ui/customization/theming/)
- [MUI System (sx prop)](https://mui.com/system/getting-started/the-sx-prop/)
