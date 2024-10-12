# Mercadona Interview Exercise

## Descripción del Proyecto

Este proyecto es un microservicio para la gestión de productos de Mercadona, permitiendo operaciones CRUD (crear, leer, actualizar, eliminar) a través de una API REST. Los productos se identifican mediante un código EAN y la aplicación utiliza una base de datos H2 en memoria junto con migraciones Flyway para la inicialización de datos.

El PDF está en el repositorio privado https://github.com/MercadonaITSeleccion/Ismael_Heluani al que hay que solicitar acceso.

## Requisitos

- **Java 8 o superior**
- **Maven**
- **Cualquier IDE** (IntelliJ IDEA, Eclipse, VS Code, etc.)
- **Postman** (para probar las API REST)
- **Git** (opcional para clonar el repositorio)

## Configuración y Ejecución del Proyecto

### 1. Clonar el Repositorio

```bash
git clone https://github.com/iHeluani/Mercadona_Interview_Process.git
```

### 2. Configurar el Entorno

No es necesario realizar configuraciones adicionales si se utilizan las dependencias que se mencionan en el archivo pom.xml. Asegúrate de tener acceso a internet para que Maven descargue las dependencias requeridas.

### 3. Compilar y Ejecutar el Proyecto

Puedes compilar el proyecto utilizando Maven desde la línea de comandos, colocándote en el directorio del proyecto:
```bash
mvn clean install
mvn spring-boot:run
```

O, si prefieres usar un IDE:

- Importa el proyecto como un proyecto Maven.
- Ejecuta la clase principal (MercadonaInterviewProcess.java) que contiene el método main.

### 4. Probar el Servicio con Postman

Cabe destacar que necesitamos la versión de escritorio para poder hacer peticiones a **localhost:8080**, como es nuestro caso.

La aplicación expone las siguientes rutas:

- Por defecto se crea el usuario **admin1** con la password **admin1**, para probar la inserción de datos de usuarios con contraseñas codificadas en base de datos. Se puede utilizar para pruebas si no se quiere crear un nuevo usuario.

**Acceso abierto**
- POST /auth/register - Registro en el sistema.
- POST /auth/login - Login en el sistema.

**Acceso con autenticación**
- GET /products - Devuelve todos los productos.
- GET /products/{ean} - Devuelve un producto por su código EAN.
- POST /products - Añade un nuevo producto.
- PUT /products/{ean} - Actualiza un producto existente.
- DELETE /products/{ean} - Elimina un producto por su EAN.


**Ejemplo de Peticiones en Postman**

POST /auth/register

- URL: http://localhost:8080/auth/register
- Body:
```bash
{
    "username": "usuario1",
    "password": "usuario1"
}
```

- Una vez nos hemos registrado con éxito, procedemos al login.


POST /auth/login

- URL: http://localhost:8080/auth/login
- Body:
```bash
{
    "username": "usuario1",
    "password": "usuario1"
}
```
- Response:
```bash
Bearer Token
```

- Es **FUNDAMENTAL** copiar este token dado que para las siguientes peticiones necesitamos estar autenticados y pasar en la cabecera de las mismas este token.
```bash
Authorization --> Bearer Token --> Pegar el token copiado.
```


GET /products:

- URL: http://localhost:8080/products
- Response:
```bash
JSON con toda la información
```


GET /products/{ean}:

- URL: http://localhost:8080/products/1234567890123
- Response:
```bash
{
  "ean": "1234567890123",
  "name": "Producto Test",
  "provider": "Proveedor Ejemplo",
  "destination": "España"
}
```


POST /products:

- URL: http://localhost:8080/products
- Body (JSON):
```bash
{
  "ean": "1234567890999",
  "name": "Producto Test",
  "provider": "Proveedor Ejemplo",
  "destination": "España"
}
```
- Response: "Producto creado con éxito: 1234567890999"


PUT /products/{ean}:

- URL: http://localhost:8080/products/1234567890999
- Body (JSON):
```bash
{
  "name": "Nuevo Nombre",
  "provider": "Nuevo Proveedor",
  "destination": "España"
}
```
- Response: "Producto actualizado con éxito: 1234567890999"


DELETE /products/{ean}:

- URL: http://localhost:8080/products/1234567890123
- Response: "Producto eliminado con éxito: 1234567890123"

### 5. Testing

El proyecto incluye tests unitarios para el controlador ProductController. Para ejecutar los tests:
```bash
mvn test
```
### 6. Base de Datos

El servicio usa una base de datos H2 en memoria para almacenar los productos y las migraciones Flyway para la inicialización de datos. Esto permite que cada vez que se inicie el proyecto, los datos se inicialicen automáticamente.

**Configuración de la Base de Datos**
- La configuración se puede encontrar en **application.properties**. Si deseas acceder a la consola H2, puedes habilitar la consola yendo a:

```bash
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: (vacío por defecto)
```

### Estructura del Proyecto

```bash
src/
├── main/
│   ├── java/
│   │   └── com.mercadona.interview/
│   │       ├── config/           # Configuraciones de seguridad y base de datos
│   │       ├── controller/       # Controladores REST
│   │       ├── exception/        # Excepciones personalizadas
│   │       ├── model/            # Modelos de datos
│   │       ├── repository/       # Repositorios JPA
│   │       ├── service/          # Lógica de negocio y servicios
│   │       └── utils/            # Clases de utilidad
│   └── resources/
│       ├── application.properties # Configuración de Spring Boot
│       └── db/migration/         # Scripts de migración Flyway
└── test/                         # Tests unitarios
```

### Contacto
Para cualquier duda, por favor contacta a [heluani@hotmail.com].
