Antes de levantar Docker, necesitás generar el `.jar` de la app.

Si estás en Windows:

```
$env:JAVA_HOME = "C:\Users\TU_USUARIO\.jdks\ms-21.0.7"
.\mvnw.cmd clean package
```

Esto va a generar el archivo `parcial-final-n-capas-0.0.1-SNAPSHOT.jar` dentro de la carpeta `target/`

## Dockerfile
En la raíz del proyecto, creá un archivo llamado `Dockerfile`
```
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

creá el archivo ``docker-compose.yml``
```
version: '3.8'

services:
  postgres:
    image: postgres:15
    container_name: soporte_postgres
    restart: always
    environment:
      POSTGRES_DB: soporte_db
      POSTGRES_USER: soporte_user
      POSTGRES_PASSWORD: soporte_pass
    ports:
      - "5432:5432"
    volumes:
      - postgres-data:/var/lib/postgresql/data

  app:
    build:
      context: .
      dockerfile: Dockerfile
    container_name: soporte_app
    depends_on:
      - postgres
    ports:
      - "8181:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/soporte_db
      SPRING_DATASOURCE_USERNAME: soporte_user
      SPRING_DATASOURCE_PASSWORD: soporte_pass

volumes:
  postgres-data:

```
Cambiamos el puerto del host a 8181 porque a veces el 8080 ya está ocupado.

## Configurar application.yml
````
spring:
  datasource:
    url: jdbc:postgresql://postgres:5432/soporte_db
    username: soporte_user
    password: soporte_pass
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
````

## Levantar Docker
````
docker compose up --build
````

## Probar la app
````
http://localhost:8181/api/users/all        --para obtener todos los usuarios
http://localhost:8181/api/users/{id}          --para obtener el usuario con id
http://localhost:8181/api/tickets/all       --para obtener todos los tickets
http://localhost:8181/api/tickets/{id}         --para obtener el ticket con id
````
