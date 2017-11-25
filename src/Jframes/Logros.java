/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//   -------------- falta validar el porcentaje de la nota de las materias  con el metodo *validar_porc* ------------
package Jframes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ander
 */
public class Logros extends javax.swing.JInternalFrame {

    /**
     * Creates new form Logros
     */
    Cone cone;
    Statement st;
    ResultSet rs;
    DefaultTableModel modelo;
    int Cod_Mat, cod_per, cod_Cur;

    public Logros() {
        initComponents();
        cone = new Cone();
        jButton3.setEnabled(false);
        Cargar_Periodo();
        Cargar_Cur();
        porcentaje_Comp();

    }

    public void cargar_Tabla(String valor) {

        String[] titulos = {"CÓDIGO", "DESCRIPCIÓN", "TIPO", "PORCENTAJE",};
        modelo = new DefaultTableModel(null, titulos);

        try {
            rs = cone.query("select id, descripcion, tipo_logro, porcentaje from logro where materia_id =" + Cod_Mat);
            String[] registro = new String[4];
            while (rs.next()) {
                registro[0] = rs.getString("id");
                registro[1] = rs.getString("descripcion");
                registro[2] = rs.getString("tipo_logro");
                registro[3] = rs.getString("porcentaje");
                modelo.addRow(registro);
            }
            jTable1.setModel(modelo);

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    public void Tabla_t(String valor) {

        String[] titulos = {"CÓDIGO", "DESCRIPCIÓN", "TIPO", "PORCENTAJE",};
        modelo = new DefaultTableModel(null, titulos);

        try {
            rs = cone.query("select id, descripcion, tipo_logro, porcentaje from logro");
            String[] registro = new String[4];
            while (rs.next()) {
                registro[0] = rs.getString("id");
                registro[1] = rs.getString("descripcion");
                registro[2] = rs.getString("tipo_logro");
                registro[3] = rs.getString("porcentaje");
                modelo.addRow(registro);
            }
            jTable1.setModel(modelo);

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public void Tabla_A(String valor) {

        String[] titulos = {"CÓDIGO", "DESCRIPCIÓN", "TIPO", "PORCENTAJE",};
        modelo = new DefaultTableModel(null, titulos);

        try {
            rs = cone.query("select id, descripcion, tipo_logro, porcentaje from logro where tipo_logro = '" + jComboBox3.getSelectedItem() + "'");
            String[] registro = new String[4];
            while (rs.next()) {
                registro[0] = rs.getString("id");
                registro[1] = rs.getString("descripcion");
                registro[2] = rs.getString("tipo_logro");
                registro[3] = rs.getString("porcentaje");
                modelo.addRow(registro);
            }
            jTable1.setModel(modelo);

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public void Tabla_B(String valor) {

        String[] titulos = {"CÓDIGO", "DESCRIPCIÓN", "TIPO", "PORCENTAJE",};
        modelo = new DefaultTableModel(null, titulos);

        try {
            rs = cone.query("select id, descripcion, tipo_logro, porcentaje from logro where tipo_logro = '" + jComboBox3.getSelectedItem() + "'");
            String[] registro = new String[4];
            while (rs.next()) {
                registro[0] = rs.getString("id");
                registro[1] = rs.getString("descripcion");
                registro[2] = rs.getString("tipo_logro");
                registro[3] = rs.getString("porcentaje");
                modelo.addRow(registro);
            }
            jTable1.setModel(modelo);

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void Cargar_Cur() {
        try {
            rs = cone.query("select *from curso");

            while (rs.next()) {
                String id = (String) rs.getString("id");
                String nom = (String) rs.getString("nombre");
                jComboBox4.addItem(id + " - " + nom);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Logros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public void Cargar_Mat() {
            jComboBox1.removeAllItems();
            jComboBox1.addItem("Seleccione la materia");
        String id[] = ( (String)jComboBox4.getSelectedItem() ).split(" - ");
        try {
            
            rs = cone.query("select m.nombre, m.id "
            + "from docentexcurso dc, materia m where dc.materia_id = m.id and dc.curso_id =" + id[0]);
            
            while (rs.next()){
                jComboBox1.addItem(rs.getString("m.nombre"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Logros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Cargar_Periodo() {
        try {
            rs = cone.query("select nombre from periodo");
            while (rs.next()) {
                jComboBox2.addItem(rs.getString("nombre"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Logros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void validar_porc(){
        try {
            int resultado = 0;
            rs= cone.query("select sum(porcentaje) from logro l inner join materia m on l.materia_id = m.id" 
                + " where m.nombre = '" + jComboBox1.getSelectedIndex() + "'");
            if(rs.next()) {
               resultado = rs.getInt("sum(porcentaje)");
            }
            
            if(resultado >= 100){
              JOptionPane.showMessageDialog(rootPane, "se ha completado el 100 % de la nota para la materia " 
                + jComboBox3.getSelectedItem() + ". \nNo se puede agregar mas logros a esta materia.");
            Tabla_t("");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Logros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void porcentaje_Comp(){
        int academ = 0, inst = 0;
        try {
            rs = cone.query("SELECT sum(porcentaje) FROM logro where tipo_logro = 'Academico'");
            if (rs.next()) {
                academ = rs.getInt("sum(porcentaje)");
            }
            
            rs = cone.query("SELECT sum(porcentaje) FROM logro where tipo_logro = 'Institucional'");
            if (rs.next()) {
                inst = rs.getInt("sum(porcentaje)");
            }
            
            int resul = academ + inst;
            String total = String.valueOf(resul);
            
            jLabel12.setText(total +" %");
//            int maximo = rs.getInt("sum(porcentaje)");

        } catch (SQLException ex) {
            Logger.getLogger(Logros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cargar_porcen_A(){
        try {
            rs = cone.query("SELECT sum(porcentaje) FROM logro where tipo_logro = '" + jComboBox3.getSelectedItem() + "'");
            if (rs.next()) {
                jLabel9.setText( rs.getString("sum(porcentaje)")+ " %" );
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public void cargar_porcen_B(){
        try {
            rs = cone.query("SELECT sum(porcentaje) FROM logro where tipo_logro = '" + jComboBox3.getSelectedItem() + "'");
            if (rs.next()) {
                jLabel9.setText( rs.getString("sum(porcentaje)")+ " %" );
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void Count_Logros_A() {
        try {
            rs = cone.query("select count(tipo_logro) from logro where tipo_logro = '" + jComboBox3.getSelectedItem() + "'");

            if (rs.next()) {
                String con = rs.getString("count(tipo_logro)");
                jLabel7.setText(con);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Logros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void Count_Logros_B() {
        try {
            rs = cone.query("select count(tipo_logro) from logro where tipo_logro = '" + jComboBox3.getSelectedItem() + "'");

            if (rs.next()) {
                String con = rs.getString("count(tipo_logro)");
                jLabel7.setText(con);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Logros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jPanel1 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        jMenuItem1.setText("Modificar");
        jMenuItem1.setToolTipText("");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("Seccion de Logros");
        setAutoscrolls(true);
        setName("Seccion de "); // NOI18N
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jDesktopPane1.setBackground(new java.awt.Color(102, 102, 102));
        jDesktopPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione la materia" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jComboBox1MousePressed(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 170, 30));

        jLabel1.setText("Materia");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 120, 30));

        jLabel2.setText("Periodo");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 110, 30));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione el periodo" }));
        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });
        jPanel1.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, 170, 30));

        jLabel3.setText("Tipo de logro");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 110, 30));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione el logro", "Academico", "Institucional" }));
        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });
        jPanel1.add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 170, 30));

        jLabel4.setText("Porcentaje");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 110, 30));

        jLabel5.setText("Descripcion");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 120, 30));

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 250, 460, 30));

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));
        jPanel1.add(jSpinner1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 200, 70, 30));

        jLabel6.setText("Cantidad de logros");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 300, 110, 30));
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 300, 30, 30));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable1.setComponentPopupMenu(jPopupMenu1);
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 560, 130));

        jLabel8.setText("Descripcion de logros");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 180, 30));

        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 30, 110, 40));
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 300, 40, 30));

        jLabel10.setText("Curso");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 6, 90, 30));

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione un curso" }));
        jComboBox4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox4ItemStateChanged(evt);
            }
        });
        jPanel1.add(jComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 170, 30));

        jLabel11.setText("Total:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 200, 40, 30));
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 200, 80, 30));

        jLabel13.setText("Total:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 300, 40, 30));

        jButton3.setText("Actualizar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 80, 110, 40));

        jDesktopPane1.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 490));

        getContentPane().add(jDesktopPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 490));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed

    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        String tip_Logro = "", descrip = "";
        int id_Logro = 0;

        try {
            rs = cone.query("SELECT max(id) FROM logro");
            if (rs.next()) {
                id_Logro = rs.getInt("max(id)") + 1;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        descrip = jTextField1.getText();
        tip_Logro = (String) jComboBox3.getSelectedItem();

        System.out.println(id_Logro + "\n" + descrip + "\n" + tip_Logro + "\n" + jSpinner1.getValue() + " %" + "\n" + Cod_Mat + "\n" + cod_per);

//        cone.update("INSERT INTO logro VALUES(" +  id_Logro + descrip + tip_Logro + porcentaje + Cod_Mat + periodo + ")");

         porcentaje_Comp();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged

        try {
            String mat = (String) jComboBox1.getSelectedItem();
            rs = cone.query("select id from materia where nombre ='" + mat + "'");
            if (rs.next()) {
                Cod_Mat = rs.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Logros.class.getName()).log(Level.SEVERE, null, ex);
        }

        cargar_Tabla("");
        validar_porc();

    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        
        String des, tipoL,  cod;
        cod = (String)jTable1.getValueAt(jTable1.getSelectedRow(), 0);
        des = (String)jTable1.getValueAt(jTable1.getSelectedRow(), 1);
        tipoL = (String)jTable1.getValueAt(jTable1.getSelectedRow(), 2);
        String porc[] = ( (String)jTable1.getValueAt(jTable1.getSelectedRow(), 3) ).split(" ");
        int por = Integer.valueOf(porc[0]);
        
//        JOptionPane.showMessageDialog(rootPane, "valores \n" + cod + "\n"+ des + "\n" + tipoL + "\n" + porc[0]);
        
        jComboBox3.setSelectedItem(tipoL);
        jSpinner1.setValue(por);
        jTextField1.setText(des);
              
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged

        try {
            String nom_per = (String) jComboBox2.getSelectedItem();
            rs = cone.query("select id from periodo where nombre = '" + nom_per + "'");

            if (rs.next()) {
                cod_per = rs.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Logros.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jComboBox2ItemStateChanged

    private void jComboBox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox4ItemStateChanged
        Cargar_Mat();
    }//GEN-LAST:event_jComboBox4ItemStateChanged

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
        
        String nomL = (String)jComboBox3.getSelectedItem();
        
        if(nomL == "academico"){
            Tabla_A("");
            cargar_porcen_A();
            Count_Logros_A();
            
        } else{
            Tabla_B("");
            cargar_porcen_B();
            Count_Logros_B();
        }
    }//GEN-LAST:event_jComboBox3ItemStateChanged

    private void jComboBox1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MousePressed
        
    }//GEN-LAST:event_jComboBox1MousePressed

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked
        
       
    }//GEN-LAST:event_jComboBox1MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jButton3.setEnabled(false);
        
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}