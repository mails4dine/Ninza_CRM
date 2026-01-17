pipeline {
    agent any

    tools {
        maven 'MavenHome'
        jdk 'JDK21'
    }

    stages {
        stage('Build & Test') {
            steps {
                bat 'mvn clean test'
            }
        }
    }
}
