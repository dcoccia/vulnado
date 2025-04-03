package com.scalesec.vulnado;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentsControllerTests {

    @Value("${app.secret}")
    private String secret;

    @Test
    public void comments_ShouldReturnAllComments() {
        // Arrange
        CommentsController controller = new CommentsController();
        String token = "valid-token";
        List<Comment> mockComments = new ArrayList<>();
        mockComments.add(new Comment("user1", "This is a comment."));
        mockComments.add(new Comment("user2", "Another comment."));
        mockStatic(Comment.class);
        when(Comment.fetch_all()).thenReturn(mockComments);

        // Act
        List<Comment> result = controller.comments(token);

        // Assert
        assertNotNull("Result should not be null", result);
        assertEquals("Result size should match", 2, result.size());
        assertEquals("First comment username should match", "user1", result.get(0).getUsername());
        assertEquals("Second comment body should match", "Another comment.", result.get(1).getBody());
    }

    @Test
    public void createComment_ShouldCreateAndReturnComment() {
        // Arrange
        CommentsController controller = new CommentsController();
        String token = "valid-token";
        CommentRequest input = new CommentRequest();
        input.setUsername("user1");
        input.setBody("This is a new comment.");
        Comment mockComment = new Comment("user1", "This is a new comment.");
        mockStatic(Comment.class);
        when(Comment.create(input.getUsername(), input.getBody())).thenReturn(mockComment);

        // Act
        Comment result = controller.createComment(token, input);

        // Assert
        assertNotNull("Result should not be null", result);
        assertEquals("Username should match", "user1", result.getUsername());
        assertEquals("Body should match", "This is a new comment.", result.getBody());
    }

    @Test
    public void deleteComment_ShouldDeleteAndReturnTrue() {
        // Arrange
        CommentsController controller = new CommentsController();
        String token = "valid-token";
        String commentId = "123";
        mockStatic(Comment.class);
        when(Comment.delete(commentId)).thenReturn(true);

        // Act
        Boolean result = controller.deleteComment(token, commentId);

        // Assert
        assertTrue("Result should be true", result);
    }

    @Test(expected = ResponseStatusException.class)
    public void comments_ShouldThrowExceptionForInvalidToken() {
        // Arrange
        CommentsController controller = new CommentsController();
        String invalidToken = "invalid-token";
        mockStatic(User.class);
        doThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED)).when(User.class);
        User.assertAuth(secret, invalidToken);

        // Act
        controller.comments(invalidToken);
    }

    @Test(expected = ResponseStatusException.class)
    public void createComment_ShouldThrowExceptionForInvalidToken() {
        // Arrange
        CommentsController controller = new CommentsController();
        String invalidToken = "invalid-token";
        CommentRequest input = new CommentRequest();
        input.setUsername("user1");
        input.setBody("This is a new comment.");
        mockStatic(User.class);
        doThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED)).when(User.class);
        User.assertAuth(secret, invalidToken);

        // Act
        controller.createComment(invalidToken, input);
    }

    @Test(expected = ResponseStatusException.class)
    public void deleteComment_ShouldThrowExceptionForInvalidToken() {
        // Arrange
        CommentsController controller = new CommentsController();
        String invalidToken = "invalid-token";
        String commentId = "123";
        mockStatic(User.class);
        doThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED)).when(User.class);
        User.assertAuth(secret, invalidToken);

        // Act
        controller.deleteComment(invalidToken, commentId);
    }
}
