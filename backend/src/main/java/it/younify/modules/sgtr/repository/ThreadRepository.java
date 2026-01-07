package it.younify.modules.sgtr.repository;
import it.younify.modules.sgac.model.entity.Host;
import it.younify.modules.sgac.model.entity.User;
import it.younify.modules.sgtr.model.entity.FlagCategory;
import it.younify.modules.sgtr.model.entity.Threads;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * The {@code ThreadRepository} interface is a Spring Data JPA repository for {@link Threads} entities.
 * It provides standard CRUD operations and custom query methods for finding threads based on various criteria.
 */
public interface ThreadRepository extends JpaRepository<Threads, Long> {

    /**
     * Finds a thread by its unique ID.
     *
     * @param ID The ID of the thread.
     * @return An {@link Optional} containing the thread if found.
     */
    Optional<Threads> findById(Long ID);

    /**
     * Finds a thread by its flag category.
     *
     * @param flag The flag category to search for.
     * @return An {@link Optional} containing a thread if found.
     */
    Optional<Threads> findByFlag(FlagCategory flag);

    /**
     * Finds a thread by its author.
     *
     * @param user The user who authored the thread.
     * @return An {@link Optional} containing a thread if found.
     */
    Optional<Threads> findByUser(User user);

    /**
     * Finds a thread by its author.
     *
     * @param host The host who authored the thread.
     * @return An {@link Optional} containing a thread if found.
     */
    Optional<Threads> findByHost(Host host);

    /**
     * Finds a thread by its exact publication date.
     *
     * @param publicationDate The publication date to search for.
     * @return An {@link Optional} containing a thread if found.
     */
    Optional<Threads> findByPublicationDate(Instant publicationDate);

    /**
     * Finds all threads by a specific user, ordered by vote count (CFU) in ascending order.
     *
     * @param user The user whose threads are to be retrieved.
     * @return A list of threads.
     */
    List<Threads> findAllByOrderByCFUAsc(User user);

    /**
     * Finds all threads by a specific user, ordered by vote count (CFU) in descending order.
     *
     * @param user The user whose threads are to be retrieved.
     * @return A list of threads.
     */
    List<Threads> findAllByOrderByCFUDesc(User user);

    /**
     * Retrieves a paginated list of all threads, sorted by publication date in descending order.
     *
     * @param pageable The pagination information.
     * @return A paginated list of threads.
     */
    Page<Threads> findAllByOrderByPublicationDateDesc(Pageable pageable);

    /**
     * Retrieves a paginated list of threads filtered by a flag category, sorted by publication date in descending order.
     *
     * @param flag The flag category to filter by.
     * @param pageable The pagination information.
     * @return A paginated list of threads.
     */
    Page<Threads> findAllByFlagOrderByPublicationDateDesc(FlagCategory flag, Pageable pageable);

    /**
     * Retrieves a paginated list of threads filtered by university, sorted by publication date in descending order.
     * This method performs a case-insensitive search across both user and host authors.
     *
     * @param universityInHost The university string to search for in hosts' university lists.
     * @param universityInUser The university string to search for in users' universities.
     * @param pageable The pagination information.
     * @return A paginated list of threads.
     */
    Page<Threads> findByHost_UniListContainingIgnoreCaseOrUser_UniversityIgnoreCaseOrderByPublicationDateDesc(

            String universityInHost,
            String universityInUser,
            Pageable pageable
    );
}
