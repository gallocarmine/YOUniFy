package it.younify.modules.sgtr.service;

import it.younify.core.exception.ResourceNotFoundException;
import it.younify.modules.sgac.model.entity.Host;
import it.younify.modules.sgac.model.entity.User;
import it.younify.modules.sgtr.model.dto.request.CommentPostRequest;
import it.younify.modules.sgtr.model.entity.Comment;
import it.younify.modules.sgtr.model.entity.Threads;
import it.younify.modules.sgtr.repository.CommentRepository;
import it.younify.modules.sgtr.repository.ThreadRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * The {@code CommentPostService} class is responsible for creating and persisting new comments on threads.
 * It handles the logic of associating a new comment with its parent thread and its author,
 * who can be either a {@link User} or a {@link Host}.
 *
 * @see it.younify.modules.sgtr.repository.CommentRepository
 * @see it.younify.modules.sgtr.repository.ThreadRepository
 */
@Service
@RequiredArgsConstructor
public class CommentPostService {

    private final ThreadRepository threadRepository;
    private final CommentRepository commentRepository;

    /**
     * Creates and saves a new comment in a transactional context.
     * This method first finds the parent thread and then determines the author of the comment
     * from the current security context before persisting a new {@link Comment} entity.
     *
     * @param commentPostRequest The request containing the comment's content and parent thread ID.
     * @throws ResourceNotFoundException if the parent thread with the given ID does not exist.
     */
    @Transactional(rollbackFor = Exception.class)
    public void post(@NotNull CommentPostRequest commentPostRequest) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String content = commentPostRequest.getContent();
        Long id = commentPostRequest.getThreadId();


        Optional<Threads> optT = threadRepository.findById(id);

        if (optT.isPresent()) {

            Threads thread = optT.get();

            if (principal instanceof User) {

                commentRepository.save(
                        Comment.builder()
                                .CFU(0)
                                .content(content)
                                .publicationDate(Instant.now())
                                .threadId(thread)
                                .user((User) principal)
                                .build()
                );
            }
            else {

                if (principal instanceof Host) {

                    commentRepository.save(
                            Comment.builder()
                                    .CFU(0)
                                    .content(content)
                                    .publicationDate(Instant.now())
                                    .threadId(thread)
                                    .host((Host) principal)
                                    .build()
                    );
                }
            }
        }
        else throw new ResourceNotFoundException("Thread not found");
    }
}