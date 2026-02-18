# WeatherGuard API

WeatherGuard is a Spring Boot backend service that fetches weather forecasts from the Open-Meteo API and evaluates event safety based on weather conditions. It provides hourly forecasts and classifies the event window based on rain probability, wind speed, and weather codes.

---

## Setup Instructions

1. **Clone the repository:**

```bash
git clone https://github.com/<your-username>/weatherguard.git
cd weatherguard

2. ** API Usage **
weather.api.base-url=https://api.open-meteo.com/v1/forecast

3. ** Build Project **
mvn clean install

4. ** Run **
mvn spring-boot:run


## API Usage
Request Body:
{
  "name": "Football Match",
  "location": {
    "latitude": 19.0760,
    "longitude": 72.8777
  },
  "start_time": "2026-01-10T17:00:00",
  "end_time": "2026-01-10T19:00:00"
}

Response 
{
  "classification": "Safe",
  "summary": "Clear Sky, Good Day for Event",
  "reason": [
    "Rain probability is 10% at 17:00",
    "Wind Speed is 5 km/h"
  ],
  "event_window_forecast": [
    {
      "time": "17:00",
      "rain_prob": 10,
      "wind_kmh": 5
    },
    {
      "time": "18:00",
      "rain_prob": 15,
      "wind_kmh": 7
    }
  ]
}


## Weather Classification Rules
| Weather Code | Rain Probability | Wind Speed | Classification | Summary                              |
| ------------ | ---------------- | ---------- | -------------- | ------------------------------------ |
| 0–20         | Any              | Any        | Safe           | Clear Sky, Good Day for Event        |
| 9            | Any              | Any        | Unsafe         | High chance of Duststorm/Sandstorm   |
| 17           | Any              | Any        | Unsafe         | High chance of Thunderstorm          |
| 30–39        | Any              | Any        | Unsafe         | High chance of Duststorm/Sandstorm   |
| ≥ 80         | Any              | Any        | Unsafe         | High chance of Rain and Thunderstorm |
| Others       | Any              | Any        | Risky          | High chance of Rain                  |


