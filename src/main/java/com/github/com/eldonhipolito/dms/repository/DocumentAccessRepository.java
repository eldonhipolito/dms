package com.github.com.eldonhipolito.dms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.com.eldonhipolito.dms.core.DocumentAccess;

public interface DocumentAccessRepository extends JpaRepository<DocumentAccess, Long> {

	Optional<DocumentAccess> findByUserIdAndDocumentId(int userId, int documentId);

}
