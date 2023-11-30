import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
public class AdvancedCalculatorJUnitTest {
    private main.java.AdvancedCalculator calculator;

    @BeforeEach
    public void setup() {
        System.out.println("Starting tests for JUnit");
        calculator = new main.java.AdvancedCalculator();
    }

    static Stream<Arguments> additionDataProvider() {
        return Stream.of(
                Arguments.of(2, 3, 5.0),
                Arguments.of(-5, -3, -8.0),
                Arguments.of(5, -3, 2.0),
                Arguments.of(5, 0, 5.0),
                Arguments.of(Double.MAX_VALUE / 2, Double.MAX_VALUE / 2, Double.MAX_VALUE),
                Arguments.of(Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE * 2),
                Arguments.of(5.5, 3.3, 8.8),
                Arguments.of(Double.NaN, 5, Double.NaN),
                Arguments.of(Double.NEGATIVE_INFINITY, 5, Double.NEGATIVE_INFINITY)
        );
    }

    @ParameterizedTest
    @MethodSource("additionDataProvider")
    public void testAddition(double a, double b, double expected) {
        System.out.println("Running testMethod1 in thread: " + Thread.currentThread().getName());
        assertEquals(expected, calculator.add(a, b));
    }

    static Stream<Arguments> additionExceptionDataProvider() {
        return Stream.of(
                Arguments.of(Double.MAX_VALUE, 1.0),
                Arguments.of(-Double.MAX_VALUE, -1.0),
                Arguments.of(Double.POSITIVE_INFINITY, 5),
                Arguments.of(1e10, 1e-10)
        );
    }

    @ParameterizedTest
    @MethodSource("additionExceptionDataProvider")
    public void testExceptionOnAddition(double a, double b) {
        System.out.println("Running testMethod1 in thread: " + Thread.currentThread().getName());
        ArithmeticException exception = assertThrows(ArithmeticException.class, () -> calculator.add(a, b));
        assertEquals("Overflow in add", exception.getMessage());
    }

    static Stream<Arguments> subtractionDataProvider() {
        return Stream.of(
                Arguments.of(5, 3, 2.0),
                Arguments.of(-5, -3, -2.0),
                Arguments.of(-5, 3, -8.0),
                Arguments.of(5, -3, 8.0),
                Arguments.of(0, 3, -3.0),
                Arguments.of(5, 0, 5.0),
                Arguments.of(Double.MAX_VALUE / 2 + Double.MAX_VALUE / 2, Double.MAX_VALUE / 2, Double.MAX_VALUE / 2),
                Arguments.of(Double.MIN_VALUE + Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE),
                Arguments.of(5.5, 3.3, 2.2),
                Arguments.of(Double.POSITIVE_INFINITY, 5, Double.POSITIVE_INFINITY)
        );
    }

    @ParameterizedTest
    @MethodSource("subtractionDataProvider")
    public void testSubtraction(double a, double b, double expected) {
        System.out.println("Running testMethod1 in thread: " + Thread.currentThread().getName());
        assertEquals(expected, calculator.subtract(a, b));
    }

    @Test
    public void testSubtractionWithNaN() {
        System.out.println("Running testMethod1 in thread: " + Thread.currentThread().getName());
        assertEquals(Double.NaN, calculator.subtract(Double.NaN, 5));
    }

    static Stream<Arguments> subtractionExceptionDataProvider() {
        return Stream.of(
                Arguments.of(Double.MAX_VALUE, -1.0),
                Arguments.of(-Double.MAX_VALUE, 1.0),
                Arguments.of(Double.NEGATIVE_INFINITY, 5)
        );
    }

    @ParameterizedTest
    @MethodSource("subtractionExceptionDataProvider")
    public void testExceptionOnSubtraction(double a, double b) {
        System.out.println("Running testMethod1 in thread: " + Thread.currentThread().getName());
        ArithmeticException exception = assertThrows(ArithmeticException.class, () -> calculator.subtract(a, b));
        assertEquals("Overflow in subtract", exception.getMessage());
    }

