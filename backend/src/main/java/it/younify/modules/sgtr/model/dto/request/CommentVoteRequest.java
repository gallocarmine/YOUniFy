package it.younify.modules.sgtr.model.dto.request;

import it.younify.modules.sgtr.model.entity.VoteType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * The {@code CommentVoteRequest} class is a Data Transfer Object (DTO) used for casting a vote on a comment.
 * It contains the ID of the comment being voted on and the type of vote.
 */
@Data
public class CommentVoteRequest {

    /**
     * The ID of the comment to vote on.
     */
    @NotNull
    private Long commentId;

    /**
     * The type of vote to cast (e.g., UPVOTE or DOWNVOTE).
     */
    @NotNull
    private VoteType voteType;
}
