# Simulador de planificacion

Programa de consola para simular los algoritmos FCFS y SJF no apropiativo.

## Requisitos

- Java JDK 17 o superior.
- Apache NetBeans con soporte para proyectos Maven.

## Abrir en NetBeans

1. Descomprimir el archivo del proyecto.
2. Abrir NetBeans.
3. Seleccionar `File > Open Project`.
4. Seleccionar la carpeta `SimuladorPlanificacion`.
5. Ejecutar la clase `simuladorplanificacion.SimuladorPlanificacion`.

## Funciones

- Ingreso de uno a cien procesos.
- Validacion de nombres, tiempos de llegada y rafagas.
- Simulacion FCFS.
- Simulacion SJF no apropiativo.
- Calculo de ST, CT, TAT, WT y RT.
- Calculo de los promedios de WT y TAT.
- Representacion del diagrama de Gantt en consola.
- Representacion de periodos de CPU inactivo.

## Formulas

```text
TAT = CT - AT
WT  = TAT - BT
RT  = ST - AT
```

## Compilar desde PowerShell

```powershell
mvn clean package
```

## Ejecutar desde PowerShell

```powershell
java -jar target\SimuladorPlanificacion-1.0.0.jar
```
# Practica1-Sistemas-Operativos2
