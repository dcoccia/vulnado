package com.scalesec.vulnado;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.autoconfigure.*;
import java.util.List;
import java.util.logging.Logger;
import java.io.Serializable;

LOGGER.info(\"Initializing CommentsController class.\");
LOGGER.info(\"CommentsController initialized.\");
@RestController
LOGGER.info(\"Enabling AutoConfiguration.\");
LOGGER.info(\"AutoConfiguration enabled.\");
@EnableAutoConfiguration
private static final Logger LOGGER = Logger.getLogger(CommentsController.class.getName());
public class CommentsController {
LOGGER.info(\"Initializing secret value.\");
  @Value("${app.secret}")
LOGGER.info(\"Secret value initialized.\");
  private String secret;

  @CrossOrigin(origins = \"http://trusted-domain.com\")
  @GetMapping(value = \"/comments\", produces = \"application/json\")
LOGGER.info(\"Fetching all comments.\");
  List<Comment> comments(@RequestHeader(value="x-auth-token") String token) {
LOGGER.info(\"Authenticating user token.\");
    User.assertAuth(secret, token);
    return Comment.fetch_all();
  }

  @CrossOrigin(origins = \"http://trusted-domain.com\")
  @PostMapping(value = \"/comments\", produces = \"application/json\", consumes = \"application/json\")
LOGGER.info(\"Processing comment creation request.\");
  Comment createComment(@RequestHeader(value="x-auth-token") String token, @RequestBody CommentRequest input) {
LOGGER.info(\"Creating a new comment.\");
    return Comment.create(input.username, input.body);
  }

  @CrossOrigin(origins = \"http://trusted-domain.com\")
  @DeleteMapping(value = \"/comments/{id}\", produces = \"application/json\")
LOGGER.info(\"Processing comment deletion request.\");
  Boolean deleteComment(@RequestHeader(value="x-auth-token") String token, @PathVariable("id") String id) {
LOGGER.info(\"Deleting a comment.\");
    return Comment.delete(id);
  }
}

LOGGER.info(\"CommentRequest class initialized.\");
class CommentRequest implements Serializable {
LOGGER.info(\"Initializing CommentRequest class.\");
  private String username;
  private String body;
}

LOGGER.info(\"Initializing BadRequest class.\");
LOGGER.info(\"BadRequest class initialized.\");
@ResponseStatus(HttpStatus.BAD_REQUEST)
LOGGER.info(\"Processing bad request exception.\");
class BadRequest extends RuntimeException {
LOGGER.warning(\"Bad request encountered.\");
  public BadRequest(String exception) {
    super(exception);
  }
}

LOGGER.info(\"Initializing ServerError class.\");
LOGGER.info(\"ServerError class initialized.\");
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
LOGGER.info(\"Processing server error exception.\");
class ServerError extends RuntimeException {
LOGGER.severe(\"Internal server error encountered.\");
  public ServerError(String exception) {
    super(exception);
  }
}
