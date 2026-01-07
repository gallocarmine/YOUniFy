package it.younify.modules.sgtr.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * The {@code CommentPostRequest} class is a Data Transfer Object (DTO) used for creating a new comment.
 * It contains the content of the comment and the ID of the parent thread.
 */
@Data
public class CommentPostRequest {

    /**
     * The ID of the parent thread to which this comment belongs.
     */
    @NotNull
    private Long threadId;

    /**
     * The text content of the comment.
     * It must not be blank and must be between 1 and 500 characters.
     */
    @NotBlank
    @Size(min = 1, max = 500)
    private String content;
}
