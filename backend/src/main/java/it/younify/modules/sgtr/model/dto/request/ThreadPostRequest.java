package it.younify.modules.sgtr.model.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

/**
 * The {@code ThreadPostRequest} class is a Data Transfer Object (DTO) used for creating a new thread.
 * It contains the content and the category (flag) for the new post.
 */
@Data
public class ThreadPostRequest {

    /**
     * The main content of the thread.
     * It must not be blank and must be between 1 and 500 characters.
     */
    @NotBlank
    @Size(min = 1, max = 500)
    private String content;

    /**
     * The category of the thread.
     * This must correspond to one of the values in the {@link it.younify.modules.sgtr.model.entity.FlagCategory} enum.
     */
    @NotBlank
    private String flag;

}
