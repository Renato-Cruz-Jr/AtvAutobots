package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.jwt.ProvedorJwt;
import com.autobots.automanager.services.AutenticacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AutenticacaoControle {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ProvedorJwt provedorJwt;

    @Autowired
    private AutenticacaoService autenticacaoService;

    @PostMapping("/registrar")
    public ResponseEntity<?> register(@RequestBody Credencial credencial) {
        try {
            Usuario usuario = autenticacaoService.registrarUsuario(credencial);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Erro ao registrar usuário");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getNomeUsuario(), login.getSenha())
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = provedorJwt.proverJwt(userDetails.getUsername());
            return ResponseEntity.ok().header("Authorization", "Bearer " + jwt).body("Login realizado com sucesso");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Nome ou senhas inválidos");
        }
    }
}