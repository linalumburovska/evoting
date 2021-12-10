package com.example.evoting.repository;

import com.example.evoting.model.Frequency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FrequencyRepository extends CrudRepository<Frequency, Long> {
    Frequency findFrequencyByCandidateId(Long id);
}