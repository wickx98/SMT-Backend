pipeline {
    agent any
    environment {
        DOCKER_IMAGE = 'wickx98/weatherapp'
        DOCKER_HUB_USERNAME = 'wickx98'
        GIT_REPO_URL = 'https://github.com/wickx98/weatherApp.git'
    }
    stages {
        stage('Checkout') {
            steps {
                git url: "${GIT_REPO_URL}", branch: 'main'
            }
        }

        stage('Check Docker Installation') {
            steps {
                script {
                    def dockerVersion = bat(script: 'docker --version', returnStdout: true).trim()
                    echo "Docker version: ${dockerVersion}"
                }
            }
        }

        stage('Check and Install Maven') {
            steps {
                script {
                    def mvnInstalled = bat(script: 'mvn -v', returnStatus: true)
                    if (mvnInstalled != 0) {
                        echo 'Maven is not installed. Installing Maven...'
                        bat 'choco install maven'
                    } else {
                        echo 'Maven is already installed.'
                    }
                }
            }
        }

        stage('Build Spring Boot App') {
            steps {
                script {
                    echo 'Building Spring Boot application...'
                    bat './mvnw.cmd clean install -DskipTests'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    def tag = "${DOCKER_IMAGE}:latest"
                    echo 'Building Docker image...'
                    docker.build(tag)
                }
            }
        }
        stage('security scan'){
            steps {
                 script {
                    echo 'Security scan...'
                    bat """
                         docker scout cves ${DOCKER_IMAGE}:latest
                         """
                }
            }
        }
        stage('Connect to Docker Hub') {
            steps {
                script {
                    try {
                        echo 'Logging into Docker Hub using credentials...'
                        // Docker login using the provided credentials
                        withCredentials([string(credentialsId: 'dockerHubPass', variable: 'dockerPass')]) {
                            bat """
                                docker login -u ${DOCKER_HUB_USERNAME} -p ${dockerPass}
                            """
                        }
                        echo 'Successfully logged into Docker Hub!'
                    } catch (Exception e) {
                        echo "Docker login failed: ${e.message}"
                        currentBuild.result = 'FAILURE'
                        throw e
                    }
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                script {
                    try {
                        def tag = "${DOCKER_IMAGE}:latest"
                        echo 'Pushing Docker image to Docker Hub...'
                        docker.image(tag).push()

                    } catch (Exception e) {
                        echo "Push failed: ${e.message}"
                        currentBuild.result = 'FAILURE'
                        throw e
                    }
                }
            }
        }
    }
    post {
        success {
            echo 'Build and push to Docker Hub completed successfully!'
        }
        failure {
            echo 'Build or push failed.'
        }
    }
}
