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

        /* Triangle No-Arg Constructor:
            prompt for input; error message and re-prompt on invalid input
         */
        Triangle myTriangle = new Triangle();

        /* check collinear function:
            if collinear
                error message,
                pause for enter then halt
         */

        /* print special points function */
        myTriangle.printSpecialPoints();

        /* check equilateral function:
            if equilateral
                print absolute value of largest distance between special points,
                pause for enter then halt
         */

        /* measure distance function:
             calculate euler line from two special points,
             measure distance D from third center,
             print D and percentage error,
             pause for enter then halt
         */
        myTriangle.measureDistance();
    }

    void pressToContinue() {
        //System.out.println("Press Enter key to continue...");
        try {
            System.in.read();
        } catch (Exception e) {
        }
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
        return "(" + X + "), (" + Y + ")";
    }
}

class Triangle {
    Point A, B, C;
    Point centroid, circumcenter, orthocenter;

    Triangle() {
        do {
            // error message and re-prompt on invalid input
        } while(!goodInput());
    }

    private boolean goodInput() {
        // prompt and re-prompt
        A = new Point(0,0);
        B = new Point(0,1);
        C = new Point(1,0);
        return true;
    }
    void printSpecialPoints() {
        // initialize then print special points
        orthocenter = new Point(-1, -1);
        centroid = new Point(-1, -1);
        circumcenter = new Point(-1, -1);
        System.out.println("Orthocenter: " + orthocenter);
        System.out.println("Centroid: " + centroid);
        System.out.println("Circumcenter: " + circumcenter);

    };

    void measureDistance() {
        double D;

        /*
         calculate euler line from two special points,
         measure distance D from third center,
         print D and percentage error,
         pause for enter then halt
         */
    }

    /* check collinear function */

    /* check equilateral function */

}
