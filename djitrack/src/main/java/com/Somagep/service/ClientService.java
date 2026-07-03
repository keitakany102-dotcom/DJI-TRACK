package com.Somagep.service;

import com.Somagep.entity.Client;
import com.Somagep.entity.Role;
import com.Somagep.entity.User;
import com.Somagep.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public Client createClient(Client client, String username, String password) {
        String numero = "CLT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        client.setNumeroClient(numero);
        client.setDateCreation(new Date());
        client.setActif(true);

        User user = userService.registerUser(
                username,
                password,
                client.getEmail(),
                client.getNom(),
                client.getPrenom(),
                Role.CLIENT
        );
        client.setUser(user);
        return clientRepository.save(client);
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    public Optional<Client> findByUser(User user) {
        return clientRepository.findByUser(user);
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public List<Client> searchClients(String keyword) {
        return clientRepository.findByNomContainingOrPrenomContaining(keyword, keyword);
    }

    public void delete(Long id) {
        clientRepository.deleteById(id);
    }
}