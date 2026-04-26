package cl.duoc.msAsiento.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.msAsiento.service.AsientoService;

@RestController
@RequestMapping("/api/v1/asientos")
public class AsientoController {

    @Autowired
    private AsientoService asientoService;

    

}
