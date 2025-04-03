package com.scalesec.vulnado;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.logging.Level;
import io.jsonwebtoken.Jwts;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

public class User {
  private String id; 
  private String username;

  private String hashedPassword;
  public User(String id, String username, String hashedPassword) {
    this.id = id;
    this.username = username;
    this.hashedPassword = hashedPassword;
  public String getId() {
  }
    return id;

  }
  public String token(String secret) {
  public String getUsername() {
    SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
    return username;
    return Jwts.builder().setSubject(this.username).signWith(key).compact();
  }
    return jws;
  public String getHashedPassword() {
  }
    return hashedPassword;

  }
  public static void assertAuth(String secret, String token) {
    try {
      SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
      Jwts.parser()
        .setSigningKey(key)
        .parseClaimsJws(token);
    } catch(Exception e) {
      Logger.getLogger(User.class.getName()).log(Level.SEVERE, e.getMessage(), e);
      throw new Unauthorized(e.getMessage());
    }
  }

  public static User fetch(String un) {
    Statement stmt = null;
    User user = null;
    try {
      Connection cxn = Postgres.connection();
    try (Statement stmt = cxn.createStatement()) {
      Logger.getLogger(User.class.getName()).info("Opened database successfully");

      String query = "select * from users where username = '" + un + "' limit 1";
      Logger.getLogger(User.class.getName()).info(query);
      String query = "SELECT * FROM users WHERE username = ? LIMIT 1";
      PreparedStatement pstmt = cxn.prepareStatement(query);
      if (rs.next()) {
      pstmt.setString(1, un);
        String userId = rs.getString("user_id");
      ResultSet rs = pstmt.executeQuery();
        String username = rs.getString("username");
        String password = rs.getString("password");
        user = new User(user_id, username, password);
      }
      cxn.close();
    } catch (Exception e) {
      Logger.getLogger(User.class.getName()).log(Level.SEVERE, e.getMessage(), e);
      Logger.getLogger(User.class.getName()).severe(e.getClass().getName() + ": " + e.getMessage());
    } finally {
    return user;
    }
  }
}
