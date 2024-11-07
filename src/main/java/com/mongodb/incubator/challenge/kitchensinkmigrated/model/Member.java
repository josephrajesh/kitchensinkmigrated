package com.mongodb.incubator.challenge.kitchensinkmigrated.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "members")
public record Member(
        @Id
        @Field(name = "_id")
        Long id,
        @NotBlank
        @Size(min = 1, max = 25)
        String name,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$",
                message = "Invalid phone number")
        @Field(name = "phone_number")
        String phoneNumber
) {
        public Member withId(Long id) {
                return new Member(id, this.name, this.email, this.phoneNumber);
        }
}
