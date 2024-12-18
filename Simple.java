import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {
    // Create UI components
    private JTextField input1;
    private JTextField input2;
    private JTextField result;
    private JButton addButton;
    private JButton subtractButton;
    private JButton multiplyButton;
    private JButton divideButton;

    public Calculator() {
        // Set up the frame
        setTitle("Simple Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Initialize components
        input1 = new JTextField(10);
        input2 = new JTextField(10);
        result = new JTextField(10);
        result.setEditable(false);

        addButton = new JButton("+");
        subtractButton = new JButton("-");
        multiplyButton = new JButton("*");
        divideButton = new JButton("/");

        // Add action listeners
        addButton.addActionListener(this);
        subtractButton.addActionListener(this);
        multiplyButton.addActionListener(this);
        divideButton.addActionListener(this);

        // Add components to the frame
        add(new JLabel("Input 1:"));
        add(input1);
        add(new JLabel("Input 2:"));
        add(input2);
        add(addButton);
        add(subtractButton);
        add(multiplyButton);
        add(divideButton);
        add(new JLabel("Result:"));
        add(result);

        // Set the frame to be visible
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            double num1 = Double.parseDouble(input1.getText());
            double num2 = Double.parseDouble(input2.getText());
            double res = 0;

            if (e.getSource() == addButton) {
                res = num1 + num2;
            } else if (e.getSource() == subtractButton) {
                res = num1 - num2;
            } else if (e.getSource() == multiplyButton) {
                res = num1 * num2;
            } else if (e.getSource() == divideButton) {
                if (num2 != 0) {
                    res = num1 / num2;
                } else {
                    result.setText("Error: Div by 0");
                    return;
                }
            }

            result.setText(String.valueOf(res));
        } catch (NumberFormatException ex) {
            result.setText("Error: Invalid input");
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
