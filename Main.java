/*
 * Title: Confirming the Euler Line
 * Description: This program takes user 2D points and reports the Euler line from special points on a triangle.
 * Authors: Daniel Eggers <dme998@mail.umsl.edu>, Peter Galloway <ptgxfv@mail.umsl.edu> 
 * Course: CS 4500, Section 001
 * Date: 02/08/2021
 * File: Main.java
 * Language: Java 8 (IDE: IntelliJ, onlinegdb.com)
 * File Dependencies: none
 * Created extra files: none
 *
 * Resources:
 * General:                     https://docs.oracle.com/javase/8/docs/api/
 * DecimalFormat:               https://stackoverflow.com/a/4184015
 * getInputDouble method:       https://coderanch.com/t/668346/java/Catch-InputMismatchException-working
 * centroid calculation:        https://byjus.com/maths/centroid-of-a-triangle/
 * orthocenter calculation:     https://byjus.com/maths/orthocenter/
 * orthocenter calculation:     https://tutors.com/math-tutors/geometry-help/how-to-find-orthocenter-of-a-triangle
 * circumcenter calculation:    https://byjus.com/maths/circumcenter-of-a-triangle/
 */

import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Print description

        // Triangle construction
        Triangle myTriangle = new Triangle(getPoints());

        // Check collinear
        if (myTriangle.isCollinear()) {
            System.out.println("The given points are collinear.");
        } else {
            System.out.println("\nResults: ");
            
            // Print special points
            myTriangle.printSpecialPoints();

            // Check equilateral
            if (myTriangle.isEquilateral()) {
                System.out.println("The given points form an equilateral triangle.");
                // Print absolute value of largest distance between special points
                System.out.println(myTriangle.absDistance());
            } else {
                // Print distance error
                System.out.println(myTriangle.distanceError());
            }
        }
        // Pause for ENTER then exit */
        slowExit();
    }

    /**
     * Calls getInputDouble function to receive input
     * @return Array of Points representing a triangle
     */
    static Point[] getPoints() {

        // assigns first point's x, y values to new Point
        double x1 = getInputDouble("Enter first point X: ");
        double y1 = getInputDouble("Enter first point Y: ");
        Point a = new Point(x1, y1);
//        System.out.println("Point A is " + a);

        // assigns second point's x, y values to new Point
        double x2 = getInputDouble("Enter second point X: ");
        double y2 = getInputDouble("Enter second point Y: ");
        Point b = new Point(x2, y2);
//        System.out.println("Point B is " + b);

        // assigns third point's x, y values to new Point
        double x3 = getInputDouble("Enter third point X: ");
        double y3 = getInputDouble("Enter third point Y: ");
        Point c = new Point(x3, y3);
//        System.out.println("Point C is " + c);

        return new Point[]{a,b,c};
    }

    /**
     * Prompts user to input points of a triangle
     * Re-prompts on invalid input
     * @param message String describing which Point should be entered
     * @return Double value to be used as a coordinate value
     */
    static double getInputDouble(String message) {
        Scanner input = new Scanner(System.in);
        System.out.print(message);

        // verifies input is in fact a double
        while(!input.hasNextDouble()) {
            System.out.print("Invalid, try again: ");
            input.nextLine();
        }
        return input.nextDouble();
    }

    /**
     * Waits for the user to press ENTER until exiting
     */
    static void slowExit() {
        System.out.println("Press ENTER key to exit...");
        try {
            System.in.read();
        } catch (Exception ignored) {
        }
    }
}

/**
 * Simple representation of a Point with two coordinate values
 */
class Point {
    // declare class member variables
    final double X, Y;
    Point(double x, double y) {
        this.X = x;
        this.Y = y;
    }

    /**
     * @return Formatted String to 2 decimal places
     */
    @Override
    public String toString() {
        return "(" + new DecimalFormat("#.##").format(X) +
                ", " + new DecimalFormat("#.##").format(Y) + ")";
    }
}

/**
 * Represents a side of a triangle, also used as a line segment
 */
class Side {
    // declare class member variables
    final Point point1, point2;
    final double length, slope, pSlope;

