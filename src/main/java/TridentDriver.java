import java.util.Scanner;

public class TridentDriver {
  public static void main(String[] args) {
    Scanner ns = new Scanner(System.in);
    while (true) {
      System.out.println("\n\n1. Write \n2. Read \n0. Exit \nYour choice: ");
      switch (ns.nextInt()) {
        case 1:
          System.out.println("\nEnter a record to add: ");
          ns = new Scanner(System.in);
          RecentsTracker.addRecord(ns.nextLine());
          break;

        case 2:
          System.out.println("\nThe records in the file are:");
          RecentsTracker.printRecords();
          break;

        case 0:
          ns.close();
          System.exit(0);

        default:
          break;
      }
    }
  }
}
