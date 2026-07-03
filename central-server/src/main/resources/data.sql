-- Dati di esempio per Connected Games Platform.
--
-- Con database persistente (jdbc:h2:file:...) questo script viene comunque
-- rieseguito ad ogni avvio (spring.sql.init.mode=always). Ogni INSERT è
-- perciò guardato da un "WHERE NOT EXISTS": la riga viene creata solo se non
-- c'è già. Così il seed iniziale resta garantito al primo avvio, ma dal
-- secondo avvio in poi lo script non tocca né duplica nulla — compresi gli
-- utenti/partite creati a runtime dall'applicazione (es. via /registrazione).

-- ── UTENTI ────────────────────────────────────────────────────
-- Password per tutti: "password123" (BCrypt hash). Chiave naturale: username.

INSERT INTO utenti (nome, cognome, email, username, password_hash, ruolo)
SELECT 'Mario', 'Rossi', 'admin@connectedgames.it', 'admin',
       '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTpyR.at9ye', 'ADMIN_PIATTAFORMA'
WHERE NOT EXISTS (SELECT 1 FROM utenti WHERE username = 'admin');

INSERT INTO utenti (nome, cognome, email, username, password_hash, ruolo)
SELECT 'Luca', 'Bianchi', 'locale1@connectedgames.it', 'admin_locale1',
       '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTpyR.at9ye', 'ADMIN_LOCALE'
WHERE NOT EXISTS (SELECT 1 FROM utenti WHERE username = 'admin_locale1');

INSERT INTO utenti (nome, cognome, email, username, password_hash, ruolo)
SELECT 'Anna', 'Verdi', 'locale2@connectedgames.it', 'admin_locale2',
       '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTpyR.at9ye', 'ADMIN_LOCALE'
WHERE NOT EXISTS (SELECT 1 FROM utenti WHERE username = 'admin_locale2');

INSERT INTO utenti (nome, cognome, email, username, password_hash, ruolo)
SELECT 'Giuseppe', 'Ferrari', 'giocatore1@test.it', 'giuseppe_f',
       '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTpyR.at9ye', 'GIOCATORE'
WHERE NOT EXISTS (SELECT 1 FROM utenti WHERE username = 'giuseppe_f');

INSERT INTO utenti (nome, cognome, email, username, password_hash, ruolo)
SELECT 'Sofia', 'Russo', 'giocatore2@test.it', 'sofia_r',
       '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTpyR.at9ye', 'GIOCATORE'
WHERE NOT EXISTS (SELECT 1 FROM utenti WHERE username = 'sofia_r');

INSERT INTO utenti (nome, cognome, email, username, password_hash, ruolo)
SELECT 'Marco', 'Esposito', 'giocatore3@test.it', 'marco_e',
       '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTpyR.at9ye', 'GIOCATORE'
WHERE NOT EXISTS (SELECT 1 FROM utenti WHERE username = 'marco_e');

INSERT INTO utenti (nome, cognome, email, username, password_hash, ruolo)
SELECT 'Giulia', 'Romano', 'giocatore4@test.it', 'giulia_r',
       '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTpyR.at9ye', 'GIOCATORE'
WHERE NOT EXISTS (SELECT 1 FROM utenti WHERE username = 'giulia_r');

INSERT INTO utenti (nome, cognome, email, username, password_hash, ruolo)
SELECT 'Paolo', 'Colombo', 'giocatore5@test.it', 'paolo_c',
       '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTpyR.at9ye', 'GIOCATORE'
WHERE NOT EXISTS (SELECT 1 FROM utenti WHERE username = 'paolo_c');

-- ── LOCALI ────────────────────────────────────────────────────
-- Chiave naturale: nome.

INSERT INTO locali (nome, indirizzo, tipo, admin_id)
SELECT 'Bar Sport Milano', 'Via Torino 15, Milano', 'PUBBLICO',
       (SELECT id FROM utenti WHERE username = 'admin_locale1')
WHERE NOT EXISTS (SELECT 1 FROM locali WHERE nome = 'Bar Sport Milano');

INSERT INTO locali (nome, indirizzo, tipo, admin_id)
SELECT 'Sala Giochi Roma', 'Via Nazionale 42, Roma', 'PUBBLICO',
       (SELECT id FROM utenti WHERE username = 'admin_locale2')
WHERE NOT EXISTS (SELECT 1 FROM locali WHERE nome = 'Sala Giochi Roma');

INSERT INTO locali (nome, indirizzo, tipo, admin_id)
SELECT 'Circolo Ricreativo Napoli', 'Corso Umberto 8, Napoli', 'PUBBLICO',
       (SELECT id FROM utenti WHERE username = 'admin_locale1')
WHERE NOT EXISTS (SELECT 1 FROM locali WHERE nome = 'Circolo Ricreativo Napoli');

INSERT INTO locali (nome, indirizzo, tipo, admin_id)
SELECT 'Casa Privata Torino', 'Via Po 22, Torino', 'PRIVATO', NULL
WHERE NOT EXISTS (SELECT 1 FROM locali WHERE nome = 'Casa Privata Torino');

-- ── GIOCHI ────────────────────────────────────────────────────
-- Chiave naturale: identificatore + locale_id (lo stesso identificatore si
-- ripete in locali diversi, quindi da solo non basta).