    /**
     * Assists in calculating special points
     * Finding slope and perpendicular slope is important
     * @param p1 First point of triangle side or line segment
     * @param p2 Second point of triangle side or line segment
     */
    Side(Point p1, Point p2){
        // Assigns points of side or line segment, and its length
        point1 = p1;
        point2 = p2;
        length = Math.sqrt(Math.pow((point2.X - point1.X),2) + Math.pow((point2.Y - point1.Y),2));

        if (point1.X == point2.X) {
            /*
            if X values are equal this is a vertical line,
            this means the perpendicular slope will be horizontal
             */
            slope = Double.NaN;
            pSlope = 0;
        } else if (point1.Y == point2.Y) {
            /*
            if Y values are equal this is a horizontal line,
            this means the perpendicular slope will be vertical
             */
            slope = 0;
            pSlope = Double.NaN;
        } else {
            /*
            vertical and horizontal lines have been dealt with already,
            only sloped lines reach this point
             */
            slope = (point2.Y - point1.Y) / (point2.X - point1.X);
            pSlope = -1 / slope;
        }
    }
}

/**
 * Contains three Points, special points, and Sides
 */
class Triangle {
    // declare class member variables
    Point A, B, C;
    Point centroid, circumcenter, orthocenter;
    Side AB, AC, BC;

    /**
     * Assigns all Triangle member variables
     * @param triangle Array of points, size 3
     */
    Triangle(Point[] triangle) {
        // Uses gathered points to make a triangle
        A = triangle[0];
        B = triangle[1];
        C = triangle[2];

        // Assigns the three sides of the triangle
        AB = new Side(A,B);
        AC = new Side(A,C);
        BC = new Side(B,C);

        // Calls appropriate methods to assign special points
        orthocenter = calculateOrthocenter();
        centroid = calculateCentroid();
        circumcenter = calculateCircumcenter();
    }

    /**
     * Prints the three special points of a triangle
     */
    void printSpecialPoints() {
        System.out.println("Orthocenter: " + orthocenter);
        System.out.println("Centroid: " + centroid);
        System.out.println("Circumcenter: " + circumcenter);
    }

    /**
     * Calculates the orthocenter of the triangle
     * @return Point object of orthocenter
     */
    private Point calculateOrthocenter() {
        // Find y-int for lines CF, AD. Then find orthocenter
        double bCF = C.Y - (AB.pSlope * C.X);
        double bAD = A.Y - (BC.pSlope * A.X);

        // Point O typically used to denote a special point
        double OX, OY;

        if (AB.slope == 0) {
            /*
            if AB slope is 0 then perpendicular slope is a vertical line,
            this means the X value will be the same as C's
             */
            OX = C.X;
            OY = BC.pSlope * (OX - A.X) + A.Y;
        } else if (BC.slope == 0) {
            /*
            if BC slope is 0 then perpendicular slope is a vertical line,
            this means the X value will be the same as A's
            */
            OX = A.X;
            OY = AB.pSlope * (OX - C.X) + C.Y;
        } else {
            /*
            calculate the orthocenter point (OX,OY)
            set AB line equal to BC line and solve for x
            mABx + bAB == mBCx + bBC
            then substitute newfound x and solve for y
             */
            OX = (bAD - bCF) == 0 ? 0 : (bAD - bCF) / (AB.pSlope - BC.pSlope);
            OY = (AB.pSlope * OX) + bCF;
        }
        return new Point(OX, OY);
    }

    /**
     * Calculates the centroid of the triangle
     * @return Point object of centroid
     */
    private Point calculateCentroid() {
        // Centroid formula: (x1+x2+x3)/3 + (y1+y2+y3)/3
        return new Point(((A.X + B.X + C.X) / 3), ((A.Y + B.Y + C.Y) / 3));
    }

