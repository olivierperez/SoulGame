- [x] Corriger la hauteur du text renvoyée par TextRender
- [x] Afficher un menu de game-over pour relancer

- [x] Kotlin 1.5

- [x] Se souvenir du best score

- [x] Laisser le choix du niveau au joueur

- [x] Mettre le jeu en pause quand on appuie sur escape

- [x] Au moment où la scène de jeu s'affiche
    - Décompte "3... 2... 1..." (le jeu est en freeze)
    - "Capturez-les tous !" (le jeu démarre)

- [x] i18n-er le jeu

- [x] Pouvoir varier les paramètres en fonction du niveau
  - [x] mana de départ / vitesse de consommation de mana
  - [x] regain/perte de mana par convertion jusqu'au portail
  - [x] regain/perte de mana par convertion au moment du contact
  - [x] thème
      - sprites: characters / doors / walls
      - font
    
- [x] Retravailler l'écran de sélection de niveau sous forme de grille

- [x] Chaque niveau peut choisir une condition de fin
  - Par exemple, le niveau 1 s'arrête quand le joueur n'a plus de mana
  - Mais un autre niveau pourrait se terminer quand le joueur a atteint un certain montant de mana
  - `EndWhen=[mana]<=0` ou `EndWhen=[mana]>=5000`

- [ ] Débloquer un niveau uniquement quand les objectifs du niveau précédent sont validés
    - objectifs
        - plus de X convertis
        - moins de Y convertis
        - jouer au moins X minutes
        - jouer au plus X minutes
    - idées
      `Objectif.Score=min(50)`
      `Objectif.Ticks=min(200)`

- [ ] Avoir des niveaux de dimension différentes (pas que 16 x 12)

- [ ] Quand on convertit une âme
    - Jouer un petit son
    - Afficher des particules ?
    
- [ ] Quand une âme convertie passe la porte
    - Jouer un petit son
    - Afficher des particules !
    
- [ ] Corriger l'alignement des éléments des menus

- [ ] Attention au "1" codé en dur dans MainScene -> sceneManager.openLevel("1")