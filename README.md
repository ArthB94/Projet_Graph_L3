# Projet Théorie des Graphes L3

**Projet à réaliser par équipes de 5 étudiants.**
**Pour le réaliser nous avons choisi de le coder en java.**

## Programme à developper
Votre programme se déroule en plusieurs étapes :
1. Lecture d’un tableau de contraintes contenu dans un fichier .txt, mise en
mémoire et affichage de ce tableau sur l’écran ;
2. Construction d’un graphe correspondant à ce tableau de contraintes ;
3. Vérification du fait que ce graphe possède toutes les propriétés nécessaires pour
qu’il soit un graphe d’ordonnancement :
+ Un seul point d’entrée
+ Un seul point de sortie
+ Pas de circuit
+ Valeurs identiques pour tous les arcs incidents vers l’extérieur à un
sommet,
+ Arcs incidents vers l’extérieur au point d’entrée ont une valeur nulle,
+ Pas d’arcs à valeur négative.
4. Si toutes ces propriétés sont vérifiées, calculer le calendrier au plus tôt, la durée
totale de projet, le calendrier au plus tard et les marges.
Pour le calcul du calendrier au plus tard, utilisez la convention que la date au
plus tard de fin de projet soit égale à sa date au plus tôt.
Comme vous savez, pour le calcul des calendriers il faut d’abord effectuer le
tri topologique du graphe : ordonner les sommets dans l’ordre des rangs
croissants. Il faut donc affecter un rang à chaque sommet, en utilisant un
algorithme de votre choix parmi ceux que vous avez vu en cours.

## Déroulement du programme
Mettre en place un programme qui exécute les actions suivantes préalables à l’ordonnancement :
1. Lecture d’un tableau de contraintes donné dans un fichier texte ( .txt) et stockage en mémoire
2. Affichage du graphe correspondant sous forme matricielle (matrice des valeurs). Attention : cet
affichage doit se faire à partir du contenu mémoire, et non pas directement en lisant le fichier.
Ce graphe doit incorporer les deux sommets fictifs a et w (notés 0 et N+1 où N est le nombre
de tâches).
3. Vérifier les propriétés nécessaires du graphe pour qu’il puisse servir d’un graphe
d’ordonnancement :
- pas de circuit,
- pas d’arcs à valeur négative.
Si la réponse à la question 3 est « Oui », procéder au calcul des calendriers :
4. Calculer les rangs de tous les sommets du graphe.
5. Calculer le calendrier au plus tôt, le calendrier au plus tard et les marges.
Pour le calcul du calendrier au plus tard, considérez que la date au plus tard de fin de projet
est égale à sa date au plus tôt.
6. Calculer le(s) chemin(s) critique(s) et les afficher