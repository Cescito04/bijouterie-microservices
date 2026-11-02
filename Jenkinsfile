// Les images publiées portent le numéro du build Jenkins.
pipeline {
    agent any
    options {
        skipDefaultCheckout(true)
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }
    triggers { pollSCM('H/5 * * * *') }
    environment { DOCKERHUB_USER = 'cescito04' }
    stages {
        // Récupération de la branche configurée dans le job SCM.
        stage('Checkout') {
            steps { checkout scm }
        }
        // Construction des trois Dockerfiles fournis.
        stage('Build') {
            steps {
                sh '''
                    set -eu
                    docker build -t bijouterie-frontend:build-${BUILD_NUMBER} frontend/
                    docker build -t bijouterie-backend:build-${BUILD_NUMBER} backend/
                    docker build -t bijouterie-mysql:build-${BUILD_NUMBER} database/
                '''
            }
        }
        // Tag numérique pour le déploiement et tag Git pour la traçabilité.
        stage('Tag') {
            steps {
                sh '''
                    set -eu
                    short_commit=$(git rev-parse --short=12 HEAD)
                    for service in frontend backend mysql; do
                        docker tag bijouterie-${service}:build-${BUILD_NUMBER} ${DOCKERHUB_USER}/bijouterie-${service}:${BUILD_NUMBER}
                        docker tag bijouterie-${service}:build-${BUILD_NUMBER} ${DOCKERHUB_USER}/bijouterie-${service}:git-${short_commit}
                    done
                '''
            }
        }
        // Le token est fourni par Jenkins et le fichier de connexion est temporaire.
        stage('Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'REGISTRY_USER', passwordVariable: 'REGISTRY_TOKEN')]) {
                    sh '''
                        set +x
                        set -eu
                        export DOCKER_CONFIG=$(mktemp -d)
                        trap 'rm -rf "$DOCKER_CONFIG"' EXIT
                        printf '%s' "$REGISTRY_TOKEN" | docker login --username "$REGISTRY_USER" --password-stdin
                        short_commit=$(git rev-parse --short=12 HEAD)
                        for service in frontend backend mysql; do
                            docker push ${DOCKERHUB_USER}/bijouterie-${service}:${BUILD_NUMBER}
                            docker push ${DOCKERHUB_USER}/bijouterie-${service}:git-${short_commit}
                        done
                    '''
                }
            }
        }
    }
}
