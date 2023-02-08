package com.dalda.dalda_server.service;

import com.dalda.dalda_server.domain.comment.CommentQueryRepository;
import com.dalda.dalda_server.domain.comment.Comments;
import com.dalda.dalda_server.response.CommentResponse;
import com.dalda.dalda_server.response.CommentsResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentQueryRepository commentQueryRepository;

    @Override
    public CommentsResponse findRootCommentListOrderByUpvote(Long page, Long size) {

        List<Comments> source = commentQueryRepository.findRootCommentListOrderByUpvote(page, size);
        Long count = commentQueryRepository.countRootCommentList();

        Boolean isEnded = (page + 1) * size >= count;
        Long currentSize = (long) source.size();
        List<CommentResponse> commentList = CommentsToResponse(source);

        return CommentsResponse.builder()
                .page(page)
                .size(currentSize)
                .isEnded(isEnded)
                .comments(commentList)
                .build();
    }

    private List<CommentResponse> CommentsToResponse(List<Comments> commentsList) {
        return commentsList.stream()
                .map(sourceComment -> {
                    String content = sourceComment.getContent();
                    Boolean isShortened = content.length() > 47;
                    if (isShortened) {
                        content = content.substring(0, 47) + "...";
                    }

                    return CommentResponse.builder()
                            .id(sourceComment.getId())
                            .userName(sourceComment.getUser().getName())
                            .createAt(sourceComment.getCreateDate().toString())
                            .isShortened(isShortened)
                            .content(content)
                            .tag(sourceComment.getTagList())
                            .subCommentSum(sourceComment.getSubCommentSum())
                            .upvoteSum(sourceComment.getUpvoteSum())
                            .build();
                })
                .toList();
    }
}
