package com.example.demo.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.example.demo.model.Dona;
import com.example.demo.model.Torta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TortaSqsService {
    private final AmazonSQS sqsClient;
    private String getQueueUrl(){
        return sqsClient.getQueueUrl("torta-queue").getQueueUrl();
    }
    public String publishstandardQueueMessage(Integer delaySeconds, Torta torta){
        Map<String, MessageAttributeValue> attributeValueMap= new HashMap<>();
        attributeValueMap.put("serial",
                new MessageAttributeValue()
                        .withDataType("String")
                        .withStringValue(torta.serial()));
        attributeValueMap.put("sabor",
                new MessageAttributeValue()
                        .withDataType("String")
                        .withStringValue(torta.tamaño()));
        attributeValueMap.put("conCrema",
                new MessageAttributeValue()
                        .withDataType("String")
                        .withStringValue(Optional.ofNullable(torta.conCrema()).orElse(Boolean.FALSE).toString()
                        ));
        attributeValueMap.put("tamaño",
                new MessageAttributeValue()
                        .withDataType("String")
                        .withStringValue(torta.tamaño()));
        SendMessageRequest sendMessageRequest= new SendMessageRequest()
                .withQueueUrl(this.getQueueUrl())
                .withMessageBody(torta.serial())
                .withDelaySeconds(delaySeconds)
                .withMessageAttributes(attributeValueMap);
        return sqsClient.sendMessage(sendMessageRequest).getMessageId();
    }
}
