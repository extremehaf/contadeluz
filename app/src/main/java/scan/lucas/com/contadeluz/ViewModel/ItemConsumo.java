package scan.lucas.com.contadeluz.ViewModel;

/**
 * Created by lucas on 05/04/2018.
 */

public class ItemConsumo
{
    private int recurso_id;
    private int perfil_id;
    private int quantidade;
    private int dias_uso;
    private int tempo_uso;

    private String recurso_nome;
    private String descricao;
    private int potencia_em_uso;

    public ItemConsumo() {
    }

    public ItemConsumo(int recurso_id, int perfil_id, int quantidade, int dias_uso, int tempo_uso, String recurso_nome, String descricao, int potencia_em_uso) {
        this.recurso_id = recurso_id;
        this.perfil_id = perfil_id;
        this.quantidade = quantidade;
        this.dias_uso = dias_uso;
        this.tempo_uso = tempo_uso;
        this.recurso_nome = recurso_nome;
        this.descricao = descricao;
        this.potencia_em_uso = potencia_em_uso;
    }

    public int getRecurso_id() {
        return recurso_id;
    }

    public void setRecurso_id(int recurso_id) {
        this.recurso_id = recurso_id;
    }

    public int getPerfil_id() {
        return perfil_id;
    }

    public void setPerfil_id(int perfil_id) {
        this.perfil_id = perfil_id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getDias_uso() {
        return dias_uso;
    }

    public void setDias_uso(int dias_uso) {
        this.dias_uso = dias_uso;
    }

    public int getTempo_uso() {
        return tempo_uso;
    }

    public void setTempo_uso(int tempo_uso) {
        this.tempo_uso = tempo_uso;
    }

    public String getRecurso_nome() {
        return recurso_nome;
    }

    public void setRecurso_nome(String recurso_nome) {
        this.recurso_nome = recurso_nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getPotencia_em_uso() {
        return potencia_em_uso;
    }

    public void setPotencia_em_uso(int potencia_em_uso) {
        this.potencia_em_uso = potencia_em_uso;
    }
}
