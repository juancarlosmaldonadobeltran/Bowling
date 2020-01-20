[![](https://api.travis-ci.org/juancarlosmaldonadobeltran/Bowling.svg)](https://travis-ci.org/juancarlosmaldonadobeltran/Bowling)

# Multi player Ten-pin Bowling Game

A console app for Ten-pin Bowling score calculation.

## Prerequisites

* [OpenJKD 8](https://adoptopenjdk.net/)

## Dependencies

* [Guice](https://github.com/google/guice)
* [Lombok](https://projectlombok.org/)
* [Apache Commons IO](http://commons.apache.org/proper/commons-io/)
* [JUnit](https://junit.org/junit4/)
* [Mockito](https://site.mockito.org/)

# Dependency management

* [Maven](https://maven.apache.org/)
* [Maven Wrapper](https://github.com/takari/maven-wrapper)

## Running the tests and installing the application

```./install.sh``` 

## Running the application

```./run.sh /path/to/players/rolls/file```

Input file containing one line per player roll in tab separated format: 

player_name knocked_pins 

See: [players_rolls.txt](./players_rolls.txt)

## Demo

```./demo.sh ```