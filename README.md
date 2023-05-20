# Cabanator

Projet réalisé dans le cadre du cours Génie logiciel orienté objet / Analyse et conception des systèmes orientés objets (GLO-2004 / IFT-2007) du Baccalauréat en Informatique de l'Université Laval.

Technologies utilisées : Java et Java Swing.

## Introduction

Le projet consiste à produire une application permettant à un utilisateur de modéliser un cabanon. Cette application doit être en mesure d'informatiser le processus de design des quatres murs d'un cabanon (Nord, Sud, Est et Ouest), du plancher et du toit. Les modifications apportées par l'utilisateur sont appliquées en temps réel et affichées à l'écran. L'utilisateur apporte ces modifications à l'aide d'un panneau d'édition à l'écran.

À la fin du processus de conception, il est possible d'exporter la liste des pièces utilisées pour la construction du cabanon ainsi que le coût total projeté. Il est également possible de sauvegarder le projet et de charger un projet sauvegardé préalablement.

Les fonctionnalités pour chaque composante du cabanon sontt énumérées ci-bas. 

## Panneau principal / Cabanon

- Choix de la vue : mur (Nord, Sud, Est ou Ouest), plancher, toit (devant, face).
- Définir la largeur, la hauteur et la longueur du cabanon.
- Définir la taille maximale des pièces (192 pouces, soit 12 pieds par défaut).
- Afficher (ou non) la grille et définir sa taille.
- Définir le mode d'interaction avec les accessoires (sélection, ajout, suppression).

![cabanon](https://github.com/psopsopso/Cabanator/blob/main/Pictures/cabanon.png?raw=true)

### Murs

- Définir la distance entre les montants.
- Pour l'ouverture sélectionnée, définir sa hauteur, sa largeur, le choix de matériel du linteau (2x6, 2x8 ou 2x10) ainsi que la distance entre les montants supérieurs et inférieurs à l'ouverture (ces montants sont distincts des montants spécifiques au mur).
- Ajouter, sélectionner, déplacer ou supprimer les ouvertures.

![cabanon](https://github.com/psopsopso/Cabanator/blob/main/Pictures/mur.png?raw=true)

### Plancher

- Définir la distance entre les solives.
- Ajouter, sélectionner, déplacer ou supprimer les entremises.

![cabanon](https://github.com/psopsopso/Cabanator/blob/main/Pictures/plancher.png?raw=true)

### Toit

- Définir l'angle du toit.
- Définir la longueur du porte-à-faux.
- Définir la distance entre les fermes.
- Définir la distance entre les entremises.

Vue de dessus:

![cabanon](https://github.com/psopsopso/Cabanator/blob/main/Pictures/toitdessus.png?raw=true)

Vue de devant:

![cabanon](https://github.com/psopsopso/Cabanator/blob/main/Pictures/toitdevant.png?raw=true)

### Fonctionnalités supplémentaires / générales

- Supporter les opérations annuler / répéter (undo / redo) jusqu'à 9999 opérations.
- Permettre à l'utilisateur de zoomer / dézoomer à l'aide de la roulette de la souris.
- La gestion de toutes les mesures se fait en format impérial (nombre de pouces entiers ainsi qu'une fraction, i.e 96 pouces 11/64).
- Les portes, fenêtres et entremises sont déplaçables à l'aide de la souris.
- Si, lors du déplacement ou de l'ajout d'un des accessoires, ceux-ci entrent en collision ou se retrouvent superposés, l'application rend le cabanon invalide, indique quels accessoires causent l'invalidité et propose à l'utilisateur de supprimer les éléments fautifs.  
- Permettre à l’utilisateur de sauvegarder ses projets dans des fichiers en vue d’une réouverture pour utilisation future.
- L'application garde en mémoire les matériaux utilisés pour la construction du cabanon de l'utilisateur (2x4, 2x6, 2x8, etc) et permet à l'utilisateur d'exporter une liste des matériaux nécessaires ainsi que le prix total du cabanon.






