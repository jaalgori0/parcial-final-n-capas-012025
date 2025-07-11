# Parcial Final Programación N-Capas – (Seguridad con Spring Security + JWT)

Este repositorio contiene un proyecto para evaluar y practicar los conceptos de seguridad en aplicaciones Spring Boot usando JWT, roles y Docker.

### Estudiantes
- **Nombre del estudiante 1**: Jorge Eduardo Avalos Velásquez - 00009322
- **Nombre del estudiante 2**: Javier Alejandro González Rivas - 00088022
- Sección: 02
---

## Sistema de Soporte Técnico

### Descripción
Simula un sistema donde los usuarios pueden crear solicitudes de soporte (tickets) y los técnicos pueden gestionarlas. Actualmente **no tiene seguridad implementada**.

Su tarea es **agregar autenticación y autorización** utilizando **Spring Security + JWT**, y contenerizar la aplicación con Docker.

### Requisitos generales

- Proyecto funcional al ser clonado y ejecutado con Docker.
- Uso de PostgreSQL (ya incluido en docker-compose).
- Seguridad implementada con JWT.
- Roles `USER` y `TECH`.
- Acceso restringido según el rol del usuario.
- Evidencia de funcionamiento (colección de Postman/Insomnia/Bruno o capturas de pantalla).

**Nota: El proyecto ya tiene una estructura básica de Spring Boot con endpoints funcionales para manejar tickets. No es necesario modificar la lógica de negocio, solo agregar seguridad. Ademas se inclye un postman collection para probar los endpoints. **

_Si van a crear mas endpoints como el login o registrarse recuerden actualizar postman/insomnia/bruno collection_

### Partes de desarrollo

#### Parte 1: Implementar login con JWT
- [x] Crear endpoint `/auth/login`.
- [x] Validar usuario y contraseña (puede estar en memoria o en BD).
- [x] Retornar JWT firmado.

#### Parte 2: Configurar filtros y validación del token
- [x] Crear filtro para validar el token en cada solicitud.
- [x] Extraer usuario desde el JWT.
- [x] Añadir a contexto de seguridad de Spring.

#### Parte 3: Proteger endpoints con Spring Security
- [x] Permitir solo el acceso al login sin token.
- [x] Proteger todos los demás endpoints.
- [x] Manejar errores de autorización adecuadamente.

#### Parte 4: Aplicar roles a los endpoints

| Rol   | Acceso permitido                                 |
|--------|--------------------------------------------------|
| USER  | Crear tickets, ver solo sus tickets              |
| TECH  | Ver todos los tickets, actualizar estado         |

- [x] Usar `@PreAuthorize` o reglas en el `SecurityFilterChain`.
- [x] Validar que un USER solo vea sus tickets.
- [x] Validar que solo un TECH pueda modificar tickets.

#### Parte 5: Agregar Docker
- [x] `Dockerfile` funcional para la aplicación.
- [x] `docker-compose.yml` que levante la app y la base de datos.
- [x] Documentar cómo levantar el entorno (`docker compose up`).

#### Parte 6: Evidencia de pruebas
- [ ] Probar todos los flujos con Postman/Insomnia/Bruno.
- [ ] Mostrar que los roles se comportan correctamente.
- [ ] Incluir usuarios de prueba (`user`, `tech`) y contraseñas.

---

## Notas sobre ajustes realizados

Durante el desarrollo y las pruebas del sistema se identificaron algunos errores menores en la configuración del código, los cuales fueron corregidos para asegurar que todos los flujos funcionaran correctamente. A continuación, se detallan los cambios realizados y los archivos modificados:

---

### 1. Acceso al endpoint de autenticación y PasswordEncoder no encriptado  
**Archivo:** `SecurityConfiguration.java`

Se ajustó la configuración de seguridad para permitir correctamente las peticiones al endpoint de autenticación:
```java
// Cambio aplicado:
.requestMatchers("/api/auth").permitAll()

// En lugar de:
.requestMatchers("/api/auth/register/user").permitAll()
```

Se utilizó `NoOpPasswordEncoder` para evitar errores al validar contraseñas sin cifrado:

```java
// Cambio aplicado temporalmente:
return NoOpPasswordEncoder.getInstance();

// En lugar de:
return new BCryptPasswordEncoder();
```

### 2. Comparación de roles en lógica de tickets
**Archivo:** `TicketServiceImpl.java`

