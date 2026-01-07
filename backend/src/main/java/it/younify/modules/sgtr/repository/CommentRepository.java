package it.younify.modules.sgtr.repository;

import it.younify.modules.sgac.model.entity.Host;
import it.younify.modules.sgac.model.entity.User;
import it.younify.modules.sgtr.model.entity.Comment;
import it.younify.modules.sgtr.model.entity.Threads;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * The {@code CommentRepository} interface is a Spring Data JPA repository for {@link Comment} entities.
 * It provides standard CRUD operations and custom query methods for finding comments based on various criteria.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Finds a comment by its unique ID.
     *
     * @param id The ID of the comment.
     * @return An {@link Optional} containing the comment if found.
     */
    Optional<Comment> findById(Long id);

    /**
     * Finds a comment by its author.
     *
     * @param user The user who authored the comment.
     * @return An {@link Optional} containing a comment if found.
     */
    Optional<Comment> findByUser(User user);

    /**
     * Finds a comment by its author.
     *
     * @param host The host who authored the comment.
     * @return An {@link Optional} containing a comment if found.
     */
    Optional<Comment> findByHost(Host host);

    /**
     * Finds a comment by its exact publication date.
     *
     * @param publicationDate The publication date to search for.
     * @return An {@link Optional} containing a comment if found.
     */
    Optional<Comment> findByPublicationDate(Instant publicationDate);

    /**
     * Finds all comments, ordered by vote count (CFU) in ascending order.
     *
     * @return A list of all comments.
     */
    List<Comment> findAllByOrderByCFUAsc();

    /**
     * Finds all comments, ordered by vote count (CFU) in descending order.
     *
     * @return A list of all comments.
     */
    List<Comment> findAllByOrderByCFUDesc();

    /**
     * Retrieves a paginated list of comments for a specific thread, sorted by vote count (CFU) in descending order.
     *
     * @param thread The thread to retrieve comments for.
     * @param pageable The pagination information.
     * @return A paginated list of comments.
     */
    Page<Comment> findByThreadIdOrderByCFUDesc(Threads thread, Pageable pageable);

    /**
     * Retrieves a paginated list of comments for a specific thread, sorted by publication date in descending order.
     *
     * @param thread The thread to retrieve comments for.
     * @param pageable The pagination information.
     * @return A paginated list of comments.
     */
    Page<Comment> findByThreadIdOrderByPublicationDateDesc(Threads thread, Pageable pageable);
}
