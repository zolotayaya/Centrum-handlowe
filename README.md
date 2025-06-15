# 🏪 Centrum-handlowe

<div align="center">

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

A sophisticated Java application simulating a shopping center management system, powered by PostgreSQL database.

</div>

## 📋 Table of Contents

- [Overview](#-overview)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [Running the Application](#-running-the-application)

## 🌟 Overview

Centrum-handlowe is a robust shopping center management system that provides comprehensive functionality for managing various aspects of a shopping center. The application is built with modern Java technologies and uses PostgreSQL for reliable data storage.

## 🛠️ Prerequisites

Before you begin, ensure you have the following installed:

- Java Development Kit (JDK) 24
- Docker (for PostgreSQL)
- Gradle Wrapper (included in the project)

### Verifying Installation


# Check Java version
```bash
java --version
```

# Check Docker version
```bash
docker --version
```


## 🚀 Installation

### 1. Clone the Repository

```bash
git clone https://github.com/zolotayaya/Centrum-handlowe
cd Centrum-handlowe
```

### 2. Start PostgreSQL Database

```bash
docker run --name shopping-center-postgres \
-e POSTGRES_USER=postgres \
-e POSTGRES_PASSWORD=datax \
-e POSTGRES_DB=postgres \
-p 5432:5432 \
-d postgres
```

## 💻 Running the Application

### 1. Build the Project

For Linux/macOS:

```bash
./gradlew build
```

For Windows:

```bash
gradlew build
```

The build process will generate a JAR file in build/libs/ directory.

### 2. Run the Application

```bash
java -jar build/libs/Centrum-handlowe-1.0-SNAPSHOT.jar
```