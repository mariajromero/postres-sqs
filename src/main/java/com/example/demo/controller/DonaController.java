package com.example.demo.controller;

import com.example.demo.model.Dona;
import com.example.demo.model.Pedido;
import com.example.demo.service.DonaSqsService;
import com.example.demo.service.PedidoSqsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/dona")
public class DonaController {
    DonaSqsService donaSqsService;
    @PostMapping("/aws")
    public Mono<String> postMessageToAwsSqs(@RequestBody Dona dona){
        return Mono.just(donaSqsService.publishstandardQueueMessage(10,dona));
    }
}
