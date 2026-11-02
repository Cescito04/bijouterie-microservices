# Argo CD

Installation dans le contexte kind-bijouterie :

```sh
kubectl --context kind-bijouterie create namespace argocd
kubectl --context kind-bijouterie apply --server-side -n argocd -f "https://raw.githubusercontent.com/argoproj/argo-cd/$(cat infra/argocd/version.txt)/manifests/install.yaml"
kubectl --context kind-bijouterie apply -f argocd/application.yaml
kubectl --context kind-bijouterie port-forward -n argocd service/argocd-server 8082:443
```

Interface : https://localhost:8082 ; compte admin. Le mot de passe initial est conservé dans le Secret argocd-initial-admin-secret du namespace argocd. Ne pas l’ajouter au dépôt ni aux captures.

L’Application surveille main et le dossier k8s, avec synchronisation automatique, prune et selfHeal. Après cette installation, les modifications des ressources Bijouterie passent par Git. Ne plus utiliser kubectl apply dans le namespace bijouterie. Les suppressions manuelles servent uniquement aux tests d’autoréparation.
