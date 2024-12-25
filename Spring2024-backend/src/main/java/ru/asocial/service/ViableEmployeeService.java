package ru.asocial.service;

import java.util.concurrent.TimeUnit;

import org.openapitools.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Slice;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ru.asocial.entity.EmployeeEntity;
import ru.asocial.entity.TempValueEntity;
import ru.asocial.repo.EmployeeRepo;
import ru.asocial.repo.TempValueRepo;

@Profile("viable")
@Service
public class ViableEmployeeService {

    private static final Logger log = LoggerFactory.getLogger(ViableEmployeeService.class);

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TempValueRepo tempValueRepo;

    private int pageSize = 1000;

    private static final String key = "control.message";
    
    private static final String topic = "demo";

    private record SignalMessage(long offset, long limit) {};

    @KafkaListener(topics = "signals")
    public void rawMessageReceived(String rawMessage) {
        try {
            SignalMessage signal = objectMapper.readValue(rawMessage, SignalMessage.class);
            log.debug("New signal received: " + signal);
            
            if (signal.limit() <= 0) {
                log.warn("Ignoring signal: limit must be positive");
                return;
            }
            
            if (signal.offset() < 0) {
                log.warn("Ignoring signal: negative offset");
                return;
            }
            
            TempValueEntity existingTv = tempValueRepo.findByKey(key);
            
            if (existingTv != null) { 
                log.warn("Ignoring signal: existing signal has not been processed yet");
                return;
            }
            
            TempValueEntity tv = new TempValueEntity();
            tv.setKey(key);
            tv.setValue(rawMessage);
            tempValueRepo.save(tv);
            log.debug("Signal saved.");            
        } catch (JsonProcessingException e) {
            log.error("Unprocessable message: " + rawMessage);
        }
    }

    @Scheduled(fixedDelay = 3, timeUnit = TimeUnit.SECONDS)
    public void processEmployees() throws JsonProcessingException {
        TempValueEntity tmpEntity = tempValueRepo.findByKey(key);
        if (tmpEntity == null) {
            log.debug("Chilling...");
            return;
        }
        tempValueRepo.delete(tmpEntity);
        
        String message = tmpEntity.getValue();
        log.debug("Begin processing signal: " + message);

        SignalMessage signal = objectMapper.readValue(message, SignalMessage.class);

        Slice<EmployeeEntity> page = employeeRepo.findSome(signal.offset(), signal.limit());
        log.debug("Fetched data from DB. Number of employees: " + page.getNumberOfElements());
        if (page.getNumberOfElements() == 0) {
            log.debug("No employees found");
            kafkaTemplate.send(topic, "NODATA");  
            return;
        }
        for (EmployeeEntity entity : page) {
            Employee employee = new Employee();
            employee.setFirstName(entity.getFirstName());
            employee.setLastName(entity.getLastName());
            employee.setBirthDate(entity.getBirthDate());
            employee.setGender(entity.getGender());
            employee.setHireDate(entity.getHireDate());
            employee.setId(entity.getId());
            kafkaTemplate.send(topic, objectMapper.writeValueAsString(employee));
            log.debug("employee sent to kafka, id = " + employee.getId());
        }
        
        kafkaTemplate.send(topic, "EOS");        
        log.debug("End of data");
    }
}
