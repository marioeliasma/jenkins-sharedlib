def call() {

    node {

        def MAVEN_HOME = tool name: 'maven-3.6.2', type: 'maven'
        def dockerTool = "docker-17.09.1-ce"
        
        
        stage("Test") { 
            checkout scm
            sh "${MAVEN_HOME}/bin/mvn test"
        }

        stage ("Build") {
            sh "${MAVEN_HOME}/bin/mvn clean package" 
        }

        stage("Deploy to Docker hub") {
            docker.build("consultec-test:v1", "/Dockerfile")
            docker.publish("consultec-test:v1")
        }
    }
 
}
