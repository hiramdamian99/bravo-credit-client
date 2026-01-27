README.md (Markdown)
# bravo-credit-client

Microservicio demo para **gestionar solicitudes de crédito** y **consultar** solicitudes registradas.

> **Meta de ejecución:** siguiendo este README, el entorno debe quedar listo y corriendo en **< 5 minutos** (asumiendo Docker y Java/Maven instalados, y una conexión a internet razonable para descargar dependencias por primera vez).

---

## 1) Objetivo

Este proyecto expone una API REST para:
- **Crear** una solicitud de crédito para un cliente.
- **Buscar** solicitudes por filtros.
- **Validar reglas de negocio** por país (MX/CO) y consultar un **banco simulado** para determinar elegibilidad y devolver una cuenta asociada.

---

## 2) Stack

- Java 17+
- Spring Boot
- Maven
- PostgreSQL 16 (Docker)
- Frontend estático (carpeta `public/`)

---

## 3) Requisitos mínimos

- Docker (Desktop o Engine)
- Java 17+ (JAVA_HOME configurado)
- Maven 3.9+ (o wrapper si existe en el repo)
- Git
- Python 3 (solo para servir el frontend estático) o cualquier servidor HTTP simple
- cURL (opcional) / Postman (opcional)

---

## 4) Quickstart (local) - menos de 5 minutos

### 4.1 Clonar repo
```bash
git clone git@github.com:hiramdamian99/bravo-credit-client.git
cd bravo-credit-client
```

### 4.2 Levantar PostgreSQL con Docker
```bash
docker run --name bravo-credit-postgres   -e POSTGRES_USER=bravo_credit_user   -e POSTGRES_PASSWORD=bravo_credit_password   -e POSTGRES_DB=demo   -p 5432:5432   -d postgres:16
```

### 4.3 Crear schema y tabla (sin instalar psql local)
Este comando ejecuta SQL dentro del contenedor:
```bash
docker exec -i bravo-credit-postgres psql -U bravo_credit_user -d demo <<'SQL'
CREATE SCHEMA IF NOT EXISTS control;

CREATE TABLE IF NOT EXISTS control.tr_client (
  process_id     BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  identifier     VARCHAR(255) NOT NULL,
  monthly_income INTEGER      NOT NULL,
  amount         INTEGER      NOT NULL,
  country        VARCHAR(10)  NOT NULL,
  created_at     TIMESTAMPTZ  DEFAULT now(),
  updated_at     TIMESTAMPTZ  DEFAULT now(),
  created_by     VARCHAR(255),
  updated_by     VARCHAR(255)
);
SQL
```

### 4.4 Levantar el backend
> Nota: la primera vez Maven puede tardar un poco más mientras descarga dependencias.

```bash
mvn spring-boot:run
```

El backend queda en:
- `http://localhost:8080`

Las configuraciones de base de datos están en:
- `src/main/resources/application.properties`

### 4.5 Levantar el frontend (desacoplado del backend)
Desde la raíz del repo:
```bash
cd public
python3 -m http.server 5173
```

Abre en el navegador:
- `http://localhost:5173`

---

## 5) API

### 5.1 Crear solicitud de crédito
**POST** `http://localhost:8080/api/v1/created/client`

Request:
```json
{
  "identifier": "AUSP000730MBSGTNA6",
  "monthlyIncome": 15000,
  "country": "MX",
  "amount": 200000
}
```

Ejemplo con cURL:
```bash
curl -X POST "http://localhost:8080/api/v1/created/client"   -H "Content-Type: application/json"   -d '{"identifier":"AUSP000730MBSGTNA6","monthlyIncome":15000,"country":"MX","amount":200000}'
```

Respuestas esperadas (alto nivel):
- `201 Created` si se registra correctamente.
- `400 Bad Request` si hay validaciones de formato o parámetros inválidos.
- `422 Unprocessable Entity` si la solicitud es válida en sintaxis pero falla reglas de negocio (opcional, depende de la implementación).
- `500 Internal Server Error` si ocurre un error inesperado.

### 5.2 Buscar solicitudes
**POST** `http://localhost:8080/api/v1/search/client`

Request (filtros opcionales - usa solo los que necesites):
```json
{
  "processId": null,
  "identifier": "AUSP000730MBSGTNA6",
  "identifierLike": null,
  "monthlyIncome": null,
  "monthlyIncomeMin": null,
  "monthlyIncomeMax": null,
  "amount": null,
  "amountMin": null,
  "amountMax": null,
  "country": null,
  "createdFrom": null,
  "createdTo": null,
  "updatedFrom": null,
  "updatedTo": null,
  "createdBy": null,
  "updatedBy": null
}
```

Ejemplo con cURL:
```bash
curl -X POST "http://localhost:8080/api/v1/search/client"   -H "Content-Type: application/json"   -d '{"identifier":"AUSP000730MBSGTNA6"}'
```

Respuesta esperada:
- `200 OK` con una lista de resultados (puede ser vacía).

---

## 6) Reglas de negocio

### 6.1 Países aceptados
- México (`MX`)
- Colombia (`CO`)

### 6.2 Formato de identificador
- **México:** CURP válido (18 caracteres alfanuméricos).
- **Colombia:** CC válido (6 a 10 dígitos numéricos).

### 6.3 Monto a solicitar
- Debe ser un número **positivo** (`amount > 0`).

### 6.4 Ingreso mensual mínimo
- **México:** `monthlyIncome >= 10%` del monto solicitado.
- **Colombia:** `monthlyIncome >= 30%` del monto solicitado.

