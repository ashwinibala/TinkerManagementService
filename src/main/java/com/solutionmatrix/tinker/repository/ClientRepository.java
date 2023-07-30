package com.solutionmatrix.tinker.repository;

import com.solutionmatrix.tinker.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
//@PreAuthorize("hasAuthority('Admin')")
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findById(Long id);

    Optional<Client> findByUsername(String userName);

    List<Client> findByCategoryId(Long categoryId);

}
