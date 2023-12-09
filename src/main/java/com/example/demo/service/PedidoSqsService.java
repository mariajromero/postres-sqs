package com.example.demo.service;

import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.example.demo.model.Pedido;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class PedidoSqsService {
    private final AmazonSQS sqsClient;
    private String getQueueUrl(){
        return sqsClient.getQueueUrl("pedido-queue").getQueueUrl();
    }
    public String publishstandardQueueMessage(Integer delaySeconds, Pedido pedido){
        Map<String, MessageAttributeValue> attributeValueMap= new HashMap<>();
        attributeValueMap.put("serial",
                new MessageAttributeValue()
                        .withDataType("String")
                        .withStringValue(pedido.serial()));
        attributeValueMap.put("nombre",
                new MessageAttributeValue()
                        .withDataType("String")
                        .withStringValue(pedido.nombre()));
        attributeValueMap.put("fecha",
                new MessageAttributeValue()
                        .withDataType("String")
                        .withStringValue(pedido.fecha()));
        attributeValueMap.put("precioConDescuento",
                new MessageAttributeValue()
                        .withDataType("String")
                        .withStringValue(pedido.precioConDescuento().toString()));
        SendMessageRequest sendMessageRequest= new SendMessageRequest()
                .withQueueUrl(this.getQueueUrl())
                .withMessageBody(pedido.serial())
                .withDelaySeconds(delaySeconds)
                .withMessageAttributes(attributeValueMap);
        return sqsClient.sendMessage(sendMessageRequest).getMessageId();
    }
}
