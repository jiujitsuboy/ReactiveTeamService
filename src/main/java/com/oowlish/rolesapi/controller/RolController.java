package com.oowlish.rolesapi.controller;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

import com.oowlish.rolesapi.hateoas.RolRepresentationModelAssembler;
import com.oowlish.rolesapi.hateoas.UserRolRepresentationModelAssembler;
import com.oowlish.rolesapi.model.Rol;
import com.oowlish.rolesapi.model.UserRol;
import com.oowlish.rolesapi.service.RolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rol")
@Api(value = "User Controller")
public class RolController {

  private final RolService service;
  private final RolRepresentationModelAssembler rolAssembler;
  private final UserRolRepresentationModelAssembler userRolAssembler;

  public RolController(RolService service, RolRepresentationModelAssembler rolAssembler, UserRolRepresentationModelAssembler userRolAssembler) {
    this.service = service;
    this.rolAssembler = rolAssembler;
    this.userRolAssembler = userRolAssembler;
  }

  @ApiOperation(value = "Update player", nickname = "updatePlayer", notes = "Allow to update firstname, lastname and country of the player-")
  @ApiResponses(value = {
      @ApiResponse(code = 202, message = "Update a couple of attributes of the player."),
      @ApiResponse(code = 500, message = "No Such Player Exception.") })
  @PatchMapping(value = "/")
  public ResponseEntity<UserRol> assignRol(@Valid @RequestBody(required = true) UserRol userRol) {
    return status(HttpStatus.ACCEPTED).body(userRolAssembler.toModel(service.assignRol(userRol)));
  }

  @ApiOperation(value = "Update player", nickname = "updatePlayer", notes = "Allow to update firstname, lastname and country of the player-")
  @ApiResponses(value = {
      @ApiResponse(code = 202, message = "Update a couple of attributes of the player."),
      @ApiResponse(code = 500, message = "No Such Player Exception.") })
  @PostMapping(value = "/")
  public ResponseEntity<Rol> createRol(@Valid @RequestBody(required = true) Rol rol) {
    return status(HttpStatus.CREATED).body(rolAssembler.toModel(service.create(rol)));
  }

  @ApiOperation(value = "Get Rol", nickname = "getRol", notes = "Retrieve a specific Roles-")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Retrieve all players.")})
  @GetMapping(value = "/{rolId}")
  public ResponseEntity<Rol> getRol(@PathVariable("rolId") Long rolId) {
      return ok(rolAssembler.toModel(service.getRol(rolId)));
  }

  @ApiOperation(value = "Get All Roles", nickname = "getRoles", notes = "Retrieve all Roles-")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Retrieve all players.")})
  @GetMapping(value = "/{userId}/{teamId}")
  public ResponseEntity<UserRol> getRolByMembership(@PathVariable("userId") String userId,@PathVariable("teamId") String teamId ) {
      return ok(userRolAssembler.toModel(service.getAssignedRol(userId, teamId)));
  }

  @ApiOperation(value = "Get All Roles", nickname = "getRoles", notes = "Retrieve all Roles-")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Retrieve all players.")})
  @GetMapping(value = "/")
  public ResponseEntity<List<Rol>> getRoles() {
      return ok(rolAssembler.toListModel(service.getRoles()));
  }

  @ApiOperation(value = "Get All Roles", nickname = "getRoles", notes = "Retrieve all Roles-")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Retrieve all players.")})
  @GetMapping(value = "/membership/{rol}")
  public ResponseEntity<List<UserRol>> getMemberships(@PathVariable("rol") String rol) {
      return ok(userRolAssembler.toListModel(service.getMemberShips(rol)));
  }
}