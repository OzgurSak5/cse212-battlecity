# Battle City — CSE212 Term Project

A Java/Swing clone of the classic *Battle City* (Tank 1990), developed as the
Spring 2025 term project for **CSE212 — Software Development Methodologies**
at Yeditepe University.

The player controls a tank and must defend the base (eagle) while destroying
20 enemy tanks per level across three increasingly difficult stages, with a
custom **bonus level** designed in the in-game Map Editor as the final challenge.

---

## Features

- **Retro title screen** with a brick-tiled "BATTLE CITY" logo, tank-cursor
  menu navigable by W/S or arrow keys, and Enter/Space to select
- **Four levels** — three campaign maps (Easy / Medium / Hard) plus an
  optional bonus map designed in the editor
- **Custom map mode** — *Play Custom Map* from the title screen launches any
  user-designed `.txt` map as a standalone level
- **Built-in Map Editor** with drag-to-paint, file save/load, and a dedicated
  *Save as Bonus* button that validates the map (exactly one base, walkable
  player spawn) before writing it
- **All six power-ups** from the original game:
  - **Tank** — extra life
  - **Star** — tiered upgrade: 1 = faster bullets, 2 = two simultaneous shots,
    3 = ability to destroy steel walls
  - **Grenade** — destroys all enemies currently on screen
  - **Clock** — freezes all enemies for a limited time
  - **Shovel** — temporarily surrounds the base with steel walls
  - **Helmet** — temporary invulnerability shield
- **Bush concealment** — tanks moving under green bushes are hidden, as in
  the original (rendered as a foreground overlay)
- **Score system** — name, score, time, and date are written to `scores.csv`
  at the end of each run; the High Scores screen shows the top 10
- **Pause / Resume**, Help, About, and full menu bar (New Game, Map Editor,
  Options, High Scores, Main Menu, Exit)

---

## Controls

| Key            | Action                          |
|----------------|---------------------------------|
| **W A S D**    | Move tank (up / left / down / right) |
| **Space**      | Shoot                           |
| **P**          | Pause / Resume                  |
| **Enter**      | Confirm (title screen)          |

---

## How to Run

The project is an Eclipse Java project.

1. Import into Eclipse (`File → Import → Existing Projects into Workspace`).
2. Run `src/edu/yeditepe/cse212/battlecity/app/Main.java`.

The application loads its assets and levels using paths **relative to the
project root** (`resources/battle_city_sprites/...`, `levels/level*.txt`,
`scores.csv`). Eclipse uses the project root as the working directory by
default, so no extra configuration is needed.

**Java version:** JavaSE-1.8 or higher.

---

## Project Structure

```
term_project_BattleCity/
├── src/edu/yeditepe/cse212/battlecity/
│   ├── app/             # Main entry point
│   ├── screen/          # TitleScreenFrame, GameFrame, GamePanel,
│   │                    # SidePanel, dialogs (Help, About, Options, HighScore)
│   ├── menu/            # GameMenuBar
│   ├── editor/          # MapEditorFrame, MapEditorPanel, TileType
│   ├── game/            # GameLoop, EnemyManager, EnemyAIThread,
│   │                    # Bullet, CollisionManager, Level, GameConstants
│   ├── map/             # GameMap, MapManager, Position
│   ├── tank/            # Tank (abstract), PlayerTank, EnemyTank
│   ├── tile/            # Tile (abstract), BrickWall, SteelWall,
│   │                    # Water, Bush, BaseTile, EmptyTile
│   ├── powerup/         # PowerUp (abstract) + 6 subclasses,
│   │                    # PowerUpManager, PowerUpEffectThread
│   ├── score/           # ScoreManager, ScoreRecord
│   ├── exception/       # Custom exceptions
│   └── util/            # ImageLoader (with HashMap caching)
├── levels/              # level1.txt, level2.txt, level3.txt, bonus.txt
├── resources/
│   └── battle_city_sprites/   # Tile, tank, power-up, title sprites
└── scores.csv           # Persistent score storage
```

---

## Course Concepts Demonstrated

This project deliberately stays within the topics covered in CSE212:

- **Inheritance & Polymorphism** — abstract `Tile`, `Tank`, and `PowerUp`
  hierarchies, each with overridden `getSprite()`, `getColor()`, and
  behavior methods
- **Collections** — `ArrayList` for bullets/enemies/power-ups, `HashMap`
  for sprite caching in `ImageLoader`
- **Threads** — `GameLoop`, `EnemyManager`, per-enemy `EnemyAIThread`, and
  `PowerUpEffectThread` for timed effects (clock freeze, shield, shovel)
- **Exception handling** — custom `MapLoadException`, `ScoreFileException`
- **Anonymous inner classes** — used throughout for `ActionListener`,
  `KeyListener`, and `MouseAdapter` event handlers
- **File I/O** — map files, CSV score storage

---

## Author

**Özgür Sak** — 20240702066
ozgur.sak@std.yeditepe.edu.tr
CSE212 — Spring 2026, Yeditepe University
