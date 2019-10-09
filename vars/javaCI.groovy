def call() {

    node {

        def MAVEN_HOME = tool name: 'maven-3.6.2', type: 'maven'
        def DOCKER_HOME = tool name: "docker-17.09.1-ce", type: 'org.jenkinsci.plugins.docker.commons.tools.DockerTool'
        
        stage("Quality") {
            pom = readMavenPom file: 'pom.xml'
            withCredentials([string(credentialsId: 'sonar', variable: 'secret')]) {
                sh "${MAVEN_HOME}/bin/mvn sonar:sonar -Dsonar.projectKey=${pom.groupId}:${pom.artifactId} -Dsonar.login=${secret} -Dsonar.host.url=http://172.16.30.117:9000"
            }
        }
        stage("Test") { 
            checkout scm
            sh "${MAVEN_HOME}/bin/mvn test"
        }

        stage ("Build") {
            sh "${MAVEN_HOME}/bin/mvn clean package" 
        }

        stage("Deploy to Docker hub") {
            pom = readMavenPom file: 'pom.xml'
            withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'password', usernameVariable: 'username')]) {
                sh "${DOCKER_HOME}/bin/docker build -t $username/${pom.artifactId}:${pom.version} ."
                sh "${DOCKER_HOME}/bin/docker login -u $username -p $password"
                sh "${DOCKER_HOME}/bin/docker push $username/${pom.artifactId}:${pom.version}"
            }
        }

        stage('Security') {
          sh 'mvn org.owasp:dependency-check-maven:check -Dformat=XML -DautoUpdate=false'
          step([$class: 'DependencyCheckPublisher', unstableTotalAll: '0'])
        }

    }
 
}
