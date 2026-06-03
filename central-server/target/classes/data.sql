-- Pulizia tabelle (ordine importante per le foreign key)


-- ── UTENTI ────────────────────────────────────────────────────


-- Password per tutti: "password123" (BCrypt hash)
INSERT INTO utenti (nome, cognome, email, username, password_hash, ruolo) VALUES
                                                                              ('Mario', 'Rossi', 'admin@connectedgames.it', 'admin',
                                                                               '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTpyR.at9ye',
                                                                               'ADMIN_PIATTAFORMA'),

                                                                              ('Luca', 'Bianchi', 'locale1@connectedgames.it', 'admin_locale1',
                                                                               '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTpyR.at9ye',
                                                                               'ADMIN_LOCALE'),

                                                                              ('Anna', 'Verdi', 'locale2@connectedgames.it', 'admin_locale2',
                                                                               '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTpyR.at9ye',
                                                                               'ADMIN_LOCALE'),

                                                                              ('Giuseppe', 'Ferrari', 'giocatore1@test.it', 'giuseppe_f',
                                                                               '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTpyR.at9ye',
                                                                               'GIOCATORE'),

                                                                              ('Sofia', 'Russo', 'giocatore2@test.it', 'sofia_r',
                                                                               '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTpyR.at9ye',
                                                                               'GIOCATORE'),

                                                                              ('Marco', 'Esposito', 'giocatore3@test.it', 'marco_e',
                                                                               '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTpyR.at9ye',
                                                                               'GIOCATORE'),

                                                                              ('Giulia', 'Romano', 'giocatore4@test.it', 'giulia_r',
                                                                               '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTpyR.at9ye',
                                                                               'GIOCATORE'),

                                                                              ('Paolo', 'Colombo', 'giocatore5@test.it', 'paolo_c',
                                                                               '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTpyR.at9ye',
                                                                               'GIOCATORE');

-- ── LOCALI ────────────────────────────────────────────────────
INSERT INTO locali (nome, indirizzo, tipo, admin_id) VALUES
                                                         ('Bar Sport Milano', 'Via Torino 15, Milano', 'PUBBLICO',
                                                          (SELECT id FROM utenti WHERE username = 'admin_locale1')),

                                                         ('Sala Giochi Roma', 'Via Nazionale 42, Roma', 'PUBBLICO',
                                                          (SELECT id FROM utenti WHERE username = 'admin_locale2')),

                                                         ('Circolo Ricreativo Napoli', 'Corso Umberto 8, Napoli', 'PUBBLICO',
                                                          (SELECT id FROM utenti WHERE username = 'admin_locale1')),

                                                         ('Casa Privata Torino', 'Via Po 22, Torino', 'PRIVATO', NULL);

-- ── GIOCHI ────────────────────────────────────────────────────
INSERT INTO giochi (nome, tipo, identificatore, locale_id) VALUES
-- Bar Sport Milano
('Calciobalilla 1', 'CALCIOBALILLA', 'CALC-001',
 (SELECT id FROM locali WHERE nome = 'Bar Sport Milano')),
('Calciobalilla 2', 'CALCIOBALILLA', 'CALC-002',
 (SELECT id FROM locali WHERE nome = 'Bar Sport Milano')),
('Freccette 1', 'FRECCETTE', 'FREC-001',
 (SELECT id FROM locali WHERE nome = 'Bar Sport Milano')),
('Monopoli 1', 'MONOPOLI', 'MONO-001',
 (SELECT id FROM locali WHERE nome = 'Bar Sport Milano')),

-- Sala Giochi Roma
('Calciobalilla 1', 'CALCIOBALILLA', 'CALC-001',
 (SELECT id FROM locali WHERE nome = 'Sala Giochi Roma')),
('Freccette 1', 'FRECCETTE', 'FREC-001',
 (SELECT id FROM locali WHERE nome = 'Sala Giochi Roma')),
('Freccette 2', 'FRECCETTE', 'FREC-002',
 (SELECT id FROM locali WHERE nome = 'Sala Giochi Roma')),
('Bocce 1', 'BOCCE', 'BOCC-001',
 (SELECT id FROM locali WHERE nome = 'Sala Giochi Roma')),

-- Circolo Ricreativo Napoli
('Calciobalilla 1', 'CALCIOBALILLA', 'CALC-001',
 (SELECT id FROM locali WHERE nome = 'Circolo Ricreativo Napoli')),
('Bocce 1', 'BOCCE', 'BOCC-001',
 (SELECT id FROM locali WHERE nome = 'Circolo Ricreativo Napoli')),
('Monopoli 1', 'MONOPOLI', 'MONO-001',
 (SELECT id FROM locali WHERE nome = 'Circolo Ricreativo Napoli')),

-- Casa Privata Torino
('Monopoli 1', 'MONOPOLI', 'MONO-001',
 (SELECT id FROM locali WHERE nome = 'Casa Privata Torino')),
('Bocce 1', 'BOCCE', 'BOCC-001',
 (SELECT id FROM locali WHERE nome = 'Casa Privata Torino'));

-- ── TORNEI ────────────────────────────────────────────────────
INSERT INTO tornei (nome, tipo_gioco, inizio_torneo, fine_torneo, stato) VALUES
                                                                             ('Torneo Calciobalilla Primavera 2026', 'CALCIOBALILLA',
                                                                              '2026-04-01 10:00:00', '2026-06-30 22:00:00', 'IN_CORSO'),

                                                                             ('Campionato Freccette 2026', 'FRECCETTE',
                                                                              '2026-05-01 10:00:00', '2026-07-31 22:00:00', 'IN_CORSO'),

                                                                             ('Torneo Bocce Estate 2026', 'BOCCE',
                                                                              '2026-07-01 10:00:00', '2026-08-31 22:00:00', 'PROGRAMMATO'),

                                                                             ('Gran Premio Monopoli 2026', 'MONOPOLI',
                                                                              '2026-03-01 10:00:00', '2026-04-30 22:00:00', 'TERMINATO');
