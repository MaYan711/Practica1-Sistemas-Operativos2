package simuladorplanificacion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;


public final class SimuladorPlanificacion {

    private static final int MAXIMO_PROCESOS = 100;
    private static final int MAXIMO_TIEMPO = 1_000_000;

    private SimuladorPlanificacion() {
    }

    /**
     * Inicia el programa, solicita los datos y muestra los resultados.
     *
     * @param args argumentos de consola no utilizados
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        try (Scanner scanner = new Scanner(System.in)) {
            boolean repetir;

            do {
                mostrarEncabezado();
                List<Proceso> procesos = leerProcesos(scanner);
                int opcion = leerAlgoritmo(scanner);

                try {
                    ejecutarOpcion(opcion, procesos);
                } catch (IllegalArgumentException ex) {
                    System.out.println("\nERROR DE SIMULACION: " + ex.getMessage());
                }

                repetir = leerSiNo(scanner, "\nDesea realizar otra simulacion? (S/N): ");
                System.out.println();
            } while (repetir);

            System.out.println("Programa finalizado.");
        }
    }

    private static void mostrarEncabezado() {
        System.out.println("============================================================");
        System.out.println("        SIMULADOR DE PLANIFICACION DE PROCESOS");
        System.out.println("                   FCFS Y SJF");
        System.out.println("============================================================");
    }

    private static List<Proceso> leerProcesos(Scanner scanner) {
        int cantidad = leerEnteroEnRango(
                scanner,
                "Ingrese el numero de procesos (1-" + MAXIMO_PROCESOS + "): ",
                1,
                MAXIMO_PROCESOS);

        List<Proceso> procesos = new ArrayList<>();
        Set<String> nombresUtilizados = new HashSet<>();

        for (int i = 0; i < cantidad; i++) {
            System.out.println("\n--- Datos del proceso " + (i + 1) + " ---");

            String nombre = leerNombreProceso(scanner, nombresUtilizados);
            int llegada = leerEnteroEnRango(
                    scanner,
                    "Tiempo de llegada (AT): ",
                    0,
                    MAXIMO_TIEMPO);
            int rafaga = leerEnteroEnRango(
                    scanner,
                    "Rafaga de CPU (BT): ",
                    1,
                    MAXIMO_TIEMPO);

            procesos.add(new Proceso(nombre, llegada, rafaga, i));
        }

        return procesos;
    }

    private static String leerNombreProceso(
            Scanner scanner,
            Set<String> nombresUtilizados) {

        while (true) {
            System.out.print("Nombre del proceso (ejemplo P1): ");
            String nombre = scanner.nextLine().trim();

            if (nombre.isEmpty()) {
                System.out.println("Error: el nombre no puede estar vacio.");
                continue;
            }
            if (nombre.length() > 12) {
                System.out.println("Error: el nombre puede tener como maximo 12 caracteres.");
                continue;
            }

            String clave = nombre.toUpperCase(Locale.ROOT);
            if (nombresUtilizados.contains(clave)) {
                System.out.println("Error: ya existe un proceso con ese nombre.");
                continue;
            }

            nombresUtilizados.add(clave);
            return nombre;
        }
    }

    private static int leerAlgoritmo(Scanner scanner) {
        System.out.println("\nSeleccione el algoritmo:");
        System.out.println("1. FCFS");
        System.out.println("2. SJF no apropiativo");
        System.out.println("3. Ejecutar ambos algoritmos");

        return leerEnteroEnRango(scanner, "Opcion: ", 1, 3);
    }

    private static void ejecutarOpcion(int opcion, List<Proceso> procesos) {
        switch (opcion) {
            case 1 -> mostrarResultado(Planificador.simularFcfs(procesos));
            case 2 -> mostrarResultado(Planificador.simularSjf(procesos));
            case 3 -> {
                mostrarResultado(Planificador.simularFcfs(procesos));
                mostrarResultado(Planificador.simularSjf(procesos));
            }
            default -> throw new IllegalArgumentException("La opcion no es valida.");
        }
    }

    private static void mostrarResultado(ResultadoPlanificacion resultado) {
        System.out.println("\n============================================================");
        System.out.println("RESULTADOS DEL ALGORITMO: " + resultado.getAlgoritmo());
        System.out.println("============================================================");

        System.out.printf(
                "%-12s %5s %5s %5s %5s %5s %5s %5s%n",
                "Proceso", "AT", "BT", "ST", "CT", "TAT", "WT", "RT");
        System.out.println("------------------------------------------------------------");

        for (Proceso proceso : resultado.getProcesos()) {
            System.out.printf(
                    "%-12s %5d %5d %5d %5d %5d %5d %5d%n",
                    proceso.getNombre(),
                    proceso.getTiempoLlegada(),
                    proceso.getRafagaCpu(),
                    proceso.getTiempoInicio(),
                    proceso.getTiempoFinalizacion(),
                    proceso.getTiempoRetorno(),
                    proceso.getTiempoEspera(),
                    proceso.getTiempoRespuesta());
        }

        System.out.println("------------------------------------------------------------");
        System.out.printf(
                "Tiempo de espera promedio (WT): %.2f%n",
                resultado.calcularEsperaPromedio());
        System.out.printf(
                "Tiempo de retorno promedio (TAT): %.2f%n",
                resultado.calcularRetornoPromedio());
        System.out.printf(
                "Tiempo de respuesta promedio (RT): %.2f%n",
                resultado.calcularRespuestaPromedio());

        mostrarGantt(resultado.getSegmentos());
    }

    private static void mostrarGantt(List<SegmentoGantt> segmentos) {
        System.out.println("\nDIAGRAMA DE GANTT");
        System.out.println("------------------------------------------------------------");

        if (segmentos.isEmpty()) {
            System.out.println("No existen segmentos para mostrar.");
            return;
        }

        System.out.print(segmentos.get(0).getInicio());
        for (SegmentoGantt segmento : segmentos) {
            System.out.printf(
                    " --[%s]--> %d",
                    segmento.getNombre(),
                    segmento.getFin());
        }
        System.out.println();

        System.out.println("\nDetalle de segmentos:");
        System.out.printf("%-14s %8s %8s %10s%n", "Segmento", "Inicio", "Fin", "Duracion");
        System.out.println("----------------------------------------------");

        for (SegmentoGantt segmento : segmentos) {
            System.out.printf(
                    "%-14s %8d %8d %10d%n",
                    segmento.getNombre(),
                    segmento.getInicio(),
                    segmento.getFin(),
                    segmento.getDuracion());
        }
    }

    private static int leerEnteroEnRango(
            Scanner scanner,
            String mensaje,
            int minimo,
            int maximo) {

        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();

            try {
                int valor = Integer.parseInt(entrada);
                if (valor < minimo || valor > maximo) {
                    System.out.printf(
                            "Error: ingrese un numero entre %d y %d.%n",
                            minimo,
                            maximo);
                    continue;
                }
                return valor;
            } catch (NumberFormatException ex) {
                System.out.println("Error: debe ingresar un numero entero valido.");
            }
        }
    }

    private static boolean leerSiNo(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String respuesta = scanner.nextLine().trim();

            if (respuesta.equalsIgnoreCase("S")) {
                return true;
            }
            if (respuesta.equalsIgnoreCase("N")) {
                return false;
            }

            System.out.println("Error: responda solamente S o N.");
        }
    }
}
