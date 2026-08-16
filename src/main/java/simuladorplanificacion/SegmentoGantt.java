package simuladorplanificacion;


public class SegmentoGantt {

    private final String nombre;
    private final int inicio;
    private final int fin;

    /**
     * Construye un segmento del diagrama.
     *
     * @param nombre nombre del proceso o INACTIVO
     * @param inicio tiempo inicial del segmento
     * @param fin tiempo final del segmento
     */
    public SegmentoGantt(String nombre, int inicio, int fin) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del segmento es obligatorio.");
        }
        if (inicio < 0 || fin <= inicio) {
            throw new IllegalArgumentException("El intervalo del segmento no es valido.");
        }

        this.nombre = nombre;
        this.inicio = inicio;
        this.fin = fin;
    }

    public String getNombre() {
        return nombre;
    }

    public int getInicio() {
        return inicio;
    }

    public int getFin() {
        return fin;
    }

    public int getDuracion() {
        return fin - inicio;
    }
}
