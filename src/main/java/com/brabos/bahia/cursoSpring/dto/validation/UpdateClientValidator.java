package com.brabos.bahia.cursoSpring.dto.validation;

import com.brabos.bahia.cursoSpring.domain.Client;
import com.brabos.bahia.cursoSpring.dto.ClientDTO;
import com.brabos.bahia.cursoSpring.repositories.ClientRepository;
import com.brabos.bahia.cursoSpring.resources.exceptions.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UpdateClientValidator implements ConstraintValidator<UpdateClient, ClientDTO> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void initialize(UpdateClient constraintAnnotation) {

    }

    @Override
    public boolean isValid(ClientDTO clientDTO, ConstraintValidatorContext context) {

        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer uriId = Integer.parseInt(map.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        Client aux = clientRepository.findByEmail(clientDTO.getEmail());

        if(aux != null && uriId != aux.getId()){
            list.add(new FieldMessage("email", "Email já cadastrado"));
        }
        for (FieldMessage field : list){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(field.getMessage())
                    .addPropertyNode(field.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
