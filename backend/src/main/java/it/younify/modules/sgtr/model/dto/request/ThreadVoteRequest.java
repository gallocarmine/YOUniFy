package it.younify.modules.sgtr.model.dto.request;

import it.younify.modules.sgtr.model.entity.VoteType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * The {@code ThreadVoteRequest} class is a Data Transfer Object (DTO) used for casting a vote on a thread.
 * It contains the ID of the thread being voted on and the type of vote.
 */
@Data
public class ThreadVoteRequest {

    /**
     * The ID of the thread to vote on.
     */
    @NotNull
    private Long threadId;

    /**
     * The type of vote to cast (e.g., UPVOTE or DOWNVOTE).
     */
    @NotNull
    private VoteType voteType;
}
