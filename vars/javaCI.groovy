def call() {

    node {

        def MAVEN_HOME = tool name: 'maven-3.6.2', type: 'maven'
        def DOCKER_HOME = tool name: 'docker-17.09.1-ce', type: 'org.jenkinsci.plugins.docker.commons.tools.DockerTool'
        
        stage("Test") { 
            checkout scm
            sh "${MAVEN_HOME}/bin/mvn test"
        }

        stage ("Build") {
            sh "${MAVEN_HOME}/bin/mvn clean package" 
        }

        stage("Deploy to Docker hub") {
            docker.build("${DOCKER_HOME}","consultec-test:v1", "${WORSPACE}")
            docker.publish("${DOCKER_HOME}","consultec-test:v1")
        }
    }
 
}
