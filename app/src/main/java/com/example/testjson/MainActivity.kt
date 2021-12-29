/*
* Test y uso de JSONTokener Antonio Villanueva Segura
* https://developer.android.com/reference/org/json/JSONTokener
 */
package com.example.testjson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.testjson.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONStringer
import org.json.JSONTokener

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
        //Bindings
        var botonJson=binding.buttonAnalisis
        var txt =binding.textView
        var nom=binding.textViewNom
        var num=binding.textViewNum
        var ip=binding.textViewIp
        var port=binding.textViewPort
        var nmsg=binding.textViewNmsg


        var ejemplo ="""  [{'nom': 'DOUCHE', 'num': 0, 'ip': '192.168.6.101', 'port': '31420', 'nmsg': '0'}] """

        var  jsonArray = JSONArray (ejemplo) //Creo un array JSON

        for (jsonIndex in 0..(jsonArray.length() - 1)) { //Recorro el array

            nom.setText(jsonArray.getJSONObject(jsonIndex).getString("nom"))
            num.setText(jsonArray.getJSONObject(jsonIndex).getString("num"))
            ip.setText(jsonArray.getJSONObject(jsonIndex).getString("ip"))
            port.setText(jsonArray.getJSONObject(jsonIndex).getString("port"))
            nmsg.setText(jsonArray.getJSONObject(jsonIndex).getString("nmsg"))
        }


    }
}