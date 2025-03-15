package ifpe.recife.agenda.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ifpe.recife.agenda.model.Contato;

import java.util.List;

@Repository
public class ContatoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Contato> encontrarPorUsuarioId(Integer usuarioId) {
        String sql = "SELECT id, usuario_id, nome, email, endereco FROM contatos WHERE usuario_id = ?";
        List<Contato> contatos = jdbcTemplate.query(sql, new Object[]{usuarioId}, (rs, rowNum) -> {
            Contato contato = new Contato();
            contato.setId(rs.getInt("id"));
            contato.setUsuarioId(rs.getInt("usuario_id"));
            contato.setNome(rs.getString("nome"));
            contato.setEmail(rs.getString("email"));
            contato.setEndereco(rs.getString("endereco"));
            contato.setTelefones(encontrarTelefonesPorId(contato.getId()));
            return contato;
        });
        return contatos;
    }

    public List<Contato> encontrarPorUsuarioIdENome(Integer usuarioId, String nome) {
        String sql = "SELECT id, usuario_id, nome, email, endereco FROM contatos WHERE usuario_id = ? AND LOWER(nome) LIKE LOWER(?)";
        List<Contato> contatos = jdbcTemplate.query(sql, new Object[]{usuarioId, "%" + nome + "%"}, (rs, rowNum) -> {
            Contato contato = new Contato();
            contato.setId(rs.getInt("id"));
            contato.setUsuarioId(rs.getInt("usuario_id"));
            contato.setNome(rs.getString("nome"));
            contato.setEmail(rs.getString("email"));
            contato.setEndereco(rs.getString("endereco"));
            contato.setTelefones(encontrarTelefonesPorId(contato.getId()));
            return contato;
        });
        return contatos;
    }


    public void salvar(Contato contato) {
        String sqlContato = "INSERT INTO contatos (usuario_id, nome, email, endereco) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sqlContato, contato.getUsuarioId(), contato.getNome(), contato.getEmail(), contato.getEndereco());

        String sqlPegarContatoId = "SELECT id FROM contatos WHERE usuario_id = ? AND nome = ? ORDER BY id DESC LIMIT 1";
        Integer contatoId = jdbcTemplate.queryForObject(sqlPegarContatoId, Integer.class, contato.getUsuarioId(), contato.getNome());

        if (contato.getTelefones() != null) {
            for (String telefone : contato.getTelefones()) {
                salvarTelefone(contatoId, telefone);
            }
        }
    }

    private void salvarTelefone(Integer contatoId, String telefone) {
        String sqlTelefone = "INSERT INTO telefones (contato_id, numero) VALUES (?, ?)";
        jdbcTemplate.update(sqlTelefone, contatoId, telefone);
    }

    private List<String> encontrarTelefonesPorId(Integer contatoId) {
        String sql = "SELECT numero FROM telefones WHERE contato_id = ?";
        return jdbcTemplate.queryForList(sql, String.class, contatoId);
    }
}