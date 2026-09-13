pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                echo 'Checking out ShopEase source code...'
            }
        }

        stage('Maven Build') {
            steps {
                echo 'Building ShopEase with Maven...'
                bat 'mvnw.cmd clean compile'
            }
        }

        stage('Unit Tests') {
            steps {
                echo 'Running unit tests...'
                bat 'mvnw.cmd test'
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging ShopEase application...'
                bat 'mvnw.cmd package -DskipTests'
            }
        }
    }

    post {
        success {
            echo 'ShopEase CI pipeline completed successfully!'
        }

        failure {
            echo 'ShopEase CI pipeline failed!'
        }
    }
}