Se corrigió la comparación de roles que generaba errores al validar si el usuario tenía permisos de tipo TECH:
```java
// Cambio aplicado:
usuarioSoporte.getNombreRol() != Rol.TECH

// En lugar de:
!usuarioSoporte.getNombreRol().equals(Rol.TECH.getValue())
```

Todos estos ajustes fueron clave para completar exitosamente las pruebas del sistema con ambos roles.

---

## Usuarios de Prueba y Autenticación

Se crearon dos usuarios para probar los distintos roles en el sistema. Ambos usuarios pueden registrarse y autenticarse correctamente a través del endpoint correspondiente.

---

### 1. Registro de Usuarios

**Endpoint:** `POST {{baseUrl}}/api/users`

#### USER

```json
{
  "nombre": "Usuario Prueba",
  "correo": "user@uca.edu.sv",
  "password": "user123",
  "nombreRol": "USER"
}
```
#### TECH
```json
{
  "nombre": "Usuario Tech",
  "correo": "Techr@uca.edu.sv",
  "password": "tech123",
  "nombreRol": "TECH"
}
```

### 2. Inicio de Sesión

**Endpoint:** `POST {{baseUrl}}/api/auth/login`

#### Login como USER
```json
{
  "correo": "user@uca.edu.sv",
  "password": "user123"
}
```
#### Login como TECH
```json
{
  "correo": "Techr@uca.edu.sv",
  "password": "tech123"
}
```
### 3. Respuesta al login exitoso (ejemplo con TECH)

```json
{
  "uri": "/api/auth/login",
  "message": "Inicio de sesión exitoso",
  "status": 200,
  "time": null,
  "data": {
    "token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJUZWNockB1Y2EuZWR1LnN2IiwiaWF0IjoxNzUyMjU4NDM0LCJleHAiOjE3NTIzNDQ4MzR9.pexJeJk3LpBt4OwK79rUJwlUX5wbyuQVa_aQDa_7djJKqMWxvM37EeFO8UUidYwj"
  }
}
```
El token JWT obtenido debe utilizarse en las siguientes peticiones protegidas agregando el header:  
`Authorization: Bearer <token>`

### Tickets

**Endpoint:** ```GET {{baseUrl}}/api/tickets```

como TECH se pueden obtener todos los resultados

<img src="./Capturas/image.png" alt="Captura obtener todo los tickets" width="600"/>

**Endpoint:** ```GET {{baseUrl}}/api/tickets/1```

Como TECH se pueden obtener los resultados por id

<img src="./Capturas/image-1.png" alt="Captura obtener el ticket por id" width="600"/>

**Endpoint:** ```POST {{baseUrl}}/api/tickets```

Como TECH no se pueden crear tickets

<img src="./Capturas/image-2.png" alt="Captura obtener el ticket por id" width="600"/>

pero como USER si

<img src="./Capturas/image-3.png" alt="Captura obtener el ticket por id" width="600"/>

**Endpoint:** ```DELETE {{baseUrl}}/api/tickets/2```

Como USER no se pueden elimiinar tickets

<img src="./Capturas/image-4.png" alt="Captura obtener el ticket por id" width="600"/>

Como TECH si se pueden eliminar tickets

<img src="./Capturas/image-5.png" alt="Captura obtener el ticket por id" width="600"/>

### USERS

**Endpoint:** ```GET {{baseUrl}}/api/users/all```

Como TECH Se pueden obtener todos los usuarios

<img src="./Capturas/image-6.png" alt="Captura obtener el ticket por id" width="600"/>

Como USER no se puede obetener los usuarios

<img src="./Capturas/image-7.png" alt="Captura obtener el ticket por id" width="600"/>

**Endpoint:** ```GET {baseUrl}}/api/users/user@uca.edu.sv```

Como TECH se puede obtener el usuario por correo

<img src="./Capturas/image-8.png" alt="Captura obtener el ticket por id" width="600"/>

Como USER no se puede obtener el usuario por correo

<img src="./Capturas/image-9.png" alt="Captura obtener el ticket por id" width="600"/>

**Endpoint:** ```DELETE {{baseUrl}}/api/users/3```

Como TECH se puede eliminar a un usuario

<img src="./Capturas/image-10.png" alt="Captura obtener el ticket por id" width="600"/>

Como USER se puede eliminar a un usuario

<img src="./Capturas/image-11.png" alt="Captura obtener el ticket por id" width="600"/>