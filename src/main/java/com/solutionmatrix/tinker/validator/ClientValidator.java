package com.solutionmatrix.tinker.validator;

import com.solutionmatrix.tinker.model.entity.Category;
import com.solutionmatrix.tinker.model.entity.Client;
import com.solutionmatrix.tinker.repository.CategoryRepository;
import com.solutionmatrix.tinker.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ClientValidator {
    private ClientRepository clientRepository;

    private CategoryRepository categoryRepository;

    public void validateClientPostRequest(Client client) {
        Optional<Client> clientUserNameExists = clientRepository.findByUsername(client.getUsername());
        if (clientUserNameExists.isPresent()) {
            throw new RuntimeException("UserName Already exists");
        }
        Optional<Category> clientCategoryExists = categoryRepository.findById(client.getCategoryId());
        if (clientCategoryExists.isEmpty()){
            throw new RuntimeException("The Category doesnot exists");
        }
    }
}
