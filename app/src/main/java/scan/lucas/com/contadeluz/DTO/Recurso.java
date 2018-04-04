package scan.lucas.com.contadeluz.DTO;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by lucas on 03/04/2018.
 */

public class Recurso {
    private  String descricao;
    private int usuario_id;
    private  String nome;
    private  byte[] foto;
    private String voltagem;
    private int potencia;

    public Recurso(){
        this.foto = null;
    }

    public Recurso(String nome, String d, byte[] f, String v, int p){
        this.nome = nome;
        this.descricao = d;
        this.foto = f;
        this.voltagem = v;
        this.potencia = p;
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

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
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
            Bitmap bmp = BitmapFactory.decodeByteArray(foto, 0, foto.length);
            return bmp;
        }

        return null;

    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }
}
