# Bolt Project : D&D 5E API Service

A Kotlin-based RESTful service that provides simplified access to the D&D 5E API.

## Features

- **Summary Endpoint**: Get counts of classes, spells, monsters, and features
- **Class Details Endpoint**: Get simplified class information
- **Caching**: In-memory caching to improve performance
- **Clean Architecture**: Well-structured codebase following domain-driven design principles


## Building the Project

```bash
# Clone the repository
git clone https://github.com/shubhamag-code/bolt-project.git
cd bolt-project

# Build the project
./gradlew build
```

## Running the Service

```bash
# Run the server
./gradlew run
```

The server will start on port 8080. You can access it at `http://localhost:8080`.

## API Endpoints

### Get D&D Resource Summary

```
GET /summary
```

Returns counts of all D&D resources including classes, spells, monsters, and features.

Example response:
```json
{
  "total_classes": 12,
  "total_spells": 319,
  "total_monsters": 334,
  "total_features": 370
}
```

### Get Class Details

```
GET /classes/{className}
```

Returns details about a specific character class.

Parameters:
- `className`: Name of the D&D class (e.g., "barbarian", "bard", "cleric")

Example response for `/classes/cleric`:
```json

{
  "name": "Cleric",
  "hit_die": 8,
  "proficiency_choices": [
    {
      "desc": "Choose two from History, Insight, Medicine, Persuasion, and Religion",
      "choose": 2,
      "type": "proficiencies",
      "from": [
        {
          "name": "Skill: History",
          "index": "skill-history",
          "url": "/api/2014/proficiencies/skill-history"
        },
        {
          "name": "Skill: Insight",
          "index": "skill-insight",
          "url": "/api/2014/proficiencies/skill-insight"
        },
        {
          "name": "Skill: Medicine",
          "index": "skill-medicine",
          "url": "/api/2014/proficiencies/skill-medicine"
        },
        {
          "name": "Skill: Persuasion",
          "index": "skill-persuasion",
          "url": "/api/2014/proficiencies/skill-persuasion"
        },
        {
          "name": "Skill: Religion",
          "index": "skill-religion",
          "url": "/api/2014/proficiencies/skill-religion"
        }
      ]
    }
  ],
  "saving_throws": [
    {
      "name": "WIS",
      "index": "wis",
      "url": "/api/2014/ability-scores/wis"
    },
    {
      "name": "CHA",
      "index": "cha",
      "url": "/api/2014/ability-scores/cha"
    }
  ]

}
```

## Project Structure

The project follows clean architecture principles with the following key components:

```
com.bolt/
├── config/          # Application configuration and entry point
│   └── MainApplication.kt  # Main function and Ktor setup
│
├── di/              # Dependency injection modules (Dagger setup)
│   └── AppModule.kt     # Provides singletons like HttpClient, services, etc.
│
├── domain/          # Business logic and models
│   ├── model/       # Domain entities like ClassDetail, ApiReference, etc.
│   │   └── mapper/  # Maps raw JSON into domain models
│   └── service/     # Service layer with logic and external API calls
│
├── api/             # API route definitions and Ktor routing logic
│   └── Routes.kt     # Defines endpoints and connects to services

```

## Caching

The service implements in-memory caching to reduce load on the external D&D API. (5 minutes TTL)



```bash
./gradlew test
```

## Future Improvements

- Add more endpoints for spells, monsters, and other D&D resources
- Implement database caching for persistence between restarts
- Add authentication for API access
- Add rate limiting to prevent abuse
