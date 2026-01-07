package it.younify.modules.sgtr.service;

import it.younify.core.exception.ResourceNotFoundException;
import it.younify.core.utils.SecurityUtils;
import it.younify.modules.sgac.model.entity.Host;
import it.younify.modules.sgac.model.entity.User;
import it.younify.modules.sgtr.model.entity.Comment;
import it.younify.modules.sgtr.repository.CommentRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * The {@code CommentDeleteService} class is responsible for handling the deletion of comments.
 * It includes authorization logic to ensure that only the comment's author or an administrator
 * can perform the deletion.
 *
 * @see it.younify.modules.sgtr.repository.CommentRepository
 * @see it.younify.core.utils.SecurityUtils
 */
@Service
@RequiredArgsConstructor
public class CommentDeleteService {

    private final CommentRepository commentRepository;

    /**
     * Deletes a comment by its ID in a transactional context.
     * <p>
     * This method performs a critical security check to verify that the current authenticated principal
     * is either the author of the comment or has administrative privileges.
     *
     * @param commentId The ID of the comment to be deleted.
     * @throws ResourceNotFoundException if the comment with the given ID does not exist.
     * @throws AccessDeniedException if the current user is not authorized to delete the comment.
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(@NotNull Long commentId) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Comment> optC = commentRepository.findByID(commentId);

        if(optC.isPresent()) {

            Comment comment = optC.get();

            if(principal instanceof User) {

                User userP = (User) principal;
                User userC = comment.getUser();

                if((userP.getID()).equals(userC.getID())){

                    commentRepository.delete(comment);
                }
            }
            else{

                if(principal instanceof Host){

                    Host hostP = (Host) principal;
                    Host hostC = comment.getHost();

                    if((hostP.getID()).equals(hostC.getID())){

                        commentRepository.delete(comment);
                    }
                }
                else {

                    if (SecurityUtils.isAdmin()) {

                        commentRepository.delete(comment);
                    }
                    else throw new AccessDeniedException("Access is denied");
                }
            }
        }
        else throw new ResourceNotFoundException("Comment not found");
    }
}
