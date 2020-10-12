
package kmeans;

public class coordenadas {
    
    private int x,y;
    private int k;
    
    public coordenadas(){
        this.k =-1;
    }
    
    public coordenadas(int x, int y){
        this.x = x;
        this.y = y;
        this.k = -1;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    @Override
    public String toString() {
        return "coordenadas{" + "x=" + x + ", y=" + y + ", k=" + k + '}';
    }
    
    
}