-- Bar Sport Milano
INSERT INTO giochi (nome, tipo, identificatore, locale_id)
SELECT 'Calciobalilla 1', 'CALCIOBALILLA', 'CALC-001', (SELECT id FROM locali WHERE nome = 'Bar Sport Milano')
WHERE NOT EXISTS (SELECT 1 FROM giochi WHERE identificatore = 'CALC-001' AND locale_id = (SELECT id FROM locali WHERE nome = 'Bar Sport Milano'));

INSERT INTO giochi (nome, tipo, identificatore, locale_id)
SELECT 'Calciobalilla 2', 'CALCIOBALILLA', 'CALC-002', (SELECT id FROM locali WHERE nome = 'Bar Sport Milano')
WHERE NOT EXISTS (SELECT 1 FROM giochi WHERE identificatore = 'CALC-002' AND locale_id = (SELECT id FROM locali WHERE nome = 'Bar Sport Milano'));

INSERT INTO giochi (nome, tipo, identificatore, locale_id)
SELECT 'Freccette 1', 'FRECCETTE', 'FREC-001', (SELECT id FROM locali WHERE nome = 'Bar Sport Milano')
WHERE NOT EXISTS (SELECT 1 FROM giochi WHERE identificatore = 'FREC-001' AND locale_id = (SELECT id FROM locali WHERE nome = 'Bar Sport Milano'));

INSERT INTO giochi (nome, tipo, identificatore, locale_id)
SELECT 'Monopoli 1', 'MONOPOLI', 'MONO-001', (SELECT id FROM locali WHERE nome = 'Bar Sport Milano')
WHERE NOT EXISTS (SELECT 1 FROM giochi WHERE identificatore = 'MONO-001' AND locale_id = (SELECT id FROM locali WHERE nome = 'Bar Sport Milano'));

-- Sala Giochi Roma
INSERT INTO giochi (nome, tipo, identificatore, locale_id)
SELECT 'Calciobalilla 1', 'CALCIOBALILLA', 'CALC-001', (SELECT id FROM locali WHERE nome = 'Sala Giochi Roma')
WHERE NOT EXISTS (SELECT 1 FROM giochi WHERE identificatore = 'CALC-001' AND locale_id = (SELECT id FROM locali WHERE nome = 'Sala Giochi Roma'));

INSERT INTO giochi (nome, tipo, identificatore, locale_id)
SELECT 'Freccette 1', 'FRECCETTE', 'FREC-001', (SELECT id FROM locali WHERE nome = 'Sala Giochi Roma')
WHERE NOT EXISTS (SELECT 1 FROM giochi WHERE identificatore = 'FREC-001' AND locale_id = (SELECT id FROM locali WHERE nome = 'Sala Giochi Roma'));

INSERT INTO giochi (nome, tipo, identificatore, locale_id)
SELECT 'Freccette 2', 'FRECCETTE', 'FREC-002', (SELECT id FROM locali WHERE nome = 'Sala Giochi Roma')
WHERE NOT EXISTS (SELECT 1 FROM giochi WHERE identificatore = 'FREC-002' AND locale_id = (SELECT id FROM locali WHERE nome = 'Sala Giochi Roma'));

INSERT INTO giochi (nome, tipo, identificatore, locale_id)
SELECT 'Bocce 1', 'BOCCE', 'BOCC-001', (SELECT id FROM locali WHERE nome = 'Sala Giochi Roma')
WHERE NOT EXISTS (SELECT 1 FROM giochi WHERE identificatore = 'BOCC-001' AND locale_id = (SELECT id FROM locali WHERE nome = 'Sala Giochi Roma'));

-- Circolo Ricreativo Napoli
INSERT INTO giochi (nome, tipo, identificatore, locale_id)
SELECT 'Calciobalilla 1', 'CALCIOBALILLA', 'CALC-001', (SELECT id FROM locali WHERE nome = 'Circolo Ricreativo Napoli')
WHERE NOT EXISTS (SELECT 1 FROM giochi WHERE identificatore = 'CALC-001' AND locale_id = (SELECT id FROM locali WHERE nome = 'Circolo Ricreativo Napoli'));

INSERT INTO giochi (nome, tipo, identificatore, locale_id)
SELECT 'Bocce 1', 'BOCCE', 'BOCC-001', (SELECT id FROM locali WHERE nome = 'Circolo Ricreativo Napoli')
WHERE NOT EXISTS (SELECT 1 FROM giochi WHERE identificatore = 'BOCC-001' AND locale_id = (SELECT id FROM locali WHERE nome = 'Circolo Ricreativo Napoli'));

INSERT INTO giochi (nome, tipo, identificatore, locale_id)
SELECT 'Monopoli 1', 'MONOPOLI', 'MONO-001', (SELECT id FROM locali WHERE nome = 'Circolo Ricreativo Napoli')
WHERE NOT EXISTS (SELECT 1 FROM giochi WHERE identificatore = 'MONO-001' AND locale_id = (SELECT id FROM locali WHERE nome = 'Circolo Ricreativo Napoli'));

-- Casa Privata Torino
INSERT INTO giochi (nome, tipo, identificatore, locale_id)
SELECT 'Monopoli 1', 'MONOPOLI', 'MONO-001', (SELECT id FROM locali WHERE nome = 'Casa Privata Torino')
WHERE NOT EXISTS (SELECT 1 FROM giochi WHERE identificatore = 'MONO-001' AND locale_id = (SELECT id FROM locali WHERE nome = 'Casa Privata Torino'));

