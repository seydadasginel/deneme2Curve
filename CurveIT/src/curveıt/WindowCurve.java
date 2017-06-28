/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curveıt;


/**
 *
 * @author seyda
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.event.MouseInputListener;

// Hermitte egrisi olusturmak icin  her kontrol noktasının tanjant vektörlerini gerektirir.

//P0:Eğrinin başlangıç noktası.
//P1:Eğrinin bitiş noktası
//T0:Eğrinin başlangıç noktasından nasıl çıktığının tanjantu
//T1:Eğrinin bitiş noktasına nasıl vatdığının tanjantı

/* Bu 4 vektör ve Hermite Fonksiyonları bir araya getirildiğinde;

    h1(s) = 2s^3 – 3s^2 + 1

    h2(s) = -2s^3 + 3s^2

    h3(s) = s^3 – 2s^2 + s

    h4(s) = s^3 – s^2

*/
public class WindowCurve extends JFrame implements MouseInputListener {
 
    private Point p1 = new Point();
    private Point p2 = new Point();
    private List list = new ArrayList();
    private Curve curveCurrentObj;
    private int clicks = 0;
 
    public WindowCurve() {
 
        setTitle("Drawing Hermite Spline (Please 4 clics for p0,p1,t0,t1)");
        addMouseListener(this);
        setLocation(100, 100);
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
 
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.white);
        g.fillRect(0, 0, 800, 800);
        g.setColor(Color.red);
        
        Curve curveObj;
        for (int i = 0; i < list.size(); i++) {
            curveObj = (Curve) (list.get(i));
            curveObj.StartHermiteCurve(g);
        }
    }
 
    public static void main(String[] args) {
        new WindowCurve();
    }
 
    @Override
    public void mouseClicked(MouseEvent e) {
    }
 
    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
 
        if (clicks == 0) {
            curveCurrentObj = new Curve();
            curveCurrentObj.setP1(new Point(x, y));
 
            clicks++;
        }
         else if (clicks == 1) {
            curveCurrentObj.setP2(new Point(x, y));
 
            clicks++;
        }
         else  if (clicks == 2) {  
            curveCurrentObj.setP3(new Point(x, y));
            clicks++;
        }
         else {
            curveCurrentObj.setP4(new Point(x, y));
            list.add(curveCurrentObj);
            clicks = 0;
        }
        repaint();
    }
 
    @Override
    public void mouseReleased(MouseEvent e) {
    }
 
    @Override
    public void mouseEntered(MouseEvent e) {
    }
 
    @Override
    public void mouseExited(MouseEvent e) {
    }
 
    @Override
    public void mouseDragged(MouseEvent e) {
    }
 
    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
 

// Hermitte egrisi olusturmak icin  her kontrol noktasının tanjant vektörlerini gerektirir.

//P0:Eğrinin başlangıç noktası.
//P1:Eğrinin bitiş noktası
//T0:Eğrinin başlangıç noktasından nasıl çıktığının tanjantu
//T1:Eğrinin bitiş noktasına nasıl vatdığının tanjantı

/* Bu 4 vektör ve Hermite Fonksiyonları bir araya getirildiğinde;

    h1(s) = 2s^3 – 3s^2 + 1

    h2(s) = -2s^3 + 3s^2

    h3(s) = s^3 – 2s^2 + s

    h4(s) = s^3 – s^2

*/
class Point {
 
    private int x;
    private int y;
 
    public Point() {
    }
 
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
 
    public int getX() {
        return x;
    }
 
    public int getY() {
        return y;
    }
 
    public void setX(int x) {
        this.x = x;
    }
 
    public void setY(int y) {
        this.y = y;
    }
}
 
 
 
 
 
class Curve {
 
    private Point p1;
    private Point p2;
    private Point p3;
    private Point p4;
    private List points;
    private double H[] = {2, 1, -2, 1, -3, -2, 3, -1, 0, 1, 0, 0, 1, 0, 0, 0};
 
    public Curve() {
    }
 
