package it.younify.modules.sgtr.service;

import it.younify.core.exception.ResourceNotFoundException;
import it.younify.modules.sgac.model.entity.Host;
import it.younify.modules.sgac.model.entity.User;
import it.younify.modules.sgtr.model.dto.request.ThreadVoteRequest;
import it.younify.modules.sgtr.model.entity.Threads;
import it.younify.modules.sgtr.model.entity.ThreadsVote;
import it.younify.modules.sgtr.model.entity.VoteType;
import it.younify.modules.sgtr.repository.ThreadRepository;
import it.younify.modules.sgtr.repository.ThreadVoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * The {@code ThreadVoteService} class handles the business logic for voting on threads.
 * It manages the creation, modification, and deletion of votes, ensuring that the
 * thread's vote count (CFU) is updated accordingly.
 *
 * @see it.younify.modules.sgtr.repository.ThreadVoteRepository
 * @see it.younify.modules.sgtr.repository.ThreadRepository
 */
@Service
@RequiredArgsConstructor
public class ThreadVoteService {

    private final ThreadVoteRepository threadVoteRepository;
    private final ThreadRepository threadRepository;

    /**
     * Processes a vote request for a thread in a transactional context.
     * <p>
     * This method handles three main scenarios:
     * <ul>
     *     <li><b>New Vote:</b> If the user has not voted on the thread, a new vote is created.</li>
     *     <li><b>Change Vote:</b> If the user votes differently (e.g., upvotes a previously downvoted thread), the existing vote is updated.</li>
     *     <li><b>Remove Vote:</b> If the user casts the same vote again (e.g., upvotes an already upvoted thread), the vote is removed.</li>
     * </ul>
     * The thread's CFU count is adjusted based on the action taken.
     *
     * @param threadVoteRequest The request containing the thread ID and vote type.
     * @throws ResourceNotFoundException if the specified thread does not exist.
     */
    @Transactional(rollbackFor = Exception.class)
    public void vote(ThreadVoteRequest threadVoteRequest){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        VoteType accountVoteType = threadVoteRequest.getVoteType();

        Optional<Threads> optT = threadRepository.findById(threadVoteRequest.getThreadId());

        if(optT.isPresent()) {

            Threads thread = optT.get();

            if(principal instanceof User){

                Optional<ThreadsVote> vote = threadVoteRepository.findByThreadAndUser(thread, ((User) principal));

                if(vote.isPresent()) {

                    ThreadsVote threadsVote = vote.get();
                    changeVote(threadsVote, accountVoteType, thread);

                }
                else{

                    threadVoteRepository.save(
                            ThreadsVote.builder()
                                    .voteType(accountVoteType)
                                    .thread(thread)
                                    .user((User) principal)
                                    .build()
                    );

                    if(accountVoteType.equals(VoteType.UPVOTE)){

                        thread.setCFU(thread.getCFU() + 1);
                    }
                    else thread.setCFU(thread.getCFU() - 1);
                }

                threadRepository.save(thread);
            }
            else{

                if(principal instanceof Host){

                    Optional<ThreadsVote> vote = threadVoteRepository.findByThreadAndHost(thread, ((Host) principal));

                    if(vote.isPresent()) {

                        ThreadsVote threadsVote = vote.get();
                        changeVote(threadsVote, accountVoteType, thread);
                    }
                    else{

                        threadVoteRepository.save(
                                ThreadsVote.builder()
                                        .voteType(accountVoteType)
                                        .thread(thread)
                                        .host((Host) principal)
                                        .build()
                        );

                        if(accountVoteType.equals(VoteType.UPVOTE)){

                            thread.setCFU(thread.getCFU() + 1);
                        }
                        else thread.setCFU(thread.getCFU() - 1);
                    }

                    threadRepository.save(thread);
                }
            }
        } else throw new ResourceNotFoundException("Thread not found");
    }


    /**
     * Handles the logic for changing or removing an existing vote.
     *
     * @param threadsVote The existing vote to modify.
     * @param accountVoteType The new vote type from the user.
     * @param thread The thread being voted on.
     */
    private void changeVote(ThreadsVote threadsVote, VoteType accountVoteType, Threads thread){


        if(threadsVote.getVoteType().equals(accountVoteType)) {

            threadVoteRepository.delete(threadsVote);

            if(accountVoteType.equals(VoteType.UPVOTE)){

                thread.setCFU(thread.getCFU() - 1);
            }
            else thread.setCFU(thread.getCFU() + 1);
        }
        else{

            if(accountVoteType.equals(VoteType.UPVOTE)){

                thread.setCFU(thread.getCFU() + 2);
                threadsVote.setVoteType(VoteType.UPVOTE);

            }
            else{

                thread.setCFU(thread.getCFU() - 2);
                threadsVote.setVoteType(VoteType.DOWNVOTE);
            }

            threadVoteRepository.save(threadsVote);
        }
    }
}
