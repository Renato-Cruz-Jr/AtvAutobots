package com.autobots.automanager.utilitarios;

import java.util.ArrayList;
import java.util.List;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioSelecionador {
    public Usuario selecionarUsuarioNome(List<Usuario> usuarios, String usuarioNome) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCredencial().getNomeUsuario().equals(usuarioNome)) {
                return usuario;
            }
        }
        return null;
    }
    public List<Usuario> selecionarPorCargo(List<Usuario> usuarios, PerfilUsuario cargo){
        List<Usuario> filtrados = new ArrayList<Usuario>();
        for(Usuario usuario : usuarios) {
            if(usuario.getPerfis().contains(cargo)) {
                filtrados.add(usuario);
            }
        }
        return filtrados;
    }
}