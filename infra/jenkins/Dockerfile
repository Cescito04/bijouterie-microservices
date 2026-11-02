# Client Docker pour les builds exécutés par Jenkins.
FROM docker:27-cli AS docker-cli
FROM jenkins/jenkins:lts-jdk21
USER root
COPY --from=docker-cli /usr/local/bin/docker /usr/local/bin/docker
RUN jenkins-plugin-cli --plugins workflow-aggregator git credentials-binding pipeline-stage-view
COPY init.groovy.d/ /usr/share/jenkins/ref/init.groovy.d/
