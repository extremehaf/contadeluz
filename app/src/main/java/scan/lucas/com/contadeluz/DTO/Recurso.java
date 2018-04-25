package scan.lucas.com.contadeluz.DTO;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucas on 03/04/2018.
 */

public class Recurso {
    private int id;
    private  String descricao;
    private int usuarioId;
    private String nome;
    private String foto;
    private String voltagem;
    private int potencia;
    private Usuario usuario;
    private List<ItemPerfil> itemPerfils;
    public Recurso(){

        this.foto = null;
        this.usuario = new Usuario();
        this.itemPerfils = new ArrayList<>();
    }

    public Recurso(String nome, String d, String f, String v, int p){
        this.nome = nome;
        this.descricao = d;
        this.foto = f;
        this.voltagem = v;
        this.potencia = p;
        this.usuario = new Usuario();
        this.itemPerfils = new ArrayList<>();
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getVoltagem() {
        return voltagem;
    }

    public void setVoltagem(String voltagem) {
        this.voltagem = voltagem;
    }

    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }
    public Bitmap retornaFotoBmp(){
        if(this.foto != null) {
            byte[] fotoByte = Base64.decode(this.foto, Base64.DEFAULT);
            Bitmap bmp = BitmapFactory.decodeByteArray(fotoByte, 0, fotoByte.length);
            return bmp;
        }

        return null;

    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ItemPerfil> getItemPerfils() {
        return itemPerfils;
    }

    public void setItemPerfils(List<ItemPerfil> itemPerfils) {
        this.itemPerfils = itemPerfils;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
