# Pursue - Technical Specification

**Version:** 0.1 (Draft)  
**Last Updated:** January 15, 2026  
**Status:** Design Phase

---

## 1. Overview

### 1.1 Purpose
**Pursue** is a peer-to-peer Android mobile application that enables individuals to form accountability groups for tracking and sharing progress on personal goals (daily, weekly, monthly, yearly). The system operates without centralized servers for group management and data storage.

### 1.2 Core Principles
- **Decentralized**: No central server required for group operations
- **Privacy-focused**: Data stays within the group, peer-to-peer synchronization
- **Offline-capable**: Core functionality works without internet connectivity
- **Secure**: Cryptographic signatures ensure data integrity and member authentication

---

## 2. System Architecture

### 2.1 High-Level Design
The application uses a **peer-to-peer push/pull synchronization model**:
- When a device comes online, it pushes local updates to all known peers
- Simultaneously pulls/queries updates from each reachable peer
- No reliance on always-on peers or central coordination

### 2.2 Data Ownership
- Each user's goal progress data is owned and signed by that user
- Group membership data is maintained as a replicated signed event log
- All peers maintain a full copy of group state (goals, members, progress)

---

## 3. Identity Management

### 3.1 Self-Sovereign Identity Model
Users own their identity through cryptographic keypairs.

**Identity Structure:**
- **User ID**: Hash of public key (e.g., first 16 bytes, base58 encoded)
- **Public Key**: Ed25519 public key (32 bytes)
- **Private Key**: Ed25519 private key (stored encrypted on device)

### 3.2 Registration Flow
1. User opens app for first time
2. App generates Ed25519 keypair
3. User creates display name and optional avatar
4. App derives 12-word BIP39 mnemonic seed phrase from private key
5. User shown seed phrase with prominent warning:
    - "WRITE THIS DOWN - This is the only way to recover your account"
    - "Never share this phrase with anyone"
6. User confirms they've saved the seed phrase (checkbox + re-enter random words)
7. **Optional Backup**: User offered choice to back up encrypted seed phrase:
    - **Google Drive Backup**: Encrypted with user-chosen password, uploaded to Google Drive
    - **Local File Backup**: Encrypted file saved to device storage (can be manually copied elsewhere)
    - **Skip Backup**: User takes full responsibility for manual seed phrase storage
8. Private key stored in Android Keystore (hardware-backed if available)

### 3.3 Seed Phrase Backup Options

**Pursue** offers optional encrypted backup of the seed phrase to reduce the risk of permanent account loss.

#### 3.3.1 Google Drive Backup
- User chooses a backup password (minimum 8 characters, strength indicator shown)
- Seed phrase encrypted using AES-256-GCM with PBKDF2-derived key:
    - Password → PBKDF2 (100,000 iterations, SHA-256) → 256-bit key
    - Random 96-bit IV generated for each backup
    - Authenticated encryption prevents tampering
- Encrypted file format:
  ```
  {
    "version": 1,
    "salt": "<base64>",
    "iv": "<base64>",
    "ciphertext": "<base64>",
    "created_at": "<timestamp>"
  }
  ```
- File uploaded to Google Drive with name: `pursue_backup_<timestamp>.json`
- User can download and decrypt on new device using backup password

#### 3.3.2 Local File Backup
- Same encryption scheme as Google Drive backup
- Saved to: `/storage/emulated/0/Documents/Pursue/pursue_backup_<timestamp>.json`
- User notified of file location and encouraged to:
    - Copy to external storage (SD card, USB drive)
    - Email to themselves
    - Store in personal cloud storage
- File can be imported on new device via "Restore from File" option

#### 3.3.3 Security Considerations
- Backup password must be different from (and stronger than) simple device unlock
- User warned: "If you forget your backup password, the backup is useless"
- Backup file alone is useless without password (encrypted at rest)
- User can create multiple backups with different passwords
- User can re-backup at any time from Account Settings

### 3.4 Account Recovery Flow

