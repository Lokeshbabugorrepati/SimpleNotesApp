import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;

public class SimpleNotesApp extends JFrame {
    private JTextArea noteArea;
    private JTextField fileNameField;

    public SimpleNotesApp() {
        setTitle("Simple Notes App");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        noteArea = new JTextArea();
        fileNameField = new JTextField("Enter filename here...");

        JButton saveBtn = new JButton("Save");
        JButton loadBtn = new JButton("Load");
        JButton clearBtn = new JButton("Clear");

        saveBtn.addActionListener(e -> saveNote());
        loadBtn.addActionListener(e -> loadNote());
        clearBtn.addActionListener(e -> noteArea.setText(""));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(fileNameField, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        btnPanel.add(saveBtn);
        btnPanel.add(loadBtn);
        btnPanel.add(clearBtn);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(noteArea), BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        File notesDir = new File("notes");
        if (!notesDir.exists()) notesDir.mkdir();

        setVisible(true);
    }

    private void saveNote() {
        String fileName = fileNameField.getText().trim();
        if (fileName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Filename cannot be empty.");
            return;
        }

        try {
            Path filePath = Paths.get("notes", fileName + ".txt");
            Files.write(filePath, noteArea.getText().getBytes());
            JOptionPane.showMessageDialog(this, "Note saved successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving note: " + e.getMessage());
        }
    }

    private void loadNote() {
        String fileName = fileNameField.getText().trim();
        if (fileName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Filename cannot be empty.");
            return;
        }

        try {
            Path filePath = Paths.get("notes", fileName + ".txt");
            String content = Files.readString(filePath);
            noteArea.setText(content);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading note: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimpleNotesApp::new);
    }
}
