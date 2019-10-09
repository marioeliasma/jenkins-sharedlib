def call() {

    node {

        def MAVEN_HOME = tool name: 'maven-3.6.2', type: 'maven'
        
        
        stage("Test") { 
            checkout scm
            sh "${MAVEN_HOME}/bin/mvn test"
        }

        stage ("Build") {
            sh "${MAVEN_HOME}/bin/mvn clean package" 
        }

        stage("Deploy to Docker hub") {
            docker.build("${DOCKER_HOME.toString()}/bin/docker", "consultec-test:v1", "/Dockerfile")
            docker.publish("${DOCKER_HOME.toString()}/bin/docker","consultec-test:v1")
        }
    }
 
}
