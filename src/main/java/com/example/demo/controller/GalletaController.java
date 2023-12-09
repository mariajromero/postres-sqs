package com.example.demo.controller;

import com.example.demo.model.Galleta;
import com.example.demo.model.Pedido;
import com.example.demo.service.GalletaSqsService;
import com.example.demo.service.PedidoSqsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/galleta")
public class GalletaController {
    GalletaSqsService galletaSqsService;
    @PostMapping("/aws")
    public Mono<String> postMessageToAwsSqs(@RequestBody Galleta galleta){
        return Mono.just(galletaSqsService.publishstandardQueueMessage(10,galleta));
    }
}
