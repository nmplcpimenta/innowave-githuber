package pt.nmplcpimenta.innowavegithuber

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import pt.nmplcpimenta.innowave_githuber.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val i = Intent(this, GitHubUserListActivity::class.java)

        startActivity(i)
    }
}
