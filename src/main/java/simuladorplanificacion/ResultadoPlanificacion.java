package simuladorplanificacion;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ResultadoPlanificacion {

    private final String algoritmo;
    private final List<Proceso> procesos;
    private final List<SegmentoGantt> segmentos;

    /**
     * Construye el resultado final de una simulacion.
     *
     * @param algoritmo nombre del algoritmo utilizado
     * @param procesos procesos con sus metricas calculadas
     * @param segmentos intervalos del diagrama de Gantt
     */
    public ResultadoPlanificacion(
            String algoritmo,
            List<Proceso> procesos,
            List<SegmentoGantt> segmentos) {

        this.algoritmo = algoritmo;

        List<Proceso> procesosOrdenados = new ArrayList<>(procesos);
        procesosOrdenados.sort(Comparator.comparingInt(Proceso::getOrdenEntrada));

        this.procesos = List.copyOf(procesosOrdenados);
        this.segmentos = List.copyOf(segmentos);
    }

    public String getAlgoritmo() {
        return algoritmo;
    }

    public List<Proceso> getProcesos() {
        return procesos;
    }

    public List<SegmentoGantt> getSegmentos() {
        return segmentos;
    }

    public double calcularEsperaPromedio() {
        return procesos.stream()
                .mapToInt(Proceso::getTiempoEspera)
                .average()
                .orElse(0.0);
    }

    public double calcularRetornoPromedio() {
        return procesos.stream()
                .mapToInt(Proceso::getTiempoRetorno)
                .average()
                .orElse(0.0);
    }

    public double calcularRespuestaPromedio() {
        return procesos.stream()
                .mapToInt(Proceso::getTiempoRespuesta)
                .average()
                .orElse(0.0);
    }
}
