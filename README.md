# Student Management System - Java

## Overview
A comprehensive Java-based Student Management System with a user-friendly Swing GUI. This application provides complete CRUD operations for managing student records with persistent file-based storage.

## Features
- **Add Students**: Create new student records with details like name, ID, email, and phone number
- **Update Students**: Modify existing student information
- **Delete Students**: Remove student records from the system
- **Search Students**: Find students by name or ID with quick filtering
- **View All Students**: Display all student records in a table format
- **File-Based Storage**: Persistent storage using CSV/text files
- **User-Friendly GUI**: Built with Java Swing for easy interaction
- **Data Validation**: Input validation to ensure data integrity

## Technology Stack
- **Language**: Java
- **GUI Framework**: Java Swing
- **Storage**: File-based (CSV format)
- **IDE**: NetBeans/Eclipse/IntelliJ IDEA compatible

## Project Structure
```
student-management-system-java/
├── StudentManagementSystem.java   # Main application class
├── Student.java                   # Student model class
├── StudentDatabase.java           # File operations and data management
├── StudentUI.java                 # GUI components
└── README.md                      # This file
```

## Installation & Setup

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Any Java IDE (NetBeans, Eclipse, IntelliJ IDEA) or command line

### How to Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/alekhyaalli2510/student-management-system-java.git
   cd student-management-system-java
   ```

2. **Compile the Java files**
   ```bash
   javac *.java
   ```

3. **Run the application**
   ```bash
   java StudentManagementSystem
   ```

## Usage

### Main Window Features
- **Add Button**: Opens a dialog to add a new student
- **Update Button**: Allows modification of selected student record
- **Delete Button**: Removes the selected student record
- **Search Box**: Filter students by name or ID
- **Refresh Button**: Reload student list from file
- **Table View**: Displays all students with their information

### Keyboard Shortcuts
- `Ctrl+N`: Add new student
- `Ctrl+E`: Edit selected student
- `Ctrl+D`: Delete selected student
- `Ctrl+F`: Focus on search box
- `Ctrl+Q`: Quit application

## Data Storage
Student records are stored in a `students.csv` file with the following format:
```
student_id,name,email,phone,registration_date
```

## Future Enhancements
- Database integration (MySQL/PostgreSQL)
- Advanced search and filtering options
- Export functionality (PDF, Excel)
- Student performance tracking
- Multi-user support with authentication
- Backup and restore features

## License
This project is open source and available under the MIT License.

## Author
alekh yaa

## Contributing
Contributions are welcome! Please feel free to submit a Pull Request.

## Support
If you encounter any issues or have suggestions, please open an issue in the repository.
