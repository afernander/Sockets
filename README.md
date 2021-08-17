# Reto Laboratorio 1 - Tópicos Especiales en Telemática - 2021-2 - Universidad EAFIT

## Conceptos fundamentales

Dos o más procesos / aplicaciones en Internet se pueden comunicar de diferentes maneras para implementar un servicio distribuido. Uno de los mecanismos de comunicación más básico es a través de **Sockets**, lo cual plantea una tubería o enlace de comunicación de intercambio de mensajes entre los procesos.

En este caso: el protocolo de la aplicación, middleware, API, etc es responsabilidad del diseñador y desarrollar la aplicación distribuida.

## Especificaciones del reto
1. Defina cualquier tipo de **aplicación sencilla distribuida** que desee diseñar e implementar. (ej: calculadora distribuida, chat, CRUD, etc)
1. Utilizar Sockets **TCP** o **UDP** en cualquier lenguaje de programación de su preferencia
1. Defina, diseñe e implemente el protocolo de aplicación que requiera para implementar dicha aplicación.
1. Realice inicialmente todos los supuestos que requiera respecto a tipo de sistema: C/S o P2P, tipo de arquitectura, y aplique algunos de los conceptos fundamentales de los sistemas distribuidos que se verán en esta Lectura: _Introducción a Sistemas Distribuidos_.
1. Impleméntela en **AWS Educate**. Con el fin de probar la funcionalidad del sistema, se requiere que al menos instancie 3 máquinas **EC2**.

### Fecha de entrega
* 16 de agosto 2021

---
---

# Solución
Primero que todo, es importante hablar sobre la definición de **Socket**.

**Socket**: es un medio por el cual dos **procesos** (que pueden estr situados en computadoras distintas) pueden intercambiar datos, generalmente de manera fiable y ordenada. El término de **socket** también es usado como el nombre de una **API** para la familia de _protocolos de internet_ **TCP/IP** que proviene del sistema operativo. Los **sockets** constituyen el mecanismo para la entrega de paquetes de datos provenientes de la **tarjeta de red** a los **procesos** o **hilos** apropiados. Un **socket** queda definido por un par de **direcciones IP** local y remota, un **protocolo de transporte** y un par de números de **puerto local** y **remoto**.

Para que dos procesos puedan comunicarse entre sí es necesario:
* Que un proceso sea capaz de localizar al otro
* Que ambos procesos sean capaces de intercambiarse cualquier secuencia de **octetos**, es decir, datos relevantes a su propósito o finalidad.

Recursos necesarios:
* Un par de **direcciones** del protocolo de red (**IP** en el caso de que usemos el protocolo **TCP/IP**) que sirven para identificar la computadora de origen y la remota.
* Independientemente del protocolo, se requiere de un par de números de puerto, que sirven para identificar lor programas dentro de cada computadora.

| UDP (User Datagram Protocol)                                                                                                                | TCP (Transmission Control Protocol)                                                                                                                                                                                                                     |
|---------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Necesita que le entreguemos paquetes de datos que el usuario debe construir.                                                                | Admite bloques de datos (cuyo tamaño puede ir desde 1 byte hasta muchos K bytes, dependiendo de la implementación) que serán empaquetados de forma transparente antes de ser transmitidos. (El usuario no tiene nada que ver, todo sucede internamente) |
| Si un paquete se llega a perder, UDP no hace nada.                                                                                          | Si un paquete se pierde, TCP lo retransmitirá, y este proceso durará hasta que el segmento haya sido entregado al host receptor, o se produzca un número máximo de retransmisiones.                                                                     |
| En UDP controlamos qué datos viajan en cada paquete                                                                                         | En TCP no podemos controlar qué datos viajan en cada paquete, debido al **empaquetamiento automático**. De hecho, TCP espera un tiempo prudencial a tener bastantes datos para transmitir antes de enviar un segmento, para ahorrar ancho de banda.         |
| Si necesitamos que los datos tarden el mínimo tiempo posible en llegar al receptor, UDP es la mejor opción, pues este tiene menor latencia. | Si requerimos asegurar la llegada de los datos al receptor, TCP es la opción más confiable.                                                                                                                                                             |

---

Las razones para elegir **TCP** sobre **UDP** son:
* **Conexión**: TCP ofrece una conexión más fácil de establecer (handshaking). Con UDP no existe tal conexión, pues cada paquete se envía individualmente directo del emisor al receptor sin un canal de datos confiable.
* **Secuenciación**: TCP es un protocolo confiable que agrega un número de secuencia a los paquetes de datos en la medida que los envía. Esto ayuda al receptor recopilar y reconstruir el mensaje. UDP no hace esto en sus headers, por lo que el receptor no sabe si recibió todos los paquetes ni puede saber su orden correcto.
* **Velocidad**: Al UDP no tener tantos requerimientos, ofrece una conexión más rápida. Por otro lado TCP, es más lento, pero más confiable. Para el caso de nuestra aplicación, al no manejar grandes volúmenes de datos pero que requieren garantizar su integridad, lo más conveniente es usar TCP.
* **Confiabilidad**: TCP provee recursos para secuenciación de paquetes, confirmación, detección de errores y su respectiva corrección. Esto lo hace un protocolo confiable. Por otro lado, UDP no tiene esto. A pesar de que UDP puede llegar a detectar los errores, no hace nada para corregirlos. Los paquetes erróneos simplemente son descartados. Por esto es más confiable TCP.
* **Verificación**: Cuando los paquetes TCP son recibidos por el receptor (valga la redundancia), este envía una señal de confirmación al emisor. Si el emisor no la recibe, asumirá que los paquetes no fueron entregados, o bien, fueron entregados corruptos, por lo que procede a enviarlos nuevamente. Por otra parte, UDP no tiene señales de confirmación, por lo que el emisor no sabe si los paquetes fueron recibidos o no.
* **Control de congestión**: TCP se asegura de controlar la congestión o el alto flujo. Al ser un protocolo orientado a la conexión, se asegura de que no haya congestión en el canal de datos que se estableció. UDP al ser connectionless, no se preocupa mucho por esto. Cada paquete se envía por separado y si un paquete se pierde debido a la congestión, el receptor no puede hacer mayor cosa al respecto.
* **Aplicaciones**: TCP se usa en aplicaciones donde la confiabilidad es más importante, como envío de archivos, emails, web browsing, etc. UDP es usado en aplicaciones donde la velocidad es más importante, como videoconferencias, live streaming y juegos online.