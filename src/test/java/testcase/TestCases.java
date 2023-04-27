package testcase;

import com.dalda.dalda_server.domain.comment.Comments;
import com.dalda.dalda_server.domain.tag.Tags;
import com.dalda.dalda_server.domain.tagcomment.TagComment;
import com.dalda.dalda_server.domain.user.Role.Role;
import com.dalda.dalda_server.domain.user.Users;
import com.dalda.dalda_server.domain.vote.Votes;

public class TestCases {
    public Users[] users;
    public Comments[] comments;
    public Tags[] tags;
    public TagComment[] tagComments;
    public Votes[] votes;

    public TestCases(int size) {
        users = new Users[size];
        comments = new Comments[size];
        tags = new Tags[size];
        tagComments = new TagComment[size];
        votes = new Votes[size];
    }

    public void makeOneTestCase(int num, long subCommentSum, long upvote, long upvoteSum) {
        users[num] = Users.builder()
                .name("user"+num)
                .email("user"+num+"@test.test")
                .role(Role.USER)
                .handle("testuser"+num)
                .picture("asdfasdf")
                .build();

        comments[num] = Comments.builder()
                .content("comment"+num)
                .description("comment"+num)
                .subCommentSum(subCommentSum)
                .upvote(upvote)
                .upvoteSum(upvoteSum)
                .build();

        tags[num] = Tags.builder()
                .name("tag"+num)
                .build();

        tagComments[num] = new TagComment();
        votes[num] = new Votes();

        comments[num].setUser(users[num]);
        tags[num].addTagComment(tagComments[num]);
        comments[num].addTagComment(tagComments[num]);
        comments[num].addVote(votes[num]);
        users[num].addVote(votes[num]);
    }
}