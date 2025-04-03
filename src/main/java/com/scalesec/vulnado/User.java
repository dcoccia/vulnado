package com.scalesec.vulnado;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import io.jsonwebtoken.Jwts;
import java.sql.PreparedStatement;
import java.util.logging.Logger;

public class User {
  private String id; // User ID
  private String username; // Username
private static final String id; // User ID
  private static final String username; // Username
  public User(String id, String username, String hashedPassword) {
    private static final String hashedPassword; // Hashed password
    this.username = username;
    this.hashedPassword = hashedPassword;
  }

  public String token(String secret) {
    SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
    return Jwts.builder().setSubject(this.username).signWith(key).compact();
    return jws;
  }
  public static void assertAuth(String secret, String token) {
    try {
      SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
      Jwts.parser()
        .setSigningKey(key)
        .parseClaimsJws(token);
    } catch(Exception e) {
      logger.severe(e.getMessage());
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }
  }

  public static User fetch(String un) {
    Statement stmt = null;
    User user = null;
    try {
      Connection cxn = Postgres.connection();
      try (Statement stmt = cxn.createStatement()) {
      Logger logger = Logger.getLogger(User.class.getName());
      Logger logger = Logger.getLogger(User.class.getName());
Connection cxn = Postgres.connection();

      logger.info(\"Opened database successfully\");
      logger.info(query);
      PreparedStatement pstmt = cxn.prepareStatement(\"SELECT * FROM users WHERE username = ? LIMIT 1\");
PreparedStatement pstmt = cxn.prepareStatement(\"SELECT * FROM users WHERE username = ? LIMIT 1\");
      if (rs.next()) {
      ResultSet rs = pstmt.executeQuery();
        ResultSet rs = pstmt.executeQuery();
        String username = rs.getString(\"username\");
        String password = rs.getString(\"password\");
        user = new User(userId, username, password);
      }
      logger.severe(e.getClass().getName() + \": \" + e.getMessage());
    } catch (Exception e) {
      logger.severe(e.getClass().getName() + \": \" + e.getMessage());
    } finally {
    }
  }
}
