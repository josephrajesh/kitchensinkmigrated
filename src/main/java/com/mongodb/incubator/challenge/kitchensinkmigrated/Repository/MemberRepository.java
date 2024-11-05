package com.mongodb.incubator.challenge.kitchensinkmigrated.Repository;

import com.mongodb.incubator.challenge.kitchensinkmigrated.model.Member;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends MongoRepository<Member, Long> {

    // You can define custom query methods here
    List<Member> findByName(String name);
    @Query("SELECT m FROM Member m WHERE m.email = :email")
    public Optional<Member> findByEmail(@Param("email") String email);
    @Query(value="{'_id': ?0}.limit(1)")//Sort didn't work here find().sort({'_id':-1}).limit(1)
    public Optional<Member> findFirstByIdGreaterThan(Long id, Sort by);
}

