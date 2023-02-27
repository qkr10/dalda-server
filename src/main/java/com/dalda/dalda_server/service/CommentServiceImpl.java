package com.dalda.dalda_server.service;

import com.dalda.dalda_server.config.auth.dto.SessionUser;
import com.dalda.dalda_server.domain.comment.CommentRepository;
import com.dalda.dalda_server.domain.comment.Comments;
import com.dalda.dalda_server.domain.tag.TagRepository;
import com.dalda.dalda_server.domain.tag.Tags;
import com.dalda.dalda_server.domain.tagcomment.TagComment;
import com.dalda.dalda_server.domain.tagcomment.TagCommentRepository;
import com.dalda.dalda_server.domain.user.UserRepository;
import com.dalda.dalda_server.domain.user.Users;
import com.dalda.dalda_server.domain.vote.VoteRepository;
import com.dalda.dalda_server.domain.vote.Votes;
import com.dalda.dalda_server.web.request.CommentRequest;
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
    private final TagRepository tagRepository;
    private final TagCommentRepository tagCommentRepository;

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
        List<CommentResponse> commentList = CommentsToResponse(source);

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
        List<CommentResponse> commentList = CommentsToResponse(source);

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
    public Long updateCommentVote(Long commentId, SessionUser sessionUser, String voteStr) {
        Optional<Users> optionalUser = userRepository.findById(sessionUser.getId());
        Optional<Comments> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty() || optionalUser.isEmpty()) {
            return 0L;
        }
        Users user = optionalUser.get();
        Comments comment = optionalComment.get();

        long vote = Long.parseLong(voteStr);
        Optional<Votes> oldVote = voteRepository.findByUserAndComment(user, comment);

        if (vote == 0 && oldVote.isPresent()) {
            voteRepository.delete(oldVote.get());

            commentRepository.decreaseUpvote(commentId);
            commentRepository.decreaseUpvoteSum(commentId);
            if (comment.getRootComment() != null) {
                commentRepository.decreaseUpvoteSum(comment.getRootComment().getId());
            }
        }
        else if (vote == 1 && oldVote.isEmpty()) {
            Votes newVote = new Votes();
            newVote.setUser(user);
            newVote.setComment(comment);
            voteRepository.save(newVote);

            commentRepository.increaseUpvote(commentId);
            commentRepository.increaseUpvoteSum(commentId);
            if (comment.getRootComment() != null) {
                commentRepository.increaseUpvoteSum(comment.getRootComment().getId());
            }
        }
        else {
            return 0L;
        }

        return 1L;
    }

    @Transactional
    @Override
    public Long createComment(SessionUser sessionUser, CommentRequest commentRequest) {
        Optional<Users> optionalWriter = userRepository.findById(sessionUser.getId());
        if (optionalWriter.isEmpty()) {
            return 0L;
        }
        Users writer = optionalWriter.get();

        Comments rootComment = null;
        Users mentionUser = null;
        if (commentRequest.getRootCommentId() != null) {
            rootComment = commentRepository
                    .findById(commentRequest.getRootCommentId())
                    .orElse(null);

            if (commentRequest.getMentionUserHandle() != null) {
                mentionUser = userRepository
                        .findByHandle(commentRequest.getMentionUserHandle())
                        .orElse(null);
            }
        }

        Comments newComment = Comments.builder()
                .subCommentSum(0L)
                .upvote(0L)
                .upvoteSum(0L)
                .content(commentRequest.getContent())
                .build();
        newComment.setUser(writer);
        newComment.setRootComment(rootComment);
        newComment.setMentionUser(mentionUser);
        commentRepository.save(newComment);

        return saveTags(commentRequest.getTags(), newComment);
    }

    @Override
    @Transactional
    public Long updateComment(Long commentId, SessionUser sessionUser, CommentRequest commentRequest) {
        Optional<Users> optionalUser = userRepository.findById(sessionUser.getId());
        Optional<Comments> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty() || optionalUser.isEmpty()) {
            return 0L;
        }
        Users user = optionalUser.get();
        Comments comment = optionalComment.get();

        if (!user.getId().equals(comment.getUser().getId()))
            return 0L;

        long result = commentRepository.updateContent(
                comment.getId(),
                commentRequest.getContent());

        result += saveTags(commentRequest.getTags(), comment);

        return result;
    }

    @Override
    @Transactional
    public Long deleteComment(Long commentId, SessionUser sessionUser) {
        Optional<Users> optionalUser = userRepository.findById(sessionUser.getId());
        Optional<Comments> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty() || optionalUser.isEmpty()) {
            return 0L;
        }
        Users user = optionalUser.get();
        Comments comment = optionalComment.get();

        if (!user.getId().equals(comment.getUser().getId()))
            return 0L;

        return deleteComment(comment);
    }

    private Long deleteComment(Comments comment) {
        long result = comment.getTagComments().stream()
                .map(tagComment -> {
                    Tags tag = tagComment.getTag();
                    if (tag.getTagComments().size() == 1)
                        tagRepository.delete(tag);

                    tagCommentRepository.delete(tagComment);
                    return 1L;
                })
                .reduce(Math::addExact)
                .orElse(0L);

        result += comment.getSubComments().stream()
                .map(this::deleteComment)
                .reduce(Math::addExact)
                .orElse(0L);

        commentRepository.delete(comment);
        return result+1;
    }

    private Long saveTags(List<String> tags, Comments comment) {
        return tags
                .stream()
                .map(tagName -> {
                    Optional<Tags> oldTag = tagRepository.findByName(tagName);
                    Tags tag = oldTag.orElseGet(
                            () -> tagRepository.save(Tags.builder().name(tagName).build()));

                    TagComment tagComment = new TagComment();
                    tagComment.setComment(comment);
                    tagComment.setTag(tag);
                    tagCommentRepository.save(tagComment);
                    return 1L;
                })
                .reduce(Math::addExact)
                .orElse(0L);
    }

    private List<CommentResponse> CommentsToResponse(List<Comments> commentsList) {
        return commentsList.stream()
                .map(sourceComment -> {
                    boolean isModified = !sourceComment.getCreateDate()
                            .isEqual(sourceComment.getModifiedDate());
                    UserResponse mentionUser = null;
                    if (null != sourceComment.getMentionUser())
                        mentionUser = new UserResponse(sourceComment.getMentionUser());

                    return CommentResponse.builder()
                            .id(sourceComment.getId())
                            .writer(new UserResponse(sourceComment.getUser()))
                            .mentionUser(mentionUser)
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