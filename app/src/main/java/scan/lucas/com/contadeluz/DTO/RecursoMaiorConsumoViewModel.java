package scan.lucas.com.contadeluz.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lucas on 02/06/2018.
 */

public class RecursoMaiorConsumoViewModel {
    @SerializedName("idPerfil")
    @Expose
    private int IdPerfil;

    @SerializedName("nomePerfil")
    @Expose
    private String NomePerfil;

    @SerializedName("idRecurso")
    @Expose
    private int IdRecurso;

    @SerializedName("nomeRecurso")
    @Expose
    private String NomeRecurs;

    @SerializedName("kwhConsumo")
    @Expose
    private double KwhConsumo;

    @SerializedName("valorConsumo")
    @Expose
    private double ValorConsumo;

    public RecursoMaiorConsumoViewModel() {
    }

    public RecursoMaiorConsumoViewModel(int idPerfil, String nomePerfil, int idRecurso, String nomeRecurs, double kwhConsumo, double valorConsumo) {
        IdPerfil = idPerfil;
        NomePerfil = nomePerfil;
        IdRecurso = idRecurso;
        NomeRecurs = nomeRecurs;
        KwhConsumo = kwhConsumo;
        ValorConsumo = valorConsumo;
    }

    public int getIdPerfil() {
        return IdPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        IdPerfil = idPerfil;
    }

    public String getNomePerfil() {
        return NomePerfil;
    }

    public void setNomePerfil(String nomePerfil) {
        NomePerfil = nomePerfil;
    }

    public int getIdRecurso() {
        return IdRecurso;
    }

    public void setIdRecurso(int idRecurso) {
        IdRecurso = idRecurso;
    }

    public String getNomeRecurs() {
        return NomeRecurs;
    }

    public void setNomeRecurs(String nomeRecurs) {
        NomeRecurs = nomeRecurs;
    }

    public double getKwhConsumo() {
        return KwhConsumo;
    }

    public void setKwhConsumo(double kwhConsumo) {
        KwhConsumo = kwhConsumo;
    }

    public double getValorConsumo() {
        return ValorConsumo;
    }

    public void setValorConsumo(double valorConsumo) {
        ValorConsumo = valorConsumo;
    }
}
