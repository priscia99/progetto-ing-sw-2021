# ing-sw-2021-prisciantelli-romagnoni-pizzamiglio

# Prova finale - Progetto di Ingegneria del Software - A.A. 2020/2021

Implementation of the board game 'Masters of the Renaissance' for PC.

The project consists in the creation of a system composed by a single server, capable of managing several games (organised in lobbies), to which several clients can connect.

Clients can connect to the server and play either via command line (CLI) or graphical interface (GUI).

## Documentation

### UML diagrams

The UML diagrams can be accessed via the following links:

- Initial UML
- Final UML

### Javadoc

For most methods, the relevant Javadoc documentation can be found within the source code.

### Coverage report

The most important methods of the model and the server controller were tested. 
No tests have been carried out for getters/setters and toString() methods.
Below is provided a screenshot with the coverage results for the tested classes.

## Functionalities

- **Complete rules**
- **CLI** (Command Line Interface)
- **GUI** (Graphical User Interface)
- 2 FA (additional functionalities):
  - **Multiple games**: 
    The server can handle several games simultaneously. Players can create or join lobbies by giving a valid Lobby ID.
  - **Resilience to disconnections**: Players can disconnect and reconnect later and continue the game. When a player is not connected, the game continues by skipping that player's turns.

## Jar Execution

### Finding Jar

Jar can be found by moving to *shade* folder.

```
cd shade
```

### Jar execution

Execute the Jar by typing this command in the terminal.

```
java -jar masters_of_the_renaissance.jar [Selected UI] [Server]
```

#### Parameters
- Selected UI:
    - ```cli``` to play the game as a client with a Command Line Interface (CLI)
    - ```gui``` to play the game as a client with a Graphical User Interface (GUI)
    - ```server``` if you want to set up a server instance
- Server:
    - ```local``` if you want to play the game with a server in the same PC. (IP is 127.0.0.1)
    - When no value is provided as ```[Server]``` parameter, the client tries to connect to the remote hosted server.
    
## Cross-platform compatibility

### Windows

- The GUI client and server in Windows work as expected.
- Due to the usage of Unicode characters (in order to display fonts and colors), playing the game using CLI in Windows is still possible but may lead to problems in displaying characters.
  It is suggested to use Wsl or Ubuntu bash in Windows for a better user experience.
  
### Linux

- All CLI, GUI clients and server in Linux work as expected.

## Requirements

This project requires Java 16 or higher to run correctly.

    
