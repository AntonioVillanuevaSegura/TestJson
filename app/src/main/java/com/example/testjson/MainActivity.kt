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

        //var ejemplo ="""  [{'nom': 'DOUCHE', 'num': 0, 'ip': '192.168.6.101', 'port': '31420', 'nmsg': '0'}] """
        /*
         var ejemplo=binding.textView.text


         if (ejemplo.isNotEmpty()) {
         var jsonArray = JSONArray(ejemplo) //Creo un array JSON desde el ejemplo

         jsonRead(jsonArray) //Lee el array JSON , escribe campos IP,PORT, NMSG
         }

          */

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
            var txt=binding.textView

            txt.setText(result.contents)//Muestra el valor scaneado en viewText
            if (txt.text.isNotEmpty()) {

                var jsonArray = JSONArray(txt.text.toString()) //Creo un array JSON desde el ejemplo

                jsonRead(jsonArray) //Lee el array JSON , escribe campos IP,PORT, NMSG
            }

        }
    }

    //Lee el array JSON , escribe campos IP,PORT, NMSG
    fun jsonRead(jsonArray: JSONArray){

        if (jsonArray.length()==0){return}

        for (jsonIndex in 0..(jsonArray.length() - 1)) { //Recorro el array

            binding.textViewNom.setText(jsonArray.getJSONObject(jsonIndex).getString("nom"))
            binding.textViewNum.setText(jsonArray.getJSONObject(jsonIndex).getString("num"))
            binding.textViewIp.setText(jsonArray.getJSONObject(jsonIndex).getString("ip"))
            binding.textViewPort.setText(jsonArray.getJSONObject(jsonIndex).getString("port"))
            binding.textViewNmsg.setText(jsonArray.getJSONObject(jsonIndex).getString("nmsg"))
        }


    }

}