import javax.swing.SwingUtilities;

class Main {
    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            createAndShow();
        });
    }

    private static void createAndShow() {
        MainFrame frame = new MainFrame("Calculator");
        frame.setVisible(true);
    }
}

