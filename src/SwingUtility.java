import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SwingUtility {
    public static JPanel createColoredPanel(int width, int height, Color color) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(width, height));
        panel.setBackground(color);
        return panel;
    }
    public static JButton styleButton(String text, Color bg, Color fg, int fontSize, boolean buttonEffects) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

                super.paintComponent(g);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(getBackground());
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);

                g2.dispose();
            }
        };

        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(false);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("Arial", Font.BOLD, fontSize));

        addButtonEffects(btn, buttonEffects);

        return btn;
    }

    public static void addButtonEffects(JButton btn, boolean enableEffects) {
        if (!enableEffects) return;

        final Color baseColor = btn.getBackground();
        final boolean[] hovered = {false};
        final boolean[] pressed = {false};

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovered[0] = true;
                btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                btn.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovered[0] = false;
                pressed[0] = false;
                btn.setCursor(Cursor.getDefaultCursor());
                btn.setBackground(baseColor);
                btn.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                pressed[0] = true;
                btn.setBackground(baseColor.darker().darker());
                btn.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                pressed[0] = false;
                if (hovered[0]) {
                    btn.setBackground(baseColor.darker());
                } else {
                    btn.setBackground(baseColor);
                }
                btn.repaint();
            }
        });
    }
}
