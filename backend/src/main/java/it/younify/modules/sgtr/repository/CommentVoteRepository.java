package it.younify.modules.sgtr.repository;

import it.younify.modules.sgac.model.entity.Host;
import it.younify.modules.sgac.model.entity.User;
import it.younify.modules.sgtr.model.entity.Comment;
import it.younify.modules.sgtr.model.entity.CommentVote;
import it.younify.modules.sgtr.model.entity.Threads;
import it.younify.modules.sgtr.model.entity.ThreadsVote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The {@code CommentVoteRepository} interface is a Spring Data JPA repository for {@link CommentVote} entities.
 * It provides methods to find votes on comments, which is essential for managing user interactions and preventing duplicate votes.
 */
public interface CommentVoteRepository extends JpaRepository<CommentVote, Long> {

    /**
     * Finds a comment vote by its unique ID.
     *
     * @return An {@link Optional} containing the vote if found.
     */
    Optional<CommentVote> findByID(Long id);

    /**
     * Finds a vote cast by a specific user on a specific comment.
     * This is crucial for checking if a user has already voted on a comment.
     *
     * @param comment The comment that was voted on.
     * @param user The user who cast the vote.
     * @return An {@link Optional} containing the vote if it exists.
     */
    Optional<CommentVote> findByCommentAndUser(Comment comment, User user);

    /**
     * Finds a vote cast by a specific host on a specific comment.
     * This is crucial for checking if a host has already voted on a comment.
     *
     * @param comment The comment that was voted on.
     * @param host The host who cast the vote.
     * @return An {@link Optional} containing the vote if it exists.
     */
    Optional<CommentVote> findByCommentAndHost(Comment comment, Host host);
}
