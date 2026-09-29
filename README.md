# Dynamic Views Analytics

Configurable **analytics / Dynamic Views** style reporting API.

Demonstrates report dimensions, filters, and metrics similar to enterprise platform analytics features.

## Features

- Configurable report dimension (`status`, `assignee`, `priority`, `category`)
- Metrics: `count`, `avgResolutionMinutes`
- Optional filters on ticket fields
- Metadata endpoint for UI/config consumers

## Stack

- Java 21
- Spring Boot 3.3
- In-memory sample dataset (no DB required)

## Run

```bash
mvn spring-boot:run
```

### Examples

```bash
# Available dimensions / metrics
curl http://localhost:8089/api/v1/reports/metadata

# Report by assignee
curl -X POST http://localhost:8089/api/v1/reports/dynamic-view \
  -H 'Content-Type: application/json' \
  -d '{"dimension":"assignee","metrics":["count","avgResolutionMinutes"]}'

# Filtered report
curl -X POST http://localhost:8089/api/v1/reports/dynamic-view \
  -H 'Content-Type: application/json' \
  -d '{"dimension":"category","filters":{"priority":"HIGH"},"metrics":["count"]}'
```

## Author

Sumanth D Gowda — Senior Platform Developer (Java Backend)
