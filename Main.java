/*
Title: Confirming the Euler Line
Description: This program takes user 2D points and reports the Euler line from special points on a triangle.
Authors: Daniel Eggers, Peter Galloway
Email: dme998@mail.umsl.edu, ptgxfv@mail.umsl.edu
Course: CS 4500, Section 001
Date: 02/08/2021
File: Main.java
Language: Java 8 (IDE: Java)
File Dependencies: none
Created extra files: none
Resources: Java Docs - https://docs.oracle.com/javase/8/docs/api/
https://coderanch.com/t/668346/java/Catch-InputMismatchException-working
 */

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /* print description */
//        testTriangles();

        Triangle myTriangle = new Triangle();
        /* build triangle function */
        myTriangle.build();

        /* print special points function */
//        myTriangle.printSpecialPoints();

        /* distance error function */
//        myTriangle.distanceError();
    }

    static void testTriangles() {
        Triangle right = new Triangle(
                new Point(0,0),
                new Point(0, 2),
                new Point(2,2),
                "right triangle"
        );
        Triangle isosceles = new Triangle(
                new Point(0,0),
                new Point(0, 2),
                new Point(1,1),
                "isosceles triangle"
        );
        Triangle equilateral = new Triangle(
                new Point(0,0),
                new Point(1, 0),
                new Point(0.5, (Math.sqrt(3))/2),
                "equilateral triangle"
        );
        Triangle verticalLine = new Triangle(
                new Point(0,0),
                new Point(0, 2),
                new Point(0,12),
                "vertical line"
        );
        Triangle horizontalLine = new Triangle(
                new Point(3,1),
                new Point(4, 1),
                new Point(6,1),
                "horizontal line"
        );
        Triangle slopedLine = new Triangle(
                new Point(0,0),
                new Point(1,1),
                new Point(2,2),
                "sloped line"
        );
    }
}

class Point {
    final double X, Y;
    Point(double x, double y) {
        this.X = x;
        this.Y = y;
    }

    @Override
    public String toString() {
        return "(" + X + ", " + Y + ")";
    }
}

class Triangle {
    Point A, B, C;
    Point centroid, circumcenter, orthocenter;

    Triangle() {}
    Triangle(Point a, Point b, Point c, String type) {
        A = a;
        B = b;
        C = c;

//        System.out.println("Collinear: " + isCollinear() + ", " + type);
        System.out.println("Equilateral: " + isEquilateral() + ", " + type);
    }
    private double getInputDouble(String message) {
        Scanner input = new Scanner(System.in);
        System.out.print(message);
        while(!input.hasNextDouble()) {
            System.out.print("Invalid, try again: ");
            input.nextLine();
        }
        return input.nextDouble();
    }
    
    void build() {
        /* prompt for A, B, C,
            error message then re-prompt on invalid input
         */

        double x1 = getInputDouble("Enter first point X: ");
        double y1 = getInputDouble("Enter first point Y: ");
        A = new Point(x1, y1);
        System.out.println("Point A is " + A);

        double x2 = getInputDouble("Enter second point X: ");
        double y2 = getInputDouble("Enter second point Y: ");
        B = new Point(x2, y2);
        System.out.println("Point B is " + B);

        double x3 = getInputDouble("Enter third point X: ");
        double y3 = getInputDouble("Enter third point Y: ");
        C = new Point(x3, y3);
        System.out.println("Point C is " + C);

        /* if collinear
            error message,
            pause for enter then halt
         */
//        slowExit();

        /* if equilateral
            print absolute value of largest distance between special points,
            pause for enter then halt
         */
//        slowExit();

        orthocenter = calculateOrthocenter();
        centroid = calculateCentroid();
        circumcenter = calculateCircumcenter();

    }

    void printSpecialPoints() {
        System.out.println("Orthocenter: " + orthocenter);
        System.out.println("Centroid: " + centroid);
        System.out.println("Circumcenter: " + circumcenter);
    };
    
