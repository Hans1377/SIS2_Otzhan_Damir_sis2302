import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class MyClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5999)) {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("Enter 3D object name (or 'Q' to quit): ");
                String shapeType = scanner.nextLine();

                if (shapeType.equalsIgnoreCase("Q")) {
                    out.writeObject("Q");
                    break;
                }

                if (shapeType.equalsIgnoreCase("Circle")) {
                    System.out.print("Enter radius: ");
                    double radius = scanner.nextDouble();
                    scanner.nextLine();
                    out.writeObject(new Circle(radius));
                } else {
                    System.out.println("Unsupported shape type.");
                    continue;
                }

                Object response = in.readObject();
                if (response instanceof Double) {
                    System.out.println("Server message: Area is " + response);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}