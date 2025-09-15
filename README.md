# Turn-Based Snake [![Coverage Status](https://coveralls.io/repos/github/merie-san/snake/badge.svg?branch=main)](https://coveralls.io/github/merie-san/snake?branch=main) [![default workflow with maven](https://github.com/merie-san/snake/actions/workflows/default.yml/badge.svg?branch=main)](https://github.com/merie-san/snake/actions/workflows/default.yml) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=merie-san_snake&metric=coverage)](https://sonarcloud.io/summary/new_code?id=merie-san_snake)[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=merie-san_snake&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=merie-san_snake)[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=merie-san_snake&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=merie-san_snake)[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=merie-san_snake&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=merie-san_snake)
Maven project for the final exam of AST (Automated Software Testing), year 2024-2025.<br>
## Introduction
Turn-based snake is a minigame programmed using Java Swing. To win in in this game you have to control the snake using the arrows to eat as many apple as possible while avoiding obstacles. The app is made of four tabs, each of which implementing specific functionalities:
- Welcome Tab: you start from here.
- History Tab: you can browse your past games here.
- Settings Tab: you can personalize your snake games here.
- Match Tab: you play your games here.
## Building the APP
To build the project with maven on the root directory run 
```
mvn -f snake/pom.xml verify -Pcode-coverage,mutation-testing
```
If you only need the standalone jar file then run
```
mvn -f snake/pom.xml package
```
## Running the APP
To start the app you will need to provide an URL for a mySQL server, by specifying to the executable the host address, the port number, the username and the password.<br>
The available options are:
```
-H, --host	mySQL host address
-p, --port	mySQL host port
-d, --db-name	mySQL database name
-U, --username	mySQL login username
-P, --password	mySQL login password
-h, --help	print help
```
If not set, the default values will be used.


