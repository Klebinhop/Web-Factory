package com.webfactory

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.jaredrummler.ktsh.Shell

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val make_button = findViewById<Button>(R.id.make_button)
        val app_name_widget = findViewById<EditText>(R.id.app_name_text)
        val app_adrees_widget = findViewById<EditText>(R.id.AppAdress)
        val toolbar_name = findViewById<TextView>(R.id.toolbar_title)
        var should_run = true

        @RequiresApi(api = Build.VERSION_CODES.M)
        fun ignoreBatteryOptimization() {
            val intent = Intent()
            val packageName = packageName
            val pm = getSystemService(POWER_SERVICE) as PowerManager
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                intent.data = Uri.parse("package:$packageName")
                startActivity(intent)
            }
        }
        ignoreBatteryOptimization()

        make_button.setOnClickListener {
            try {
                val verify_ = packageManager.getApplicationIcon("com.termux")
            } catch (e: Exception){
                val builder_1 = AlertDialog.Builder(this)
                builder_1.setTitle("ATTENTION")
                builder_1.setMessage("Please install termux before make app!\n" +
                        "After install termux allow external apps")
                val dialog = builder_1.create()
                dialog.show()
                should_run = false
            }

            if (should_run == true){
                val builder_2 = AlertDialog.Builder(this)
                builder_2.setMessage("Making App...")
                builder_2.setCancelable(false)
                val dialog2 = builder_2.create()
                dialog2.show()
                Shell.SU.run("am startservice --user 0 -n com.termux/com.termux.app.RunCommandService -a com.termux.RUN_COMMAND --es com.termux.RUN_COMMAND_PATH '/data/data/com.termux/files/usr/bin/bash' --esa com.termux.RUN_COMMAND_ARGUMENTS -c,'am start com.webfactory/.MainActivity > /dev/null; apt update; apt --option=Dpkg::Options::=--force-confdef install openssl wget -y; rm -rf Canary.zip WebfactoryBaseApp-Canary; wget https://github.com/Klebinhop/WebfactoryBaseApp/archive/refs/tags/Canary.zip && unzip Canary.zip && cd WebfactoryBaseApp-Canary/ && chmod +x build.sh && ./build.sh ${app_adrees_widget.text.toString()} ${app_name_widget.text.toString()}' --es com.termux.RUN_COMMAND_WORKDIR '/data/data/com.termux/files/home' --ez com.termux.RUN_COMMAND_BACKGROUND 'false' --es com.termux.RUN_COMMAND_SESSION_ACTION '0'")
            }
        }
    }
}