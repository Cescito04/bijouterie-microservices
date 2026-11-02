# Cluster local Bijouterie

Créer le cluster :

```sh
kind create cluster --name bijouterie --image kindest/node:v1.36.1 --config infra/kind/cluster.yaml --wait 120s
kubectl --context kind-bijouterie apply -f k8s/
kubectl --context kind-bijouterie rollout status deployment -n bijouterie --timeout=180s
```

Le port 30080 du Mac est publié vers le port 30080 du nœud kind. Le Service frontend utilise le même NodePort. Le site est accessible sur http://localhost:30080 sans `kubectl port-forward`.

Le port local doit être libre avant la création du cluster. Le contexte de travail est `kind-bijouterie`. Le cluster `docker-desktop` reste distinct ; préciser le contexte dans les commandes évite de déployer dans le mauvais cluster.

Ce nouveau cluster démarre avec sa propre base MySQL et les données de démonstration. Aucun volume persistant n’est configuré pour MySQL.
