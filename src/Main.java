import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private static final JLabel topLabel = new JLabel("", SwingConstants.RIGHT);
    private static final JLabel bottomLabel = new JLabel("", SwingConstants.RIGHT);
    private static String currentInput = "", expression = "", operator = "", firstOperand = "";

    public static void main(String[] args) {
        GraphicsFrame frame = new GraphicsFrame();
        int screenWidth = frame.getScreenWidth();
        int screenHeight = frame.getScreenHeight();

        JPanel outputContainer = createVerticalContainer(screenWidth, 70, 130);
        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setPreferredSize(new Dimension(screenWidth, screenHeight));

        int betaWidth = 100;
        int alphaWidth = screenWidth - betaWidth;

        JPanel alphaContainer = new JPanel(new BorderLayout());
        alphaContainer.setPreferredSize(new Dimension(alphaWidth, screenHeight));

        alphaContainer.add(createGridPanel(1, 3, alphaWidth, 80, Color.BLACK,
                new String[]{"C", "+/-", "<html><b>⌫</b></html>"}, 10, 10, 0, 10), BorderLayout.NORTH);

        alphaContainer.add(createGridPanel(4, 3, alphaWidth, screenHeight - 100, Color.BLACK,
                new String[]{"7", "8", "9", "4", "5", "6", "1", "2", "3", "%", "0", "."}, 10, 10, 10, 10), BorderLayout.CENTER);

        JPanel betaContainer = new JPanel(new BorderLayout());
        betaContainer.setPreferredSize(new Dimension(betaWidth, screenHeight));
        betaContainer.add(createGridPanel(5, 1, betaWidth, screenHeight, Color.BLACK,
                new String[]{"÷", "x", "-", "+", "="}, 10, 0, 10, 10), BorderLayout.CENTER);

        mainContainer.add(alphaContainer, BorderLayout.CENTER);
        mainContainer.add(betaContainer, BorderLayout.EAST);

        frame.add(outputContainer, BorderLayout.NORTH);
        frame.add(mainContainer, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    public static JPanel createVerticalContainer(int width, int h1, int h2) {
        JPanel container = new JPanel(new BorderLayout());
        container.setPreferredSize(new Dimension(width, h1 + h2));

        topLabel.setForeground(Color.WHITE);
        topLabel.setFont(new Font("Arial", Font.BOLD, 25));
        topLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        bottomLabel.setForeground(Color.WHITE);
        bottomLabel.setFont(new Font("Arial", Font.BOLD, 50));
        bottomLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        JPanel topPanel = SwingUtility.createColoredPanel(width, h1, Color.BLACK);
        topPanel.setLayout(new BorderLayout());
        topPanel.add(topLabel, BorderLayout.CENTER);

        JPanel bottomPanel = SwingUtility.createColoredPanel(width, h2, Color.BLACK);
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(bottomLabel, BorderLayout.CENTER);

        container.add(topPanel, BorderLayout.NORTH);
        container.add(bottomPanel, BorderLayout.SOUTH);

        return container;
    }
    public static JPanel createGridPanel(int rows, int cols, int width, int height, Color bg, String[] labels, int top, int left, int bottom, int right) {
        JPanel panel = new JPanel(new GridLayout(rows, cols, 10, 10));
        panel.setPreferredSize(new Dimension(width, height));
        panel.setBackground(bg);
        panel.setBorder(new EmptyBorder(top, left, bottom, right));

        for (String label : labels) {
            JButton btn = new JButton(label);
            Color bgColor = Color.WHITE, fgColor = Color.BLACK;

            if (label.equals("C")) {
                bgColor = Color.RED; fgColor = Color.WHITE;
            } else if ("+−x÷=+/-<html><b>⌫</b></html>".contains(label)) {
                bgColor = new Color(0xFEBA17); fgColor = Color.WHITE;
            } else if (label.equals("<html><b>⌫</b></html>")) {
                btn.setText("<html><b>⌫</b></html>");
            }

            btn = SwingUtility.styleButton(btn.getText(), bgColor, fgColor, 35, true);
            btn.addActionListener(new ButtonListener(label));
            panel.add(btn);
        }
        return panel;
    }
    private static class ButtonListener implements ActionListener {
        String value;
        public ButtonListener(String value) { this.value = value; }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (value) {
                case "C": reset(); break;
                case "<html><b>⌫</b></html>":
                    if (!currentInput.isEmpty()) {
                        currentInput = currentInput.substring(0, currentInput.length() - 1);
                        bottomLabel.setText(currentInput);
                    }
                    break;
                case "=":
                    if (canEvaluate()) {
                        double num1 = Double.parseDouble(firstOperand);
                        double num2 = Double.parseDouble(currentInput);
                        double result = 0;
                        boolean error = false;
                        switch (operator) {
                            case "+": result = num1 + num2; break;
                            case "-": result = num1 - num2; break;
                            case "x": result = num1 * num2; break;
                            case "÷":
                                if (num2 == 0) {
                                    bottomLabel.setText("Error");
                                    error = true;
                                } else {
                                    result = num1 / num2;
                                }
                                break;
                            case "%":
                                result = num1 * (num2 / 100);
                                break;
                        }
                        if (!error) {
                            topLabel.setText(expression + currentInput);
                            bottomLabel.setText(String.valueOf(result));
                            currentInput = String.valueOf(result);
                        }
                        resetState();
                    }
                    break;
                case "+": case "-": case "x": case "÷": case "%":
                    if (!currentInput.isEmpty()) {
                        operator = value;
                        firstOperand = currentInput;
                        expression = currentInput + " " + value + " ";
                        topLabel.setText(expression);
                        currentInput = "";
                        bottomLabel.setText("");
                    }
                    break;
                case "+/-":
                    if (!currentInput.isEmpty()) {
                        currentInput = currentInput.startsWith("-") ? currentInput.substring(1) : "-" + currentInput;
                        bottomLabel.setText(currentInput);
                    }
                    break;
                default:
                    currentInput += value;
                    bottomLabel.setText(currentInput);
            }
        }
        private boolean canEvaluate() {
            return !currentInput.isEmpty() && !operator.isEmpty() && !firstOperand.isEmpty();
        }
        private void reset() {
            currentInput = expression = operator = firstOperand = "";
            topLabel.setText("");
            bottomLabel.setText("");
        }
        private void resetState() {
            operator = expression = firstOperand = "";
        }
    }
}