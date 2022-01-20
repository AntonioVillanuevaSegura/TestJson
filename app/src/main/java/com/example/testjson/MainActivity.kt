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
import org.json.JSONException


import com.journeyapps.barcodescanner.ScanOptions

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        //Bindings
        val botonJson=binding.buttonAnalisis

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

            //Lanza el scanner , podemos prescindir de lo anterior
            lanzaScanner.launch(options)

        }
    }

    // Registre el lanzador y el controlador de resultados
    private val lanzaScanner = registerForActivityResult(ScanContract())
    {    result: ScanIntentResult ->
        if (result.contents == null) {/*No hace nada si no ha recuperado nada*/ }
        else {
            //Muestra scan en EditView @+id/textViewReadText
            val txt=binding.textView
            txt.setText(result.contents)//Muestra el valor scaneado en viewText

            if (txt.text.isNotEmpty()) {
              val json=Json(result.contents,this) //Instancia la clase Json con el String de la lectura

                //Bindings activity_main.xml
                binding.textViewNom.setText(json.getValue("nom"))
                binding.textViewNum.setText(json.getValue("num"))
                binding.textViewIp.setText(json.getValue("ip"))
                binding.textViewPort.setText(json.getValue("port"))
                binding.textViewNmsg.setText(json.getValue("nmsg"))

            }
        }
    }

}