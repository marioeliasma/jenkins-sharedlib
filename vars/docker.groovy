def build(String imageName, String pathToDockerfile) {
    sh "docker build -t ${imageName} ${pathToDockerfile}"
}

def publish(String imageName) {
    withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'password', usernameVariable: 'username')]) {
        sh "docker login -u $USERNAME -p $PASSWORD"
        sh "docker push marioelias/${imageName}"
    }
}