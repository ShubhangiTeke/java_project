
package LostFoundIntelligence;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.security.MessageDigest;
import java.time.LocalDate;

public class LostFoundApp extends JFrame {

    private int userId;
    private String userName;
    private String userRole;

    private final Color navy = new Color(25, 42, 75);
    private final Color teal = new Color(13, 148, 136);
    private final Color background = new Color(244, 247, 251);
    private final CardLayout layout = new CardLayout();
    private final JPanel root = new JPanel(layout);

    public LostFoundApp() {
        setTitle("Lost & Found Intelligence System");
        setSize(1100, 700);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        root.add(loginScreen(), "login");
        root.add(registerScreen(), "register");
        add(root);
        layout.show(root, "login");
    }

    // ---------------- PASSWORD HASHING ----------------

    private String hash(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] bytes = md.digest(
                password.getBytes(java.nio.charset.StandardCharsets.UTF_8));

        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    // ---------------- DESIGN HELPERS ----------------

    private JLabel heading(String text, int size) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, size));
        label.setForeground(navy);
        return label;
    }

    private JButton button(String text, Color color) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(new EmptyBorder(10, 16, 10, 16));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    private JTextField textField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return field;
    }

    private JPanel page() {
        JPanel p = new JPanel(new BorderLayout(15, 15));
        p.setBackground(background);
        p.setBorder(new EmptyBorder(25, 30, 25, 30));
        return p;
    }

    private void error(String message) {
        JOptionPane.showMessageDialog(
                this, message, "Please check", JOptionPane.ERROR_MESSAGE);
    }

    private void info(String message) {
        JOptionPane.showMessageDialog(
                this, message, "Lost & Found", JOptionPane.INFORMATION_MESSAGE);
    }

    // ---------------- LOGIN ----------------

    private JPanel loginScreen() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(background);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(35, 45, 35, 45));

        JLabel icon = new JLabel("🔎");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 42));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = heading("Lost & Found", 28);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Find what matters. Return what belongs.");
        subtitle.setForeground(Color.GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField email = textField(23);
        JPasswordField password = new JPasswordField(23);
        password.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        card.add(icon);
        card.add(Box.createVerticalStrut(8));
        card.add(title);
        card.add(Box.createVerticalStrut(5));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(25));
        card.add(new JLabel("Email address"));
        card.add(email);
        card.add(Box.createVerticalStrut(12));
        card.add(new JLabel("Password"));
        card.add(password);
        card.add(Box.createVerticalStrut(20));

        JButton login = button("Login", teal);
        JButton register = button("Create Account", navy);
        login.setAlignmentX(Component.CENTER_ALIGNMENT);
        register.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(login);
        card.add(Box.createVerticalStrut(10));
        card.add(register);

        JLabel adminHint = new JLabel(
                "<html><center>Admin demo: admin@lostfound.com<br>"
                + "Password: Admin@123</center></html>");
        adminHint.setForeground(Color.GRAY);
        adminHint.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalStrut(20));
        card.add(adminHint);
        outer.add(card);
        login.addActionListener(e -> {
            try (Connection con = Dbconnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(
                         "SELECT user_id,name,role FROM users "
                         + "WHERE email=? AND password=?")) {

                ps.setString(1, email.getText().trim());
                ps.setString(2, hash(new String(password.getPassword())));

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        userId = rs.getInt("user_id");
                        userName = rs.getString("name");
                        userRole = rs.getString("role");
                        showDashboard();
                    } else {
                        error("Invalid email or password.");
                    }
                }
            } catch (Exception ex) {
                error("Login failed: " + ex.getMessage());
            }
        });

        register.addActionListener(e -> layout.show(root, "register"));
        password.addActionListener(e -> login.doClick());

        return outer;
    }

    // ---------------- REGISTRATION ----------------

    private JPanel registerScreen() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(background);

        JPanel card = new JPanel(new GridLayout(0, 1, 8, 8));
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(30, 40, 30, 40));

        card.add(heading("Create Your Account", 25));

        JTextField name = textField(25);
        JTextField email = textField(25);
        JPasswordField password = new JPasswordField(25);
        password.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        card.add(new JLabel("Full name"));
        card.add(name);
        card.add(new JLabel("Email"));
        card.add(email);
        card.add(new JLabel("Password (minimum 6 characters)"));
        card.add(password);

        JButton create = button("Register", teal);
        JButton back = button("Back to Login", navy);
        card.add(create);
        card.add(back);
        outer.add(card);

        create.addActionListener(e -> {
            String n = name.getText().trim();
            String em = email.getText().trim();
            String pw = new String(password.getPassword());

            if (n.isEmpty() || em.isEmpty() || pw.length() < 6) {
                error("Enter your name, email, and a password "
                        + "of at least 6 characters.");
                return;
            }

            try (Connection con = Dbconnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(
                         "INSERT INTO users(name,email,password,role) "
                         + "VALUES(?,?,?,'USER')")) {

                ps.setString(1, n);
                ps.setString(2, em);
                ps.setString(3, hash(pw));
                ps.executeUpdate();

                info("Account created successfully! Please log in.");
                layout.show(root, "login");

            } catch (SQLIntegrityConstraintViolationException ex) {
                error("This email is already registered.");
            } catch (Exception ex) {
                error("Registration failed: " + ex.getMessage());
            }
        });

        back.addActionListener(e -> layout.show(root, "login"));
        return outer;
    }

    // ---------------- DASHBOARD ----------------

    private void showDashboard() {
        JPanel dashboard = new JPanel(new BorderLayout());
        dashboard.setBackground(background);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(navy);
        header.setBorder(new EmptyBorder(18, 25, 18, 25));

        JLabel brand = new JLabel("🔎  LOST & FOUND");
        brand.setFont(new Font("Segoe UI", Font.BOLD, 22));
        brand.setForeground(Color.WHITE);

        String role = "ADMIN".equalsIgnoreCase(userRole)
                ? "ADMIN" : "USER";

        JLabel greeting = new JLabel(
                "Welcome, " + userName + "  |  " + role + "   ");
        greeting.setForeground(Color.WHITE);
        greeting.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JButton logout = button("Logout", new Color(190, 65, 65));
        header.add(brand, BorderLayout.WEST);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setOpaque(false);
        right.add(greeting);
        right.add(logout);
        header.add(right, BorderLayout.EAST);

        dashboard.add(header, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 14));

        tabs.addTab("Overview", overviewPanel());
        tabs.addTab("Report Lost", reportPanel(true));
        tabs.addTab("Report Found", reportPanel(false));
        tabs.addTab("Browse Lost", browsePanel(true));
        tabs.addTab("Browse Found", browsePanel(false));
        tabs.addTab("My Reports", myReportsPanel());

        if ("ADMIN".equalsIgnoreCase(userRole)) {
            tabs.addTab("Admin Management", adminPanel());
        }

        dashboard.add(tabs, BorderLayout.CENTER);

        logout.addActionListener(e -> {
            userId = 0;
            userName = null;
            userRole = null;
            layout.show(root, "login");
        });

        root.add(dashboard, "dashboard");
        layout.show(root, "dashboard");
    }

    // ---------------- OVERVIEW ----------------

    private JPanel overviewPanel() {
        JPanel p = page();
        p.add(heading("Your Lost & Found Dashboard", 25),
                BorderLayout.NORTH);

        JPanel content = new JPanel(new GridLayout(2, 2, 18, 18));
        content.setBackground(background);

        content.add(statCard("LOST ITEMS", count("lost_items", false),
                "Reports submitted by users"));
        content.add(statCard("FOUND ITEMS", count("found_items", false),
                "Items reported as found"));
        content.add(statCard("MY LOST REPORTS", count("lost_items", true),
                "Your lost-item reports"));
        content.add(statCard("MY FOUND REPORTS", count("found_items", true),
                "Your found-item reports"));

        p.add(content, BorderLayout.CENTER);

        JLabel note = new JLabel(
                "Tip: Include an accurate location and description "
                + "to help identify the correct item.");
        note.setForeground(new Color(70, 90, 110));
        p.add(note, BorderLayout.SOUTH);

        return p;
    }

    private JPanel statCard(String title, int number, String description) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 228, 238)),
                new EmptyBorder(20, 22, 20, 22)));

        JLabel t = new JLabel(title);
        t.setForeground(teal);
        t.setFont(new Font("Segoe UI", Font.BOLD, 15));

        JLabel n = new JLabel(String.valueOf(number));
        n.setForeground(navy);
        n.setFont(new Font("Segoe UI", Font.BOLD, 35));

        JLabel d = new JLabel(description);
        d.setForeground(Color.GRAY);

        card.add(t);
        card.add(Box.createVerticalStrut(12));
        card.add(n);
        card.add(Box.createVerticalStrut(6));
        card.add(d);

        return card;
    }

    private int count(String table, boolean mine) {
        if (!table.equals("lost_items") && !table.equals("found_items")) {
            return 0;
        }

        String sql = "SELECT COUNT(*) FROM " + table
                + (mine ? " WHERE user_id=?" : "");

        try (Connection con = Dbconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            if (mine) {
                ps.setInt(1, userId);
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return 0;
    }

    // ---------------- REPORT LOST / FOUND ----------------

    private JPanel reportPanel(boolean lost) {
        JPanel p = page();

        p.add(heading(lost ? "Report a Lost Item" : "Report a Found Item",
                25), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(new EmptyBorder(25, 30, 25, 30));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(9, 9, 9, 9);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;

        JTextField item = textField(25);
        JTextArea description = new JTextArea(4, 25);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JTextField location = textField(25);
        JTextField date = textField(25);
        date.setText(LocalDate.now().toString());

        int row = 0;
        addFormRow(form, g, row++, "Item name *", item);
        addFormRow(form, g, row++, "Description", new JScrollPane(description));
        addFormRow(form, g, row++, "Location *", location);
        addFormRow(form, g, row++, lost ? "Date lost *" : "Date found *", date);

        JLabel hint = new JLabel("Date format: YYYY-MM-DD");
        hint.setForeground(Color.GRAY);
        g.gridx = 1;
        g.gridy = row++;
        form.add(hint, g);

        JButton submit = button("Submit Report", teal);
        g.gridx = 1;
        g.gridy = row;
        form.add(submit, g);

        submit.addActionListener(e -> {
            if (item.getText().trim().isEmpty()
                    || location.getText().trim().isEmpty()) {
                error("Item name and location are required.");
                return;
            }

            try {
                Date sqlDate = Date.valueOf(date.getText().trim());

                String sql = lost
                    ? "INSERT INTO lost_items(item_name,description,location,"
                      + "date_lost,status,user_id) VALUES(?,?,?,?, 'Lost',?)"
                    : "INSERT INTO found_items(item_name,description,location,"
                      + "date_found,status,user_id) VALUES(?,?,?,?, 'Found',?)";

                try (Connection con = Dbconnection.getConnection();
                     PreparedStatement ps = con.prepareStatement(sql)) {

                    ps.setString(1, item.getText().trim());
                    ps.setString(2, description.getText().trim());
                    ps.setString(3, location.getText().trim());
                    ps.setDate(4, sqlDate);
                    ps.setInt(5, userId);
                    ps.executeUpdate();
                }

                info("Your report was submitted successfully!");
                item.setText("");
                description.setText("");
                location.setText("");
                date.setText(LocalDate.now().toString());

            } catch (IllegalArgumentException ex) {
                error("Enter a valid date in YYYY-MM-DD format.");
            } catch (SQLException ex) {
                error("Could not save report: " + ex.getMessage());
            }
        });

        p.add(new JScrollPane(form), BorderLayout.CENTER);
        return p;
    }

    private void addFormRow(JPanel form, GridBagConstraints g,
                            int row, String label, Component field) {
        g.gridy = row;
        g.gridx = 0;
        g.weightx = 0.3;
        form.add(new JLabel(label), g);

        g.gridx = 1;
        g.weightx = 0.7;
        form.add(field, g);
    }

    // ---------------- BROWSE AND SEARCH ----------------

    private JPanel browsePanel(boolean lost) {
        JPanel p = page();

        p.add(heading(lost ? "Browse Lost Items" : "Browse Found Items", 24),
                BorderLayout.NORTH);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setBackground(background);

        JTextField search = textField(22);
        JButton searchButton = button("Search", teal);
        JButton refresh = button("Show All", navy);

        top.add(new JLabel("Item or location:"));
        top.add(search);
        top.add(searchButton);
        top.add(refresh);

        DefaultTableModel model = tableModel();
        JTable table = new JTable(model);
        styleTable(table);

        JPanel center = new JPanel(new BorderLayout(5, 10));
        center.add(top, BorderLayout.NORTH);
        center.add(new JScrollPane(table), BorderLayout.CENTER);
        p.add(center, BorderLayout.CENTER);

        Runnable load = () -> loadItems(model, lost,
                search.getText().trim(), false);

        searchButton.addActionListener(e -> load.run());
        refresh.addActionListener(e -> {
            search.setText("");
            load.run();
        });

        load.run();
        return p;
    }

    private DefaultTableModel tableModel() {
        return new DefaultTableModel(
                new String[]{"ID", "Item", "Description", "Location",
                             "Date", "Status", "Reported By"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private void styleTable(JTable table) {
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 13));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    private void loadItems(DefaultTableModel model, boolean lost,
                           String search, boolean mine) {
        model.setRowCount(0);

        String table = lost ? "lost_items" : "found_items";
        String dateColumn = lost ? "date_lost" : "date_found";

        String sql = "SELECT i.item_id,i.item_name,i.description,i.location,"
                + "i." + dateColumn + ",i.status,u.name "
                + "FROM " + table + " i "
                + "LEFT JOIN users u ON i.user_id=u.user_id "
                + "WHERE (i.item_name LIKE ? OR i.location LIKE ?) "
                + (mine ? "AND i.user_id=? " : "")
                + "ORDER BY i.item_id DESC";

        try (Connection con = Dbconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String pattern = "%" + search + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            if (mine) {
                ps.setInt(3, userId);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getDate(5), rs.getString(6),
                        rs.getString(7)
                    });
                }
            }
        } catch (SQLException ex) {
            error("Could not load reports: " + ex.getMessage());
        }
    }

    // ---------------- MY REPORTS ----------------

    private JPanel myReportsPanel() {
        JPanel p = page();
        p.add(heading("My Reports", 24), BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();

        DefaultTableModel lostModel = tableModel();
        JTable lostTable = new JTable(lostModel);
        styleTable(lostTable);
        tabs.addTab("My Lost Items", new JScrollPane(lostTable));

        DefaultTableModel foundModel = tableModel();
        JTable foundTable = new JTable(foundModel);
        styleTable(foundTable);
        tabs.addTab("My Found Items", new JScrollPane(foundTable));

        JButton refresh = button("Refresh My Reports", teal);
        refresh.addActionListener(e -> {
            loadItems(lostModel, true, "", true);
            loadItems(foundModel, false, "", true);
        });

        p.add(tabs, BorderLayout.CENTER);
        p.add(refresh, BorderLayout.SOUTH);

        loadItems(lostModel, true, "", true);
        loadItems(foundModel, false, "", true);

        return p;
    }

    // ---------------- ADMIN MANAGEMENT ----------------

    private JPanel adminPanel() {
        JPanel p = page();
        p.add(heading("Admin Report Management", 24), BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(
                new String[]{"Type", "ID", "Item", "Description", "Location",
                             "Date", "Status", "Reported By"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        styleTable(table);

        JButton refresh = button("Refresh Reports", teal);
        JButton update = button("Update Selected Status", navy);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.setBackground(background);
        bottom.add(refresh);
        bottom.add(update);

        p.add(new JScrollPane(table), BorderLayout.CENTER);
        p.add(bottom, BorderLayout.SOUTH);

        Runnable load = () -> loadAllReports(model);
        refresh.addActionListener(e -> load.run());

        update.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                error("Select a report first.");
                return;
            }

            String type = model.getValueAt(row, 0).toString();
            int id = Integer.parseInt(model.getValueAt(row, 1).toString());
            String current = model.getValueAt(row, 6).toString();

            String[] statuses = type.equals("LOST")
                    ? new String[]{"Lost", "Under Review", "Matched",
                                   "Returned", "Closed"}
                    : new String[]{"Found", "Under Review", "Matched",
                                   "Claimed", "Returned", "Closed"};

            JComboBox<String> choices = new JComboBox<>(statuses);
            choices.setSelectedItem(current);

            int result = JOptionPane.showConfirmDialog(
                    this, choices, "Choose new status",
                    JOptionPane.OK_CANCEL_OPTION);

            if (result != JOptionPane.OK_OPTION) {
                return;
            }

            String newStatus = (String) choices.getSelectedItem();
            String sql = type.equals("LOST")
                    ? "UPDATE lost_items SET status=? WHERE item_id=?"
                    : "UPDATE found_items SET status=? WHERE item_id=?";

            try (Connection con = Dbconnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, newStatus);
                ps.setInt(2, id);
                ps.executeUpdate();

                info("Report status updated.");
                load.run();

            } catch (SQLException ex) {
                error("Could not update status: " + ex.getMessage());
            }
        });

        load.run();
        return p;
    }

    private void loadAllReports(DefaultTableModel model) {
        model.setRowCount(0);

        String sql =
            "SELECT 'LOST',l.item_id,l.item_name,l.description,l.location,"
            + "l.date_lost,l.status,u.name "
            + "FROM lost_items l LEFT JOIN users u ON l.user_id=u.user_id "
            + "UNION ALL "
            + "SELECT 'FOUND',f.item_id,f.item_name,f.description,f.location,"
            + "f.date_found,f.status,u.name "
            + "FROM found_items f LEFT JOIN users u ON f.user_id=u.user_id "
            + "ORDER BY 2 DESC";

        try (Connection con = Dbconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString(1), rs.getInt(2), rs.getString(3),
                    rs.getString(4), rs.getString(5), rs.getDate(6),
                    rs.getString(7), rs.getString(8)
                });
            }
        } catch (SQLException ex) {
            error("Could not load admin reports: " + ex.getMessage());
        }
    }

    // ---------------- APPLICATION ENTRY POINT ----------------

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }

            new LostFoundApp().setVisible(true);
        });
    }
}
