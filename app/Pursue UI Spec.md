# Pursue UI Specification

**Version:** 0.1 (Draft)  
**Last Updated:** January 15, 2026  
**Status:** Design Phase  
**Platform:** Android (Material Design 3)

---

## 1. Overview

### 1.1 Purpose
This document defines the user interface, user experience, and visual design for the Pursue mobile application. It covers screen layouts, navigation patterns, interaction flows, and design principles.

### 1.2 Design Philosophy
- **Clear and Focused**: Minimize distractions, highlight what matters (today's goals and progress)
- **Efficient**: Fast app startup, minimal animations, direct access to core functions
- **Encouraging**: Use positive language, celebrate achievements, gentle nudges
- **Transparent**: Show sync status, make P2P nature visible but not intrusive
- **Accessible**: Large touch targets, high contrast, readable fonts, colorblind-friendly palette
- **Privacy-Aware**: Make security features (seed phrase, encryption) understandable
- **Professional**: Blue and gold palette conveys trust, achievement, and reliability

### 1.3 Target Users
- **Primary**: Adults 25-45 seeking accountability for personal goals
- **Secondary**: Students, fitness enthusiasts, professionals with work goals
- **Technical Level**: Range from basic smartphone users to tech-savvy power users

---

## 2. Design System

### 2.1 Color Palette

**Primary Colors:**
```
Primary (Brand):     #1976D2 (Blue 700) - Trust, progress, clarity
Primary Variant:     #1565C0 (Blue 800) - Darker accent
On Primary:          #FFFFFF (White) - Text on primary

Secondary:           #F9A825 (Yellow 800) - Achievement, energy, warmth
Secondary Variant:   #F57F17 (Yellow 900) - Darker accent
On Secondary:        #000000 (Black) - Text on secondary
```

**Surface Colors:**
```
Background:          #FAFAFA (Light grey) - Main app background
Surface:             #FFFFFF (White) - Cards, dialogs
Surface Variant:     #F5F5F5 (Lighter grey) - Secondary surfaces
On Surface:          #212121 (Almost black) - Primary text
On Surface Variant:  #757575 (Grey 600) - Secondary text
```

**Semantic Colors:**
```
Success:             #1976D2 (Blue 700) - Completed goals
Warning:             #F9A825 (Yellow 800) - Pending sync, attention needed
Error:               #D32F2F (Red 700) - Failed sync, errors
Info:                #0288D1 (Light Blue 700) - Information, tips

Goal Completed:      #1976D2 (Blue)
Goal Incomplete:     #E0E0E0 (Grey 300)
Goal Overdue:        #FFE082 (Yellow 200)
```

**Group Member Colors (Colorblind-Friendly Palette):**
- Assign each group member a distinct color optimized for colorblind users
- Use palette designed for deuteranopia/protanopia visibility:
  - Blue: #1976D2
  - Gold: #F9A825
  - Teal: #00897B
  - Purple: #7B1FA2
  - Orange: #F57C00
  - Pink: #C2185B
  - Brown: #5D4037
  - Grey: #616161
- Ensure sufficient contrast for accessibility (verified with colorblind simulators)

### 2.2 Typography

**Font Family:** Roboto (Android system default)

**Text Styles:**
```
Headline Large:      32sp, Medium (500), -0.25sp letter spacing
Headline Medium:     28sp, Medium (500), 0sp letter spacing
Headline Small:      24sp, Medium (500), 0sp letter spacing

Title Large:         22sp, Medium (500), 0sp letter spacing
Title Medium:        16sp, Medium (500), 0.15sp letter spacing
Title Small:         14sp, Medium (500), 0.1sp letter spacing

Body Large:          16sp, Regular (400), 0.5sp letter spacing
Body Medium:         14sp, Regular (400), 0.25sp letter spacing
Body Small:          12sp, Regular (400), 0.4sp letter spacing

Label Large:         14sp, Medium (500), 0.1sp letter spacing
Label Medium:        12sp, Medium (500), 0.5sp letter spacing
Label Small:         11sp, Medium (500), 0.5sp letter spacing
```

**Hierarchy:**
- Page titles: Headline Medium
- Section headers: Title Large
- Card titles: Title Medium
- Body text: Body Large
- Secondary info: Body Medium
- Captions: Body Small

### 2.3 Spacing & Layout

**Spacing Scale (8dp grid):**
```
XXS: 4dp   - Tight spacing within components
XS:  8dp   - Small gaps
S:   12dp  - Compact spacing
M:   16dp  - Standard spacing (most common)
L:   24dp  - Large spacing
XL:  32dp  - Section separation
XXL: 48dp  - Major section breaks
```

**Margins:**
- Screen edges: 16dp horizontal
- Card padding: 16dp all sides
- List item padding: 16dp vertical, 16dp horizontal

**Touch Targets:**
- Minimum: 48dp × 48dp (Material Design guideline)
- Icon buttons: 48dp × 48dp
- Text buttons: 48dp height, flexible width
- FAB (Floating Action Button): 56dp × 56dp

### 2.4 Elevation & Shadows

**Material Design 3 Elevation:**
```
Level 0: 0dp   - Background, flat surfaces
Level 1: 1dp   - Cards at rest
Level 2: 3dp   - Cards on hover/focus
Level 3: 6dp   - Modals, dialogs
Level 4: 8dp   - Navigation drawer
Level 5: 12dp  - FAB, snackbars
```

### 2.5 Iconography

**Icon Style:** Material Design Icons (outlined style for consistency)

**Key Icons:**
```
Home/Groups:         group, groups
Goals:               flag, emoji_events
Progress Entry:      add_circle, check_circle
Calendar:            calendar_today, event
Profile:             person, account_circle
Settings:            settings, tune
Sync:                sync, cloud_done, cloud_off
Notifications:       notifications, notifications_active
Admin:               admin_panel_settings, shield
Invite:              person_add, qr_code
Menu:                more_vert, more_horiz
```

**Icon Sizes:**
- Small: 16dp (inline with text)
- Medium: 24dp (standard buttons, list items)
- Large: 32dp (prominent actions)
- Hero: 48dp+ (empty states, illustrations)

### 2.6 Components

**Buttons:**
- **Primary (Filled)**: Blue background, white text - Main CTAs
- **Secondary (Outlined)**: Blue border, blue text - Secondary actions
- **Tertiary (Text)**: Blue text only - Least prominent actions
- Height: 40dp minimum
- Corner radius: 20dp (fully rounded)

**Cards:**
- White background, 1dp elevation
- Corner radius: 12dp
- Padding: 16dp
- Ripple effect on tap

**Text Fields:**
- Outlined style (Material 3)
- Corner radius: 4dp
- Label: Body Medium
- Helper text: Body Small
- Error state: Red outline + error text below

**Chips:**
- Small: 24dp height (tags, filters)
- Medium: 32dp height (selections)
- Rounded corners: Full (50% of height)

**Progress Indicators:**
- Linear: 4dp height, blue fill
- Circular: 48dp diameter for prominent, 24dp for inline
- Determinate for known progress, indeterminate for loading

---

## 3. Navigation Structure

### 3.1 Information Architecture

```
Pursue App
│
├── Home / Groups List ────────────────┐
│   ├── Group Detail (Select a group)  │
│   │   ├── Goals Tab                  │
│   │   ├── Members Tab                │
│   │   └── Activity Tab               │
│   └── [FAB: Create New Group]        │
│                                       │
├── Today (Quick access to daily goals)│
│   └── [FAB: Log Progress]            │
│                                       │
├── Profile                             │
│   ├── Display Name & Avatar           │
│   ├── My Progress Summary             │
│   └── Account Settings                │
│       ├── Backup Seed Phrase          │
│       ├── Manage Backups              │
│       ├── Connected Devices           │
│       └── Privacy & Security          │
│                                       │
└── [First-time Setup]                 │
    ├── Welcome / Onboarding            │
    ├── Create Identity                 │
    ├── Seed Phrase Backup              │
    └── Create or Join Group            │
```

### 3.2 Bottom Navigation Bar

**Tabs (3-4 tabs for optimal UX):**
```
┌────────────────────────────────────────────┐
│  [Home]    [Today]    [Profile]            │
│   🏠        📅         👤                   │
└────────────────────────────────────────────┘
```

**Home Tab:**
- Icon: `groups` (overlapping people)
- Label: "Groups"
- Badge: Show count of unread updates

**Today Tab:**
- Icon: `calendar_today`
- Label: "Today"
- Badge: Show count of incomplete daily goals

**Profile Tab:**
- Icon: `person`
- Label: "Profile"
- No badge

**Navigation Behavior:**
- Selected tab: Primary color (blue)
- Unselected tabs: On Surface Variant (grey)
- Tap animation: Brief scale + ripple
- Maintains state when switching tabs

### 3.3 Top App Bar

**Persistent Elements:**
- Left: Back arrow (when applicable) or hamburger menu
- Center: Screen title or group name
- Right: Sync status indicator, overflow menu (3 dots)

**Sync Status Indicator:**
```
✓ Synced        - Blue checkmark
⟳ Syncing...    - Rotating circular arrow (grey)
⚠ Pending       - Gold/yellow warning icon
✗ Failed        - Red X icon
○ Offline       - Grey circle outline
```

Tap sync indicator to see sync details (last synced, pending items, errors).

**Overflow Menu (Context-Specific):**
- Group Detail: Edit group, Invite members, Leave group
- Profile: Settings, Help, About
- Goals: Filter, Sort, Archive

---

## 4. Screen Specifications

### 4.1 First-Time User Experience

#### 4.1.1 Welcome / Splash Screen

**Display Logic:**
- **New users only**: Show splash screen if no identity exists locally
- **Returning users**: Skip directly to Home screen (Groups List)
- **No animations**: Static display, no fade-in or transitions
- **Productivity-focused**: Minimize time to app functionality

**Layout:**
```
┌─────────────────────────────────────┐
│                                     │
│          [Pursue Logo]              │
│       Large blue/gold icon/text     │
│                                     │
│   "Achieve goals together"          │
│   Subtitle: Body Large (grey)       │
│                                     │
│                                     │
│   [Get Started] ──── Primary btn   │
│                      (blue)         │
│                                     │
│   Already have an account?          │
│   [Restore Account] ─ Text button   │
│                      (blue text)    │
│                                     │
└─────────────────────────────────────┘
```

**Behavior:**
- Appears instantly (no fade-in)
- Only shown to first-time users
- Tapping "Get Started" proceeds to onboarding
- Tapping "Restore Account" proceeds to restore flow
- Once identity is created, this screen never shows again

#### 4.1.2 Onboarding Carousel (Optional)

3 screens explaining key concepts:

**Screen 1: "Track Goals Together"**
- Illustration: People sharing progress
- Text: "Create groups with friends to stay accountable on daily, weekly, monthly, or yearly goals."

**Screen 2: "Your Data, Your Control"**
- Illustration: Lock/shield with peer-to-peer arrows
- Text: "Pursue is peer-to-peer. Your progress stays on your device and syncs directly with your group."

**Screen 3: "Never Lose Your Account"**
- Illustration: Key/seed phrase
- Text: "You'll get a recovery phrase. Write it down somewhere safe—it's the only way to restore your account."

Navigation: Swipe between screens, "Skip" button, "Next" / "Get Started" on final screen.

#### 4.1.3 Create Identity Screen

**Layout:**
```
┌─────────────────────────────────────┐
│ ← Back                              │
│                                     │
│ Create Your Identity                │
│ ─────────────────────                │
│                                     │
│ Display Name *                      │
│ ┌─────────────────────────────────┐│
│ │ [Enter your name]               ││
│ └─────────────────────────────────┘│
│ How others will see you             │
│                                     │
│ Profile Picture (optional)          │
│ ┌───────┐                           │
│ │  📷   │ [Choose Photo]            │
│ └───────┘                           │
│                                     │
│                                     │
│                                     │
│ [Continue] ──────── Primary button  │
│                                     │
└─────────────────────────────────────┘
```

**Validation:**
- Display name: Required, 1-30 characters
- Profile picture: Optional, max 5 MB
- Show character count: "28/30"

**On Continue:**
- Generate Ed25519 keypair in background
- Show loading spinner: "Creating your secure identity..."
- Proceed to Seed Phrase screen

#### 4.1.4 Seed Phrase Backup Screen

**Layout:**
```
┌─────────────────────────────────────┐
│ ← Back                              │
│                                     │
│ 🔑 Your Recovery Phrase              │
│ ─────────────────────────────────   │
│                                     │
│ ⚠️ IMPORTANT: Write this down!      │
│                                     │
│ This 12-word phrase is the ONLY way │
│ to recover your account if you lose │
│ your phone. Never share it.         │
│                                     │
│ ┌─────────────────────────────────┐│
│ │  1. example    7. another       ││
│ │  2. words      8. set           ││
│ │  3. here       9. of            ││
│ │  4. from      10. recovery      ││
│ │  5. the       11. phrase        ││
│ │  6. seed      12. words         ││
│ └─────────────────────────────────┘│
│                                     │
│ [📋 Copy to Clipboard]              │
│                                     │
│ [I've Written It Down]              │
│                                     │
└─────────────────────────────────────┘
```

**Behavior:**
- Words displayed in 2-column grid with numbering
- Copy button shows tooltip: "Copied!" for 2 seconds
- "I've Written It Down" is a checkbox, must check before Continue appears
- Emphasize security with warning color (yellow/gold background on warning box)

**On Continue:**
- Proceed to Verification Screen

#### 4.1.5 Seed Phrase Verification Screen

**Layout:**
```
┌─────────────────────────────────────┐
│ ← Back                              │
│                                     │
│ Verify Your Recovery Phrase         │
│ ─────────────────────────────────   │
│                                     │
│ To make sure you wrote it down      │
│ correctly, please enter:            │
│                                     │
│ Word #3:                            │
│ ┌─────────────────────────────────┐│
│ │ [Enter word]                    ││
│ └─────────────────────────────────┘│
│                                     │
│ Word #7:                            │
│ ┌─────────────────────────────────┐│
│ │ [Enter word]                    ││
│ └─────────────────────────────────┘│
│                                     │
│ Word #11:                           │
│ ┌─────────────────────────────────┐│
│ │ [Enter word]                    ││
│ └─────────────────────────────────┘│
│                                     │
│ [Verify & Continue]                 │
│                                     │
└─────────────────────────────────────┘
```

**Validation:**
- Check 3 random words from the seed phrase
- If incorrect: Show error message, allow retry
- Max 3 attempts before forcing user back to view seed phrase again

**On Success:**
- Proceed to Backup Options screen

#### 4.1.6 Optional Backup Screen

**Layout:**
```
┌─────────────────────────────────────┐
│ ← Back                              │
│                                     │
│ 💾 Backup Your Recovery Phrase      │
│ ─────────────────────────────────   │
│                                     │
│ For extra security, you can create  │
│ an encrypted backup:                │
│                                     │
│ ┌─────────────────────────────────┐│
│ │ 📱 Google Drive Backup          ││
│ │ Encrypted and saved to your     ││
│ │ Google Drive                    ││
│ │                     [Setup →]   ││
│ └─────────────────────────────────┘│
│                                     │
│ ┌─────────────────────────────────┐│
│ │ 📄 Local File Backup            ││
│ │ Save encrypted file to device   ││
│ │                     [Setup →]   ││
│ └─────────────────────────────────┘│
│                                     │
│                                     │
│ [Skip for Now]   [Continue]         │
│ Text button      Primary button     │
└─────────────────────────────────────┘
```

**Behavior:**
- Cards are tappable to expand and configure
- Skip button: Proceed without backup (show warning dialog)
- Continue after setup: Saves backup, proceeds to Groups screen

#### 4.1.7 Google Drive Backup Flow

**Sub-screen 1: Google Sign-In**
- Use Google Sign-In SDK
- Request only Drive API scope
- Show permissions clearly

**Sub-screen 2: Create Backup Password**
```
┌─────────────────────────────────────┐
│ Create Backup Password              │
│                                     │
│ This password encrypts your backup. │
│ You'll need it to restore.          │
│                                     │
│ Password:                           │
│ ┌─────────────────────────────────┐│
│ │ [••••••••]              👁       ││
│ └─────────────────────────────────┘│
│ Strength: ████░░ Medium             │
│                                     │
│ Confirm Password:                   │
│ ┌─────────────────────────────────┐│
│ │ [••••••••]              👁       ││
│ └─────────────────────────────────┘│
│                                     │
│ ⚠️ If you forget this password,    │
│    your backup is useless!          │
│                                     │
│ [Create Backup]                     │
│                                     │
└─────────────────────────────────────┘
```

**Password Requirements:**
- Minimum 8 characters
- Strength indicator: Weak, Medium, Strong
- Eye icon toggles password visibility

**On Create:**
- Show progress: "Encrypting and uploading..."
- Success: "✓ Backup saved to Google Drive"
- Return to main flow

#### 4.1.8 Local File Backup Flow

**Layout:**
```
┌─────────────────────────────────────┐
│ Create Local Backup                 │
│                                     │
│ Create a password-protected file:   │
│                                     │
│ Password:                           │
│ ┌─────────────────────────────────┐│
│ │ [••••••••]              👁       ││
│ └─────────────────────────────────┘│
│                                     │
│ Confirm Password:                   │
│ ┌─────────────────────────────────┐│
│ │ [••••••••]              👁       ││
│ └─────────────────────────────────┘│
│                                     │
│ Save Location:                      │
│ Documents/Pursue/                   │
│                                     │
│ [Create Backup File]                │
│                                     │
└─────────────────────────────────────┘
```

**On Create:**
- Save to: `/Documents/Pursue/pursue_backup_<timestamp>.json`
- Show success dialog with file path
- Offer to share file (email, cloud storage, etc.)

---

### 4.2 Home Screen (Groups List)

**Layout:**
```
┌─────────────────────────────────────┐
│ ☰  Groups                    ⟳ ⋮   │ ← Top bar
├─────────────────────────────────────┤
│                                     │
│ My Groups                           │
│                                     │
│ ┌─────────────────────────────────┐│
│ │ 🏃 Morning Runners      →       ││ ← Group card
│ │ 8 members · 5 active goals      ││
│ │ ──────────────────── 80%        ││ ← Today's progress
│ │ Last activity: 2 hours ago      ││
│ └─────────────────────────────────┘│
│                                     │
│ ┌─────────────────────────────────┐│
│ │ 📚 Book Club            →       ││
│ │ 12 members · 3 active goals     ││
│ │ ──────────────────── 60%        ││
│ │ Last activity: 1 day ago        ││
│ └─────────────────────────────────┘│
│                                     │
│ ┌─────────────────────────────────┐│
│ │ 💼 Work Goals           →       ││
│ │ 4 members · 8 active goals      ││
│ │ ──────────────────── 45%        ││
│ │ Last activity: 3 hours ago      ││
│ └─────────────────────────────────┘│
│                                     │
└─────────────────────────────────────┘
                                   [+] ← FAB (Create Group)
```

**Group Card Components:**
- **Group Icon/Emoji**: User-selected or auto-generated from name
- **Group Name**: Title Medium, bold
- **Member & Goal Count**: Body Small, grey
- **Progress Bar**: Aggregate completion % for today's goals across all members
- **Last Activity**: Relative timestamp (e.g., "2 hours ago")
- **Tap Anywhere**: Navigate to Group Detail

**Empty State (No Groups Yet):**
```
┌─────────────────────────────────────┐
│                                     │
│          [Illustration]             │
│        People achieving goals       │
│                                     │
│   You're not in any groups yet      │
│                                     │
│   Create a group to start tracking  │
│   goals with friends, or join an    │
│   existing group.                   │
│                                     │
│   [Create Group]   [Join Group]     │
│   Primary btn      Secondary btn    │
│                                     │
└─────────────────────────────────────┘
```

**FAB Actions:**
- Primary: [+] Create New Group
- On tap: Navigate to Create Group flow

**Sync Status (Top Right):**
- Small icon indicating current sync state
- Tap to open Sync Details bottom sheet

**Overflow Menu (⋮):**
- Refresh sync
- Settings
- Help & Feedback
- About Pursue

---

### 4.3 Group Detail Screen

**Tabbed Layout:**
```
┌─────────────────────────────────────┐
│ ← Morning Runners            ⟳ ⋮   │
├─────────────────────────────────────┤
│ [Goals]  [Members]  [Activity]      │ ← Tabs
├─────────────────────────────────────┤
│                                     │
│ [Tab Content Below]                 │
│                                     │
└─────────────────────────────────────┘
```

#### 4.3.1 Goals Tab

**Layout:**
```
┌─────────────────────────────────────┐
│                                     │
│ Daily Goals                         │
│ ┌─────────────────────────────────┐│
│ │ ✓ 30 min run                    ││
│ │ Shannon ● Alex ○ Jamie ✓        ││ ← Member status dots
│ └─────────────────────────────────┘│
│ ┌─────────────────────────────────┐│
│ │ ○ Meditate 10 min               ││
│ │ Shannon ○ Alex ○ Jamie ○        ││
│ └─────────────────────────────────┘│
│                                     │
│ Weekly Goals                        │
│ ┌─────────────────────────────────┐│
│ │ ✓ Read 2 books                  ││
│ │ ████████░░░░░░░░ 50% (1/2)      ││ ← Progress bar
│ │ Shannon ✓ Alex ○ Jamie ✓        ││
│ └─────────────────────────────────┘│
│                                     │
│ Monthly Goals                       │
│ ┌─────────────────────────────────┐│
│ │ ○ Run 100km                     ││
│ │ ████████████░░░░ 75% (75/100)   ││
│ │ Shannon 45km · Alex 30km        ││ ← Individual progress
│ └─────────────────────────────────┘│
│                                     │
└─────────────────────────────────────┘
                                   [+] ← FAB (Log Progress)
```

**Goal Card Design:**
- **Status Icon**: ✓ (completed) or ○ (incomplete)
- **Goal Title**: Title Medium
- **Member Status Dots**:
  - ✓ Blue checkmark (completed)
  - ○ Grey circle (incomplete)
  - ● Colored dot (in progress, with value)
- **Progress Bar**: For numeric/cumulative goals
- **Tap Card**: Open Goal Detail view

**Empty State (No Goals):**
```
┌─────────────────────────────────────┐
│                                     │
│          📋                          │
│                                     │
│   No active goals yet               │
│                                     │
│   Group admins can add goals for    │
│   everyone to track.                │
│                                     │
│   [Add Goal] ← (if user is admin)   │
│                                     │
└─────────────────────────────────────┘
```

**FAB Actions:**
- [+] Log Progress (opens bottom sheet to select goal and enter value)

**Overflow Menu (⋮):**
- Filter goals (All / Mine / By cadence)
- Sort goals (By name / By completion / By cadence)
- Add goal (admins only)
- Archive completed goals (admins only)

#### 4.3.2 Members Tab

**Layout:**
```
┌─────────────────────────────────────┐
│                                     │
│ Admins                              │
│ ┌─────────────────────────────────┐│
│ │ 👤 Shannon (You)          🛡     ││ ← Admin badge
│ │ Last active: Now                ││
│ └─────────────────────────────────┘│
│                                     │
│ Members (6)                         │
│ ┌─────────────────────────────────┐│
│ │ 👤 Alex Thompson                ││
│ │ Last active: 2 hours ago        ││
│ │ ○ Online                        ││ ← Sync status
│ └─────────────────────────────────┘│
│ ┌─────────────────────────────────┐│
│ │ 👤 Jamie Lee                    ││
│ │ Last active: 1 day ago          ││
│ │ ○ Offline                       ││
│ └─────────────────────────────────┘│
│ ┌─────────────────────────────────┐│
│ │ 👤 Morgan Kim                   ││
│ │ Last active: 5 minutes ago      ││
│ │ ⟳ Syncing...                    ││
│ └─────────────────────────────────┘│
│                                     │
└─────────────────────────────────────┘
                                   [+] ← FAB (Invite Member)
```

**Member Card Components:**
- **Avatar**: Profile picture or colored circle with initials
- **Display Name**: Title Medium
- **Admin Badge**: Shield icon (🛡) if admin
- **Last Active**: Relative timestamp
- **Sync Status**:
  - ○ Online (blue)
  - ○ Offline (grey)
  - ⟳ Syncing (animated, grey)
- **Tap Card**: Open Member Profile view

**FAB Actions:**
- [+] Invite Member (admins only)
  - Shows dialog: "Invite via QR Code" or "Invite via Text Code"

**Overflow Menu (⋮):**
- Refresh member status
- Manage admins (creator only)
- Leave group

#### 4.3.3 Activity Tab (Feed)

**Layout:**
```
┌─────────────────────────────────────┐
│                                     │
│ Today                               │
│ ┌─────────────────────────────────┐│
│ │ ✓ Alex completed "30 min run"   ││
│ │ 2 hours ago                     ││
│ └─────────────────────────────────┘│
│ ┌─────────────────────────────────┐│
│ │ 👥 Jamie joined the group       ││
│ │ 3 hours ago                     ││
│ └─────────────────────────────────┘│
│                                     │
│ Yesterday                           │
│ ┌─────────────────────────────────┐│
│ │ ✓ You completed "Meditate"      ││
│ │ Yesterday at 7:30 AM            ││
│ └─────────────────────────────────┘│
│ ┌─────────────────────────────────┐│
│ │ 🏷️ Group renamed from           ││
│ │   "Runners" to "Morning Runners"││
│ │ Yesterday at 10:15 AM           ││
│ │ by Shannon (Admin)              ││
│ └─────────────────────────────────┘│
│                                     │
│ This Week                           │
│ ┌─────────────────────────────────┐│
│ │ ✓ Morgan completed "Read 1 book"││
│ │ 3 days ago                      ││
│ └─────────────────────────────────┘│
│                                     │
└─────────────────────────────────────┘
```

**Activity Card Types:**
- **Progress Update**: ✓ icon, "[Name] completed [Goal]"
- **Member Added**: 👥 icon, "[Name] joined the group"
- **Member Removed**: 👥 icon, "[Name] left the group"
- **Admin Change**: 🛡 icon, "[Name] promoted to admin"
- **Group Renamed**: 🏷️ icon, "Group renamed from [old] to [new] by [Admin]"
- **Goal Added**: 📋 icon, "New goal: [Goal Name]"
- **Goal Archived**: 📁 icon, "Goal archived: [Goal Name]"

**Grouping:**
- Group by time: Today, Yesterday, This Week, Earlier
- Show most recent first
- Infinite scroll (load more on scroll down)

**Empty State:**
```
┌─────────────────────────────────────┐
│                                     │
│          📊                          │
│                                     │
│   No activity yet                   │
│                                     │
│   As members log progress and make  │
│   changes, you'll see updates here. │
│                                     │
└─────────────────────────────────────┘
```

---

### 4.4 Today Screen (Quick Daily View)

**Purpose:** Fast access to today's goals across all groups.

**Layout:**
```
┌─────────────────────────────────────┐
│ ☰  Today                     ⟳ ⋮   │
├─────────────────────────────────────┤
│                                     │
│ Wednesday, January 15               │
│ ──────────────── 40% complete       │
│                                     │
│ Morning Runners (2/5)               │
│ ┌─────────────────────────────────┐│
│ │ ✓ 30 min run                    ││
│ │ ○ Meditate 10 min               ││
│ └─────────────────────────────────┘│
│                                     │
│ Book Club (0/1)                     │
│ ┌─────────────────────────────────┐│
│ │ ○ Read 30 pages                 ││
│ └─────────────────────────────────┘│
│                                     │
│ Work Goals (0/2)                    │
│ ┌─────────────────────────────────┐│
│ │ ○ Update project plan           ││
│ │ ○ Review pull requests          ││
│ └─────────────────────────────────┘│
│                                     │
└─────────────────────────────────────┘
                                   [+] ← FAB (Log Progress)
```

**Behavior:**
- Shows only daily goals from all groups
- Aggregates progress percentage across all goals
- Groups goals by group membership
- Tap goal to log progress
- Swipe left/right on date to see other days (past/future)

**Empty State (No Daily Goals):**
```
┌─────────────────────────────────────┐
│                                     │
│          🎯                          │
│                                     │
│   No daily goals today              │
│                                     │
│   You don't have any daily goals    │
│   in your groups yet.               │
│                                     │
│   [View My Groups]                  │
│                                     │
└─────────────────────────────────────┘
```

**FAB Actions:**
- [+] Log Progress (bottom sheet to select goal and enter value)

---

### 4.5 Profile Screen

**Layout:**
```
┌─────────────────────────────────────┐
│ ☰  Profile                   ⋮      │
├─────────────────────────────────────┤
│                                     │
│        ┌─────────┐                  │
│        │   👤    │                  │ ← Avatar (large)
│        │ Shannon │                  │
│        └─────────┘                  │
│                                     │
│     Shannon Thompson                │ ← Display Name
│     ID: 5K2PwDd8FvGH7t              │ ← User ID (truncated)
│                                     │
│ ┌─────────────────────────────────┐│
│ │ 📊 My Progress                  ││
│ │ ────────────────────────────────││
│ │ Total goals: 15                 ││
│ │ Completed this week: 12         ││
│ │ Current streak: 7 days 🔥       ││
│ │                     [View All →]││
│ └─────────────────────────────────┘│
│                                     │
│ ┌─────────────────────────────────┐│
│ │ ⚙️ Account Settings             ││
│ │                     [Open →]    ││
│ └─────────────────────────────────┘│
│                                     │
│ ┌─────────────────────────────────┐│
│ │ 🔐 Privacy & Security           ││
│ │                     [Open →]    ││
│ └─────────────────────────────────┘│
│                                     │
│ ┌─────────────────────────────────┐│
│ │ ℹ️ About Pursue                 ││
│ │                     [Open →]    ││
│ └─────────────────────────────────┘│
│                                     │
└─────────────────────────────────────┘
```

**Overflow Menu (⋮):**
- Edit profile
- Help & Support
- Send Feedback

---

### 4.6 Account Settings Screen

**Layout:**
```
┌─────────────────────────────────────┐
│ ← Account Settings                  │
├─────────────────────────────────────┤
│                                     │
│ Identity                            │
│ ┌─────────────────────────────────┐│
│ │ Display Name                    ││
│ │ Shannon Thompson    [Edit]      ││
│ └─────────────────────────────────┘│
│ ┌─────────────────────────────────┐│
│ │ Profile Picture                 ││
│ │ [Avatar]           [Change]     ││
│ └─────────────────────────────────┘│
│ ┌─────────────────────────────────┐│
│ │ User ID (Public Key Hash)       ││
│ │ 5K2PwDd8FvGH7t     [Copy]       ││
│ └─────────────────────────────────┘│
│                                     │
│ Recovery & Backup                   │
│ ┌─────────────────────────────────┐│
│ │ 🔑 View Seed Phrase             ││
│ │ Last viewed: Never    [Show →]  ││
│ └─────────────────────────────────┘│
│ ┌─────────────────────────────────┐│
│ │ 💾 Manage Backups               ││
│ │ Last backup: 2 days ago         ││
│ │                       [Manage →]││
│ └─────────────────────────────────┘│
│                                     │
│ Devices                             │
│ ┌─────────────────────────────────┐│
│ │ Connected Devices               ││
│ │ This device · Pixel 8           ││
│ │                       [View →]  ││
│ └─────────────────────────────────┘│
│                                     │
└─────────────────────────────────────┘
```

**View Seed Phrase:**
- Requires authentication (biometric or PIN)
- Shows warning dialog before revealing
- Displays seed phrase in full screen with copy button

**Manage Backups:**
- List existing backups (Google Drive, local files)
- Create new backup
- Delete old backups
- Test restore (verify backup password)

**Connected Devices:**
- Shows this device only (single-device identity for MVP)
- Future: List of devices using same identity (if multi-device implemented)

---

### 4.7 Privacy & Security Screen

**Layout:**
```
┌─────────────────────────────────────┐
│ ← Privacy & Security                │
├─────────────────────────────────────┤
│                                     │
│ Sync & Notifications                │
│ ┌─────────────────────────────────┐│
│ │ Enable Relay Server             ││
│ │ Use server for push             ││
│ │ notifications when offline      ││
│ │                          [ON]   ││ ← Toggle switch
│ └─────────────────────────────────┘│
│ ┌─────────────────────────────────┐│
│ │ Notification Preferences        ││
│ │                       [Setup →] ││
│ └─────────────────────────────────┘│
│                                     │
│ Data & Storage                      │
│ ┌─────────────────────────────────┐│
│ │ Local Storage Used              ││
│ │ 25 MB / 1 GB available          ││
│ │                       [Clear →] ││
│ └─────────────────────────────────┘│
│ ┌─────────────────────────────────┐│
│ │ Export All Data                 ││
│ │ Download JSON export            ││
│ │                      [Export →] ││
│ └─────────────────────────────────┘│
│                                     │
│ Security                            │
│ ┌─────────────────────────────────┐│
│ │ App Lock                        ││
│ │ Require biometric/PIN           ││
│ │                         [OFF]   ││
│ └─────────────────────────────────┘│
│                                     │
│ Advanced                            │
│ ┌─────────────────────────────────┐│
│ │ View Sync Logs                  ││
│ │                        [View →] ││
│ └─────────────────────────────────┘│
│                                     │
└─────────────────────────────────────┘
```

**Relay Server Toggle:**
- When OFF: No push notifications, manual sync only
- Shows warning dialog explaining trade-offs

**Notification Preferences:**
- Toggle notifications per event type (progress updates, membership changes, etc.)
- Quiet hours

**Clear Local Storage:**
- Warning dialog: "This will delete all local data. You'll need to re-sync from peers."
- Requires confirmation

---

### 4.8 Create Group Flow

#### 4.8.1 Create Group Screen

**Layout:**
```
┌─────────────────────────────────────┐
│ ✕  Create New Group                 │
├─────────────────────────────────────┤
│                                     │
│ Group Icon (optional)               │
│ ┌───────┐                           │
│ │  🏃   │ [Choose Emoji]            │
│ └───────┘                           │
│                                     │
│ Group Name *                        │
│ ┌─────────────────────────────────┐│
│ │ Morning Runners                 ││
│ └─────────────────────────────────┘│
│                                     │
│ Description (optional)              │
│ ┌─────────────────────────────────┐│
│ │ Daily accountability for        ││
│ │ morning exercise routines       ││
│ └─────────────────────────────────┘│
│                                     │
│ Initial Goals (optional)            │
│ ┌─────────────────────────────────┐│
│ │ + Add a goal                    ││
│ └─────────────────────────────────┘│
│                                     │
│                                     │
│ [Cancel]              [Create]      │
│ Text btn              Primary btn   │
│                                     │
└─────────────────────────────────────┘
```

**Validation:**
- Group name: Required, 1-50 characters
- Emoji picker: Grid of common emojis or search
- Add goal: Opens inline form (goal name, cadence, type)

**On Create:**
- Generate group UUID
- User becomes creator and first admin
- Navigate to Group Detail screen
- Show success message: "Group created! Invite members to get started."

#### 4.8.2 Add Goal (Inline Form)

**Layout:**
```
┌─────────────────────────────────────┐
│ Add Goal                            │
│                                     │
│ Goal Name *                         │
│ ┌─────────────────────────────────┐│
│ │ 30 minute run                   ││
│ └─────────────────────────────────┘│
│                                     │
│ Cadence *                           │
│ ┌─────────────────────────────────┐│
│ │ [Daily ▼]                       ││ ← Dropdown
│ └─────────────────────────────────┘│
│ Options: Daily, Weekly, Monthly, Yearly
│                                     │
│ Type *                              │
│ ┌─────────────────────────────────┐│
│ │ [Binary (Yes/No) ▼]             ││
│ └─────────────────────────────────┘│
│ Options: Binary, Numeric, Duration
│                                     │
│ Target (for Numeric/Duration)       │
│ ┌─────────────────────────────────┐│
│ │ [10000]   steps                 ││
│ └─────────────────────────────────┘│
│                                     │
│ [Cancel]              [Add Goal]    │
│                                     │
└─────────────────────────────────────┘
```

---

### 4.9 Invite Member Flow

#### 4.9.1 Generate Invite Screen

**Layout:**
```
┌─────────────────────────────────────┐
│ ✕  Invite to Morning Runners        │
├─────────────────────────────────────┤
│                                     │
│ Choose invite method:               │
│                                     │
│ ┌─────────────────────────────────┐│
│ │ 📱 QR Code                      ││
│ │ Show QR code for scanning       ││
│ │                     [Generate →]││
│ └─────────────────────────────────┘│
│                                     │
│ ┌─────────────────────────────────┐│
│ │ 🔤 Text Code                    ││
│ │ Share code via message          ││
│ │                     [Generate →]││
│ └─────────────────────────────────┘│
│                                     │
│ Invite Settings (optional)          │
│ ┌─────────────────────────────────┐│
│ │ Expiration: [Never ▼]           ││
│ │ Max uses:   [Unlimited ▼]       ││
│ └─────────────────────────────────┘│
│                                     │
│ [Close]                             │
│                                     │
└─────────────────────────────────────┘
```

#### 4.9.2 QR Code Display

**Layout:**
```
┌─────────────────────────────────────┐
│ ✕  Scan to Join                     │
├─────────────────────────────────────┤
│                                     │
│   Morning Runners                   │
│                                     │
│   ┌─────────────────────────────┐  │
│   │                             │  │
│   │      [QR CODE IMAGE]        │  │
│   │                             │  │
│   │                             │  │
│   └─────────────────────────────┘  │
│                                     │
│   Have them scan this code          │
│   with their Pursue app             │
│                                     │
│   Or share text code:               │
│   ABCD-1234-EFGH-5678               │
│   [📋 Copy Code]                    │
│                                     │
│   Expires: Never                    │
│   Max uses: Unlimited               │
│                                     │
│   [Close]                           │
│                                     │
└─────────────────────────────────────┘
```

---

### 4.10 Join Group Flow

#### 4.10.1 Join Group Entry Points

**From Groups List:**
- FAB menu: "Join Group"

**From Deep Link:**
- Tap invite link: `pursue://join?code=ABCD-1234...`

#### 4.10.2 Scan QR Code Screen

**Layout:**
```
┌─────────────────────────────────────┐
│ ✕  Join Group                       │
├─────────────────────────────────────┤
│                                     │
│ ┌─────────────────────────────────┐│
│ │                                 ││
│ │    [Camera Viewfinder]          ││
│ │                                 ││
│ │    Position QR code in frame    ││
│ │                                 ││
│ │    ┌─────────────────┐          ││
│ │    │                 │          ││ ← Scanning frame
│ │    │                 │          ││
│ │    └─────────────────┘          ││
│ │                                 ││
│ └─────────────────────────────────┘│
│                                     │
│ Or enter text code manually:        │
│ [Enter Code Instead]                │
│                                     │
│ [Cancel]                            │
│                                     │
└─────────────────────────────────────┘
```

**Permissions:**
- Request camera permission on first use
- If denied: Show "Enter Code Instead" option

#### 4.10.3 Enter Code Screen

**Layout:**
```
┌─────────────────────────────────────┐
│ ✕  Enter Invite Code                │
├─────────────────────────────────────┤
│                                     │
│ Paste or type the invite code       │
│ you received:                       │
│                                     │
│ ┌─────────────────────────────────┐│
│ │ ABCD-1234-EFGH-5678             ││
│ └─────────────────────────────────┘│
│ [📋 Paste]                          │
│                                     │
│                                     │
│ [Cancel]              [Continue]    │
│                                     │
└─────────────────────────────────────┘
```

**Validation:**
- Format check: XXXX-XXXX-XXXX-XXXX
- Auto-format as user types (add hyphens)

#### 4.10.4 Confirm Join Screen

**Layout:**
```
┌─────────────────────────────────────┐
│ ✕  Join Group?                      │
├─────────────────────────────────────┤
│                                     │
│        🏃                            │
│   Morning Runners                   │
│                                     │
│   8 members · 5 active goals        │
│                                     │
│   Created by Shannon Thompson       │
│                                     │
│   ────────────────────              │
│                                     │
│   By joining, you'll be able to:    │
│   • See group goals and members     │
│   • Track your progress             │
│   • View other members' progress    │
│                                     │
│   An admin must approve your        │
│   request to join.                  │
│                                     │
│ [Cancel]          [Request to Join] │
│                                     │
└─────────────────────────────────────┘
```

**On Request:**
- Create join request signed with user's private key
- Push to any reachable group member
- Show confirmation: "Join request sent! You'll be notified when an admin approves."
- Return to Groups List

---

### 4.11 Admin Approval Flow

#### 4.11.1 Join Request Notification

**Push Notification:**
```
🔔 Pursue
New member request
Alex Thompson wants to join Morning Runners
```

**In-App Notification (Bell icon with badge):**
- Shows count of pending requests

#### 4.11.2 Join Request Detail Screen

**Layout:**
```
┌─────────────────────────────────────┐
│ ✕  Member Request                   │
├─────────────────────────────────────┤
│                                     │
│        👤                            │
│   Alex Thompson                     │
│   ID: 8M9NxFfJ2wKL3p                │
│                                     │
│   Wants to join:                    │
│   Morning Runners                   │
│                                     │
│   Requested: 5 minutes ago          │
│                                     │
│   ────────────────────              │
│                                     │
│   Review this request carefully.    │
│   Only approve people you know      │
│   and trust.                        │
│                                     │
│                                     │
│ [Reject]                 [Approve]  │
│ Text btn (red)           Primary    │
│                                     │
└─────────────────────────────────────┘
```

**On Approve:**
- Create MEMBER_ADDED event, sign with admin's key
- Push event to all group members
- Show success: "Alex Thompson added to group"
- Send notification to new member

**On Reject:**
- Delete join request (no event needed)
- Optionally send rejection notification to requester

---

### 4.12 Log Progress Flow

#### 4.12.1 Log Progress Bottom Sheet

**Triggered by FAB on Goals Tab or Today Tab**

**Layout (Binary Goal):**
```
┌─────────────────────────────────────┐
│ ───                                 │ ← Drag handle
│ Log Progress                        │
├─────────────────────────────────────┤
│                                     │
│ Select Goal:                        │
│ ┌─────────────────────────────────┐│
│ │ 30 min run (Daily) ▼            ││ ← Dropdown
│ └─────────────────────────────────┘│
│                                     │
│ Did you complete this goal today?   │
│                                     │
│   ┌──────────┐  ┌──────────┐       │
│   │   Yes    │  │    No    │       │ ← Toggle buttons
│   └──────────┘  └──────────┘       │
│                                     │
│ Add Note (optional)                 │
│ ┌─────────────────────────────────┐│
│ │ Great run in the park!          ││
│ └─────────────────────────────────┘│
│                                     │
│ [Cancel]                 [Log]      │
│                                     │
└─────────────────────────────────────┘
```

**Layout (Numeric Goal):**
```
┌─────────────────────────────────────┐
│ ───                                 │
│ Log Progress                        │
├─────────────────────────────────────┤
│                                     │
│ Select Goal:                        │
│ ┌─────────────────────────────────┐│
│ │ Run distance (Weekly) ▼         ││
│ └─────────────────────────────────┘│
│                                     │
│ How far did you run?                │
│                                     │
│ ┌─────────────────────────────────┐│
│ │ [5.2]            km             ││ ← Number input
│ └─────────────────────────────────┘│
│                                     │
│ Progress: 26 / 50 km (52%)          │
│ ████████████░░░░░░░░░░░░            │
│                                     │
│ Add Note (optional)                 │
│ ┌─────────────────────────────────┐│
│ │                                 ││
│ └─────────────────────────────────┘│
│                                     │
│ [Cancel]                 [Log]      │
│                                     │
└─────────────────────────────────────┘
```

**On Log:**
- Create signed ProgressEntry
- Save to local database
- Attempt to push to peers
- Show success: "Progress logged!"
- Update UI immediately (optimistic update)
- Close bottom sheet

---

### 4.13 Sync Status & Details

#### 4.13.1 Sync Status Bottom Sheet

**Triggered by tapping sync icon in top bar**

**Layout:**
```
┌─────────────────────────────────────┐
│ ───                                 │
│ Sync Status                         │
├─────────────────────────────────────┤
│                                     │
│ ✓ All changes synced                │ ← Status summary
│ Last synced: 2 minutes ago          │
│                                     │
│ ────────────────────                │
│                                     │
│ Peers Online (3)                    │
│ ┌─────────────────────────────────┐│
│ │ ○ Alex Thompson                 ││
│ │ ○ Jamie Lee                     ││
│ │ ○ Morgan Kim                    ││
│ └─────────────────────────────────┘│
│                                     │
│ Peers Offline (5)                   │
│ ┌─────────────────────────────────┐│
│ │ ○ Chris Davis (last: 2 hrs ago) ││
│ │ ○ Sam Parker (last: 1 day ago)  ││
│ │ ... [View All]                  ││
│ └─────────────────────────────────┘│
│                                     │
│ Pending Changes (0)                 │
│ All progress synced ✓               │
│                                     │
│ [Sync Now]            [Close]       │
│ Outlined btn          Text btn      │
│                                     │
└─────────────────────────────────────┘
```

**Status Variants:**

**Syncing:**
```
⟳ Syncing...
Sending 3 updates to peers
```

**Pending:**
```
⚠ 5 changes pending
Waiting for peers to come online
[View Details]
```

**Failed:**
```
✗ Sync failed
Could not connect to peers
[Retry]  [View Details]
```

**Offline:**
```
○ Offline mode
No internet connection
Changes will sync when online
```

#### 4.13.2 Pending Changes Detail

**If user taps "View Details" on pending:**

**Layout:**
```
┌─────────────────────────────────────┐
│ ← Pending Changes                   │
├─────────────────────────────────────┤
│                                     │
│ Progress Updates (3)                │
│ ┌─────────────────────────────────┐│
│ │ ✓ Completed "30 min run"        ││
│ │ Today at 7:15 AM                ││
│ │ Status: Waiting for peers       ││
│ └─────────────────────────────────┘│
│ ┌─────────────────────────────────┐│
│ │ ✓ Completed "Meditate"          ││
│ │ Today at 7:45 AM                ││
│ │ Status: Waiting for peers       ││
│ └─────────────────────────────────┘│
│ ┌─────────────────────────────────┐│
│ │ 5.2 km added to "Run distance"  ││
│ │ Today at 7:50 AM                ││
│ │ Status: Sending to Alex...      ││
│ └─────────────────────────────────┘│
│                                     │
│ These updates will sync             │
│ automatically when peers come       │
│ online or you can use the relay     │
│ server for immediate delivery.      │
│                                     │
│ [Enable Relay Server]               │
│                                     │
└─────────────────────────────────────┘
```

---

### 4.14 Goal Detail View

**Layout:**
```
┌─────────────────────────────────────┐
│ ← 30 min run                   ⋮    │
├─────────────────────────────────────┤
│                                     │
│ Daily Goal                          │
│ Type: Binary (Yes/No)               │
│ Created by: Shannon                 │
│ Created: Jan 1, 2026                │
│                                     │
│ ────────────────────                │
│                                     │
│ This Week                           │
│ Mon  Tue  Wed  Thu  Fri  Sat  Sun   │
│  ✓    ✓    ○    ○    ○    ○    ○   │ ← Week view
│                                     │
│ Your Streak: 7 days 🔥              │
│ Completion rate: 85% (6/7 days)     │
│                                     │
│ ────────────────────                │
│                                     │
│ Group Progress                      │
│ ┌─────────────────────────────────┐│
│ │ Shannon      ✓✓✓✓✓○○  71% (5/7) ││
│ │ Alex         ✓✓✓✓✓✓✓ 100% (7/7) ││
│ │ Jamie        ✓✓○○✓○○  43% (3/7) ││
│ │ Morgan       ✓✓✓✓○○○  57% (4/7) ││
│ └─────────────────────────────────┘│
│                                     │
│ ────────────────────                │
│                                     │
│ Recent Activity                     │
│ ┌─────────────────────────────────┐│
│ │ Alex completed · Today 6:30 AM  ││
│ │ You completed · Yesterday 7:15  ││
│ │ Morgan completed · Yesterday    ││
│ └─────────────────────────────────┘│
│                                     │
└─────────────────────────────────────┘
                                   [+] ← FAB (Log Progress)
```

**Overflow Menu (⋮):**
- Edit goal (admins only)
- Archive goal (admins only)
- View full history

**Calendar Views (Monthly/Yearly Goals):**
- Show mini calendar with marked completion days
- Tap day to see details

---

### 4.15 Member Profile View

**Layout:**
```
┌─────────────────────────────────────┐
│ ← Alex Thompson                ⋮    │
├─────────────────────────────────────┤
│                                     │
│        ┌─────────┐                  │
│        │   👤    │                  │
│        │  Alex   │                  │
│        └─────────┘                  │
│                                     │
│     Alex Thompson                   │
│     Member since Jan 1, 2026        │
│     Last active: 2 hours ago        │
│                                     │
│ ────────────────────                │
│                                     │
│ Progress This Week                  │
│ ┌─────────────────────────────────┐│
│ │ 30 min run         ✓✓✓✓✓✓✓ 100% ││
│ │ Meditate           ✓✓✓○○○○  43% ││
│ │ Read 30 pages      ✓✓✓✓✓○○  71% ││
│ └─────────────────────────────────┘│
│                                     │
│ Overall Stats                       │
│ ┌─────────────────────────────────┐│
│ │ Total completions: 127          ││
│ │ Current streak: 14 days 🔥      ││
│ │ Longest streak: 28 days         ││
│ │ Completion rate: 78%            ││
│ └─────────────────────────────────┘│
│                                     │
└─────────────────────────────────────┘
```

**Overflow Menu (⋮) - Admins Only:**
- Promote to admin
- Remove from group

---

## 5. Empty States & Error Handling

### 5.1 Empty State Illustrations

**Style:**
- Simple, friendly line drawings
- Primary blue and gold colors with grey accents
- Optimistic, encouraging tone

**Common Empty States:**
- No groups yet → People holding hands in a circle
- No goals yet → Target/bullseye
- No activity yet → Calendar with checkmarks
- No notifications → Bell icon
- No search results → Magnifying glass

### 5.2 Error States

#### 5.2.1 Network Errors

**Snackbar (bottom of screen):**
```
┌─────────────────────────────────────┐
│ ⚠ No internet connection            │
│ Changes will sync when online       │
│                          [Dismiss]  │
└─────────────────────────────────────┘
```

Duration: 5 seconds or until dismissed

#### 5.2.2 Sync Failures

**Persistent Banner (top of screen):**
```
┌─────────────────────────────────────┐
│ ✗ Sync failed                       │
│ Could not connect to peers          │
│ [Retry]  [Details]           [✕]    │
└─────────────────────────────────────┘
```

Stays visible until resolved or dismissed

#### 5.2.3 Validation Errors

**Inline (below field):**
```
┌─────────────────────────────────────┐
│ Group Name *                        │
│ ┌─────────────────────────────────┐│
│ │                                 ││ ← Red outline
│ └─────────────────────────────────┘│
│ ✗ Group name is required            │ ← Red text
└─────────────────────────────────────┘
```

#### 5.2.4 Critical Errors (Dialog)

**Layout:**
```
┌─────────────────────────────────────┐
│                                     │
│          ⚠️                          │
│                                     │
│     Something went wrong            │
│                                     │
│  We couldn't complete that action.  │
│  Please try again.                  │
│                                     │
│  Error code: ERR_DB_001             │
│                                     │
│           [Try Again]               │
│           [Report Issue]            │
│                                     │
└─────────────────────────────────────┘
```

---

## 6. Notifications

### 6.1 Push Notification Types

**Progress Update:**
```
🔔 Pursue
Alex completed "30 min run"
Morning Runners
```

**New Member:**
```
🔔 Pursue
Jamie joined Morning Runners
Tap to welcome them!
```

**Group Renamed:**
```
🔔 Pursue
Shannon renamed the group
"Runners" → "Morning Runners"
```

**Join Request (Admins):**
```
🔔 Pursue
New member request
Alex wants to join Morning Runners
```

**Approved to Join:**
```
🔔 Pursue
You're in!
Shannon approved your request to join Morning Runners
```

### 6.2 In-App Notifications

**Bell Icon with Badge (top bar):**
- Shows count of unread notifications
- Tap to open Notifications screen

**Notifications Screen:**
```
┌─────────────────────────────────────┐
│ ← Notifications                ⋮    │
├─────────────────────────────────────┤
│                                     │
│ Today                               │
│ ┌─────────────────────────────────┐│
│ │ ✓ Alex completed "30 min run"   ││
│ │ Morning Runners · 2 hours ago   ││
│ └─────────────────────────────────┘│
│ ┌─────────────────────────────────┐│
│ │ 👥 New member request           ││
│ │ Chris Davis → Morning Runners   ││
│ │ 3 hours ago      [Approve]      ││
│ └─────────────────────────────────┘│
│                                     │
│ Yesterday                           │
│ ┌─────────────────────────────────┐│
│ │ 🏷️ Group renamed                ││
│ │ "Runners" → "Morning Runners"   ││
│ │ by Shannon                      ││
│ └─────────────────────────────────┘│
│                                     │
└─────────────────────────────────────┘
```

**Overflow Menu (⋮):**
- Mark all as read
- Notification settings

---

## 7. Animations & Transitions

### 7.1 Animation Philosophy

**Productivity First:**
- Animations serve a functional purpose (provide feedback, maintain context)
- No decorative animations that delay user actions
- Short durations (150-250ms typical)
- Returning users experience minimal animation overhead
- All animations respect Android's "Reduce Motion" accessibility setting

### 7.2 Screen Transitions

**Navigation:**
- **Forward**: Slide in from right (250ms, standard easing)
- **Back**: Slide out to right (200ms, accelerate easing)
- **Tab Switch**: Crossfade (150ms)
- **First Launch Only**: No splash screen animation for returning users

**Bottom Sheets:**
- **Open**: Slide up from bottom (250ms, decelerate easing)
- **Close**: Slide down (200ms, accelerate easing)

**Dialogs:**
- **Open**: Fade in (150ms)
- **Close**: Fade out (100ms)

### 7.3 Micro-Interactions

**Essential Feedback Only:**

**Button Press:**
- Ripple effect only (Material Design standard, ~300ms)
- No additional scale animations

**Card Tap:**
- Ripple effect (Material Design standard)
- No elevation change on tap

**Checkbox/Toggle:**
- Instant state change
- Standard Material Design toggle animation (200ms)

**Progress Logging:**
- Checkmark appears instantly
- Optional: Brief success feedback (200ms) for major milestones only

**Sync Status Icon:**
- Rotating sync icon when actively syncing (1s rotation)
- Static icons for all other states (synced, offline, failed)

**Pull to Refresh:**
- Standard Material Design pull-to-refresh
- Show sync status during refresh

### 7.4 Loading States

**Skeleton Screens:**
- Use for initial load of lists/cards
- Shimmer effect (1.5s loop)
- Grey boxes matching content layout

**Progress Indicators:**
- Circular spinner for full-screen loading
- Linear progress bar for operations with known duration

---

## 8. Accessibility

### 8.1 Requirements

**WCAG 2.1 Level AA Compliance:**
- Color contrast ratio ≥ 4.5:1 for normal text
- Color contrast ratio ≥ 3:1 for large text (18pt+)
- Touch targets ≥ 48dp × 48dp
- Support for screen readers (TalkBack)
- Dynamic text sizing (respect system font size)

### 8.2 Screen Reader Support

**Content Descriptions:**
- All images, icons, buttons have meaningful labels
- Example: Sync icon → "Sync status: All changes synced"
- Example: FAB → "Log progress for goals"

**Heading Hierarchy:**
- Proper use of heading levels for navigation
- Screen titles are H1
- Section headers are H2
- Subsections are H3

**Focus Order:**
- Logical tab order (top to bottom, left to right)
- Skip to main content option

### 8.3 High Contrast Mode

**Support Android's High Contrast setting:**
- Increase border weights (1dp → 2dp)
- Higher contrast colors
- No reliance on color alone for information

### 8.4 Reduce Motion

**Respect Android's Reduce Motion setting:**
- Disable decorative animations (confetti, etc.)
- Keep functional animations (loading, transitions)
- Reduce animation duration by 50%

---

## 9. Responsive Design

### 9.1 Screen Size Support

**Target Devices:**
- Small phones (5" - 5.5", 360dp width)
- Standard phones (5.5" - 6.5", 411dp width)
- Large phones/phablets (6.5"+, 480dp width)
- Tablets (7"+, 600dp+ width) - Nice to have

### 9.2 Orientation

**Portrait (Primary):**
- Optimized for one-handed use
- Bottom navigation accessible with thumb

**Landscape (Secondary):**
- Same layouts, wider spacing
- Consider two-column layout for tablets
- Bottom nav becomes side rail on tablets

### 9.3 Adaptive Layouts

**Breakpoints:**
- **Compact** (<600dp width): Phone portrait
- **Medium** (600-840dp width): Phone landscape, small tablet
- **Expanded** (>840dp width): Large tablet

**Adaptations:**
- Compact: Single column, bottom nav
- Medium: Single column, side nav (optional)
- Expanded: Two-column master-detail, side nav

---

## 10. Theming & Customization

### 10.1 Dark Mode Support

**Future Enhancement:**
- Follow system dark mode setting
- Dark color palette:
  - Background: #121212
  - Surface: #1E1E1E
  - Primary: #64B5F6 (Lighter blue)
  - Secondary: #FFD54F (Lighter gold/yellow)
  - Text: #FFFFFF, #B0B0B0

**MVP:** Light mode only

### 10.2 User Customization

**Current MVP:**
- Profile picture
- Display name

**Future:**
- Theme color selection (different primary colors)
- Group icon/emoji customization
- Goal icons

---

## 11. Onboarding Tips & Education

### 11.1 First-Time Tooltips

**After completing setup, show contextual tips:**

**Tip 1 (Home Screen):**
```
┌─────────────────────────────────────┐
│   💡 Tip                            │
│                                     │
│   Tap the + button to create        │
│   your first group!                 │
│                                     │
│              [Got it]               │
└─────────────────────────────────────┘
```

**Tip 2 (After creating group):**
```
┌─────────────────────────────────────┐
│   💡 Tip                            │
│                                     │
│   Invite members by tapping the     │
│   + button on the Members tab.      │
│                                     │
│              [Got it]               │
└─────────────────────────────────────┘
```

**Tip 3 (First daily goal):**
```
┌─────────────────────────────────────┐
│   💡 Tip                            │
│                                     │
│   Tap the + button to log your      │
│   progress each day!                │
│                                     │
│              [Got it]               │
└─────────────────────────────────────┘
```

**Dismissal:**
- User can tap "Got it" to dismiss
- Don't show again after dismissal
- Max 3-4 tips total

### 11.2 Empty State CTAs

**All empty states should include:**
- Clear explanation of what's missing
- Actionable next step (button)
- Optional: Link to help documentation

---

## 12. Platform-Specific Considerations

### 12.1 Android Guidelines

**Material Design 3:**
- Use Material Components (MDC-Android)
- Follow Material motion principles
- Respect system settings (font size, dark mode, etc.)

**Android System Integration:**
- Support share sheet for invite codes
- Support Android backup/restore (for app settings, not identity)
- Respect Do Not Disturb settings
- Battery optimization awareness (explain sync behavior)

**Permissions:**
- Camera (for QR scanning) - request on first use
- Notifications - request on first use
- Storage (for local backup) - scoped storage (Android 11+)
- Google Drive (for cloud backup) - OAuth flow

### 12.2 Performance Considerations

**Target Performance:**
- **App startup (cold)**: <1 second to first screen for returning users
- **App startup (warm)**: <300ms to first screen
- Screen transitions: 60 fps (16ms per frame)
- List scrolling: 60 fps
- Database queries: <50ms for common operations
- **Direct to functionality**: Returning users skip splash, see content immediately

**Optimization Strategies:**
- Lazy load group lists (paginate if >20 groups)
- Recycle views for long lists
- Cache avatars/images
- Background thread for crypto operations
- Debounce search/filter inputs

---

## 13. Future UI Enhancements

### 13.1 Short-Term (3-6 months)

- [ ] Progress charts and visualizations
- [ ] Goal templates library
- [ ] Custom goal icons
- [ ] Group themes/colors
- [ ] Achievement badges/milestones
- [ ] Weekly/monthly summary emails

### 13.2 Medium-Term (6-12 months)

- [ ] Dark mode
- [ ] Tablet optimized layouts
- [ ] Widgets (Today's goals on home screen)
- [ ] Apple Watch / Wear OS support
- [ ] Voice input for progress logging
- [ ] Photo attachments for progress

### 13.3 Long-Term (12+ months)

- [ ] Social features (comments, reactions on progress)
- [ ] Challenges/competitions within groups
- [ ] Integration with fitness trackers
- [ ] Web companion app
- [ ] iOS version (full parity)

---

## 14. Design Handoff & Assets

### 14.1 Required Assets

**Icons:**
- App icon (adaptive: 432×432dp foreground, 108×108dp background)
- Launcher icon (round, squircle, square variants)
- Notification icon (silhouette, 24×24dp)

**Illustrations:**
- Empty states (6-8 illustrations)
- Onboarding screens (3 illustrations)
- Error states (3-4 illustrations)

**Images:**
- Default avatar/profile pictures
- Group icon fallbacks

### 14.2 Design Tools & Files

**Figma (Recommended):**
- Complete design system with components
- All screens at 360dp width baseline
- Interactive prototype for flows
- Component library for developers

**Deliverables:**
- Figma file with all screens
- Exported SVG icons
- Color palette and typography specs (JSON/XML for Android)
- Animation specifications (Lottie files if applicable)

---

## 15. Success Metrics (UI/UX)

### 15.1 Usability Metrics

- **Task completion rate**: >90% for core flows (create group, log progress)
- **Time to first action**: <30 seconds from app open to first progress logged
- **Error rate**: <5% of interactions result in errors
- **User satisfaction**: >4.0/5.0 average rating

### 15.2 Engagement Metrics

- **Daily active users**: Track retention day 1, 7, 30
- **Progress logging frequency**: >70% of users log daily goals
- **Group creation rate**: Average user creates 1.5 groups
- **Invitation success**: >60% of invites result in joined members

---

**End of UI Specification**