package com.example.evoting.repository;

import com.example.evoting.model.Candidate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends CrudRepository<Candidate, Long> {

    Candidate findCandidateById(Long id);
}