#### Option A: Restore from Seed Phrase
1. User selects "Restore Account" on new device
2. Enters 12-word seed phrase manually
3. App regenerates keypair from seed
4. User re-enters display name (stored separately from identity)
5. Groups must be re-joined (membership records already exist in group logs)

#### Option B: Restore from Encrypted Backup
1. User selects "Restore from Backup" on new device
2. Chooses backup source:
    - **Google Drive**: App lists available `pursue_backup_*.json` files
    - **Local File**: User browses to file location
3. User enters backup password
4. App decrypts backup and extracts seed phrase
5. Keypair regenerated from seed (same as Option A)
6. User re-enters display name
7. Groups must be re-joined

#### Recovery Edge Cases
- **Lost seed phrase AND backup**: Account unrecoverable, user must create new identity
- **Forgotten backup password**: Backup file useless, must use manual seed phrase
- **Corrupted backup file**: Integrity check fails, user prompted to use seed phrase or different backup
- **Multiple backups available**: User can try each until correct password found

### 3.5 Profile Information
- **Immutable**: User ID (public key hash)
- **Mutable**: Display name, avatar, device name
- Profile updates signed and gossiped to groups

---

## 4. Group Management

### 4.1 Group Structure
```
Group:
  - group_id: UUID
  - group_name: String
  - creation_timestamp: Unix timestamp
  - creator_pubkey: bytes32
  - administrators: Set<UserID>
  - members: Set<UserID>
  - goals: List<Goal>
  - event_log: List<SignedEvent>
```

### 4.2 Group Roles
- **Creator**: Initial admin, creates the group
- **Administrator**: Can approve new members, remove members, modify group goals
- **Member**: Can track personal progress, view others' progress

### 4.3 Group Creation
1. User creates group with name and initial goals
2. App generates group UUID
3. Creator automatically becomes first administrator
4. Invitation codes generated (see 4.4)

### 4.4 Member Invitation System

**Invitation Code Structure:**
```
InviteCode:
  - group_id: UUID
  - invite_token: random 128-bit value
  - created_by: admin UserID
  - created_at: timestamp
  - expiry: timestamp (optional)
  - max_uses: int (optional)
```

**Invitation Flow:**
1. Admin generates invite code
2. Code encoded as:
    - **Text code**: Base58-encoded string (for manual entry)
    - **QR code**: JSON payload encoded in QR format
3. New user scans/enters code
4. App generates join request:
   ```
   JoinRequest:
     - group_id: UUID
     - invite_token: bytes16
     - requester_pubkey: bytes32
     - requester_name: String
     - timestamp: Unix timestamp
     - signature: sign(requester_privkey, [group_id|invite_token|timestamp])
   ```
5. Join request pushed to any reachable group member
6. Admin receives request notification
7. Admin approves/rejects:
   ```
   MembershipEvent:
     - event_type: "MEMBER_ADDED" | "MEMBER_REMOVED"
     - group_id: UUID
     - subject_user_id: UserID
     - admin_user_id: UserID
     - timestamp: Unix timestamp
     - lamport_clock: int
     - admin_signature: bytes64
   ```
8. Approval gossiped to all group members
9. New member receives full group state from peers

### 4.5 Membership Event Log

The group maintains a **signed append-only event log** for all membership changes.

**Event Types:**
- `GROUP_CREATED`
- `MEMBER_ADDED`
- `MEMBER_REMOVED`
- `ADMIN_PROMOTED`
- `ADMIN_DEMOTED`
- `GROUP_NAME_CHANGED` (includes old_name and new_name fields)

**Conflict Resolution Rules:**
1. Events ordered by Lamport clock (with timestamp as secondary sort)
2. In case of conflicting concurrent events (e.g., two admins add different members):
    - Both events accepted (ADD always succeeds)
3. For contradictory events (e.g., concurrent ADD and REMOVE of same member):
    - **ADD wins over REMOVE** (permissive by default)
    - Both events retained in log for audit purposes
4. Admin signatures must be valid and from current admin set
5. Events signed by removed admins after their removal are rejected

