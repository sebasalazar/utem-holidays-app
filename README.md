# Proyecto

Este es un proyecto de ejemplo, que implementará el cálculo de días hábiles entre dos fechas.

Este proyecto asume como día hábil un día entre Lunes a Viernes que no sea un feriado legal de chile. Para validarlo se consumirá un servicio rest proporcionado por el gobierno.

## Requerimientos
- Java. Se necesita una máquina virtual de java, en  ubuntu se puede instalar como **sudo apt-get install openjdk-11-jdk**
- Para compilar es necesario tener un entorno maven, en ubuntu se puede instalar como **sudo apt-get install maven**
- Además se utilizará una conexión a un motor de base de datos postgresql.
-- host: 143.110.144.202
-- puerto: 6432
-- db: holidaysdb
-- usuario: holiday
-- password: (el mismo que el usuario)

## Diseño
Para consultar un día feriado, se consumirá el servicio gubernamental https://apis.digital.gob.cl/fl

## Ejecución

### Ejecución básica
**java -jar app/target/holiday.war**



