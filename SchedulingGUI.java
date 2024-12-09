import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class SchedulingGUI extends JFrame {

    private ArrayList<Process> processLogs;

    public SchedulingGUI(ArrayList<Process> processLogs, List<Process> processesInformation) {
        this.processLogs = processLogs;

        if(processLogs.getLast() == null){
            processLogs.removeLast();
        }

        setTitle("CPU Scheduling Graph");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel graphPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGraph(g);
            }
        };

        int unitWidth = 40; // Width of each unit
        int totalWidth = processLogs.size() * unitWidth; // Total width based on the number of processes
        graphPanel.setPreferredSize(new Dimension(totalWidth, 300)); // Set preferred size dynamically
        graphPanel.setBackground(Color.DARK_GRAY);

        // Wrap the graphPanel in a JScrollPane for horizontal scrolling
        JScrollPane graphScrollPane = new JScrollPane(graphPanel);
        graphScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        graphScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        add(graphScrollPane, BorderLayout.CENTER);

        // Right section: Process Info
        JPanel processInfoPanel = new JPanel();
        processInfoPanel.setLayout(new GridLayout(0, 1));
        processInfoPanel.setPreferredSize(new Dimension(340, 300));
        processInfoPanel.setBackground(Color.black);

        JLabel titleLabel = new JLabel("Processes Information", JLabel.CENTER);
        titleLabel.setForeground(Color.WHITE);
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

        setVisible(true);
    }

    private void drawGraph(Graphics g) {
        int xStart = 20; // Starting horizontal position
        int rowHeight = 50; // Height of each row
        int unitWidth = 30; // Width of each unit
        int yStart = 20; // Starting vertical position

        // Mapping each process to a row based on its name
        Map<String, Integer> processRows = new HashMap<>();
        int currentRow = 0;

        for (Process process : processLogs) {
            String processName = process != null ? process.getName() : "Idle";
            // Assign a row for each process
            if (!processRows.containsKey(processName)) {
                processRows.put(processName, currentRow++);
            }

            int y = yStart + (processRows.get(processName) * rowHeight); // Vertical position based on the process
            int x = xStart; // Horizontal position starts from the left

            if (process != null) {
                g.setColor(parseColor(process.getColor())); // Set color for the process
                g.fillRect(x, y, unitWidth, rowHeight - 10); // Draw the unit rectangle
                g.setColor(Color.BLACK); // Draw the border
                g.drawRect(x, y, unitWidth, rowHeight - 10);
                g.drawString(process.getName(), x + 5, y + 15); // Draw the process name
            } else {
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(x, y, unitWidth, rowHeight - 10);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, unitWidth, rowHeight - 10);
                g.drawString("Idle", x + 5, y + 15);
            }

            xStart += unitWidth;
        }
    }

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
                case "purple":
                    return Color.MAGENTA;
                case "cyan":
                    return Color.CYAN;
                case "orange":
                    return Color.ORANGE;
                case "pink":
                    return Color.PINK;
                default:
                    return Color.decode(colorStr);
            }
        } catch (NumberFormatException e) {
            return Color.GRAY;
        }
    }

}
