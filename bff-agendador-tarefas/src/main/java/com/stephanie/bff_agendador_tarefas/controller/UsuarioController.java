package com.stephanie.bff_agendador_tarefas.controller;


import com.stephanie.bff_agendador_tarefas.business.UsuarioService;
import com.stephanie.bff_agendador_tarefas.business.dto.in.EnderecoDTORequest;
import com.stephanie.bff_agendador_tarefas.business.dto.in.LoginDTORequest;
import com.stephanie.bff_agendador_tarefas.business.dto.in.TelefoneDTORequest;
import com.stephanie.bff_agendador_tarefas.business.dto.out.EnderecoDTOResponse;
import com.stephanie.bff_agendador_tarefas.business.dto.out.TelefoneDTOResponse;
import com.stephanie.bff_agendador_tarefas.business.dto.in.UsuarioDTORequest;
import com.stephanie.bff_agendador_tarefas.business.dto.out.UsuarioDTOResponse;
import com.stephanie.bff_agendador_tarefas.infrastructure.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@Tag(name = "Usuário", description = "Cadastro e login de usuários")
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @Operation(summary = "Salvar Usuários", description = "Cria um novo usuário")
    @ApiResponse(responseCode = "200", description = "Usuário salvo com sucesso")
    @ApiResponse(responseCode = "409", description = "Usuário já cadastrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<UsuarioDTOResponse> salvaUsuario(@RequestBody UsuarioDTORequest usuarioDTO){
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDTO));
    }

    @PostMapping("/login")
    @Operation(summary = "Login de Usuários", description = "Login do usuário")
    @ApiResponse(responseCode = "200", description = "Usuário logado com sucesso")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public String login(@RequestBody LoginDTORequest usuarioDTO){
        return usuarioService.loginUsuario(usuarioDTO);
    }

    @GetMapping
    @Operation(summary = "Buscar dados do usuário pelo e-mail", description = "Buscar dados do usuário")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    @ApiResponse(responseCode = "403", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity <UsuarioDTOResponse> buscaUsuarioPorEmail(@RequestParam("email") String email,
                                                                   @RequestHeader(name = "Authorization", required = false) String token){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email, token));
    }

    @DeleteMapping("/{email}")
    @Operation(summary = "Deletar usuários por ID", description = "Deleta usuário")
    @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    @ApiResponse(responseCode = "403", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<Void> deletaUsuarioPorEmail(@PathVariable String email,
                                                      @RequestHeader(name = "Authorization", required = false) String token){
        usuarioService.deletaUsuarioPorEmail(email,token);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Operation(summary = "Atualizar dados do Usuário", description = "Atualizar dados do usuário")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    @ApiResponse(responseCode = "403", description = "Usuário não cadastrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<UsuarioDTOResponse> atualizaDadosUsuario(@RequestBody UsuarioDTORequest dto,
                                                                  @RequestHeader(name = "Authorization", required = false) String token){
        return ResponseEntity.ok(usuarioService.atualizarDadosUsuario(token, dto));
    }

    @PutMapping("/endereco")
    @Operation(summary = "Atualizar endereço do usuário", description = "Atualiza endereço do usuário")
    @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    @ApiResponse(responseCode = "403", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<EnderecoDTOResponse> atualizaEndereco(@RequestBody EnderecoDTORequest dto,
                                                                @RequestParam("id") Long id,
                                                                @RequestHeader(name = "Authorization", required = false) String token){
        return ResponseEntity.ok(usuarioService.atualizaEndereco(id, dto, token));
    }

    @PutMapping("/telefone")
    @Operation(summary = "Atualizar telefone do usuário", description = "Atualiza telefone do usuário")
    @ApiResponse(responseCode = "200", description = "Telefone atualizado com sucesso")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    @ApiResponse(responseCode = "403", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<TelefoneDTOResponse> atualizaTelefone(@RequestBody TelefoneDTORequest dto,
                                                                @RequestParam("id") Long id,
                                                                @RequestHeader(name = "Authorization", required = false) String token){
        return ResponseEntity.ok(usuarioService.atualizaTelefone(id, dto, token));
    }

    @PostMapping("/endereco")
    @Operation(summary = "Salva endereço do usuário", description = "Salva endereço do usuário")
    @ApiResponse(responseCode = "200", description = "Endereço salvo com sucesso")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    @ApiResponse(responseCode = "403", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<EnderecoDTOResponse> cadastraEndereco(@RequestBody EnderecoDTORequest dto,
                                                               @RequestHeader(name = "Authorization", required = false) String token){
        return ResponseEntity.ok(usuarioService.cadastraEndereco(token, dto));
    }

    @PostMapping("/telefone")
    @Operation(summary = "Salva telefone do usuário", description = "Salva telefone do usuário")
    @ApiResponse(responseCode = "200", description = "Telefone salvo com sucesso")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    @ApiResponse(responseCode = "403", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<TelefoneDTOResponse> cadastraTelefone(@RequestBody TelefoneDTORequest dto,
                                                                @RequestHeader(name = "Authorization", required = false) String token){
        return ResponseEntity.ok(usuarioService.cadastraTelefone(token, dto));
    }
}
