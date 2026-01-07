package it.younify.modules.sgtr.model.dto.response;


import it.younify.modules.sgtr.model.entity.FlagCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * The {@code ThreadResponse} class is a Data Transfer Object (DTO) that represents a thread to be sent to the client.
 * It provides a client-friendly representation of a {@link it.younify.modules.sgtr.model.entity.Threads} entity,
 * including author information and vote count.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ThreadResponse {

    /**
     * The unique identifier for the thread.
     */
    private Long ID;

    /**
     * The main content of the thread post.
     */
    private String content;

    /**
     * The total vote count for the thread.
     */
    private int CFU;

    /**
     * The category or topic of the thread.
     */
    private FlagCategory flag;

    /**
     * The university associated with the thread's author.
     */
    private String University;

    /**
     * The exact date and time the thread was published.
     */
    private Instant publicationDate;

    /**
     * The username of the host who authored the thread. This is null if the author is a regular user.
     */
    private String hostUsername;

    /**
     * The username of the user who authored the thread. This is null if the author is a host.
     */
    private String userUsername;

}
