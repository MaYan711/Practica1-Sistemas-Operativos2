package simuladorplanificacion;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Contiene la implementacion de FCFS y SJF no apropiativo.
 */
public final class Planificador {

    private static final String CPU_INACTIVO = "INACTIVO";

    private Planificador() {
    }

    /**
     * Ejecuta los procesos en orden de llegada.
     *
     * @param procesos procesos originales
     * @return resultado de la simulacion FCFS
     */
    public static ResultadoPlanificacion simularFcfs(List<Proceso> procesos) {
        validarProcesos(procesos);

        List<Proceso> copia = copiarProcesos(procesos);
        copia.sort(Comparator
                .comparingInt(Proceso::getTiempoLlegada)
                .thenComparingInt(Proceso::getOrdenEntrada));

        List<Proceso> completados = new ArrayList<>();
        List<SegmentoGantt> gantt = new ArrayList<>();
        int tiempoActual = 0;

        for (Proceso proceso : copia) {
            if (tiempoActual < proceso.getTiempoLlegada()) {
                gantt.add(new SegmentoGantt(
                        CPU_INACTIVO,
                        tiempoActual,
                        proceso.getTiempoLlegada()));
                tiempoActual = proceso.getTiempoLlegada();
            }

            int inicio = tiempoActual;
            tiempoActual += proceso.getRafagaCpu();

            proceso.completar(inicio, tiempoActual);
            gantt.add(new SegmentoGantt(proceso.getNombre(), inicio, tiempoActual));
            completados.add(proceso);
        }

        return new ResultadoPlanificacion("FCFS", completados, gantt);
    }

    /**
     * Ejecuta SJF no apropiativo. Entre los procesos disponibles selecciona
     * el de menor rafaga. En caso de empate, utiliza llegada y orden de entrada.
     *
     * @param procesos procesos originales
     * @return resultado de la simulacion SJF
     */
    public static ResultadoPlanificacion simularSjf(List<Proceso> procesos) {
        validarProcesos(procesos);

        List<Proceso> pendientes = copiarProcesos(procesos);
        List<Proceso> completados = new ArrayList<>();
        List<SegmentoGantt> gantt = new ArrayList<>();
        int tiempoActual = 0;

        while (!pendientes.isEmpty()) {
            Proceso seleccionado = seleccionarProcesoSjf(pendientes, tiempoActual);

            if (seleccionado == null) {
                int proximaLlegada = buscarProximaLlegada(pendientes);
                gantt.add(new SegmentoGantt(CPU_INACTIVO, tiempoActual, proximaLlegada));
                tiempoActual = proximaLlegada;
                continue;
            }

            int inicio = tiempoActual;
            tiempoActual += seleccionado.getRafagaCpu();

            seleccionado.completar(inicio, tiempoActual);
            gantt.add(new SegmentoGantt(
                    seleccionado.getNombre(),
                    inicio,
                    tiempoActual));

            completados.add(seleccionado);
            pendientes.remove(seleccionado);
        }

        return new ResultadoPlanificacion("SJF no apropiativo", completados, gantt);
    }

    private static Proceso seleccionarProcesoSjf(
            List<Proceso> pendientes,
            int tiempoActual) {

        Proceso mejor = null;

        for (Proceso candidato : pendientes) {
            if (candidato.getTiempoLlegada() > tiempoActual) {
                continue;
            }

            if (mejor == null || esMejorCandidato(candidato, mejor)) {
                mejor = candidato;
            }
        }

        return mejor;
    }

    private static boolean esMejorCandidato(Proceso candidato, Proceso actual) {
        if (candidato.getRafagaCpu() != actual.getRafagaCpu()) {
            return candidato.getRafagaCpu() < actual.getRafagaCpu();
        }
        if (candidato.getTiempoLlegada() != actual.getTiempoLlegada()) {
            return candidato.getTiempoLlegada() < actual.getTiempoLlegada();
        }
        return candidato.getOrdenEntrada() < actual.getOrdenEntrada();
    }

    private static int buscarProximaLlegada(List<Proceso> pendientes) {
        return pendientes.stream()
                .mapToInt(Proceso::getTiempoLlegada)
                .min()
                .orElseThrow();
    }

    private static List<Proceso> copiarProcesos(List<Proceso> procesos) {
        List<Proceso> copia = new ArrayList<>();
        for (Proceso proceso : procesos) {
            copia.add(proceso.copiar());
        }
        return copia;
    }

    private static void validarProcesos(List<Proceso> procesos) {
        if (procesos == null || procesos.isEmpty()) {
            throw new IllegalArgumentException("Debe existir al menos un proceso.");
        }
    }
}
