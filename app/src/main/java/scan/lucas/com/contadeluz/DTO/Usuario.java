package scan.lucas.com.contadeluz.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lucas on 05/04/2018.
 */

public class Usuario  implements Serializable {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String endereco;
    private Date dataNascimento;
    private List<Recurso> recursos;
    private List<PerfilConsumo> perfilConsumos;
    public Usuario() {
        this.recursos = new ArrayList<>();
        this.perfilConsumos = new ArrayList<>();
    }

    public Usuario(int id, String nome, String email, String senha, String endereco, Date dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
        this.dataNascimento = dataNascimento;
        this.recursos = new ArrayList<>();
        this.perfilConsumos = new ArrayList<>();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public List<Recurso> getRecursos() {
        return recursos;
    }

    public void setRecursos(List<Recurso> recursos) {
        this.recursos = recursos;
    }

    public List<PerfilConsumo> getPerfilConsumos() {
        return perfilConsumos;
    }

    public void setPerfilConsumos(ArrayList<PerfilConsumo> perfilConsumos) {
        this.perfilConsumos = perfilConsumos;
    }
}
