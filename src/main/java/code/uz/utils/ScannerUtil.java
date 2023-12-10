package code.uz.utils;

import java.util.Scanner;

public class ScannerUtil {
    public static int getOption() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter action: ");
            try {
                int n = scanner.nextInt();
                return n;
            } catch (RuntimeException e) {
                System.out.println("Hato kirildi. Son kiriting Mazgi.");
            }
        }
    }
}