INSERT INTO giochi (nome, tipo, identificatore, locale_id)
SELECT 'Bocce 1', 'BOCCE', 'BOCC-001', (SELECT id FROM locali WHERE nome = 'Casa Privata Torino')
WHERE NOT EXISTS (SELECT 1 FROM giochi WHERE identificatore = 'BOCC-001' AND locale_id = (SELECT id FROM locali WHERE nome = 'Casa Privata Torino'));

-- ── TORNEI ────────────────────────────────────────────────────
-- Chiave naturale: nome.

INSERT INTO tornei (nome, tipo_gioco, inizio_torneo, fine_torneo, stato)
SELECT 'Torneo Calciobalilla Primavera 2026', 'CALCIOBALILLA', '2026-04-01 10:00:00', '2026-06-30 22:00:00', 'IN_CORSO'
WHERE NOT EXISTS (SELECT 1 FROM tornei WHERE nome = 'Torneo Calciobalilla Primavera 2026');

INSERT INTO tornei (nome, tipo_gioco, inizio_torneo, fine_torneo, stato)
SELECT 'Campionato Freccette 2026', 'FRECCETTE', '2026-05-01 10:00:00', '2026-07-31 22:00:00', 'IN_CORSO'
WHERE NOT EXISTS (SELECT 1 FROM tornei WHERE nome = 'Campionato Freccette 2026');

INSERT INTO tornei (nome, tipo_gioco, inizio_torneo, fine_torneo, stato)
SELECT 'Torneo Bocce Estate 2026', 'BOCCE', '2026-07-01 10:00:00', '2026-08-31 22:00:00', 'PROGRAMMATO'
WHERE NOT EXISTS (SELECT 1 FROM tornei WHERE nome = 'Torneo Bocce Estate 2026');

INSERT INTO tornei (nome, tipo_gioco, inizio_torneo, fine_torneo, stato)
SELECT 'Gran Premio Monopoli 2026', 'MONOPOLI', '2026-03-01 10:00:00', '2026-04-30 22:00:00', 'TERMINATO'
WHERE NOT EXISTS (SELECT 1 FROM tornei WHERE nome = 'Gran Premio Monopoli 2026');

-- ── TORNEO_LOCALI (locali che partecipano a ciascun torneo) ────
-- Chiave naturale: coppia (torneo_id, locale_id).

INSERT INTO torneo_locali (torneo_id, locale_id)
SELECT (SELECT id FROM tornei WHERE nome = 'Torneo Calciobalilla Primavera 2026'), (SELECT id FROM locali WHERE nome = 'Bar Sport Milano')
WHERE NOT EXISTS (SELECT 1 FROM torneo_locali WHERE torneo_id = (SELECT id FROM tornei WHERE nome = 'Torneo Calciobalilla Primavera 2026') AND locale_id = (SELECT id FROM locali WHERE nome = 'Bar Sport Milano'));

INSERT INTO torneo_locali (torneo_id, locale_id)
SELECT (SELECT id FROM tornei WHERE nome = 'Torneo Calciobalilla Primavera 2026'), (SELECT id FROM locali WHERE nome = 'Sala Giochi Roma')
WHERE NOT EXISTS (SELECT 1 FROM torneo_locali WHERE torneo_id = (SELECT id FROM tornei WHERE nome = 'Torneo Calciobalilla Primavera 2026') AND locale_id = (SELECT id FROM locali WHERE nome = 'Sala Giochi Roma'));

INSERT INTO torneo_locali (torneo_id, locale_id)
SELECT (SELECT id FROM tornei WHERE nome = 'Torneo Calciobalilla Primavera 2026'), (SELECT id FROM locali WHERE nome = 'Circolo Ricreativo Napoli')
WHERE NOT EXISTS (SELECT 1 FROM torneo_locali WHERE torneo_id = (SELECT id FROM tornei WHERE nome = 'Torneo Calciobalilla Primavera 2026') AND locale_id = (SELECT id FROM locali WHERE nome = 'Circolo Ricreativo Napoli'));

INSERT INTO torneo_locali (torneo_id, locale_id)
SELECT (SELECT id FROM tornei WHERE nome = 'Campionato Freccette 2026'), (SELECT id FROM locali WHERE nome = 'Bar Sport Milano')
WHERE NOT EXISTS (SELECT 1 FROM torneo_locali WHERE torneo_id = (SELECT id FROM tornei WHERE nome = 'Campionato Freccette 2026') AND locale_id = (SELECT id FROM locali WHERE nome = 'Bar Sport Milano'));

INSERT INTO torneo_locali (torneo_id, locale_id)
SELECT (SELECT id FROM tornei WHERE nome = 'Campionato Freccette 2026'), (SELECT id FROM locali WHERE nome = 'Sala Giochi Roma')
WHERE NOT EXISTS (SELECT 1 FROM torneo_locali WHERE torneo_id = (SELECT id FROM tornei WHERE nome = 'Campionato Freccette 2026') AND locale_id = (SELECT id FROM locali WHERE nome = 'Sala Giochi Roma'));

INSERT INTO torneo_locali (torneo_id, locale_id)
SELECT (SELECT id FROM tornei WHERE nome = 'Torneo Bocce Estate 2026'), (SELECT id FROM locali WHERE nome = 'Sala Giochi Roma')
WHERE NOT EXISTS (SELECT 1 FROM torneo_locali WHERE torneo_id = (SELECT id FROM tornei WHERE nome = 'Torneo Bocce Estate 2026') AND locale_id = (SELECT id FROM locali WHERE nome = 'Sala Giochi Roma'));

