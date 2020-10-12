package kmeans;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class KMeans {

    private int numPuntos;//numero de datos puntos
    private int numclusters;//numero de clases
    private int numA;
    private ArrayList<coordenadas> objetos = new ArrayList<>();
    private ArrayList<coordenadas> centroides = new ArrayList<>();
    private ArrayList<Color> colores = new ArrayList<>();
    private String typeDistan = "";


    public String getTypeDistan() {
        return typeDistan;
    }

    public void setTypeDistan(String typeDistan) {
        this.typeDistan = typeDistan;
    }

    public KMeans() {
    }

    /*public KMeans(int numPuntos, int numclusters) {
        this.numPuntos = numPuntos;
        this.numclusters = numclusters;
    }*/

    public int getNumPuntos() {
        return numPuntos;
    }

    public void setNumPuntos(int numPuntos) {
        this.numPuntos = numPuntos;
    }

    public int getNumclusters() {
        return numclusters;
    }

    public void setNumclusters(int numclusters) {
        this.numclusters = numclusters;
    }

    public ArrayList<coordenadas> getObjetos() {
        return objetos;
    }

    public void setObjetos(ArrayList<coordenadas> objetos) {
        this.objetos = objetos;
    }

    public ArrayList<coordenadas> getCentroides() {
        return centroides;
    }

    public void setCentroides(ArrayList<coordenadas> centroides) {
        this.centroides = centroides;
    }

    public ArrayList<Color> getColores() {
        return colores;
    }

    public void setColores(ArrayList<Color> colores) {
        this.colores = colores;
    }


    @Override
    public String toString() {
        return "KMeans{" + "numPuntos=" + numPuntos + ", numclusters=" + numclusters + ", objetos=" + objetos + '}';
    }

    public void generaObjetos() {
        Random r = new Random();
        //tamaño de panel x y y
        for (int i = 0; i < numPuntos; i++) {
            objetos.add(new coordenadas(r.nextInt(700), r.nextInt(600)));
            System.out.println(objetos.get(i));
        }
    }

    //taarea
    public void ClasificarObjetos() {
        int NumObjetos = numPuntos;
        int NumClases = numclusters;
        int NumAtractores = numA;

        System.out.printf("Objetos: " + NumObjetos + "\nClases: " + NumClases + "\nAtractores: " + NumAtractores);
    }

    public void CoordenadaAtractores(int x, int y) {
        coordenadas b = new coordenadas(x, y);
    }

    public int getNumA() {
        return numA;
    }

    public void setNumA(int numA) {
        this.numA = numA;
    }
    //fin tarea

    public void limpiar() {
        ClasificarObjetos();
        this.numclusters = 0;
        this.numPuntos = 0;
        objetos.clear();
        centroides.clear();
        colores.clear();
        //txtobjetos
    }


    public float distancia(coordenadas p1, coordenadas p2) {
        float d = (float) Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
        return d;
    }

    public float distanciamanhattan(coordenadas p1, coordenadas p2) {
        //float d =(float) Math.sqrt( Math.pow(p2.getX()-p1.getX(),2)+ Math.pow(p2.getY()- p1.getY(),2));
        float d = Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY());
        //distance += abs(x_value - x_goal) + abs(y_value - y_goal)
        return d;
    }

    public float hammingDistance(coordenadas p1, coordenadas p2) {
        int a = (int) p1.getX();
        int b = (int) p1.getY();
        int c = (int) p2.getX();
        int d = (int) p2.getY();
        int x = a ^ b ^ c ^ d;
        int setBits = 0;
        int f=0;

        while (x > 0) {
            //setBits += x & 1;
            f += x & 1;
            x >>= 1;
        }

        return f;
    }

    public float haversine(coordenadas p1, coordenadas p2) {
        // distance between latitudes and longitudes
        float d;
        float lat1 = p1.getX();
        float lat2 = p2.getX();
        float lon1 = p1.getY();
        float lon2 = p2.getY();
        float dLat = (float)Math.toRadians(lat2 - lat1);
        //float dLat =(float) Math.toRadians(p2.getX() - p1.getX());
        float dLon = (float)Math.toRadians(lon2 - lon1);
        //float dLon = Math.toRadians(p2.getY()) - p1.getY());

        // convert to radians
        lat1 = (float)Math.toRadians(lat1);
        lat2 = (float)Math.toRadians(lat2);

        // apply formulae
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) *
                        Math.cos(lat1) *
                        Math.cos(lat2);
        float rad = 6371;
        float c = 2 * (float)Math.asin(Math.sqrt(a));
        d=rad*c;
        return d;
    }

    public void distMenor(coordenadas act) {
        float menor = 100000;

        for (int i = 0; i < centroides.size(); i++) {
            float d = 0;
            switch (getTypeDistan()) {
                case "euclidiana":
                    d = distancia(act, centroides.get(i));
                    break;
                case "manhattan":
                    d = distanciamanhattan(act, centroides.get(i));
                    break;
                case "hamming":
                    d = hammingDistance(act, centroides.get(i));
                    break;
                case "haversine":
                    d = haversine(act, centroides.get(i));
                    break;
                case "ultima":
                    d = hammingDistance(act, centroides.get(i));
                    break;

            }
            //float d = distancia(act, centroides.get(i));
            if (d < menor) {
                menor = d;
                act.setK(i + 1); //indice de la menor distancia
            }
            //System.out.println("\nDistancia : "+ d);
        }
        //System.out.println("\nClase "+ act.getK());
    }

    public void asignarClases() {
        for (coordenadas act : objetos) {
            distMenor(act);
        }
    }

    public void calificacionCentroides() {
        int[] objXClase = new int[numclusters]; //guarda número
        int[][] coord = new int[numclusters][2]; //(X,Y)

        //System.out.println("objClase: " + objXClase);
        for (coordenadas act : objetos) {

            int clase = act.getK() - 1;
            objXClase[clase]++;
            //System.out.println("objClase: " + objXClase[clase]);
            coord[clase][0] += act.getX();
            coord[clase][1] += act.getY();

        }
        int clase = 0;
        for (coordenadas c : centroides) {
            c.setX(coord[clase][0] / objXClase[clase]);
            c.setY(coord[clase][1] / objXClase[clase]);
            clase++;
        }
    }

}
