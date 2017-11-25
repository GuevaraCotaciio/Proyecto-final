package Jframes;

import java.awt.event.*;
import java.sql.*;
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

            conex = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyectosaie", "Anderson", "192837465");
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
            JOptionPane.showMessageDialog(null, ex, "Error Actualizacion", JOptionPane
                    .WARNING_MESSAGE);
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
