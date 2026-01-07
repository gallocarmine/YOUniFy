package it.younify.modules.sgtr.service;

import it.younify.modules.sgac.model.entity.Host;
import it.younify.modules.sgac.model.entity.User;
import it.younify.modules.sgtr.model.dto.request.ThreadPostRequest;
import it.younify.modules.sgtr.model.entity.FlagCategory;
import it.younify.modules.sgtr.model.entity.Threads;
import it.younify.modules.sgtr.repository.ThreadRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;


/**
 * The {@code ThreadPostService} class is responsible for creating and persisting new threads.
 * It handles the logic of associating a new thread with its author, who can be either a
 * {@link User} or a {@link Host}.
 *
 * @see it.younify.modules.sgtr.repository.ThreadRepository
 */
@Service
@RequiredArgsConstructor
public class ThreadPostService {

    private final ThreadRepository threadRepository;

    /**
     * Creates and saves a new thread in a transactional context.
     * This method determines the author of the thread from the current security context
     * and persists a new {@link Threads} entity.
     *
     * @param threadPostRequest The request containing the thread's content and hsi flag.
     */
    @Transactional(rollbackFor = Exception.class)
    public void post(@NotNull ThreadPostRequest threadPostRequest) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        FlagCategory flag = FlagCategory.valueOf(threadPostRequest.getFlag());

        String content = threadPostRequest.getContent();

        if (principal instanceof User) {

            threadRepository.save(
                    Threads.builder()
                            .content(content)
                            .CFU(0)
                            .flag(flag)
                            .publicationDate(Instant.now())
                            .user((User) principal)
                            .build()
            );
        }
        else {

            if (principal instanceof Host) {

                threadRepository.save(
                        Threads.builder()
                                .content(content)
                                .CFU(0)
                                .flag(flag)
                                .publicationDate(Instant.now())
                                .host((Host) principal)
                                .build()
                );
            }
        }
    }
}
