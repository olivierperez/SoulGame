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

- [ ] Pouvoir varier les paramètres en fonction du niveau
  - mana de départ / vitesse de consomation de mana
  - regain/perte de mana par convertion jusqu'au portail
  - regain/perte de mana par convertion au moment du contact
  - thème
      - sprites: characters / doors / walls
      - font
    
- [ ] Retravailler l'écran de sélection de niveau sous forme de grille

- [ ] Débloquer un niveau uniquement quand les objectifs du niveau précédent sont validés
    - objectifs
        - plus de X convertis
        - moins de Y convertis
        - jouer au moins X minutes
        - jouer au plus X minutes

- [ ] Avoir des niveaux de dimension différentes (pas que 16 x 12)

- [ ] Quand on convertit une âme
    - Jouer un petit son
    - Afficher des particules ?
    
- [ ] Quand une âme convertie passe la porte
    - Jouer un petit son
    - Afficher des particules !
    
- [ ] Corriger l'alignement des éléments des menus

- [ ] Attention au "level_1" codé en dur dans MainScene -> sceneManager.openLevel("level_1")