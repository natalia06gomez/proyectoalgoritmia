/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.main;
import java.util.ArrayList;
import java.util.List;

public class Teatro {
    private static final int FILAS_PREFERENCIALES = 2;
    private static final int COLUMNAS_PREFERENCIALES = 4;
    private static final int FILAS_GENERALES = 7;
    private static final int COLUMNAS_GENERALES = 6;

    private Asiento[][] asientosPreferenciales;
    private Asiento[][] asientosGenerales;
    private List<Cliente> listaClientes;

    public Teatro() {
        Asiento[][] asientosPreferenciales = new Asiento[FILAS_PREFERENCIALES][COLUMNAS_PREFERENCIALES];
        Asiento[][] asientosGenerales = new Asiento[FILAS_GENERALES][COLUMNAS_GENERALES];
        listaClientes = new ArrayList<>();

        inicializarSillas();
    }

    private void inicializarSillas() {
        // Iniciar asientos preferenciales
        for (int fila = 0; fila < FILAS_PREFERENCIALES; fila++) {
            for (int columna = 0; columna < COLUMNAS_PREFERENCIALES; columna++) {
                Asiento[][] asientosPreferenciales =null;
                asientosPreferenciales[fila][columna] = new Asiento(TipoAsiento.PREFERENCIAL, fila, columna);
            }
        }

        // Iniciar asientos generales
        for (int fila = 0; fila < FILAS_GENERALES; fila++) {
            for (int columna = 0; columna < COLUMNAS_GENERALES; columna++) {
                Asiento[][] asientosGenerales = null;
                asientosGenerales[fila][columna] = new Asiento(TipoAsiento.GENERAL, fila, columna);
            }
        }
    }

    public boolean registrarCliente(Cliente cliente) {
        if (buscarClientePorCedula(cliente.getCedula()) != null) {
            return false; // El cliente ya está registrado
        }

        listaClientes.add(cliente);
        return true;
    }

    public Cliente buscarClientePorCedula(String cedula) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getCedula().equals(cedula)) {
                return cliente;
            }
        }
        return null;
        // No se encontró el cliente con los datos proporcionados
    }

    public boolean asignarAsiento(Cliente cliente, int fila, int columna) {
        Asiento[][] asientos;
        if (cliente.getTipoAsiento() == TipoAsiento.PREFERENCIAL) {
            Asiento[][] asientoPreferenciales = null;
            asientos = asientoPreferenciales;
        } else {
            asientos = asientosGenerales;
        }

        if (esAsientoValido(asientos, fila, columna) && !asientos[fila][columna].isOcupada()) {
            cliente.setFila(fila);
            cliente.setColumna(columna);
            asientos[fila][columna].setOcupada(true);
            return true;
        }

        return false;
    }

    private boolean esAsientoValido(Asiento[][] asiento, int fila, int columna) {
        return fila >= 0 && fila < asiento.length && columna >= 0 && columna < asiento[0].length;
    }

    public boolean eliminarCliente(String cedula) {
        Cliente cliente = buscarClientePorCedula(cedula);
        if (cliente != null) {
            Asiento[][] asientos;
            if (cliente.getTipoAsiento() == TipoAsiento.PREFERENCIAL) {
                Asiento[][] asientosPreferenciales = null;
                asientos = asientosPreferenciales;
            } else {
                Asiento[][] asientosGenerales = null;
                asientos = asientosGenerales;
            }

            int fila = cliente.getFila();
            int columna = cliente.getColumna();
            if (esAsientoValido(asientos, fila, columna)) {
                asientos[fila][columna].setOcupada(false);
            }

            listaClientes.remove(cliente);
            return true;
        }

        return false;
        // No se encontró el cliente con la cédula proporcionada

    }

    public Asiento buscarAsientoPorNumero(int numero) {
    // Buscar en sillas preferenciales
    for (int fila = 0; fila < FILAS_PREFERENCIALES; fila++) {
        for (int columna = 0; columna < COLUMNAS_PREFERENCIALES; columna++) {
            if (asientosPreferenciales[fila][columna].numero == numero) {
                return asientosPreferenciales[fila][columna];
            }
        }
    }

    // Buscar en asirntos generales
    for (int fila = 0; fila < FILAS_GENERALES; fila++) {
        for (int columna = 0; columna < COLUMNAS_GENERALES; columna++) {
            if (asientosGenerales[fila][columna].numero == numero) {
                return asientosGenerales[fila][columna];
            }
        }
    }

    return null;
    // No se encontró un asiento con el número dado
}

    public boolean cambiarUbicacionSilla(Cliente cliente, int nuevaFila, int nuevaColumna) {
        Asiento[][] asientos = null;
        if (cliente.getTipoAsiento() == TipoAsiento.PREFERENCIAL) {
            Asiento[][] asientosPreferenciales = null;
            Asiento[][] Asientos = asientosPreferenciales;
        } else {
            Asiento[][] asientosGenerales = null;
            asientos = asientosGenerales;
        }

        int filaActual = cliente.getFila();
        int columnaActual = cliente.getColumna();

        if (esAsientoValido(asientos, nuevaFila, nuevaColumna) && !asientos[nuevaFila][nuevaColumna].isOcupada()) {
            asientos[filaActual][columnaActual].setOcupada(false);
            cliente.setFila(nuevaFila);
            cliente.setColumna(nuevaColumna);
            asientos[nuevaFila][nuevaColumna].setOcupada(true);
            return true;
        }

        return false;
    }

    public double calcularPorcentajeOcupacion() {
        int asientosPreferencialesOcupados = contarAsientosOcupados(asientosPreferenciales);
        int asientosGeneralesOcupados = contarAsientosOcupados(asientosGenerales);
        int asientosTotales = FILAS_PREFERENCIALES * COLUMNAS_PREFERENCIALES + FILAS_GENERALES * COLUMNAS_GENERALES;

        return (double) (asientosPreferencialesOcupados + asientosGeneralesOcupados) / asientosTotales * 100;
    }

    private int contarAsientosOcupados(Asiento[][] asientos) {
        int contador = 0;
        for (int fila = 0; fila < asientos.length; fila++) {
            for (int columna = 0; columna < asientos[0].length; columna++) {
                if (asientos[fila][columna].isOcupada()) {
                    contador++;
                }
            }
        }
        return contador;
    }

    
}







