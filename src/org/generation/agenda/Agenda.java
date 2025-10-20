package org.generation.agenda;

import java.util.Optional;

/**
 * Interfaz que define las operaciones básicas de una agenda de contactos.
 *
 * <p>Las agendas almacenan contactos únicos según su nombre. Todas las operaciones
 * principales (añadir, buscar, eliminar) están documentadas con su complejidad
 * aproximada para facilitar el análisis de rendimiento.
 */
public interface Agenda {
    /**
     * Añade un contacto a la agenda.
     * Si la agenda está llena o ya existe un contacto con el mismo nombre,
     * no se realiza ninguna modificación.
     *
     * @param c el contacto a añadir
     * @return {@code true} si el contacto se añadió, {@code false} si no se pudo añadir
     */
    boolean anadirContacto(Contacto c);

    /**
     * Comprueba si existe en la agenda un contacto con el mismo nombre que el dado.
     * El teléfono del parámetro se ignora para la búsqueda.
     *
     * @param c contacto con el nombre a comprobar
     * @return {@code true} si existe un contacto con ese nombre; {@code false} en caso contrario
     */
    boolean existeContacto(Contacto c);

    /**
     * Muestra por consola todos los contactos de la agenda en orden de inserción.
     * Si la agenda está vacía se indica al usuario.
     */
    void listarContactos();

    /**
     * Busca un contacto por su nombre.
     * Devuelve un {@link Optional} que contiene el contacto encontrado o vacío
     * si no existe. Es preferible usar un {@code Optional} para evitar valores
     * nulos en la lógica del cliente.
     *
     * @param nombre nombre del contacto a buscar
     * @return un {@code Optional} con el contacto si existe, o vacío si no se encuentra
     */
    Optional<Contacto> buscaContacto(String nombre);

    /**
     * Elimina un contacto de la agenda según su nombre.
     * El teléfono del parámetro se ignora para la búsqueda. Si el contacto se
     * elimina, se informa al usuario; de lo contrario se indica que no existe.
     *
     * @param c contacto con el nombre a eliminar
     * @return {@code true} si el contacto se eliminó; {@code false} si no se encontró
     */
    boolean eliminarContacto(Contacto c);

    /**
     * Indica si la agenda ha alcanzado su capacidad máxima.
     * @return {@code true} si la agenda está llena; {@code false} en caso contrario
     */
    boolean agendaLlena();

    /**
     * Devuelve el número de posiciones libres que quedan en la agenda.
     * @return número de contactos que aún se pueden añadir
     */
    int espaciosLibres();

    /**
     * Devuelve la capacidad máxima de la agenda.
     * Útil para mostrar información al usuario.
     * @return capacidad total de la agenda
     */
    int capacidad();

    /**
     * Devuelve el número de contactos almacenados actualmente.
     * @return total de contactos en la agenda
     */
    int total();
}
