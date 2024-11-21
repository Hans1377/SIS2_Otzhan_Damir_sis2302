class ExamChecking extends Thread {
    private static int examSheets = 500; // Общее количество листов
    private final int limit; // Лимит итераций для одного потока

    public ExamChecking(int limit) {
        this.limit = limit;
    }

    @Override
    public void run() {
        for (int i = 0; i < limit; i++) {
            synchronized (ExamChecking.class) {
                if (examSheets <= 0) {
                    System.out.println("There is no any exam sheet left! All papers was already checked!!");
                    return;
                }
                examSheets--;
                System.out.println(getName() + " finished checking, at: " + new java.util.Date() +
                        ", exam sheet count is now " + examSheets);
            }
            try {
                Thread.sleep(100); // Имитация паузы между проверками
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // Создание и запуск потоков
        ExamChecking n1 = new ExamChecking(6);
        n1.setName("Dana");
        ExamChecking n2 = new ExamChecking(6);
        n2.setName("Alibek");
        ExamChecking n3 = new ExamChecking(6);
        n3.setName("Aibat");

        n1.start();
        n2.start();
        n3.start();
    }
}