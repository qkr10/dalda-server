package com.dalda.dalda_server.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dalda.dalda_server.config.auth.dto.UserPrincipal;
import com.dalda.dalda_server.domain.comment.CommentRepository;
import com.dalda.dalda_server.domain.tag.TagRepository;
import com.dalda.dalda_server.domain.tagcomment.TagCommentRepository;
import com.dalda.dalda_server.domain.user.UserRepository;
import com.dalda.dalda_server.domain.vote.VoteRepository;
import com.dalda.dalda_server.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import testcase.TestCases;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TagCommentRepository tagCommentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private CommentService commentService;

    private TestCases testCases;

    @BeforeEach
    public void beforeEach() {
        testCases = new TestCases(2);
        testCases.makeOneTestCase(0, 1, 1, 2);
        testCases.makeOneTestCase(1, 0, 1, 1);
        testCases.comments[1].setRootComment(testCases.comments[0]);

        userRepository.saveAll(List.of(testCases.users));
        commentRepository.saveAll(List.of(testCases.comments));
        tagRepository.saveAll(List.of(testCases.tags));
        tagCommentRepository.saveAll(List.of(testCases.tagComments));
        voteRepository.saveAll(List.of(testCases.votes));
    }

    @AfterEach
    public void afterEach() {
        voteRepository.deleteAll();
        tagCommentRepository.deleteAll();
        tagRepository.deleteAll();
        commentRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("get root comments : 댓글에 이미 좋아요를 누른 사람이 요청")
    public void testGetComment1() throws Exception {
        //request
        String page = "1", size = "1";
        var requestURL = "/comments?page="+page+"&size="+size;
        var requestPrincipal = new UserPrincipal(
                testCases.users[0],
                new HashMap<String, Object>(),
                Collections.singleton(new SimpleGrantedAuthority(testCases.users[0].getRoleKey())));

        //response
        var responseComments = commentService.findRootCommentListOrderByUpvote(page, size, requestPrincipal);
        var responseBody = new ObjectMapper().writeValueAsString(responseComments);
        var isLike = jsonPath("$.list[0].isLike").value(true);

        //perform
        mockMvc.perform(get(requestURL).with(oauth2Login().oauth2User(requestPrincipal)))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody))
                .andExpect(isLike);
    }

    @Test
    @DisplayName("get root comments : 로그인 하지 않고 요청")
    public void testGetComment2() throws Exception {
        //request
        String page = "1", size = "1";
        var requestURL = "/comments?page="+page+"&size="+size;

        //response
        var responseComments = commentService.findRootCommentListOrderByUpvote(page, size, null);
        var responseBody = new ObjectMapper().writeValueAsString(responseComments);
        var isLike = jsonPath("$.list[0].isLike").value(false);

        //perform
        mockMvc.perform(get(requestURL))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody))
                .andExpect(isLike);
    }

}
