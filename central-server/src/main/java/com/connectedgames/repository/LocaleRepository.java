package com.connectedgames.repository;

import com.connectedgames.model.Locale;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LocaleRepository extends JpaRepository<Locale, Long> {
    List<Locale> findByTipo(Locale.TipoLocale tipo);
    List<Locale> findByAmministratoreId(Long adminId);
}