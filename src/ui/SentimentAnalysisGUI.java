package src.ui;

import javax.swing.*;

import src.puem.SentimentAnalysisClient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SentimentAnalysisGUI extends JFrame {
    private JPanel imagePanel;
    private JTextArea textArea;
    private JButton submitButton;
    private JButton clearButton;
    private JLabel sentimentLabel;
    
    // private JFrame Frame;
    

    public SentimentAnalysisGUI() {
        initializeUI();
        
    }

    private void initializeUI() {
        
        setTitle("Sentiment Analysis GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 550);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        createComponents();
        addComponentsToUI();
        setListeners();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createComponents() {
        sentimentLabel = new JLabel("Sentiment Analysis");
        sentimentLabel.setFont(new Font("Arial", Font.BOLD, 24));
        sentimentLabel.setHorizontalAlignment(JLabel.CENTER);
        
        imagePanel = new JPanel(new FlowLayout());
        JLabel imageLabel = new JLabel();

        imageLabel.setIcon(new ImageIcon(
            new ImageIcon("src/images/NORMAL.jpg")
                .getImage()
                .getScaledInstance(200, 300, Image.SCALE_SMOOTH)
        ));

        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imagePanel.add(imageLabel);
        imagePanel.setBackground(Color.white);

        textArea = new JTextArea(5, 20);
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        textArea.setMargin(new Insets(10, 10, 10, 10));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        submitButton = new JButton("Submit");
        clearButton = new JButton("Clear");
        submitButton.setBackground(Color.LIGHT_GRAY);
        submitButton.setForeground(Color.DARK_GRAY);
    }
    
    private void addComponentsToUI() {
        JPanel inputPanel = new JPanel(new BorderLayout());
        
        inputPanel.add(textArea, BorderLayout.CENTER);
        inputPanel.add(submitButton, BorderLayout.SOUTH);

        add(sentimentLabel, BorderLayout.NORTH);
        add(imagePanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
    }

    private void setListeners() {
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = textArea.getText();
                if (userInput.isEmpty()) {
                    JOptionPane.showMessageDialog(
                        null,
                        "Please enter a text",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    try {
                        SentimentAnalysisClient client = new SentimentAnalysisClient();
                        String sentimentResult = client.getSentiment(userInput).get("label");
                        System.out.println(sentimentResult);
                        if (sentimentResult.equals("pos")) {
                            imagePanel.removeAll();
                            imagePanel.add(new JLabel(new ImageIcon(
                                new ImageIcon("src/images/HAPPY.png")
                                    .getImage()
                                    .getScaledInstance(200, 300, Image.SCALE_SMOOTH)
                            )));
                            imagePanel.revalidate();
                            imagePanel.repaint();
                        } else if (sentimentResult.equals("neg")) {
                            imagePanel.removeAll();
                            imagePanel.add(new JLabel(new ImageIcon(
                                new ImageIcon("src/images/ANGRY.png")
                                    .getImage()
                                    .getScaledInstance(200, 300, Image.SCALE_SMOOTH)
                            )));
                            imagePanel.revalidate();
                            imagePanel.repaint();
                        } else if (sentimentResult.equals("neu")) {
                            imagePanel.removeAll();
                            imagePanel.add(new JLabel(new ImageIcon(
                                new ImageIcon("src/images/SAD.png")
                                    .getImage()
                                    .getScaledInstance(200, 300, Image.SCALE_SMOOTH)
                            )));
                            imagePanel.revalidate();
                            imagePanel.repaint();
                        }
                    } catch (Exception ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearTextArea();
            }
        });
    }

    public void clearTextArea() {
        textArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SentimentAnalysisGUI();
        });
    }
}
