# PHASE 3: Dockerization & Version Control
## Warehouse Management System — Magerwa Ltd, Rwanda
### SENG 8240 — Best Programming Practices and Design Patterns

---

## PART 1: DOCKERIZING THE APPLICATION

### 1.1 What is Docker?

Docker is a platform for containerization — a method of packaging an application
and all its dependencies (libraries, runtime, OS packages) into a single,
portable unit called a **container**. Containers run consistently regardless of
the environment (development, testing, or production).

**Key Docker Concepts:**

| Concept         | Description                                                    |
|-----------------|----------------------------------------------------------------|
| **Image**       | A read-only template with instructions for creating a container|
| **Container**   | A running instance of an image                                 |
| **Dockerfile**  | A text file with instructions to build an image                |
| **Docker Hub**  | A public registry to store and share Docker images             |
| **Volume**      | Persistent storage that survives container restarts             |
| **Port Mapping**| Links a host port to a container port (e.g., 8080:8080)        |

### 1.2 Why Dockerize?

| Benefit                | Explanation                                              |
|------------------------|----------------------------------------------------------|
| **Consistency**        | "Works on my machine" problem is eliminated              |
| **Portability**        | Same container runs on Windows, Linux, Mac, cloud        |
| **Isolation**          | App runs independently from the host system              |
| **Easy Deployment**    | Ship a single image instead of complex setup instructions|
| **Scalability**        | Easy to replicate containers for load balancing          |

### 1.3 Process to Dockerize an Application (Step-by-Step)

**Step 1: Install Docker**
```bash
# Download Docker Desktop from: https://www.docker.com/products/docker-desktop/
# Verify installation:
docker --version
docker-compose --version
```

**Step 2: Create a Dockerfile**

A Dockerfile is a recipe that tells Docker how to build your application image.
Our WMS uses a **multi-stage build** for efficiency:

```
┌─────────────────────────────────────────────────┐
│  STAGE 1: BUILD                                 │
│  ┌───────────────────────────────────────────┐  │
│  │  Base Image: maven:3.9-eclipse-temurin-17 │  │
│  │  1. Copy pom.xml                          │  │
│  │  2. Download dependencies                 │  │
│  │  3. Copy source code                      │  │
│  │  4. Run: mvn clean package                │  │
│  │  5. Output: app.jar                       │  │
│  └───────────────────────────────────────────┘  │
│                      │                          │
│                      ▼                          │
│  STAGE 2: RUN                                   │
│  ┌───────────────────────────────────────────┐  │
│  │  Base Image: eclipse-temurin:17-jre-alpine│  │
│  │  1. Copy app.jar from Stage 1             │  │
│  │  2. Expose port 8080                      │  │
│  │  3. Run: java -jar app.jar                │  │
│  └───────────────────────────────────────────┘  │
└─────────────────────────────────────────────────┘
```

**Why multi-stage?** Stage 1 has Maven + JDK (~400MB). Stage 2 only has JRE (~150MB).
The final image is smaller and more secure because build tools are excluded.

**Step 3: Create a .dockerignore file**

Excludes unnecessary files (IDE configs, build output, git history) from the
Docker build context to speed up builds and reduce image size.

**Step 4: Build the Docker Image**
```bash
# Navigate to project directory (where Dockerfile is located)
cd WMS

# Build the image and tag it as "magerwa-wms"
docker build -t magerwa-wms .

# This command:
#   1. Reads the Dockerfile
#   2. Executes each instruction (FROM, COPY, RUN, etc.)
#   3. Creates a layered image tagged "magerwa-wms"
```

**Step 5: Run the Container**
```bash
# Run the container, mapping port 8080
docker run -d -p 8080:8080 --name magerwa-wms-container magerwa-wms

# Flags explained:
#   -d          Run in background (detached mode)
#   -p 8080:8080  Map host port 8080 to container port 8080
#   --name      Give the container a friendly name
```

**Step 6: Access the Application**
```
Open browser: http://localhost:8080
Login with: admin / admin123
```

**Step 7: Useful Docker Commands**
```bash
# View running containers
docker ps

# View container logs
docker logs magerwa-wms-container

# Stop the container
docker stop magerwa-wms-container

# Remove the container
docker rm magerwa-wms-container

# List all images
docker images

# Remove the image
docker rmi magerwa-wms
```

### 1.4 Using Docker Compose

Docker Compose simplifies running the application with a single command:

```bash
# Build and start (reads docker-compose.yml)
docker-compose up --build

# Run in background
docker-compose up -d --build

# Stop and remove containers
docker-compose down

# View logs
docker-compose logs -f
```

### 1.5 Our Dockerfile Explained (Line by Line)

