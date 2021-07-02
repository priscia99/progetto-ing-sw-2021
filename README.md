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

### Libraries and plugins

| Library/Plugin  | Short description |
| ------------- | ------------- |
| JavaFX  | Graphic library used for GUI implementation |
| Maven  |  A build automation tool used primarily for Java projects |
| JUnit  | Unit testing framework |
| gson | A Java library that can be used to convert Java Objects into their JSON representation |

### Javadoc

For most methods, the relevant Javadoc documentation can be found within the source code.

### Coverage report

The most important methods of the model and the server controller were tested. 
No tests have been carried out for getters/setters and toString() methods.
Below is provided a screenshot with the coverage results for the tested classes.
*Last update: 2021/07/02 15:48*

| Element  | Class [%] | Method [%] | Line [%] |
| ------------- | ------------- | ------------- | ------------- |
| Controller  | 100% (2/2) | 71% (25/35) | 66% (127/192) |
| Model  | 97%  (44/45) | 71% (249/348) | 67% (767/1138) |

## Functionalities

- **Complete rules**
- **CLI** (Command Line Interface)
- **GUI** (Graphical User Interface) - created using JavaFX
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
java -jar masters_of_the_renaissance.jar [cli|gui|server] [IP]
```

#### Parameters
- Selected UI:
    - ```cli``` to play the game as a client with a Command Line Interface (CLI)
    - ```gui``` to play the game as a client with a Graphical User Interface (GUI)
    - ```server``` if you want to set up a server instance
- Server IP:
    - Provide the IP of the server, only for ```cli|gui```

#### Examples

If you want run the client as CLI, with the server hosted at ```192.168.1.55```, execute the Jar as follows:

```java -jar masters_of_the_renaissance.jar cli 192.168.1.55```

If you want run the client as GUI, with the server hosted at ```192.168.1.55```, execute the Jar as follows:

```java -jar masters_of_the_renaissance.jar gui 192.168.1.55```

If you want run a new server instance, execute the Jar as follows

```java -jar masters_of_the_renaissance.jar server```
    
## Cross-platform compatibility

### Windows

- The GUI client and server in Windows work as expected.
- Due to the usage of Unicode characters (in order to display fonts and colors), playing the game using CLI in Windows is still possible but may lead to problems in displaying characters.
  It is suggested to use Wsl or Ubuntu bash in Windows for a better user experience.
  
### Linux

- All CLI, GUI clients and server in Linux work as expected.

## Requirements

This project requires Java 16 or higher to run correctly.

    
