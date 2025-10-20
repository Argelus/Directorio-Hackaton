package org.generation.agenda;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Implementación concreta de la interfaz {@link Agenda} que gestiona los contactos
 * usando un {@link LinkedHashMap}.
 *
 * <p>Esta estructura de datos combina las ventajas de un mapa (búsqueda O(1)
 * promedio por clave) y un conjunto enlazado (preservación del orden de inserción).
 * La clave del mapa es el nombre normalizado del contacto, y el valor es la
 * instancia {@link Contacto} correspondiente.
 */
public class AgendaContactos implements Agenda {

    /** Capacidad máxima de la agenda. */
    private final int capacidad;
    /**
     * Mapa que almacena los contactos con clave el nombre normalizado.
     * {@link LinkedHashMap} mantiene el orden en el que se añadieron los contactos.
     */
    private final LinkedHashMap<String, Contacto> datos;

    /**
     * Crea una agenda con la capacidad por defecto de 10 contactos.
     */
    public AgendaContactos() {
        this(10);
    }

    /**
     * Crea una agenda con la capacidad especificada.
     * @param capacidad número máximo de contactos permitidos (debe ser mayor que cero)
     * @throws IllegalArgumentException si la capacidad no es positiva
     */
    public AgendaContactos(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor que cero.");
        }
        this.capacidad = capacidad;
        // Estimamos el tamaño inicial del mapa para evitar redimensionamientos internos.
        this.datos = new LinkedHashMap<>(capacidad);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Complejidad: O(1) promedio.
     * Primero se comprueba si hay espacio disponible y si ya existe un contacto con el mismo
     * nombre. Si no hay duplicado y la agenda no está llena, se inserta en el mapa.
     */
    @Override
    public boolean anadirContacto(Contacto c) {
        if (agendaLlena()) {
            System.out.println("No se puede añadir: la agenda está llena.");
            return false;
        }
        String clave = c.getClaveNombre();
        if (datos.containsKey(clave)) {
            System.out.println("No se puede añadir: ya existe un contacto con ese nombre.");
            return false;
        }
        datos.put(clave, c);
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Complejidad: O(1) promedio ya que usa la búsqueda en el mapa por la clave normalizada.</p>
     */
    @Override
    public boolean existeContacto(Contacto c) {
        return datos.containsKey(c.getClaveNombre());
    }

    /**
     * {@inheritDoc}
     *
     * <p>Recorre todos los valores del mapa manteniendo el orden de inserción para mostrarlos.
     * Complejidad: O(n), siendo n el número de contactos en la agenda.</p>
     */
    @Override
    public void listarContactos() {
        if (datos.isEmpty()) {
            System.out.println("(Agenda vacía)");
            return;
        }
        int i = 1;
        for (Map.Entry<String, Contacto> entry : datos.entrySet()) {
            System.out.println(i + ". " + entry.getValue());
            i++;
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>La búsqueda se realiza normalizando la clave proporcionada y recuperando
     * directamente del mapa. Complejidad: O(1) promedio.</p>
     */
    @Override
    public Optional<Contacto> buscaContacto(String nombre) {
        String clave = Contacto.normalizarNombre(nombre);
        return Optional.ofNullable(datos.get(clave));
    }

    /**
     * {@inheritDoc}
     *
     * <p>Para eliminar, se normaliza la clave del contacto dado y se usa el mapa
     * para eliminar en O(1) promedio. Si se elimina, se muestra un mensaje.</p>
     */
    @Override
    public boolean eliminarContacto(Contacto c) {
        String clave = c.getClaveNombre();
        Contacto eliminado = datos.remove(clave);
        if (eliminado != null) {
            System.out.println("Contacto eliminado: " + eliminado.getNombre());
            return true;
        }
        System.out.println("No se encontró el contacto para eliminar.");
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Devuelve {@code true} si se ha alcanzado o superado el número máximo de contactos.</p>
     */
    @Override
    public boolean agendaLlena() {
        return datos.size() >= capacidad;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Calcula la diferencia entre la capacidad y el tamaño actual.</p>
     */
    @Override
    public int espaciosLibres() {
        return capacidad - datos.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int capacidad() {
        return capacidad;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int total() {
        return datos.size();
    }
}
