package com.dalda.dalda_server.domain.comment;

import java.util.Comparator;

public class CommentsComparatorsFactory {
    public enum CommentsComparatorsType {
        UPVOTE,
        DATE
    }

    public static class ComparatorByUpvote implements Comparator<Comments> {
        @Override
        public int compare(Comments o1, Comments o2) {
            return - o1.getUpvoteSum().compareTo(o2.getUpvoteSum());
        }
    }

    static class ComparatorByDate implements Comparator<Comments> {
        @Override
        public int compare(Comments o1, Comments o2) {
            return o1.getCreateDate().compareTo(o2.getCreateDate());
        }
    }

    public static Comparator<Comments> getComparator(CommentsComparatorsType type) {
        return switch (type) {
            case UPVOTE -> new ComparatorByUpvote();
            case DATE -> new ComparatorByDate();
        };
    }
}