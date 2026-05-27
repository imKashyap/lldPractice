## Problem Statement

Design a Library Management System that allows librarians to manage books and members, and enables members to borrow and return books.  The system should support the following operations:
 1. Add, update, and remove books from the library catalog
 2. Register new members and manage their information
 3. Allow members to borrow and return books
 4. Track due dates and calculate fines for overdue books
 5. Search for books by various criteria (title, author, subject, etc.)

## Requirements

- The system should maintain information about books including title, author, ISBN, publication date, and category
- Each book can have multiple physical copies, each with a unique ID
- Members should have profiles with contact information and borrowing history
- Members can borrow a limited number of books for a specific duration
- The system should track due dates and calculate fines for overdue books
- Librarians should be able to search for books and members
- The system should generate reports on book availability, overdue books, and popular books

## Constraints

- A book can be borrowed if at least one copy is available
- A member cannot borrow more than the allowed limit of books
- A book cannot be borrowed if the member has unpaid fines above a threshold
- Books marked as "reference" cannot be borrowed

## Prep / Design Notes

### Core Entities

`Book`
- Represents bibliographic metadata only.
- Fields: `isbn`, `title`, `authors`, `subject`, `publicationDate`, `category`, `referenceOnly`.

`BookItem`
- Represents a physical copy of a book.
- Fields: `copyId`, `book`, `rackLocation`, `status`.

`User`
- Base class for people in the system.
- Fields: `userId`, `name`, `contactInfo`.

`Member`
- Extends `User`.
- Tracks member status.

`Librarian`
- Extends `User`.
- Represents the librarian role.

`Loan`
- Represents checkout lifecycle.
- Fields: `loanId`, `member`, `bookItem`, `checkoutDate`, `dueDate`, `returnDate`, `status`.

`Fine`
- Represents overdue or penalty charge.
- Fields: `fineId`, `member`, `amount`, `status`.

`Reservation`
- Represents a hold request on a title.
- Fields: `reservationId`, `book`, `member`, `createdAt`, `status`.

### Relationships

- One `Book` has many `BookItem`.
- One `Member` has many `Loan`.
- One `Member` has many `Fine`.
- One `Member` has many `Reservation`.
- One `Book` has many `Reservation`.
- One `BookItem` can have at most one active `Loan`.

### Services

`CatalogService`
- Adds, updates, removes, and searches books and book items.

`MembershipService`
- Registers, updates, suspends, and removes members.

`CirculationService`
- Checks out, returns, renews, and reserves books.

`FineService`
- Computes outstanding fines and collects payments.

`NotificationService`
- Sends due-date, overdue, and reservation-available notifications.

`ReportService`
- Generates availability, overdue, and popular-book reports.

### Repositories

- `BookRepository`
- `BookItemRepository`
- `MemberRepository`
- `LoanRepository`
- `FineRepository`
- `ReservationRepository`

### Policies And Strategies

`BorrowingPolicy`
- Encapsulates whether a member can borrow a specific item.
- Rules: max active loans, unpaid fine threshold, reference-only restriction.

`FineCalculationStrategy`
- Encapsulates overdue fine calculation logic.

`SearchStrategy`
- Encapsulates searching by title, author, subject, or category.

### Checkout Flow

1. Validate member is active.
2. Validate outstanding fines are below threshold.
3. Validate item is borrowable through `BorrowingPolicy`.
4. Find an available `BookItem`.
5. Create a `Loan`.
6. Mark the item as `LOANED`.
7. Notify interested subsystems.

### Patterns Used

- Strategy: search, fine calculation, borrowing policy.
- Repository: persistence abstraction.
- Facade: `LibraryManagementSystem`.
- Observer: notification hooks for due dates and reservations.
- Dependency Injection: services depend on repository and policy abstractions.
