package it.younify.modules.sgtr.model.entity;

/**
 * The {@code VoteType} enum represents the type of vote that can be cast on a thread or comment.
 */
public enum VoteType {

    /**
     * Represents an upvote, which increases the score (CFU) of a post.
     */
    UPVOTE,

    /**
     * Represents a downvote, which decreases the score (CFU) of a post.
     */
    DOWNVOTE,
}