INSERT INTO torneo_locali (torneo_id, locale_id)
SELECT (SELECT id FROM tornei WHERE nome = 'Torneo Bocce Estate 2026'), (SELECT id FROM locali WHERE nome = 'Circolo Ricreativo Napoli')
WHERE NOT EXISTS (SELECT 1 FROM torneo_locali WHERE torneo_id = (SELECT id FROM tornei WHERE nome = 'Torneo Bocce Estate 2026') AND locale_id = (SELECT id FROM locali WHERE nome = 'Circolo Ricreativo Napoli'));

INSERT INTO torneo_locali (torneo_id, locale_id)
SELECT (SELECT id FROM tornei WHERE nome = 'Torneo Bocce Estate 2026'), (SELECT id FROM locali WHERE nome = 'Casa Privata Torino')
WHERE NOT EXISTS (SELECT 1 FROM torneo_locali WHERE torneo_id = (SELECT id FROM tornei WHERE nome = 'Torneo Bocce Estate 2026') AND locale_id = (SELECT id FROM locali WHERE nome = 'Casa Privata Torino'));

INSERT INTO torneo_locali (torneo_id, locale_id)
SELECT (SELECT id FROM tornei WHERE nome = 'Gran Premio Monopoli 2026'), (SELECT id FROM locali WHERE nome = 'Bar Sport Milano')
WHERE NOT EXISTS (SELECT 1 FROM torneo_locali WHERE torneo_id = (SELECT id FROM tornei WHERE nome = 'Gran Premio Monopoli 2026') AND locale_id = (SELECT id FROM locali WHERE nome = 'Bar Sport Milano'));

INSERT INTO torneo_locali (torneo_id, locale_id)
SELECT (SELECT id FROM tornei WHERE nome = 'Gran Premio Monopoli 2026'), (SELECT id FROM locali WHERE nome = 'Circolo Ricreativo Napoli')
WHERE NOT EXISTS (SELECT 1 FROM torneo_locali WHERE torneo_id = (SELECT id FROM tornei WHERE nome = 'Gran Premio Monopoli 2026') AND locale_id = (SELECT id FROM locali WHERE nome = 'Circolo Ricreativo Napoli'));

INSERT INTO torneo_locali (torneo_id, locale_id)
SELECT (SELECT id FROM tornei WHERE nome = 'Gran Premio Monopoli 2026'), (SELECT id FROM locali WHERE nome = 'Casa Privata Torino')
WHERE NOT EXISTS (SELECT 1 FROM torneo_locali WHERE torneo_id = (SELECT id FROM tornei WHERE nome = 'Gran Premio Monopoli 2026') AND locale_id = (SELECT id FROM locali WHERE nome = 'Casa Privata Torino'));

-- ── PARTITE ──────────────────────────────────────────────────────
-- Chiave naturale: coppia (gioco_id, iniziata) — non serve conoscere gli id
-- auto-generati, stesso principio usato sopra per locali/giochi/tornei.

-- 1) Calciobalilla — Bar Sport Milano — terminata, torneo Primavera
INSERT INTO partite (gioco_id, iniziata, terminata, stato, tipo, torneo_id)
SELECT (SELECT id FROM giochi WHERE identificatore = 'CALC-001' AND locale_id = (SELECT id FROM locali WHERE nome = 'Bar Sport Milano')),
       '2026-04-15 18:00:00', '2026-04-15 18:35:00', 'TERMINATA', 'A_SQUADRE',
       (SELECT id FROM tornei WHERE nome = 'Torneo Calciobalilla Primavera 2026')
WHERE NOT EXISTS (
    SELECT 1 FROM partite
    WHERE gioco_id = (SELECT id FROM giochi WHERE identificatore = 'CALC-001' AND locale_id = (SELECT id FROM locali WHERE nome = 'Bar Sport Milano'))
      AND iniziata = '2026-04-15 18:00:00'
);

-- 2) Freccette — Sala Giochi Roma — terminata, torneo Campionato Freccette
INSERT INTO partite (gioco_id, iniziata, terminata, stato, tipo, torneo_id)
SELECT (SELECT id FROM giochi WHERE identificatore = 'FREC-001' AND locale_id = (SELECT id FROM locali WHERE nome = 'Sala Giochi Roma')),
       '2026-05-10 19:00:00', '2026-05-10 19:20:00', 'TERMINATA', 'INDIVIDUALE',
       (SELECT id FROM tornei WHERE nome = 'Campionato Freccette 2026')
WHERE NOT EXISTS (
    SELECT 1 FROM partite
    WHERE gioco_id = (SELECT id FROM giochi WHERE identificatore = 'FREC-001' AND locale_id = (SELECT id FROM locali WHERE nome = 'Sala Giochi Roma'))
      AND iniziata = '2026-05-10 19:00:00'
);

-- 3) Bocce — Circolo Ricreativo Napoli — terminata, amichevole (nessun torneo)
INSERT INTO partite (gioco_id, iniziata, terminata, stato, tipo, torneo_id)
SELECT (SELECT id FROM giochi WHERE identificatore = 'BOCC-001' AND locale_id = (SELECT id FROM locali WHERE nome = 'Circolo Ricreativo Napoli')),
       '2026-05-20 17:00:00', '2026-05-20 17:45:00', 'TERMINATA', 'INDIVIDUALE', NULL
