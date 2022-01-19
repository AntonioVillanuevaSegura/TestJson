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

               //{'nom': 'DOUCHE', 'num': '0', 'ip': '192.168.6.101', 'port': '31420', 'nmsg': '0'}

                if (testJson(result.contents)) {//Analiza el texto , compatibilidad json
                    //Crea un jsonObject se tokeniza
                    val jsonObject = JSONTokener(result.contents).nextValue() as JSONObject
                    jsonRead(jsonObject)
                }else {txt.setText("Error QR !") }

            }
        }
    }

    /*Analisis texto si es compatible JSON
        No trabaja con un numero fijo de elementos , pueden faltar
     */
    fun testJson ( txt : String):Boolean{


        //El texto esta vacio ?
        if (txt.isEmpty()){return false}

        //Al inicio y al final contiene llaves {} ?
        if (txt.first()!='{' ){return false}
        if (txt.last()!='}') {return false}


        //Estructura ejemplo {'nom': 'DOUCHE', 'num': '0', 'ip': '192.168.6.101', 'port': '31420', 'nmsg': '0'}

        var comas=0
        var puntos=0
        var comillas=0

        //Analiza elementos :,' para el ejemplo anterior 5 puntos,4 comas, 18 comillas
        for (n in txt){ //Cuenta ptos,comas,comillas
            if (n==':') {puntos++}
            if (n==',') {comas++}
            if (n=='\'') {comillas++}
        }

        //Los puntos son igual a comas +1 ?
        if (puntos != comas+1){ return false }

        //Test ' comillas en el ejemplo elementos(5)*4=20
        if (comillas != puntos*4){ return false }

        return true

    }

    /*Lee un objeto JSON y escribe los campos en la vista android
        Permite utilizar una cantidad de elementos variable
     */
    fun jsonRead (jsonobject:JSONObject){
        /*
        binding.textViewNom.setText(jsonobject.getString("nom"))
        binding.textViewNum.setText(jsonobject.getString("num"))
        binding.textViewIp.setText(jsonobject.getString("ip"))
        binding.textViewPort.setText(jsonobject.getString("port"))
        binding.textViewNmsg.setText(jsonobject.getString("nmsg"))
         */


        if (jsonobject.has("nom")){
            binding.textViewNom.setText(jsonobject.getString("nom"))
        }

        if (jsonobject.has("num")) {
            binding.textViewNum.setText(jsonobject.getString("num"))
        }

        if (jsonobject.has("ip")) {
            binding.textViewIp.setText(jsonobject.getString("ip"))
        }

        if (jsonobject.has("port")) {
            binding.textViewPort.setText(jsonobject.getString("port"))
        }

        if (jsonobject.has("nmsg")) {
            binding.textViewNmsg.setText(jsonobject.getString("nmsg"))
        }


    }
}