package com.solutionmatrix.tinker.service;

import com.solutionmatrix.tinker.model.entity.Client;
import com.solutionmatrix.tinker.model.entity.ClientAvailability;
import com.solutionmatrix.tinker.repository.ClientAvailabilityRepository;
import com.solutionmatrix.tinker.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientAvailabilityService {

        private final ClientAvailabilityRepository clientAvailabilityRepository;

        private final ClientRepository clientRepository;

        @Transactional
        public List<Client> getClientAvailability(Long categoryId, String date, Long timeslotId) {
                List<Client> availableClients = new ArrayList<>();
                try {
                        List<Client> clients = clientRepository.findByCategoryId(categoryId);
                        for (Client client : clients) {
                                Optional<ClientAvailability> clientAvailability = clientAvailabilityRepository.findByClientIdAndDateAndTimeslotIdAndStatusId(client.getId(), date, timeslotId, 1L);
                                if (clientAvailability.isPresent()) {
                                        availableClients.add(client);
                                }
                        }
                        return availableClients;
                } catch(Exception e){
                        throw new RuntimeException();
                }
        }

        public void addClientAvailability(Long clientId) {
                try {
                        LocalDate localDate = LocalDate.now();
                        for (int i = 0; i < 7; i++) {
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                localDate = localDate.plus(1, ChronoUnit.DAYS);
                                for (long j = 1; j <= 5; j++) {
                                        ClientAvailability clientAvailability = ClientAvailability.builder()
                                                .clientId(clientId)
                                                .date(localDate.format(formatter))
                                                .timeslotId(j)
                                                .statusId(1L)
                                                .build();
                                        clientAvailabilityRepository.save(clientAvailability);
                                }
                        }
                } catch(Exception e) {
                        throw new RuntimeException();
                }
        }

        public void updateClientAvailability(Long clientId, String date, Long timeslotId, Long status) {
                try {
                        Optional<ClientAvailability> clientAvailability;
                        if(status == 1L) {
                                clientAvailability = clientAvailabilityRepository.findByClientIdAndDateAndTimeslotIdAndStatusId(clientId, date, timeslotId, 2L);
                        } else {
                                clientAvailability = clientAvailabilityRepository.findByClientIdAndDateAndTimeslotIdAndStatusId(clientId, date, timeslotId, 1L);
                        }
                   if (clientAvailability.isPresent()){
                           ClientAvailability availability = clientAvailability.get();
                           availability.setStatusId(status);
                           clientAvailabilityRepository.save(availability);
                   }
                } catch(Exception e) {
                        throw new RuntimeException();
                }
        }
}
