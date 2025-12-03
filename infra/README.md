---
# Jenkins Setup for Local Development (Docker)

This guide explains how to run **Jenkins locally** using the configuration inside the `infra/` folder.

---

## 1. Requirements

Make sure the following are installed:

* **Docker** (latest)
* **Docker Compose** (v2+)
* **Git**

Verify installation:

```sh
docker --version
docker compose version
git --version
```

---

## 2. Project Structure

```
buy-01-git/
│
├── app/                 # Backend + frontend source code
├── infra/
│   ├── Dockerfile       # Custom Jenkins image (Docker, Maven, Node included)
│   │── docker-compose.yml
│   └── README.md
```

---

## 3. Build the Jenkins Image

Navigate to the `infra/` folder:

```sh
cd infra
```

Build the custom Jenkins image:

```sh
docker build -t buy01-jenkins .
```

This image includes **Docker CLI**, **Compose plugin**, **Maven**, and **Node**.

---

## 4. Start Jenkins

Still inside the `infra/` directory:

```sh
docker compose up -d
```

Jenkins will be available at:

👉 **[http://localhost:8085](http://localhost:8085)**

---

## 5. First-Time Login

1. Open your browser and go to:

   👉 **[http://localhost:8085](http://localhost:8085)**

2. Jenkins will ask for the initial admin password. Retrieve it:

   ```sh
   docker exec -it jenkins cat /var/jenkins_home/secrets/initialAdminPassword
   ```

3. Paste the password into Jenkins.

4. Choose **"Install suggested plugins"**.

5. Create your admin user.

---

## 6. Enable Docker Inside Jenkins

The repo already mounts Docker:

```
/var/run/docker.sock:/var/run/docker.sock
```

This lets Jenkins run Docker commands directly.

To verify:

* Go to **Manage Jenkins → Tools → Docker**
* Jenkins should detect Docker automatically.

Create a test pipeline stage:

```sh
docker ps
```

If you see output, Docker is working.

---

## 7. Connect Jenkins to GitHub

Create a job:

1. Dashboard → **New Item**
2. Select **Multibranch Pipeline**
3. Set:

    * **Repository URL** → your GitHub repo
   
4. Choose branch sources (e.g., `dev`, personal or all branches)

Jenkins will automatically detect your `Jenkinsfile`.

---

## 8. Jenkinsfile Required in Your Repo

Place this in `app/` or the project root:

```groovy
pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps { checkout scm }
        }

        stage('Build backend') {
            steps {
                sh 'mvn -B clean package'
            }
        }

        stage('Build frontend') {
            steps {
                sh 'npm install'
                sh 'npm run build'
            }
        }
    }
}
```

Modify as needed.

---

## 9. Restart Jenkins

```sh
docker compose restart
```

---

## 10. Stop Jenkins

```sh
docker compose down
```

Data persists in the `jenkins_home` Docker volume.

---

## 11. Reset Everything (Deletes All Jenkins Data)

```sh
docker compose down -v
```

---
