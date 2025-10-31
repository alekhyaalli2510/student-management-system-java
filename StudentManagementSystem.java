import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class StudentManagementSystem extends JFrame {
    private StudentDatabase database;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton addButton, updateButton, deleteButton, refreshButton, searchButton;
    private JLabel totalStudentsLabel;
    
    public StudentManagementSystem() {
        database = new StudentDatabase();
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Student Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Top Panel - Search and Controls
        JPanel topPanel = createTopPanel();
        
        // Middle Panel - Table
        JPanel tablePanel = createTablePanel();
        
        // Bottom Panel - Status
        JPanel bottomPanel = createBottomPanel();
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        setVisible(true);
    }
    
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBackground(new Color(240, 240, 240));
        
        // Search components
        JLabel searchLabel = new JLabel("Search:");
        searchField = new JTextField(15);
        searchButton = new JButton("Search");
        
        searchButton.addActionListener(e -> searchStudents());
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchStudents();
                }
            }
        });
        
        // Buttons
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        refreshButton = new JButton("Refresh");
        
        addButton.addActionListener(e -> addStudent());
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        refreshButton.addActionListener(e -> refreshTable());
        
        panel.add(searchLabel);
        panel.add(searchField);
        panel.add(searchButton);
        panel.addSeparator();
        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(refreshButton);
        
        return panel;
    }
    
    private void addSeparator(Container container) {
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setPreferredSize(new Dimension(2, 30));
        container.add(separator);
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Create table model
        String[] columns = {"Student ID", "Name", "Email", "Phone", "Registration Date"};
        tableModel = new DefaultTableModel(columns, 0);
        studentTable = new JTable(tableModel);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentTable.setRowHeight(25);
        
        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Load initial data
        refreshTable();
        
        return panel;
    }
    
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createEtchedBorder());
        
        totalStudentsLabel = new JLabel("Total Students: 0");
        totalStudentsLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        panel.add(totalStudentsLabel);
        
        return panel;
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Student> students = database.getAllStudents();
        
        for (Student student : students) {
            Object[] row = {
                student.getStudentId(),
                student.getName(),
                student.getEmail(),
                student.getPhone(),
                student.getFormattedDate()
            };
            tableModel.addRow(row);
        }
        
        totalStudentsLabel.setText("Total Students: " + database.getTotalStudents());
    }
    
    private void searchStudents() {
        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            refreshTable();
            return;
        }
        
        tableModel.setRowCount(0);
        List<Student> results = database.searchStudent(query);
        
        for (Student student : results) {
            Object[] row = {
                student.getStudentId(),
                student.getName(),
                student.getEmail(),
                student.getPhone(),
                student.getFormattedDate()
            };
            tableModel.addRow(row);
        }
    }
    
    private void addStudent() {
        JDialog dialog = new JDialog(this, "Add New Student", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel idLabel = new JLabel("Student ID:");
        JTextField idField = new JTextField();
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel phoneLabel = new JLabel("Phone:");
        JTextField phoneField = new JTextField();
        
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        
        saveButton.addActionListener(e -> {
            if (idField.getText().isEmpty() || nameField.getText().isEmpty() ||
                emailField.getText().isEmpty() || phoneField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Student student = new Student(idField.getText(), nameField.getText(),
                                         emailField.getText(), phoneField.getText());
            if (database.addStudent(student)) {
                JOptionPane.showMessageDialog(dialog, "Student added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Student ID already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        panel.add(idLabel);
        panel.add(idField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(phoneLabel);
        panel.add(phoneField);
        panel.add(saveButton);
        panel.add(cancelButton);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void updateStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to update!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String studentId = (String) tableModel.getValueAt(selectedRow, 0);
        Student student = database.getStudentById(studentId);
        
        JDialog dialog = new JDialog(this, "Update Student", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel idLabel = new JLabel("Student ID:");
        JTextField idField = new JTextField(student.getStudentId());
        idField.setEditable(false);
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(student.getName());
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(student.getEmail());
        JLabel phoneLabel = new JLabel("Phone:");
        JTextField phoneField = new JTextField(student.getPhone());
        
        JButton updateButtonDialog = new JButton("Update");
        JButton cancelButton = new JButton("Cancel");
        
        updateButtonDialog.addActionListener(e -> {
            if (nameField.getText().isEmpty() || emailField.getText().isEmpty() || phoneField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Student updatedStudent = new Student(studentId, nameField.getText(),
                                                emailField.getText(), phoneField.getText());
            if (database.updateStudent(studentId, updatedStudent)) {
                JOptionPane.showMessageDialog(dialog, "Student updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
                dialog.dispose();
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        panel.add(idLabel);
        panel.add(idField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(phoneLabel);
        panel.add(phoneField);
        panel.add(updateButtonDialog);
        panel.add(cancelButton);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void deleteStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String studentId = (String) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this student?", "Confirm", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (database.deleteStudent(studentId)) {
                JOptionPane.showMessageDialog(this, "Student deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentManagementSystem());
    }
}
