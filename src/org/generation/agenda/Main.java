package org.generation.agenda;

import java.util.Optional;
import java.util.Scanner;

/**
 * Clase principal con un menú interactivo en consola para gestionar la agenda.
 *
 * <p>Este menú permite al usuario añadir, buscar, listar y eliminar contactos,
 * así como consultar el estado de la agenda. Los mensajes se imprimen en
 * español para facilitar la interacción.</p>
 */
public class Main {

    /**
     * Enumeración interna que lista las opciones disponibles en el menú.
     * Cada constante está asociada a un número para facilitar la selección.
     */
    private enum OpcionMenu {
        ANADIR(1, "Añadir contacto"),
        EXISTE(2, "Existe contacto (por nombre completo)"),
        LISTAR(3, "Listar contactos"),
        BUSCAR(4, "Buscar contacto (muestra teléfono)"),
        ELIMINAR(5, "Eliminar contacto"),
        LLENA(6, "¿Agenda llena?"),
        LIBRES(7, "Espacios libres"),
        TOTALES(8, "Ver totales (capacidad / ocupados)"),
        SALIR(9, "Salir");

        private final int numero;
        private final String descripcion;

        OpcionMenu(int numero, String descripcion) {
            this.numero = numero;
            this.descripcion = descripcion;
        }

        public int getNumero() {
            return numero;
        }

        public String getDescripcion() {
            return descripcion;
        }

        /**
         * Devuelve la opción correspondiente al número dado o {@code null}
         * si no existe.
         * @param n número introducido por el usuario
         * @return opción del menú o {@code null}
         */
        public static OpcionMenu desdeNumero(int n) {
            for (OpcionMenu op : values()) {
                if (op.numero == n) {
                    return op;
                }
            }
            return null;
        }
    }

    /**
     * Muestra por pantalla todas las opciones del menú.
     */
    private static void mostrarMenu() {
        System.out.println("\n=== MENÚ AGENDA ===");
        for (OpcionMenu op : OpcionMenu.values()) {
            System.out.printf("%d. %s%n", op.getNumero(), op.getDescripcion());
        }
    }

    /**
     * Solicita al usuario un número entero mediante el {@link Scanner}.
     * Si el usuario introduce algo que no sea un número válido se le vuelve a
     * preguntar. Se puede mostrar un mensaje de prompt para guiar al usuario.
     *
     * @param sc     el objeto {@link Scanner} para leer de consola
     * @param prompt mensaje opcional a mostrar antes de leer
     * @return el número entero leído
     */
    private static int leerEntero(Scanner sc, String prompt) {
        while (true) {
            if (prompt != null && !prompt.isEmpty()) {
                System.out.print(prompt);
            }
            String linea = sc.nextLine();
            try {
                return Integer.parseInt(linea.trim());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingresa un número válido.");
            }
        }
    }

    /**
     * Solicita al usuario una cadena de texto no vacía.
     * Se repite la solicitud hasta que se introduce un valor distinto de vacío.
     *
     * @param sc     el objeto {@link Scanner} para leer de consola
     * @param prompt mensaje a mostrar al usuario
     * @return la cadena leída sin espacios extremos
     */
    private static String leerNoVacio(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine();
            if (s != null && !s.trim().isEmpty()) {
                return s.trim();
            }
            System.out.println("El valor no puede estar vacío.");
        }
    }

    /**
     * Punto de entrada de la aplicación.
     * Inicializa la agenda (utilizando {@link AgendaAuditable} para ver mensajes
     * adicionales) y muestra el menú hasta que el usuario decide salir.
     *
     * @param args argumentos de la línea de comandos (no se utilizan)
     */
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Bienvenido a la Agenda.");
            System.out.println("Si deseas usar la capacidad por defecto (10), deja en blanco y pulsa Enter.");
            System.out.print("Capacidad deseada: ");
            String entrada = sc.nextLine().trim();
            int capacidad = 10;
            if (!entrada.isEmpty()) {
                try {
                    capacidad = Integer.parseInt(entrada);
                    if (capacidad <= 0) {
                        System.out.println("Capacidad no válida. Se usará la capacidad por defecto de 10.");
                        capacidad = 10;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("No se ha reconocido como número. Se usará la capacidad por defecto de 10.");
                    capacidad = 10;
                }
            }

            // Aquí se elige la implementación a usar. Se puede cambiar a AgendaContactos para
            // desactivar la auditoría.
            Agenda agenda = new AgendaAuditable(capacidad);
            System.out.println("Agenda inicializada con capacidad: " + agenda.capacidad());

            boolean continuar = true;
            while (continuar) {
                mostrarMenu();
                int opNum = leerEntero(sc, "Elige una opción: ");
                OpcionMenu opcion = OpcionMenu.desdeNumero(opNum);
                if (opcion == null) {
                    System.out.println("Opción no válida. Por favor, selecciona una de las opciones del menú.");
                    continue;
                }
                switch (opcion) {
                    case ANADIR: {
                        String nombre = leerNoVacio(sc, "Nombre: ");
                        String telefono = leerNoVacio(sc, "Teléfono: ");
                        Contacto c = new Contacto(nombre, telefono);
                        agenda.anadirContacto(c);
                        break;
                    }
                    case EXISTE: {
                        String nombre = leerNoVacio(sc, "Nombre a verificar: ");
                        Contacto c = new Contacto(nombre, "N/A");
                        System.out.println(agenda.existeContacto(c)
                                ? "Sí existe un contacto con ese nombre."
                                : "No existe un contacto con ese nombre.");
                        break;
                    }
                    case LISTAR: {
                        agenda.listarContactos();
                        break;
                    }
                    case BUSCAR: {
                        String nombre = leerNoVacio(sc, "Nombre a buscar: ");
                        Optional<Contacto> hallado = agenda.buscaContacto(nombre);
                        if (hallado.isPresent()) {
                            Contacto c = hallado.get();
                            System.out.println("Teléfono de " + c.getNombre() + ": " + c.getTelefono());
                        } else {
                            System.out.println("No se encontró un contacto con ese nombre.");
                        }
                        break;
                    }
                    case ELIMINAR: {
                        String nombre = leerNoVacio(sc, "Nombre a eliminar: ");
                        Contacto c = new Contacto(nombre, "N/A");
                        agenda.eliminarContacto(c);
                        break;
                    }
                    case LLENA: {
                        System.out.println(agenda.agendaLlena() ? "La agenda está llena." : "Aún hay espacio disponible.");
                        break;
                    }
                    case LIBRES: {
                        System.out.println("Espacios libres: " + agenda.espaciosLibres());
                        break;
                    }
                    case TOTALES: {
                        System.out.println("Capacidad: " + agenda.capacidad() + " | Ocupados: " + agenda.total());
                        break;
                    }
                    case SALIR: {
                        continuar = false;
                        System.out.println("¡Hasta luego!");
                        break;
                    }
                }
            }
        }
    }
}