### 6.5 Validación con banco (simulado)
- El sistema consulta un **banco simulado** para determinar si el cliente es apto.
- Si es apto, retorna (o asocia) la **cuenta** del cliente.

**Supuesto:** para este reto, el "banco" es una dependencia externa simulada (adapter de salida) para que el flujo sea reproducible localmente.

---

## 7) Decisiones técnicas y arquitectura

### 7.1 Enfoque
El backend está diseñado bajo **Arquitectura Hexagonal (Ports & Adapters)** para mantener:
- Dominio y casos de uso **independientes** de frameworks (Spring), transporte (HTTP) y persistencia (JPA/SQL).
- Integraciones (DB / banco simulado) como detalles intercambiables.

### 7.2 Capas (resumen)
- **Domain:** reglas de negocio, entidades/value objects, excepciones de negocio.
- **Application:** casos de uso (CreateClient / SearchClient), puertos de entrada/salida.
- **Adapters In:** controllers REST (mapean HTTP/JSON a casos de uso).
- **Adapters Out:** repositorios SQL/JPA y clientes externos (banco simulado).

### 7.3 Flujo end-to-end
1. Llega un request HTTP al Controller.
2. El Controller transforma el request y llama al caso de uso (input port).
3. El caso de uso valida reglas de negocio y llama puertos de salida (repo / banco).
4. Un adapter de salida ejecuta integración (DB / API simulada) y retorna resultado.
5. El Controller responde al cliente.

### 7.4 Manejo de errores (recomendación Spring)
Centralizar en un `@RestControllerAdvice` para devolver un formato uniforme:
- `timestamp`, `status`, `message`, `path`, `traceId` (si aplica), `details` (lista de validaciones).

Semántica recomendada:
- `400` validación/parametros
- `404` no encontrado
- `409` conflicto (duplicados/estado)
- `422` reglas de negocio (opcional)
- `500` inesperado
- `502/503/504` dependencias externas

---

## 8) Pruebas

```bash
mvn test
```

> Si existen pruebas de integración que requieren DB, asegúrate de tener PostgreSQL corriendo (sección 4.2).

---

## 9) Mejoras futuras (backlog)

- Mejorar estilos del frontend.
- Incrementar cobertura de pruebas unitarias e integración.
- Implementar re-encolamiento / mensajería (ej. RabbitMQ) para procesos asíncronos.
- Separar la inserción en DB en un patrón **orquestador + worker** para escalar workers en múltiples pods.
- Extraer seguridad y manejo de excepciones a librerías/módulos comunes.
- Añadir API Gateway para seguridad y ruteo.
- Externalizar configuración a un servidor de configuración.
- Pipeline CI/CD, monitoreo y alertas.
- Pruebas de carga para validar rendimiento.

---

## 10) Troubleshooting rápido

- **Puerto ocupado (5432/8080/5173):** cambia el puerto o detén el proceso que lo usa.
- **Reiniciar DB:** `docker restart bravo-credit-postgres`
- **Borrar contenedor DB (reset):**
  ```bash
  docker rm -f bravo-credit-postgres
  ```

## Seguridad (JWT + Roles)
Este servicio protege los endpoints usando JWT (HS256) y control de acceso por roles.
Flujo de autenticación
1) El cliente solicita un token en:
   - POST /auth/token (público)
2) El servicio responde un:
   - access_token (JWT) con expiración de 1 hora
3) Para consumir endpoints protegidos, el cliente envía:
   - Header Authorization: Bearer <access_token>
     Credenciales de demo
- username: supervisor
- password: 1234
  Nota: esto es solo para demo/ejercicio. En un entorno real se reemplaza por un proveedor de identidad o un store de usuarios con hash (BCrypt), rotación de secretos, MFA, etc.
  Autorización por rutas
- /auth/**: permitAll (sin token)
- /api/v1/**: requiere ROLE_SUPERVISOR
- cualquier otra ruta: requiere autenticación
  El rol se obtiene del claim roles del JWT (ej. ["SUPERVISOR"]) y se mapea internamente a ROLE_SUPERVISOR.
  Configuración del secreto (HS256)
  El token se firma/valida con un secreto configurado en:
- security.jwt.secret
  Ejemplo en src/main/resources/application.properties:
  security.jwt.secret=change_me_super_secret_key
  Recomendación: usar variable de entorno:
  security.jwt.secret=${JWT_SECRET}
  CORS (Frontend local)
  Para permitir llamadas desde el frontend local, CORS está habilitado para:
- http://localhost:5173
  y métodos GET, POST, PUT, DELETE, OPTIONS.
  Ejemplos con cURL
1) Obtener token
   curl -X POST "http://localhost:8080/auth/token" \
   -H "Content-Type: application/json" \
   -d '{"username":"supervisor","password":"1234"}'
   Respuesta esperada:
   { "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." }
2) Consumir endpoint protegido
   TOKEN="PEGA_AQUI_EL_ACCESS_TOKEN"
   curl -X POST "http://localhost:8080/api/v1/created/client" \
   -H "Content-Type: application/json" \
   -H "Authorization: Bearer $TOKEN" \
   -d '{"identifier":"AUSP000730MBSGTNA6","monthlyIncome":15000,"country":"MX","amount":200000}'
   Nota técnica (Spring Resource Server)
   La validación de JWT normalmente se activa configurando oauth2ResourceServer().jwt(...) en el SecurityFilterChain.
   Si al probar notas que el token no se está validando como esperas, asegúrate de tener algo como:
   http.oauth2ResourceServer(oauth2 -> oauth2
   .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
   );
   (El claim roles se convierte a authorities con prefijo ROLE_, por eso se exige hasRole("SUPERVISOR").)
