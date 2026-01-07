package it.younify.modules.sgtr.model.entity;


import it.younify.modules.sgac.model.entity.Host;
import it.younify.modules.sgac.model.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

/**
 * The {@code Threads} class is a JPA entity that represents a thread in the application's forum.
 * Each thread has content, a category (flag), and a vote count (CFU), and is authored by either a {@link User} or a {@link Host}.
 */
@Entity
@Table(name = "threads")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Threads {

    /**
     * The unique identifier for the thread.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    /**
     * The total vote count for the thread, representing its popularity or value.
     */
    @Column(nullable = false)
    private int CFU;

    /**
     * The main content of the thread post.
     */
    @Column(nullable = false, length = 500)
    private String content;

    /**
     * The category or topic of the thread, used for filtering and organization.
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FlagCategory flag;

    /**
     * The exact date and time the thread was published.
     */
    @Column(nullable = false)
    private Instant publicationDate;

    /**
     * The host who authored the thread. This is null if the author is a regular user.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id")
    private Host host;

    /**
     * The user who authored the thread. This is null if the author is a host.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
