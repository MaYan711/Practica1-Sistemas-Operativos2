package simuladorplanificacion;

public class Proceso {

    private final String nombre;
    private final int tiempoLlegada;
    private final int rafagaCpu;
    private final int ordenEntrada;

    private int tiempoInicio;
    private int tiempoFinalizacion;
    private int tiempoRetorno;
    private int tiempoEspera;
    private int tiempoRespuesta;

    /**
     * Construye un proceso con sus datos iniciales.
     *
     * @param nombre identificador del proceso
     * @param tiempoLlegada tiempo de llegada al sistema
     * @param rafagaCpu tiempo total requerido de CPU
     * @param ordenEntrada posicion en la que fue ingresado
     */
    public Proceso(String nombre, int tiempoLlegada, int rafagaCpu, int ordenEntrada) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del proceso es obligatorio.");
        }
        if (tiempoLlegada < 0) {
            throw new IllegalArgumentException("El tiempo de llegada no puede ser negativo.");
        }
        if (rafagaCpu <= 0) {
            throw new IllegalArgumentException("La rafaga de CPU debe ser mayor que cero.");
        }

        this.nombre = nombre;
        this.tiempoLlegada = tiempoLlegada;
        this.rafagaCpu = rafagaCpu;
        this.ordenEntrada = ordenEntrada;
        this.tiempoInicio = -1;
        this.tiempoFinalizacion = -1;
        this.tiempoRetorno = -1;
        this.tiempoEspera = -1;
        this.tiempoRespuesta = -1;
    }

    /**
     * Crea una copia sin resultados calculados.
     *
     * @return copia del proceso
     */
    public Proceso copiar() {
        return new Proceso(nombre, tiempoLlegada, rafagaCpu, ordenEntrada);
    }

    /**
     * Registra el inicio y el final, y calcula todas las metricas.
     *
     * @param inicio primer instante de uso del CPU
     * @param finalizacion instante en que termina el proceso
     */
    public void completar(int inicio, int finalizacion) {
        if (inicio < tiempoLlegada) {
            throw new IllegalArgumentException("El proceso no puede iniciar antes de llegar.");
        }
        if (finalizacion < inicio) {
            throw new IllegalArgumentException("La finalizacion no puede ser menor que el inicio.");
        }

        this.tiempoInicio = inicio;
        this.tiempoFinalizacion = finalizacion;
        this.tiempoRetorno = tiempoFinalizacion - tiempoLlegada;
        this.tiempoEspera = tiempoRetorno - rafagaCpu;
        this.tiempoRespuesta = tiempoInicio - tiempoLlegada;
    }

    public String getNombre() {
        return nombre;
    }

    public int getTiempoLlegada() {
        return tiempoLlegada;
    }

    public int getRafagaCpu() {
        return rafagaCpu;
    }

    public int getOrdenEntrada() {
        return ordenEntrada;
    }

    public int getTiempoInicio() {
        return tiempoInicio;
    }

    public int getTiempoFinalizacion() {
        return tiempoFinalizacion;
    }

    public int getTiempoRetorno() {
        return tiempoRetorno;
    }

    public int getTiempoEspera() {
        return tiempoEspera;
    }

    public int getTiempoRespuesta() {
        return tiempoRespuesta;
    }
}
