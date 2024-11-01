# Weather Application

This Java project is a graphical user interface (GUI) application that provides real-time and historical weather data for a specified location and date. The application integrates with the Tomorrow.io API to fetch weather information, displaying results in a clean, easy-to-use interface built with Java Swing.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Installation and Setup](#installation-and-setup)
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
    - Add `WEATHER_API_KEY` with your Tomorrow.io API key.
    - Add `CHATGPT_API_KEY` with your ChatGPT API key.


## Usage

1. Run the `Main` class.
2. The GUI will launch with two panels:
    - **Left Panel:** For specific location and date-based weather queries.
    - **Right Panel:** Accepts free-text questions for weather, extracting location and date before fetching relevant data.
3. **View Results:** The application will display temperature data and a relevant weather icon for the selected date or location.

---

Feel free to contribute to the project by opening issues or submitting pull requests!
