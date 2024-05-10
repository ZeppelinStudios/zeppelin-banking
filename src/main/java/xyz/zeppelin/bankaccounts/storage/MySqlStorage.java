package xyz.zeppelin.bankaccounts.storage;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.zeppelin.bankaccounts.BankPlugin;
import xyz.zeppelin.bankaccounts.models.BankAccount;
import xyz.zeppelin.bankaccounts.models.Transaction;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MySqlStorage implements StorageType<Connection> {
    private Connection connection;

    private final String host;
    private final int port;
    private final String user;
    private final String password;
    private final String databaseName;

    public MySqlStorage(String host, int port, String user, String password, String databaseName) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.databaseName = databaseName;
    }

    @SneakyThrows
    @Override
    public void enable() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Connect to the database
            String url = "jdbc:mysql://" + host + ":" + port + "/" + databaseName;
            connection = DriverManager.getConnection(url, user, password);

            BankPlugin.getInstance().getLogger().info("Connected to MySQL database.");
        } catch (ClassNotFoundException | SQLException e) {
            BankPlugin.getInstance().getLogger().warning("Could not establish connection to the MySQL Storage Adapter.");
            e.printStackTrace();
        }

        try (Statement stmt = connection.createStatement()) {
            String query = "CREATE TABLE IF NOT EXISTS bank_accounts (" +
                    "uuid VARCHAR(36) PRIMARY KEY," +
                    "player VARCHAR(36) NOT NULL," +
                    "name VARCHAR(255) NOT NULL," +
                    "balance DECIMAL(19, 2) NOT NULL," +
                    "active BOOLEAN NOT NULL" +
                    ")";

            String query2 = "CREATE TABLE IF NOT EXISTS transactions(" +
                    "uuid VARCHAR(36) PRIMARY KEY," +
                    "data VARCHAR(255) NOT NULL" +
                    ")";
            stmt.executeUpdate(query);
            stmt.executeUpdate(query2);
            BankPlugin.getInstance().getLogger().info("Successfully validated database.");
        }
    }

    @Override
    public void disable() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                Bukkit.getLogger().info("Disconnected from MySQL database.");
            }
        } catch (SQLException e) {
            Bukkit.getLogger().warning("Could not establish connection to the MySQL Storage Adapter.");
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Connection> get() {
        return Optional.ofNullable(connection);
    }

    public void save(BankAccount bankAccount) {
        String query = "INSERT INTO bank_accounts (uuid, player, name, balance, active) VALUES (?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE name = VALUES(name), player = VALUES(player), balance = VALUES(balance), active = VALUES(active)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, bankAccount.getUuid().toString());
            pstmt.setString(2, bankAccount.getPlayer().getUniqueId().toString());
            pstmt.setString(3, bankAccount.getName());
            pstmt.setBigDecimal(4, bankAccount.getBalance());
            pstmt.setBoolean(5, bankAccount.isActive());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<BankAccount> get(UUID uuid) {
        String query = "SELECT * FROM bank_accounts WHERE uuid = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, uuid.toString());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Player player = Bukkit.getPlayer(UUID.fromString(rs.getString("player")));
                String name = rs.getString("name");
                BigDecimal balance = rs.getBigDecimal("balance");
                boolean active = rs.getBoolean("active");
                BankAccount bankAccount = new BankAccount(uuid, player, name, null, balance, active, null); // Assuming no player and shares for now
                return Optional.of(bankAccount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void save(Transaction transaction) {
        String query = "INSERT INTO transactions (uuid, data) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, transaction.getUuid().toString());
            pstmt.setString(2, transaction.getData());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Transaction> getTransaction(UUID uuid) {
        String query = "SELECT * FROM transactions WHERE uuid = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, uuid.toString());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String data = rs.getString("data");
                Transaction transaction = new Transaction(uuid, data);
                return Optional.of(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM transactions";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                UUID uuid = UUID.fromString(rs.getString("uuid"));
                String data = rs.getString("data");
                Transaction transaction = new Transaction(uuid, data);
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public List<BankAccount> getAllBankAccounts() {
        List<BankAccount> bankAccounts = new ArrayList<>();
        String query = "SELECT * FROM bank_accounts";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                UUID uuid = UUID.fromString(rs.getString("uuid"));
                Player player = Bukkit.getPlayer(UUID.fromString(rs.getString("player")));
                String name = rs.getString("name");
                BigDecimal balance = rs.getBigDecimal("balance");
                boolean active = rs.getBoolean("active");
                BankAccount bankAccount = new BankAccount(uuid, player, name, null, balance, active, null); // Assuming no player and shares for now
                bankAccounts.add(bankAccount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bankAccounts;
    }

    public void deleteTransaction(UUID uuid) {
        String query = "DELETE FROM transactions WHERE uuid = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, uuid.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBankAccount(UUID uuid) {
        String query = "DELETE FROM bank_accounts WHERE uuid = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, uuid.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
