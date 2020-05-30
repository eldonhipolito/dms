package com.github.com.eldonhipolito.dms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.com.eldonhipolito.dms.core.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {

}