    public Curve(Point p1, Point p2,Point p3, Point p4) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
      
    }
 
    public Curve(List points) {
        this.points = points;
    }
 
    public Point getP1() {
        return p1;
    }
 
    public Point getP2() {
        return p2;
    }
 
    public void setP1(Point p1) {
        this.p1 = p1;
    }
 
    public void setP2(Point p2) {
        this.p2 = p2;
    }
 
    public Point getP3() {
        return p3;
    }
 
    public Point getP4() {
        return p4;
    }
 
    public void setP3(Point p3) {
        this.p3 = p3;
    }
 
    public void setP4(Point p4) {
        this.p4 = p4;
    }
 
   public void StartHermiteCurve(Graphics g)
   {    
       
       DrawHermiteCurve(g,p1,p2,p3,p4,140);
   }
    Vector4 GetHermiteCoeff(double x0, double s0, double x1, double s1) {
 
        Matrix4 basis = new Matrix4(H);
        Vector4 v = new Vector4(x0, s0, x1, s1);
        return basis.multiply(v);
    }
 
    void DrawHermiteCurve(Graphics g, Point P0, Point T0, Point P1, Point T1, int numpoints) {
        Vector4 xcoeff = GetHermiteCoeff(P0.getX(), T0.getX(), P1.getX(), T1.getX());
        Vector4 ycoeff = GetHermiteCoeff(P0.getY(), T0.getY(), P1.getY(), T1.getY());
        System.out.println("Xcoeff  "+xcoeff.getValue(0)+", "+xcoeff.getValue(1)+", "+xcoeff.getValue(2)+", "+xcoeff.getValue(3));
        System.out.println("Ycoeff  "+ycoeff.getValue(0)+", "+ycoeff.getValue(1)+", "+ycoeff.getValue(2)+", "+ycoeff.getValue(3));
         
        if (numpoints < 2) {
            return;
        }
        double dt = 1.0 / (numpoints - 1);
 
        for (double t = 0; t <= 1; t += dt) {
            Vector4 vt = new Vector4();
            vt.setValue(3, 1);
            for (int i = 2; i >= 0; i--) {
                vt.setValue(i, vt.getValue(i + 1) * t);
            }
            int x = (int) Math.round(xcoeff.DotProduct(vt));
            int y = (int) Math.round(ycoeff.DotProduct(vt));
            g.drawOval(x, y, 1, 1);
        }
    }
 
    void DrawHermiteCurve2(Graphics g, Point P0, Point T0, Point P1, Point T1, int numpoints) {
        Vector4 xcoeff = GetHermiteCoeff(P0.getX(), T0.getX(), P1.getX(), T1.getX());
        Vector4 ycoeff = GetHermiteCoeff(P0.getY(), T0.getY(), P1.getY(), T1.getY());
        if (numpoints < 2) {
            return;
        }
        double dt = 1.0 / (numpoints - 1);
        for (double t = 0; t <= 1; t += dt) {
            Vector4 vt = new Vector4();
            vt.setValue(3, 1);
            for (int i = 2; i >= 0; i--) {
 
                vt.setValue(i, vt.getValue(i + 1) * t);
            }
            int x = (int) Math.round(xcoeff.DotProduct(vt));
            int y = (int) Math.round(ycoeff.DotProduct(vt));
            if (t == 0) {
                // MoveToEx(g, x, y, NULL);
                g.drawOval(x, y, 1, 1);
            } else {
                //LineTo(g, x, y);
                g.drawOval(x, y, 1, 1);
            }
 
 
        }
    }
   
 
class Vector4 {
 
    public double v[] = new double[4];
 
    public Vector4() {
    }
 
    public Vector4(double a, double b, double c, double d) {
        v[0] = a;
        v[1] = b;
        v[2] = c;
        v[3] = d;
    }
 
    public double[] getV() {
        return v;
    }
 
    public double getValue(int index) {
        return v[index];
    }
 
    public void setValue(int index, double value) {
        v[index] = value;
    }
 
    public double DotProduct(Vector4 b) {
        return v[0] * b.v[0] + v[1] * b.v[1] + v[2] * b.v[2] + v[3] * b.v[3];
    }
}
 
class Matrix4 {
 
    public Vector4 M[] = new Vector4[4];
 
    public Matrix4(double A[]) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            M[i]=new Vector4();
            for (int j = 0; j < 4; j++) {
                System.out.println(A[count]);
                  System.out.println( M[i].getValue(0));
                M[i].setValue(j, A[count]);
                count++;
            }
        }
    }
 
    public Vector4[] getM() {
        return M;
    }
 
    Vector4 multiply(Vector4 b) {
        Vector4 res = new Vector4();
        double count = 0.0d;
        for (int i = 0; i < 4; i++) {
 
            for (int j = 0; j < 4; j++) {
 
                count += M[i].getValue(j) * b.getValue(j);
            }
            res.setValue(i, count);
            count = 0;
        }
 
        return res;
    }
    }
}
