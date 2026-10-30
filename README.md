# JPaint 🎨

A small desktop drawing application built with **Java Swing**, object-oriented shape models, and **SQLite** persistence. Refined from an earlier Java course project.

## Features

- Draw rectangles and circles by clicking and dragging.
- Choose colors from a palette and filter displayed shapes by type.
- Undo (`Ctrl+Z`) and redo (`Ctrl+Y`) drawing actions.
- Create and name drawings, view shape counts and geometry statistics.
- Save drawings to SQLite and load them by name and author.

## Requirements

- JDK 17 or later
- Apache Maven 3.8+

## Run

```bash
mvn compile exec:java
```

On the first run, Maven downloads the SQLite JDBC dependency. A local `jpaint.db` file is created when saving. This file is excluded from Git.

## Project structure

- `src/.../jpaint/geometry/` — shapes and geometry
- `src/.../jpaint/gui/` — Swing interface and interactions
- `src/.../jpaint/Drawing.java` — drawing model
- `src/.../jpaint/FileHandler.java` — SQLite persistence
- `img/` — application icon

## Notes

Undo/redo covers newly drawn shapes during the current drawing session. Starting or loading a drawing clears the redo history. The original educational package structure has been preserved.

## Screenshots

Add a screenshot of the running application here before publishing.
