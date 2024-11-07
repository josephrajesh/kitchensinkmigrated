/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mongodb.incubator.challenge.kitchensinkmigrated.controller;

import com.mongodb.incubator.challenge.kitchensinkmigrated.model.Member;
import com.mongodb.incubator.challenge.kitchensinkmigrated.service.MemberRegistrationService;
import com.mongodb.incubator.challenge.kitchensinkmigrated.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/rest")
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class MemberController {

    private final MemberRegistrationService memberRegistrationService;
    private final MemberService memberService;

    public MemberController(MemberRegistrationService memberRegistrationService, MemberService memberService) {
        this.memberRegistrationService = memberRegistrationService;
        this.memberService = memberService;
    }

    @GetMapping("/members")
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }
    @GetMapping("/member/{Id}")
    public ResponseEntity<?> getAMemberById(@PathVariable final Long Id) {
        Optional<Member> member = memberService.findMemberById(Id);
        if (member.isPresent()) {
            return ResponseEntity.ok(member.get());
        } else {
            log.info("Member with ID {} not found", Id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Member newMember) {
        try {
            List<Member>  memberList = memberRegistrationService.register(newMember);
            // Return a success message with 201 status
            return ResponseEntity.ok(memberList);
        } catch (DuplicateKeyException e) {
            String errorMessage = getRootErrorMessage(e);
            // Return error message with 500 status
            return new ResponseEntity<>(HttpStatus.CONFLICT); // Return a 409 Conflict status
        } catch (Throwable e) {
            String errorMessage = getRootErrorMessage(e);
            // Return error message with 500 status
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Utility method for extracting the root error message
    private String getRootErrorMessage(Throwable e) {
        String errorMessage = "Registration failed. See server log for more information.";
        if (e == null) {
            return errorMessage;
        }
        // Recursively find the root cause
        Throwable t = e;
        while (t != null) {
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
            log.error(errorMessage);
        }
        log.error("Registration failed {}", errorMessage, e);
        return errorMessage;
    }
}

