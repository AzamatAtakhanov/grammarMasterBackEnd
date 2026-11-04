# GrammarMaster

A beginner-friendly English grammar quiz REST API built with Java, Spring Boot, Spring Data JPA and H2.

## Requirements
- JDK 24 (or change `java.version` in `pom.xml` to your installed JDK)
- Maven 3.9+ or IntelliJ IDEA with Maven support

## Run in IntelliJ IDEA
1. Extract the ZIP.
2. Open the `GrammarMaster` folder in IntelliJ IDEA.
3. Set Project SDK to JDK 24.
4. Wait for Maven dependencies to download.
5. Run `GrammarMasterApplication`.
6. The API starts at `http://localhost:8080`.

The project uses an in-memory H2 database by default. It creates sample quizzes and questions automatically at startup. Data resets when the application restarts.

## API endpoints
- `GET /api/quizzes` — list all quizzes
- `GET /api/quizzes/1` — get a quiz and its questions (correct answers are not exposed)
- `POST /api/quizzes/1/submit` — submit answers and receive score + explanations
- `GET /api/results` — list saved quiz results
- `POST /api/admin/login` — exchange the admin password for a token
- `GET|POST /api/admin/quizzes`, `PUT|DELETE /api/admin/quizzes/{id}` — manage quizzes and questions (admin token required, correct answers included)
- `GET /swagger-ui.html` — interactive API documentation
- `GET /h2-console` — H2 database console

## Example: submit a quiz
Send `POST http://localhost:8080/api/quizzes/1/submit` with JSON:

```json
{
  "studentName": "Alex",
  "answers": [
    {"questionId": 1, "selectedOption": "B"},
    {"questionId": 2, "selectedOption": "C"},
    {"questionId": 3, "selectedOption": "B"},
    {"questionId": 4, "selectedOption": "D"},
    {"questionId": 5, "selectedOption": "C"}
  ]
}
```

## Admin panel
Admin endpoints live under `/api/admin` and need a token. Log in with the password from `application.properties`
(`grammarmaster.admin.password`, default `admin123`) and send the returned token as `Authorization: Bearer <token>`.
Set the `ADMIN_PASSWORD` environment variable to use your own password. Tokens are kept in memory, so restarting the backend signs everyone out.

Deleting a quiz also deletes its questions and saved student results.

## Use PostgreSQL (optional)
1. Create a database named `grammarmaster`.
2. Open `src/main/resources/application.properties`.
3. Replace `CHANGE_ME` with your local PostgreSQL password.
4. Run with the `postgres` profile:
   `mvn spring-boot:run -Dspring-boot.run.profiles=postgres`

## Learning notes
- `model` contains JPA entities.
- `repository` contains database access interfaces.
- `dto` contains request/response objects.
- `service` contains quiz scoring logic and the admin quiz management.
- `controller` exposes REST endpoints.
- `config/DataSeeder.java` inserts demo quiz content.

For a classroom demo, explain how a quiz is retrieved, how submitted answers are checked on the server, and how the result is saved.
