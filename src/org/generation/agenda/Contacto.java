package org.generation.agenda;

import java.util.Objects;

/**
 * Clase que representa un contacto con un nombre y un número de teléfono.
 *
 * <p>Los objetos de esta clase son inmutables: una vez creados, su nombre y teléfono
 * no pueden modificarse. La igualdad y el valor de hash de un contacto se basan
 * únicamente en su nombre normalizado (sin espacios y en minúsculas), lo que
 * permite comparar contactos por nombre de forma insensible a mayúsculas/minúsculas.
 */
public final class Contacto {
    /** Nombre original introducido por el usuario. */
    private final String nombre;
    /** Teléfono original introducido por el usuario. */
    private final String telefono;
    /** Nombre normalizado usado para comparar y almacenar contactos de manera eficiente. */
    private final String claveNombre;

    /**
     * Crea un nuevo contacto con el nombre y teléfono dados.
     * Se eliminan los espacios en blanco iniciales/finales y se valida que ninguno esté vacío.
     *
     * @param nombre   nombre del contacto (no puede ser nulo ni vacío)
     * @param telefono número de teléfono del contacto (no puede ser nulo ni vacío)
     * @throws IllegalArgumentException si el nombre o el teléfono son nulos o están vacíos
     */
    public Contacto(String nombre, String telefono) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío.");
        }
        this.nombre = nombre.trim();
        this.telefono = telefono.trim();
        // Guardamos el nombre normalizado una única vez para evitar recomputarlo.
        this.claveNombre = normalizarNombre(this.nombre);
    }

    /**
     * Devuelve el nombre tal como lo introdujo el usuario.
     * @return nombre original del contacto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve el teléfono del contacto.
     * @return número de teléfono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Normaliza un nombre eliminando espacios y convirtiendo a minúsculas.
     * Este método es estático para que se pueda reutilizar en otras clases (por ejemplo, al buscar)
     * sin necesidad de crear instancias de Contacto.
     *
     * @param n nombre a normalizar (puede ser null)
     * @return el nombre normalizado en minúsculas sin espacios en los extremos; cadena vacía si {@code n} es null
     */
    public static String normalizarNombre(String n) {
        return n == null ? "" : n.trim().toLowerCase();
    }

    /**
     * Devuelve el nombre normalizado (clave) asociado a este contacto.
     * Útil para estructuras de datos que requieran un identificador uniforme (por ejemplo, mapas o índices).
     *
     * @return nombre normalizado (minúsculas y sin espacios)
     */
    public String getClaveNombre() {
        return claveNombre;
    }

    /**
     * Dos contactos son iguales si sus nombres normalizados coinciden.
     * Esta implementación ignora el teléfono al comparar, tal como exige el enunciado.
     *
     * @param o objeto a comparar
     * @return {@code true} si representan el mismo nombre; {@code false} en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contacto)) return false;
        Contacto other = (Contacto) o;
        return this.claveNombre.equals(other.claveNombre);
    }

    /**
     * Calcula el código hash basándose en el nombre normalizado.
     * Dos contactos iguales según {@link #equals(Object)} tienen el mismo hash.
     *
     * @return valor hash del contacto
     */
    @Override
    public int hashCode() {
        return claveNombre.hashCode();
    }

    /**
     * Representación textual del contacto.
     * Se usa para mostrar la agenda al usuario en el menú.
     * @return cadena con formato "nombre -> teléfono"
     */
    @Override
    public String toString() {
        return nombre + " -> " + telefono;
    }
}