WHERE NOT EXISTS (
    SELECT 1 FROM partite
    WHERE gioco_id = (SELECT id FROM giochi WHERE identificatore = 'BOCC-001' AND locale_id = (SELECT id FROM locali WHERE nome = 'Circolo Ricreativo Napoli'))
      AND iniziata = '2026-05-20 17:00:00'
);

-- 4) Monopoli — Casa Privata Torino — terminata, amichevole
INSERT INTO partite (gioco_id, iniziata, terminata, stato, tipo, torneo_id)
SELECT (SELECT id FROM giochi WHERE identificatore = 'MONO-001' AND locale_id = (SELECT id FROM locali WHERE nome = 'Casa Privata Torino')),
       '2026-06-01 21:00:00', '2026-06-01 23:10:00', 'TERMINATA', 'INDIVIDUALE', NULL
WHERE NOT EXISTS (
    SELECT 1 FROM partite
    WHERE gioco_id = (SELECT id FROM giochi WHERE identificatore = 'MONO-001' AND locale_id = (SELECT id FROM locali WHERE nome = 'Casa Privata Torino'))
      AND iniziata = '2026-06-01 21:00:00'
);

-- 5) Calciobalilla 2 — Bar Sport Milano — in corso
INSERT INTO partite (gioco_id, iniziata, terminata, stato, tipo, torneo_id)
SELECT (SELECT id FROM giochi WHERE identificatore = 'CALC-002' AND locale_id = (SELECT id FROM locali WHERE nome = 'Bar Sport Milano')),
       '2026-07-03 10:00:00', NULL, 'IN_CORSO', 'A_SQUADRE', NULL
WHERE NOT EXISTS (
    SELECT 1 FROM partite
    WHERE gioco_id = (SELECT id FROM giochi WHERE identificatore = 'CALC-002' AND locale_id = (SELECT id FROM locali WHERE nome = 'Bar Sport Milano'))
      AND iniziata = '2026-07-03 10:00:00'
);

-- 6) Freccette 2 — Sala Giochi Roma — in corso, torneo Campionato Freccette
INSERT INTO partite (gioco_id, iniziata, terminata, stato, tipo, torneo_id)
SELECT (SELECT id FROM giochi WHERE identificatore = 'FREC-002' AND locale_id = (SELECT id FROM locali WHERE nome = 'Sala Giochi Roma')),
       '2026-07-03 11:00:00', NULL, 'IN_CORSO', 'INDIVIDUALE',
       (SELECT id FROM tornei WHERE nome = 'Campionato Freccette 2026')
WHERE NOT EXISTS (
    SELECT 1 FROM partite
    WHERE gioco_id = (SELECT id FROM giochi WHERE identificatore = 'FREC-002' AND locale_id = (SELECT id FROM locali WHERE nome = 'Sala Giochi Roma'))
      AND iniziata = '2026-07-03 11:00:00'
);

-- 7) Monopoli — Circolo Ricreativo Napoli — in corso
INSERT INTO partite (gioco_id, iniziata, terminata, stato, tipo, torneo_id)
SELECT (SELECT id FROM giochi WHERE identificatore = 'MONO-001' AND locale_id = (SELECT id FROM locali WHERE nome = 'Circolo Ricreativo Napoli')),
       '2026-07-03 09:30:00', NULL, 'IN_CORSO', 'INDIVIDUALE', NULL
WHERE NOT EXISTS (
    SELECT 1 FROM partite
    WHERE gioco_id = (SELECT id FROM giochi WHERE identificatore = 'MONO-001' AND locale_id = (SELECT id FROM locali WHERE nome = 'Circolo Ricreativo Napoli'))
      AND iniziata = '2026-07-03 09:30:00'
);

-- 8) Freccette — Bar Sport Milano — annullata
INSERT INTO partite (gioco_id, iniziata, terminata, stato, tipo, torneo_id)
SELECT (SELECT id FROM giochi WHERE identificatore = 'FREC-001' AND locale_id = (SELECT id FROM locali WHERE nome = 'Bar Sport Milano')),
       '2026-06-15 16:00:00', NULL, 'ANNULLATA', 'INDIVIDUALE', NULL
WHERE NOT EXISTS (
    SELECT 1 FROM partite
    WHERE gioco_id = (SELECT id FROM giochi WHERE identificatore = 'FREC-001' AND locale_id = (SELECT id FROM locali WHERE nome = 'Bar Sport Milano'))
      AND iniziata = '2026-06-15 16:00:00'
);

-- ── PARTITA_GIOCATORI (chi ha giocato ciascuna partita) ─────────
-- Chiave naturale: coppia (partita_id, utente_id).

INSERT INTO partita_giocatori (partita_id, utente_id)
SELECT (SELECT id FROM partite WHERE iniziata = '2026-04-15 18:00:00'), (SELECT id FROM utenti WHERE username = 'giuseppe_f')
WHERE NOT EXISTS (SELECT 1 FROM partita_giocatori WHERE partita_id = (SELECT id FROM partite WHERE iniziata = '2026-04-15 18:00:00') AND utente_id = (SELECT id FROM utenti WHERE username = 'giuseppe_f'));