    private double getSlope(Point p1, Point p2) {
        /* calculates slope or sets to undefined (NaN) if denominator is 0 */
        double m = (p2.X - p1.X != 0) ? (p2.Y - p1.Y) / (p2.X - p1.X) : Double.NaN;
        System.out.println("Slope is " + m);
        return m;
    }
    private double getPerpSlope(double m) {
        /* calculates perpendicular slope */
        double p = (m != 0) ? (-1 / m) : 0;
        System.out.println("P-Slope is " + p);
        return p;
    }
    private double getYIntercept(double m, Point p) {
        /* calculates for b in the equation, y = mx + b */
        double b = p.Y - (m * p.X);
        System.out.println("Y-int of " + p.X + "," + p.Y + " with slope " + m + " is " + b);
        return b;
    }
    
    private Point calculateOrthocenter() {
        /* side AB */
        double mAB = getSlope(A, B);  //slope
        double pAB = getPerpSlope(mAB);  //perpendicular slope
        double bAB = getYIntercept(pAB, C);  //y-int
        
        /* side BC */
        double mBC = getSlope(B, C);  //slope
        double pBC = getPerpSlope(mBC);  //perpendicular slope
        double bBC = getYIntercept(pBC, A);  //y-int
        
        /* side AC */
        double mAC = getSlope(A, C);  //slope
        double pAC = getPerpSlope(mAC);  //perpendicular slope
        double bAC;  //y-int (not necessary unless another side is undefined)
        
        /* calculate the orthocenter point (x,y)
         * set AB line equal to BC line and solve for x
         * mABx + bAB == mBCx + bBC 
         * then substitute newfound x and solve for y
         * NaN checks are used to protect against divide by zero & undef errors
         */
        double ox = (pAB - pBC != 0) ? (bBC - bAB) / (pAB - pBC) : Double.NaN;
        double oy = (!Double.isNaN(ox)) ? (pAB * ox) + bAB : Double.NaN;

        orthocenter = new Point(ox, oy);
        return orthocenter;
    }
    
    private Point calculateCentroid() {
        /* (x1+x2+x3)/3 + (y1+y2+y3)/3 */
        centroid = new Point(((A.X + B.X + C.X) / 3), ((A.Y + B.Y + C.Y) / 3));
        return centroid;
    }
    
    private Point calculateCircumcenter() {
        //TODO
        return circumcenter;
    }

    void distanceError() {
        /*
         calculate euler line from two special points,
         measure distance D from third center,
         print D and percentage error,
         pause for enter then halt
         */
    }

    /* check collinear function */
    boolean isCollinear() {
        boolean isCollinear = false;

        if ((A.X == B.X) && (C.X == A.X)) {
            // vertical line: x1 = x2 = x3
            isCollinear = true;
        } else if ((A.Y == B.Y) && (C.Y == A.Y)) {
            // horizontal line: y1 = y2 = y3
            isCollinear = true;
        } else {
            double m = (B.Y - A.Y)/(B.X - A.X);
            // sloped line: y2 - y1 = m(x2 - x1)
            isCollinear = (C.Y - A.Y) == m * (C.X - A.X);
        }
        return isCollinear;
    }

    /* check equilateral function */
    boolean isEquilateral() {
        int abLen, acLen, bcLen;
        abLen = (int)(AB.length * Math.pow(10,6));
        acLen = (int)(AC.length * Math.pow(10,6));
        bcLen = (int)(BC.length * Math.pow(10,6));
        if (abLen % 10 > 5) {
            abLen++;
        }
        if (acLen % 10 > 5) {
            acLen++;
        }
        if (bcLen % 10 > 5) {
            bcLen++;
        }
        return (abLen == acLen) && (acLen == bcLen);
    }

    private void slowExit() {
//        System.out.println("Press Enter key to continue...");
        try {
            System.in.read();
        } catch (Exception ignored) {
        }
    }
}
