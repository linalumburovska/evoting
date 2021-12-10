package com.example.evoting.controller;

import com.example.evoting.model.Candidate;
import com.example.evoting.model.Frequency;
import com.example.evoting.repository.CandidateRepository;
import com.example.evoting.repository.FrequencyRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins =  "http://localhost:4200")
public class CandidateController {

    private final CandidateRepository candidateRepository;
    private final FrequencyRepository frequencyRepository;

    public CandidateController(CandidateRepository candidateRepository, FrequencyRepository frequencyRepository) {
        this.candidateRepository = candidateRepository;
        this.frequencyRepository = frequencyRepository;
    }

    @GetMapping("/candidates")
    public List<Candidate> getAllCandidates() {
        return (List<Candidate>) candidateRepository.findAll();
    }

    @PostMapping("/candidates/create")
    void addCandidate(@RequestBody Candidate candidate) {
        candidateRepository.save(candidate);
        Frequency f = new Frequency(candidate.getId(), 0);
        frequencyRepository.save(f);
    }

    @PutMapping("/candidates/{id}")
    void updateCandidateById(@RequestBody String name, @PathVariable String id) {
        Candidate candidate = candidateRepository.findCandidateById(Long.parseLong(id));
        candidate.setName(name);
        candidateRepository.save(candidate);
    }

    @DeleteMapping("/candidates/{id}")
    void deleteCandidateById(@PathVariable String id) {
        Candidate candidate = candidateRepository.findCandidateById(Long.parseLong(id));
        candidateRepository.delete(candidate);
    }

    @GetMapping("/candidates/votes/{id}")
    public long getVotesForCandidate(@PathVariable String id) {
        Frequency f = frequencyRepository.findFrequencyByCandidateId(Long.parseLong(id));
        return f.getCounter();
    }

}