INSTALACIÓN DE SERVICIO EN LOCAL

requisitos previos:
- Tener instalado Docker en tu máquina local.
- Tener instalado Java 17 o superior.
- Tener instalado Maven.
- Tener acceso a internet para clonar el repositorio y descargar dependencias.
- Tener instalado un cliente de Postgres (opcional, para verificar la base de datos).
- Tener instalado Git para clonar el repositorio.
- Tener un IDE como IntelliJ IDEA o Eclipse (opcional, para facilitar el desarrollo y ejecución del proyecto).
- Tener configurada la variable de entorno JAVA_HOME apuntando a la instalación de Java 17 o superior.
- Tener configurada la variable de entorno MAVEN_HOME apuntando a la instalación de Maven.
- Tener configurada la variable de entorno PATH para incluir los binarios de Java y Maven.
- Tener instalado cURL o Postman para probar las APIs del servicio.
- Tener conocimientos básicos de Java, Spring Boot y PostgreSQL.
- Tener conocimientos básicos de Docker y Maven.
- Tener conocimientos básicos de Git para clonar el repositorio.
- Tener conocimientos básicos de JSON para entender los formatos de entrada y salida de las APIs.
- Tener conocimientos básicos de HTTP para entender las solicitudes y respuestas de las APIs.

Para instalar y ejecutar el servicio en tu máquina local, sigue estos pasos:
1. Clona el repositorio:
   ```bash
   git clone git@github.com:hiramdamian99/bravo-credit-client.git

2. Levanta una imagen de Postgres usando Docker:

    ```bash
    docker run --name bravo-credit-postgres \
      -e POSTGRES_USER=bravo_credit_user \
      -e POSTGRES_PASSWORD=bravo_credit_password \
      -e POSTGRES_DB=demo \
      -p 5432:5432 \
      -d postgres:16
    ```

   
3. crea la base tabla en base datos con el siguiente comando:
   ```sql
   
 CREATE SCHEMA IF NOT EXISTS control;

   
   
CREATE TABLE IF NOT EXISTS control.tr_client (
process_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
identifier     VARCHAR(255),
monthly_income INTEGER,
amount         INTEGER,
country        VARCHAR(10),
created_at     TIMESTAMPTZ DEFAULT now(),
updated_at     TIMESTAMPTZ DEFAULT now(),
created_by     VARCHAR(255),
updated_by     VARCHAR(255)
);
   ```

4. levanta el microservicio con el siguiente comando:
   ```bash
   mvn spring-boot:run
   ```
las configuraciones de la base de datos se encuentran en el archivo application.properties ubicado en src/main/resources

5 . Levantar el front desaclopado de back, ubicarse en la carpeta public y ejecutar
python3 -m http.server 5173

**REGLAS DE NEGOCIO**

El servicio debe cumplir con las siguientes reglas de negocio al procesar las solicitudes de crédito:

Paises Aceptados:
- México 
- Colombia 


Formato de Identificador:
- México: El identificador debe ser un CURP válido (18 caracteres alfanuméricos).
- Colombia: El identificador debe ser un CC válido (entre 6 y 10 dígitos numéricos).

Monto a Solicitar: 
- El monto solicitado debe ser un valor numérico positivo.

Ingreso Mensual
- el ingreso mensual debe ser el al menos el 10% del monto solicitado. prara mexico
- el ingreso mensual debe ser al menos el 30% del monto solicitado para colombia.

Consultar en el banco si el cliente es apto para credito y retornar la cuenta asociada al cliente







Url de creacion de cliente de credito
http://localhost:8080/api/v1/created/client

formato de json de entrada
```json
{

  "identifier": "AUSP000730MBSGTNA6",
  "monthlyIncome": "15000",
  "country": "MX",
  "amount": "200000"
  
}
```

Url de consulta de cliente de credito
http://localhost:8080/api/v1/search/client


formato de json de entrada
```json
{

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
  
}


## Decisiones de implementación

### Arquitectura y stack
- Se desarrolló el microservicio con **arquitectura hexagonal** (Ports & Adapters).
- Las **reglas de negocio** y validaciones se ejecutan en el **dominio**.
- Se utiliza **Spring Boot** como framework principal.
- Se utiliza **Maven** como gestor de dependencias.
- Se utiliza **Java 17** como versión del lenguaje.
- Se utiliza **PostgreSQL** como base de datos.
- Se utiliza **Docker** para levantar la base de datos en un contenedor.

##El backend de bravo-credit-client está diseñado bajo un enfoque de Arquitectura Hexagonal, 
también conocida como Ports & Adapters, con el objetivo de mantener el dominio y los casos de uso independientes de frameworks, 
infraestructura y proveedores externos. Esto permite que el sistema sea testeable, mantenible, y evolutivo, reduciendo el acoplamiento con detalles técnicos.


La idea central es:
* El núcleo (Dominio ) define qué hace el sistema.
* La aplicación y la infraestructura resuelven cómo se conecta el sistema con el mundo exterior (HTTP, DB, APIs, mensajería, etc.).
* La comunicación se gobierna a través de puertos (interfaces) que el núcleo expone o consume.

Capas y responsabilidades
1) Dominio (Domain Usecase)
El dominio contiene la lógica de negocio “pura” y los conceptos principales del sistema.
Responsabilidades típicas:
* Entidades / Value Objects
* Reglas de negocio invariantes
* Modelos del dominio
* Excepciones de negocio
* Contratos del dominio (en algunos diseños)
Principio clave: el dominio no depende de Spring, JPA, HTTP, JSON, etc.

