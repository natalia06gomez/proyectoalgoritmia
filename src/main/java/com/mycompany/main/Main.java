/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    private Teatro teatro;
    private JTextField nombreField;
    private JTextField cedulaField;
    private JComboBox<TipoSilla> tipoSillaComboBox;
    private JTextField filaField;
    private JTextField columnaField;

    public Main() {
        teatro = new Teatro();
        crearInterfaz();
    }

    private void crearInterfaz() {
        JFrame frame = new JFrame("Gestión de Reservas de Teatro");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreField = new JTextField();
        JLabel cedulaLabel = new JLabel("Cédula:");
        cedulaField = new JTextField();
        JLabel tipoSillaLabel = new JLabel("Tipo de Silla:");
        tipoSillaComboBox = new JComboBox<>(TipoSilla.values());
        JLabel filaLabel = new JLabel("Fila:");
        filaField = new JTextField();
        JLabel columnaLabel = new JLabel("Columna:");
        columnaField = new JTextField();

        JButton registrarButton = new JButton("Registrar Cliente");
        registrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarCliente();
            }
        });

        JButton asignarButton = new JButton("Asignar Silla");
        asignarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                asignarSilla();
            }
        });

        JButton eliminarButton = new JButton("Eliminar Cliente");
        eliminarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarCliente();
            }
        });

        JButton buscarButton = new JButton("Buscar Cliente");
        buscarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarCliente();
            }
        });

        JButton buscarSillaButton = new JButton("Buscar Silla");
        buscarSillaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarSilla();
            }
        });

        JButton cambiarButton = new JButton("Cambiar Silla");
        cambiarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cambiarSilla();
            }
        });

        JButton porcentajeButton = new JButton("Porcentaje de Ocupación");
        porcentajeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calcularPorcentajeOcupacion();
            }
        });

        panel.add(nombreLabel);
        panel.add(nombreField);
        panel.add(cedulaLabel);
        panel.add(cedulaField);
        panel.add(tipoSillaLabel);
        panel.add(tipoSillaComboBox);
        panel.add(filaLabel);
        panel.add(filaField);
        panel.add(columnaLabel);
        panel.add(columnaField);
        panel.add(registrarButton);
        panel.add(asignarButton);
        panel.add(eliminarButton);
        panel.add(buscarButton);
        panel.add(buscarSillaButton);
        panel.add(cambiarButton);
        panel.add(porcentajeButton);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private void registrarCliente() {
        String nombre = nombreField.getText();
        String cedula = cedulaField.getText();
        TipoSilla tipoSilla = (TipoSilla) tipoSillaComboBox.getSelectedItem();

        Cliente cliente = new Cliente(nombre, cedula, tipoSilla);
        teatro.registrarCliente(cliente);

        JOptionPane.showMessageDialog(null, "Cliente registrado correctamente");
        limpiarCampos();
    }

    private void asignarSilla() {
        String cedula = cedulaField.getText();
        int fila = Integer.parseInt(filaField.getText());
        int columna = Integer.parseInt(columnaField.getText());

        Cliente cliente = teatro.buscarClientePorCedula(cedula);
        if (cliente == null) {
            JOptionPane.showMessageDialog(null, "No se encontró un cliente con la cédula proporcionada");
        } else {
            if (teatro.asignarSilla(cliente, fila, columna)) {
                JOptionPane.showMessageDialog(null, "Silla asignada correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo asignar la silla solicitada");
            }
            limpiarCampos();
        }
    }

    private void eliminarCliente() {
        String cedula = cedulaField.getText();

        if (teatro.eliminarCliente(cedula)) {
            JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente");
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un cliente con la cédula proporcionada");
        }
    }

    private void buscarCliente() {
        String cedula = cedulaField.getText();

        Cliente cliente = teatro.buscarClientePorCedula(cedula);
        if (cliente != null) {
            JOptionPane.showMessageDialog(null, "Cliente encontrado:\n" + cliente);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un cliente con la cédula proporcionada");
        }
    }

    private void buscarSilla() {
        int numero = Integer.parseInt(columnaField.getText());

        Silla silla = teatro.buscarSillaPorNumero(numero);
        if (silla != null) {
            JOptionPane.showMessageDialog(null, "Silla encontrada:\n" + silla);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró una silla con el número proporcionado");
        }
    }

    private void cambiarSilla() {
        String cedula = cedulaField.getText();
        int nuevaFila = Integer.parseInt(filaField.getText());
        int nuevaColumna = Integer.parseInt(columnaField.getText());

        Cliente cliente = teatro.buscarClientePorCedula(cedula);
        if (cliente == null) {
            JOptionPane.showMessageDialog(null, "No se encontró un cliente con la cédula proporcionada");
        } else {
            if (teatro.cambiarUbicacionSilla(cliente, nuevaFila, nuevaColumna)) {
                JOptionPane.showMessageDialog(null, "Silla cambiada correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo cambiar la silla solicitada");
            }
            limpiarCampos();
        }
    }

    private void calcularPorcentajeOcupacion() {
        double porcentajeOcupacion = teatro.calcularPorcentajeOcupacion();
        JOptionPane.showMessageDialog(null, "Porcentaje de ocupación del teatro: " + porcentajeOcupacion + "%");
    }

    private void limpiarCampos() {
        nombreField.setText("");
        cedulaField.setText("");
        filaField.setText("");
        columnaField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}




