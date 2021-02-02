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

        Triangle myTriangle = new Triangle();
        /* build triangle function */
        myTriangle.build();

        /* print special points function */
        myTriangle.printSpecialPoints();

        /* distance error function */
        myTriangle.distanceError();
    }
}

class Point {
    private final double X, Y;
    Point(double x, double y) {
        this.X = x;
        this.Y = y;
    }

    double getX() { return X; }
    double getY() { return Y; }

    @Override
    public String toString() {
        return "(" + X + ", " + Y + ")";
    }
}

class Triangle {
    Point A, B, C;
    Point centroid, circumcenter, orthocenter;
    
    private double getInputDouble(String message) {
        Scanner input = new Scanner(System.in);
        System.out.print(message);
        while(!input.hasNextDouble()) {
            System.out.print("Invalid, try again: ");
            input.next();
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
    }

    void printSpecialPoints() {
        System.out.println("Orthocenter: " + orthocenter);
        System.out.println("Centroid: " + centroid);
        System.out.println("Circumcenter: " + circumcenter);

    };

    void distanceError() {
        /*
         calculate euler line from two special points,
         measure distance D from third center,
         print D and percentage error,
         pause for enter then halt
         */
    }

    /* check collinear function */

    /* check equilateral function */

    private void slowExit() {
//        System.out.println("Press Enter key to continue...");
        try {
            System.in.read();
        } catch (Exception ignored) {
        }
    }
}