INSERT INTO partita_giocatori (partita_id, utente_id)
SELECT (SELECT id FROM partite WHERE iniziata = '2026-04-15 18:00:00'), (SELECT id FROM utenti WHERE username = 'marco_e')
WHERE NOT EXISTS (SELECT 1 FROM partita_giocatori WHERE partita_id = (SELECT id FROM partite WHERE iniziata = '2026-04-15 18:00:00') AND utente_id = (SELECT id FROM utenti WHERE username = 'marco_e'));

INSERT INTO partita_giocatori (partita_id, utente_id)
SELECT (SELECT id FROM partite WHERE iniziata = '2026-05-10 19:00:00'), (SELECT id FROM utenti WHERE username = 'sofia_r')
WHERE NOT EXISTS (SELECT 1 FROM partita_giocatori WHERE partita_id = (SELECT id FROM partite WHERE iniziata = '2026-05-10 19:00:00') AND utente_id = (SELECT id FROM utenti WHERE username = 'sofia_r'));

INSERT INTO partita_giocatori (partita_id, utente_id)
SELECT (SELECT id FROM partite WHERE iniziata = '2026-05-10 19:00:00'), (SELECT id FROM utenti WHERE username = 'paolo_c')
WHERE NOT EXISTS (SELECT 1 FROM partita_giocatori WHERE partita_id = (SELECT id FROM partite WHERE iniziata = '2026-05-10 19:00:00') AND utente_id = (SELECT id FROM utenti WHERE username = 'paolo_c'));

INSERT INTO partita_giocatori (partita_id, utente_id)
SELECT (SELECT id FROM partite WHERE iniziata = '2026-05-20 17:00:00'), (SELECT id FROM utenti WHERE username = 'giulia_r')
WHERE NOT EXISTS (SELECT 1 FROM partita_giocatori WHERE partita_id = (SELECT id FROM partite WHERE iniziata = '2026-05-20 17:00:00') AND utente_id = (SELECT id FROM utenti WHERE username = 'giulia_r'));

INSERT INTO partita_giocatori (partita_id, utente_id)
SELECT (SELECT id FROM partite WHERE iniziata = '2026-05-20 17:00:00'), (SELECT id FROM utenti WHERE username = 'marco_e')
WHERE NOT EXISTS (SELECT 1 FROM partita_giocatori WHERE partita_id = (SELECT id FROM partite WHERE iniziata = '2026-05-20 17:00:00') AND utente_id = (SELECT id FROM utenti WHERE username = 'marco_e'));

INSERT INTO partita_giocatori (partita_id, utente_id)
SELECT (SELECT id FROM partite WHERE iniziata = '2026-06-01 21:00:00'), (SELECT id FROM utenti WHERE username = 'giuseppe_f')
WHERE NOT EXISTS (SELECT 1 FROM partita_giocatori WHERE partita_id = (SELECT id FROM partite WHERE iniziata = '2026-06-01 21:00:00') AND utente_id = (SELECT id FROM utenti WHERE username = 'giuseppe_f'));

INSERT INTO partita_giocatori (partita_id, utente_id)
SELECT (SELECT id FROM partite WHERE iniziata = '2026-06-01 21:00:00'), (SELECT id FROM utenti WHERE username = 'sofia_r')
WHERE NOT EXISTS (SELECT 1 FROM partita_giocatori WHERE partita_id = (SELECT id FROM partite WHERE iniziata = '2026-06-01 21:00:00') AND utente_id = (SELECT id FROM utenti WHERE username = 'sofia_r'));

INSERT INTO partita_giocatori (partita_id, utente_id)
SELECT (SELECT id FROM partite WHERE iniziata = '2026-06-01 21:00:00'), (SELECT id FROM utenti WHERE username = 'marco_e')
WHERE NOT EXISTS (SELECT 1 FROM partita_giocatori WHERE partita_id = (SELECT id FROM partite WHERE iniziata = '2026-06-01 21:00:00') AND utente_id = (SELECT id FROM utenti WHERE username = 'marco_e'));

INSERT INTO partita_giocatori (partita_id, utente_id)
SELECT (SELECT id FROM partite WHERE iniziata = '2026-06-01 21:00:00'), (SELECT id FROM utenti WHERE username = 'giulia_r')
WHERE NOT EXISTS (SELECT 1 FROM partita_giocatori WHERE partita_id = (SELECT id FROM partite WHERE iniziata = '2026-06-01 21:00:00') AND utente_id = (SELECT id FROM utenti WHERE username = 'giulia_r'));

INSERT INTO partita_giocatori (partita_id, utente_id)
SELECT (SELECT id FROM partite WHERE iniziata = '2026-07-03 10:00:00'), (SELECT id FROM utenti WHERE username = 'paolo_c')
WHERE NOT EXISTS (SELECT 1 FROM partita_giocatori WHERE partita_id = (SELECT id FROM partite WHERE iniziata = '2026-07-03 10:00:00') AND utente_id = (SELECT id FROM utenti WHERE username = 'paolo_c'));

INSERT INTO partita_giocatori (partita_id, utente_id)
SELECT (SELECT id FROM partite WHERE iniziata = '2026-07-03 10:00:00'), (SELECT id FROM utenti WHERE username = 'giulia_r')
WHERE NOT EXISTS (SELECT 1 FROM partita_giocatori WHERE partita_id = (SELECT id FROM partite WHERE iniziata = '2026-07-03 10:00:00') AND utente_id = (SELECT id FROM utenti WHERE username = 'giulia_r'));

