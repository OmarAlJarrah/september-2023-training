# Anime Rating Management System

## Table of Contents

1. [Introduction](#introduction)
2. [Features](#features)
3. [Requirements](#requirements)
4. [How to Use](#how-to-use)
5. [Code Explanation](#code-explanation)

---

## Introduction

This Java-based application allows users to manage anime ratings. It reads anime ratings from a text file, allows querying ratings, and stores recent queries in an LRU cache.

---

## Features

- Read anime ratings from a text file
- Query anime ratings based on user input
- Store recent anime queries in an LRU cache
- Allows adding new ratings to the existing list

---

## Requirements

- Java 8 or above
- A text file with anime ratings (e.g., `sample-input.txt`)

---

## How to Use

1. Clone the repository
2. Compile the Java files (`Main.java` and `AnimeRating.java`)
3. Run the `Main` class
4. Follow the on-screen instructions to query or add anime ratings

---

## Code Explanation

### Main.java

#### Variables:

- **log**: A static string to keep track of the log to be written to a file.
- **lruCache**: A `LinkedHashMap` that implements an LRU (Least Recently Used) cache to store the 10 most recently accessed anime ratings.
- **animeRatings**: A `List` that stores all the anime ratings read from a text file.

#### Methods:

- **LogAndPrint(String s)**: Logs and prints the given string `s` to the console and also appends it to the `log`.

- **main(String[] args)**: The entry point for the application. It has several functionalities as described below:
    1. **File Reading**: Reads the `sample-input.txt` file and populates the `animeRatings` list.
    2. **Query Handling**: Accepts user inputs to query anime ratings. The queries can be based on a rating threshold or anime name.
    3. **LRU Cache**: Utilizes `lruCache` to quickly retrieve recently queried anime ratings.
    4. **New Rating Addition**: Allows the user to add new ratings if the anime is not found.
    5. **Log Writing**: Writes the `log` to a file called `LOG.txt`.

### AnimeRating.java

#### Variables:

- **animeShowName**: A `String` that stores the name of the anime show.
- **rating**: A `float` that stores the rating of the anime.

#### Methods:

- **AnimeRating(String animeShowName, float rating)**: The constructor initializes an instance of `AnimeRating` with the given anime name and rating.

- **toString()**: Overrides the default `toString` method to return the anime name and its rating as a single string.

