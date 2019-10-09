def call() {

pipeline {

    environment {
        MAVEN_HOME = tool name: 'maven-3.6.2', type: 'maven'
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
                docker.build("consultec-test", WORSPACE)
                docker.publish("consultec-test")
            }
        }
    }
    
}
    
}
