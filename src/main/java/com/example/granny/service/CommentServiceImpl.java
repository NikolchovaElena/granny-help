package com.example.granny.service;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.domain.entities.Cause;
import com.example.granny.domain.entities.Comment;
import com.example.granny.domain.entities.User;
import com.example.granny.domain.models.view.CommentViewModel;
import com.example.granny.repository.CauseRepository;
import com.example.granny.repository.CommentRepository;
import com.example.granny.repository.UserRepository;
import com.example.granny.service.api.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CauseRepository causeRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, CauseRepository causeRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.causeRepository = causeRepository;
    }

    @Override
    public CommentViewModel create(String commentContent, String email, Integer causeId) {
        if (commentContent.isEmpty()) {
            throw GlobalConstants.COMMENT_CANNOT_BE_EMPTY;
        }
        User author = userRepository.findByEmail(email).orElseThrow(
                () -> GlobalConstants.NO_USER_WITH_THAT_EXCEPTION);
        Cause cause = causeRepository.findById(causeId).orElseThrow(
                () -> GlobalConstants.NO_CAUSE_WITH_THAT_EXCEPTION);
        Comment comment = commentRepository.saveAndFlush(new Comment(commentContent, author, cause));

        return mapCommentModel(comment);
    }

    @Override
    public List<CommentViewModel> findAll(Integer causeId) {
        List<Comment> comments = commentRepository.findAll(causeId);
        return comments.stream()
                .map(c -> {
                    return mapCommentModel(c);
                }).collect(Collectors.toList());
    }

    @Override
    public void deleteAll(Integer causeId) {
        commentRepository.deleteAll(
                commentRepository.findAll(causeId));
    }

    @Override
    public void delete(Integer commentId) {
        commentRepository.delete(commentId);
    }

    private CommentViewModel mapCommentModel(Comment comment) {
        String name = comment.getAuthor().getFirstName() + ' ' + comment.getAuthor().getLastName();
        String date = comment.getPublishingDate().format(DateTimeFormatter.ofPattern("dd.MMM.yyyy HH:mm"));

        return new CommentViewModel(comment.getId(), date, comment.getComment(), name);
    }
}