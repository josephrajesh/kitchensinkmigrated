package com.mongodb.incubator.challenge.kitchensinkmigrated.controller;

import com.mongodb.incubator.challenge.kitchensinkmigrated.model.Member;
import com.mongodb.incubator.challenge.kitchensinkmigrated.service.MemberRegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MemberControllerUnitTest {

    @Mock
    private MemberRegistrationService memberRegistrationService;

    @InjectMocks
    private MemberController memberController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterSuccess() throws Exception {
        // Arrange
        Member newMember = new Member(1L, "John Doe", "john.doe@email.com", "1112228888");
        List<Member> memberList = new ArrayList<>();
        memberList.add(newMember);

        when(memberRegistrationService.register(any(Member.class))).thenReturn(memberList);

        // Act
        ResponseEntity<?> response = memberController.register(newMember);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(memberList, response.getBody());
        verify(memberRegistrationService, times(1)).register(newMember);
    }

    @Test
    void testRegisterFailure() throws Exception {
        // Arrange
        Member newMember = new Member(1L, "John Doe", "john.doe@email.com", "1112228888");
        String errorMessage = "Registration failed";

        when(memberRegistrationService.register(any(Member.class))).thenThrow(new RuntimeException(errorMessage));

        // Act
        ResponseEntity<?> response = memberController.register(newMember);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
        verify(memberRegistrationService, times(1)).register(newMember);
    }
}
