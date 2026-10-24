package edu.umg.progra2.aplicacion;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import edu.umg.progra2.modelo.Empleado;
import edu.umg.progra2.modelo.EmpleadoFijo;
import edu.umg.progra2.modelo.EmpleadoPorHora;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final String[] meses = {
            "Enero", "Febrero", "Marzo", "Abril",
            "Mayo", "Junio", "Julio", "Agosto",
            "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };

    private static final ArrayList<Empleado> empleados = new ArrayList<>();

    private static int numeroMes;

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);

        seleccionarMes();

        int opcion;

        do {
            limpiarPantalla();
            mostrarMenuPrincipal();

            opcion = leerEnteroEnRango(
                    "Seleccione una opcion: ", 1, 3);

            switch (opcion) {

            case 1:
                ingresoNomina();
                break;

            case 2:
                imprimirNomina();
                break;

            case 3:
                limpiarPantalla();
                System.out.println("Programa finalizado.");
                break;
            }

        } while (opcion != 3);

        scanner.close();
    }

    private static void seleccionarMes() {

        limpiarPantalla();

        System.out.println("CALCULO DE NOMINA");

        numeroMes = leerEnteroEnRango(
                "Ingrese el mes a trabajar (1-12): ", 1, 12);
    }

    private static void mostrarMenuPrincipal() {

        System.out.println(
                "NOMINA DEL MES: "
                + meses[numeroMes - 1].toUpperCase());

        System.out.println();
        System.out.println("1 - Ingreso de nomina");
        System.out.println("2 - Impresion de nomina");
        System.out.println("3 - Salir");
        System.out.println();
    }

    private static void ingresoNomina() {

        boolean continuar = true;

        while (continuar) {

            limpiarPantalla();

            System.out.println(
                    "NOMINA DEL MES: "
                    + meses[numeroMes - 1].toUpperCase());

            System.out.println("INGRESO DE EMPLEADO A NOMINA");
            System.out.println(
                    "Trabajadores actuales = " + empleados.size());

            System.out.println();
            System.out.println("1 - Empleado fijo");
            System.out.println("2 - Empleado por hora");
            System.out.println();

            int tipo = leerEnteroEnRango(
                    "Seleccione el tipo de empleado: ", 1, 2);

            if (tipo == 1) {
                registrarEmpleadoFijo();
            } else {
                registrarEmpleadoPorHora();
            }

            System.out.println();
            System.out.println("Desea ingresar otro empleado?");
            System.out.println("1 - Si, ingresar otro empleado");
            System.out.println("2 - Volver al menu principal");

            int opcion = leerEnteroEnRango(
                    "Seleccione una opcion: ", 1, 2);

            continuar = opcion == 1;
        }
    }

    private static void registrarEmpleadoFijo() {

        limpiarPantalla();

        System.out.println(
                "NOMINA DEL MES: "
                + meses[numeroMes - 1].toUpperCase());

        System.out.println("INGRESO DE EMPLEADO FIJO");
        System.out.println();

        String dpi = leerDpi();
        String nombre = leerNombre();

        double sueldoMensual = leerNumeroNoNegativo(
                "Sueldo mensual (Q): ");

        double bonificacion = leerNumeroNoNegativo(
                "Bonificacion de este mes (Q): ");

        Empleado empleado = new EmpleadoFijo(
                dpi, nombre, sueldoMensual, bonificacion);

        empleados.add(empleado);

        System.out.printf(
                "Pago de %s = Q %.2f%n",
                empleado.getNombre(),
                empleado.calcularPago());

        System.out.println();
        System.out.println("Empleado agregado correctamente.");
        System.out.println(
                "Trabajadores actuales = " + empleados.size());
    }

    private static void registrarEmpleadoPorHora() {

        limpiarPantalla();

        System.out.println(
                "NOMINA DEL MES: "
                + meses[numeroMes - 1].toUpperCase());

        System.out.println("INGRESO DE EMPLEADO POR HORA");
        System.out.println();

        String dpi = leerDpi();
        String nombre = leerNombre();

        double pagoPorHora = leerNumeroNoNegativo(
                "Pago por hora (Q): ");

        double horasTrabajadas = leerNumeroNoNegativo(
                "Horas trabajadas este mes: ");

        Empleado empleado = new EmpleadoPorHora(
                dpi, nombre, pagoPorHora, horasTrabajadas);

        empleados.add(empleado);

        System.out.printf(
                "Pago de %s = Q %.2f%n",
                empleado.getNombre(),
                empleado.calcularPago());

        System.out.println();
        System.out.println("Empleado agregado correctamente.");
        System.out.println(
                "Trabajadores actuales = " + empleados.size());
    }

    private static void imprimirNomina() {

        limpiarPantalla();

        System.out.println(
                "NOMINA DEL MES: "
                + meses[numeroMes - 1].toUpperCase());

        System.out.println();

        if (empleados.isEmpty()) {

            System.out.println(
                    "No hay empleados registrados en la nomina de este mes");

            System.out.println();
            System.out.println("Total de trabajadores: 0");
            System.out.println("Total de nomina: Q 0.00");

            pausar();
            return;
        }

        System.out.printf(
                "%-16s %-22s %-12s %12s%n",
                "DPI", "Nombre", "Tipo", "Pago (Q)");

        double total = 0;

        for (Empleado empleado : empleados) {

            double pago = empleado.calcularPago();

            total = total + pago;

            System.out.printf(
                    "%-16s %-22s %-12s %12.2f%n",
                    empleado.getDpi(),
                    empleado.getNombre(),
                    empleado.obtenerTipo(),
                    pago);
        }

        System.out.println();
        System.out.println(
                "Total de trabajadores: " + empleados.size());

        System.out.printf(
                "Total de nomina: Q %.2f%n", total);

        pausar();
    }

    private static int leerEnteroEnRango(
            String mensaje, int minimo, int maximo) {

        while (true) {

            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();

            try {

                int numero = Integer.parseInt(entrada);

                if (numero >= minimo && numero <= maximo) {
                    return numero;
                }

                System.out.println(
                        "Error: ingrese un numero entre "
                        + minimo + " y " + maximo + ".");

            } catch (NumberFormatException e) {

                System.out.println(
                        "Error: debe ingresar un numero entero.");
            }
        }
    }

    private static String leerDpi() {

        while (true) {

            System.out.print("DPI: ");
            String dpi = scanner.nextLine().trim();

            if (dpi.matches("\\d{13}")) {
                return dpi;
            }

            System.out.println(
                    "Error: el DPI debe contener exactamente 13 digitos.");
        }
    }

    private static String leerNombre() {

        while (true) {

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine().trim();

            if (!nombre.isEmpty()) {
                return nombre;
            }

            System.out.println(
                    "Error: el nombre no puede estar vacio.");
        }
    }

    private static double leerNumeroNoNegativo(String mensaje) {

        while (true) {

            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();

            try {

                double numero = Double.parseDouble(entrada);

                if (Double.isFinite(numero) && numero >= 0) {
                    return numero;
                }

                System.out.println(
                        "Error: ingrese un valor mayor o igual a 0.");

            } catch (NumberFormatException e) {

                System.out.println(
                        "Error: debe ingresar un valor numerico.");
            }
        }
    }

    private static void pausar() {

        System.out.println();
        System.out.print(
                "Presione Enter para volver al menu principal...");

        scanner.nextLine();
    }

    private static void limpiarPantalla() {

        for (int i = 0; i < 25; i++) {
            System.out.println();
        }
    }
}