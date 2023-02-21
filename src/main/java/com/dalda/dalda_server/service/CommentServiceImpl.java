package com.dalda.dalda_server.service;

import com.dalda.dalda_server.domain.comment.CommentRepository;
import com.dalda.dalda_server.domain.comment.Comments;
import com.dalda.dalda_server.response.CommentResponse;
import com.dalda.dalda_server.response.CommentsResponse;
import com.dalda.dalda_server.response.UserResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public CommentsResponse findRootCommentListOrderByUpvote(String pageStr, String sizeStr) {
        long page = 1L, size = 20L;
        if (pageStr != null && !pageStr.isEmpty()) {
            page = Long.parseLong(pageStr);
            if (page < 1) page = 1L;
        }
        if (sizeStr != null && !sizeStr.isEmpty()) {
            size = Long.parseLong(sizeStr);
            if (size < 1) size = 1L;
        }

        List<Comments> source = commentRepository.findRootCommentListOrderByUpvote(page - 1, size);
        Long count = commentRepository.countRootCommentList();

        Boolean isEnded = page * size >= count;
        Long currentSize = (long) source.size();
        List<CommentResponse> commentList = CommentsToResponse(source, null);

        return CommentsResponse.builder()
                .page(page)
                .size(currentSize)
                .isLast(isEnded)
                .list(commentList)
                .build();
    }

    @Override
    public CommentsResponse findSubCommentListOrderByDate(Long rootId, String pageStr, String sizeStr) {
        long page = 1L, size = 20L;
        if (pageStr != null && !pageStr.isEmpty()) {
            page = Long.parseLong(pageStr);
        }
        if (sizeStr != null && !sizeStr.isEmpty()) {
            size = Long.parseLong(sizeStr);
        }

        UserResponse rootUser = commentRepository.findById(rootId)
                .map(rootComment -> new UserResponse(rootComment.getUser()))
                .orElse(new UserResponse());
        List<Comments> source = commentRepository.findSubCommentListOrderByDate(rootId, page - 1, size);
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
                            .build();
                })
                .toList();
    }
}