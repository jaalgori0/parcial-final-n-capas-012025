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
