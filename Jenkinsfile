pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                sh 'echo "Building..."'
                sh 'mvn clean package' // Замените на соответствующую команду сборки
            }
        }
    }
}