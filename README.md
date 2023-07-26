# Expatrio Assignment

We need to create a backend application that has a few entities(department and user)
and possibility to manage them with a REST API.

### Data requirements:
- Entity Department should have ID and name.
- Entity User should have ID, role(Admin, Employee), name, salary.
- User record can be connected just to one department.
- All data types can be selected according to your preference.

### The application should have the following available features:
- login as a user with role Admin
- CRUD operations for department entities
- CRUD operations for user entities with role Employee
- get average salary per department

### Non-functional requirements:
- REST API
- Spring Boot + Java/Kotlin
- Any relational database(preferable Postgres)
- (Optional) Flyway
- Preferable to use Jooq to work with a database, but other tools are fine as well