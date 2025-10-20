package org.generation.agenda;

/**
 * Subclase de {@link AgendaContactos} que añade mensajes de auditoría
 * al añadir o eliminar contactos.
 *
 * <p>Demuestra herencia y polimorfismo: la lógica de gestión de la agenda
 * permanece en la clase base, y la subclase aporta comportamiento extra sin
 * duplicar código.
 */
public class AgendaAuditable extends AgendaContactos {

    /**
     * Crea una agenda auditable con la capacidad por defecto.
     */
    public AgendaAuditable() {
        super();
    }

    /**
     * Crea una agenda auditable con la capacidad indicada.
     * @param capacidad capacidad máxima de la agenda
     */
    public AgendaAuditable(int capacidad) {
        super(capacidad);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Antes y después de delegar en la clase base, se imprimen mensajes
     * indicando la operación realizada y su resultado.</p>
     */
    @Override
    public boolean anadirContacto(Contacto c) {
        System.out.println("Agregando contacto: " + c.getNombre());
        boolean ok = super.anadirContacto(c);
        System.out.println("Resultado: " + (ok ? "Añadido" : "No añadido"));
        return ok;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Se imprime un mensaje antes y después de intentar eliminar el contacto.</p>
     */
    @Override
    public boolean eliminarContacto(Contacto c) {
        System.out.println("Eliminando contacto: " + c.getNombre());
        boolean ok = super.eliminarContacto(c);
        System.out.println("Resultado: " + (ok ? "Eliminado" : "No eliminado"));
        return ok;
    }
}
