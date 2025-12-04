import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.*;

public class StudentTable extends JFrame {

    public StudentTable() {

        // Current date
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
        String currentDay = dayFormat.format(today);
        String currentMonth = monthFormat.format(today);

        Container c = this.getContentPane();
        c.setLayout(null);
        c.setBackground(Color.lightGray);
        this.setTitle("Student Attendance System");
        this.setSize(1000, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel titleLabel = new JLabel("STUDENT ATTENDANCE SYSTEM");
        titleLabel.setBounds(350, 10, 400, 40);
        c.add(titleLabel);

        JLabel dateLabel = new JLabel("Today: " + currentDay + " " + currentMonth);
        dateLabel.setBounds(750, 10, 200, 40);
        dateLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        c.add(dateLabel);

        JLabel selectDateLabel = new JLabel("Select Date:");
        selectDateLabel.setBounds(20, 50, 100, 30);
        c.add(selectDateLabel);

        // Day selector
        JComboBox<String> dayBox = new JComboBox<>();
        for (int i = 1; i <= 31; i++) {
            dayBox.addItem(String.format("%02d", i));
        }
        dayBox.setSelectedItem(currentDay);
        dayBox.setBounds(120, 50, 60, 30);
        c.add(dayBox);

        // Month selector
        JComboBox<String> monthBox = new JComboBox<>(new String[]{
                "January","February","March","April","May","June","July","August",
                "September","October","November","December"
        });
        monthBox.setSelectedItem(currentMonth);
        monthBox.setBounds(190, 50, 100, 30);
        c.add(monthBox);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(20, 80, 50, 30);
        c.add(idLabel);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 120, 50, 30);
        c.add(nameLabel);

        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setBounds(400, 120, 80, 30);
        c.add(statusLabel);

        JTextField idTf = new JTextField();
        idTf.setBounds(80, 80, 150, 30);
        c.add(idTf);

        JTextField nameTf = new JTextField();
        nameTf.setBounds(80, 120, 150, 30);
        c.add(nameTf);

        JComboBox<String> statusBox = new JComboBox<>(new String[]{"Present", "Absent"});
        statusBox.setBounds(480, 120, 150, 30);
        c.add(statusBox);

        JButton addButton = new JButton("Add");
        addButton.setBounds(250, 80, 100, 30);
        c.add(addButton);

        JButton updateButton = new JButton("Update");
        updateButton.setBounds(250, 120, 100, 30);
        c.add(updateButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(250, 160, 100, 30);
        c.add(deleteButton);

        JButton markBtn = new JButton("Mark Attendance");
        markBtn.setBounds(480, 160, 200, 30);
        c.add(markBtn);

        // Student table
        DefaultTableModel studentModel = new DefaultTableModel(new Object[]{"ID", "Name"}, 0);
        JTable studentTable = new JTable(studentModel);
        studentTable.setSelectionBackground(Color.lightGray);
        JScrollPane sp1 = new JScrollPane(studentTable);
        sp1.setBounds(20, 200, 350, 400);
        c.add(sp1);

        // Attendance table
        DefaultTableModel attendanceModel = new DefaultTableModel(new Object[]{"Month", "Day", "Status"}, 0);
        JTable attendanceTable = new JTable(attendanceModel);
        JScrollPane sp2 = new JScrollPane(attendanceTable);
        sp2.setBounds(400, 230, 550, 350);
        c.add(sp2);

        // Color rendering
        attendanceTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (column == 2) {
                    String status = table.getValueAt(row, column).toString();
                    if (status.equalsIgnoreCase("Present")) c.setBackground(Color.green);
                    else if (status.equalsIgnoreCase("Absent")) c.setBackground(Color.red);
                    else c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                } else {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                }
                return c;
            }
        });

        List<String[]> studentList = new ArrayList<>();
        Map<String, List<String[]>> attendanceMap = new HashMap<>();

        // Helper to get selected date
        java.util.function.Supplier<String[]> getSelectedDate = () -> {
            String day = (String) dayBox.getSelectedItem();
            String month = (String) monthBox.getSelectedItem();
            return new String[]{month, day}; // Month first, Day second
        };

        // Add button
        addButton.addActionListener(e -> {
            String id = idTf.getText().trim();
            String name = nameTf.getText().trim();
            if (id.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter ID and Name!");
                return;
            }
            for (String[] s : studentList) {
                if (s[0].equalsIgnoreCase(id)) {
                    JOptionPane.showMessageDialog(this, "ID already exists!");
                    return;
                }
            }
            studentList.add(new String[]{id, name});
            studentModel.addRow(new Object[]{id, name});
            attendanceMap.put(id, new ArrayList<>());
            idTf.setText("");
            nameTf.setText("");
        });

        // Update button
        updateButton.addActionListener(e -> {
            int row = studentTable.getSelectedRow();
            if (row >= 0) {
                String id = idTf.getText().trim();
                String name = nameTf.getText().trim();
                studentModel.setValueAt(id, row, 0);
                studentModel.setValueAt(name, row, 1);
                studentList.set(row, new String[]{id, name});
                JOptionPane.showMessageDialog(this, "Updated!");
            }
        });

        // Delete button
        deleteButton.addActionListener(e -> {
            int row = studentTable.getSelectedRow();
            if (row >= 0) {
                String id = studentModel.getValueAt(row, 0).toString();
                studentModel.removeRow(row);
                studentList.remove(row);
                attendanceMap.remove(id);
                attendanceModel.setRowCount(0);
            }
        });

        // Show attendance for selected student
        studentTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                int row = studentTable.getSelectedRow();
                if (row != -1) {
                    String selectedId = studentModel.getValueAt(row, 0).toString();
                    idTf.setText(selectedId);
                    nameTf.setText(studentModel.getValueAt(row, 1).toString());
                    refreshAttendanceTable(selectedId, attendanceMap, attendanceModel);
                }
            }
        });

        // Update table when day or month changes
        ActionListener dateChangeListener = e -> {
            int row = studentTable.getSelectedRow();
            if (row != -1) {
                String selectedId = studentModel.getValueAt(row, 0).toString();
                refreshAttendanceTable(selectedId, attendanceMap, attendanceModel);
            }
        };
        dayBox.addActionListener(dateChangeListener);
        monthBox.addActionListener(dateChangeListener);

        // Mark attendance
        markBtn.addActionListener(e -> {
            int row = studentTable.getSelectedRow();
            if (row >= 0) {
                String id = studentModel.getValueAt(row, 0).toString();
                String[] selDate = getSelectedDate.get();
                String month = selDate[0];
                String day = selDate[1];
                String status = (String) statusBox.getSelectedItem();

                List<String[]> records = attendanceMap.get(id);
                boolean alreadyMarked = false;
                for (String[] rec : records) {
                    if (rec[0].equals(month) && rec[1].equals(day)) {
                        rec[2] = status;
                        alreadyMarked = true;
                        break;
                    }
                }
                if (!alreadyMarked) {
                    records.add(new String[]{month, day, status});
                }

                refreshAttendanceTable(id, attendanceMap, attendanceModel);
            } else {
                JOptionPane.showMessageDialog(this, "Select a student first!");
            }
        });
    }

    // Refresh table sorted ascending (earliest dates on top)
    private void refreshAttendanceTable(String studentId, Map<String, List<String[]>> attendanceMap,
                                        DefaultTableModel attendanceModel) {
        attendanceModel.setRowCount(0);
        List<String[]> records = new ArrayList<>(attendanceMap.getOrDefault(studentId, new ArrayList<>()));

        // Sort ascending: earliest dates first
        records.sort((a, b) -> {
            int monthA = monthStringToInt(a[0]);
            int monthB = monthStringToInt(b[0]);
            if (monthA != monthB) return monthA - monthB; 
            return Integer.parseInt(a[1]) - Integer.parseInt(b[1]);
        });

        for (String[] rec : records) {
            attendanceModel.addRow(rec);
        }
    }

    private int monthStringToInt(String month) {
        switch (month) {
            case "January": return 1;
            case "February": return 2;
            case "March": return 3;
            case "April": return 4;
            case "May": return 5;
            case "June": return 6;
            case "July": return 7;
            case "August": return 8;
            case "September": return 9;
            case "October": return 10;
            case "November": return 11;
            case "December": return 12;
            default: return 0;
        }
    }

    public static void main(String[] args) {
        StudentTable frame = new StudentTable();
        frame.setVisible(true);
    }
}