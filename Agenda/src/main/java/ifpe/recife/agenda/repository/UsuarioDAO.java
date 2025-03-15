package ifpe.recife.agenda.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ifpe.recife.agenda.model.Usuario;

import java.util.List;

@Repository
public class UsuarioDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Usuario encontrarPorEmail(String email) {
        String sql = "SELECT id, nome, email, senha FROM usuarios WHERE email = ?";
        @SuppressWarnings("deprecation")
		List<Usuario> usuarios = jdbcTemplate.query(sql, new Object[]{email}, (rs, rowNum) ->
                new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha")
                ));
        return usuarios.isEmpty() ? null : usuarios.get(0);
    }

    @SuppressWarnings("deprecation")
	public Usuario encontrarPorId(Integer id) {
        String sql = "SELECT id, nome, email, senha FROM usuarios WHERE id = ?";
        List<Usuario> usuarios = jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) ->
                new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha")
                ));
        return usuarios.isEmpty() ? null : usuarios.get(0);
    }


    public void salvar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome, email, senha) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, usuario.getNome(), usuario.getEmail(), usuario.getSenha());
    }

    public void atualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nome = ?, senha = ? WHERE id = ?";
        jdbcTemplate.update(sql, usuario.getNome(), usuario.getSenha(), usuario.getId());
    }
}