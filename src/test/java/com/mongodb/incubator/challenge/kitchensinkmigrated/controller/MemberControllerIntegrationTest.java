package com.mongodb.incubator.challenge.kitchensinkmigrated.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.incubator.challenge.kitchensinkmigrated.Repository.MemberRepository;
import com.mongodb.incubator.challenge.kitchensinkmigrated.model.Member;
import com.mongodb.incubator.challenge.kitchensinkmigrated.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Slf4j
public class MemberControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    @Transactional
    public void setUp() throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String dbProductName = jdbcTemplate.queryForObject("SELECT H2VERSION() FROM DUAL", String.class);
        assertTrue(dbProductName.startsWith("2."), "Test is not using H2 database");
        jdbcTemplate.execute("DELETE FROM MEMBERS");

        // Check if MEMBER table exists
        DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
        ResultSet tables = metaData.getTables(null, null, "MEMBERS", null);
        assertTrue(tables.next(), "MEMBERS table does not exist");
        log.info("Set up completed. MEMBERS table exists: {}", tables.next());
        // Clear the member table before each test
        jdbcTemplate.execute("DROP TABLE MEMBERS IF EXISTS");
        log.info("Set up completed. MEMBERS table exists: {}", tables.next());
    }

    @Test
    @Transactional
    public void testGetAllMembers() throws Exception {
        // Given
        Member member1 = new Member(1L, "John Doe", "john@example.com", "2126784567");

        memberService.saveMember(member1);
        Member member2 = new Member(2L, "Jane Doe", "jane@example.com", "2126784567");
        memberService.saveMember(member2);

        // When & Then
        mockMvc.perform(get("/rest/members"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("John Doe")))
                .andExpect(jsonPath("$[1].name", is("Jane Doe")));
    }

    @Test
    @Transactional
    public void testGetMemberById() throws Exception {
        // Given
        Member member = new Member(1L, "Test User", "test@example.com", "2126784567");
        Member savedMember = memberService.saveMember(member);

        // When & Then
        mockMvc.perform(get("/rest/member/{id}", savedMember.id()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Test User")))
                .andExpect(jsonPath("$.email", is("test@example.com")));
    }


@Test
@Transactional
public void testRegisterMember() throws Exception {
    // Verify the table is empty at the start
    log.info("Number of members count: {}", memberRepository.count());
    memberRepository.deleteAll();
    log.info("Deleted all, remaining count: {}", memberRepository.count());

    // Given
    String newMemberJson = "{'name': 'New User', 'email': 'new@example.com', 'phoneNumber': '2223334444'}";
    ObjectMapper objectMapper = new ObjectMapper();
    Member memberNew = new Member(null, "John Doe", "john@example.com", "2223334444");
    // When
    MvcResult result = mockMvc.perform(post("/rest/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(memberNew)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].name", is("John Doe")))
            .andExpect(jsonPath("$[0].email", is("john@example.com")))
            .andExpect(jsonPath("$[0].phoneNumber", is("2223334444")))
            .andReturn();
    log.info("Response: {}", result.getResponse().getContentAsString());

    // Then
    List<Member> membersAfterRegister = memberRepository.findAll();
    System.out.println("Members after register count: " + membersAfterRegister.size());
    membersAfterRegister.forEach(member -> System.out.println("Member: " + member.name() + ", " + member.email()));
    assertEquals(1, membersAfterRegister.size(), "There should be one member after registration");
    Member registeredMember = membersAfterRegister.get(0);
    assertEquals("John Doe", registeredMember.name());
    assertEquals("john@example.com", registeredMember.email());
    assertEquals("2223334444", registeredMember.phoneNumber());
}
}
