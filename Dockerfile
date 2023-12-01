FROM openjdk:11

COPY . /app
WORKDIR /app

# Compile the Java program
RUN javac src/main/java/AdvancedCalculator.java

# Set the command to run your program
CMD ["java", "-classpath", "src", "main.java.AdvancedCalculator"]