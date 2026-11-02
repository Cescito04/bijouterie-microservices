# Captures du projet Bijouterie

Enregistrer ici les captures au fur et à mesure des manipulations. Utiliser des noms numérotés et explicites, par exemple `01-local-catalogue.png` et `02-docker-compose-ps.png`.

Chaque capture doit être lisible, titrée et accompagnée d'une explication dans le rapport. Ne jamais afficher de mot de passe, token, contenu du fichier `.env` ou valeurs des Secrets.

## Suivi des preuves

Remplir les colonnes après avoir réellement effectué la manipulation. Une ligne prévue ne constitue pas une preuve de réalisation.

| Fichier prévu | Manipulation effectuée | Ce que montre la capture | Ce qu'elle prouve |
| --- | --- | --- | --- |
| 01-local-catalogue.png | À compléter | À compléter | À compléter |
| 02-docker-compose-ps.png | À compléter | À compléter | À compléter |

Ajouter une ligne par nouvelle capture. Pour un test avant/après, conserver les deux images.

## Fiche à reprendre pour chaque capture

- Numéro et titre :
- Nom du fichier :
- Manipulation effectuée :
- Résultat observé :
- Ce que la capture prouve :
- Commentaire à intégrer au rapport :

## Exemple de légende

Figure X — État des services en local. La commande `docker compose ps` affiche les services frontend, backend et MySQL en cours d'exécution. Cette capture atteste de leur démarrage ; les tests dans le navigateur vérifient ensuite le fonctionnement de l'application.

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
