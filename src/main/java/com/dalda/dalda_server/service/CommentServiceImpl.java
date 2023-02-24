package com.dalda.dalda_server.service;

import com.dalda.dalda_server.config.auth.dto.SessionUser;
import com.dalda.dalda_server.domain.comment.CommentRepository;
import com.dalda.dalda_server.domain.comment.Comments;
import com.dalda.dalda_server.domain.user.UserRepository;
import com.dalda.dalda_server.domain.user.Users;
import com.dalda.dalda_server.domain.vote.VoteRepository;
import com.dalda.dalda_server.domain.vote.Votes;
import com.dalda.dalda_server.web.response.CommentResponse;
import com.dalda.dalda_server.web.response.CommentsResponse;
import com.dalda.dalda_server.web.response.UserResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;

    @Override
    public CommentsResponse findRootCommentListOrderByUpvote(String pageStr, String sizeStr, SessionUser sessionUser) {
        long page = 1L, size = 20L;
        if (pageStr != null && !pageStr.isEmpty()) {
            page = Long.parseLong(pageStr);
            if (page < 1) page = 1L;
        }
        if (sizeStr != null && !sizeStr.isEmpty()) {
            size = Long.parseLong(sizeStr);
            if (size < 1) size = 20L;
        }

        List<Comments> source = commentRepository.findRootCommentListOrderByUpvote(
                page - 1,
                size,
                sessionUser);
        List<CommentResponse> commentList = CommentsToResponse(source, null);

        Long count = commentRepository.countRootCommentList();
        Boolean isEnded = page * size >= count;
        Long currentSize = (long) source.size();

        return CommentsResponse.builder()
                .page(page)
                .size(currentSize)
                .isLast(isEnded)
                .list(commentList)
                .build();
    }

    @Override
    public CommentsResponse findSubCommentListOrderByDate(Long rootId, String pageStr, String sizeStr,
            SessionUser sessionUser) {
        long page = 1L, size = 20L;
        if (pageStr != null && !pageStr.isEmpty()) {
            page = Long.parseLong(pageStr);
            if (page < 1) page = 1L;
        }
        if (sizeStr != null && !sizeStr.isEmpty()) {
            size = Long.parseLong(sizeStr);
            if (size < 1) size = 20L;
        }

        List<Comments> source = commentRepository.findSubCommentListOrderByDate(
                rootId,
                page - 1, size,
                sessionUser);
        UserResponse rootUser = commentRepository.findById(rootId)
                .map(rootComment -> new UserResponse(rootComment.getUser()))
                .orElse(null);
        List<CommentResponse> commentList = CommentsToResponse(source, rootUser);

        Long count = commentRepository.countRootCommentList();
        Boolean isEnded = page * size >= count;
        Long currentSize = (long) source.size();

        return CommentsResponse.builder()
                .page(page)
                .size(currentSize)
                .isLast(isEnded)
                .list(commentList)
                .build();
    }

    @Transactional
    @Override
    public Long updateCommentVote(Long commentId, Long userId, String voteStr) {
        long vote = Long.parseLong(voteStr);

        Optional<Users> optionalUser = userRepository.findById(userId);
        Optional<Comments> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty() || optionalUser.isEmpty()) {
            return 0L;
        }

        Users user = optionalUser.get();
        Comments comment = optionalComment.get();
        Optional<Votes> oldVote = voteRepository.findByUserAndComment(user, comment);

        if (vote == 0 && oldVote.isPresent()) {
            voteRepository.delete(oldVote.get());

            commentRepository.decreaseUpvote(commentId);
            if (comment.getCommentRoot() != null) {
                commentRepository.decreaseUpvoteSum(comment.getCommentRoot());
            }
            else {
                commentRepository.decreaseUpvoteSum(commentId);
            }
        }
        else if (vote == 1 && oldVote.isEmpty()) {
            Votes newVote = new Votes();
            newVote.setUser(user);
            newVote.setComment(comment);
            voteRepository.save(newVote);

            commentRepository.increaseUpvote(commentId);
            if (comment.getCommentRoot() != null) {
                commentRepository.increaseUpvoteSum(comment.getCommentRoot());
            }
            else {
                commentRepository.increaseUpvoteSum(commentId);
            }
        }
        else {
            return 0L;
        }

        return 1L;
    }

    private List<CommentResponse> CommentsToResponse(List<Comments> commentsList, UserResponse rootUser) {
        return commentsList.stream()
                .map(sourceComment -> {
                    boolean isModified = !sourceComment.getCreateDate()
                            .isEqual(sourceComment.getModifiedDate());

                    return CommentResponse.builder()
                            .id(sourceComment.getId())
                            .writer(new UserResponse(sourceComment.getUser()))
                            .mentionUser(rootUser)
                            .createdAt(sourceComment.getCreateDate().toString())
                            .updatedAt(sourceComment.getModifiedDate().toString())
                            .isModified(isModified)
                            .content(sourceComment.getContent())
                            .tags(sourceComment.getTagList())
                            .subCommentsCount(sourceComment.getSubCommentSum())
                            .likes(sourceComment.getUpvoteSum())
                            .isLike(sourceComment.getIsLike())
                            .build();
                })
                .toList();
    }
}