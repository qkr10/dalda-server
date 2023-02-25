package com.dalda.dalda_server.domain.comment;

import java.util.Comparator;

public class ComparatorByUpvote implements Comparator<Comments> {

    @Override
    public int compare(Comments o1, Comments o2) {
        return - o1.getUpvoteSum().compareTo(o2.getUpvoteSum());
    }
}
