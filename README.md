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



REGLAS DE NEGOCIO
El servicio debe cumplir con las siguientes reglas de negocio al procesar las solicitudes de crédito:

Paises Aceptados:
- México 
- Colombia 


Formato de Identificador:
- México: El identificador debe ser un CURP válido (18 caracteres alfanuméricos).
- Colombia: El identificador debe ser un NIT válido (10 dígitos numér

Monto a Solicitar: 
- El monto solicitado debe ser un valor numérico positivo.

Ingreso Mensual
- el ingreso mensual debe ser el al menos el 10% del monto solicitado. prara mexico
- el ingreso mensual debe ser al menos el 30% del monto solicitado para colombia.

La informacion bancaria del cliente debe ser alida





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

### Reglas de negocio
- Si el **país** no es **MX** o **CO**, se **rechaza** la solicitud.
- Si el **identificador** no cumple con el formato requerido para el país especificado, se **rechaza** la solicitud.

### Persistencia y rendimiento
- Se implementó **actualización de clientes** mediante **query nativa** para optimizar el rendimiento.
- Se implementó el **filtrado de operaciones** mediante **query nativa** para optimizar el rendimiento.

### Calidad
- Se implementaron **pruebas unitarias** y **pruebas de integración** para asegurar la calidad del código.


