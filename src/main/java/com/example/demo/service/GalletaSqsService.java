package com.example.demo.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.example.demo.model.Dona;
import com.example.demo.model.Galleta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class GalletaSqsService {
    private final AmazonSQS sqsClient;
    private String getQueueUrl(){
        return sqsClient.getQueueUrl("galleta-queue").getQueueUrl();
    }
    public String publishstandardQueueMessage(Integer delaySeconds, Galleta galleta){
        Map<String, MessageAttributeValue> attributeValueMap= new HashMap<>();
        attributeValueMap.put("serial",
                new MessageAttributeValue()
                        .withDataType("String")
                        .withStringValue(galleta.serial()));
        attributeValueMap.put("sabor",
                new MessageAttributeValue()
                        .withDataType("String")
                        .withStringValue(galleta.sabor()));
        attributeValueMap.put("tamaño",
                new MessageAttributeValue()
                        .withDataType("String")
                        .withStringValue(galleta.tamaño()));
        attributeValueMap.put("tieneGluten",
                new MessageAttributeValue()
                        .withDataType("String")
                        .withStringValue(Optional.ofNullable(galleta.tieneGluten()).orElse(Boolean.FALSE).toString()
                        ));
        attributeValueMap.put("precio",
                new MessageAttributeValue()
                        .withDataType("String")
                        .withStringValue(galleta.precio().toString()));
        SendMessageRequest sendMessageRequest= new SendMessageRequest()
                .withQueueUrl(this.getQueueUrl())
                .withMessageBody(galleta.serial())
                .withDelaySeconds(delaySeconds)
                .withMessageAttributes(attributeValueMap);
        return sqsClient.sendMessage(sendMessageRequest).getMessageId();
    }
}
