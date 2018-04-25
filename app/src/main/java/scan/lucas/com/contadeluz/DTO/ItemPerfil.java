package scan.lucas.com.contadeluz.DTO;

import android.app.ActivityManager;

/**
 * Created by lucas on 05/04/2018.
 */

public class ItemPerfil {
    private int id;
    private int recursoId;
    private int perfilId;
    private int quantidade;
    private int diasUso;
    private double tempo_uso;
    private Recurso recurso;
    private PerfilConsumo perfilConsumo;

    public int getId() {
        return id;
    }

    public ItemPerfil() {

    }

    public ItemPerfil(int id, int recursoId, int perfilId, int quantidade, int diasUso, double tempo_uso) {
        this.id = id;
        this.recursoId = recursoId;
        this.perfilId = perfilId;
        this.quantidade = quantidade;
        this.diasUso = diasUso;
        this.tempo_uso = tempo_uso;
        this.recurso = null;
        this.perfilConsumo = null;
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

    public int getDiasUso() {
        return diasUso;
    }

    public void setDiasUso(int diasUso) {
        this.diasUso = diasUso;
    }

    public double getTempo_uso() {
        return tempo_uso;
    }

    public void setTempo_uso(double tempo_uso) {
        this.tempo_uso = tempo_uso;
    }

    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }

    public PerfilConsumo getPerfilConsumo() {
        return perfilConsumo;
    }

    public void setPerfilConsumo(PerfilConsumo perfilConsumo) {
        this.perfilConsumo = perfilConsumo;
    }
}
