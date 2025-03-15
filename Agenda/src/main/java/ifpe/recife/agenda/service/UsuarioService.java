package ifpe.recife.agenda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ifpe.recife.agenda.model.Usuario;
import ifpe.recife.agenda.repository.UsuarioDAO;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    public Usuario registrarUsuario(String nome, String email, String senha) {
        if (usuarioDAO.encontrarPorEmail(email) != null) {
            return null; 
        }
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha); 
        usuarioDAO.salvar(usuario);
        return usuarioDAO.encontrarPorEmail(email); 
    }

    public Usuario logarUsuario(String email, String senha) {
        Usuario usuario = usuarioDAO.encontrarPorEmail(email);
        if (usuario != null && usuario.getSenha().equals(senha)) { 
            return usuario;
        }
        return null;
    }

    public Usuario atualizarUsuario(Integer usuarioId, String nome, String senha) {
        Usuario usuario = usuarioDAO.encontrarPorId(usuarioId);
        if (usuario != null) {
            usuario.setNome(nome);
            usuario.setSenha(senha); 
            usuarioDAO.atualizar(usuario);
            return usuarioDAO.encontrarPorId(usuarioId);
        }
        return null;
    }

    public Usuario encontrarUsuarioPorId(Integer usuarioId) {
        return usuarioDAO.encontrarPorId(usuarioId);
    }
}