    /**
     * Calculates the circumcenter of the triangle
     * @return Point object of circumcenter
     */
    private Point calculateCircumcenter() {
        // Instantiate new Point's representing the midpoint
        Point midAB = new Point((A.X + B.X)/2,(A.Y + B.Y)/2);
        Point midBC = new Point((B.X + C.X)/2,(B.Y + C.Y)/2);

        // Find y-int for lines FO, DO going through Circumcenter
        double bFO = midAB.Y - (AB.pSlope * midAB.X);
        double bDO = midBC.Y - (BC.pSlope * midBC.X);

        // Point O typically used to denote a special point
        double OX, OY;

        if (AB.slope == 0) {
            /*
            if AB slope is 0 then perpendicular slope is a vertical line,
            this means the X value will be the same as the midpoint of AB
             */
            OX = midAB.X;
            OY = BC.pSlope * (OX - midBC.X) + midBC.Y;
        } else if (BC.slope == 0) {
            /*
            if BC slope is 0 then perpendicular slope is a vertical line,
            this means the X value will be the same as the midpoint of BC
             */
            OX = midBC.X;
            OY = AB.pSlope * (OX - midAB.X) + midAB.Y;
        } else {
            /*
            calculate the circumcenter point (OX,OY)
            set midAB line equal to midBC line and solve for x
            mABx + bAB == mBCx + bBC
            then substitute newfound x and solve for y
             */
            OX = (bDO - bFO) == 0 ? 0 : (bDO - bFO) / (AB.pSlope - BC.pSlope);
            OY = AB.pSlope * (OX - midAB.X) + midAB.Y;
        }
        return new Point(OX, OY);
    }

    /**
     * Finds the distance from centroid to the Euler Line
     * @return Two Strings: distance, and rounding percentage error
     */
    String distanceError() {
        // Instantiate new Side for Euler Line
        Side eulerLine = new Side(orthocenter, circumcenter);

        // Find y-int for lines EL (euler line), PL (perpendicular to euler line)
        double bEL = orthocenter.Y - (eulerLine.slope * orthocenter.X);
        double bPL = centroid.Y - (eulerLine.pSlope * centroid.X);
        double distance;

        if (eulerLine.slope == 0) {
            // If EL slope is 0 then distance measured from difference of centroid and orthocenter Y values
            distance = Math.abs(centroid.Y - orthocenter.Y);
        } else if (eulerLine.pSlope == 0) {
            // If PL slope is 0 then distance measured from difference of centroid and orthocenter X values
            distance = Math.abs(centroid.X - orthocenter.X);
        } else {
            /*
            calculate point (EX,EY) that intersects both EL and PL,
            this finds the closest distance from the EL to the centroid
             */
            double EX = (bEL - bPL) / (eulerLine.pSlope - eulerLine.slope);
            double EY = (eulerLine.slope * EX) + bEL;
            distance = new Side(centroid, new Point(EX, EY)).length;
        }
        return "Distance D from Euler Line: " +
                new DecimalFormat("#.##").format(distance) +
                "\nPercentage Error (Approx.): " +
                new DecimalFormat("#.##").format((distance / eulerLine.length) * 100) +
                "%";
    }

    /**
     * Checks whether the Points given represent a triangle or a straight line
     * @return Are these points a straight line?
     */
    boolean isCollinear() {
        boolean isCollinear;

        // checks if points are vertical, horizontal, or sloped line
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

    /**
     * Checks whether the triangle is an Equilateral triangle
     * @return Are all sides of equal length?
     */
    boolean isEquilateral() {
        final int rd = 2;  //number of decimal places to round 
        
        // compares side lengths up to rd decimal places for equality
        int abLen = (int)(AB.length * Math.pow(10,rd));
        int acLen = (int)(AC.length * Math.pow(10,rd));
        int bcLen = (int)(BC.length * Math.pow(10,rd));
        
        // if the rdth digit is above 5, round it up 
        if (abLen % 10 > 5) abLen++;
        if (acLen % 10 > 5) acLen++;
        if (bcLen % 10 > 5) bcLen++;
        
        return (abLen == acLen) && (acLen == bcLen);
    }

    /**
     * Finds the biggest absolute distance between special points
     * @return Formatted String
     */
    String absDistance() {
        // Create new Side's to measure the length between special points
        Side oneTwo = new Side(orthocenter, centroid);
        Side oneThree = new Side(orthocenter, circumcenter);
        Side twoThree = new Side(centroid, circumcenter);

        // Compare set of lengths {(1,2),(1,3),(2,3)} of special points to find the biggest
        double maxDistance = oneTwo.length;
        if (maxDistance < oneThree.length) maxDistance = oneThree.length;
        if (maxDistance < twoThree.length) maxDistance = twoThree.length;

        return "Abs. value of biggest distance between special points: " +
                new DecimalFormat("#.#########").format(maxDistance);
    }
}