### 4.6 Admin Management
- Creator can promote members to admin
- Admins can promote/demote other admins (except creator)
- Creator cannot be demoted (special status)
- If creator leaves, admin privileges continue for existing admins
- Last admin standing can promote new admins
- **Admins can rename the group** (creates GROUP_NAME_CHANGED event)

---

## 5. Goal Tracking

### 5.1 Goal Structure
```
Goal:
  - goal_id: UUID
  - title: String
  - description: String (optional)
  - cadence: DAILY | WEEKLY | MONTHLY | YEARLY
  - metric_type: BINARY | NUMERIC | DURATION | CUSTOM
  - target_value: float (optional, for numeric goals)
  - created_by: UserID
  - created_at: timestamp
  - active: boolean
```

### 5.2 Goal Types by Cadence
- **Daily**: Reset every day at midnight (user's local time)
- **Weekly**: Reset every Monday at midnight
- **Monthly**: Reset on the 1st of each month
- **Yearly**: Reset on January 1st

### 5.3 Progress Tracking
```
ProgressEntry:
  - entry_id: UUID
  - goal_id: UUID
  - user_id: UserID
  - timestamp: Unix timestamp
  - period_id: String (e.g., "2026-01-15" for daily, "2026-W03" for weekly)
  - value: float | boolean
  - note: String (optional)
  - signature: sign(user_privkey, [entry_id|goal_id|timestamp|value])
```

**Progress Entry Properties:**
- Signed by user to prevent tampering
- Immutable once created (no editing, only add new entries)
- Append-only log per user per goal
- Multiple entries per period allowed (e.g., incrementing a counter)

### 5.4 Goal Management
- Any member can propose a new goal (stored as proposal)
- Admin must approve goal to make it active
- Admins can archive goals (stops new progress, historical data retained)
- Goals cannot be deleted (maintain historical integrity)

---

## 6. Peer-to-Peer Synchronization

### 6.1 Push/Pull Sync Protocol

**When Device Comes Online:**
1. Connect to all known peers for this group
2. **PUSH phase**: Send local updates since last sync
    - New progress entries
    - New membership events
    - Profile updates
3. **PULL phase**: Query peers for their updates
    - Request events since last known Lamport clock value
    - Request progress entries since last sync timestamp
4. **Reconciliation**: Merge received data with local state

### 6.2 Peer Discovery & Connectivity

Each group maintains a peer list:
```
Peer:
  - user_id: UserID
  - device_id: UUID
  - last_seen: timestamp
  - connection_addresses: List<Address>
    - local: IP address (for LAN discovery)
```

**Discovery Mechanisms:**
- **Local Network (LAN)**: mDNS/DNS-SD for nearby peers
- **Direct Connection**: Manual IP:Port entry
- **Relay Server**: Minimal notification relay for offline message delivery (see Section 6.6)

### 6.3 Relay Server for Push Notifications

To enable push notifications when the app is closed, Pursue uses a **minimal relay server** that facilitates message delivery without storing group data.

**Relay Server Responsibilities:**
- Register and store FCM (Firebase Cloud Messaging) tokens for devices
- Forward encrypted notification payloads to offline peers via FCM
- Temporarily store encrypted message blobs (max 7 days)
- Auto-delete blobs after delivery or expiration

**What the Relay Server CANNOT Do:**
- Read group data (all data end-to-end encrypted)
- Modify user data (all updates cryptographically signed)
- Participate in groups or impersonate users
- Access goal tracking data or progress entries

**Privacy Considerations:**
- Server sees metadata: sender ID, recipient IDs, timestamps
- Server cannot decrypt message content
- Server cannot determine group membership or relationships
- All sensitive data remains on user devices only

**User Control:**
- Users can optionally disable relay in settings (manual sync only)
- Advanced users can self-host their own relay server
- Relay server code will be open-source

For detailed relay server architecture, see `Pursue-FCM-Relay-Server-Spec.md`.

### 6.4 Connection Protocol
- Use TCP sockets for direct connections
- TLS/DTLS for encryption in transit
- Each message signed with sender's private key
- Mutual authentication using public keys

### 6.5 Sync Message Format
```
SyncMessage:
  - protocol_version: int
  - sender_user_id: UserID
  - sender_device_id: UUID
  - group_id: UUID
  - message_type: PUSH | PULL_REQUEST | PULL_RESPONSE
  - payload: SyncPayload
  - signature: bytes64

SyncPayload:
  - membership_events: List<MembershipEvent>
  - progress_entries: List<ProgressEntry>
  - last_known_lamport: int
  - last_sync_timestamp: Unix timestamp
```

### 6.6 Offline Support & Relay Flow

**When Both Peers Online:**
- Direct peer-to-peer connection (no relay involvement)
- Synchronous push/pull sync

**When Recipient Offline:**
1. Sender encrypts update with recipient's public key
2. Sender sends encrypted blob to relay server with signature
3. Relay server:
    - Verifies sender signature
    - Stores encrypted blob temporarily
    - Sends FCM push notification to recipient's device
4. Recipient's device wakes up from FCM notification
5. Recipient fetches encrypted blob from relay
6. Recipient decrypts, verifies signature, applies update
7. Relay deletes blob after successful retrieval

**Local Queue:**
- All local updates (progress, membership events) queued in SQLite
- Sync automatically when network becomes available
- Show clear UI indicators for "synced" vs "pending sync" state

---

## 7. Data Storage

### 7.1 Local Storage (SQLite)
Each device maintains:
- **Users**: Identity information (pubkey, display name, avatar)
- **Groups**: Group metadata
- **Membership Events**: Full event log per group
- **Goals**: Goal definitions per group
- **Progress Entries**: All users' progress (full replica)
- **Sync State**: Last sync timestamps per peer
- **Pending Updates**: Outgoing updates not yet synced

### 7.2 Data Retention
- All historical data retained indefinitely by default
- Users can manually export and delete old groups
- No automatic pruning (preserves accountability history)

---

## 8. Security Considerations

### 8.1 Threat Model
**Protected Against:**
- Impersonation (via signature verification)
- Data tampering (all updates signed)
- Unauthorized membership (admin signatures required)
- Privacy breaches (no central server, data stays in group)

**Not Protected Against:**
- Malicious admin (admins have full control)
- Device compromise (private key theft)
- Social engineering (sharing seed phrase)
- Group members colluding (they can see all group data)

### 8.2 Cryptographic Primitives
- **Signatures**: Ed25519 (fast, secure, 64-byte signatures)
- **Hashing**: SHA-256 for UserIDs
- **Seed Phrases**: BIP39 standard (12 words = 128 bits entropy)
- **Key Storage**: Android Keystore (hardware-backed when available)

### 8.3 Signature Verification
All peers verify:
1. Membership events signed by valid admin
2. Progress entries signed by the claiming user
3. Message signatures match sender's public key
4. Timestamps within acceptable window (prevent replay attacks)

### 8.4 Privacy
- Group data only shared with group members
- No analytics or telemetry by default
- No central database of users or groups
- User can be member of multiple groups (identities isolated per group)

---

## 9. User Interface Considerations

### 9.1 Core Screens
1. **Home/Groups List**: All groups user belongs to
2. **Group Detail**: Members, goals, recent activity
3. **Goal Dashboard**: User's goals and progress for a period
4. **Progress Entry**: Log achievement for a goal
5. **Member Profile**: View another member's progress
6. **Group Settings**: Manage members, goals, admins (if admin)
7. **Account Settings**: Backup seed phrase (Google Drive/File), manage backups, profile, devices

### 9.2 Key UX Patterns
- **Visual sync status**: Clear indication of synced vs pending data
- **Offline indicators**: Show which peers are online/offline
- **Progress visualization**: Charts, streaks, completion percentages
- **Notifications**: New achievements, membership changes, sync completion
- **Group rename notifications**: When a GROUP_NAME_CHANGED event arrives:
    - Show prominent in-app notification: "Admin [name] renamed '[old name]' to '[new name]'"
    - Display banner at top of group detail screen on next visit
    - Add entry to group activity feed/timeline
    - Consider push notification for significant changes
- **Gentle onboarding**: Explain P2P nature, seed phrase importance, backup options
- **Backup reminders**: Periodic gentle reminders to verify backup exists (monthly)

---

## 10. Open Questions & Further Analysis Needed

### 10.1 Network & Connectivity
- [ ] **NAT Traversal Strategy**: Do we require relay servers for internet connectivity? What's the fallback if local network isn't available?
- [ ] **Bandwidth Optimization**: For large groups (50+ members), how do we minimize data transfer? Delta syncing? Compression?
- [ ] **Connection Prioritization**: If 20 peers are online, do we connect to all? Random subset? Most recently active?
- [ ] **Timeout Handling**: How long do we wait for a peer before marking them offline?

### 10.2 Scalability
- [ ] **Group Size Limits**: What's the maximum practical group size given storage and sync overhead?
- [ ] **Goal Count Limits**: How many active goals per group is reasonable?
- [ ] **Historical Data**: After 2 years of daily entries, how large is the database? Do we need archival features?
- [ ] **Sync Performance**: Benchmarking needed for sync time with varying data sizes

### 10.3 Conflict Resolution Edge Cases
- [ ] **Split-Brain Scenarios**: What if group partitions into two halves that both operate independently for a week?
- [ ] **Byzantine Admins**: What if a rogue admin mass-removes members or goes on a promotion spree?
- [ ] **Time Synchronization**: How do we handle devices with incorrect system clocks?
- [ ] **Lamport Clock Overflow**: Unlikely but should define behavior

### 10.4 User Experience
- [ ] **Multiple Device Support**: Should we support one user having multiple devices in a group? How does this interact with identity?
- [ ] **Group Discovery**: How do users find and join public/semi-public groups if they exist?
- [ ] **Notification Strategy**: How chatty should the app be? Sync completion notifications could be noisy.
- [ ] **Backup Password Strength**: Should we enforce minimum password requirements for encrypted backups?
- [ ] **Backup Versioning**: Should users be able to maintain multiple named backups (e.g., "home computer", "USB drive")?
- [ ] **Group Rename Frequency**: Should we rate-limit how often admins can rename a group to prevent spam?
- [ ] **Rename History**: Should users be able to view full rename history in group settings/activity log?

### 10.5 Platform & Implementation
- [ ] **iOS Support**: Is this Android-only or should we plan for cross-platform (React Native, Flutter)?
- [ ] **Desktop Companion**: Would a web/desktop app for viewing progress be useful?
- [ ] **Relay Server Hosting**: Google Cloud Run chosen for production. Need to determine staging/dev environments.
- [ ] **Self-Hosted Relay**: Documentation and Docker setup for users who want to run their own relay
- [ ] **Testing Strategy**: How do we test P2P sync behavior reliably? Simulator for network conditions?
- [ ] **FCM Fallback**: What happens if FCM is unavailable in certain regions (China, etc.)?

### 10.6 Privacy & Compliance
- [ ] **GDPR Compliance**: How does a user exercise "right to be forgotten" in a P2P system?
- [ ] **Data Minimization**: Are we collecting only necessary data?
- [ ] **Child Safety**: Age verification? COPPA compliance if minors use it?
- [ ] **Abuse Prevention**: How do we handle harassment within groups? Block functionality?

### 10.7 Goal Tracking Features
- [ ] **Partial Credit**: Should users be able to log partial completion (e.g., 30 min of 60 min goal)?
- [ ] **Retroactive Logging**: Can users log progress for past days? How far back?
- [ ] **Goal Templates**: Pre-built goal types (exercise, reading, meditation, etc.)?
- [ ] **Progress Verification**: Can peers challenge/verify each other's achievements? Photo proof?

### 10.8 Advanced Features (Future Consideration)
- [ ] **Subgroups**: Can a large group have subdivisions?
- [ ] **Cross-Group Goals**: Can users track the same goal across multiple groups?
- [ ] **Group Challenges**: Time-bound competitions within a group?
- [ ] **Data Export**: Standard format for exporting group data (CSV, JSON)?
- [ ] **Integration**: Sync with fitness trackers, calendar, other apps?

---

## 11. Development Phases

### Phase 1: Core Prototype
- Single device, local-only mode
- Basic identity creation (seed phrase)
- Create group, add goals, log progress
- Simple UI for core flows

### Phase 2: P2P Sync (LAN)
- Local network peer discovery (mDNS)
- Push/pull sync protocol
- Membership event log
- Multi-device testing on same WiFi

### Phase 3: Relay Server & Push Notifications
- Deploy minimal FCM relay server to Google Cloud Run
- Implement encrypted blob storage and retrieval
- FCM push notification integration
- Background sync when app closed
- Sync status UI

### Phase 4: Internet Connectivity & NAT Traversal
- Manual peer connection (IP:Port)
- Relay-mediated message delivery for offline peers
- Connection fallback strategies

### Phase 5: Polish & Features
- Notifications
- Progress visualizations
- Admin controls
- Backup/restore flows (Google Drive + local file)
- Performance optimization

### Phase 6: Beta Testing
- Real-world usage with test groups
- Bug fixes, UX improvements
- Security audit
- Scalability testing
- Relay server load testing

---

## 12. Success Metrics

- **Sync Reliability**: 99%+ of updates successfully synced within 5 minutes of peer availability
- **Offline Capability**: All core features work without network
- **Performance**: Sync <5 seconds for typical group (10 members, 50 goals, 1000 entries)
- **Security**: Zero unauthorized access events in beta testing
- **Usability**: <5% seed phrase loss rate among test users

---

## 13. References & Inspirations

- **Signal Protocol**: Multi-device sync and identity management
- **Signal Sealed Sender**: Server-mediated delivery without metadata exposure
- **Briar**: P2P messaging app with opportunistic sync and mailbox servers
- **Secure Scuttlebutt**: Gossip protocol for social networks
- **BitTorrent**: Peer discovery and data synchronization
- **IPFS**: Content-addressed distributed file system
- **BIP39**: Mnemonic seed phrase standard
- **Firebase Cloud Messaging (FCM)**: Push notification infrastructure
- **Google Cloud Run**: Serverless container platform for relay server

---

## Appendix A: Example Data Structures

### A.1 Example Progress Entry
```json
{
  "entry_id": "550e8400-e29b-41d4-a716-446655440000",
  "goal_id": "7c9e6679-7425-40de-944b-e07fc1f90ae7",
  "user_id": "5K2PwDd8FvGH7t",
  "timestamp": 1705334400,
  "period_id": "2026-01-15",
  "value": true,
  "note": "30 minute run in the park",
  "signature": "3045022100ab3c2d..."
}
```

### A.2 Example Membership Event (Member Addition)
```json
{
  "event_type": "MEMBER_ADDED",
  "group_id": "9b7e6679-1234-40de-944b-e07fc1f90123",
  "subject_user_id": "8M9NxFfJ2wKL3p",
  "admin_user_id": "5K2PwDd8FvGH7t",
  "timestamp": 1705334400,
  "lamport_clock": 47,
  "admin_signature": "304502210098fa7b..."
}
```

### A.2b Example Membership Event (Group Rename)
```json
{
  "event_type": "GROUP_NAME_CHANGED",
  "group_id": "9b7e6679-1234-40de-944b-e07fc1f90123",
  "old_name": "Morning Runners",
  "new_name": "Dawn Warriors Running Club",
  "admin_user_id": "5K2PwDd8FvGH7t",
  "timestamp": 1705420800,
  "lamport_clock": 89,
  "admin_signature": "304502210012cd8f..."
}
```

### A.3 Example Invite Code (QR Payload)
```json
{
  "v": 1,
  "group_id": "9b7e6679-1234-40de-944b-e07fc1f90123",
  "group_name": "Morning Runners",
  "token": "Xk9mPq2vN8bL5tR3",
  "created_by": "5K2PwDd8FvGH7t",
  "expires": 1705420800
}
```

---

**End of Specification Document**