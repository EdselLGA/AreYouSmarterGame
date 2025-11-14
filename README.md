# Are You Smarter (Game Project)


AreYouSmarter/
│── src/
│   ├── main/
│   │   ├── Game.java                  # Main entry point
│   │   ├── ui/
│   │   │   ├── GameWindow.java        # Fullscreen window + CardLayout manager
│   │   │   ├── SplashScreenPanel.java # Animated splash screen (title pulse, clouds)
│   │   │   ├── MainMenuPanel.java     # Main menu with animated transitions
│   │   │   ├── GameScreenPanel.java   # Gameplay UI (questions, helpers, lifelines)
│   │   │   ├── ResultScreenPanel.java # End-of-game summary screen
│   │   │   └── HelpersUI.java         # UI effects (fading, throbbing, scaling)
│   │   ├── core/
│   │   │   ├── Question.java          # Represents a single question
│   │   │   ├── QuestionBank.java      # Stores questions by category
│   │   │   ├── GameLogic.java         # Handles scoring, progression, multipliers
│   │   │   ├── Lifeline.java          # Lifeline base class
│   │   │   ├── Peek.java              # Lifeline: view helper answer
│   │   │   ├── Copy.java              # Lifeline: copy helper answer
│   │   │   └── Save.java              # Lifeline: second chance mechanic
│   │   ├── player/
│   │   │   ├── Player.java            # Player data (name, winnings, lifelines)
│   │   │   └── Bot.java               # 5th grader bot with probability-based answers
│   │   └── utils/
│   │       ├── FileHandler.java       # Save/load high scores, user profiles
│   │       └── Randomizer.java        # Handles probabilities & random prize values
│   └── test/                          # Optional JUnit tests
│
├── assets/
│   ├── questions.json                 # Question data (categories + answers)
│   ├── title.png                      # Game title image
│   ├── press_start.png                # Press start prompt
│   ├── splash.png                     # Background image for screens
│   ├── Cloud.png                      # Cloud decoration (top corners)
│   ├── avatars/                       # Player avatars
│   └── sounds/                        # Music and SFX
│
├── docs/
│   ├── DESIGN.md                      # Architecture overview, UML
│   ├── TASKS.md                       # Work breakdown structure
│   └── WIREFRAMES.png                 # UI layout mockups
│
├── .gitignore
├── README.md
└── LICENSE
