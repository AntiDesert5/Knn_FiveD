package kmeans.form;

import kmeans.KMeans;
import kmeans.coordenadas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class GUIKnn {
    private JButton correrButton;
    private JButton limpiarButton;
    private JTable table1;
    private JSpinner spinnerk;
    private JSpinner spinnerclusters;
    private JPanel panelmain;
    private JPanel jpanel1;
    private JButton seleccionarAtractoresButton;
    private JComboBox comboBox1;
    private JButton generarPuntosButton;
    DefaultTableModel modelo;
    KMeans kmeans = new KMeans();
    int cont= -1;

    public GUIKnn() {
        generarPuntosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int datos= (int)spinnerk.getValue();
                String typedistance = comboBox1.getSelectedItem().toString();
                kmeans.setTypeDistan(typedistance);
                //System.out.println("tipo de distancia: "+typedistance);
                kmeans.setNumPuntos(datos); //mando el valor de todos los puntos
                kmeans.generaObjetos(); //generamos los puntos

                comboBox1.setEnabled(false);
                generarPuntosButton.setEnabled(false);
                correrButton.setEnabled(false);
                seleccionarAtractoresButton.setEnabled(true);
            }
        });
        seleccionarAtractoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int atractores= (int)spinnerclusters.getValue();
                kmeans.setNumclusters(atractores);//esta al reves cambiar en codigo
                cont = 0;
                seleccionarAtractoresButton.setEnabled(false);
                correrButton.setEnabled(true);
            }
        });
        generarPuntosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kmeans.asignarClases();

            }
        });
        correrButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                kmeans.asignarClases();
                dibujarObjetosColor();
                kmeans.calificacionCentroides();
                crearCentroides();
                for(int i=0;i<100;i++){
                    kmeans.asignarClases();
                    kmeans.calificacionCentroides();
                    crearCentroides();

                }
            }
        });
        jpanel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //super.mouseClicked(e);
                if (cont!=-1 && cont< kmeans.getNumclusters()){
                    cont++;
                    coordenadas a= new coordenadas(e.getX(),e.getY());
                    a.setK(cont);
                    System.out.println("Atractor no: "+cont+a);

                    JColorChooser seleccionarcolor = new JColorChooser();
                    Color c = seleccionarcolor.showDialog(null,  "Color de Clase "+ cont, Color.RED);
                    crearPunto(a.getX(),a.getY(),c);
                    kmeans.getCentroides().add(a);
                    kmeans.getColores().add(c);

                    kmeans.CoordenadaAtractores(e.getX(),e.getY());

                    Graphics g= jpanel1.getGraphics();
                    g.setColor(Color.blue);
                    g.fillOval(a.getX(),a.getY(),1,1);
                }
                kmeans.setNumA(cont);
            }
        });

        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboBox1.setEnabled(true);
                generarPuntosButton.setEnabled(true);
                correrButton.setEnabled(false);
                seleccionarAtractoresButton.setEnabled(false);
                jpanel1.repaint();
                kmeans.limpiar();
                for(int i=1;i<=(int)spinnerk.getValue();i++) {
                    modelo.removeRow(i);
                }
            }
        });
    }

    private void dibujarObjetosColor(){

        Graphics g = jpanel1.getGraphics();
        for(int i = 0; i<kmeans.getNumclusters(); i++){
            Color c = kmeans.getColores().get(i);
            g.setColor(c);
            for(coordenadas p: kmeans.getObjetos() ){
                if( p.getK() == (i+1))
                    g.fillOval(p.getX(), p.getY(), 5, 5);
            }
        }
    }
    private void crearCentroides(){

        Graphics g = jpanel1.getGraphics();
        modelo = new DefaultTableModel();
        modelo.addColumn("X1");
        modelo.addColumn("X2");

        for(int i = 0; i<kmeans.getNumclusters(); i++){
            Color c = kmeans.getColores().get(i);
            g.setColor(c);
            int x = kmeans.getCentroides().get(i).getX();
            int y = kmeans.getCentroides().get(i).getY();
            System.out.println("Coordenadas Centroides : "+x+" , "+y);
            //*****

            modelo.addRow(new Object[]{x,y});
            g.fillOval(x, y ,9 ,9);
        }
        this.table1.setModel(modelo);
    }
    private void crearPunto(int x, int y, Color c){
        Graphics g= jpanel1.getGraphics();
        g.setColor(c);
        g.fillOval(x, y, 9, 9);
    }
    public void vista(){
        JFrame frame = new JFrame("KNN");
        frame.setContentPane(new GUIKnn().panelmain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
