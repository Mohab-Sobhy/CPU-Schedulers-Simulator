import javax.swing.*;


public class CPUSchedulingGUI {
    private JFrame window;

    public CPUSchedulingGUI(ProcessorLogs processorLogs) {
        window = new JFrame();
        window.setTitle("CPU Scheduling Graph");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// to finish program when click X
        window.setSize(800, 500);
        window.setLocationRelativeTo(null);// to center the window
    }

    public void show() {
        window.setVisible(true);
    }

}