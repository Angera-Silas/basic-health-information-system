# Basic Health Information System

## Introduction

The **Basic Health Information System** is a comprehensive platform designed to manage client and program data for healthcare providers. It allows doctors to manage clients, enroll them in health programs, and view detailed client information. The system consists of a **frontend** built with Flutter and a **backend** powered by Spring Boot and PostgreSQL.

---

## Features

- **Client Management**:
  - Add new clients with detailed information.
  - Search for clients in real-time using a search dialog.
  - View detailed information about a client, including their enrolled programs.

- **Program Management**:
  - Fetch and display available programs.
  - Enroll clients in programs.
  - View program details.

- **Real-Time Search**:
  - Search for clients by name in real-time.
  - Filter and display matching results dynamically.

---

## Requirements for Running

Before running the project, ensure you have the following installed:

1. **Flutter SDK**: [Install Flutter](https://flutter.dev/docs/get-started/install)
2. **Dart SDK**: Comes bundled with Flutter.
3. **Java Development Kit (JDK)**: Version 17 or higher.
4. **PostgreSQL**: Installed locally or running in a Docker container.
5. **Docker**: For containerized database setup (optional).

---
## How to Run

### Project

1. **Clone the Project Repository**:
   ```bash
   git clone https://github.com/your-username/health-system.git
   cd health-system-backend
   ```


### Frontend

1. **Navigate to frontend folder**:
   ```bash
  
   cd health-system-frontend
   ```

2. **Install Dependencies**:
   ```bash
   flutter pub get
   ```

3. **Configure API Endpoint**:
   Update the `ApiService` class in the frontend to point to your backend API URL:
   ```dart
   static const String baseUrl = "http://localhost:8088/api";
   ```

4. **Run the Frontend**:
   ```bash
   flutter run
   ```

---

### Tech Stack

- **Flutter**: For building cross-platform applications (Web, Desktop, and Mobile).
- **Dart**: Programming language for Flutter.

### Key Features

- **Client List Screen**:
  - Displays a list of clients.
  - Allows searching, adding, and viewing client details.

- **Program Screen**:
  - Displays available programs.
  - Allows enrolling clients in programs.

---

### Backend

1. **Configure the Database**:
   - Update the `application.properties` file with your PostgreSQL credentials:
     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/health_system
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```

2. **Run the Backend**:
- Start docker containers

```bash
docker-compose up -d
```

- Run springboot
```bash
./mvnw clean install

./mvnw spring-boot:run
```

### Backend Tech Stack

- **Spring Boot**: Backend framework for building RESTful APIs.
- **PostgreSQL**: Relational database for storing client and program data.
- **Docker**: For containerized database setup (optional).

### Backend Key Features

- **Client Management API**:
  - `GET /client-details/by-doctor/{doctorId}`: Fetch all clients for a specific doctor.
  - `GET /client-details/full-info/{clientId}`: Fetch detailed information about a client.
  - `POST /client-details/create`: Add a new client.

- **Program Management API**:
  - `GET /programs/{programId}/unenrolled-clients/{doctorId}`: Fetch unenrolled clients for a program.
  - `POST /enrollments/create`: Enroll a client in a program.

---

## Troubleshooting

### API Errors
- Check the API URL in the `ApiService` class.
- Use tools like Postman to verify the backend endpoints.

---

## Contributing

Contributions are welcome! To contribute:

1. Fork the repository.
2. Create a new branch:
   ```bash
   git checkout -b feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add feature-name"
   ```
4. Push to your branch:
   ```bash
   git push origin feature-name
   ```
5. Open a pull request.

---

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---
