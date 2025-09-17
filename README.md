# Are You Smarter (Game Project)


AreYouSmarter/
│── src/
│   ├── main/
│   │   ├── Game.java              # Main entry point
│   │   ├── ui/
│   │   │   ├── MainMenu.java      # Main menu screen
│   │   │   ├── GameScreen.java    # Gameplay UI (questions, lifelines)
│   │   │   ├── ResultScreen.java  # End game summary
│   │   │   └── HelpersUI.java     # Avatars & helpers
│   │   ├── core/
│   │   │   ├── Question.java      # Question model (category, text, choices, answer)
│   │   │   ├── QuestionBank.java  # Stores questions by category
│   │   │   ├── GameLogic.java     # Handles scoring, winnings, progression
│   │   │   ├── Lifeline.java      # Abstract lifeline
│   │   │   ├── Peek.java          # Lifeline implementation
│   │   │   ├── Copy.java
│   │   │   └── Save.java
│   │   ├── player/
│   │   │   ├── Player.java        # Player profile, winnings, lifelines
│   │   │   └── Bot.java           # 5th grader bot (for PvB mode)
│   │   └── utils/
│   │       ├── FileHandler.java   # Save/load high scores
│   │       └── Randomizer.java    # Probability system for winnings
│   └── test/                      # JUnit tests (optional for validation)
│
├── assets/
│   ├── questions.json             # Store questions & answers
│   ├── avatars/                   # Avatar images
│   └── sounds/                    # Background music, sound effects
│
├── docs/
│   ├── DESIGN.md                  # Architecture, class diagrams
│   ├── TASKS.md                   # Work breakdown & deadlines
│   └── WIREFRAMES.png             # UI mockups
│
├── .gitignore
├── README.md
└── LICENSE


