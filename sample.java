class Tables {
  public static void tablesOf(int n) {
    for (int i = 1; i <= 12; i++)
      System.out.println(n + " x " + i + " = " + (n * i));
  }

  public static void main(String[] args) {
    System.out.println(" === FIVE TABLES ===");
    tablesOf(5);
  }
}
