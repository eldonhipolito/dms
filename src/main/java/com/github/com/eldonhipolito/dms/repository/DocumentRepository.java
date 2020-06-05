package com.github.com.eldonhipolito.dms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.com.eldonhipolito.dms.core.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {

  List<Document> findDocumentByDocumentOwnerUsername(String username);
}
