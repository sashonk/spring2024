Spring Boot application with a single html page. On the page there is an employee DataGrid (dataset ~ 300K records)
<p>Frontend: React+Material UI. </p>
<p>Backend: Spring Boot + Data JPA, Postgres, OpenTelemetry (Spring, Jaeger)</p>
<p>App is containerised. Docker compose script raises several containers: app itself, postgres, opentelemetry connector, jaeger.</p>

<h1>Start</h1>
Run "docker compose up -d". When all containers got up and running, go to http://localhost:7070/index.html

<h1>Stop</h1>
Run "docker compose down" to stop

<h1>Jaeger</h1>
Jaeger UI is available at http://localhost:16686
