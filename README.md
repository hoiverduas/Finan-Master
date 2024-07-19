Generalidades
Este proyecto es una aplicación utilizada por los empleados de una entidad financiera. 
La aplicación permite la administración de clientes, la creación de productos financieros (cuentas corrientes y de ahorro)
y la realización de movimientos transaccionales (consignaciones, retiros y transferencias).
A continuación se explica en detalle cada una de las funcionalidades desarrolladas en el proyecto.

Cliente

Funcionalidades:
Crear Cliente: Permite registrar un nuevo cliente con los atributos mínimos: id, tipo de identificación, número de identificación, nombres, apellidos, correo electrónico, fecha de nacimiento, fecha de creación y fecha de modificación 
(se calcula automáticamente al actualizar la información).
Modificar Cliente: Permite actualizar la información de un cliente existente. La fecha de modificación se calcula automáticamente con cada actualización.
Eliminar Cliente: Permite eliminar un cliente siempre y cuando no tenga productos financieros vinculados.


Productos (Cuentas)

Funcionalidades:
Crear Producto: Permite crear cuentas corrientes y de ahorro vinculadas a un cliente existente. 
Los atributos mínimos incluyen: id, tipo de cuenta, número de cuenta (único y generado automáticamente),
estado (activa, inactiva, cancelada), saldo, exención GMF, fecha de creación (calculada automáticamente), 
fecha de modificación, y cliente asociado.

Restricciones:
Las cuentas de ahorro no pueden tener un saldo menor a $0.
El número de cuenta debe tener 10 dígitos, empezando con "53" para cuentas de ahorro y "33" para cuentas corrientes.
Las cuentas pueden ser activadas o inactivadas en cualquier momento.
Solo se pueden cancelar las cuentas con saldo igual a $0.

Transacciones (Movimientos Financieros)

Funcionalidades:
Consignación: Permite depositar una cantidad específica en una cuenta.
Se actualiza el saldo de la cuenta y se registra la transacción en el historial de movimientos.
Retiro: Permite retirar una cantidad específica de una cuenta.
Se actualiza el saldo de la cuenta y se registra la transacción en el historial de movimientos.
Si los fondos son insuficientes, se lanza una excepción.
Transferencia: Permite transferir una cantidad específica de una cuenta a otra.
Se actualizan los saldos de ambas cuentas y se registra la transacción en el historial de movimientos.
Si los fondos son insuficientes en la cuenta de origen, se lanza una excepción.


Detalles Técnicos

Capas del Proyecto:

Controller: Maneja las solicitudes HTTP y dirige las acciones correspondientes al servicio adecuado.

Service: Contiene la lógica de negocio y realiza operaciones sobre las entidades.

Repository: Interactúa con la base de datos para realizar operaciones CRUD sobre las entidades.

Entity: Representa las tablas de la base de datos.

Exception: Maneja las excepciones personalizadas.

DTO (Data Transfer Object): Utilizado para transferir datos entre las diferentes capas.

Configuración de la Base de Datos:

Se utiliza PostgreSQL.
La configuración de la base de datos se encuentra en el archivo application.properties.

Pruebas Unitarias:
Implementadas utilizando Junit para las capas Service y Controller.



