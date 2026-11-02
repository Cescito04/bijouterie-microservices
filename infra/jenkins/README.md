# Jenkins local

Démarrage : `docker compose -f infra/jenkins/compose.yaml up -d --build`.
Interface : http://localhost:8081 ; utilisateur : admin. Le mot de passe local est dans `.secrets/admin-password`, exclu de Git.

Le volume conserve la configuration Jenkins. Le client Docker utilise le moteur Docker du Mac via son socket. Pour ce TP local, l’exécuteur intégré lance les builds avec accès au moteur ; un environnement de production utiliserait des agents isolés.

Créer dans Jenkins un credential global « Username with password », ID `dockerhub-credentials`, avec l’utilisateur `cescito04` et un token Docker Hub Read & Write comme mot de passe.

Le job `bijouterie` lit le `Jenkinsfile` de la branche main. Le pipeline vérifie Git toutes les cinq minutes après son premier lancement. Les images reçoivent le numéro de build et un tag Git. Les manifests Kubernetes restent à mettre à jour après chaque publication.
