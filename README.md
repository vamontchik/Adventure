# Adventure
A console-based RPG. Utilization of JUnit 4 for testing suite and GSON for JSON file parsing.

### Organization

/data/ holds the JSON files from which the maps are generated, including test maps.

/lib/ holds 3rd party jar files needed for compilation

/src/ holds the project's source files, organized as:
    
    /src/command/ ---> All commands (including base abstract ones) that can be run
    /src/console/ ---> Abstraction of the "UI"
    /src/data/ ---> POJOs that hold the data of the game
    /src/error/ ---> Exceptions that can be thrown due to faulty game state
    /src/game/ ---> Starting point for the game, housing the main loop
    /src/json/ ---> Parsing of JSON files, using GSON
    /src/validator/ ---> Validates that the given layout can be finished by recursively 
                         following all directions out of the starting zone
                         
/test/ holds the unit tests for the various parts of the program
