package swingPassGen;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class PasswordGenerator extends JFrame {

	List<String> allowedCharsList = new LinkedList<>();

	JCheckBox uppercaseJcb = new JCheckBox("Use uppercase letters (A, B, C, D, ...)");
	JCheckBox lowercaseJcb = new JCheckBox("Use lowercase letters (a, b, c, d, ...)");
	JCheckBox numbersJcb = new JCheckBox("Use numbers (0, 3, 7, 9, ...)");
	JCheckBox specialCharsJcb = new JCheckBox("Use special characters (*, #, $, _, ...)");
	JLabel lblLength = new JLabel("Length of the password (8 - 32)");
	JTextField lengthField = new JTextField();
	JTextField passwordField = new JTextField();
	JButton generatePasswordButton = new JButton("Generate new password");

	public PasswordGenerator() {

		addLayout();

		addFunctionality();

	}

	public void addLayout() {
		JPanel contentPane = (JPanel) this.getContentPane();
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// NORTH PANEL

		JPanel conditionsPanel = new JPanel(new GridLayout(5, 0, 0, 5));
		conditionsPanel.add(uppercaseJcb);
		conditionsPanel.add(lowercaseJcb);
		conditionsPanel.add(numbersJcb);
		conditionsPanel.add(specialCharsJcb);

		JPanel lengthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		lengthPanel.add(lblLength);
		lengthPanel.add(lengthField);
		lengthField.setColumns(3);
		conditionsPanel.add(lengthPanel);

		add(conditionsPanel, BorderLayout.NORTH);

		// SOUTH PANEL
		JPanel passwordPanel = new JPanel();
		passwordPanel.add(generatePasswordButton);
		passwordPanel.add(passwordField);
		passwordField.setEditable(false);
		passwordField.setColumns(25);

		add(passwordPanel, BorderLayout.SOUTH);

	}

	// Adding functionality to Swing components

	public void addFunctionality() {
		uppercaseJcb.addItemListener((e) -> {
			if (e.getStateChange() == 1)
				addAllUppercaseLetters();
			else
				removeAllUppercaseLetters();
		});

		lowercaseJcb.addItemListener((e) -> {
			if (e.getStateChange() == 1)
				addAllLowercaseLetters();
			else
				removeAllLowercaseLetters();
		});

		numbersJcb.addItemListener((e) -> {
			if (e.getStateChange() == 1)
				addAllNumbers();
			else
				removeAllNumbers();
		});

		specialCharsJcb.addItemListener((e) -> {
			if (e.getStateChange() == 1)
				addSpecialCharacters();
			else
				removeSpecialCharacters();
		});

		generatePasswordButton.addActionListener((e) -> {
			try {
				if (lengthField.getText().equals(""))
					throw new IllegalArgumentException("Enter the wished password length between 8 and 32!");

				int length = Integer.parseInt(lengthField.getText());

				if (length < 8 || length > 32)
					throw new IllegalArgumentException("Enter the wished password length between 8 and 32!");
				if (allowedCharsList.isEmpty())
					throw new IllegalArgumentException("Select at least one checkbox!");

				String password = generatePassword();

				passwordField.setText(password);

			} catch (NumberFormatException excNum) {
				JOptionPane.showMessageDialog(null, "Wrong input!");
			} catch (Exception exc) {
				JOptionPane.showMessageDialog(null, exc.getMessage());
				exc.printStackTrace();
			}

		});
	}

	// Generating and testing password

	public String generatePassword() {
		String password = "";
		for (int i = 0; i < Integer.parseInt(lengthField.getText()); i++) {
			int randomNum = (int) (Math.random() * (allowedCharsList.size()));
			password += allowedCharsList.get(randomNum);
		}
		return password;
	}

	// METHODS FOR MODIFYING LIST DEPENDING ON USER

	public void addAllLowercaseLetters() {
		for (int i = 'a'; i <= 'z'; i++) {
			allowedCharsList.add((char) i + "");
		}
	}

	public void removeAllLowercaseLetters() {
		for (int i = 'a'; i <= 'z'; i++) {
			if (allowedCharsList.contains((char) i + ""))
				allowedCharsList.remove((char) i + "");
		}
	}

	public void addAllUppercaseLetters() {
		for (int i = 'A'; i <= 'Z'; i++) {
			allowedCharsList.add((char) i + "");
		}
	}

	public void removeAllUppercaseLetters() {
		for (int i = 'A'; i <= 'Z'; i++) {
			if (allowedCharsList.contains((char) i + ""))
				allowedCharsList.remove((char) i + "");
		}
	}

	public void addAllNumbers() {
		for (int i = '0'; i <= '9'; i++) {
			allowedCharsList.add((char) i + "");
		}
	}

	public void removeAllNumbers() {
		for (int i = '0'; i <= '9'; i++) {
			if (allowedCharsList.contains((char) i + ""))
				allowedCharsList.remove((char) i + "");
		}
	}

	public void addSpecialCharacters() {
		allowedCharsList.add("?");
		allowedCharsList.add("_");
		allowedCharsList.add("*");
		allowedCharsList.add("-");
		allowedCharsList.add("$");
		allowedCharsList.add("#");
		allowedCharsList.add("%");
	}

	public void removeSpecialCharacters() {
		if (allowedCharsList.contains("?"))
			allowedCharsList.remove("?");
		if (allowedCharsList.contains("_"))
			allowedCharsList.remove("_");
		if (allowedCharsList.contains("*"))
			allowedCharsList.remove("*");
		if (allowedCharsList.contains("-"))
			allowedCharsList.remove("-");
		if (allowedCharsList.contains("$"))
			allowedCharsList.remove("$");
		if (allowedCharsList.contains("#"))
			allowedCharsList.remove("#");
		if (allowedCharsList.contains("%"))
			allowedCharsList.remove("%");
	}

	// TESTING

	public static void main(String[] args) {

		PasswordGenerator frame = new PasswordGenerator();
		SwingUtilities.invokeLater(() -> {
			frame.setVisible(true);
			frame.setTitle("Password generator");
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			frame.setSize(500, 300);
			frame.setLocation(300, 100);
		});

	}
}