```dockerfile
# Stage 1: Use Maven with JDK 17 to build the application
FROM maven:3.9-eclipse-temurin-17 AS build

# Set /app as the working directory inside the container
WORKDIR /app

# Copy pom.xml first (dependency caching optimization)
COPY pom.xml .

# Download all Maven dependencies (cached layer)
RUN mvn dependency:go-offline -B

# Copy the Java source code
COPY src ./src

# Compile and package into a JAR file
RUN mvn clean package -DskipTests

# Stage 2: Use lightweight JRE Alpine image to run
FROM eclipse-temurin:17-jre-alpine

# Set working directory
WORKDIR /app

# Copy the JAR from the build stage (not all of Maven/JDK)
COPY --from=build /app/target/*.jar app.jar

# Tell Docker this container listens on port 8080
EXPOSE 8080

# Command that runs when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

## PART 2: VERSION CONTROL WITH GIT

### 2.1 What is Version Control?

A Version Control System (VCS) is a tool that tracks and manages changes to
source code over time. It allows developers to:
- Track every change made to the code
- Revert to previous versions if something breaks
- Collaborate with other developers without conflicts
- Maintain a complete history of the project

### 2.2 Why Git?

Git is the most widely used distributed VCS. Unlike centralized systems (SVN),
every developer has a complete copy of the repository, enabling offline work
and faster operations.

### 2.3 Installing and Configuring Git

**Step 1: Install Git**
```bash
# Download from: https://git-scm.com/downloads
# Verify installation:
git --version
```

**Step 2: Configure Git (first-time setup)**
```bash
# Set your identity (used in every commit)
git config --global user.name "Prince"
git config --global user.email "prince@student.auca.ac.rw"

# Verify configuration
git config --list
```

### 2.4 Initializing the Repository

```bash
# Navigate to the project directory
cd WMS

# Initialize a new Git repository
git init

# This creates a hidden .git/ directory that tracks all changes
```

### 2.5 Tracking Files and Making Commits

```bash
# Check which files are untracked/modified
git status

# Add ALL project files to staging area
git add .

# Make the first commit (snapshot of the project)
git commit -m "Initial commit: WMS Phase 2 prototype with Spring Boot"

# View commit history
git log --oneline
```

### 2.6 Working with Branches

```bash
# Create a new branch for a feature
git branch feature/docker-setup

# Switch to that branch
git checkout feature/docker-setup

# Make changes, then commit
git add .
git commit -m "Add Dockerfile and docker-compose.yml for Phase 3"

# Switch back to main branch and merge
git checkout main
git merge feature/docker-setup
```

### 2.7 Connecting to Remote Repository (GitHub)

```bash
# Create a repository on GitHub (github.com → New Repository)
# Then link your local repo to GitHub:

git remote add origin https://github.com/prince/WMS-Magerwa.git

# Push code to GitHub
git push -u origin main

# Pull latest changes from GitHub
git pull origin main
```

### 2.8 Our .gitignore File

The `.gitignore` file tells Git which files to exclude from tracking:
- `target/` — compiled output (can be rebuilt)
- `.idea/`, `.vscode/` — IDE settings (personal to each developer)
- `*.class`, `*.jar` — compiled Java files
- `*.log` — runtime logs
- `.env` — environment secrets

### 2.9 Complete Git Workflow for This Project

```bash
# 1. Initialize repository
cd WMS
git init

# 2. Add .gitignore (already created)
git add .gitignore
git commit -m "Add .gitignore for Java/Maven project"

# 3. Add all source code
git add .
git commit -m "Initial commit: WMS prototype with Spring Boot, MVC, design patterns"

# 4. Add Docker files
git add Dockerfile docker-compose.yml .dockerignore
git commit -m "Add Docker configuration for containerization"

# 5. Add Phase 3 documentation
git add PHASE3_DOCUMENTATION.md
git commit -m "Add Phase 3 documentation (Docker + Git)"

# 6. View history
git log --oneline

# 7. Push to GitHub (optional)
git remote add origin https://github.com/prince/WMS-Magerwa.git
git push -u origin main
```

---

## SUMMARY

| Component        | Tool            | Purpose                                    |
|------------------|-----------------|--------------------------------------------|
| Containerization | Docker          | Package app + dependencies into a container|
| Build Definition | Dockerfile      | Instructions to build the Docker image     |
| Orchestration    | Docker Compose  | Simplify multi-container management        |
| Version Control  | Git             | Track code changes and history             |
| Remote Hosting   | GitHub          | Store repository online for collaboration  |

---

**Author:** Prince
**Course:** SENG 8240 — Best Programming Practices & Design Patterns
**Institution:** Adventist University of Central Africa
**Case Study:** Magerwa Ltd, Rwanda
