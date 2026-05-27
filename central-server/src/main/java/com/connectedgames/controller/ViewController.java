package com.connectedgames.controller;

import com.connectedgames.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ViewController {

    private final UtenteService utenteService;
    private final LocaleService localeService;
    private final GiocoService giocoService;
    private final PartitaService partitaService;
    private final TorneoService torneoService;

    public ViewController(UtenteService utenteService,
                          LocaleService localeService,
                          GiocoService giocoService,
                          PartitaService partitaService,
                          TorneoService torneoService) {
        this.utenteService = utenteService;
        this.localeService = localeService;
        this.giocoService = giocoService;
        this.partitaService = partitaService;
        this.torneoService = torneoService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("page", "dashboard");
        model.addAttribute("giochi", giocoService.findAll());
        model.addAttribute("torneiAttivi", torneoService.findByStato(
                com.connectedgames.model.Torneo.StatoTorneo.IN_CORSO));
        return "dashboard";
    }

    @GetMapping("/prenotazioni")
    public String prenotazioni(Model model) {
        model.addAttribute("page", "prenotazioni");
        model.addAttribute("giochi", giocoService.findAll());
        return "prenotazioni";
    }

    @GetMapping("/tornei")
    public String tornei(Model model) {
        model.addAttribute("page", "tornei");
        model.addAttribute("tornei", torneoService.findAll());
        return "tornei";
    }

    @GetMapping("/locale/dashboard")
    public String localeDashboard(Model model) {
        model.addAttribute("page", "locale-dashboard");
        model.addAttribute("giochi", giocoService.findAll());
        return "locale/dashboard";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("page", "admin-dashboard");
        model.addAttribute("utenti", utenteService.findAll());
        model.addAttribute("locali", localeService.findAll());
        model.addAttribute("tornei", torneoService.findAll());
        return "admin/dashboard";
    }

    @GetMapping("/admin/utenti")
    public String adminUtenti(Model model) {
        model.addAttribute("page", "admin-utenti");
        model.addAttribute("utenti", utenteService.findAll());
        return "admin/utenti";
    }

    @GetMapping("/admin/locali")
    public String adminLocali(Model model) {
        model.addAttribute("page", "admin-locali");
        model.addAttribute("locali", localeService.findAll());
        return "admin/locali";
    }
}