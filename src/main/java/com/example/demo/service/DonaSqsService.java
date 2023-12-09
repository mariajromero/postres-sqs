package com.example.demo.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.example.demo.model.Dona;
import com.example.demo.model.Pedido;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DonaSqsService {
    private final AmazonSQS sqsClient;
    private String getQueueUrl(){
        return sqsClient.getQueueUrl("dona-queue").getQueueUrl();
    }
    public String publishstandardQueueMessage(Integer delaySeconds, Dona dona){
        Map<String, MessageAttributeValue> attributeValueMap= new HashMap<>();
        attributeValueMap.put("serial",
                new MessageAttributeValue()
                        .withDataType("String")
                        .withStringValue(dona.serial()));
        attributeValueMap.put("sabor",
                new MessageAttributeValue()
                        .withDataType("String")
                        .withStringValue(dona.sabor()));
        attributeValueMap.put("conRelleno",
                new MessageAttributeValue()
                        .withDataType("String")
                        .withStringValue(Optional.ofNullable(dona.conRelleno()).orElse(Boolean.FALSE).toString()
                        ));
        attributeValueMap.put("precio",
                new MessageAttributeValue()
                        .withDataType("String")
                        .withStringValue(dona.precio().toString()));
        SendMessageRequest sendMessageRequest= new SendMessageRequest()
                .withQueueUrl(this.getQueueUrl())
                .withMessageBody(dona.serial())
                .withDelaySeconds(delaySeconds)
                .withMessageAttributes(attributeValueMap);
        return sqsClient.sendMessage(sendMessageRequest).getMessageId();
    }
}
