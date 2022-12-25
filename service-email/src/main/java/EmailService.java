import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Map;

public class EmailService {
    public static void main(String[] args) {
        var emailService = new EmailService();

        try(var kafkaService = new KafkaService(
                EmailService.class.getSimpleName(),
                "ECOMMERCE_SEND_EMAIL",
                emailService::parse,
                Email.class,
                Map.of()
        )){
            kafkaService.run();
        }
    }

    private void parse(ConsumerRecord<String, Email> record) {
        System.out.println("Processing sending email, checking for fraud");
        System.out.println("Key: " + record.key());
        System.out.println("Value: " + record.value());
        System.out.println("Partition: " + record.partition());
        System.out.println("Offset: " + record.offset());
        System.out.println("Time: " + record.timestamp());

        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Email sent");
    }
}
