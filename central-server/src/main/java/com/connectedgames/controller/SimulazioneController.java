package com.connectedgames.controller;

import com.connectedgames.model.Partita;
import com.connectedgames.service.GiocoService;
import com.connectedgames.service.PartitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/simulazione")
public class SimulazioneController {

    @Autowired
    private PartitaService partitaService;

    @Autowired
    private GiocoService giocoService;

    @GetMapping
    public String simulazione(Model model) {
        model.addAttribute("partite", partitaService.findAll()
                .stream()
                .filter(p -> p.getStato() == Partita.StatoPartita.IN_CORSO)
                .collect(Collectors.toList()));
        model.addAttribute("giochi", giocoService.findAll());
        return "simulazione";
    }
}