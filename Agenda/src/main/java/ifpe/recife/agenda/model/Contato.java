package ifpe.recife.agenda.model;

import java.util.List;

public class Contato {
    private Integer id;
    private Integer usuarioId;
    private String nome;
    private String email;
    private String endereco;
    private List<String> telefones;

    public Contato() {
    }

    public Contato(Integer id, Integer usuarioId, String nome, String email, String endereco, List<String> telefones) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.telefones = telefones;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public List<String> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<String> telefones) {
        this.telefones = telefones;
    }
}