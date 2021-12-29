/*
* Test y uso de JSONTokener Antonio Villanueva Segura
* https://developer.android.com/reference/org/json/JSONTokener
 */
package com.example.testjson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.testjson.databinding.ActivityMainBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONStringer
import org.json.JSONTokener

import com.journeyapps.barcodescanner.ScanOptions

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





        //Si pulsa boton arranca scanner
       botonJson.setOnClickListener{
            //lanzaScanner.launch(ScanOptions())

            //Personifico ciertas opciones
            val options = ScanOptions()
            //options.setDesiredBarcodeFormats(ScanOptions.ONE_D_CODE_TYPES)
            options.setPrompt("Scan a barcode")
            //options.setCameraId(0) // Use a specific camera of the device

            options.setBeepEnabled(true)
            options.setBarcodeImageEnabled(true)
            options.setOrientationLocked(false)
            options.setTimeout(25000)
            //options.setTorchEnabled(true)

            //Lanza el scanner podemos prescindir de lo anterior
            lanzaScanner.launch(options)

        }



    }


    // Registre el lanzador y el controlador de resultados
    private val lanzaScanner = registerForActivityResult(ScanContract())
    {    result: ScanIntentResult ->
        if (result.contents == null) {/*No hace nada si no ha recuperado nada*/ }
        else {
            //Muestra scan en EditView @+id/textViewReadText
            binding.textView.setText(result.contents)//Muestra el valor scaneado en viewText
        }
    }


}