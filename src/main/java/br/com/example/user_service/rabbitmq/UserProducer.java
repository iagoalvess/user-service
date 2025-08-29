package br.com.example.user_service.rabbitmq;

import br.com.example.user_service.dtos.EmailDto;
import br.com.example.user_service.models.UserModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value(value = "${broker.queue.email.name}")
    private String routingKey;

    public void publishMessageEmail(UserModel userModel) {
        var emailDto = new EmailDto(
                userModel.getUserId(),
                userModel.getEmail(),
                "Registration successful!",
                userModel.getName() + ", welcome! \nThank you for registering."
        );

        rabbitTemplate.convertAndSend("", routingKey, emailDto);
    }
}