INSERT INTO partita_giocatori (partita_id, utente_id)
SELECT (SELECT id FROM partite WHERE iniziata = '2026-07-03 11:00:00'), (SELECT id FROM utenti WHERE username = 'giuseppe_f')
WHERE NOT EXISTS (SELECT 1 FROM partita_giocatori WHERE partita_id = (SELECT id FROM partite WHERE iniziata = '2026-07-03 11:00:00') AND utente_id = (SELECT id FROM utenti WHERE username = 'giuseppe_f'));

INSERT INTO partita_giocatori (partita_id, utente_id)
SELECT (SELECT id FROM partite WHERE iniziata = '2026-07-03 11:00:00'), (SELECT id FROM utenti WHERE username = 'sofia_r')
WHERE NOT EXISTS (SELECT 1 FROM partita_giocatori WHERE partita_id = (SELECT id FROM partite WHERE iniziata = '2026-07-03 11:00:00') AND utente_id = (SELECT id FROM utenti WHERE username = 'sofia_r'));

INSERT INTO partita_giocatori (partita_id, utente_id)
SELECT (SELECT id FROM partite WHERE iniziata = '2026-07-03 09:30:00'), (SELECT id FROM utenti WHERE username = 'marco_e')
WHERE NOT EXISTS (SELECT 1 FROM partita_giocatori WHERE partita_id = (SELECT id FROM partite WHERE iniziata = '2026-07-03 09:30:00') AND utente_id = (SELECT id FROM utenti WHERE username = 'marco_e'));

INSERT INTO partita_giocatori (partita_id, utente_id)
SELECT (SELECT id FROM partite WHERE iniziata = '2026-07-03 09:30:00'), (SELECT id FROM utenti WHERE username = 'giulia_r')
WHERE NOT EXISTS (SELECT 1 FROM partita_giocatori WHERE partita_id = (SELECT id FROM partite WHERE iniziata = '2026-07-03 09:30:00') AND utente_id = (SELECT id FROM utenti WHERE username = 'giulia_r'));

INSERT INTO partita_giocatori (partita_id, utente_id)
SELECT (SELECT id FROM partite WHERE iniziata = '2026-07-03 09:30:00'), (SELECT id FROM utenti WHERE username = 'paolo_c')
WHERE NOT EXISTS (SELECT 1 FROM partita_giocatori WHERE partita_id = (SELECT id FROM partite WHERE iniziata = '2026-07-03 09:30:00') AND utente_id = (SELECT id FROM utenti WHERE username = 'paolo_c'));

INSERT INTO partita_giocatori (partita_id, utente_id)
SELECT (SELECT id FROM partite WHERE iniziata = '2026-06-15 16:00:00'), (SELECT id FROM utenti WHERE username = 'sofia_r')
WHERE NOT EXISTS (SELECT 1 FROM partita_giocatori WHERE partita_id = (SELECT id FROM partite WHERE iniziata = '2026-06-15 16:00:00') AND utente_id = (SELECT id FROM utenti WHERE username = 'sofia_r'));

-- ── RISULTATI (solo per le partite terminate) ───────────────────
-- Chiave naturale: partita_id (una partita ha al più un risultato).

INSERT INTO risultati (partita_id, vincitore_id, punteggio_json, dettagli_json)
SELECT (SELECT id FROM partite WHERE iniziata = '2026-04-15 18:00:00'),
       (SELECT id FROM utenti WHERE username = 'giuseppe_f'),
       '{"rosso":5,"blu":3}', '{"mvp":"giuseppe_f","goalTotali":8}'
WHERE NOT EXISTS (SELECT 1 FROM risultati WHERE partita_id = (SELECT id FROM partite WHERE iniziata = '2026-04-15 18:00:00'));

INSERT INTO risultati (partita_id, vincitore_id, punteggio_json, dettagli_json)
SELECT (SELECT id FROM partite WHERE iniziata = '2026-05-10 19:00:00'),
       (SELECT id FROM utenti WHERE username = 'paolo_c'),
       '{"sofia_r":142,"paolo_c":187}', '{"tiriTotali":15}'
WHERE NOT EXISTS (SELECT 1 FROM risultati WHERE partita_id = (SELECT id FROM partite WHERE iniziata = '2026-05-10 19:00:00'));

INSERT INTO risultati (partita_id, vincitore_id, punteggio_json, dettagli_json)
SELECT (SELECT id FROM partite WHERE iniziata = '2026-05-20 17:00:00'),
       (SELECT id FROM utenti WHERE username = 'giulia_r'),
       '{"giulia_r":4,"marco_e":2}', '{"distanzaMinimaCm":8}'
WHERE NOT EXISTS (SELECT 1 FROM risultati WHERE partita_id = (SELECT id FROM partite WHERE iniziata = '2026-05-20 17:00:00'));

INSERT INTO risultati (partita_id, vincitore_id, punteggio_json, dettagli_json)
SELECT (SELECT id FROM partite WHERE iniziata = '2026-06-01 21:00:00'),
       (SELECT id FROM utenti WHERE username = 'marco_e'),
       '{"giuseppe_f":0,"sofia_r":450,"marco_e":2380,"giulia_r":1120}', '{"durataMinuti":130,"proprietaAcquistate":9}'
