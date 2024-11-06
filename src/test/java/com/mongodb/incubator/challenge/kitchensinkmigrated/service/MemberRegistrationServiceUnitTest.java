package com.mongodb.incubator.challenge.kitchensinkmigrated.service;

import com.mongodb.incubator.challenge.kitchensinkmigrated.Repository.MemberRepository;
import com.mongodb.incubator.challenge.kitchensinkmigrated.model.Member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MemberRegistrationServiceUnitTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private MemberService memberService;

    @InjectMocks
    private MemberRegistrationService memberRegistrationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterNewMember() throws Exception {
        // Arrange
        Member newMember = new Member(1L, "John Doe", "john.doe@example.com", "4445557777");

        when(memberRepository.save(any(Member.class))).thenReturn(newMember);
        when(memberService.getAllMembers()).thenReturn(List.of(newMember));

        // Act
        List<Member> result = memberRegistrationService.register(newMember);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(newMember, result.get(0));
        verify(memberRepository, times(1)).save(newMember);
    }

    @Test
    void testRegisterNullMember() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> memberRegistrationService.register((Member) null));
        verify(memberRepository, never()).save(any(Member.class));
    }
}
