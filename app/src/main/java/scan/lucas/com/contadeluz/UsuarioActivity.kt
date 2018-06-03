package scan.lucas.com.contadeluz

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_usuario.*
import kotlinx.android.synthetic.main.content_usuario.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import scan.lucas.com.contadeluz.DTO.Usuario
import scan.lucas.com.contadeluz.Helpers.PreferenceHelper
import scan.lucas.com.contadeluz.Helpers.PreferenceHelper.get
import scan.lucas.com.contadeluz.Helpers.PreferenceHelper.set
import scan.lucas.com.contadeluz.REST.ApiClient
import scan.lucas.com.contadeluz.REST.Controller

class UsuarioActivity : AppCompatActivity() {

    var user = Usuario()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario)
        setSupportActionBar(toolbar)

        val gson = Gson()
        val prefs = PreferenceHelper.defaultPrefs(this)
        val json: String? = prefs["USUARIO"] //getter  prefs["USUARIO"]
        user = gson.fromJson(json, Usuario::class.java)

        txtNome.setText(user!!.nome)
        txtEmail.setText(user!!.email)
        txtEnd.setText(user!!.endereco)
        txtSenha.setText(user!!.senha)
        txtData.setText(user!!.dataNascimento.toString())

        btnSalvar.setOnClickListener { view ->
            salvarDados()
        }
        /*fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }*/


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun salvarDados() {
        showProgress(true)
        val controllerApi = Controller.createService(ApiClient::class.java)
        //vai na API com esse id e traz o cliente

        user!!.nome = txtNome.getText().toString();

        user!!.email = txtEmail.getText().toString();
        user!!.endereco = txtEnd.getText().toString();
        user!!.senha = txtSenha.getText().toString();

        val request: retrofit2.Call<Usuario>
        if (user!!.id > 0) {
            request = controllerApi.AtualizarUsuario(user.id, user)
        } else {
            request = controllerApi.registerUser(user)
        }
        request.enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {


                if (response != null && response.isSuccessful) {
                    user = response.body()!!

                    val prefs = PreferenceHelper.defaultPrefs(this@UsuarioActivity)
                    val gson = Gson()
                    val json = gson.toJson(user)
                    prefs["USUARIO"] = json;
                }
                showProgress(false)

            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                showProgress(false)
                Toast.makeText(applicationContext, "Erro ao salvar dados, tente novamnente", Toast.LENGTH_SHORT)
            }
        })
    }

    var mDialog: ProgressDialog? = null

    private fun showProgress(show: Boolean?) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (show!!)
            mDialog = ProgressDialog.show(this@UsuarioActivity, "", "Carregando. Por favor Aguarde...", true)
        else
            mDialog!!.dismiss()


    }


}
