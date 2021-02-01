/*
Title: Confirming the Euler Line
Description: This program takes user 2D points and reports the Euler line from special points on a triangle.

Authors: Daniel Eggers, Peter Galloway
Email: [daniel eggers' email], ptgxfv@mail.umsl.edu
Course: CS 4500, Section 001
Date: 02/08/2021

File: Main.java
Language: Java 8 (IDE: Java)
File Dependencies: none
Created extra files: none
Resources: Java Docs - https://docs.oracle.com/javase/8/docs/api/
 */

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

    void build() {
        /* prompt for A, B, C,
            error message then re-prompt on invalid input
         */

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
