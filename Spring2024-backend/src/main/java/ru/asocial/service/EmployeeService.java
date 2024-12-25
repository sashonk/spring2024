package ru.asocial.service;

import java.util.concurrent.TimeUnit;

import org.openapitools.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ru.asocial.entity.EmployeeEntity;
import ru.asocial.entity.TempValueEntity;
import ru.asocial.repo.EmployeeRepo;
import ru.asocial.repo.TempValueRepo;

@Profile("dumb")
@Service
public class EmployeeService {
    
    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepo employeeRepo;
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;  
        
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private TempValueRepo tempValueRepo;
    
    private int pageSize = 1000;
    
    private static final String key = "employee.processing.page";
        
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.SECONDS)
    public void processEmployees() throws JsonProcessingException {
        TempValueEntity pageNumberEntity = tempValueRepo.findByKey(key);
        if (pageNumberEntity == null) {
            pageNumberEntity = new TempValueEntity();
            pageNumberEntity.setKey(key);
            pageNumberEntity.setValue("0");
            pageNumberEntity = tempValueRepo.save(pageNumberEntity);
        }
        int pageNumber = Integer.valueOf(pageNumberEntity.getValue());
        PageRequest pr = PageRequest.of(pageNumber, pageSize, Direction.ASC, "id");
        Page<EmployeeEntity> page = employeeRepo.findEmployees(pr);
        log.info("pulled " + page.getSize() + " employees from DB | page = " + pageNumber);
        for (EmployeeEntity entity : page) {
            Employee employee = new Employee();
            employee.setFirstName(entity.getFirstName());
            employee.setLastName(entity.getLastName());
            employee.setBirthDate(entity.getBirthDate());
            employee.setGender(entity.getGender());
            employee.setHireDate(entity.getHireDate());
            employee.setId(entity.getId());
            kafkaTemplate.send("demo", objectMapper.writeValueAsString(employee));
            log.info("employee sent to kafka, id = " + employee.getId());
        }
        pageNumberEntity.setValue(Integer.toString(pageNumber + 1));
        tempValueRepo.save(pageNumberEntity);
    }
}
