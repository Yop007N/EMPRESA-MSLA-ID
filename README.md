# Demo - Exchange Rate Microservice

Microservicio en Spring Boot para conversion de divisas usando APILayer, con persistencia de historial y resumen agregado por moneda destino.

## Caracteristicas

- Conversion de divisas en tiempo real (`/api/exchange/convert`)
- Persistencia de conversiones en base de datos
- Historial por rango de fechas y filtro opcional por moneda
- Resumen total convertido agrupado por moneda destino
- Seguridad HTTP Basic para endpoints de negocio
- Swagger UI + OpenAPI
- Actuator healthcheck

## Stack tecnico

- Java 25
- Spring Boot 4.0.3
- Spring Data JPA
- Spring Security (HTTP Basic)
- Spring Cloud OpenFeign
- PostgreSQL 16 (produccion/local con Docker)
- H2 (perfil local rapido)
- OpenAPI Generator (contrato en YAML)
- Docker + Docker Compose

## Requisitos

- JDK 25 instalado y en `PATH`
- Docker Desktop (opcional, para entorno completo)
- Conexion a internet para consultar APILayer

## Configuracion

Variables principales (con valores por defecto del proyecto):

| Variable | Default | Descripcion |
| --- | --- | --- |
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://localhost:5432/exchangedb` | URL de PostgreSQL |
| `SPRING_DATASOURCE_USERNAME` | `postgres` | Usuario DB |
| `SPRING_DATASOURCE_PASSWORD` | `postgres` | Password DB |
| `APP_SECURITY_USERNAME` | `admin` | Usuario para HTTP Basic |
| `APP_SECURITY_PASSWORD` | `admin123` | Password para HTTP Basic |
| `APILAYER_API_KEY` | configurada en `application.properties` | API key para APILayer |
| `APILAYER_BASE_URL` | `https://api.apilayer.com/exchangerates_data` | URL base API externa |

## Ejecutar el proyecto

### Opcion 1: Local rapido (perfil `local` con H2)

No requiere PostgreSQL.

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=local"
```

En Linux/macOS:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

### Opcion 2: Local con PostgreSQL

1. Levantar solo la base:

```powershell
docker compose up -d postgres
```

2. (Opcional) Sobrescribir API key:

```powershell
$env:APILAYER_API_KEY="TU_API_KEY"
```

3. Ejecutar la app:

```powershell
.\mvnw.cmd spring-boot:run
```

### Opcion 3: Entorno completo con Docker Compose

```powershell
docker compose up --build -d
```

Servicios:

- API: `http://localhost:8080`
- PostgreSQL: `localhost:5432`

Parar todo:

```powershell
docker compose down
```

## Autenticacion

Endpoints protegidos: `/api/exchange/**`  
Endpoints publicos: `/actuator/health`, `/swagger-ui.html`, `/api-docs/**`, `/h2-console/**`

Credenciales por defecto:

- Usuario: `admin`
- Password: `admin123`

## Documentacion API

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api-docs`
- Health: `http://localhost:8080/actuator/health`

Contrato OpenAPI fuente:

- `src/main/resources/openapi/exchange-api.yaml`

## Endpoints principales

### 1) Convertir moneda

`POST /api/exchange/convert`

```bash
curl -u admin:admin123 \
  -X POST "http://localhost:8080/api/exchange/convert" \
  -H "Content-Type: application/json" \
  -d "{\"from\":\"USD\",\"to\":\"PEN\",\"amount\":100.0}"
```

### 2) Historial

`GET /api/exchange/history?from=2025-01-01&to=2026-12-31&currency=USD`

```bash
curl -u admin:admin123 \
  "http://localhost:8080/api/exchange/history?from=2025-01-01&to=2026-12-31&currency=USD"
```

### 3) Resumen por moneda destino

`GET /api/exchange/summary`

```bash
curl -u admin:admin123 \
  "http://localhost:8080/api/exchange/summary"
```

## Base de datos

Script inicial:

- `src/main/resources/db/init.sql`

Tabla principal:

- `exchange_history` (almacena cada conversion con monto, tasa, fecha y timestamp de creacion)

## Pruebas

```powershell
.\mvnw.cmd test
```

Nota: el proyecto compila con `java.version=25`. Si tenes una version menor, Maven falla con `release version 25 not supported`.

## Postman

Coleccion incluida:

- `postman/Exchange-Rate-API.postman_collection.json`

Importala en Postman y ajusta `baseUrl`, `username` y `password` si corresponde.

---

## Cumplimiento de requerimientos de la prueba tecnica

### Microservicio

| Requerimiento | Estado |
|---|---|
| Arquitectura N capas (controller / service / repository / entity / client) | Implementado |
| `POST /api/exchange/convert` — conversion PEN ↔ USD | Implementado |
| `GET /api/exchange/history` — historial con filtro por fecha y moneda | Implementado |
| `GET /api/exchange/summary` — suma total agrupada por moneda destino | Implementado |
| Spring Cloud Feign Client para consumo de APILayer | Implementado |
| PostgreSQL + Spring Data JPA (persistencia de historial) | Implementado |
| Spring Security HTTP Basic Auth | Implementado |

### Contrato y documentacion

| Requerimiento | Estado |
|---|---|
| Contract First — OpenAPI 3.0 YAML (`exchange-api.yaml`) | Implementado |
| `openapi-generator-maven-plugin` genera interfaces y modelos | Implementado |
| Swagger UI accesible en `/swagger-ui.html` | Implementado |

### Infraestructura

| Requerimiento | Estado |
|---|---|
| Dockerfile multi-stage (build JDK 25 + runtime JRE 25) | Implementado |
| Docker Compose (app + PostgreSQL 16 con healthcheck) | Implementado |
| Script SQL de inicializacion (`db/init.sql`) | Implementado |

### Entregables

| Requerimiento | Estado |
|---|---|
| Tests unitarios JUnit 5 + Mockito (4 tests, sin contexto Spring) | Implementado |
| Coleccion Postman JSON | Implementado |
| Repositorio GitHub en rama `enrique-b` | Implementado |
