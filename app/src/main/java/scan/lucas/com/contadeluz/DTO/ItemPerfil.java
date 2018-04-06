package scan.lucas.com.contadeluz.DTO;

/**
 * Created by lucas on 05/04/2018.
 */

public class ItemPerfil {
    private int id;
    private int recursoId;
    private int perfilId;
    private int quantidade;
    private int dias_uso;
    private int tempo_uso;

    public int getId() {
        return id;
    }

    public ItemPerfil() {
    }

    public ItemPerfil(int id, int recursoId, int perfilId, int quantidade, int dias_uso, int tempo_uso) {
        this.id = id;
        this.recursoId = recursoId;
        this.perfilId = perfilId;
        this.quantidade = quantidade;
        this.dias_uso = dias_uso;
        this.tempo_uso = tempo_uso;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecursoId() {
        return recursoId;
    }

    public void setRecursoId(int recursoId) {
        this.recursoId = recursoId;
    }

    public int getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(int perfilId) {
        this.perfilId = perfilId;
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
}
