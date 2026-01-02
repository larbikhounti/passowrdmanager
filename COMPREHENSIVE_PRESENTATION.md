# Password Manager Application
## Comprehensive Technical Presentation

**A JavaFX-Based Secure Credential Management System**

**Technology Stack**: Java 21 | JavaFX 23.0.1 | Oracle Database | Maven

---

## Table of Contents

1. [Project Overview](#1-project-overview)
2. [System Architecture](#2-system-architecture)
3. [Database Design & ER Diagram](#3-database-design--er-diagram)
4. [Complete Class Diagram](#4-complete-class-diagram)
5. [Design Patterns (6 Patterns)](#5-design-patterns)
6. [Security Implementation](#6-security-implementation)
7. [Key Features & CRUD Operations](#7-key-features--crud-operations)
8. [User Interface](#8-user-interface)
9. [Application Flow](#9-application-flow)
10. [Build & Deployment](#10-build--deployment)
11. [Challenges & Solutions](#11-challenges--solutions)
12. [Future Improvements](#12-future-improvements)
13. [Conclusion](#13-conclusion)

---

## 1. Project Overview

### What is This Project?

A **desktop password manager** application that provides:
- Secure storage for **Email credentials**, **Credit Cards**, and **Secure Notes**
- **Master password** authentication using PBKDF2 hashing
- **Complete CRUD operations** for all credential types
- **Search functionality** across all fields
- **Three-panel dashboard** UI with dark theme
- **Database-backed persistence** with Oracle Database

### Why This Project?

- Demonstrates **object-oriented programming** principles
- Implements **6 design patterns** professionally
- Shows **database integration** with complex queries
- Applies **security best practices** (PBKDF2 hashing)
- Creates a **modern JavaFX UI** with FXML

### Technology Justification

| Technology | Reason |
|------------|--------|
| **Java 21** | Latest LTS with modern features, record classes, pattern matching |
| **JavaFX 23.0.1** | Cross-platform UI framework with FXML support |
| **Oracle Database** | Enterprise-grade RDBMS with strong ACID compliance |
| **Maven** | Industry-standard build tool with dependency management |
| **JPMS** | Module system for better encapsulation and security |

---

## 2. System Architecture

### Layered Architecture Diagram

```
┌──────────────────────────────────────────────────────────────┐
│                   PRESENTATION LAYER                         │
│  ┌────────────────────────────────────────────────────────┐  │
│  │  FXML Views (dashboard_view, authentication_view)      │  │
│  │  Controllers (DashboardController, AuthController)     │  │
│  └────────────────────────────────────────────────────────┘  │
└───────────────────────────┬──────────────────────────────────┘
                            │
                            ↓
┌──────────────────────────────────────────────────────────────┐
│                     SERVICE LAYER                            │
│  ┌────────────────────────────────────────────────────────┐  │
│  │  DashboardService                                       │  │
│  │  CredentialsService                                     │  │
│  │  RenderService                                          │  │
│  │  AuthenticationService                                  │  │
│  └────────────────────────────────────────────────────────┘  │
└───────────────────────────┬──────────────────────────────────┘
                            │
                            ↓
┌──────────────────────────────────────────────────────────────┐
│                   STRATEGY LAYER                             │
│  ┌────────────────────────────────────────────────────────┐  │
│  │  StrategiesFactory (Factory Pattern)                    │  │
│  │  EmailStrategy, NoteStrategy, CreditCardStrategy        │  │
│  └────────────────────────────────────────────────────────┘  │
└───────────────────────────┬──────────────────────────────────┘
                            │
                            ↓
┌──────────────────────────────────────────────────────────────┐
│                  REPOSITORY LAYER                            │
│  ┌────────────────────────────────────────────────────────┐  │
│  │  ICredential Interface                                  │  │
│  │  EmailRepository, NoteRepository, CreditCardRepository  │  │
│  │  CredetialsRepository (Aggregator)                      │  │
│  └────────────────────────────────────────────────────────┘  │
└───────────────────────────┬──────────────────────────────────┘
                            │
                            ↓
┌──────────────────────────────────────────────────────────────┐
│                    UTILITY LAYER                             │
│  ┌────────────────────────────────────────────────────────┐  │
│  │  DbConnector (Singleton)                                │  │
│  │  Helpers (Static Utilities)                             │  │
│  │  PasswordStorage (Security)                             │  │
│  └────────────────────────────────────────────────────────┘  │
└───────────────────────────┬──────────────────────────────────┘
                            │
                            ↓
┌──────────────────────────────────────────────────────────────┐
│                   DATA LAYER                                 │
│  ┌────────────────────────────────────────────────────────┐  │
│  │  Oracle Database XE (192.168.1.8:1521/xe)              │  │
│  │  Tables: credential, emails, credit_cards, notes        │  │
│  └────────────────────────────────────────────────────────┘  │
└──────────────────────────────────────────────────────────────┘
```

### MVC with Service Layer

**Model (Entity Layer)**:
```java
public abstract class Entity {
    private int id;
    public abstract void render(RenderService renderer);
    public abstract void renderMany(RenderService renderer);
}

public class Email extends Entity {
    private String url, email, password;
    // Getters, setters, render methods
}
```

**View (FXML)**:
- `dashboard_view.fxml` - Main UI
- `authentication_view.fxml` - Login
- `setPassword_view.fxml` - Setup
- `addCredential_view.fxml` - Form

**Controller**:
```java
public class DashboardController {
    @FXML private TextField searchTextField;
    @FXML private VBox credentialsContainer;
    private DashboardService dashboardService;

    @FXML
    public void initialize() {
        dashboardService = new DashboardService(...);
        dashboardService.initializeDashboard();
    }
}
```

**Service**:
```java
public class DashboardService {
    public void initializeDashboard() {
        loadCredentials();
        refreshUI();
    }
}
```

### Package Structure

```
src/main/java/com/password/manager/
├── App.java                          # Entry point
├── module-info.java                  # JPMS module
│
├── auth/                             # 3 classes
│   ├── controllers/
│   │   ├── AuthenticationController.java
│   │   └── SetPasswordController.java
│   ├── passwordHandler/
│   │   └── PasswordStorage.java      # PBKDF2 implementation
│   └── services/
│       └── AuthenticationService.java
│
├── credentials/                      # 21 classes
│   ├── base/
│   │   └── Entity.java               # Abstract base
│   ├── contracts/
│   │   └── ICredential.java          # CRUD interface
│   ├── controllers/
│   │   └── CredentialController.java
│   ├── entities/
│   │   ├── Email.java
│   │   ├── Note.java
│   │   └── CreditCard.java
│   ├── enums/
│   │   ├── CredentialEnum.java
│   │   └── CardTypeEnum.java
│   ├── factories/
│   │   └── StrategiesFactory.java    # Factory Pattern
│   ├── repositories/
│   │   ├── CredetialsRepository.java # Aggregator
│   │   ├── EmailRepository.java
│   │   ├── NoteRepository.java
│   │   └── CreditCardRepository.java
│   ├── seeds/
│   │   └── CredentialSeeder.java
│   └── services/
│       ├── CredentialsService.java
│       ├── RenderService.java
│       └── strategies/
│           ├── EmailStrategy.java
│           ├── NoteStrategy.java
│           ├── CreditCardStrategy.java
│           └── wallet.java           # Stub
│
├── dashboard/                        # 2 classes
│   ├── controllers/
│   │   └── DashboardController.java
│   └── services/
│       └── DashboardService.java
│
└── utils/                            # 2 classes
    ├── DbConnector.java              # Singleton
    └── Helpers.java                  # Static utilities

**TOTAL**: 28 Java classes + 4 FXML views
```

---

## 3. Database Design & ER Diagram

### Database System

**Oracle Database XE (Express Edition)**
- **Host**: 192.168.1.8
- **Port**: 1521
- **SID**: xe
- **JDBC URL**: `jdbc:oracle:thin:@//192.168.1.8:1521/xe`
- **Credentials**: system/123456 (hardcoded - security issue!)

### Complete Database Schema

#### Table 1: `credential` (Parent)

```sql
CREATE TABLE credential (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    type VARCHAR2(50) NOT NULL
         CHECK (type IN ('password', 'credit_card', 'note'))
);
```

**Purpose**: Parent table storing credential type
**Type Mapping**:
- `'password'` → Email entity (credential_id = 2)
- `'credit_card'` → CreditCard entity (credential_id = 3)
- `'note'` → Note entity (credential_id = 1)

#### Table 2: `emails` (Child)

```sql
CREATE TABLE emails (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    credential_id NUMBER NOT NULL,
    url VARCHAR2(255) NOT NULL,
    email VARCHAR2(255) NOT NULL,
    password VARCHAR2(255) NOT NULL,
    FOREIGN KEY (credential_id) REFERENCES credential(id)
        ON DELETE CASCADE
);
```

**Purpose**: Stores email/password credentials with website URLs
**Searchable Fields**: url, email, password

#### Table 3: `credit_cards` (Child)

```sql
CREATE TABLE credit_cards (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    credential_id NUMBER NOT NULL,
    card_number VARCHAR2(50) NOT NULL,
    card_expiry VARCHAR2(10) NOT NULL,
    card_cvv VARCHAR2(10) NOT NULL,
    card_holder_name VARCHAR2(100) NOT NULL,
    FOREIGN KEY (credential_id) REFERENCES credential(id)
        ON DELETE CASCADE
);
```

**Purpose**: Stores credit card information
**Searchable Fields**: card_number, card_expiry, card_cvv, card_holder_name

#### Table 4: `notes` (Child)

```sql
CREATE TABLE notes (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    credential_id NUMBER NOT NULL,
    title VARCHAR2(255),
    note VARCHAR2(500),
    FOREIGN KEY (credential_id) REFERENCES credential(id)
        ON DELETE CASCADE
);
```

**Purpose**: Stores secure text notes
**Searchable Fields**: title, note

### Entity-Relationship Diagram

```
                    ┌──────────────────────┐
                    │    credential        │
                    ├──────────────────────┤
                    │ PK: id (NUMBER)      │
                    │     type (VARCHAR2)  │
                    │     CHECK (type IN   │
                    │     ('password',     │
                    │      'credit_card',  │
                    │      'note'))        │
                    └──────────┬───────────┘
                               │
                               │ 1:N (ONE TO MANY)
                               │
        ┌──────────────────────┼──────────────────────┐
        │                      │                      │
        │                      │                      │
┌───────▼─────────┐   ┌────────▼────────┐   ┌────────▼────────┐
│    emails       │   │  credit_cards   │   │     notes       │
├─────────────────┤   ├─────────────────┤   ├─────────────────┤
│ PK: id          │   │ PK: id          │   │ PK: id          │
│ FK: credential_ │   │ FK: credential_ │   │ FK: credential_ │
│     id          │   │     id          │   │     id          │
│     url         │   │     card_number │   │     title       │
│     email       │   │     card_expiry │   │     note        │
│     password    │   │     card_cvv    │   │                 │
│                 │   │     card_holder │   │                 │
│                 │   │     _name       │   │                 │
└─────────────────┘   └─────────────────┘   └─────────────────┘
     ▲                      ▲                      ▲
     │                      │                      │
     │   ON DELETE CASCADE  │                      │
     └──────────────────────┴──────────────────────┘

Foreign Key Constraints:
• emails.credential_id → credential.id (ON DELETE CASCADE)
• credit_cards.credential_id → credential.id (ON DELETE CASCADE)
• notes.credential_id → credential.id (ON DELETE CASCADE)
```

### Complex LEFT JOIN Query

**From**: `CredetialsRepository.java`

```sql
SELECT
    n.id AS note_id,
    n.title,
    n.note,
    n.credential_id AS note_credential_id,

    e.id AS email_id,
    e.email,
    e.url,
    e.password,
    e.credential_id AS email_credential_id,

    c.id AS credit_id,
    c.card_holder_name,
    c.card_number,
    c.card_cvv,
    c.card_expiry,
    c.credential_id AS credit_credential_id,

    cred.id AS credential_id,
    cred.type AS credential_type
FROM credential cred
LEFT JOIN notes n ON n.credential_id = cred.id
LEFT JOIN emails e ON e.credential_id = cred.id
LEFT JOIN credit_cards c ON c.credential_id = cred.id;
```

**Why LEFT JOIN?**
- Retrieves ALL credentials even if child data doesn't exist
- Single query for all three credential types
- Efficient data retrieval

**Runtime Type Detection**:
```java
if ("note".equals(type) && rs.getInt("note_id") != 0) {
    Note note = new Note();
    note.setId(rs.getInt("note_id"));
    note.setTitle(rs.getString("title"));
    note.setNote(rs.getString("note"));
    credentials.add(note);
}
```

### Database Design Benefits

✅ **Referential Integrity**: Foreign keys prevent orphaned records
✅ **Cascade Delete**: Deleting credential auto-deletes child records
✅ **Type Safety**: CHECK constraint ensures valid credential types
✅ **Extensibility**: Easy to add new credential types
✅ **Normalization**: Follows 3NF (Third Normal Form)

### Database Design Issues

❌ **No Encryption**: Credentials stored in **PLAIN TEXT**
❌ **SQL Injection**: Queries use `String.format()` instead of PreparedStatement
❌ **Hardcoded Credentials**: DB password in source code
❌ **No Indexes**: Queries may be slow with large datasets
❌ **No Audit Log**: No tracking of who changed what and when

---

## 4. Complete Class Diagram

### 4.1 Entity Layer (Inheritance Hierarchy)

```
┌────────────────────────────────────────────────────────┐
│              <<abstract>>                               │
│                Entity                                   │
│   (credentials/base/Entity.java)                       │
├────────────────────────────────────────────────────────┤
│ + NOTE: String = "note" {static final}                 │
│ + EMAIL: String = "password" {static final}            │
│ + CREDIT_CARD: String = "credit_card" {static final}   │
│ - id: int                                              │
├────────────────────────────────────────────────────────┤
│ + getId(): int                                         │
│ + setId(int id): void                                  │
│ + render(RenderService renderer): void {abstract}      │
│ + renderMany(RenderService renderer): void {abstract}  │
└────────────────────────────────────────────────────────┘
                         △
                         │ extends
         ┌───────────────┼───────────────┐
         │               │               │
┌────────┴────────┐ ┌────┴────────┐ ┌───┴─────────────┐
│     Email       │ │    Note     │ │   CreditCard    │
│  (entities/)    │ │ (entities/) │ │   (entities/)   │
├─────────────────┤ ├─────────────┤ ├─────────────────┤
│ - url: String   │ │ - title:    │ │ - creditCard    │
│ - email: String │ │   String    │ │   Number: String│
│ - password:     │ │ - note:     │ │ - creditCard    │
│   String        │ │   String    │ │   Expiry: String│
├─────────────────┤ ├─────────────┤ │ - creditCardCVV │
│ + getUrl()      │ │ + getTitle()│ │   : String      │
│ + setUrl()      │ │ + setTitle()│ │ - creditCard    │
│ + getEmail()    │ │ + getNote() │ │   HolderName:   │
│ + setEmail()    │ │ + setNote() │ │   String        │
│ + getPassword() │ ├─────────────┤ ├─────────────────┤
│ + setPassword() │ │ + render()  │ │ + get/set for   │
├─────────────────┤ │ + renderMany│ │   all fields    │
│ + render()      │ │   ()        │ │ + render()      │
│ + renderMany()  │ └─────────────┘ │ + renderMany()  │
└─────────────────┘                 └─────────────────┘
```

### 4.2 Repository Layer & Interface

```
┌────────────────────────────────────────────────────┐
│           <<interface>>                            │
│            ICredential                             │
│   (contracts/ICredential.java)                    │
├────────────────────────────────────────────────────┤
│ + addCredential(Entity): boolean                   │
│ + editCredential(int, Entity): boolean             │
│ + removeCredential(int): boolean                   │
│ + getCredential(int): Entity                       │
│ + getAllCredentials(): ArrayList<Entity>           │
└────────────────────────────────────────────────────┘
                     △
                     │ implements
         ┌───────────┼───────────┬─────────────┐
         │           │           │             │
┌────────┴───────┐ ┌─┴──────────┐ ┌──────────┴─────┐
│Email           │ │Note         │ │CreditCard      │
│Repository      │ │Repository   │ │Repository      │
│(repositories/) │ │(repositories│ │(repositories/) │
├────────────────┤ │/)           │ ├────────────────┤
│- connection:   │ ├─────────────┤ │- connection:   │
│  Connection    │ │- connection:│ │  Connection    │
├────────────────┤ │  Connection │ ├────────────────┤
│+ add           │ ├─────────────┤ │+ add           │
│  Credential()  │ │+ add        │ │  Credential()  │
│+ edit          │ │  Credential │ │+ edit          │
│  Credential()  │ │  ()         │ │  Credential()  │
│+ remove        │ │+ edit       │ │+ remove        │
│  Credential()  │ │  Credential │ │  Credential()  │
│+ get           │ │  ()         │ │+ get           │
│  Credential()  │ │+ remove     │ │  Credential()  │
│+ getAll        │ │  Credential │ │+ getAll        │
│  Credentials() │ │  ()         │ │  Credentials() │
└────────────────┘ │+ get        │ └────────────────┘
                   │  Credential │
                   │  ()         │
                   │+ getAll     │
                   │  Credentials│
                   │  ()         │
                   └─────────────┘

┌────────────────────────────────────────────────────┐
│        CredetialsRepository                        │
│   (repositories/CredetialsRepository.java)        │
│   [Does NOT implement ICredential - Aggregator]   │
├────────────────────────────────────────────────────┤
│ - connection: Connection                           │
├────────────────────────────────────────────────────┤
│ + CredetialsRepository() throws SQLException       │
│ + getAllCredentials(): ArrayList<Entity>           │
│   throws SQLException                              │
│   [Executes complex LEFT JOIN query]              │
└────────────────────────────────────────────────────┘
                     │
                     │ uses
                     ▼
┌────────────────────────────────────────────────────┐
│              DbConnector                           │
│         (utils/DbConnector.java)                  │
│            [SINGLETON PATTERN]                     │
├────────────────────────────────────────────────────┤
│ - connection: Connection {static}                  │
├────────────────────────────────────────────────────┤
│ + getConnection(): Connection {static}             │
│ - createConnection(): void {static}                │
│ + closeConnection(): void {static}                 │
│ + checkConnection(): boolean {static}              │
└────────────────────────────────────────────────────┘
```

### 4.3 Strategy Pattern Layer

```
┌────────────────────────────────────────────────────┐
│         StrategiesFactory                          │
│   (factories/StrategiesFactory.java)              │
│            [FACTORY PATTERN]                       │
├────────────────────────────────────────────────────┤
│ + EmailStrategy(): ICredential {static}            │
│ + CreditCardStrategy(): ICredential {static}       │
│ + NoteStrategy(): ICredential {static}             │
│ + getStrategy(Entity): ICredential {static}        │
└─────────────────┬──────────────────────────────────┘
                  │ creates
      ┌───────────┼───────────┬──────────┐
      │           │           │          │
┌─────▼──────┐ ┌──▼────────┐ ┌▼─────────────┐
│Email       │ │Note        │ │CreditCard    │
│Strategy    │ │Strategy    │ │Strategy      │
│(services/  │ │(services/  │ │(services/    │
│strategies/)│ │strategies/)│ │strategies/)  │
├────────────┤ ├────────────┤ ├──────────────┤
│-emailRepo: │ │-noteRepo:  │ │-cardRepo:    │
│ EmailRepo  │ │ NoteRepo   │ │ CreditCard   │
│ sitory     │ │ sitory     │ │ Repository   │
├────────────┤ ├────────────┤ ├──────────────┤
│implements  │ │implements  │ │implements    │
│ICredential │ │ICredential │ │ICredential   │
├────────────┤ ├────────────┤ ├──────────────┤
│+ add()     │ │+ add()     │ │+ add()       │
│+ edit()    │ │+ edit()    │ │+ edit()      │
│+ remove()  │ │+ remove()  │ │+ remove()    │
│+ get()     │ │+ get()     │ │+ get()       │
│+ getAll()  │ │+ getAll()  │ │+ getAll()    │
│+ check     │ └────────────┘ └──────────────┘
│  Password  │
│  Strength()│
└────────────┘
      │
      │ composition (◆ has-a)
      ▼
┌────────────┐
│Email       │
│Repository  │
└────────────┘
```

### 4.4 Service Layer

```
┌──────────────────────────────────────────┐
│      CredentialsService                  │
│ (services/CredentialsService.java)      │
├──────────────────────────────────────────┤
│ - credetialsRepository:                  │
│   CredetialsRepository                   │
├──────────────────────────────────────────┤
│ + CredentialsService()                   │
│ + getCredentials(): ArrayList<Entity>    │
│   throws SQLException                    │
└──────────────────┬───────────────────────┘
                   │ uses
                   ▼
     ┌─────────────────────────┐
     │ CredetialsRepository    │
     └─────────────────────────┘

┌──────────────────────────────────────────┐
│       DashboardService                   │
│ (dashboard/services/                     │
│  DashboardService.java)                  │
├──────────────────────────────────────────┤
│ - emailsCredentialCount: Label           │
│ - notesCredentialCount: Label            │
│ - creditCardCredentialCount: Label       │
│ - totalCredentialsCount: Label           │
│ - renderService: RenderService           │
│ - credentialsService: CredentialsService │
│ - credentials: ObservableList<Entity>    │
│ - currentSearchQuery: String             │
├──────────────────────────────────────────┤
│ + DashboardService(...)                  │
│ - loadCredentials(): void                │
│ + searchCredentials(String): void        │
│ - getFilteredCredentials(): List<Entity> │
│ - matchesSearchQuery(Entity, String):    │
│   boolean                                │
│ - matchesField(String, String): boolean  │
│ - refreshUI(): void                      │
│ - hasValidData(Entity): boolean          │
│ + initializeDashboard(): void            │
└──────────────────┬───────────────────────┘
                   │ uses
         ┌─────────┴────────┐
         ▼                  ▼
┌─────────────────┐  ┌──────────────────┐
│Credentials      │  │ RenderService    │
│Service          │  │                  │
└─────────────────┘  └──────────────────┘

┌──────────────────────────────────────────┐
│          RenderService                   │
│ (services/RenderService.java)           │
├──────────────────────────────────────────┤
│ - credentialsContainer: VBox             │
│ - credentialContainer: VBox              │
│ - credentialsService: CredentialsService │
│ - dashboardService: DashboardService     │
├──────────────────────────────────────────┤
│ + RenderService(...)                     │
│ + clearCredentialsContainer(): void      │
│ + clearCredentialContainer(): void       │
│ + renderCredentialMany(int, String,      │
│   String, String, Entity): void          │
│ + renderCredentialsOne(Entity): void     │
│ + renderEmailCredential(Email): void     │
│ + renderNoteCredential(Note): void       │
│ + renderCreditCardCredential             │
│   (CreditCard): void                     │
│ - renderButton(String, String,           │
│   Runnable): void                        │
└──────────────────────────────────────────┘
```

### 4.5 Controller Layer

```
┌──────────────────────────────────────────┐
│       DashboardController                │
│ (dashboard/controllers/                  │
│  DashboardController.java)               │
├──────────────────────────────────────────┤
│ + credentialContainer: VBox {@FXML}      │
│ - credentialsContainer: VBox {@FXML}     │
│ - emailsCredentialCount: Label {@FXML}   │
│ - noteCredentialCount: Label {@FXML}     │
│ - creditCardCredentialCount: Label       │
│   {@FXML}                                │
│ - totalCredentialsCount: Label {@FXML}   │
│ - searchTextField: TextField {@FXML}     │
│ - dashboardService: DashboardService     │
├──────────────────────────────────────────┤
│ + initialize(): void {@FXML}             │
│ + handleSearch(): void {@FXML}           │
│ + showCreateScene(ActionEvent): void     │
│   throws IOException                     │
└──────────────────┬───────────────────────┘
                   │ uses
                   ▼
     ┌─────────────────────────┐
     │  DashboardService       │
     └─────────────────────────┘

┌──────────────────────────────────────────┐
│      CredentialController                │
│ (credentials/controllers/                │
│  CredentialController.java)              │
│  implements Initializable                │
├──────────────────────────────────────────┤
│ - credentialTypeComboBox:                │
│   ComboBox<String> {@FXML}               │
│ - formContainer: VBox {@FXML}            │
│ - saveButton: Button {@FXML}             │
│ - urlField, emailField, passwordField... │
├──────────────────────────────────────────┤
│ + initialize(URL, ResourceBundle): void  │
│ + onCredentialTypeChanged(ActionEvent):  │
│   void {@FXML}                           │
│ - loadPasswordForm(): void               │
│ - loadCreditCardForm(): void             │
│ - loadNoteForm(): void                   │
│ + onSave(ActionEvent): void {@FXML}      │
│ - saveEmail(): void                      │
│ - saveCreditCard(): void                 │
│ - saveNote(): void                       │
│ + onCancel(ActionEvent): void {@FXML}    │
│ - closeWindow(): void                    │
└──────────────────────────────────────────┘

┌──────────────────────────────────────────┐
│    AuthenticationController              │
│ (auth/controllers/                       │
│  AuthenticationController.java)          │
├──────────────────────────────────────────┤
│ - authenticationService:                 │
│   AuthenticationService                  │
│ - passwordField: PasswordField {@FXML}   │
│ - feedbackLabel: Label {@FXML}           │
├──────────────────────────────────────────┤
│ + AuthenticationController()             │
│ - handleLoginButtonAction(ActionEvent):  │
│   void {@FXML} throws IOException        │
└──────────────────────────────────────────┘
```

### 4.6 Utility Classes

```
┌──────────────────────────────────────────┐
│         PasswordStorage                  │
│ (auth/passwordHandler/                   │
│  PasswordStorage.java)                   │
│   [SECURITY - PBKDF2 Implementation]     │
├──────────────────────────────────────────┤
│ - PASSWORD_FILE: String = "./master.dat" │
│   {static final}                         │
│ - ITERATIONS: int = 65536 {static final} │
│ - KEY_LENGTH: int = 256 {static final}   │
├──────────────────────────────────────────┤
│ + savePassword(String): void {static}    │
│   throws Exception                       │
│ + verifyPassword(String): boolean        │
│   {static} throws Exception              │
│ - hashPassword(String, byte[]): byte[]   │
│   {static} throws Exception              │
│ + isMasterPasswordSet(): boolean {static}│
└──────────────────────────────────────────┘

┌──────────────────────────────────────────┐
│            Helpers                       │
│ (utils/Helpers.java)                    │
│       [STATIC UTILITY CLASS]             │
├──────────────────────────────────────────┤
│ + switchScene(ActionEvent, String): void │
│   {static} throws IOException            │
│ + labelHandler(Label, String, String,    │
│   boolean): void {static}                │
│ + delayer(int, Runnable): void {static}  │
│ + Logger(String, String): void {static}  │
│ + isStringsEqual(String, String):        │
│   boolean {static}                       │
│ + showAlert(String, String, AlertType):  │
│   void {static}                          │
└──────────────────────────────────────────┘

┌──────────────────────────────────────────┐
│            DbConnector                   │
│ (utils/DbConnector.java)                │
│         [SINGLETON PATTERN]              │
├──────────────────────────────────────────┤
│ - connection: Connection {static}        │
├──────────────────────────────────────────┤
│ + getConnection(): Connection {static}   │
│   throws SQLException                    │
│ - createConnection(): void {static}      │
│   throws SQLException                    │
│ + closeConnection(): void {static}       │
│   throws SQLException                    │
│ + checkConnection(): boolean {static}    │
└──────────────────────────────────────────┘
```

### 4.7 Complete Relationship Diagram

```
┌───────────┐
│   App     │──────► PasswordStorage
│ (Entry)   │         (check master password)
└─────┬─────┘
      │
      ▼
┌─────────────────────┐
│ Authentication      │──► AuthenticationService ──► PasswordStorage
│ Controller          │                               (verify password)
└─────────────────────┘
      │ (on success)
      ▼
┌─────────────────────┐
│  Dashboard          │──┐
│  Controller         │  │
└──────┬──────────────┘  │
       │                 │
       │ creates         │
       ▼                 │
┌─────────────────────┐  │
│ DashboardService    │◄─┘
└──────┬──────┬───────┘
       │      │
       │      └─────► RenderService ──► StrategiesFactory
       │                   │                    │
       │                   │                    ▼
       │                   │          ┌──────────────────┐
       │                   │          │ Email/Note/Card  │
       │                   │          │ Strategy         │
       │                   │          └────┬─────────────┘
       │                   │               │
       │                   │               ▼
       │                   │     ┌──────────────────────┐
       │                   │     │ Email/Note/Card      │
       │                   │     │ Repository           │
       │                   │     └──────┬───────────────┘
       │                   │            │
       ▼                   ▼            ▼
┌────────────────┐  ┌──────────────────────────┐
│  Credentials   │  │      DbConnector         │
│  Service       │  │      (Singleton)         │
└────────┬───────┘  └──────────┬───────────────┘
         │                     │
         ▼                     ▼
┌──────────────────┐   ┌───────────────┐
│ Credetials       │   │ Oracle DB     │
│ Repository       │───│ (4 tables)    │
│ (Aggregator)     │   └───────────────┘
└──────────────────┘

Legend:
──► : Dependency (uses)
◄── : Dependency (used by)
◆── : Composition (has-a, strong)
◇── : Aggregation (has-a, weak)
△   : Inheritance (is-a)
```

---

## 5. Design Patterns

### Pattern 1: MVC with Service Layer

**Intent**: Separate presentation logic (View) from business logic (Model) via Controller and Service layers

**Implementation**:
- **Model**: Entity classes (Email, Note, CreditCard)
- **View**: FXML files
- **Controller**: JavaFX controllers
- **Service**: Business logic layer (DashboardService, CredentialsService)

**Code Example**:
```java
// Model
public class Email extends Entity {
    private String url, email, password;
}

// Controller
public class DashboardController {
    @FXML private VBox credentialsContainer;

    @FXML
    public void initialize() {
        DashboardService service = new DashboardService(...);
        service.initializeDashboard();
    }
}

// Service
public class DashboardService {
    public void initializeDashboard() {
        List<Entity> credentials = credentialsService.getCredentials();
        for (Entity cred : credentials) {
            cred.renderMany(renderService);
        }
    }
}
```

**Benefits**: Testability, Maintainability, Separation of Concerns

---

### Pattern 2: Repository Pattern

**Intent**: Abstract data access logic from business logic

**Implementation**:
```java
// Interface
public interface ICredential {
    boolean addCredential(Entity credential);
    boolean editCredential(int id, Entity credential);
    boolean removeCredential(int id);
    Entity getCredential(int id);
    ArrayList<Entity> getAllCredentials();
}

// Implementation
public class EmailRepository implements ICredential {
    private Connection connection;

    @Override
    public boolean addCredential(Entity credential) {
        Email email = (Email) credential;
        String sql = String.format(
            "INSERT INTO emails (url, email, password, credential_id) " +
            "VALUES ('%s', '%s', '%s', %d)",
            email.getUrl(), email.getEmail(),
            email.getPassword(), 2
        );
        // Execute SQL...
    }
}
```

**Benefits**: Centralized data access, Testable (can mock), Easy to swap databases

---

### Pattern 3: Strategy Pattern

**Intent**: Select algorithm (repository) at runtime based on credential type

**Implementation**:
```java
// Factory creates strategies
public class StrategiesFactory {
    public static ICredential getStrategy(Entity credential) {
        if (credential instanceof Email) {
            return EmailStrategy();
        } else if (credential instanceof CreditCard) {
            return CreditCardStrategy();
        } else if (credential instanceof Note) {
            return NoteStrategy();
        }
        return null;
    }

    public static ICredential EmailStrategy() {
        return new EmailStrategy(new EmailRepository());
    }
}

// Strategy wraps repository
public class EmailStrategy implements ICredential {
    private EmailRepository emailRepository;

    public EmailStrategy(EmailRepository repo) {
        this.emailRepository = repo;
    }

    @Override
    public boolean addCredential(Entity credential) {
        return emailRepository.addCredential(credential);
    }
}
```

**Benefits**: Runtime algorithm selection, Easy to add new types, Encapsulates type-specific logic

---

### Pattern 4: Factory Pattern

**Intent**: Create objects without specifying exact class

**Implementation**:
```java
public class StrategiesFactory {
    // Factory methods
    public static ICredential EmailStrategy() {
        return new EmailStrategy(new EmailRepository());
    }

    public static ICredential NoteStrategy() {
        return new NoteStrategy(new NoteRepository());
    }

    public static ICredential CreditCardStrategy() {
        return new CreditCardStrategy(new CreditCardRepository());
    }

    // Runtime selection
    public static ICredential getStrategy(Entity credential) {
        if (credential instanceof Email) return EmailStrategy();
        if (credential instanceof Note) return NoteStrategy();
        if (credential instanceof CreditCard) return CreditCardStrategy();
        return null;
    }
}

// Usage
ICredential strategy = StrategiesFactory.getStrategy(credential);
strategy.addCredential(credential);
```

**Benefits**: Centralized object creation, Loose coupling, Dependency injection

---

### Pattern 5: Template Method Pattern

**Intent**: Define skeleton of algorithm in base class, let subclasses implement steps

**Implementation**:
```java
// Abstract base class
public abstract class Entity {
    private int id;

    // Template methods (must be implemented by subclasses)
    public abstract void render(RenderService renderer);
    public abstract void renderMany(RenderService renderer);
}

// Concrete implementation
public class Email extends Entity {
    @Override
    public void render(RenderService renderer) {
        renderer.renderEmailCredential(this);
    }

    @Override
    public void renderMany(RenderService renderer) {
        renderer.renderCredentialMany(
            this.getId(),
            this.getEmail(),  // Title
            this.getPassword(),  // Subtitle
            "[Email]",  // Icon
            this
        );
    }
}
```

**Polymorphic Usage**:
```java
List<Entity> credentials = ...;
for (Entity credential : credentials) {
    credential.renderMany(renderService);  // Polymorphic call
}
```

**Benefits**: Code reuse, Consistent interface, Polymorphic behavior

---

### Pattern 6: Singleton Pattern

**Intent**: Ensure only one instance of a class exists

**Implementation**:
```java
public class DbConnector {
    private static Connection connection = null;

    // Private constructor prevents instantiation
    private DbConnector() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            createConnection();
        }
        return connection;
    }

    private static void createConnection() throws SQLException {
        connection = DriverManager.getConnection(
            "jdbc:oracle:thin:@//192.168.1.8:1521/xe",
            "system",
            "123456"
        );
    }
}

// Usage
Connection conn = DbConnector.getConnection();
```

**Benefits**: Single connection instance, Resource optimization, Global access point

**Also Used**: RenderService (one instance per dashboard)

---

## 6. Security Implementation

### Master Password Protection

**Algorithm**: PBKDF2WithHmacSHA256

**Security Parameters**:
```java
private static final String PASSWORD_FILE = "./master.dat";
private static final int ITERATIONS = 65536;  // OWASP recommended
private static final int KEY_LENGTH = 256;    // 256 bits
```

**Password Hashing**:
```java
public static void savePassword(String password) throws Exception {
    // 1. Generate random salt
    byte[] salt = new byte[16];
    SecureRandom random = new SecureRandom();
    random.nextBytes(salt);

    // 2. Hash password with salt
    byte[] hash = hashPassword(password, salt);

    // 3. Save to file: [16 bytes salt][32 bytes hash]
    try (FileOutputStream fos = new FileOutputStream(PASSWORD_FILE)) {
        fos.write(salt);  // 16 bytes
        fos.write(hash);  // 32 bytes
    }
}

private static byte[] hashPassword(String password, byte[] salt)
        throws Exception {
    PBEKeySpec spec = new PBEKeySpec(
        password.toCharArray(),
        salt,
        ITERATIONS,  // 65,536 iterations
        KEY_LENGTH   // 256 bits
    );
    SecretKeyFactory factory = SecretKeyFactory.getInstance(
        "PBKDF2WithHmacSHA256"
    );
    return factory.generateSecret(spec).getEncoded();
}
```

**Password Verification**:
```java
public static boolean verifyPassword(String password) throws Exception {
    // 1. Read stored salt and hash
    try (FileInputStream fis = new FileInputStream(PASSWORD_FILE)) {
        byte[] salt = new byte[16];
        byte[] storedHash = new byte[32];

        fis.read(salt);
        fis.read(storedHash);

        // 2. Hash provided password with stored salt
        byte[] computedHash = hashPassword(password, salt);

        // 3. Constant-time comparison (prevents timing attacks)
        return Arrays.equals(storedHash, computedHash);
    }
}
```

### Security Strengths

✅ **Strong Hashing**: PBKDF2 is industry-standard (NIST, OWASP recommended)
✅ **High Iterations**: 65,536 iterations slow down brute-force attacks
✅ **Random Salt**: Unique salt per password prevents rainbow table attacks
✅ **256-bit Key**: Strong cryptographic strength
✅ **One-Way**: Cannot reverse hash to get password
✅ **Constant-Time Comparison**: `Arrays.equals()` prevents timing attacks

### Critical Security Vulnerabilities

#### ❌ 1. SQL Injection (CRITICAL)

**Problem**: All repositories use `String.format()` for SQL queries

**Vulnerable Code** (EmailRepository.java):
```java
String sql = String.format(
    "INSERT INTO emails (url, email, password, credential_id) " +
    "VALUES ('%s', '%s', '%s', %d)",
    email.getUrl(),
    email.getEmail(),
    email.getPassword(),
    2
);
```

**Attack Example**:
```
URL: test.com
Email: admin@test.com
Password: '; DROP TABLE emails; --
```

**Fix**: Use PreparedStatement
```java
String sql = "INSERT INTO emails (url, email, password, credential_id) " +
             "VALUES (?, ?, ?, ?)";
PreparedStatement stmt = connection.prepareStatement(sql);
stmt.setString(1, email.getUrl());
stmt.setString(2, email.getEmail());
stmt.setString(3, email.getPassword());
stmt.setInt(4, 2);
stmt.executeUpdate();
```

#### ❌ 2. No Credential Encryption (CRITICAL)

**Problem**: All credentials stored in **PLAIN TEXT** in database

**Current Database State**:
```sql
SELECT email, password FROM emails WHERE id = 1;
-- Returns: "user@gmail.com", "MyPassword123" (plain text!)
```

**Fix**: Implement AES-256 encryption
```java
// Derive encryption key from master password
byte[] encryptionKey = deriveKeyFromMasterPassword();

// Encrypt before storing
String encryptedPassword = AES256.encrypt(password, encryptionKey);
repository.save(email, encryptedPassword);

// Decrypt when retrieving
String decryptedPassword = AES256.decrypt(encryptedPassword, encryptionKey);
```

#### ❌ 3. Hardcoded Database Credentials (HIGH)

**Problem** (DbConnector.java:25-27):
```java
connection = DriverManager.getConnection(
    "jdbc:oracle:thin:@//192.168.1.8:1521/xe",
    "system",    // ← Hardcoded username
    "123456"     // ← Weak password in source code
);
```

**Fix**: Environment variables
```java
String url = System.getenv("DB_URL");
String user = System.getenv("DB_USER");
String password = System.getenv("DB_PASSWORD");
connection = DriverManager.getConnection(url, user, password);
```

#### ❌ 4. No Input Validation (HIGH)

**Missing Validations**:
- Email format (regex validation)
- Credit card number (Luhn algorithm)
- Expiry date format
- CVV length (3-4 digits)
- URL format
- Password strength

**Fix Example**:
```java
public boolean isValidEmail(String email) {
    String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    return email.matches(regex);
}

public boolean isValidCardNumber(String cardNumber) {
    // Luhn algorithm implementation
    return luhnCheck(cardNumber);
}
```

#### ❌ 5. Information Disclosure (MEDIUM)

**Problem**: Sensitive data in console logs
```java
Helpers.Logger("Password: " + password, "DEBUG");
Helpers.Logger("Search query: " + searchQuery, "INFO");
```

**Fix**: Never log sensitive data, use proper logging framework
```java
Logger.info("Credential operation completed");  // No sensitive data
```

---

## 7. Key Features & CRUD Operations

### Feature 1: Master Password Authentication

**First-Time Setup Flow**:
```
1. User launches app
2. App checks if master.dat exists
3. If NO → Show setPassword_view.fxml
   - User enters password twice
   - Passwords must match
   - PBKDF2 hash generated with random salt
   - Salt + hash saved to master.dat
   - Navigate to authentication_view.fxml
```

**Login Flow**:
```
1. App shows authentication_view.fxml
2. User enters master password
3. System:
   - Loads salt from master.dat
   - Hashes input password with stored salt
   - Compares hashes
4. If match → Navigate to dashboard_view.fxml
5. If no match → Show error message
```

### Feature 2: CRUD Operations

#### CREATE (Add Credential)

**Flow**:
```
User → Click "+" button → CredentialController
     → Select type from ComboBox
     → Form dynamically changes
     → Fill fields → Click "Save"
     → StrategiesFactory.getStrategy(credential)
     → Strategy.addCredential(credential)
     → Repository executes INSERT query
     → Alert: "Credential added successfully"
     → Close form → Dashboard refreshes
```

**Code**:
```java
// Dynamic form loading
private void onCredentialTypeChanged(ActionEvent event) {
    String selected = credentialTypeComboBox.getValue();
    if ("Password".equals(selected)) {
        loadPasswordForm();  // URL, Email, Password fields
    } else if ("CreditCard".equals(selected)) {
        loadCreditCardForm();  // Card details
    } else if ("Note".equals(selected)) {
        loadNoteForm();  // Title, Text area
    }
}

// Save
private void saveEmail() {
    Email email = new Email();
    email.setUrl(urlField.getText());
    email.setEmail(emailField.getText());
    email.setPassword(passwordField.getText());

    ICredential strategy = StrategiesFactory.EmailStrategy();
    boolean success = strategy.addCredential(email);

    if (success) {
        Helpers.showAlert("Success", "Email credential added",
                         Alert.AlertType.INFORMATION);
    }
}
```

#### READ (View Credentials)

**List View**:
```java
// DashboardService loads all credentials
public void initializeDashboard() {
    List<Entity> credentials = credentialsService.getCredentials();

    // Filter valid credentials
    List<Entity> validCredentials = credentials.stream()
        .filter(this::hasValidData)
        .toList();

    // Render each credential
    for (Entity credential : validCredentials) {
        credential.renderMany(renderService);  // Polymorphic call
    }
}
```

**Detail View** (click on credential):
```java
// RenderService.renderCredentialMany()
item.setOnMouseClicked(e -> {
    for (Entity cred : credentialsService.getCredentials()) {
        if (cred.getId() == id &&
            cred.getClass().equals(type.getClass())) {
            renderCredentialsOne(cred);  // Show in right panel
            break;
        }
    }
});

// Polymorphic rendering
public void renderCredentialsOne(Entity credential) {
    credentialContainer.getChildren().clear();
    credential.render(this);  // Calls Email/Note/CreditCard.render()
}
```

#### UPDATE (Edit Credential)

**Flow**:
```
User → Click credential → Detail view shows
     → Fields are editable TextFields
     → User modifies values → Click "Update"
     → Strategy.editCredential(id, updatedCredential)
     → Repository executes UPDATE query
     → Dashboard refreshes
```

**Code**:
```java
// RenderService creates editable fields
TextField emailField = new TextField(email.getEmail());
emailField.setEditable(true);

Button updateBtn = new Button("Update");
updateBtn.setOnAction(e -> {
    // Get updated values
    email.setEmail(emailField.getText());
    email.setUrl(urlField.getText());
    email.setPassword(passwordField.getText());

    // Update in database
    ICredential strategy = StrategiesFactory.getStrategy(email);
    boolean success = strategy.editCredential(email.getId(), email);

    if (success) {
        dashboardService.initializeDashboard();  // Refresh
    }
});
```

#### DELETE (Remove Credential)

**Flow**:
```
User → Click credential → Detail view shows
     → Click "Delete" button
     → Strategy.removeCredential(id)
     → Repository executes DELETE query
     → Foreign key CASCADE deletes child record
     → Dashboard refreshes (credential disappears)
```

**Code**:
```java
Button deleteBtn = new Button("Delete");
deleteBtn.setOnAction(e -> {
    ICredential strategy = StrategiesFactory.getStrategy(credential);
    boolean success = strategy.removeCredential(credential.getId());

    if (success) {
        Helpers.showAlert("Deleted", "Credential removed",
                         Alert.AlertType.INFORMATION);
        dashboardService.initializeDashboard();
    }
});
```

### Feature 3: Search Functionality

**Implementation**:
```java
// DashboardService
private String currentSearchQuery = "";

public void searchCredentials(String searchQuery) {
    this.currentSearchQuery = searchQuery;
    refreshUI();  // Re-render with filtered credentials
}

private List<Entity> getFilteredCredentials() {
    String query = currentSearchQuery.trim().toLowerCase();

    if (query.isEmpty()) {
        return new ArrayList<>(credentials);  // Show all
    }

    List<Entity> filtered = new ArrayList<>();
    for (Entity credential : credentials) {
        if (matchesSearchQuery(credential, query)) {
            filtered.add(credential);
        }
    }
    return filtered;
}

private boolean matchesSearchQuery(Entity credential, String query) {
    if (credential instanceof Email email) {
        return matchesField(email.getUrl(), query) ||
               matchesField(email.getEmail(), query) ||
               matchesField(email.getPassword(), query);
    } else if (credential instanceof Note note) {
        return matchesField(note.getTitle(), query) ||
               matchesField(note.getNote(), query);
    } else if (credential instanceof CreditCard card) {
        return matchesField(card.getCreditCardHolderName(), query) ||
               matchesField(card.getCreditCardNumber(), query) ||
               matchesField(card.getCreditCardExpiry(), query) ||
               matchesField(card.getCreditCardCVV(), query);
    }
    return false;
}

private boolean matchesField(String fieldValue, String query) {
    return fieldValue != null &&
           fieldValue.toLowerCase().contains(query);
}
```

**Features**:
- Case-insensitive search
- Multi-field matching (searches ALL fields)
- Button-triggered (not real-time)
- Updates credential counts dynamically

### Feature 4: Password Visibility Toggle

**Implementation**:
```java
// Create both PasswordField (masked) and TextField (visible)
PasswordField passwordField = new PasswordField();
TextField textField = new TextField();

passwordField.setText(password);
passwordField.setVisible(true);
textField.setVisible(false);

// Click to toggle
passwordField.setOnMouseClicked(e -> {
    textField.setText(passwordField.getText());
    passwordField.setVisible(false);
    textField.setVisible(true);
});

textField.setOnMouseClicked(e -> {
    passwordField.setText(textField.getText());
    textField.setVisible(false);
    passwordField.setVisible(true);
});
```

**Also Used For**: CVV codes in credit cards

---

## 8. User Interface

### Three-Panel Dashboard Layout

```
┌──────────────────────────────────────────────────────────────────────┐
│                         Dashboard (BorderPane)                       │
├────────────────┬────────────────────────────────┬────────────────────┤
│                │                                │                    │
│ LEFT SIDEBAR   │     CENTER PANEL               │   RIGHT PANEL      │
│ (220px fixed)  │     (Flexible width)           │   (350px fixed)    │
│ #252525        │     #1e1e1e                    │   #2d2d2d          │
│                │                                │                    │
│ ┌────────────┐ │ ┌────────────────────────────┐ │ ┌────────────────┐ │
│ │🔑 All      │ │ │ All              [+]       │ │ │                │ │
│ │Count: 15   │ │ ├────────────────────────────┤ │ │   No Item      │ │
│ ├────────────┤ │ │ [Search] [Search Button]   │ │ │   Selected     │ │
│ │Email       │ │ └────────────────────────────┘ │ │                │ │
│ │Count: 6    │ │ ┌────────────────────────────┐ │ │     🔑         │ │
│ ├────────────┤ │ │ScrollPane (fitToWidth)     │ │ │                │ │
│ │Cards       │ │ │                            │ │ └────────────────┘ │
│ │Count: 5    │ │ │ [Email] example.com        │ │                    │
│ ├────────────┤ │ │ user@email.com             │ │ (After click)      │
│ │Notes       │ │ │ ─────────────────────       │ │ ┌────────────────┐ │
│ │Count: 4    │ │ │ [card] John Doe            │ │ │URL: _________  │ │
│ └────────────┘ │ │ **** **** **** 1234        │ │ │Email: ________ │ │
│                │ │ ─────────────────────       │ │ │Pass: ******    │ │
│ Shared Groups  │ │ [Note] Meeting Notes       │ │ │                │ │
│ (disabled)     │ │ Project discussion...      │ │ │ [Update]       │ │
│                │ │                            │ │ │ [Delete]       │ │
│                │ └────────────────────────────┘ │ └────────────────┘ │
└────────────────┴────────────────────────────────┴────────────────────┘
```

### Color Scheme (Dark Theme)

| Element | Hex Code | Usage |
|---------|----------|-------|
| **Main Background** | `#2d2d2d` | Dashboard outer background, right panel |
| **Sidebar** | `#252525` | Left sidebar, credential list items |
| **Panels** | `#1e1e1e` | Center panel, input field backgrounds |
| **Accent Blue** | `#0066cc` | Selected category, borders, buttons, icons |
| **Text White** | `#ffffff` | Primary labels, headings |
| **Text Light Gray** | `#bbbbbb` | "No Item Selected" message |
| **Text Light Blue** | `#b3d9ff` | Active category counts |
| **Text Muted** | `#888888` | Unselected counts, subtitles |
| **Text Prompt** | `#666666` | Search field placeholder |

### Visual Elements

**Credential Type Icons**:
- 🔗 **[Email]** - Blue text indicator for email/password credentials
- 💳 **[card]** - Blue text indicator for credit cards
- 📝 **[Note]** - Blue text indicator for secure notes
- 🔑 **Lock icon** - Empty state indicator

**Interactive Elements**:
- Hover effect: `cursor: hand` (pointer cursor)
- Active category: Blue background (#0066cc)
- Inactive categories: Gray background (#333333)
- Separators between list items

### FXML Views

#### 1. authentication_view.fxml (Login)
```xml
<VBox>
    <PasswordField fx:id="passwordField" promptText="Enter Master Password"/>
    <Button text="Login" onAction="#handleLoginButtonAction"/>
    <Label fx:id="feedbackLabel" textFill="red"/>
</VBox>
```

#### 2. setPassword_view.fxml (Setup)
```xml
<VBox>
    <TextField fx:id="password" promptText="Create Master Password"/>
    <TextField fx:id="retypedPassword" promptText="Re-type Password"/>
    <Button fx:id="setPasswordBtn" text="Set Password" onAction="#savePassword"/>
    <Label fx:id="feedbackLabel"/>
</VBox>
```

#### 3. dashboard_view.fxml (Main UI - 3 panels)
- **Left**: Category sidebar with VBox containing HBox buttons
- **Center**: BorderPane with search bar (top) and ScrollPane (center)
- **Right**: VBox for credential details (initially shows empty state)

#### 4. addCredential_view.fxml (Dynamic Form)
```xml
<VBox>
    <ComboBox fx:id="credentialTypeComboBox"
              onAction="#onCredentialTypeChanged">
        <items>
            <String>Password</String>
            <String>CreditCard</String>
            <String>Note</String>
        </items>
    </ComboBox>
    <VBox fx:id="formContainer"/>  <!-- Dynamically populated -->
    <Button fx:id="saveButton" text="Save" onAction="#onSave"/>
</VBox>
```

### UI Rendering Pattern

**List Item Rendering** (`renderCredentialMany()`):
```java
HBox item = new HBox();
item.setStyle("-fx-background-color: #252525; -fx-padding: 12; -fx-cursor: hand;");
item.setId(String.valueOf(id));

Label icon = new Label(iconText);  // [Email], [card], [Note]
icon.setStyle("-fx-text-fill: #0066cc; -fx-font-size: 14px;");

VBox textBox = new VBox(2);
Label titleLabel = new Label(title);  // Email address or title
titleLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

Label subtitleLabel = new Label(subtitle);  // Password or note preview
subtitleLabel.setStyle("-fx-text-fill: #888888;");

textBox.getChildren().addAll(titleLabel, subtitleLabel);
item.getChildren().addAll(icon, textBox);
credentialsContainer.getChildren().add(item);
credentialsContainer.getChildren().add(new Separator());
```

---

## 9. Application Flow

### Complete Startup Sequence

```
┌─────────────────────────────────────────────────────┐
│ 1. main(String[] args)                              │
│    Application.launch(App.class, args)              │
└────────────────────┬────────────────────────────────┘
                     │
                     ↓
┌─────────────────────────────────────────────────────┐
│ 2. App.start(Stage stage)                           │
│    ├─ DbConnector.checkConnection()                 │
│    │  └─ Try connecting to Oracle DB                │
│    │     ├─ SUCCESS → Continue                      │
│    │     └─ FAIL → Alert + exit(1)                  │
│    │                                                 │
│    ├─ CredentialSeeder.seedCredentialTypes()        │
│    │  └─ Insert 'password', 'credit_card', 'note'   │
│    │     into credential table (if empty)           │
│    │                                                 │
│    └─ PasswordStorage.isMasterPasswordSet()         │
│       └─ Check if ./master.dat exists               │
└────────────┬──────────────────┬─────────────────────┘
             │ NO               │ YES
             ↓                  ↓
┌──────────────────────┐  ┌─────────────────────────┐
│ setPassword_view.fxml│  │ authentication_view.fxml│
│                      │  │                         │
│ ┌──────────────────┐ │  │ ┌─────────────────────┐ │
│ │ Create Master    │ │  │ │ Enter Master        │ │
│ │ Password         │ │  │ │ Password            │ │
│ │                  │ │  │ │                     │ │
│ │ [Password]       │ │  │ │ [Password]          │ │
│ │ [Re-type]        │ │  │ │                     │ │
│ │                  │ │  │ │ [Login Button]      │ │
│ │ [Set Password]   │ │  │ └─────────────────────┘ │
│ └──────────────────┘ │  │                         │
│                      │  │ AuthenticationController│
│ SetPasswordController│  │ .handleLoginButtonAction│
│ .savePassword()      │  │ ()                      │
│   ↓                  │  │   ↓                     │
│ PasswordStorage      │  │ AuthenticationService   │
│ .savePassword()      │  │ .login()                │
│   ↓                  │  │   ↓                     │
│ Generate random salt │  │ PasswordStorage         │
│ Hash password        │  │ .verifyPassword()       │
│ Save to master.dat   │  │   ↓                     │
│   ↓                  │  │ Load salt from file     │
│ Navigate to login    │  │ Hash input + salt       │
└──────────┬───────────┘  │ Compare hashes          │
           │              │   ↓                     │
           │              │ If match → Success      │
           │              │ If not → Error          │
           └──────────────┴─────────┬───────────────┘
                                    │ SUCCESS
                                    ↓
┌──────────────────────────────────────────────────────┐
│ dashboard_view.fxml                                  │
│                                                      │
│ DashboardController.initialize()                     │
│   ├─ Create CredentialsService                      │
│   ├─ Create DashboardService (pass UI components)   │
│   └─ DashboardService.initializeDashboard()         │
│       ├─ loadCredentials()                          │
│       │  └─ CredentialsService.getCredentials()     │
│       │     └─ CredetialsRepository                 │
│       │        .getAllCredentials()                 │
│       │        └─ Execute LEFT JOIN query           │
│       │           └─ Map ResultSet to entities      │
│       │              ├─ note → Note entity          │
│       │              ├─ password → Email entity     │
│       │              └─ credit_card → CreditCard    │
│       │                                             │
│       └─ refreshUI()                                │
│          ├─ getFilteredCredentials()                │
│          ├─ Filter valid credentials (hasValidData) │
│          ├─ For each credential:                    │
│          │  └─ credential.renderMany(renderService) │
│          │     └─ RenderService                     │
│          │        .renderCredentialMany()           │
│          │        └─ Create HBox with icon,         │
│          │           title, subtitle                │
│          │        └─ Add to credentialsContainer    │
│          │                                           │
│          └─ Update count labels                     │
│             ├─ Email count                          │
│             ├─ Note count                           │
│             ├─ Card count                           │
│             └─ Total count                          │
└──────────────────────────────────────────────────────┘
```

### User Interaction Flows

**Add Credential Flow**:
```
User clicks "+" button
  ↓
Show addCredential_view.fxml
  ↓
User selects type from ComboBox
  ↓
CredentialController.onCredentialTypeChanged()
  ├─ "Password" → loadPasswordForm()
  ├─ "CreditCard" → loadCreditCardForm()
  └─ "Note" → loadNoteForm()
  ↓
User fills fields → Clicks "Save"
  ↓
onSave() → saveEmail() / saveCreditCard() / saveNote()
  ↓
StrategiesFactory.getStrategy(credential)
  ↓
Strategy.addCredential(credential)
  ↓
Repository INSERT query → Database
  ↓
Success → Alert → Close window → Dashboard refreshes
```

**Search Flow**:
```
User types in search field → Clicks "Search" button
  ↓
DashboardController.handleSearch()
  ├─ Get searchTextField.getText()
  └─ DashboardService.searchCredentials(query)
      ├─ Store currentSearchQuery = query
      └─ refreshUI()
          └─ getFilteredCredentials()
              ├─ If query empty → Return all credentials
              └─ For each credential
                  └─ matchesSearchQuery(credential, query)
                      ├─ Email: Check url, email, password
                      ├─ Note: Check title, note
                      └─ Card: Check holder, number, expiry, CVV
                  ↓
              Return filtered list
                  ↓
          Render filtered credentials
          Update counts (shows filtered counts)
```

---

## 10. Build & Deployment

### Maven Configuration (pom.xml)

**Properties**:
```xml
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>21</maven.compiler.source>
    <maven.compiler.target>21</maven.compiler.target>
</properties>
```

**Dependencies**:
```xml
<dependencies>
    <!-- JavaFX -->
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>23.0.1</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-fxml</artifactId>
        <version>23.0.1</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-graphics</artifactId>
        <version>23.0.1</version>
    </dependency>

    <!-- Oracle JDBC -->
    <dependency>
        <groupId>com.oracle.database.jdbc</groupId>
        <artifactId>ojdbc11</artifactId>
        <version>23.5.0.0</version>
    </dependency>
</dependencies>
```

**Plugins**:
```xml
<plugins>
    <!-- Compiler Plugin -->
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.13.0</version>
        <configuration>
            <release>21</release>
        </configuration>
    </plugin>

    <!-- JavaFX Plugin -->
    <plugin>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-maven-plugin</artifactId>
        <version>0.0.8</version>
        <configuration>
            <mainClass>com.password.manager.App</mainClass>
        </configuration>
    </plugin>

    <!-- Shade Plugin (Create Uber JAR) -->
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-shade-plugin</artifactId>
        <version>3.5.0</version>
        <executions>
            <execution>
                <phase>package</phase>
                <goals>
                    <goal>shade</goal>
                </goals>
                <configuration>
                    <finalName>passowrdmanager-${project.version}-${build.profile.id}</finalName>
                    <transformers>
                        <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                            <mainClass>com.password.manager.App</mainClass>
                        </transformer>
                    </transformers>
                </configuration>
            </execution>
        </executions>
    </plugin>
</plugins>
```

**Platform-Specific Profiles**:
```xml
<profiles>
    <profile>
        <id>windows</id>
        <properties>
            <build.profile.id>windows</build.profile.id>
        </properties>
        <dependencies>
            <dependency>
                <groupId>org.openjfx</groupId>
                <artifactId>javafx-graphics</artifactId>
                <version>23.0.1</version>
                <classifier>win</classifier>
            </dependency>
        </dependencies>
    </profile>

    <profile>
        <id>mac</id>
        <properties>
            <build.profile.id>mac</build.profile.id>
        </properties>
        <dependencies>
            <dependency>
                <groupId>org.openjfx</groupId>
                <artifactId>javafx-graphics</artifactId>
                <version>23.0.1</version>
                <classifier>mac</classifier>
            </dependency>
        </dependencies>
    </profile>
</profiles>
```

### Build Commands

**Development**:
```bash
# Run application directly
mvn clean javafx:run

# Compile only (check for errors)
mvn compile

# Clean and compile
mvn clean compile
```

**Production Builds**:
```bash
# Build Windows JAR
mvn clean package -P windows
# Output: target/passowrdmanager-1.0-SNAPSHOT-windows.jar

# Build macOS JAR
mvn clean package -P mac
# Output: target/passowrdmanager-1.0-SNAPSHOT-mac.jar
```

### Run Scripts

**Windows (run.bat)**:
```batch
@echo off
echo Starting Password Manager...
java --module-path "path\to\javafx-sdk\lib" --add-modules javafx.controls,javafx.fxml -jar target\passowrdmanager-1.0-SNAPSHOT-windows.jar
pause
```

**macOS/Linux (run.sh)**:
```bash
#!/bin/bash
echo "Starting Password Manager..."
java --module-path "/path/to/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml -jar target/passowrdmanager-1.0-SNAPSHOT-mac.jar
```

### Deployment Checklist

✅ **Java 21 JDK** installed
✅ **Maven 3.6+** installed
✅ **Oracle Database XE** running at 192.168.1.8:1521
✅ **Database schema** created (run SQL from readme.md)
✅ **Network access** to database
✅ **Platform-specific build** created
✅ **Run script** configured with correct paths

### First-Time Setup

1. **Install Prerequisites**:
   ```bash
   # Check Java version
   java -version  # Should be 21+

   # Check Maven
   mvn -version
   ```

2. **Configure Database**:
   ```sql
   -- Connect to Oracle as system
   sqlplus system/123456@//192.168.1.8:1521/xe

   -- Run schema from readme.md
   @schema.sql
   ```

3. **Build Project**:
   ```bash
   cd passowrdmanager
   mvn clean package -P [windows|mac]
   ```

4. **Run Application**:
   ```bash
   ./run.sh  # macOS/Linux
   # OR
   run.bat   # Windows
   ```

5. **First Launch**:
   - Set master password
   - Start adding credentials

---

## 11. Challenges & Solutions

### Challenge 1: Database Integration

**Problem**: Migrating from in-memory storage to Oracle Database persistence

**Complexities**:
- Three different credential types → three tables
- Need to retrieve all types efficiently
- Maintain referential integrity
- Handle type discrimination at runtime

**Solution**:
1. **Parent-Child Schema**: credential table (parent) with foreign keys to child tables
2. **Complex LEFT JOIN Query**: Single query retrieves all credential types
3. **Runtime Type Detection**: Map credential_type to appropriate entity class
4. **Factory Pattern**: Create correct entity instances based on type

**Code**:
```java
// Single query for all types
String query = "SELECT ... FROM credential cred " +
               "LEFT JOIN notes n ON n.credential_id = cred.id " +
               "LEFT JOIN emails e ON e.credential_id = cred.id " +
               "LEFT JOIN credit_cards c ON c.credential_id = cred.id";

// Runtime type mapping
while (rs.next()) {
    String type = rs.getString("credential_type");
    if ("note".equals(type) && rs.getInt("note_id") != 0) {
        Note note = new Note();
        // Set properties...
        credentials.add(note);
    }
}
```

**Benefits**:
- Single query instead of three
- Automatic type discrimination
- Referential integrity enforced

---

### Challenge 2: Dynamic UI Forms

**Problem**: Different credential types need different input fields without page reload

**Requirements**:
- Email: URL, email, password fields
- Credit Card: Card number, holder name, expiry, CVV fields
- Note: Title and text area

**Solution**:
1. **ComboBox Selection**: User selects credential type
2. **Dynamic Form Generation**: Programmatically create fields in Java
3. **Type-Safe Entity Creation**: Use appropriate entity class

**Code**:
```java
private void onCredentialTypeChanged(ActionEvent event) {
    formContainer.getChildren().clear();  // Clear old form

    String selected = credentialTypeComboBox.getValue();
    if ("Password".equals(selected)) {
        loadPasswordForm();
    } else if ("CreditCard".equals(selected)) {
        loadCreditCardForm();
    } else if ("Note".equals(selected)) {
        loadNoteForm();
    }
}

private void loadPasswordForm() {
    urlField = new TextField();
    urlField.setPromptText("URL");

    emailField = new TextField();
    emailField.setPromptText("Email");

    passwordField = new PasswordField();
    passwordField.setPromptText("Password");

    formContainer.getChildren().addAll(
        new Label("URL:"), urlField,
        new Label("Email:"), emailField,
        new Label("Password:"), passwordField
    );
}
```

**Benefits**:
- No page reload needed
- Type-safe forms
- Consistent styling via programmatic creation

---

### Challenge 3: Cross-Platform JavaFX Build

**Problem**: JavaFX requires platform-specific native libraries

**Complications**:
- Windows needs win classifier
- macOS needs mac classifier
- Can't bundle both in same JAR

**Solution**:
1. **Maven Profiles**: Separate profiles for Windows and macOS
2. **Classifier Property**: Profile sets build.profile.id
3. **Platform-Specific Dependencies**: Include correct JavaFX graphics library

**Code** (pom.xml):
```xml
<profiles>
    <profile>
        <id>windows</id>
        <properties>
            <build.profile.id>windows</build.profile.id>
        </properties>
        <dependencies>
            <dependency>
                <groupId>org.openjfx</groupId>
                <artifactId>javafx-graphics</artifactId>
                <classifier>win</classifier>
            </dependency>
        </dependencies>
    </profile>

    <profile>
        <id>mac</id>
        <properties>
            <build.profile.id>mac</build.profile.id>
        </properties>
        <dependencies>
            <dependency>
                <groupId>org.openjfx</groupId>
                <artifactId>javafx-graphics</artifactId>
                <classifier>mac</classifier>
            </dependency>
        </dependencies>
    </profile>
</profiles>
```

**Build Commands**:
```bash
mvn clean package -P windows  # Windows JAR
mvn clean package -P mac      # macOS JAR
```

**Benefits**:
- Single codebase
- Platform-specific builds
- Easy to extend (add Linux profile)

---

### Challenge 4: Password Visibility Toggle

**Problem**: Need to show/hide passwords without losing data

**Complications**:
- PasswordField always masks input
- TextField always shows input
- Need synchronized text between both

**Solution**:
1. **Create Both Fields**: PasswordField (masked) and TextField (visible)
2. **Toggle Visibility**: Click event switches which is visible
3. **Synchronize Text**: Copy text between fields on toggle

**Code**:
```java
PasswordField passwordField = new PasswordField();
TextField textField = new TextField();

// Initialize
passwordField.setText(password);
passwordField.setVisible(true);
textField.setVisible(false);

// Toggle on click
passwordField.setOnMouseClicked(e -> {
    textField.setText(passwordField.getText());
    passwordField.setVisible(false);
    textField.setVisible(true);
});

textField.setOnMouseClicked(e -> {
    passwordField.setText(textField.getText());
    textField.setVisible(false);
    passwordField.setVisible(true);
});
```

**Benefits**:
- User can verify password
- Secure by default (masked)
- Simple toggle mechanism

---

## 12. Future Improvements

### Critical Security Fixes (MUST DO)

#### 1. Fix SQL Injection Vulnerabilities
**Problem**: All queries use String.format()
**Fix**: Migrate to PreparedStatement
**Estimated Effort**: 2-3 days
**Files to Modify**: All repository classes (EmailRepository.java, NoteRepository.java, CreditCardRepository.java)

```java
// Before (VULNERABLE)
String sql = String.format("INSERT INTO emails VALUES ('%s', '%s', '%s')",
                          url, email, password);

// After (SECURE)
String sql = "INSERT INTO emails VALUES (?, ?, ?)";
PreparedStatement stmt = connection.prepareStatement(sql);
stmt.setString(1, url);
stmt.setString(2, email);
stmt.setString(3, password);
```

#### 2. Implement Credential Encryption
**Problem**: Credentials stored in plain text
**Fix**: AES-256 encryption at rest
**Estimated Effort**: 1 week

**Implementation**:
```java
// Derive key from master password
byte[] encryptionKey = PBKDF2.deriveKey(masterPassword, salt, 100000, 256);

// Encrypt before storing
String encryptedPassword = AES256.encrypt(password, encryptionKey);
repository.save(email, encryptedPassword);

// Decrypt when retrieving
String decryptedPassword = AES256.decrypt(encryptedPassword, encryptionKey);
```

#### 3. Remove Hardcoded Credentials
**Problem**: DB password in source code
**Fix**: Environment variables
**Estimated Effort**: 1 day

```java
String dbUrl = System.getenv("DB_URL");
String dbUser = System.getenv("DB_USER");
String dbPassword = System.getenv("DB_PASSWORD");
```

#### 4. Add Input Validation
**Problem**: No validation
**Fix**: Comprehensive validation layer
**Estimated Effort**: 3-4 days

```java
// Email validation
if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
    throw new ValidationException("Invalid email format");
}

// Credit card validation (Luhn algorithm)
if (!luhnCheck(cardNumber)) {
    throw new ValidationException("Invalid card number");
}
```

### Feature Enhancements

#### 5. Password Generator
- Random password generation
- Configurable length and character sets
- Copy to clipboard
- Strength indicator

#### 6. Export/Import
- Encrypted backup to JSON/CSV
- Import from other password managers
- Restore from backup

#### 7. Two-Factor Authentication
- TOTP support (Google Authenticator)
- Biometric authentication (fingerprint)
- SMS verification

#### 8. Password Strength Meter
- Real-time strength analysis
- Suggestions for improvement
- Breach detection (haveibeenpwned API)

#### 9. Auto-Logout
- Inactivity timer
- Lock screen with re-authentication
- Session management

#### 10. Tags/Categories
- Custom tags for organization
- Filter by tags
- Favorites/starred credentials

### Technical Improvements

#### 11. Unit Testing
- JUnit 5 tests for all services
- Mock repositories for testing
- Integration tests for database
- Target: 80% code coverage

#### 12. Logging Framework
- Replace `Helpers.Logger()` with SLF4J + Logback
- Log levels (DEBUG, INFO, WARN, ERROR)
- Log rotation
- **Never log sensitive data**

#### 13. Database Optimizations
- Add indexes on frequently queried columns
- Connection pooling (HikariCP)
- Prepared statement caching
- Query optimization

#### 14. UI Improvements
- Light theme option
- Keyboard shortcuts (Ctrl+S to save, etc.)
- Drag-and-drop credential reordering
- Responsive design for different screen sizes

#### 15. Code Quality
- JavaDoc documentation
- Static code analysis (SonarQube)
- Code formatting (Google Java Style)
- Remove code smells

---

## 13. Conclusion

### Project Summary

This password manager application demonstrates:

✅ **Solid Object-Oriented Design**:
- Inheritance hierarchy (Entity → Email/Note/CreditCard)
- Polymorphism (render methods)
- Encapsulation (private fields, public methods)
- Abstraction (interfaces, abstract classes)

✅ **Professional Design Pattern Implementation**:
- 6 design patterns correctly applied
- Clean architecture with clear layer separation
- Maintainable, extensible codebase

✅ **Database Integration Expertise**:
- Oracle Database connectivity via JDBC
- Complex LEFT JOIN query for efficiency
- Parent-child relationships with foreign keys
- Type discrimination at runtime

✅ **Modern UI Development**:
- JavaFX 23.0.1 with FXML
- Three-panel responsive layout
- Dark theme with consistent styling
- Dynamic form generation

✅ **Security Fundamentals**:
- Industry-standard PBKDF2 hashing (65,536 iterations)
- Random salt generation (SecureRandom)
- 256-bit key strength
- Constant-time hash comparison

✅ **Full-Stack Application**:
- Complete CRUD operations
- Search functionality
- User authentication
- Data persistence

### Key Takeaways

**Technical Skills**:
- Java 21 with modern features
- JavaFX UI framework mastery
- JDBC database programming
- Maven build system
- Design pattern implementation
- Cryptographic hashing (PBKDF2)

**Software Engineering**:
- MVC architecture
- Repository pattern
- Strategy pattern
- Factory pattern
- Template method pattern
- Singleton pattern

**Problem-Solving**:
- Complex database schema design
- Runtime type discrimination
- Dynamic UI generation
- Cross-platform builds

### Honest Assessment

**Strengths**:
- Well-organized codebase
- Multiple design patterns
- Functional application
- Secure master password

**Critical Weaknesses** (acknowledged):
- SQL injection vulnerabilities
- No credential encryption
- Hardcoded database credentials
- Missing input validation
- Information disclosure via logging

**Learning Points**:
- Security is hard and requires multiple layers
- Input validation is critical
- Never use string concatenation for SQL
- Encryption at rest is essential for sensitive data
- Hardcoded credentials are never acceptable

### Future Direction

**Immediate Priorities** (within 1 month):
1. Fix SQL injection (PreparedStatement)
2. Implement AES-256 encryption
3. Add input validation
4. Remove hardcoded credentials

**Medium-Term Goals** (within 3 months):
1. Password generator
2. Export/import functionality
3. Unit tests (80% coverage)
4. Logging framework

**Long-Term Vision** (within 6 months):
1. Two-factor authentication
2. Browser extension
3. Cloud synchronization
4. Mobile companion app

### Statistics

**Project Metrics**:
- **Total Classes**: 28 Java classes
- **Total Views**: 4 FXML files
- **Packages**: 8 packages
- **Design Patterns**: 6 patterns
- **Database Tables**: 4 tables
- **Credential Types**: 3 types
- **Development Time**: ~40 hours
- **Lines of Code**: ~2,500 (estimated)

**Technologies**:
- Java 21 (LTS)
- JavaFX 23.0.1
- Oracle Database XE
- Maven 3.x
- JDBC ojdbc11

---

## Thank You!

### Questions & Discussion

**Topics for Discussion**:
1. Design pattern choices and alternatives
2. Security implementation and improvements
3. Database schema design
4. UI/UX decisions
5. Cross-platform build challenges
6. Future feature prioritization

### Resources

**Project Files**:
- Source Code: `src/main/java/com/password/manager/`
- FXML Views: `src/main/resources/com/password/manager/auth/`
- Database Schema: `readme.md`
- This Presentation: `COMPREHENSIVE_PRESENTATION.md`
- Build Configuration: `pom.xml`

**Key Documentation**:
- Project Instructions: `CLAUDE.md`
- Database Schema: `readme.md`
- Run Scripts: `run.sh` (macOS), `run.bat` (Windows)

### Contact & Next Steps

**This project demonstrates**:
- Strong understanding of OOP principles
- Practical application of design patterns
- Database-driven application development
- Security awareness (with room for improvement)
- Full-stack desktop application skills

**Ready for**:
- Code review
- Security audit
- Feature enhancement
- Team collaboration

---

*Presentation created for academic purposes - Password Manager Application*

*Technology Stack: Java 21 | JavaFX 23.0.1 | Oracle Database | Maven*
