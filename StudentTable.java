import java.awt.Color;
import java.awt.Container;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StudentTable extends JFrame {

    private Container c;
    private JLabel titleLabel, idLabel, nameLabel, monthLabel, dayLabel, statusLabel;
    private JTextField idTf, nameTf;
    private JButton addButton, updateButton, deleteButton;
    private JTable studentTable;
    private JComboBox<String> monthBox, dayBox, statusBox;
    private JButton markBtn;

    private DefaultTableModel studentModel;

    private List<Student> studentList = new ArrayList<>();
    private final String STUDENT_FILE = "students.dat";

    public StudentTable() {
        initComponents();
        loadData();
    }

    static class Student implements Serializable {
        String id, name;
        Student(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    private void initComponents() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 700);
        this.setLocationRelativeTo(null);
        this.setTitle("Student Attendance System - Update 1");

        c = this.getContentPane();
        c.setLayout(null);
        c.setBackground(Color.lightGray);

        // Title
        titleLabel = new JLabel("STUDENT ATTENDANCE SYSTEM (UPDATE 1)");
        titleLabel.setBounds(350, 10, 400, 40);
        c.add(titleLabel);

        // Student Inputs
        idLabel = new JLabel("ID:");
        idLabel.setBounds(20, 80, 50, 30);
        c.add(idLabel);

        idTf = new JTextField();
        idTf.setBounds(80, 80, 150, 30);
        c.add(idTf);

        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 120, 50, 30);
        c.add(nameLabel);

        nameTf = new JTextField();
        nameTf.setBounds(80, 120, 150, 30);
        c.add(nameTf);

        addButton = new JButton("Add");
        addButton.setBounds(250, 80, 100, 30);
        c.add(addButton);

        updateButton = new JButton("Update");
        updateButton.setBounds(250, 120, 100, 30);
        c.add(updateButton);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(250, 160, 100, 30);
        c.add(deleteButton);

        // Student Table
        studentTable = new JTable();
        studentModel = new DefaultTableModel(new Object[]{"ID", "Name"}, 0);
        studentTable.setModel(studentModel);
        studentTable.setSelectionBackground(Color.GREEN);
        JScrollPane sp1 = new JScrollPane(studentTable);
        sp1.setBounds(20, 200, 350, 400);
        c.add(sp1);

        // Attendance Input
        monthLabel = new JLabel("Month:");
        monthLabel.setBounds(400, 80, 80, 30);
        c.add(monthLabel);

        monthBox = new JComboBox<>(new String[]{"January", "February", "March", "April"});
        monthBox.setBounds(480, 80, 150, 30);
        c.add(monthBox);

        dayLabel = new JLabel("Day:");
        dayLabel.setBounds(400, 120, 80, 30);
        c.add(dayLabel);

        dayBox = new JComboBox<>();
        for (int i = 1; i <= 31; i++) dayBox.addItem(String.valueOf(i));
        dayBox.setBounds(480, 120, 150, 30);
        c.add(dayBox);

        statusLabel = new JLabel("Status:");
        statusLabel.setBounds(400, 160, 80, 30);
        c.add(statusLabel);

        statusBox = new JComboBox<>(new String[]{"Present", "Absent"});
        statusBox.setBounds(480, 160, 150, 30);
        c.add(statusBox);

        markBtn = new JButton("Mark Attendance");
        markBtn.setBounds(480, 200, 150, 30);
        c.add(markBtn);

        // Button Actions
        addButton.addActionListener(e -> addStudent());
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());

        // Mark attendance does nothing yet
        markBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Attendance function will be enabled in UPDATE 3.");
        });

        studentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = studentTable.getSelectedRow();
                if (row != -1) {
                    idTf.setText(studentModel.getValueAt(row, 0).toString());
                    nameTf.setText(studentModel.getValueAt(row, 1).toString());
                }
            }
        });

        // Save ONLY student data on close
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                saveData();
            }
        });
    }

    private void addStudent() {
        String id = idTf.getText().trim();
        String name = nameTf.getText().trim();
        if (id.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter ID and Name!");
            return;
        }

        studentModel.addRow(new Object[]{id, name});
        studentList.add(new Student(id, name));
        idTf.setText("");
        nameTf.setText("");
    }

    private void updateStudent() {
        int row = studentTable.getSelectedRow();
        if (row >= 0) {
            String id = idTf.getText().trim();
            String name = nameTf.getText().trim();

            studentModel.setValueAt(id, row, 0);
            studentModel.setValueAt(name, row, 1);

            Student s = studentList.get(row);
            s.id = id;
            s.name = name;
        } else {
            JOptionPane.showMessageDialog(this, "Select a student to update!");
        }
    }

    private void deleteStudent() {
        int row = studentTable.getSelectedRow();
        if (row >= 0) {
            studentModel.removeRow(row);
            studentList.remove(row);
        } else {
            JOptionPane.showMessageDialog(this, "Select a student to delete!");
        }
    }

    private void saveData() {
        try (ObjectOutputStream out1 = new ObjectOutputStream(new FileOutputStream(STUDENT_FILE))) {
            out1.writeObject(studentList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadData() {
        try (ObjectInputStream in1 = new ObjectInputStream(new FileInputStream(STUDENT_FILE))) {
            studentList = (List<Student>) in1.readObject();
            for (Student s : studentList) {
                studentModel.addRow(new Object[]{s.id, s.name});
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No previous student data found, starting fresh.");
        }
    }

    public static void main(String[] args) {
    	StudentTable frame = new StudentTable();
    	frame.setVisible(true);
    }
}
