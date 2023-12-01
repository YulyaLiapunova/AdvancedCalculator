import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AdvancedCalculatorV2Test {

    long startTime, endTime;
    private main.java.AdvancedCalculator calculator;
    private static final Logger logger = LogManager.getLogger(main.java.AdvancedCalculator.class);


//    @BeforeClass
//    public void beforeClass() {
//        System.out.println("Used memory before test: " + MemoryUtil.getUsedMemoryBeforeGC() + " bytes");
//    }

//    @AfterClass
//    public void afterClass() {
//        System.out.println("Used memory after test: " + MemoryUtil.getUsedMemory() + " bytes");
//    }

    @BeforeClass
    private void startTime(){
        startTime = System.currentTimeMillis();
    }

    @AfterClass
    public void endTime() {
        endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        logger.info("Test executed in: " + elapsedTime + " milliseconds");
    }
    @BeforeMethod
    public void setup() {
        logger.info("Starting tests for testNg");
        calculator = new main.java.AdvancedCalculator();
    }

    @DataProvider(name = "additionDataProvider")
    public Object[][] additionDataProvider() {
        return new Object[][] {
                {2, 3, 5.0},
                {-5, -3, -8.0},
                {5, -3, 2.0},
                {5, 0, 5.0},
                {Double.MAX_VALUE / 2, Double.MAX_VALUE / 2, Double.MAX_VALUE},
                {Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE * 2},
                {5.5, 3.3, 8.8},
                {Double.NaN, 5, Double.NaN},
                {Double.NEGATIVE_INFINITY, 5, Double.NEGATIVE_INFINITY},
        };
    }

    @Test(dataProvider = "additionDataProvider")
    public void testAddition(double a, double b, double expected) {
        logger.info("Running testMethod1 in thread: " + Thread.currentThread().getName());
        assertEquals(calculator.add(a, b), expected);
    }

    @DataProvider(name = "additionExceptionDataProvider")
    public Object[][] additionExceptionDataProvider() {
        return new Object[][] {
                {Double.MAX_VALUE, 1.0},
                {-Double.MAX_VALUE, -1.0},
                {Double.POSITIVE_INFINITY, 5},
                {1e10, 1e-10}
        };
    }

    @Test(dataProvider = "additionExceptionDataProvider", expectedExceptions = ArithmeticException.class, expectedExceptionsMessageRegExp = "Overflow in add")
    public void testExceptionOnAddition(double a, double b) {
        logger.info("Running testMethod1 in thread: " + Thread.currentThread().getName());
        calculator.add(a, b);
    }

    @DataProvider(name = "subtractionDataProvider")
    public Object[][] subtractionDataProvider() {
        return new Object[][]{
                {5, 3, 2.0},
                {-5, -3, -2.0},
                {-5, 3, -8.0},
                {5, -3, 8.0},
                {0, 3, -3.0},
                {5, 0, 5.0},
                {Double.MAX_VALUE / 2 + Double.MAX_VALUE / 2, Double.MAX_VALUE / 2, Double.MAX_VALUE / 2},
                {Double.MIN_VALUE + Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE},
                {5.5, 3.3, 2.2},
                {Double.POSITIVE_INFINITY, 5, Double.POSITIVE_INFINITY},
        };
    }

    @Test(dataProvider = "subtractionDataProvider")
    public void testSubtraction(double a, double b, double expected) {
        logger.info("Running testMethod1 in thread: " + Thread.currentThread().getName());
        assertEquals(calculator.subtract(a, b), expected, 0.000001);
    }

    @Test
    public void testSubtractionWithNaN() {
        logger.info("Running testMethod1 in thread: " + Thread.currentThread().getName());
        assertEquals(calculator.subtract(Double.NaN, 5), Double.NaN);
    }

    @DataProvider(name = "subtractionExceptionDataProvider")
    public Object[][] subtractionExceptionDataProvider() {
        return new Object[][] {
                {Double.MAX_VALUE, -1.0},
                {-Double.MAX_VALUE, 1.0},
                {Double.NEGATIVE_INFINITY, 5}
        };
    }

    @Test(dataProvider = "subtractionExceptionDataProvider", expectedExceptions = ArithmeticException.class, expectedExceptionsMessageRegExp = "Overflow in subtract")
    public void testExceptionOnSubtraction(double a, double b) {
        logger.info("Running testMethod1 in thread: " + Thread.currentThread().getName());
        calculator.subtract(a, b);
    }

    @DataProvider(name = "multiplicationDataProvider")
    public Object[][] multiplicationDataProvider() {
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

    @Test(dataProvider = "multiplicationDataProvider")
    public void testMultiplication(double a, double b, double expected) {
        logger.info("Running testMethod1 in thread: " + Thread.currentThread().getName());
        assertEquals(calculator.multiply(a, b), expected, 0.000001);
    }

    @DataProvider(name = "multiplicationExceptionDataProvider")
    public Object[][] multiplicationExceptionDataProvider() {
        return new Object[][] {
                {Double.MAX_VALUE, 2.0},
                {-Double.MAX_VALUE, 2.0}
        };
    }

    @Test(dataProvider = "multiplicationExceptionDataProvider", expectedExceptions = ArithmeticException.class, expectedExceptionsMessageRegExp = "Overflow in multiply")
    public void testExceptionOnMultiplication(double a, double b) {
        logger.info("Running testMethod1 in thread: " + Thread.currentThread().getName());
        calculator.multiply(a, b);
    }

    @DataProvider(name = "divisionDataProvider")
    public Object[][] divisionDataProvider() {
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

    @Test(dataProvider = "divisionDataProvider")
    public void testDivision(double a, double b, double expected) {
        logger.info("Running testMethod1 in thread: " + Thread.currentThread().getName());
        assertEquals(calculator.divide(a, b), expected, 0.000001);
    }

    @Test(expectedExceptions = ArithmeticException.class, expectedExceptionsMessageRegExp = "Division by zero is not allowed")
    public void testDivisionByZero() {
        logger.info("Running testMethod1 in thread: " + Thread.currentThread().getName());
        calculator.divide(1, 0);
    }

    @Test(expectedExceptions = ArithmeticException.class, expectedExceptionsMessageRegExp = "Overflow or invalid operation in divide")
    public void testDivideOverflow() {
        logger.info("Running testMethod1 in thread: " + Thread.currentThread().getName());
        calculator.divide(Double.MAX_VALUE, Double.MIN_VALUE);
    }

    @DataProvider(name = "powerDataProvider")
    public Object[][] powerDataProvider() {
        return new Object[][]{
                {2, 3, 8.0},
                {5, 0, 1.0},
                {0, 3, 0.0},
                {-2, 3, -8.0},
                {2, -3, 1.0/8.0},
                {-2, -3, -1.0/8.0},
                {5, 1, 5.0},
                {1, 100, 1.0},
                {1e150, 2, 1e300}
                // Тесты на исключения и другие специальные случаи лучше оставить в отдельных тестах без DataProvider
        };
    }

    @Test(dataProvider = "powerDataProvider")
    public void testPower(double base, double exponent, double expected) {
        logger.info("Running testMethod1 in thread: " + Thread.currentThread().getName());
        assertEquals(calculator.power(base, exponent), expected, 1e290);
    }

    @Test(expectedExceptions = ArithmeticException.class, expectedExceptionsMessageRegExp = "Overflow in power operation")
    public void testPowerOverflow() {
        logger.info("Running testMethod1 in thread: " + Thread.currentThread().getName());
        double base = 10.0;
        double exponent = 309.0;
        calculator.power(base, exponent);
    }

    @Test(expectedExceptions = ArithmeticException.class, expectedExceptionsMessageRegExp = "Invalid operation in power")
    public void testPowerInvalidOperation() {
        logger.info("Running testMethod1 in thread: " + Thread.currentThread().getName());
        double base = -4.0;
        double exponent = 0.5;
        calculator.power(base, exponent);
    }

    @DataProvider(name = "sqrtDataProvider")
    public Object[][] sqrtDataProvider() {
        return new Object[][]{
                {4, 2.0},
                {0, 0.0},
                {1, 1.0},
                {2.25, 1.5},
                {1e10, 1e5},
                {1e-6, 1e-3}
        };
    }

    @Test(dataProvider = "sqrtDataProvider")
    public void testSqrt(double input, double expected) {
        logger.info("Running testMethod1 in thread: " + Thread.currentThread().getName());
        assertEquals(calculator.sqrt(input), expected, 0.000001);
    }

    @Test(expectedExceptions = ArithmeticException.class,
            expectedExceptionsMessageRegExp = "Cannot calculate the square root of a negative number")
    public void testSqrtNegativeNumber() {
        logger.info("Running testMethod1 in thread: " + Thread.currentThread().getName());
        calculator.sqrt(-4.0);
    }

    @DataProvider(name = "factorialDataProvider")
    public Object[][] factorialDataProvider() {
        return new Object[][]{
                {5, 120.0},
                {0, 1.0},
                {1, 1.0},
                {20, 2.43290200817664E18}
        };
    }

    @Test(dataProvider = "factorialDataProvider")
    public void testFactorial(int input, double expected) {
        logger.info("Running testMethod1 in thread: " + Thread.currentThread().getName());
        assertEquals(calculator.factorial(input), expected, 0.00001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Factorial of negative numbers is not defined")
    public void testFactorialOfNegativeNumber() {
        logger.info("Running testMethod1 in thread: " + Thread.currentThread().getName());
        calculator.factorial(-5);
    }

    @Test(expectedExceptions = ArithmeticException.class, expectedExceptionsMessageRegExp = "Factorial value is too large")
    public void testFactorialOfVeryLargeNumber() {
        logger.info("Running testMethod1 in thread: " + Thread.currentThread().getName());
        calculator.factorial(1000);
    }

    @DataProvider(name = "logDataProvider")
    public Object[][] logDataProvider() {
        return new Object[][]{
                {Math.E, 1.0},
                {1.0, 0.0},
                {0.5, -0.693147}
        };
    }

    @Test(dataProvider = "logDataProvider")
    public void testLog(double input, double expected) {
        logger.info("Running testMethod1 in thread: " + Thread.currentThread().getName());
        assertEquals(calculator.log(input), expected, 0.000001);
    }

    @DataProvider(name = "logExceptionDataProvider")
    public Object[][] logExceptionDataProvider() {
        return new Object[][]{
                {-5.0},
                {0.0}
        };
    }

    @Test(dataProvider = "logExceptionDataProvider", expectedExceptions = ArithmeticException.class, expectedExceptionsMessageRegExp = "Logarithm of non-positive values is not defined")
    public void testLogExceptions(double input) {
        logger.info("Running testMethod1 in thread: " + Thread.currentThread().getName());
        calculator.log(input);
    }

    @DataProvider(name = "logComparisonDataProvider")
    public Object[][] logComparisonDataProvider() {
        return new Object[][]{
                {1e10, true},
                {1e-6, false}
        };
    }

    @Test(dataProvider = "logComparisonDataProvider")
    public void testLogComparison(double input, boolean expectedResult) {
        logger.info("Running testMethod1 in thread: " + Thread.currentThread().getName());
        if (expectedResult) {
            assertTrue(calculator.log(input) > 0);
        } else {
            assertTrue(calculator.log(input) < 0);
        }
    }

}
