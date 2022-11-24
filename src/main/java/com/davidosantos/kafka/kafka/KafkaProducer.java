package com.davidosantos.kafka.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.andreinc.mockneat.MockNeat;

@AllArgsConstructor
@Component
@Log4j2
public class KafkaProducer {

    private final KafkaTemplate<String, User> kafkaTemplate;

    private final MockNeat mock = MockNeat.threadLocal();

    @Scheduled(fixedRate = 500, initialDelay = 2000)
    public void generate() {

        User user = User.newBuilder()
        .setId(mock.longs().range(1l, 30_000l).get())
        .setName(mock.names().get())
        .setEmail(mock.emails().get())
        .build();
        
        log.info("Sending message to Kafka: " + user.toString());

        kafkaTemplate.send("harryPotterBookShelf-avro", user.getEmail().toString(), user);

    }
}
