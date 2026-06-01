package com.connectedgames.controller;

import com.connectedgames.model.Partita;
import com.connectedgames.model.Utente;
import com.connectedgames.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
public class ViewController {

    @Autowired private UtenteService utenteService;
    @Autowired private LocaleService localeService;
    @Autowired private GiocoService giocoService;
    @Autowired private PartitaService partitaService;
    @Autowired private TorneoService torneoService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("giochi", giocoService.findAll());
        model.addAttribute("tornei", torneoService.findAll());
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registrazione")
    public String registrazioneForm() {
        return "registrazione";
    }

    @PostMapping("/registrazione")
    public String registrazione(
            @RequestParam String nome,
            @RequestParam String cognome,
            @RequestParam String email,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String ruolo,
            RedirectAttributes redirectAttributes) {
        try {
            utenteService.registra(nome, cognome, email, username, password, ruolo);
            redirectAttributes.addFlashAttribute("success",
                    "Registrazione completata! Ora puoi accedere.");
            return "redirect:/login?success";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errore", e.getMessage());
            return "redirect:/registrazione?error";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails,
                            Model model) {
        Utente utente = utenteService.findByUsername(userDetails.getUsername());
        model.addAttribute("utente", utente);

        if (utente.getRuolo() == Utente.RuoloUtente.ADMIN_PIATTAFORMA) {
            return "redirect:/admin/dashboard";
        }
        if (utente.getRuolo() == Utente.RuoloUtente.ADMIN_LOCALE ||
                utente.getRuolo() == Utente.RuoloUtente.ADMIN_GIOCO) {
            return "redirect:/admin/locale";
        }

        model.addAttribute("partite", partitaService.findByGiocatore(utente.getId()));
        model.addAttribute("tornei", torneoService.findAll());
        return "utente/dashboard";
    }

    @GetMapping("/profilo")
    public String profilo(@AuthenticationPrincipal UserDetails userDetails,
                          Model model) {
        Utente utente = utenteService.findByUsername(userDetails.getUsername());
        model.addAttribute("utente", utente);
        model.addAttribute("partite", partitaService.findByGiocatore(utente.getId()));
        return "utente/profilo";
    }

    @GetMapping("/giochi")
    public String giochi(Model model) {
        model.addAttribute("giochi", giocoService.findAll());
        model.addAttribute("locali", localeService.findAll());
        return "giocatore/giochi";
    }

    @GetMapping("/partite")
    public String partite(Model model) {
        model.addAttribute("partite", partitaService.findAll());
        return "giocatore/partite";
    }

    @GetMapping("/tornei")
    public String tornei(Model model) {
        model.addAttribute("tornei", torneoService.findAll());
        return "giocatore/tornei";
    }

    @GetMapping("/statistiche")
    public String statistiche(Model model) {
        model.addAttribute("partite", partitaService.findAll());
        model.addAttribute("giochi", giocoService.findAll());
        return "giocatore/statistiche";
    }

    @GetMapping("/prenotazione")
    public String prenotazione(Model model) {
        model.addAttribute("giochi", giocoService.findAll());
        model.addAttribute("locali", localeService.findAll());
        return "prenotazione";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("utenti", utenteService.findAll());
        model.addAttribute("locali", localeService.findAll());
        model.addAttribute("giochi", giocoService.findAll());
        model.addAttribute("partite", partitaService.findAll());
        model.addAttribute("tornei", torneoService.findAll());
        return "admin/dashboard";
    }

    @GetMapping("/admin/utenti")
    public String adminUtenti(Model model) {
        model.addAttribute("utenti", utenteService.findAll());
        return "admin/utenti";
    }

    @GetMapping("/admin/tornei")
    public String adminTornei(Model model) {
        model.addAttribute("tornei", torneoService.findAll());
        model.addAttribute("giochi", giocoService.findAll());
        model.addAttribute("locali", localeService.findAll());
        return "admin/tornei";
    }

    @GetMapping("/admin/locale")
    public String adminLocale(Model model) {
        model.addAttribute("locali", localeService.findAll());
        model.addAttribute("giochi", giocoService.findAll());
        model.addAttribute("partite", partitaService.findAll());
        return "admin/locale/dashboard";
    }

    @GetMapping("/admin/locale/partite")
    public String adminLocalePartite(Model model) {
        model.addAttribute("partite", partitaService.findAll());
        model.addAttribute("giochi", giocoService.findAll());
        return "admin/locale/partite";
    }

    @GetMapping("/admin/locale/partite/nuova")
    public String nuovaPartitaForm(Model model) {
        model.addAttribute("giochi", giocoService.findAll());
        model.addAttribute("utenti", utenteService.findAll());
        return "admin/locale/nuova-partita";
    }

    @PostMapping("/admin/locale/partite/nuova")
    public String nuovaPartita(
            @RequestParam Long giocoId,
            @RequestParam String tipo,
            @RequestParam(required = false) List<Long> giocatoriIds,
            RedirectAttributes redirectAttributes) {
        try {
            Partita partita = new Partita();
            partita.setGioco(giocoService.findById(giocoId));
            partita.setTipo(Partita.TipoPartita.valueOf(tipo));
            partitaService.iniziaPartita(partita);
            redirectAttributes.addFlashAttribute("success", "Partita creata!");
            return "redirect:/admin/locale/partite";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errore", e.getMessage());
            return "redirect:/admin/locale/partite/nuova";
        }
    }

    @PostMapping("/admin/locale/partite/{id}/termina")
    public String terminaPartita(@PathVariable Long id,
                                 RedirectAttributes redirectAttributes) {
        partitaService.terminaPartita(id);
        redirectAttributes.addFlashAttribute("success", "Partita terminata!");
        return "redirect:/admin/locale/partite";
    }

    @GetMapping("/admin/locale/utenti")
    public String adminLocaleUtenti(Model model) {
        model.addAttribute("utenti", utenteService.findAll());
        model.addAttribute("partite", partitaService.findAll());
        return "admin/locale/utenti";
    }
}