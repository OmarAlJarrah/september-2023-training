# Anime Rating System

The Anime Rating System is a Java-based command-line application that allows users to interact with anime data. Users can query anime by name or rating, and the system provides information about anime ratings, caching, and user interaction logging.

## Table of Contents
- [Features](#features)
- [How it Works](#how-it-works)
- [Usage](#usage)
- [Example Usage](#example-usage)
- [Caching](#caching)
- [Logging](#logging)
- [Anime Data File](#anime-data-file)
- [Contributing](#contributing)

## Features

- **Anime Information**: Users can input either an anime name or a rating to retrieve information about the anime, including its name and rating.

- **Caching**: The system employs a Least Recently Used (LRU) cache to improve performance by storing recently accessed anime ratings and names.

- **Logging**: User interactions with the system are logged in a `LOG.txt` file, including user input and system responses.

## How it Works

The Anime Rating System consists of three primary classes:

- `Main.java`: This serves as the entry point of the program. It initializes the `AnimeRatingSystem` class and handles user input.

- `AnimeRatingSystem.java`: This is the core of the system, managing anime data, user input processing, and caching of anime ratings and names.

- `Anime.java`: Represents an anime with a name and a rating.

## Usage

To use the Anime Rating System:

1. **Compile**: Compile the Java files using your preferred Java development environment or by running `javac Main.java AnimeRatingSystem.java Anime.java`.

2. **Create Anime Data File**: Create an `anime.txt` file with your anime data. Each line should contain an anime name followed by its rating. For example:
<br>
One Piece 10<br>
Death Note 10<br>
Naruto 2<br>
...<br>


3. **Run the Program**: Execute `java Main` in your terminal or IDE to run the program.

4. **Interact with the System**: You can perform the following actions:
- Enter an anime name to get its rating.
- Enter a rating to find an anime with that rating.
- Type 'man of a culture' to exit the program.

## Example Usage

Here's an example of how to use the Anime Rating System:

- Enter an anime name to get its rating.
- Enter a rating to find an anime with that rating.
- Type 'man of a culture' to exit the program.

## Caching

The system uses a Least Recently Used (LRU) cache to improve performance by storing recently accessed anime ratings and names. The default cache size is 10, but you can adjust it in the `AnimeRatingSystem` class.

## Logging

User interactions with the system are logged in a `LOG.txt` file, including user input and system responses. This log file is created and updated during program execution.

## Anime Data File

The `anime.txt` file contains the anime data used by the system. Each line of the file represents an anime with its name and rating.

## Contributing

Contributions to this project are welcome. Feel free to fork the repository, make improvements, and submit pull requests.

