import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class BookLendingApp {

    // Components for Login Page
    private JFrame loginFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField schoolField;
    private JButton loginButton, registerButton;

    // Components for Main Page
    private JFrame mainFrame;
    private JTextField searchField, borrowDateField;
    private JTable userTable;
    private DefaultTableModel tableModel;
    private List<User> users;
    private List<Book> books;
    private User currentUser;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BookLendingApp::new);
    }

    public BookLendingApp() {
        users = new ArrayList<>();
        books = loadBooks();
        setupLoginPage();
    }

    private void setupLoginPage() {
        loginFrame = new JFrame("Pinjam Pustaka");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLayout(new GridLayout(7, 2, 20, 20));
        loginFrame.getContentPane().setBackground(new Color(245, 245, 250)); // Light grayish background

        // Title Label
        JLabel titleLabel = new JLabel("Pinjam Pustaka", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titleLabel.setForeground(new Color(70, 130, 180)); // Steel blue
        loginFrame.add(titleLabel);
        loginFrame.add(new JLabel("")); // Empty cell for spacing

        // Username Field
        loginFrame.add(createStyledLabel("Username:"));
        usernameField = createStyledTextField();
        loginFrame.add(usernameField);

        // Password Field
        loginFrame.add(createStyledLabel("Password:"));
        passwordField = createStyledPasswordField();
        loginFrame.add(passwordField);

        // School Field
        loginFrame.add(createStyledLabel("School:"));
        schoolField = createStyledTextField();
        loginFrame.add(schoolField);

        // Buttons
        loginButton = createStyledButton("Login");
        loginButton.addActionListener(e -> handleLogin());
        loginFrame.add(loginButton);

        registerButton = createStyledButton("Register");
        registerButton.addActionListener(e -> handleRegister());
        loginFrame.add(registerButton);

        loginFrame.pack();
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 18));
        label.setForeground(new Color(0, 51, 102)); // Dark blue
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        textField.setBorder(BorderFactory.createLineBorder(new Color(192, 192, 192), 2));
        return textField;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(192, 192, 192), 2));
        return passwordField;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setBackground(new Color(70, 130, 180)); // Steel blue
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(192, 192, 192), 2));
        return button;
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        currentUser = users.stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (currentUser != null) {
            loginFrame.dispose();
            setupMainPage();
        } else {
            JOptionPane.showMessageDialog(loginFrame, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleRegister() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String school = schoolField.getText();

        if (username.isEmpty() || password.isEmpty() || school.isEmpty()) {
            JOptionPane.showMessageDialog(loginFrame, "Fields cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (password.length() < 6) {
            JOptionPane.showMessageDialog(loginFrame, "Password must be at least 6 characters", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        users.add(new User(username, password, school));
        JOptionPane.showMessageDialog(loginFrame, "User registered successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void setupMainPage() {
        mainFrame = new JFrame("Book Lending System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout(10, 10));
        mainFrame.getContentPane().setBackground(new Color(245, 245, 250));

        // Top Panel
        JPanel topPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        topPanel.setBackground(new Color(245, 245, 250));

        topPanel.add(createStyledLabel("Search Book:"));
        searchField = createStyledTextField();
        topPanel.add(searchField);

        JButton searchButton = createStyledButton("Search");
        searchButton.addActionListener(e -> searchBook());
        topPanel.add(searchButton);

        topPanel.add(createStyledLabel("Borrow Date (YYYY-MM-DD):"));
        borrowDateField = createStyledTextField();
        topPanel.add(borrowDateField);

        JButton borrowButton = createStyledButton("Borrow");
        borrowButton.addActionListener(e -> borrowBook());
        topPanel.add(borrowButton);

        mainFrame.add(topPanel, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new Object[]{"Username", "School", "Book", "Borrow Date", "Return Date", "Status", "Image"}, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 6 ? ImageIcon.class : String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Prevent editing of all cells
            }
        };

        userTable = new JTable(tableModel);
        userTable.setRowHeight(120);
        userTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        userTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
        userTable.getTableHeader().setBackground(new Color(70, 130, 180));
        userTable.getTableHeader().setForeground(Color.WHITE);
        mainFrame.add(new JScrollPane(userTable), BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(245, 245, 250));

        JButton returnButton = createStyledButton("Return Book");
        returnButton.addActionListener(e -> returnBook());
        bottomPanel.add(returnButton);

        JButton logoutButton = createStyledButton("Logout");
        logoutButton.setBackground(Color.RED); // Set logout button to red
        logoutButton.addActionListener(e -> logout());
        bottomPanel.add(logoutButton);

        mainFrame.add(bottomPanel, BorderLayout.SOUTH);

        mainFrame.setSize(900, 700);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void searchBook() {
        String title = searchField.getText().trim();

        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Please enter a book title to search", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Book> matchingBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                matchingBooks.add(book);
            }
        }

        if (!matchingBooks.isEmpty()) {
            StringBuilder result = new StringBuilder("Found books:\n");
            for (Book book : matchingBooks) {
                result.append(book.getTitle()).append("\n");
            }
            JOptionPane.showMessageDialog(mainFrame, result.toString(), "Search Results", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(mainFrame, "No books found", "Search Results", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void borrowBook() {
        String title = searchField.getText().trim();
        String borrowDate = borrowDateField.getText().trim();

        if (title.isEmpty() || borrowDate.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Fields cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Book book = books.stream()
                .filter(b -> b.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);

        if (book != null) {
            LocalDate borrowDateParsed = LocalDate.parse(borrowDate);
            LocalDate returnDate = borrowDateParsed.plusDays(7);

            ImageIcon bookImage = loadBookImage(book.getTitle());
            tableModel.addRow(new Object[]{
                    currentUser.getUsername(), currentUser.getSchool(), book.getTitle(),
                    borrowDate, returnDate.toString(), "Active", bookImage
            });

            JOptionPane.showMessageDialog(mainFrame, "Book borrowed successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Book not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void returnBook() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.setValueAt("Returned", selectedRow, 5);
            JOptionPane.showMessageDialog(mainFrame, "Book returned successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Please select a row to return a book", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private ImageIcon loadBookImage(String title) {
        String imagePath = "images/" + title.replaceAll("\\s+", "_").toLowerCase() + ".jpg";
        try {
            ImageIcon imageIcon = new ImageIcon(imagePath);
            Image image = imageIcon.getImage().getScaledInstance(100, 120, Image.SCALE_SMOOTH); // Resize image
            return new ImageIcon(image);
        } catch (Exception e) {
            ImageIcon defaultIcon = new ImageIcon("images/default.jpg");
            Image defaultImage = defaultIcon.getImage().getScaledInstance(100, 120, Image.SCALE_SMOOTH);
            return new ImageIcon(defaultImage);
        }
    }

    private void logout() {
        mainFrame.dispose();
        setupLoginPage();
    }

    private List<Book> loadBooks() {
        return Arrays.asList(
                new Book("Bumi Manusia", "Pramoedya Ananta Toer", 1980, "Fiksi sejarah"),
                new Book("Laskar Pelangi", "Andrea Hirata", 2005, "Fiksi inspiratif"),
                new Book("Negeri 5 Menara", "Ahmad Fuadi", 2009, "Fiksi inspiratif"),
                new Book("Sang Pemimpi", "Andrea Hirata", 2006, "Fiksi inspiratif"),
                new Book("Habis Gelap Terbitlah Terang", "R.A. Kartini", 1911, "Filsafat")
        );
    }

    static class Book {
        private final String title;
        private final String author;
        private final int year;
        private final String genre;

        public Book(String title, String author, int year, String genre) {
            this.title = title;
            this.author = author;
            this.year = year;
            this.genre = genre;
        }

        public String getTitle() {
            return title;
        }
    }

    static class User {
        private final String username;
        private final String password;
        private final String school;

        public User(String username, String password, String school) {
            this.username = username;
            this.password = password;
            this.school = school;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getSchool() {
            return school;
        }
    }
}
