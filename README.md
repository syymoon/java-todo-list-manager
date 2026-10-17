# To-do List Manager (Java)

A small command-line application for creating and managing tasks. This beginner learning project practices Java classes, lists, loops, input handling and file storage.

## Features

- Add a task with a title
- List tasks and show which ones are completed
- Mark a task as completed by its ID
- Delete a task by its ID
- Save tasks automatically and load them the next time the program runs
- Handle empty task titles, incorrect menu choices and invalid IDs

## Requirements

- JDK 17 or newer (tested with JDK 21)
- No external libraries

## Run locally

Open a terminal **inside the project folder**.

**Windows PowerShell:**

```powershell
javac -encoding UTF-8 -d out src/Main.java src/Task.java src/TaskManager.java
java -cp out Main
```

**macOS / Linux:**

```bash
javac -encoding UTF-8 -d out src/*.java
java -cp out Main
```

Example:

```text
=== TO-DO LIST MANAGER ===
1. Show tasks
2. Add task
3. Mark task as completed
4. Delete task
5. Exit
Choose an option: 2
Task title: Practice Java
Task added.
```

## Project files

- `src/Main.java` — main menu and user input
- `src/Task.java` — a single task: ID, title, completion status
- `src/TaskManager.java` — methods that manage and save tasks
- `data/tasks.txt` — automatically generated; excluded from Git
- `.gitignore` — keeps generated files out of the repository

## How saving works

Tasks are saved as lines in `data/tasks.txt`. Each line contains the task ID, its completion status and an encoded title, separated by semicolons. `URLEncoder` and `URLDecoder` keep titles with characters such as `;` or `+` safe. This format is retained so older saved tasks continue to work.

## Concepts practiced

Classes, objects, constructors, private fields, methods, `ArrayList`, `for` loops, `if` / `else`, `Scanner`, file operations and simple exception handling.

## Possible next steps

- Count how many tasks are still open
- Add a way to rename a task
- Sort tasks by completion status