2) Aplicación 
La capa de aplicación orquesta el negocio a través de casos de uso (services o interactors). Aquí se coordinan validaciones, flujos, transacciones y llamadas a puertos.
Responsabilidades típicas:
* Casos de uso (ej. CreateClient, SearchClient)
* Definición de puertos:
    * Input ports: lo que el sistema ofrece (lo que el exterior puede pedir)
    * Output ports: lo que el sistema necesita (repositorios, APIs externas, mensajería, etc.)
* Modelos de entrada/salida de aplicación (DTOs internos o comandos/queries)

Regla: La aplicación depende del dominio, y de interfaces, no de implementaciones.

3) Adaptadores de entrada 
Son los puntos por donde entra una interacción del mundo exterior al sistema.
Ejemplos comunes:
* Controllers REST 
* Consumers de mensajería
Rol: traducen HTTP/JSON (u otro protocolo) a un caso de uso (puerto ), y transforman la respuesta a un formato de transporte.
Importante: el controller no “hace negocio”, solo conecta.

4) Adaptadores de salida 
Implementan los puertos  definidos por la aplicación para integrar infraestructura.
Ejemplos:
* Persistencia (SQL) como implementación de ClientRepositoryPort
* Integraciones con servicios externos (HTTP clients / SDKs)
* Mensajería (Rabbit)

Rol: cumplir el contrato del puerto, encapsulando detalles técnicos, manejo de errores de terceros y mapeos.

Flujo típico end-to-end (cómo “viaja” una petición)
1. HTTP Request llega a un Controller (adaptador ).
2. El controller valida lo mínimo de transporte (headers requeridos, formato, etc.).
3. Llama a un caso de uso (puerto ).
4. El caso de uso ejecuta reglas de negocio y llama a puertos  (repo, servicio externo, etc.).
5. Adaptadores output ejecutan integración técnica (DB/API) y retornan resultados al caso de uso.
6. El caso de uso retorna un resultado.
7. El controller traduce a HTTP Response.

Este flujo garantiza que el core no dependa del mundo exterior.

Implementaciones: cómo se materializa en el código
En una implementación profesional de hexagonal, normalmente se ve algo así:
* domain/ → entidades, objetos de valor, reglas
* application/in →  puertos (interfaces), controllera, commands/queries, REST controllers, , seguridad, request/response DTOs
* adapters/out/ → repositorios JPA, clients externos, implementaciones de puertos
* infrastructure/ → configuración (Spring @Configuration), observabilidad


Manejo de excepciones 
1. Mantener consistencia y facilitar troubleshooting (logs + correlation id + códigos claros).
1) Tipos de excepciones recomendadas
a) Excepciones de negocio (dominio/aplicación)
*  “CURP inválido”, “estado no permitido”
* Se convierten típicamente a 400 / 404 / 409 según el caso.
b) Excepciones de infraestructura
* Timeouts, errores de DB, caídas de servicios externos, etc.

2) Traducción a HTTP (Controller Advice / Global Handler)
Lo más sólido (y estándar en Spring) es centralizar la conversión a HTTP con:
* @RestControllerAdvice
* @ExceptionHandler
Resultado: Un formato de error uniforme como:
* timestamp
* status
* error
* message
* path
* traceId / correlationId
* details (opcional: lista de validaciones)
Esto permite que tu API sea predecible y fácil de consumir.

3) Semántica HTTP 
* 400 Bad Request: validación, formato, parámetros inválidos
* 404 Not Found: recurso inexistente
* 409 Conflict: conflicto de estado (duplicados, reglas de transición)
* 422 Unprocessable Entity: válido en sintaxis pero falla reglas de negocio (opcional)
* 500 Internal Server Error: errores inesperados
* 502/503/504: dependencias externas fallando (si manejas integraciones externas)
* 200 ok: creación exitosa 

4) Logging y trazabilidad 
* Log estructurado 
* Mensajes de error sin filtrar stack traces al cliente
* Stack trace solo en logs internos (o condicionado por ambiente)

Ventajas logradas con este diseño
* Alta testabilidad: puedes probar casos de uso con mocks de puertos.
* Mantenibilidad: cambios en DB o APIs externas no afectan el dominio.
* Evolución segura: agregar nuevos canales de entrada (gRPC, mensajería) no rompe el core.
* Menor acoplamiento: Spring/JPA/HTTP quedan como detalles periféricos.


### Persistencia y rendimiento
- Se implementó **actualización de clientes** mediante **query nativa** para optimizar el rendimiento.
- Se implementó el **filtrado de operaciones** mediante **query nativa** para optimizar el rendimiento.

### Calidad
- Se implementaron **pruebas unitarias** y **pruebas de integración** para asegurar la calidad del código.

**MEJORA CONTINUA**

- terminar de implelmtar la mejora de estilos del frontend
- Se recomienda agregar más pruebas unitarias y de integración para cubrir más casos de uso.
- terminar de imlementar el reecolamiento por medio de rabitmq
- gestionar operaciones de insercion en base de datos por medio de dos api un orq y procesador
para poder tener escalabilidad del procesador a multiples pods para soportar mayor carga, tambien mejorar
el rendimiento del api orq al no tener que esperar a que se realicen las operaciones de insercion en base de datos
- la seguridad la externalizaria desde una libreria
- usar un gateway para manejar la seguridad y el enrutamiento de las peticiones
- el manejo de excepciones las externalizaria a un modulo comun para utilizarlas dependiendo del tipo de aplicacion
- Se recomienda implementar pruebas de carga para evaluar el rendimiento bajo condiciones de alta demanda.
- la configuraciones de cada api en el archivo application.properties las externalizaria a un servidor de configuracion
- Se recomienda implementar un pipeline de CI/CD para automatizar pruebas, builds y despliegues.
- Se recomienda implementar monitoreo y alertas para el servicio en producción.
- Se recomienda realizar revisiones de código periódicas para mantener la calidad del código.


