# Casos de prueba

## Caso 1: datos del ejercicio 2

Procesos:

| Proceso | AT | BT |
| --- | ---: | ---: |
| P1 | 0 | 1 |
| P2 | 2 | 9 |
| P3 | 4 | 3 |
| P4 | 5 | 4 |

Seleccionar la opcion 3 para ejecutar FCFS y SJF.

Resultados esperados para ambos algoritmos:

```text
Gantt: 0 --[P1]--> 1 --[INACTIVO]--> 2 --[P2]--> 11 --[P3]--> 14 --[P4]--> 18
WT promedio: 4.00
TAT promedio: 8.25
RT promedio: 4.00
```

## Caso 2: SJF cambia el orden

Procesos:

| Proceso | AT | BT |
| --- | ---: | ---: |
| P1 | 0 | 5 |
| P2 | 1 | 2 |
| P3 | 1 | 1 |

Seleccionar la opcion 2 para ejecutar SJF.

Resultado esperado:

```text
Gantt: 0 --[P1]--> 5 --[P3]--> 6 --[P2]--> 8
WT promedio: 3.00
TAT promedio: 5.67
RT promedio: 3.00
```

## Caso 3: manejo de errores

Probar las siguientes entradas:

- Letras al solicitar un numero.
- Cantidad de procesos igual a cero.
- Nombre vacio.
- Nombre de proceso repetido.
- Tiempo de llegada negativo.
- Rafaga igual a cero.
- Opcion de algoritmo fuera del rango 1 a 3.
- Respuesta diferente de S o N.

El programa debe mostrar un mensaje de error y volver a solicitar el dato.

## Capturas recomendadas

1. Ingreso de los procesos.
2. Tabla final de FCFS.
3. Gantt de FCFS.
4. Tabla final de SJF.
5. Gantt de SJF.
6. Al menos una validacion de entrada incorrecta.
