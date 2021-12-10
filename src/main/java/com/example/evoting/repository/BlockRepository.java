package com.example.evoting.repository;

import com.example.evoting.model.Block;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepository extends CrudRepository<Block, Long> {
    Block findBlockById(Block block);
}
