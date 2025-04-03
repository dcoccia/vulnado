package com.scalesec.vulnado;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostgresTests {

    @Test
    public void connection_ShouldEstablishConnection() {
        try {
            Connection connection = Postgres.connection();
            assertNotNull("Connection should not be null", connection);
            connection.close();
        } catch (Exception e) {
            fail("Exception occurred while establishing connection: " + e.getMessage());
        }
    }

    @Test
    public void setup_ShouldCreateTablesAndInsertSeedData() {
        try {
            Postgres.setup();
            Connection connection = Postgres.connection();

            // Verify users table
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM users");
            rs.next();
            int userCount = rs.getInt("count");
            assertEquals("Users table should have 5 seed entries", 5, userCount);

            // Verify comments table
            rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM comments");
            rs.next();
            int commentCount = rs.getInt("count");
            assertEquals("Comments table should have 2 seed entries", 2, commentCount);

            connection.close();
        } catch (Exception e) {
            fail("Exception occurred during setup: " + e.getMessage());
        }
    }

    @Test
    public void md5_ShouldReturnCorrectHash() {
        String input = "test";
        String expectedHash = "098f6bcd4621d373cade4e832627b4f6"; // Precomputed MD5 hash for "test"
        String actualHash = Postgres.md5(input);
        assertEquals("MD5 hash should match expected value", expectedHash, actualHash);
    }

    @Test
    public void insertUser_ShouldInsertUserIntoDatabase() {
        try {
            String username = "testUser";
            String password = "testPassword";
            Postgres.insertUser(username, password);

            Connection connection = Postgres.connection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            assertTrue("User should exist in the database", rs.next());
            assertEquals("Username should match", username, rs.getString("username"));
            assertEquals("Password hash should match", Postgres.md5(password), rs.getString("password"));

            connection.close();
        } catch (Exception e) {
            fail("Exception occurred while inserting user: " + e.getMessage());
        }
    }

    @Test
    public void insertComment_ShouldInsertCommentIntoDatabase() {
        try {
            String username = "testUser";
            String body = "This is a test comment";
            Postgres.insertComment(username, body);

            Connection connection = Postgres.connection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM comments WHERE username = ? AND body = ?");
            stmt.setString(1, username);
            stmt.setString(2, body);
            ResultSet rs = stmt.executeQuery();

            assertTrue("Comment should exist in the database", rs.next());
            assertEquals("Username should match", username, rs.getString("username"));
            assertEquals("Comment body should match", body, rs.getString("body"));

            connection.close();
        } catch (Exception e) {
            fail("Exception occurred while inserting comment: " + e.getMessage());
        }
    }
}
