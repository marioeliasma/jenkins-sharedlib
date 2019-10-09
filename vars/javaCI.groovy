def call() {

pipeline {

    environment {
        MAVEN_HOME = tool name: 'maven-3.6.2', type: 'maven'
        DOCKER_HOME = tool name: 'docker-17.09.1-ce', type: 'org.jenkinsci.plugins.docker.commons.tools.DockerTool'
    }
    checkout scm

    stages {
        stage("Test") {
            steps {
                sh "${MAVEN_HOME}/bin/mvn test"
            }
        }

        stage ("Build") {
            steps {
                sh "${MAVEN_HOME}/bin/mvn clean package" 
            }
        }

        stage("Deploy to Docker hub") {
            steps {
                docker.build("${DOCKER_HOME}","consultec-test:v1", "${WORSPACE}")
                docker.publish("${DOCKER_HOME}","consultec-test:v1")
            }
        }
    }
    
}
    
}
