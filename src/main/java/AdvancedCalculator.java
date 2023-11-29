package main.java;

public class AdvancedCalculator {
    public double add(double a, double b) {
        double result = a + b;

        if ((a > 0 && b > 0 && result <= a) || (a < 0 && b < 0 && result >= a)) {
            throw new ArithmeticException("Overflow in add");
        }

        return result;
    }

    public double subtract(double a, double b) {
        double result = a - b;

        if ((a > 0 && b < 0 && result <= a) || (a < 0 && b > 0 && result >= a)) {
            throw new ArithmeticException("Overflow in subtract");
        }

        return result;
    }

    public double multiply(double a, double b) {
        if (a == 0 || b == 0) {
            return 0;
        }

        double result = a * b;

        if (Double.isInfinite(result)) {
            throw new ArithmeticException("Overflow in multiply");
        }

        return result;
    }

    public double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero is not allowed");
        }

        double result = a / b;

        double threshold = Double.MAX_VALUE - 1e10;
        if (Double.isInfinite(result) || Double.isNaN(result) || result > threshold) {
            throw new ArithmeticException("Overflow or invalid operation in divide");
        }

        return result;
    }

    public double power(double base, double exponent) {
        double result = Math.pow(base, exponent);

        if (Double.isInfinite(result)) {
            throw new ArithmeticException("Overflow in power operation");
        }
        if (Double.isNaN(result)) {
            throw new ArithmeticException("Invalid operation in power");
        }
        return result;
    }

    public double sqrt(double a) {
        if (a < 0) {
            throw new ArithmeticException("Cannot calculate the square root of a negative number");
        }

        return Math.sqrt(a);
    }

    public double factorial(int n) {
        if (n < 0) throw new IllegalArgumentException("Factorial of negative numbers is not defined");
        if (n > 20) {
            throw new ArithmeticException("Factorial value is too large");
        }
        if (n == 0) return 1;
        double result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public double log(double value) {
        if (value <= 0) {
            throw new ArithmeticException("Logarithm of non-positive values is not defined");
        }
        return Math.log(value);
    }
}
