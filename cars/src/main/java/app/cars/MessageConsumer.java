package app.cars;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.amqp.core.Message;

@Slf4j
@Service
public class MessageConsumer
{
    @RabbitListener(queues = CarsApplication.QUEUE_NAME)
    public void consumeMessage(final Message message)
    {
        log.info("Received message: {}", message.toString());
    }
}
