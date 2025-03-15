package ifpe.recife.agenda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ifpe.recife.agenda.model.Contato;
import ifpe.recife.agenda.repository.ContatoDAO;

import java.util.List;

@Service
public class ContatoService {

    @Autowired
    private ContatoDAO contatoDAO;

    public void salvarContato(Integer usuarioId, String nome, String email, String endereco, List<String> telefones) {
        Contato contato = new Contato();
        contato.setUsuarioId(usuarioId);
        contato.setNome(nome);
        contato.setEmail(email);
        contato.setEndereco(endereco);
        contato.setTelefones(telefones);
        contatoDAO.salvar(contato);
    }

    public List<Contato> obterContatosPorUsuarioId(Integer usuarioId) {
        return contatoDAO.encontrarPorUsuarioId(usuarioId);
    }

    public List<Contato> pesquisarContatosPorNome(Integer usuarioId, String nome) {
        return contatoDAO.encontrarPorUsuarioIdENome(usuarioId, nome);
    }
}