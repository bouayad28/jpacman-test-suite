<img src=img/jpacman_logo.png alt="The Green Thumb Logo" width="129" height="133">

--------------------------------------------------------------------------------
[![MIT License](https://img.shields.io/badge/license-Apache_2.0-green.svg)](https://github.com/johanneshagspiel/jpacman-test-suite/Apache-2.0-LICENSE.txt)
[![Top Language](https://img.shields.io/github/languages/top/johanneshagspiel/jpacman-test-suite)](https://github.com/johanneshagspiel/jpacman-test-suite)
[![Latest Release](https://img.shields.io/github/v/release/johanneshagspiel/jpacman-test-suite)](https://github.com/johanneshagspiel/jpacman-test-suite/releases/)

# JPacman Test Suite

This repository contains the test suite created for the "JPacman" game. "JPacman" was originally developed by [Arie van Deursen](https://github.com/avandeursen) and is a recreation of the "Pacman" game in Java.

## Features

The test suite contains a variety of different test such as:

- unit tests made with [JUnit](https://junit.org/junit5/)
- boundary tests using a 1x1 domain testing strategy
- mock tests made with [Mockito](https://site.mockito.org/) to increase observability and controllability
- fuzz testing to determine a potential security vulnerability of a plugin
- system tests based on system requirements
- model based tests, for which first a UML state machine was created which then was turned into a transition tree and then finally into a transition table

Most of these tests were created directly on the basis of user stories in line with agile methodologies. Finally, "JPacman's" functionality was extended in a test-driven manner in order to support multi-level games.

## Tools

| Purpose               | Name                                                                                                                     |
|-----------------------|--------------------------------------------------------------------------------------------------------------------------|
| Programming language  | [Java](https://openjdk.org/)                                                                                             |
| Dependency manager    | [Gradle]()                                                                                                               |
| Version control system | [Git](https://git-scm.com/)                                                                                              |
| Unit testing framework | [JUnit](https://junit.org/junit5/)                                                                                       |
| Mocking framework     | [Mockito](https://site.mockito.org/)                                                                                     |
| Code coverage tool    | [JaCoCo](https://www.jacoco.org/jacoco/)                                                                                     |
| Static analysis tools | [checkstyle](https://checkstyle.sourceforge.io/), [PMD](https://pmd.github.io/), [SpotBugs](https://spotbugs.github.io/) |


## Installation Process

It is assumed that both a [Java JDK](https://openjdk.org/) and an IDE such as [IntelliJ](https://www.jetbrains.com/idea/) or [Eclipse](https://www.eclipse.org/ide/) are installed.

- Import the repository as a gradle project and resolve all dependencies.
- Run the game by executing the main method in the [Launcher class](src/main/java/nl/tudelft/jpacman/Launcher.java).
- Execute the tests in the [test suite](src/test/java/nl/tudelft/jpacman)

## Contributors

The authors of the "JPacman" game can be found [AUTHORS](AUTHORS.md) file and the repository of the original source code can be found [here](https://github.com/SERG-Delft/jpacman-framework).

This test suite was created together with:

- [Reinout Meliesie](https://github.com/Zedfrigg)

## Licence

The original "JPacman-Framework" was published under the Apache 2.0 license, which can be found in the [Apache-2.0-LICENSE](https://github.com/johanneshagspiel/jpacman-test-suite/tree/master/Apache-2.0-LICENSE.txt) file. For this repository, the terms laid out there shall not apply to any individual that is currently enrolled at a higher education institution as a student. Those individuals shall not interact with any other part of this repository besides this README in any way by, for example cloning it or looking at its source code or have someone else interact with this repository in any way.
