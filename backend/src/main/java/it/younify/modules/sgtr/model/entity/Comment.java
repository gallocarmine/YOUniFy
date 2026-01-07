package it.younify.modules.sgtr.model.entity;

import it.younify.modules.sgac.model.entity.Host;
import it.younify.modules.sgac.model.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

/**
 * The {@code Comment} class is a JPA entity that represents a comment on a thread.
 * Each comment belongs to a specific {@link Threads} and is authored by either a {@link User} or a {@link Host}.
 */
@Entity
@Table(name = "comment")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    /**
     * The unique identifier for the comment.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    /**
     * The total vote count for the comment, representing its popularity or value.
     */
    @Column(nullable = false)
    private int CFU;

    /**
     * The text content of the comment.
     */
    @Column(nullable = false, length = 500)
    private String content;

    /**
     * The exact date and time the comment was published.
     */
    @Column(nullable = false)
    private Instant publicationDate;

    /**
     * The parent thread to which this comment belongs.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thread_id")
    private Threads threadId;

    /**
     * The user who authored the comment. This is null if the author is a host.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * The host who authored the comment. This is null if the author is a regular user.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id")
    private Host host;
}
