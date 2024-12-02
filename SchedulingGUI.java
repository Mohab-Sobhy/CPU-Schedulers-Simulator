import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SchedulingGUI extends JFrame {

    private ArrayList<Process> processLogs;

    public SchedulingGUI( ArrayList<Process> processLogs , List<Process> processesInformation ) {
        this.processLogs = processLogs;
        setTitle("CPU Scheduling Graph");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top section: Graph
        JPanel graphPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGraph(g);
            }
        };
        graphPanel.setPreferredSize(new Dimension(600, 300));
        graphPanel.setBackground(Color.WHITE);
        add(graphPanel, BorderLayout.CENTER);

        // Right section: Process Info
        JPanel processInfoPanel = new JPanel();
        processInfoPanel.setLayout(new GridLayout(0, 1));
        processInfoPanel.setPreferredSize(new Dimension(200, 300));
        processInfoPanel.setBackground(Color.DARK_GRAY);

        JLabel titleLabel = new JLabel("Processes Information", JLabel.CENTER);
        titleLabel.setForeground(Color.RED);
        processInfoPanel.add(titleLabel);

        for (Process process : processesInformation) {
            if (process != null) {
                JLabel processLabel = new JLabel(
                        "Name: " + process.getName() + " | Arrival Time: " + process.getArrivalTime() + " | Priority: " + process.getPriority() + " | Color: " + process.getColor()
                );
                processLabel.setForeground(Color.WHITE);
                processInfoPanel.add(processLabel);
            }
        }
        add(processInfoPanel, BorderLayout.EAST);

        // Bottom section: Statistics
        JPanel statsPanel = new JPanel();
        statsPanel.setPreferredSize(new Dimension(800, 100));
        statsPanel.setBackground(Color.BLACK);

        JLabel statsLabel = new JLabel("Average Waiting Time: fake number  | Average Turnaround Time: fake number");
        statsLabel.setForeground(Color.RED);
        statsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statsPanel.add(statsLabel);

        add(statsPanel, BorderLayout.SOUTH);
    }

    private void drawGraph(Graphics g) {
        int xStart = 20; // الموضع الأفقي الأولي
        int rowHeight = 30; // ارتفاع كل سطر
        int unitWidth = 40; // عرض كل وحدة
        int yStart = 50; // الموضع العمودي الأولي

        // تحديد موقع السطر لكل عملية بناءً على اسمها
        java.util.Map<String, Integer> processRows = new java.util.HashMap<>();
        int currentRow = 0;

        for (Process process : processLogs) {
            String processName = process != null ? process.getName() : "Idle";
            // تحديد السطر للعملية
            if (!processRows.containsKey(processName)) {
                processRows.put(processName, currentRow++);
            }

            int y = yStart + (processRows.get(processName) * rowHeight); // الموضع العمودي بناءً على العملية
            int x = xStart; // الموضع الأفقي يبدأ من اليسار

            if (process != null) {
                g.setColor(parseColor(process.getColor())); // تحديد لون العملية
                g.fillRect(x, y, unitWidth, rowHeight - 10); // رسم مستطيل للوحدة
                g.setColor(Color.BLACK); // رسم الإطار
                g.drawRect(x, y, unitWidth, rowHeight - 10);
                g.drawString(process.getName(), x + 5, y + 20); // كتابة اسم العملية
            } else {
                // وقت الخمول (Idle)
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(x, y, unitWidth, rowHeight - 10);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, unitWidth, rowHeight - 10);
                g.drawString("Idle", x + 5, y + 20);
            }

            // تحريك الموضع الأفقي للوحدة التالية
            xStart += unitWidth;
        }
    }



    // دالة لتحليل النص إلى لون
    private Color parseColor(String colorStr) {
        try {
            switch (colorStr.toLowerCase()) {
                case "red":
                    return Color.RED;
                case "green":
                    return Color.GREEN;
                case "blue":
                    return Color.BLUE;
                case "yellow":
                    return Color.YELLOW;
                case "black":
                    return Color.BLACK;
                case "white":
                    return Color.WHITE;
                default:
                    // افترض أن اللون بصيغة Hex (مثل #FF0000)
                    return Color.decode(colorStr);
            }
        } catch (NumberFormatException e) {
            // إذا كان اللون غير معروف، استخدم لونًا افتراضيًا
            return Color.GRAY;
        }
    }

}