package com.mongodb.incubator.challenge.kitchensinkmigrated.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.incubator.challenge.kitchensinkmigrated.Repository.MemberRepository;
import com.mongodb.incubator.challenge.kitchensinkmigrated.model.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Slf4j
public class MemberControllerIntegrationMockRepoTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MemberRepository memberRepository;

    @BeforeEach
    @Transactional
    public void setUp() throws SQLException {

    }

    @Test
    @Transactional
    public void testGetAllMembers() throws Exception {
        // Given
        Member member1 = new Member(1L, "GetAll Member1", "getall.member1@example.com", "2126784567");
        when(memberRepository.save(member1)).thenReturn(member1);
        Member member2 = new Member(2L, "GetAll Member2", "getall.member2@example.com", "2126784567");
        when(memberRepository.findAll()).thenReturn(List.of(member1, member2));

        // When & Then
        mockMvc.perform(get("/rest/members"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("GetAll Member1")))
                .andExpect(jsonPath("$[1].name", is("GetAll Member2")));
    }

    @Test
    @Transactional
    public void testGetMemberById() throws Exception {
        // Given
        Member member = new Member(1L, "GetBy MebmberId", "getby.mebmberid@example.com", "2126784567");
        when(memberRepository.save(member)).thenReturn(member);
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        // When & Then
        mockMvc.perform(get("/rest/member/{id}", member.id()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("GetBy MebmberId")))
                .andExpect(jsonPath("$.email", is("getby.mebmberid@example.com")));
    }


@Test
@Transactional
public void testRegisterMember() throws Exception {
    // Given
    ObjectMapper objectMapper = new ObjectMapper();
    Member memberNew = new Member(null, "Register NewMember", "register.newmember@example.com", "2223334444");
    when(memberRepository.save(memberNew)).thenReturn(memberNew);
    when(memberRepository.findAll()).thenReturn(List.of(memberNew));

    // When
    MvcResult result = mockMvc.perform(post("/rest/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(memberNew)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].name", is("Register NewMember")))
            .andExpect(jsonPath("$[0].email", is("register.newmember@example.com")))
            .andExpect(jsonPath("$[0].phoneNumber", is("2223334444")))
            .andExpect(jsonPath("$", hasSize(1)))
            .andReturn();
    log.info("Response: {}", result.getResponse().getContentAsString());
}
}
