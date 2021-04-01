package com.brabos.bahia.cursoSpring.services;

import com.brabos.bahia.cursoSpring.domain.Client;
import com.brabos.bahia.cursoSpring.repositories.ClientRepository;
import com.brabos.bahia.cursoSpring.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    EmailService emailService;

    private Random random = new Random();

    public void sendNewPassword(String email){
        Client client = clientRepository.findByEmail(email);
        if (client == null){
            throw new ObjectNotFoundException("Cliente não encontrado");
        }

        String newPassword = newPassword();
        client.setPassword(encoder.encode(newPassword));

        clientRepository.save(client);
        emailService.sendNewPasswordEmail(client, newPassword);
    }

    private String newPassword() {
        char[] vet = new char[10];
        for(int i=0; i < 10; i++){
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar() {
        int opt = random.nextInt(3);
        //gera um digito
        if(opt == 0){
            return (char) (random.nextInt(10) + 40);
        }
        //gera uma letra
        else if(opt == 1){
            return (char) (random.nextInt(26) + 65);
        }
        //gera letra minuscula
        else {
            return (char) (random.nextInt(26) + 97);
        }
    }
}
