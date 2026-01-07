package it.younify.modules.sgtr.service;


import it.younify.modules.sgtr.model.dto.response.ThreadResponse;
import it.younify.modules.sgtr.model.entity.FlagCategory;
import it.younify.modules.sgtr.model.entity.Threads;
import it.younify.modules.sgtr.repository.ThreadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * The {@code ThreadViewService} class provides methods for retrieving and viewing threads.
 * It handles the logic for fetching threads based on different criteria and converting them
 * into a client-friendly response format.
 *
 * @see it.younify.modules.sgtr.repository.ThreadRepository
 * @see it.younify.modules.sgtr.model.dto.response.ThreadResponse
 */
@Service
@RequiredArgsConstructor
public class ThreadViewService {

    private final ThreadRepository threadRepository;

    /**
     * Retrieves a paginated list of all threads, sorted by publication date in descending order.
     *
     * @param pageable The pagination information.
     * @return A paginated list of {@link ThreadResponse}.
     */
    public Page<ThreadResponse> getThreadsByDate(Pageable pageable) {

        return threadRepository.findAllByOrderByPublicationDateDesc(pageable).map(this::toResponse);
    }

    /**
     * Retrieves a paginated list of threads filtered by a specific flag category,
     * sorted by publication date in descending order.
     *
     * @param flag The flag category to filter by.
     * @param pageable The pagination information.
     * @return A paginated list of {@link ThreadResponse}.
     */
    public Page<ThreadResponse> getThreadsByFlag(FlagCategory flag, Pageable pageable) {
        return threadRepository.findAllByFlagOrderByPublicationDateDesc(flag, pageable).map(this::toResponse);
    }

    /**
     * Retrieves a paginated list of threads filtered by a specific university,
     * sorted by publication date in descending order.
     * This method searches for threads posted by both users and hosts associated with the given university.
     *
     * @param university The university to filter by.
     * @param pageable The pagination information.
     * @return A paginated list of {@link ThreadResponse}.
     */
    public Page<ThreadResponse> getThreadsByUniversity(String university, Pageable pageable) {
        return threadRepository.findByHost_UniListContainingIgnoreCaseOrUser_UniversityIgnoreCaseOrderByPublicationDateDesc(university, university, pageable).map(this::toResponse);
    }


    /**
     * Converts a {@link Threads} entity into a {@link ThreadResponse} DTO.
     * This method handles the mapping of author information, whether the author is a {@link it.younify.modules.sgac.model.entity.User}
     * or a {@link it.younify.modules.sgac.model.entity.Host}.
     *
     * @param thread The thread entity to convert.
     * @return The corresponding {@link ThreadResponse}.
     */
    private ThreadResponse toResponse(Threads thread) {

        if(thread.getUser() != null){

            return new ThreadResponse(
                    thread.getID(),
                    thread.getContent(),
                    thread.getCFU(),
                    thread.getFlag(),
                    thread.getUser().getUniversity(),
                    thread.getPublicationDate(),
                    "",
                    thread.getUser().getFirstName() + " " + thread.getUser().getLastName()
            );
        }
        else{

            return new ThreadResponse(
                    thread.getID(),
                    thread.getContent(),
                    thread.getCFU(),
                    thread.getFlag(),
                    thread.getHost().getUniList(),
                    thread.getPublicationDate(),
                    thread.getHost().getHostName(),
                    ""
            );
        }
    }
}
