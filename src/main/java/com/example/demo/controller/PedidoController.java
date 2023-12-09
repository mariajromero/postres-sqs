package com.example.demo.controller;

import com.example.demo.model.Pedido;
import com.example.demo.service.PedidoSqsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/pedido")
public class PedidoController {
    PedidoSqsService pedidoSqsService;
    @PostMapping("/aws")
    public Mono<String> postMessageToAwsSqs(@RequestBody Pedido pedido){
        return Mono.just(pedidoSqsService.publishstandardQueueMessage(10,pedido));
    }
}
