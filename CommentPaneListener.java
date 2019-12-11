
class CommentPaneListener extends Thread {
  @Override
  public void run() {
    try {
      while (true) {
        Trident.status1.setText("Ready.");
        Thread.sleep(20000);
      }
    } catch (InterruptedException strangeException) {
      Trident.ErrorDialog("STATUS_THREAD_KILLED", strangeException);
    } catch (Exception died) {
      died.printStackTrace();
    }
  }
}
