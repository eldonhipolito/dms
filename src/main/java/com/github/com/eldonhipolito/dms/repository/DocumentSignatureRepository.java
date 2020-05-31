package com.github.com.eldonhipolito.dms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.com.eldonhipolito.dms.core.DocumentSignature;

public interface DocumentSignatureRepository extends JpaRepository<DocumentSignature, Long> {

}
