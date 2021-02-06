/*
Title: Confirming the Euler Line
Description: This program takes user 2D points and reports the Euler line from special points on a triangle.
Authors: Daniel Eggers, Peter Galloway
Email: dme998@mail.umsl.edu, ptgxfv@mail.umsl.edu
Course: CS 4500, Section 001
Date: 02/08/2021
File: Main.java
Language: Java 8 (IDE: IntelliJ, onlinegdb.com)
File Dependencies: none
Created extra files: none
Resources: Java Docs - https://docs.oracle.com/javase/8/docs/api/
https://coderanch.com/t/668346/java/Catch-InputMismatchException-working
 */

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /* print description */

        /* triangle construction */
//        testTriangles();
        Triangle myTriangle = new Triangle(getPoints());

        if (myTriangle.isCollinear()) {
            /* check collinear */
            System.out.println("Collinear message");
            slowExit();
        } else if (myTriangle.isEquilateral()) {
            /* check equilateral */
            /* print absolute value of largest distance between special points */
            System.out.println("Equilateral message");
            slowExit();
        } else {
            myTriangle.printSpecialPoints();
            myTriangle.distanceError();
        }
    }

    static Point[] getPoints() {
        double x1 = getInputDouble("Enter first point X: ");
        double y1 = getInputDouble("Enter first point Y: ");
        Point a = new Point(x1, y1);
        System.out.println("Point A is " + a);

        double x2 = getInputDouble("Enter second point X: ");
        double y2 = getInputDouble("Enter second point Y: ");
        Point b = new Point(x2, y2);
        System.out.println("Point B is " + b);

        double x3 = getInputDouble("Enter third point X: ");
        double y3 = getInputDouble("Enter third point Y: ");
        Point c = new Point(x3, y3);
        System.out.println("Point C is " + c);

        return new Point[]{a,b,c};
    }

    static double getInputDouble(String message) {
        Scanner input = new Scanner(System.in);
        System.out.print(message);
        while(!input.hasNextDouble()) {
            System.out.print("Invalid, try again: ");
            input.nextLine();
        }
        return input.nextDouble();
    }

    static void slowExit() {
//        System.out.println("Press Enter key to continue...");
        try {
            System.in.read();
        } catch (Exception ignored) {
        }
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

class Side {
    final Point point1, point2;
    final double length, slope, pSlope;
    Side(Point p1, Point p2){
        point1 = p1;
        point2 = p2;
        length = Math.sqrt(Math.pow((point2.X - point1.X),2) + Math.pow((point2.Y - point1.Y),2));
        if (point1.X == point2.X) {
            slope = Double.NaN;
            pSlope = 0;
        } else if (point1.Y == point2.Y) {
            slope = 0;
            pSlope = Double.NaN;
        } else {
            slope = (point2.Y - point1.Y) / (point2.X - point1.X);
            pSlope = -1 / slope;
        }
    }
}

class Triangle {
    Point A, B, C;
    Point centroid, circumcenter, orthocenter;
    Side AB, AC, BC;

    Triangle() {}
    Triangle(Point[] triangle) {
        A = triangle[0];
        B = triangle[1];
        C = triangle[2];
        AB = new Side(A,B);
        AC = new Side(A,C);
        BC = new Side(B,C);

        orthocenter = calculateOrthocenter();
        centroid = calculateCentroid();
        circumcenter = calculateCircumcenter();
    }

    // for use with testTriangles function
    Triangle(Point a, Point b, Point c, String type) {
        A = a;
        B = b;
        C = c;
        AB = new Side(A,B);
        AC = new Side(A,C);
        BC = new Side(B,C);

//        System.out.println("Collinear: " + isCollinear() + ", " + type);
        System.out.println("Equilateral: " + isEquilateral() + ", " + type);
    }

    void printSpecialPoints() {
        System.out.println("Orthocenter: " + orthocenter);
        System.out.println("Centroid: " + centroid);
        System.out.println("Circumcenter: " + circumcenter);
    };

    private Point calculateOrthocenter() {
        double CF = C.Y - (AB.pSlope * C.X);
        double AD = A.Y - (BC.pSlope * A.X);

        double OX, OY;
        if (AB.slope == 0) {
            OX = C.X;
            OY = BC.pSlope * (OX - A.X) + A.Y;
        } else if (BC.slope == 0) {
            OX = A.X;
            OY = AB.pSlope * (OX - C.X) + C.Y;
        } else {
            OX = (AD - CF) == 0 ? 0 : (AD - CF) / (AB.pSlope - BC.pSlope);
            OY = (AB.pSlope * OX) + CF;
        }
        /* calculate the orthocenter point (x,y)
         * set AB line equal to BC line and solve for x
         * mABx + bAB == mBCx + bBC 
         * then substitute newfound x and solve for y
         * NaN checks are used to protect against divide by zero & undef errors
         */

        return new Point(OX, OY);
    }
    
    private Point calculateCentroid() {
        /* (x1+x2+x3)/3 + (y1+y2+y3)/3 */
        return new Point(((A.X + B.X + C.X) / 3), ((A.Y + B.Y + C.Y) / 3));
    }
    
    private Point calculateCircumcenter() {
        Point midAB = new Point((A.X + B.X)/2,(A.Y + B.Y)/2);
        Point midBC = new Point((B.X + C.X)/2,(B.Y + C.Y)/2);

        double FO = midAB.Y - (AB.pSlope * midAB.X);
        double DO = midBC.Y - (BC.pSlope * midBC.X);
        double OX, OY;

        if (AB.slope == 0) {
            OX = midAB.X;
            OY = BC.pSlope * (OX - midBC.X) + midBC.Y;
        } else if (BC.slope == 0) {
            OX = midBC.X;
            OY = AB.pSlope * (OX - midAB.X) + midAB.Y;
        } else {
            OX = (DO - FO) == 0 ? 0 : (DO - FO) / (AB.pSlope - BC.pSlope);
            OY = AB.pSlope * (OX - midAB.X) + midAB.Y;
        }
        return new Point(OX, OY);
    }

    void distanceError() {
        /*
         calculate euler line from two special points,
         measure distance D from third center,
         print D and percentage error,
         pause for enter then halt
         */
    }

    /* check collinear method */
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

    /* check equilateral method */
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
}
