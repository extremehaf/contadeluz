package scan.lucas.com.contadeluz

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.lblEmail
import kotlinx.android.synthetic.main.nav_header_main.lblNome
import kotlinx.android.synthetic.main.nav_header_main.*

import android.Manifest
import android.annotation.TargetApi
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import android.support.annotation.RequiresApi
import android.support.v7.app.AlertDialog
import android.system.Os.bind
import android.widget.TextView

import scan.lucas.com.contadeluz.DTO.Usuario
import scan.lucas.com.contadeluz.Helpers.PreferenceHelper.defaultPrefs
import scan.lucas.com.contadeluz.Helpers.PreferenceHelper.get
import scan.lucas.com.contadeluz.Helpers.PreferenceHelper.set
import com.google.gson.Gson
import kotlinx.android.synthetic.main.nav_header_main.view.*
import scan.lucas.com.contadeluz.Helpers.PreferenceHelper


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    final val permissoes: kotlin.Array<String> = arrayOf(Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        obterPermissao(this, permissoes)
        val gson = Gson()
        val prefs = PreferenceHelper.defaultPrefs(this)
        val json: String? = prefs["USUARIO"] //getter  prefs["USUARIO"]
        val user = gson.fromJson(json, Usuario::class.java)

        val headerView = nav_view.getHeaderView(0)
        headerView.lblNome!!.setText(user.nome)
        headerView.lblEmail!!.setText(user.email)
    }

    fun obterPermissao(context: Context, permissions: kotlin.Array<String>) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissoesAtivas()) {
//                    permissions granted, continue flow normally
            } else {
                requestMultiplePermissions();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun permissoesAtivas(): Boolean {
        for (permission in permissoes) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED)
                return false
        }
        return true
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun requestMultiplePermissions() {
        val remainingPermissions = permissoes.filter { checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED }
        requestPermissions(remainingPermissions.toTypedArray(), 101)
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: kotlin.Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101) {
            if (grantResults.any { it != PackageManager.PERMISSION_GRANTED }) {
                if (permissions.any { shouldShowRequestPermissionRationale(it) }) {
                    AlertDialog.Builder(this)
                            .setMessage("É necessario as permissoes")
                            .setPositiveButton("Permitir") { dialog, which -> requestMultiplePermissions() }
                            .setNegativeButton("Cancelar") { dialog, which -> dialog.dismiss() }
                            .create()
                            .show()
                }
            }
            //all is good, continue flow
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_aparelhos -> {
                val intent = Intent(this, AparelhosActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_areas -> {
                val intent = Intent(this, AreasActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
