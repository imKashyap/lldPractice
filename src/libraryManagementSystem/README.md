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