    public static Object[][] multiplicationDataProvider() {
        return new Object[][]{
                {2, 3, 6.0},
                {-5, -3, 15.0},
                {5, -3, -15.0},
                {5.0, 0.0, 0.0},
                {0.0, 5.0, 0.0},
                {0.0, 0.0, 0.0},
                {5.5, 2.0, 11.0}
        };
    }

    @ParameterizedTest
    @MethodSource("multiplicationDataProvider")
    public void testMultiplication(double a, double b, double expected) {
        System.out.println("Running testMethod1 in thread: " + Thread.currentThread().getName());
        assertEquals(expected, calculator.multiply(a, b), 0.000001, "Multiplication result was incorrect");
    }

    public static Object[][] multiplicationExceptionDataProvider() {
        return new Object[][]{
                {Double.MAX_VALUE, 2.0},
                {-Double.MAX_VALUE, 2.0}
        };
    }

    @ParameterizedTest
    @MethodSource("multiplicationExceptionDataProvider")
    public void testExceptionOnMultiplication(double a, double b) {
        System.out.println("Running testMethod1 in thread: " + Thread.currentThread().getName());
        ArithmeticException exception = assertThrows(ArithmeticException.class, () -> calculator.multiply(a, b));
        assertEquals("Overflow in multiply", exception.getMessage());
    }

    public static Object[][] divisionDataProvider() {
        return new Object[][]{
                {6, 3, 2.0},
                {-6, -3, 2.0},
                {6, -3, -2.0},
                {-6, 3, -2.0},
                {0, 3, 0.0},
                {5.5, 2.0, 2.75},
                {1, 1000000, 0.000001}
        };
    }

    @ParameterizedTest
    @MethodSource("divisionDataProvider")
    public void testDivision(double a, double b, double expected) {
        System.out.println("Running testMethod1 in thread: " + Thread.currentThread().getName());
        assertEquals(expected, calculator.divide(a, b), 0.000001);
    }

    @Test
    public void testDivisionByZero() {
        System.out.println("Running testMethod1 in thread: " + Thread.currentThread().getName());
        ArithmeticException exception = assertThrows(ArithmeticException.class, () -> {
            calculator.divide(1, 0);
        });
        assertEquals("Division by zero is not allowed", exception.getMessage());
    }

    @Test
    public void testDivideOverflow() {
        System.out.println("Running testMethod1 in thread: " + Thread.currentThread().getName());
        ArithmeticException exception = assertThrows(ArithmeticException.class, () -> {
            calculator.divide(Double.MAX_VALUE, Double.MIN_VALUE);
        });
        assertEquals("Overflow or invalid operation in divide", exception.getMessage());
    }

    static Stream<Arguments> powerDataProvider() {
        return Stream.of(
                Arguments.of(2, 3, 8.0),
                Arguments.of(5, 0, 1.0),
                Arguments.of(0, 3, 0.0),
                Arguments.of(-2, 3, -8.0),
                Arguments.of(2, -3, 1.0 / 8.0),
                Arguments.of(-2, -3, -1.0 / 8.0),
                Arguments.of(5, 1, 5.0),
                Arguments.of(1, 100, 1.0),
                Arguments.of(1e150, 2, 1e300)
        );
    }

    @ParameterizedTest
    @MethodSource("powerDataProvider")
    public void testPower(double base, double exponent, double expected) {
        System.out.println("Running testMethod1 in thread: " + Thread.currentThread().getName());
        assertEquals(expected, calculator.power(base, exponent), 1e290);
    }

    @Test
    public void testPowerOverflow() {
        System.out.println("Running testMethod1 in thread: " + Thread.currentThread().getName());
        double base = 10.0;
        double exponent = 309.0;

        ArithmeticException exception = assertThrows(
                ArithmeticException.class,
                () -> calculator.power(base, exponent)
        );

        assertTrue(exception.getMessage().contains("Overflow in power operation"));
    }

