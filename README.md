# Weather Application

This Java project is a graphical user interface (GUI) application that provides real-time and historical weather data for a specified location and date. The application integrates with the Tomorrow.io API to fetch weather information, displaying results in a clean, easy-to-use interface built with Java Swing.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Installation and Setup](#installation-and-setup)
- [Classes](#classes)
    - [WeatherRealTime](#weatherrealtime)
    - [WeatherInDate](#weatherindate)
    - [Design](#design)
    - [Main](#main)
- [API Key Setup](#api-key-setup)
- [Usage](#usage)

## Overview

The application allows users to either enter a location and a specific date for historical weather data or free-text weather inquiries, extracting the location and date using a language model to fetch weather data. The `Tomorrow.io API` provides weather data for both current and historical data points.

## Features

- **Real-Time Weather:** Get the current temperature for any location.
- **Historical Weather:** Retrieve temperature data for specific times of the day (08:00, 14:00, and 20:00) for a specified date and location.
- **Natural Language Processing:** Users can enter free-text queries, and the application will attempt to parse location and date information.
- **Interactive GUI:** A Java Swing interface that is easy to use and displays weather results and appropriate icons based on temperature.

## Technologies Used

- **Java (Swing):** For GUI development.
- **OkHttp:** HTTP client for making API requests.
- **Tomorrow.io API:** Provides real-time and historical weather data.
- **JSON Parsing:** Uses `org.json` for handling JSON responses.

## Installation and Setup

1. Clone the repository:
    ```bash
    git clone https://github.com/yourusername/WeatherApplication.git
    cd WeatherApplication
    ```

2. Set up your IDE to recognize environment variables. In IntelliJ:
    - Go to **Run** > **Edit Configurations**.
    - Add `API_KEY` with your Tomorrow.io API key.

3. Install dependencies (OkHttp and org.json):
    - Download the required `.jar` files for [OkHttp](https://square.github.io/okhttp/) and [JSON](https://mvnrepository.com/artifact/org.json/json).
    - Add the `.jar` files to your project libraries.

## Classes

### WeatherRealTime

Handles requests to the Tomorrow.io API for real-time weather data. It takes a location as input and returns the current temperature.

- **Method:** `getWeather(String location)`
    - **Description:** Sends a GET request to the Tomorrow.io API with the specified location to retrieve current temperature data.
    - **Returns:** Current temperature in Celsius.
    - **Throws:** `IOException` if there is a network issue.

### WeatherInDate

Fetches historical weather data from the Tomorrow.io API. It uses the API’s timeline feature to get temperatures at specific times on a given date.

- **Method:** `getDateWeather(String date, String location)`
    - **Description:** Sends a POST request with JSON payload including location and date to retrieve temperature data for 08:00, 14:00, and 20:00 hours.
    - **Returns:** Array of temperatures for the specified times of day.
    - **Throws:** `IOException` if there is a network issue.

### Design

Builds the GUI for the application, allowing users to input data and retrieve results for weather information.

- **Method:** `frame()`
    - **Description:** Initializes the Main frame and layout, splitting it into panels for real-time and historical weather inputs.
- **Method:** `leftPanel()`
    - **Description:** Creates the left panel for the user to input location and date for historical weather.
- **Method:** `rightPanel()`
    - **Description:** Creates the right panel for free-text input and displays parsed results.

### Main

The entry point of the application, which initializes and launches the GUI.

- **Method:** `Main(String[] args)`
    - **Description:** Calls `Design.frame()` to display the Main GUI.

## API Key Setup

To securely handle the API key:

1. Set the `API_KEY` as an environment variable to avoid hardcoding it.
    ```java
    public static String API_KEY = System.getenv("API_KEY");
    ```

2. In IntelliJ:
    - Go to **Run** > **Edit Configurations** > **Environment variables**.
    - Add a variable `API_KEY` with your API key value.

## Usage

1. Run the `Main` class.
2. The GUI will launch with two panels:
    - **Left Panel:** For specific location and date-based weather queries.
    - **Right Panel:** Accepts free-text questions for weather, extracting location and date before fetching relevant data.
3. **View Results:** The application will display temperature data and a relevant weather icon for the selected date or location.

---

Feel free to contribute to the project by opening issues or submitting pull requests!
