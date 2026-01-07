package it.younify.modules.sgtr.model.dto.response;


import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * The {@code CommentResponse} class is a Data Transfer Object (DTO) that represents a comment to be sent to the client.
 * It provides a client-friendly representation of a {@link it.younify.modules.sgtr.model.entity.Comment} entity,
 * including author information and vote count.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {

    /**
     * The unique identifier for the comment.
     */
    private Long ID;

    /**
     * The text content of the comment.
     */
    private String content;

    /**
     * The total vote count for the comment.
     */
    private int CFU;

    /**
     * The exact date and time the comment was published.
     */
    private Instant publicationDate;

    /**
     * The username of the host who authored the comment. This is null if the author is a regular user.
     */
    private String hostUsername;

    /**
     * The username of the user who authored the comment. This is null if the author is a host.
     */
    private String userUsername;

}