    @Test
    public void testPowerInvalidOperation() {
        System.out.println("Running testMethod1 in thread: " + Thread.currentThread().getName());
        double base = -4.0;
        double exponent = 0.5;

        ArithmeticException exception = assertThrows(
                ArithmeticException.class,
                () -> calculator.power(base, exponent)
        );

        assertTrue(exception.getMessage().contains("Invalid operation in power"));
    }

    private static Stream<Arguments> sqrtDataProvider() {
        return Stream.of(
                Arguments.of(4, 2.0),
                Arguments.of(0, 0.0),
                Arguments.of(1, 1.0),
                Arguments.of(2.25, 1.5),
                Arguments.of(1e10, 1e5),
                Arguments.of(1e-6, 1e-3)
        );
    }

    @ParameterizedTest
    @MethodSource("sqrtDataProvider")
    public void testSqrt(double input, double expected) {
        System.out.println("Running testMethod1 in thread: " + Thread.currentThread().getName());
        assertEquals(expected, calculator.sqrt(input), 0.000001);
    }

    @Test
    public void testSqrtNegativeNumber() {
        System.out.println("Running testMethod1 in thread: " + Thread.currentThread().getName());
        ArithmeticException exception = assertThrows(
                ArithmeticException.class,
                () -> calculator.sqrt(-4.0)
        );

        assertTrue(exception.getMessage().contains("Cannot calculate the square root of a negative number"));
    }

    private static Stream<Arguments> factorialDataProvider() {
        return Stream.of(
                Arguments.of(5, 120.0),
                Arguments.of(0, 1.0),
                Arguments.of(1, 1.0),
                Arguments.of(20, 2.43290200817664E18)
        );
    }

    @ParameterizedTest
    @MethodSource("factorialDataProvider")
    public void testFactorial(int input, double expected) {
        System.out.println("Running testMethod1 in thread: " + Thread.currentThread().getName());
        assertEquals(expected, calculator.factorial(input), 0.00001);
    }

    @Test
    public void testFactorialOfNegativeNumber() {
        System.out.println("Running testMethod1 in thread: " + Thread.currentThread().getName());
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculator.factorial(-5)
        );
        assertTrue(exception.getMessage().contains("Factorial of negative numbers is not defined"));
    }

    @Test
    public void testFactorialOfVeryLargeNumber() {
        System.out.println("Running testMethod1 in thread: " + Thread.currentThread().getName());
        Exception exception = assertThrows(
                ArithmeticException.class,
                () -> calculator.factorial(1000)
        );
        assertTrue(exception.getMessage().contains("Factorial value is too large"));
    }

    static Stream<Arguments> logDataProvider() {
        return Stream.of(
                Arguments.of(Math.E, 1.0),
                Arguments.of(1.0, 0.0),
                Arguments.of(0.5, -0.693147)
        );
    }

    @ParameterizedTest
    @MethodSource("logDataProvider")
    public void testLog(double input, double expected) {
        System.out.println("Running testMethod1 in thread: " + Thread.currentThread().getName());
        assertEquals(expected, calculator.log(input), 0.000001);
    }

    static Stream<Arguments> logExceptionDataProvider() {
        return Stream.of(
                Arguments.of(-5.0),
                Arguments.of(0.0)
        );
    }

    @ParameterizedTest
    @MethodSource("logExceptionDataProvider")
    public void testLogExceptions(double input) {
        System.out.println("Running testMethod1 in thread: " + Thread.currentThread().getName());
        assertThrows(
                ArithmeticException.class,
                () -> calculator.log(input),
                "Logarithm of non-positive values is not defined"
        );
    }

    static Stream<Arguments> logComparisonDataProvider() {
        return Stream.of(
                Arguments.of(1e10, true),
                Arguments.of(1e-6, false)
        );
    }

    @ParameterizedTest
    @MethodSource("logComparisonDataProvider")
    public void testLogComparison(double input, boolean expectedResult) {
        System.out.println("Running testMethod1 in thread: " + Thread.currentThread().getName());
        if (expectedResult) {
            assertTrue(calculator.log(input) > 0);
        } else {
            assertTrue(calculator.log(input) < 0);
        }
    }
}
