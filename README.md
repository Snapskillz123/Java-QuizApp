# Quiz Application

A Java Swing-based Quiz Application with an admin interface for managing questions and a user interface for taking quizzes.

## Features

- **Admin Interface**: 
  - Password-protected admin panel
  - Add, edit, and delete quiz questions
  - Manage multiple choice questions with 4 options each
  
- **Quiz Interface**:
  - Take quizzes with timer functionality
  - Navigate between questions (previous/next)
  - Real-time score calculation
  - Results display with percentage and time taken

- **Modern UI**:
  - Clean, professional design
  - Color-coded buttons and status indicators
  - Responsive layout

## Architecture

The application follows a layered architecture pattern:

```
com.quiz/
├── QuizApplication.java          # Main entry point
├── model/                        # Data models
│   ├── Question.java            # Question entity
│   └── Quiz.java                # Quiz entity
├── service/                      # Business logic
│   ├── QuizService.java         # Quiz management service
│   └── AuthenticationService.java # Authentication service
├── view/                         # User interface
│   ├── AdminInterface.java      # Admin panel
│   ├── QuestionEditor.java      # Question add/edit form
│   └── QuizInterface.java       # Quiz taking interface
└── controller/                   # Controllers (for future expansion)
```

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven 3.6+ (optional, for building)

### Running the Application

#### Option 1: Using Maven
```bash
mvn compile exec:java
```

#### Option 2: Using Java directly
```bash
# Compile
javac -d target/classes src/main/java/com/quiz/*.java src/main/java/com/quiz/*/*.java

# Run
java -cp target/classes com.quiz.QuizApplication
```

#### Option 3: Create executable JAR
```bash
mvn clean package
java -jar target/quiz-application-1.0.0.jar
```

### Default Admin Credentials

- **Username**: Admin access (no username required)
- **Password**: `admin123`

## Usage

1. **Start the Application**: Run the main class to open the Admin Interface
2. **Add Questions**: Click "Add Question" and enter admin password to create new questions
3. **Start Quiz**: Click "Start Quiz" to begin taking the quiz
4. **Take Quiz**: Answer questions within the time limit and view your results

## Project Structure

```
QuizAPP/
├── pom.xml                       # Maven configuration
├── README.md                     # This file
└── src/
    └── main/
        └── java/
            └── com/
                └── quiz/
                    ├── QuizApplication.java
                    ├── model/
                    │   ├── Question.java
                    │   └── Quiz.java
                    ├── service/
                    │   ├── QuizService.java
                    │   └── AuthenticationService.java
                    └── view/
                        ├── AdminInterface.java
                        ├── QuestionEditor.java
                        └── QuizInterface.java
```

## Design Patterns Used

- **Singleton Pattern**: QuizService and AuthenticationService
- **MVC Pattern**: Separation of Model, View, and Service layers
- **Observer Pattern**: Event handling in Swing components

## Future Enhancements

- Database integration for persistent storage
- User management and different user roles
- Question categories and difficulty levels
- Export/import functionality for questions
- Detailed analytics and reporting
- Web-based interface



## License

This project is open source and available under the MIT License.
