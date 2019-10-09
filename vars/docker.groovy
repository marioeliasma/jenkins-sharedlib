def build(String dockerBin, String imageName, String pathToDockerfile) {
    sh "${dockerBin} build -t ${imageName} ${pathToDockerfile}"
}

def publish(String dockerBin, String imageName) {
    withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'password', usernameVariable: 'username')]) {
        sh "${dockerBin} login -u $USERNAME -p $PASSWORD"
        sh "${dockerBin} push marioelias14/${imageName}"
    }
}