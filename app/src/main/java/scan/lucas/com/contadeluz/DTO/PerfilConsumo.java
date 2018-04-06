package scan.lucas.com.contadeluz.DTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucas on 05/04/2018.
 */

public class PerfilConsumo {
    private int id;
    private int usuarioId;
    private String tipo;
    private double icms;
    private double pis;
    private double cofins;
    private double kwh;
    private double consumoDiario;
    private double consumoMensal;
    private double valorEstimado;
    private List<ItemPerfil> itemPerfils;

    public PerfilConsumo() {
        this.itemPerfils = new ArrayList<>();
    }

    public PerfilConsumo(int id, int usuarioId, String tipo, double icms, double pis, double cofins, double kwh, double consumoDiario, double consumoMensal, double valorEstimado, List<ItemPerfil> itemPerfils) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.tipo = tipo;
        this.icms = icms;
        this.pis = pis;
        this.cofins = cofins;
        this.kwh = kwh;
        this.consumoDiario = consumoDiario;
        this.consumoMensal = consumoMensal;
        this.valorEstimado = valorEstimado;
        this.itemPerfils = itemPerfils;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getIcms() {
        return icms;
    }

    public void setIcms(double icms) {
        this.icms = icms;
    }

    public double getPis() {
        return pis;
    }

    public void setPis(double pis) {
        this.pis = pis;
    }

    public double getCofins() {
        return cofins;
    }

    public void setCofins(double cofins) {
        this.cofins = cofins;
    }

    public double getKwh() {
        return kwh;
    }

    public void setKwh(double kwh) {
        this.kwh = kwh;
    }

    public double getConsumoDiario() {
        return consumoDiario;
    }

    public void setConsumoDiario(double consumoDiario) {
        this.consumoDiario = consumoDiario;
    }

    public double getConsumoMensal() {
        return consumoMensal;
    }

    public void setConsumoMensal(double consumoMensal) {
        this.consumoMensal = consumoMensal;
    }

    public double getValorEstimado() {
        return valorEstimado;
    }

    public void setValorEstimado(double valorEstimado) {
        this.valorEstimado = valorEstimado;
    }

    public List<ItemPerfil> getItemPerfils() {
        return itemPerfils;
    }

    public void setItemPerfils(List<ItemPerfil> itemPerfils) {
        this.itemPerfils = itemPerfils;
    }
}