WHERE NOT EXISTS (SELECT 1 FROM risultati WHERE partita_id = (SELECT id FROM partite WHERE iniziata = '2026-06-01 21:00:00'));

-- ── PRENOTAZIONI ─────────────────────────────────────────────────
-- Chiave naturale: (locale_id, gioco_id, data_ora) — evita di dover
-- confrontare utente_id, che per le prenotazioni anonime è NULL.

INSERT INTO prenotazioni (utente_id, locale_id, gioco_id, data_ora, stato, creata)
SELECT (SELECT id FROM utenti WHERE username = 'giuseppe_f'),
       (SELECT id FROM locali WHERE nome = 'Bar Sport Milano'),
       (SELECT id FROM giochi WHERE identificatore = 'CALC-001' AND locale_id = (SELECT id FROM locali WHERE nome = 'Bar Sport Milano')),
       '2026-07-10 18:00:00', 'CONFERMATA', '2026-07-02 09:12:00'
WHERE NOT EXISTS (
    SELECT 1 FROM prenotazioni
    WHERE locale_id = (SELECT id FROM locali WHERE nome = 'Bar Sport Milano')
      AND gioco_id = (SELECT id FROM giochi WHERE identificatore = 'CALC-001' AND locale_id = (SELECT id FROM locali WHERE nome = 'Bar Sport Milano'))
      AND data_ora = '2026-07-10 18:00:00'
);

INSERT INTO prenotazioni (utente_id, locale_id, gioco_id, data_ora, stato, creata)
SELECT (SELECT id FROM utenti WHERE username = 'sofia_r'),
       (SELECT id FROM locali WHERE nome = 'Sala Giochi Roma'),
       (SELECT id FROM giochi WHERE identificatore = 'FREC-001' AND locale_id = (SELECT id FROM locali WHERE nome = 'Sala Giochi Roma')),
       '2026-07-12 20:00:00', 'CONFERMATA', '2026-07-02 14:47:00'
WHERE NOT EXISTS (
    SELECT 1 FROM prenotazioni
    WHERE locale_id = (SELECT id FROM locali WHERE nome = 'Sala Giochi Roma')
      AND gioco_id = (SELECT id FROM giochi WHERE identificatore = 'FREC-001' AND locale_id = (SELECT id FROM locali WHERE nome = 'Sala Giochi Roma'))
      AND data_ora = '2026-07-12 20:00:00'
);

INSERT INTO prenotazioni (utente_id, locale_id, gioco_id, data_ora, stato, creata)
SELECT NULL,
       (SELECT id FROM locali WHERE nome = 'Circolo Ricreativo Napoli'),
       (SELECT id FROM giochi WHERE identificatore = 'BOCC-001' AND locale_id = (SELECT id FROM locali WHERE nome = 'Circolo Ricreativo Napoli')),
       '2026-07-08 17:30:00', 'CONFERMATA', '2026-07-01 11:03:00'
WHERE NOT EXISTS (
    SELECT 1 FROM prenotazioni
    WHERE locale_id = (SELECT id FROM locali WHERE nome = 'Circolo Ricreativo Napoli')
      AND gioco_id = (SELECT id FROM giochi WHERE identificatore = 'BOCC-001' AND locale_id = (SELECT id FROM locali WHERE nome = 'Circolo Ricreativo Napoli'))
      AND data_ora = '2026-07-08 17:30:00'
);

-- ── EVENTI (storico eventi sensore sincronizzati dagli edge-server) ─
-- Chiave naturale: (gioco_id, tipo, ricevuto). NB: il sensor-simulator
-- genera sempre gli identificatori CALC-001/FREC-001/BOCC-001/MONO-001 su
-- locale-1 (vedi sensor-simulator), quindi gli eventi di esempio usano gli
-- stessi valori.

INSERT INTO eventi (gioco_id, locale_id, tipo, payload, ricevuto)
SELECT 'CALC-001', 'locale-1', 'INIZIO_PARTITA', '{"tipo":"INIZIO_PARTITA","giocoId":"CALC-001","localeId":"locale-1"}', '2026-07-03 10:00:02'
WHERE NOT EXISTS (SELECT 1 FROM eventi WHERE gioco_id = 'CALC-001' AND tipo = 'INIZIO_PARTITA' AND ricevuto = '2026-07-03 10:00:02');

INSERT INTO eventi (gioco_id, locale_id, tipo, payload, ricevuto)
SELECT 'CALC-001', 'locale-1', 'GOAL', '{"tipo":"GOAL","squadra":"ROSSO","punteggioRosso":1,"punteggioBlue":0,"giocoId":"CALC-001"}', '2026-07-03 10:02:14'
WHERE NOT EXISTS (SELECT 1 FROM eventi WHERE gioco_id = 'CALC-001' AND tipo = 'GOAL' AND ricevuto = '2026-07-03 10:02:14');

INSERT INTO eventi (gioco_id, locale_id, tipo, payload, ricevuto)
SELECT 'FREC-001', 'locale-1', 'TIRO', '{"tipo":"TIRO","giocoId":"FREC-001","punteggio":45,"zona":"ALTA"}', '2026-07-03 11:05:33'
WHERE NOT EXISTS (SELECT 1 FROM eventi WHERE gioco_id = 'FREC-001' AND tipo = 'TIRO' AND ricevuto = '2026-07-03 11:05:33');
