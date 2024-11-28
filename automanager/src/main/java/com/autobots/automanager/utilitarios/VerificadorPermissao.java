package com.autobots.automanager.utilitarios;

import java.util.Set;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import org.springframework.stereotype.Component;

@Component
public class VerificadorPermissao {
    public boolean verificar(Set<PerfilUsuario> cargoUsuario, Set<PerfilUsuario> permissao) {
        if (cargoUsuario.contains(PerfilUsuario.ROLE_GERENTE) && permissao.contains(PerfilUsuario.ROLE_ADMIN)) {
            return false;
        }
        if (cargoUsuario.contains(PerfilUsuario.ROLE_VENDEDOR) && !permissao.contains(PerfilUsuario.ROLE_CLIENTE)) {
            return false;
        }
        if (cargoUsuario.contains(PerfilUsuario.ROLE_CLIENTE) && !permissao.contains(PerfilUsuario.ROLE_CLIENTE)) {
            return false;
        }
        return true;
    }
}