import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5999)) {
            System.out.println("Server is running...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());

            while (true) {
                Object obj = in.readObject();

                if (obj instanceof String && obj.equals("Q")) {
                    System.out.println("Client requested to close the connection.");
                    break;
                }

                if (obj instanceof Shape) {
                    Shape shape = (Shape) obj;
                    double area = shape.calculateArea();
                    System.out.println("Received: " + shape);
                    System.out.println("Calculated area: " + area);

                    out.writeObject(area);
                }
            }

            clientSocket.close();
            System.out.println("Server shut down.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

abstract class Shape implements Serializable {
    abstract double calculateArea();
}

class Circle extends Shape {
    private final double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public String toString() {
        return "Circle with radius " + radius;
    }
}