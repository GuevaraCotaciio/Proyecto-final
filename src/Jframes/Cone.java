package Jframes;

import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.swing.*;
import javax.swing.table.*;

public class Cone {

    Statement st;
    Connection conex;
    DefaultTableModel modelo;
    public static final String ICONO_LOGIN = "/imagenes/login.png";
    public static final String ICONO_MENU = "/imagenes/logo.png";

    public Cone() {
        try {

            conex = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto_saie", "root", "");
            st = conex.createStatement();
            System.out.println("Conexion establecida!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "Error Conexion", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void update(String sql) {

        try {
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error Actualizacion", JOptionPane.WARNING_MESSAGE);
        }
    }

    public ResultSet query(String sql) {
        ResultSet res = null;

        try {
            res = st.executeQuery(sql);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error de Consulta", JOptionPane.WARNING_MESSAGE);
        }

        return res;
    }

    public Integer password() {
        int contra = 0;
        String value = "";

        do {
            try {

                contra = (int) (Math.random() * (1000 - 1000000) + 1000000);

                ResultSet rs = st.executeQuery("call validate('" + contra + "')");

                if (rs.next()) {
                    value = rs.getString("value");
                }
                
                if (value.equals("true")) {
                    return contra;
                }
                
            } catch (SQLException e) {
                System.out.println(e);
            }

        } while (value.equals("true"));

        return null;
    }

    public DefaultTableModel tabla(String sql, String cod, String nom, JTable tabla) {

        try {

            String titulo[] = {"Código", "Nombre"}, registro[] = new String[2];
            modelo = new DefaultTableModel(null, titulo) {
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };

            ResultSet res = st.executeQuery(sql);

            while (res.next()) {
                registro[0] = res.getString(cod);
                registro[1] = res.getString(nom);

                modelo.addRow(registro);

            }

            tabla.setModel(modelo);

            return modelo;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;

    }

    public DefaultTableModel tabla3(String sql, String cod, String nom, String otro, JTable tabla) {

        try {

            String titulo[] = {"Código", "Nombre", otro}, registro[] = new String[3];
            modelo = new DefaultTableModel(null, titulo) {
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };

            ResultSet res = st.executeQuery(sql);

            while (res.next()) {
                registro[0] = res.getString(cod);
                registro[1] = res.getString(nom);
                registro[2] = res.getString(otro);

                modelo.addRow(registro);

            }

            tabla.setModel(modelo);

            return modelo;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;

    }

    public DefaultTableModel tabla4(String sql, String codPri, String nomPri, String codSec, String nomSec, JTable tabla) {

        try {

            String titulo[] = {codPri, nomPri, codSec, nomSec}, registro[] = new String[4];

            modelo = new DefaultTableModel(null, titulo) {
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };

            ResultSet res = st.executeQuery(sql);

            while (res.next()) {
                registro[0] = res.getString(codPri);
                registro[1] = res.getString(nomPri);
                registro[2] = res.getString(codSec);
                registro[3] = res.getString(nomSec);

                modelo.addRow(registro);

            }

            tabla.setModel(modelo);
            return modelo;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;

    }

    public ArrayList cursos(String sql, String cod, String nom, JComboBox box) {

        try {
            ArrayList<String> index = new ArrayList<>();
            String nombre;

            ResultSet res = st.executeQuery(sql);
            int i = 0;
            while (res.next()) {

                index.add(res.getString(cod));
                nombre = res.getString(nom);

                box.addItem(index.get(i) + "    -    " + nombre);
                i++;
            }

            return index;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error de Consulta", JOptionPane.WARNING_MESSAGE);
        }
        return null;
    }

    public boolean espacio(String texto) {

        String sinBlancos = "";

        for (int i = 0; i < texto.length(); i++) {
            if (texto.charAt(i) != ' ') {
                sinBlancos += texto.charAt(i);
            }
        }

        return sinBlancos.equals("");
    }

    public String space(String texto) {

        String Blancos[] = new String[texto.length()];
        String code = "";

        for (int i = 0; i < texto.length(); i++) {

            if (texto.charAt(i) == ':') {
                Blancos[i] = ".";
            } else if (texto.charAt(i) != ' ') {

                Blancos[i] = String.valueOf(texto.charAt(i));

            }
            if (Blancos[i] != null) {
                code += Blancos[i];
            }
        }

        return code;
    }

    public void letras(KeyEvent evt) {

        Character s;
        s = evt.getKeyChar();

        if (!Character.isLetter(s) && s != KeyEvent.VK_SPACE) {
            evt.consume();

        }

    }

    public String numero(String texto) {

        String numeros = "";

        for (int i = 0; i < texto.length(); i++) {

            if (Character.isDigit(texto.charAt(i))) {
                numeros += String.valueOf(texto.charAt(i));
            }
        }

        return numeros;

    }

    public String numeroP(String texto) {

        String numeros = "";

        for (int i = 0; i < texto.length(); i++) {

            if (Character.isDigit(texto.charAt(i)) || texto.charAt(i) == '.') {
                numeros += String.valueOf(texto.charAt(i));
            }
        }

        return numeros;

    }

    public String punto(String texto) {

        String puntos = "";

        for (int i = 0; i < texto.length(); i++) {

            if (texto.charAt(i) == '.') {
                puntos += texto.charAt(i);

            }
//            if (Character.isLetter(texto.charAt(i)) || Character.isSpaceChar(texto.charAt(i))) {
//                letras += texto.charAt(i);
//            }
        }

        if (!puntos.equals("")) {
            return puntos;
        }

        return "";

    }

    public void inicio(String sql, String col, JLabel campo, JTextField borrar) {

        try {
            String resultado = "";
            ResultSet res = st.executeQuery(sql);

            if (res.next()) {
                resultado = res.getString(col);
            }

            if (resultado == null) {

                resultado = "0";
            }

            int suma = Integer.valueOf(resultado) + 1;

            String result = String.valueOf(suma);

            campo.setText(result);
            borrar.setText("");
            borrar.requestFocus();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error de Incremento", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void hora(JLabel jLabel3, JLabel jLabel4, JLabel jLabel5) {

        Timer time = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                GregorianCalendar fecha = new GregorianCalendar();
                int day = fecha.getTime().getDate();
                int month = fecha.getTime().getMonth() + 1;
                int year = fecha.getTime().getYear() + 1900;

                int hour = fecha.getTime().getHours();
                int min = fecha.getTime().getMinutes();
                int seg = fecha.getTime().getSeconds();

                String[] wek = {"Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};

                if (seg < 10 && min < 10) {
                    jLabel5.setText(hour + " : 0" + min + " : 0" + seg);

                } else if (seg < 10) {
                    jLabel5.setText(hour + " : " + min + " : 0" + seg);

                } else if (min < 10) {
                    jLabel5.setText(hour + " : 0" + min + " : " + seg);

                } else {
                    jLabel5.setText(hour + " : " + min + " : " + seg);
                }

                jLabel3.setText(wek[fecha.getTime().getDay()]);
                jLabel4.setText(day + " - " + month + " - " + year);

            }

        });

        time.start();

    }

}
