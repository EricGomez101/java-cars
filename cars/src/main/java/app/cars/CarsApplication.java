package app.cars;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CarsApplication
{
    public static final String EXCHANGE_NAME = "CarServer";
    public static final String QUEUE_NAME = "Log";

    public static void main(String[] args)
    {

        SpringApplication.run(CarsApplication.class, args);
    }

    @Bean
    public TopicExchange appExchange()
    {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue appQueue()
    {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public Binding bindQueueToExchange()
    {
        return BindingBuilder.bind(appQueue()).to(appExchange()).with(QUEUE_NAME);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory cf)
    {
        final RabbitTemplate RABBIT_TEMPLATE = new RabbitTemplate(cf);
        RABBIT_TEMPLATE.setMessageConverter(producerJackson2MessageConverter());
        return RABBIT_TEMPLATE;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter()
    {
        return new Jackson2JsonMessageConverter();
    }
}

