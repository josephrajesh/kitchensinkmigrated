package com.mongodb.incubator.challenge.kitchensinkmigrated.Repository;

import com.mongodb.incubator.challenge.kitchensinkmigrated.model.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends MongoRepository<Member, Long> {

    // You can define custom query methods here
    List<Member> findByName(String name);
    public Optional<Member>  findFirstByOrderByIdDesc(Long id);


}

