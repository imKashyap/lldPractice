## Better Design

### Core Entities

`Book`
- Represents bibliographic metadata only
- Fields: `isbn`, `title`, `authors`, `subject`, `publicationDate`, `category`, `referenceOnly`

`BookItem`
- Represents a physical copy of a book
- Fields: `copyId`, `book`, `rackLocation`, `status`

`User`
- Base class for users in the system
- Fields: `userId`, `name`, `contactInfo`

`Member extends User`
- Fields: `status`

`Librarian extends User`
- Represents the librarian role

`Loan`
- Represents a checkout transaction
- Fields: `loanId`, `member`, `bookItem`, `checkoutDate`, `dueDate`, `returnDate`, `status`

`Fine`
- Represents overdue or penalty charges
- Fields: `fineId`, `member`, `amount`, `status`

`Reservation`
- Represents a hold request on a title
- Fields: `reservationId`, `book`, `member`, `createdAt`, `status`

### Relationships

- One `Book` has many `BookItem`
- One `Member` has many `Loan`
- One `Member` has many `Fine`
- One `Member` has many `Reservation`
- One `Book` has many `Reservation`
- One `BookItem` can have at most one active `Loan`

### Services

`CatalogService`
- Add, update, remove, and search books and book items

`MembershipService`
- Register, update, suspend, and remove members

`CirculationService`
- Checkout, return, renew, and reserve books

`FineService`
- Compute outstanding fines and collect payments

`NotificationService`
- Send due-date, overdue, and reservation-available notifications

`ReportService`
- Generate availability, overdue, and popular-book reports

### Repositories

- `BookRepository`
- `BookItemRepository`
- `MemberRepository`
- `LoanRepository`
- `FineRepository`
- `ReservationRepository`

### Policies And Strategies

`BorrowingPolicy`
- Encapsulates whether a member can borrow a specific item
- Rules: max active loans, unpaid fine threshold, reference-only restriction

`FineCalculationStrategy`
- Encapsulates overdue fine calculation logic

`SearchStrategy`
- Encapsulates searching by title, author, subject, or category

### Design Principles

- `SRP`: entities keep domain state, services orchestrate use cases, repositories handle persistence
- `OCP`: new fine rules, search criteria, and borrowing rules can be added via strategies/policies
- `DIP`: services depend on repository and policy abstractions
- `ISP`: each service interface stays focused on one use-case family

### Patterns Used

- `Strategy`: search, fine calculation, borrowing policy
- `Repository`: persistence abstraction
- `Facade`: `LibraryManagementSystem`
- `Observer`: notification hooks for due dates and reservations

### Suggested Checkout Flow

1. Validate member is active
2. Validate outstanding fines are below threshold
3. Validate item is borrowable through `BorrowingPolicy`
4. Find an available `BookItem`
5. Create a `Loan`
6. Mark the item as `LOANED`
7. Notify interested subsystems
