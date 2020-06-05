package com.github.com.eldonhipolito.dms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.com.eldonhipolito.dms.core.Document;
import com.github.com.eldonhipolito.dms.repository.DocumentRepository;
import com.github.com.eldonhipolito.dms.request.CreateDocumentAccessRequest;
import com.github.com.eldonhipolito.dms.request.CreateDocumentRequest;
import com.github.com.eldonhipolito.dms.request.CreateDocumentRequestWrapper;
import com.github.com.eldonhipolito.dms.request.CreateDocumentSignatureRequest;
import com.github.com.eldonhipolito.dms.response.CreateDocumentAccessResponse;
import com.github.com.eldonhipolito.dms.response.DocumentCreationResponse;
import com.github.com.eldonhipolito.dms.response.DocumentResponse;
import com.github.com.eldonhipolito.dms.response.GenericResponse;
import com.github.com.eldonhipolito.dms.response.ListDocumentResponse;
import com.github.com.eldonhipolito.dms.service.DocumentAccessService;
import com.github.com.eldonhipolito.dms.service.DocumentService;
import com.github.com.eldonhipolito.dms.service.DocumentSignatureService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("documents")
public class DocumentController {

  private final DocumentService documentService;

  private final DocumentRepository documentRepository;

  private final DocumentSignatureService documentSignatureService;

  private final DocumentAccessService documentAccessService;

  @GetMapping("/{owner}")
  public ResponseEntity<ListDocumentResponse> listDocumentsByOwner(@PathVariable String owner) {
    log.trace("[DOCUMENT] - listDocumentsByOwner({})", owner);

    try {
      List<Document> documents = this.documentRepository.findDocumentByDocumentOwnerUsername(owner);

      List<DocumentResponse> docres = new ArrayList<DocumentResponse>();

      for (Document doc : documents) {
        docres.add(
            new DocumentResponse(
                doc.getId().intValue(),
                doc.getTitle(),
                doc.getDocumentHash(),
                doc.getNumberSignatoriesRequired()));
      }

      return new ResponseEntity<ListDocumentResponse>(
          new ListDocumentResponse(true, "success", docres), HttpStatus.OK);

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return new ResponseEntity<ListDocumentResponse>(
          new ListDocumentResponse(false, "Error retrieving list", null),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping(
      consumes = {"multipart/form-data"},
      method = RequestMethod.POST,
      path = "/create")
  public ResponseEntity<DocumentCreationResponse> createDocument(
      @ModelAttribute("file") MultipartFile file, @ModelAttribute("document") String document)
      throws IOException {

    CreateDocumentRequestWrapper createDocumentRequest = new CreateDocumentRequestWrapper();
    createDocumentRequest.setFile(file);
    createDocumentRequest.setDocument(document);

    log.info("[DOCUMENT] - createDocument({}, {})", document);

    DocumentCreationResponse response;
    try {
      CreateDocumentRequest req = createDocumentRequest.getDocument();
      req.setFile(file);
      String hash = this.documentService.createDocument(req);
      response = new DocumentCreationResponse(hash, true, "Document created");
    } catch (Exception e) {
      log.error("Error creating document :", e);
      response =
          new DocumentCreationResponse("", false, "Error encountered while creating document");
    }

    return new ResponseEntity<DocumentCreationResponse>(response, HttpStatus.CREATED);
  }

  @PostMapping("/documents/{documentId}/signature")
  public ResponseEntity<GenericResponse> createDocumentSignature(
      @RequestBody CreateDocumentSignatureRequest createDocumentSignatureRequest) {
    log.trace("DOCUMENT] - createDocumentSignatureRequest({})", createDocumentSignatureRequest);
    GenericResponse response;

    try {
      int result =
          this.documentSignatureService.createDocumentSignature(createDocumentSignatureRequest);
      response = new GenericResponse(true, "Successfully created document signature");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      response = new GenericResponse(false, "Error creating document signature");
    }

    return new ResponseEntity<GenericResponse>(response, HttpStatus.CREATED);
  }

  @PostMapping("/documents/{documentId}/grantaccess")
  public ResponseEntity<CreateDocumentAccessResponse> grantAccess(
      @RequestBody CreateDocumentAccessRequest request) {
    log.trace("[DOCUMENT] - grantAccess({})", request.getViewer());

    CreateDocumentAccessResponse response;
    try {

      String code = this.documentAccessService.grantAccess(request);

      response = new CreateDocumentAccessResponse(code, true, "Granted access");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      response = new CreateDocumentAccessResponse("", false, "Error granting access to document");
    }

    return new ResponseEntity<CreateDocumentAccessResponse>(response, HttpStatus.OK);
  }
}
