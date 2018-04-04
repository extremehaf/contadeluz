package scan.lucas.com.contadeluz.DTO;

import java.util.List;

/**
 * Created by lucas on 04/04/2018.
 */

public class AreaConsumo {
    private int id;
    private int usuario_id;
    private String nome;
    private String tipo;
    private double icms;
    private double pis;
    private double cofis;
    private double kwh;
    private double consumoDiario;
    private double consumoMensal;
    private double consumoAnual;
    private double valorEstimado;
    private List<Recurso> recursos;

    public AreaConsumo(){

    }
    public AreaConsumo(int id, int usuario_id, String nome,String tipo, double icms, double pis, double cofis, double kwh, double consumoDiario, double consumoMensal, double consumoAnual, double valorEstimado, List<Recurso> recursos ){
        this.id            = id;
        this.usuario_id    = usuario_id;
        this.nome          = nome;
        this.tipo          = tipo;
        this.icms = icms;
        this.pis           = pis;
        this.cofis         = cofis;
        this.kwh           = kwh;
        this.consumoDiario = consumoDiario;
        this.consumoMensal = consumoMensal;
        this.consumoAnual  = consumoAnual;
        this.valorEstimado =  valorEstimado;
        this.recursos		= recursos;	
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
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

    public double getCofis() {
        return cofis;
    }

    public void setCofis(double cofis) {
        this.cofis = cofis;
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

    public double getConsumoAnual() {
        return consumoAnual;
    }

    public void setConsumoAnual(double consumoAnual) {
        this.consumoAnual = consumoAnual;
    }

    public double getValorEstimado() {
        return valorEstimado;
    }

    public void setValorEstimado(double valorEstimado) {
        this.valorEstimado = valorEstimado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Recurso> getRecursos() {
        return recursos;
    }

    public void setRecursos(List<Recurso> recursos) {
        this.recursos = recursos;
    }
}
