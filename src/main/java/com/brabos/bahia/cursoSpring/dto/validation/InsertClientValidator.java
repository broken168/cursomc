package com.brabos.bahia.cursoSpring.dto.validation;

import com.brabos.bahia.cursoSpring.domain.Client;
import com.brabos.bahia.cursoSpring.domain.enums.ClientType;
import com.brabos.bahia.cursoSpring.dto.NewClientDTO;
import com.brabos.bahia.cursoSpring.dto.validation.utils.BR;
import com.brabos.bahia.cursoSpring.repositories.ClientRepository;
import com.brabos.bahia.cursoSpring.resources.exceptions.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class InsertClientValidator implements ConstraintValidator<InsertClient, NewClientDTO> {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void initialize(InsertClient ann){
    }

    @Override
    public boolean isValid(NewClientDTO newClientDTO, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();
        //incluir os testes aqui, inserindo erros na lista
        if(newClientDTO.getType().equals(ClientType.REGULARPERSON.getCode()) && !BR.isValidCPF(newClientDTO.getCpfOrCnpj())){
            list.add(new FieldMessage("cpfOrCnpj", "CPF inválido"));
        }
        if(newClientDTO.getType().equals(ClientType.LEGALPERSON.getCode()) && !BR.isValidCNPJ(newClientDTO.getCpfOrCnpj())){
            list.add(new FieldMessage("cpfOrCnpj", "CNPJ inválido"));
        }

        Client aux = clientRepository.findByEmail(newClientDTO.getEmail());
        if(aux != null){
            list.add(new FieldMessage("email", "Email já cadastrado"));
        }

        for(FieldMessage e : list){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
