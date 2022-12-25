import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try(var orderDispatcher = new KafkaDispatcher<Order>()) {
            try(var emailDispatcher = new KafkaDispatcher<Email>()) {
                for (int i = 0; i < 10; i++) {
                    var userId = UUID.randomUUID().toString();
                    var orderId = UUID.randomUUID().toString();
                    var amount = new BigDecimal(Math.random() * 5000 + 1);

                    Order newOrder = new Order(userId, orderId, amount);

                    orderDispatcher.send("ECOMMERCE_NEW_ORDER", orderId, newOrder);

                    var keyEmail = UUID.randomUUID().toString();
                    Email newEmail = new Email("Processing order", "Thank you for your order! We are processing your order");
                    emailDispatcher.send("ECOMMERCE_SEND_EMAIL", keyEmail, newEmail);
                }
            }
        }
    }
}
