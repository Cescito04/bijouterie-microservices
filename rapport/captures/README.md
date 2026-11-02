# Captures du projet Bijouterie

Captures et résultats des vérifications. Les images ne doivent afficher aucun mot de passe ni token.

## Tests locaux réalisés le 1er novembre 2026

| Preuve enregistrée | Résultat observé | Commentaire pour le rapport |
| --- | --- | --- |
| `01-local-catalogue.jpg` | 9 bijoux après ajout de Bague Teranga — test local | L'ajout avec une image PNG de test fonctionne ; le catalogue affiche le nouveau bijou. L'image est une illustration, pas une photographie. |
| `02-docker-compose-ps.png` | Trois services actifs et healthy | La capture du terminal confirme que les trois services Docker Compose sont actifs et sains. |
| `03-local-modification.jpg` | Formulaire avec prix 47500 et stock 3 | Le formulaire charge le bijou et son image ; les nouvelles valeurs sont saisies avant enregistrement. |
| `04-local-modification-resultat.jpg` | Prix 47500 FCFA et stock 3 dans le catalogue | La modification a bien été enregistrée. Valeurs initiales : prix 45000, stock 2. |
| `05-local-suppression.jpg` | Retour à 8 bijoux après suppression du bijou de test | Le test de suppression a réussi. Les huit bijoux initiaux ont été conservés. |

L'application est accessible sur http://localhost:8080. Aucun code applicatif n'a été modifié pour ces tests.


## Kubernetes et Docker Hub — 2 novembre 2026

- `06-kubernetes-noeud.png` : contexte docker-desktop et nœud desktop-control-plane Ready ; le cluster est disponible.
- `dockerhub-depots-publics.jpg` : création des trois dépôts publics.
- `dockerhub-frontend-tags.jpg`, `dockerhub-backend-tags.jpg`, `dockerhub-mysql-tags.jpg` : publication des trois images sous le tag initial-1. Ce tag initial a été publié manuellement ; les builds Jenkins fourniront ensuite leurs propres tags.

## Déploiement Kubernetes

Les 11 manifests de `k8s/` ont été appliqués au contexte `docker-desktop`. Lors de la vérification, les cinq pods étaient Running et prêts. Les sondes vérifient notamment `/api/ready`, qui confirme la connexion MySQL.

Conflit de port : le Service `wordpress/wordpress-service` occupait déjà le NodePort 30080. Il a été déplacé vers 30081 avant de créer le Service frontend Bijouterie sur 30080.

Sur cette installation, l'accès direct à localhost:30080 a refusé la connexion malgré les pods prêts. Un tunnel local `kubectl --context docker-desktop port-forward -n bijouterie service/frontend 30080:80` permet l'accès au navigateur. Une capture obtenue avec ce tunnel prouve le fonctionnement via port-forward, pas l'accessibilité directe du NodePort. Le routage direct du NodePort reste à résoudre pour la démonstration correspondante du TP.

Les mots de passe Kubernetes sont générés pour ce TP et encodés en base64 dans les deux Secrets ; ils ne sont pas les identifiants du Docker Compose.

## Ajout sur Kubernetes

`12-kubernetes-ajout-photo.jpg` : ajout de « Bague Teranga Kubernetes », 18 carats, 3,5 g, 45000 FCFA, stock 2. Le catalogue passe de 8 à 9 bijoux et affiche l’image PNG jointe (illustration de test). Accès par le tunnel local sur le port 30080 ; cette capture ne démontre pas le routage direct du NodePort.

## Modification sur Kubernetes

`13-kubernetes-modification-formulaire.jpg` et `13-kubernetes-modification.jpg` : le prix de Bague Teranga Kubernetes passe de 45000 à 47500 FCFA et le stock de 2 à 3. Le catalogue affiche les nouvelles valeurs après enregistrement ; l’image est conservée.


## Tests Kubernetes terminés

| Capture | Résultat |
| --- | --- |
| `14-kubernetes-suppression.jpg` | Création puis suppression du bijou temporaire par API REST ; 9 bijoux restent dans le catalogue. |
| `15-changement-shop-name.jpg` | SHOP_NAME passe à Maison Amina Dakar après application de la ConfigMap et rollout restart du backend. |
| `16-recreation-pod-backend.jpg` | Le pod supprimé est remplacé et les deux replicas sont prêts. |
| `17-repartition-backend.jpg` | Deux valeurs de servedBy observées via le Service ClusterIP backend. |
| `18-mysql-avant.jpg` | 9 bijoux, dont Bague Teranga Kubernetes avec son image. |
| `19-mysql-apres.jpg` | 8 bijoux de démonstration ; la bague ajoutée a disparu après recréation du pod MySQL. |

Les captures 14, 16 et 17 montrent les sorties réelles enregistrées des tests, présentées dans une page locale ; ce ne sont pas des captures du Terminal. Les sorties sources sont conservées dans `rapport/preuves/`. La répartition est testée depuis le frontend vers le Service backend ; le tunnel port-forward ne démontre pas le routage direct du NodePort.

Sans volume persistant, le nouveau conteneur MySQL réinitialise sa base à partir du script init.sql. Les bijoux ajoutés et leurs images sont perdus. Les ConfigMaps et Secrets restent présents, donc le nom de boutique et les identifiants sont conservés. La capture 12 utilise une illustration PNG de test et non une photographie.

## Vérification de l’accès direct au NodePort

Le 2 novembre 2026, après arrêt du tunnel, `curl http://localhost:30080/api/ready` renvoie une connexion refusée. L’adresse du nœud `172.22.0.2:30080` ne répond pas depuis le Mac. La recréation du Service frontend ne corrige pas le problème. Le tunnel a été rétabli pour conserver l’accès local. L’accès direct reste à configurer au niveau du réseau du cluster ; aucune réinitialisation du cluster existant n’a été effectuée.
