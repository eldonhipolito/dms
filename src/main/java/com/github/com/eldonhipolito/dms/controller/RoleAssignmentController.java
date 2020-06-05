package com.github.com.eldonhipolito.dms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.com.eldonhipolito.dms.request.RoleAssignmentRequest;
import com.github.com.eldonhipolito.dms.response.GenericResponse;
import com.github.com.eldonhipolito.dms.service.RoleAssignmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("role-assignments")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RoleAssignmentController {

  private final RoleAssignmentService roleAssignmentService;

  @PostMapping("/")
  public ResponseEntity<GenericResponse> store(@RequestBody RoleAssignmentRequest request) {

    log.debug("[ROLE ASSIGNMENT] - store ({})", request.toString());

    GenericResponse response;

    try {
      roleAssignmentService.addRoleAssignment(request);
      response = new GenericResponse(true, "Successfully saved role assignments.");
    } catch (Exception e) {
      log.error("Error while creating user : {}", e);
      response = new GenericResponse(false, "Error saving role assignments. Please try again.");
    }

    return new ResponseEntity<GenericResponse>(response, HttpStatus.OK);
  }
}
