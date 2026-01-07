package it.younify.modules.sgtr.model.entity;

import it.younify.modules.sgac.model.entity.Host;
import it.younify.modules.sgac.model.entity.User;
import jakarta.persistence.*;
import lombok.*;

/**
 * The {@code CommentVote} class is a JPA entity that represents a vote on a comment.
 * This entity links a {@link Comment}, a voter (either a {@link User} or a {@link Host}), and the type of vote cast.
 * It is crucial for preventing duplicate votes and for calculating the total score (CFU) of a comment.
 */
@Entity
@Table(name = "comment_vote")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentVote {

    /**
     * The unique identifier for the vote.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    /**
     * The type of vote (e.g., UPVOTE or DOWNVOTE).
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VoteType voteType;

    /**
     * The comment that was voted on.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    /**
     * The user who cast the vote. This is null if the voter is a host.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * The host who cast the vote. This is null if the voter is a regular user.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id")
    private Host host;
}

