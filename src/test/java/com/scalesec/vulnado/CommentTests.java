package com.scalesec.vulnado;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VulnadoApplicationTests {

    @Test
    public void contextLoads() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Test
    public void Comment_Create_ShouldReturnComment() {
        // Arrange
        String username = "testUser";
        String body = "testBody";
        Comment mockComment = mock(Comment.class);
        when(mockComment.commit()).thenReturn(true);

        // Act
        Comment result = Comment.create(username, body);

        // Assert
        assertNotNull("Comment should not be null", result);
        assertEquals("Username should match", username, result.getUsername());
        assertEquals("Body should match", body, result.getBody());
    }

    @Test(expected = BadRequest.class)
    public void Comment_Create_ShouldThrowBadRequestWhenCommitFails() {
        // Arrange
        String username = "testUser";
        String body = "testBody";
        Comment mockComment = mock(Comment.class);
        when(mockComment.commit()).thenReturn(false);

        // Act
        Comment.create(username, body);
    }

    @Test(expected = ServerError.class)
    public void Comment_Create_ShouldThrowServerErrorOnException() {
        // Arrange
        String username = "testUser";
        String body = "testBody";
        Comment mockComment = mock(Comment.class);
        when(mockComment.commit()).thenThrow(new SQLException("Database error"));

        // Act
        Comment.create(username, body);
    }

    @Test
    public void Comment_FetchAll_ShouldReturnListOfComments() throws SQLException {
        // Arrange
        Connection mockConnection = mock(Connection.class);
        Statement mockStatement = mock(Statement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("id")).thenReturn(UUID.randomUUID().toString());
        when(mockResultSet.getString("username")).thenReturn("testUser");
        when(mockResultSet.getString("body")).thenReturn("testBody");
        when(mockResultSet.getTimestamp("created_on")).thenReturn(new Timestamp(System.currentTimeMillis()));

        Postgres.setConnection(mockConnection);

        // Act
        List<Comment> comments = Comment.fetchAll();

        // Assert
        assertNotNull("Comments list should not be null", comments);
        assertEquals("Comments list should contain one comment", 1, comments.size());
    }

    @Test
    public void Comment_Delete_ShouldReturnTrueWhenSuccessful() throws SQLException {
        // Arrange
        String commentId = UUID.randomUUID().toString();
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        Postgres.setConnection(mockConnection);

        // Act
        boolean result = Comment.delete(commentId);

        // Assert
        assertTrue("Delete should return true when successful", result);
    }

    @Test
    public void Comment_Delete_ShouldReturnFalseWhenUnsuccessful() throws SQLException {
        // Arrange
        String commentId = UUID.randomUUID().toString();
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        Postgres.setConnection(mockConnection);

        // Act
        boolean result = Comment.delete(commentId);

        // Assert
        assertFalse("Delete should return false when unsuccessful", result);
    }

    @Test
    public void Comment_Commit_ShouldReturnTrueWhenSuccessful() throws SQLException {
        // Arrange
        Comment comment = new Comment(UUID.randomUUID().toString(), "testUser", "testBody", new Timestamp(System.currentTimeMillis()));
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        Postgres.setConnection(mockConnection);

        // Act
        boolean result = comment.commit();

        // Assert
        assertTrue("Commit should return true when successful", result);
    }

    @Test
    public void Comment_Commit_ShouldReturnFalseWhenUnsuccessful() throws SQLException {
        // Arrange
        Comment comment = new Comment(UUID.randomUUID().toString(), "testUser", "testBody", new Timestamp(System.currentTimeMillis()));
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        Postgres.setConnection(mockConnection);

        // Act
        boolean result = comment.commit();

        // Assert
        assertFalse("Commit should return false when unsuccessful", result);
    }
}
