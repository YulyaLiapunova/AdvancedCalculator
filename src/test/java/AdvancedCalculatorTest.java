package test.java;

import main.java.AdvancedCalculator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class AdvancedCalculatorTest {
    private AdvancedCalculator calculator;

    @BeforeMethod
    public void setup() {
        calculator = new AdvancedCalculator();
    }

    @Test
    public void testAddition() {
        assertEquals(calculator.add(2, 3), 5.0);
    }

    @Test
    public void testAdditionOfNegativeNumbers() {
        assertEquals(calculator.add(-5, -3), -8.0);
    }

    @Test
    public void testAdditionOfMixedSignNumbers() {
        assertEquals(calculator.add(5, -3), 2.0);
    }

    @Test
    public void testAdditionWithZero() {
        assertEquals(calculator.add(5, 0), 5.0);
    }

    @Test
    public void testAdditionOfLargeNumbers() {
        double largeNumber = Double.MAX_VALUE / 2;
        assertEquals(calculator.add(largeNumber, largeNumber), Double.MAX_VALUE);
    }

    @Test
    public void testAdditionOfSmallNumbers() {
        double smallNumber = Double.MIN_VALUE;
        assertEquals(calculator.add(smallNumber, smallNumber), smallNumber * 2);
    }

    @Test
    public void testAdditionOfDecimals() {
        assertEquals(calculator.add(5.5, 3.3), 8.8, 0.000001);  // учет небольшой погрешности
    }

    @Test(expectedExceptions = ArithmeticException.class, expectedExceptionsMessageRegExp = "Overflow in add")
    public void testOverflowOnAddition() {
        calculator.add(Double.MAX_VALUE, 1.0);
    }

    @Test(expectedExceptions = ArithmeticException.class, expectedExceptionsMessageRegExp = "Overflow in add")
    public void testUnderflowOnAddition() {
        calculator.add(-Double.MAX_VALUE, -1.0);
    }

    @Test
    public void testAdditionWithNaN() {
        assertEquals(calculator.add(Double.NaN, 5), Double.NaN);
    }

    @Test(expectedExceptions = ArithmeticException.class, expectedExceptionsMessageRegExp = "Overflow in add")
    public void testAdditionWithInfinity() {
        calculator.add(Double.POSITIVE_INFINITY, 5);
        calculator.add(Double.NEGATIVE_INFINITY, 5);
    }

    @Test(expectedExceptions = ArithmeticException.class, expectedExceptionsMessageRegExp = "Overflow in add")
    public void testLossOfPrecision() {
        double largeNumber = 1e10;
        double smallNumber = 1e-10;
        // Здесь мы предполагаем, что потеря точности происходит, и результат будет равен largeNumber
        calculator.add(largeNumber, smallNumber);
    }

    @Test
    public void testSubtraction() {
        assertEquals(calculator.subtract(5, 3), 2.0);
    }

    @Test
    public void testSubtractionOfNegativeNumbers() {
        assertEquals(calculator.subtract(-5, -3), -2.0);
    }

    @Test
    public void testSubtractionOfMixedSignNumbers1() {
        assertEquals(calculator.subtract(-5, 3), -8.0);
    }

    @Test
    public void testSubtractionOfMixedSignNumbers2() {
        assertEquals(calculator.subtract(5, -3), 8.0);
    }

    @Test
    public void testSubtractionFromZero() {
        assertEquals(calculator.subtract(0, 3), -3.0);
    }

    @Test
    public void testSubtractionOfZero() {
        assertEquals(calculator.subtract(5, 0), 5.0);
    }

    @Test
    public void testSubtractionOfLargeNumbers() {
        double largeNumber = Double.MAX_VALUE / 2;
        assertEquals(calculator.subtract(largeNumber + largeNumber, largeNumber), largeNumber);
    }

    @Test
    public void testSubtractionOfSmallNumbers() {
        double smallNumber = Double.MIN_VALUE;
        assertEquals(calculator.subtract(smallNumber + smallNumber, smallNumber), smallNumber);
    }

    @Test
    public void testSubtractionOfDecimals() {
        assertEquals(calculator.subtract(5.5, 3.3), 2.2, 0.000001);  // учет небольшой погрешности
    }

    @Test(expectedExceptions = ArithmeticException.class, expectedExceptionsMessageRegExp = "Overflow in subtract")
    public void testOverflowOnPositiveSubtraction() {
        calculator.subtract(Double.MAX_VALUE, -1.0);
    }

    @Test(expectedExceptions = ArithmeticException.class, expectedExceptionsMessageRegExp = "Overflow in subtract")
    public void testOverflowOnNegativeSubtraction() {
        calculator.subtract(-Double.MAX_VALUE, 1.0);
    }

    @Test
    public void testSubtractionWithNaN() {
        assertEquals(calculator.subtract(Double.NaN, 5), Double.NaN);
    }

    @Test(expectedExceptions = ArithmeticException.class, expectedExceptionsMessageRegExp = "Overflow in subtract")
    public void testSubtractionWithInfinity() {
        calculator.subtract(Double.POSITIVE_INFINITY, 5);
        calculator.subtract(Double.NEGATIVE_INFINITY, 5);
    }

    @Test
    public void testMultiplication() {
        assertEquals(calculator.multiply(2, 3), 6.0);
    }

    @Test
    public void testMultiplicationOfNegativeNumbers() {
        assertEquals(calculator.multiply(-5, -3), 15.0);
    }

    @Test
    public void testMultiplicationOfMixedSignNumbers() {
        assertEquals(calculator.multiply(5, -3), -15.0);
    }

    @Test
    public void testMultiplicationWithZero() {
        assertEquals(calculator.multiply(5.0, 0.0), 0.0);
        assertEquals(calculator.multiply(0.0, 5.0), 0.0);
        assertEquals(calculator.multiply(0.0, 0.0), 0.0);
    }

    @Test(expectedExceptions = ArithmeticException.class, expectedExceptionsMessageRegExp = "Overflow in multiply")
    public void testOverflowOnPositiveMultiplication() {
        calculator.multiply(Double.MAX_VALUE, 2.0);
    }

    @Test(expectedExceptions = ArithmeticException.class, expectedExceptionsMessageRegExp = "Overflow in multiply")
    public void testOverflowOnNegativeMultiplication() {
        calculator.multiply(-Double.MAX_VALUE, 2.0);
    }

    @Test
    public void testMultiplicationOfDecimals() {
        assertEquals(calculator.multiply(5.5, 2.0), 11.0, 0.000001);  // учет небольшой погрешности
    }

    @Test
    public void testBasicDivision() {
        Assert.assertEquals(calculator.divide(6, 3), 2.0);
    }

    @Test
    public void testDivisionOfNegativeNumbers() {
        Assert.assertEquals(calculator.divide(-6, -3), 2.0);
    }

    @Test
    public void testDivisionOfMixedSignNumbers1() {
        Assert.assertEquals(calculator.divide(6, -3), -2.0);
    }

    @Test
    public void testDivisionOfMixedSignNumbers2() {
        Assert.assertEquals(calculator.divide(-6, 3), -2.0);
    }

    @Test(expectedExceptions = ArithmeticException.class, expectedExceptionsMessageRegExp = "Division by zero is not allowed")
    public void testDivisionByZero() {
        calculator.divide(1, 0);
    }

    @Test
    public void testZeroDividedByNumber() {
        Assert.assertEquals(calculator.divide(0, 3), 0.0);
    }

    @Test
    public void testDivisionOfDecimals() {
        Assert.assertEquals(calculator.divide(5.5, 2.0), 2.75, 0.000001);  // учет небольшой погрешности
    }

    @Test
    public void testSmallNumberDividedByLargeNumber() {
        Assert.assertEquals(calculator.divide(1, 1000000), 0.000001, 0.000001);  // учет небольшой погрешности
    }

    @Test(expectedExceptions = ArithmeticException.class, expectedExceptionsMessageRegExp = "Overflow or invalid operation in divide")
    public void testDivideOverflow() {
        calculator.divide(Double.MAX_VALUE, Double.MIN_VALUE);
    }

    @Test
    public void testPower() {
        Assert.assertEquals(calculator.power(2, 3), 8.0);
    }

    @Test
    public void testPowerWithZeroExponent() {
        Assert.assertEquals(calculator.power(5, 0), 1.0);
    }


    @Test
    public void testZeroBasePower() {
        Assert.assertEquals(calculator.power(0, 3), 0.0);
    }

    @Test
    public void testNegativeBasePower() {
        Assert.assertEquals(calculator.power(-2, 3), -8.0);
    }

    @Test
    public void testPowerWithNegativeExponent() {
        Assert.assertEquals(calculator.power(2, -3), 1.0/8.0, 0.000001);  // учет небольшой погрешности
    }

    @Test
    public void testNegativeBaseWithNegativeExponent() {
        Assert.assertEquals(calculator.power(-2, -3), -1.0/8.0, 0.000001);  // учет небольшой погрешности
    }

    @Test
    public void testPowerWithExponentOne() {
        Assert.assertEquals(calculator.power(5, 1), 5.0);
    }

    @Test
    public void testOneBasePower() {
        Assert.assertEquals(calculator.power(1, 100), 1.0);
    }

    @Test
    public void testLargeBasePower() {
        double largeBase = 1e150;
        Assert.assertEquals(calculator.power(largeBase, 2), 1e300, 1e290);  // учет погрешности, которая может возникать из-за переполнения
    }

    @Test(expectedExceptions = ArithmeticException.class, expectedExceptionsMessageRegExp = "Overflow in power operation")
    public void testPowerOverflow() {
        double base = 10.0;
        double exponent = 309.0; // Это значение может вызвать переполнение для базы 10
        calculator.power(base, exponent);
    }

    @Test(expectedExceptions = ArithmeticException.class, expectedExceptionsMessageRegExp = "Invalid operation in power")
    public void testPowerInvalidOperation() {
        double base = -4.0;
        double exponent = 0.5; // Корень четвертой степени из отрицательного числа - это комплексное число в действительных числах
        calculator.power(base, exponent);
    }

    @Test
    public void testBasicSqrt() {
        Assert.assertEquals(calculator.sqrt(4), 2.0, 0.000001);
    }

    @Test
    public void testSqrtOfZero() {
        Assert.assertEquals(calculator.sqrt(0), 0.0, 0.000001);
    }

    @Test
    public void testSqrtOfOne() {
        Assert.assertEquals(calculator.sqrt(1), 1.0, 0.000001);
    }

    @Test
    public void testSqrtOfDecimals() {
        Assert.assertEquals(calculator.sqrt(2.25), 1.5, 0.000001);  // учет небольшой погрешности
    }

    @Test
    public void testSqrtOfLargeNumber() {
        double largeNumber = 1e10;
        Assert.assertEquals(calculator.sqrt(largeNumber), 1e5, 1e-1);  // учет небольшой погрешности
    }

    @Test
    public void testSqrtOfSmallNumber() {
        double smallNumber = 1e-6;
        Assert.assertEquals(calculator.sqrt(smallNumber), 1e-3, 1e-9);  // учет небольшой погрешности
    }

    @Test(expectedExceptions = ArithmeticException.class,
            expectedExceptionsMessageRegExp = "Cannot calculate the square root of a negative number")
    public void testSqrtNegativeNumber() {
        calculator.sqrt(-4.0);
    }

    @Test
    public void testFactorial() {
        Assert.assertEquals(calculator.factorial(5), 120.0);
    }

    @Test
    public void testFactorialOfZero() {
        Assert.assertEquals(calculator.factorial(0), 1.0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Factorial of negative numbers is not defined")  // или другое соответствующее исключение
    public void testFactorialOfNegativeNumber() {
        calculator.factorial(-5);
    }

    @Test
    public void testFactorialOfOne() {
        Assert.assertEquals(calculator.factorial(1), 1.0);
    }

    @Test(expectedExceptions = ArithmeticException.class, expectedExceptionsMessageRegExp = "Factorial value is too large") // или другое исключение, связанное с переполнением
    public void testFactorialOfVeryLargeNumber() {
        calculator.factorial(1000);  // Это значение может быть разным в зависимости от реализации и предельных значений, которые может обработать ваша функция.
    }

    @Test
    public void testFactorialOfMaxSupportedNumber() {
        // Предполагая, что ваша функция поддерживает числа до 20 включительно
        double expected = 2.43290200817664E18; // это 2432902008176640000 в научной записи
        double actual = calculator.factorial(20); // предполагая, что 20 - это максимальное поддерживаемое число
        double delta = 0.00001; // допустимая погрешность
        assertEquals(actual, expected, delta);
    }

    @Test
    public void testBasicLog() {
        Assert.assertEquals(calculator.log(Math.E), 1.0, 0.000001); // логарифм единицы по основанию e равен 1
    }

    @Test
    public void testLogOfOne() {
        Assert.assertEquals(calculator.log(1), 0.0, 0.000001);
    }

    @Test(expectedExceptions = ArithmeticException.class, expectedExceptionsMessageRegExp = "Logarithm of non-positive values is not defined")  // или другое соответствующее исключение
    public void testLogOfNegativeNumber() {
        calculator.log(-5);
    }

    @Test(expectedExceptions = ArithmeticException.class, expectedExceptionsMessageRegExp = "Logarithm of non-positive values is not defined")  // или другое соответствующее исключение
    public void testLogOfZero() {
        calculator.log(0);
    }

    @Test
    public void testLogOfDecimals() {
        Assert.assertEquals(calculator.log(0.5), -0.693147, 0.000001);  // учет небольшой погрешности
    }

    @Test
    public void testLogOfLargeNumber() {
        double largeNumber = 1e10;
        // Значение может варьироваться, но важно проверить, что функция корректно обрабатывает большие числа
        Assert.assertTrue(calculator.log(largeNumber) > 0);
    }

    @Test
    public void testLogOfSmallNumber() {
        double smallNumber = 1e-6;
        // Значение может варьироваться, но важно проверить, что функция корректно обрабатывает маленькие числа
        Assert.assertTrue(calculator.log(smallNumber) < 0);
    }

}
