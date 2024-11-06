package com.mongodb.incubator.challenge.kitchensinkmigrated.Repository;

import com.mongodb.incubator.challenge.kitchensinkmigrated.model.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends MongoRepository<Member, Long> {
    public Optional<Member>  findFirstByOrderByIdDesc(Long id);
}

