pipeline {
    agent {
        docker {
            image 'infra-jenkins:latest'
            args '-u root -v /var/run/docker.sock:/var/run/docker.sock'
        }
    }
    stages {
        stage('Test') {
            steps {
                sh 'echo Hello Jenkins'
            }
        }
    }
    post {
        always {
            echo "Cleaning workspace"
            cleanWs notFailBuild: true
        }
    }
}
