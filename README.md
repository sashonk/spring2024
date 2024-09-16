Spring Boot application with a single html page. On the page there is an employee DataGrid (dataset ~ 300K records)
Frontend: React+Material UI. 
Backend: Spring Boot + Data JPA, Postgres, OpenTelemetry (Spring, Jaeger)
App runs in Docker. Docker compose script raises several containers: app itself, postgres, opentelemetry connector, jaeger.

<h1>Start</h1>
Run "docker compose up -d". When all containers got up and running, go to http://localhost:7070/index.html

<h1>Stop</h1>
Run "docker compose down" to stop

