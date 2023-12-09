package com.example.demo.controller;

import com.example.demo.model.Pedido;
import com.example.demo.model.Torta;
import com.example.demo.service.PedidoSqsService;
import com.example.demo.service.TortaSqsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/torta")
public class TortaController {
    TortaSqsService tortaSqsService;
    @PostMapping("/aws")
    public Mono<String> postMessageToAwsSqs(@RequestBody Torta torta){
        return Mono.just(tortaSqsService.publishstandardQueueMessage(10,torta));
    }
}
