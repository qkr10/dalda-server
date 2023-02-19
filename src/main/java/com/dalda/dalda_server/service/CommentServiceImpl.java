package com.dalda.dalda_server.service;

import com.dalda.dalda_server.domain.comment.CommentQueryRepository;
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

    private final CommentQueryRepository commentQueryRepository;

    @Override
    public CommentsResponse findRootCommentListOrderByUpvote(String pageStr, String sizeStr) {
        long page = 0L, size = 20L;
        if (pageStr != null && !pageStr.isEmpty()) {
            page = Long.parseLong(pageStr) - 1;
        }
        if (sizeStr != null && !sizeStr.isEmpty()) {
            size = Long.parseLong(sizeStr);
        }

        List<Comments> source = commentQueryRepository.findRootCommentListOrderByUpvote(page, size);
        Long count = commentQueryRepository.countRootCommentList();

        Boolean isEnded = (page + 1) * size >= count;
        Long currentSize = (long) source.size();
        List<CommentResponse> commentList = CommentsToResponse(source);

        return CommentsResponse.builder()
                .page(page)
                .size(currentSize)
                .isLast(isEnded)
                .comments(commentList)
                .build();
    }

    private List<CommentResponse> CommentsToResponse(List<Comments> commentsList) {
        return commentsList.stream()
                .map(sourceComment -> {
                    boolean isModified = !sourceComment.getCreateDate()
                            .isEqual(sourceComment.getModifiedDate());

                    return CommentResponse.builder()
                            .id(sourceComment.getId())
                            .writer(new UserResponse(sourceComment.getUser()))
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