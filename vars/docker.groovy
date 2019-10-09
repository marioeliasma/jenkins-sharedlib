def build(String imageName, String pathToDockerfile) {
    def DOCKER_HOME = tool name: "docker-17.09.1-ce", type: 'org.jenkinsci.plugins.docker.commons.tools.DockerTool'
    sh "${DOCKER_HOME}/bin/docker build -t ${imageName} ${pathToDockerfile}"
}

def publish(String imageName) {
    def DOCKER_HOME = tool name: "docker-17.09.1-ce", type: 'org.jenkinsci.plugins.docker.commons.tools.DockerTool'
    withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'password', usernameVariable: 'username')]) {
        sh "${DOCKER_HOME}/bin/docker login -u $USERNAME -p $PASSWORD"
        sh "${DOCKER_HOME}/bin/docker push marioelias14/${imageName}"
    }
}