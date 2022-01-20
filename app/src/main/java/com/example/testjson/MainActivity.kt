/*
* Test y uso de JSONTokener Antonio Villanueva Segura
* https://developer.android.com/reference/org/json/JSONTokener
 */
package com.example.testjson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testjson.databinding.ActivityMainBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult


import com.journeyapps.barcodescanner.ScanOptions

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        //Binding Button
        val botonJson=binding.buttonAnalisis

        //If you press the button, the scanner starts.
        botonJson.setOnClickListener{

        //We configure scanner options
        val scanner = ScanOptions()
        scannerOptions(scanner)

        //Launch the scanner, we can dispense the previous configuration code ....
        lanzaScanner.launch(scanner)

        }
    }

    // Registre el lanzador y el controlador de resultados
    private val lanzaScanner = registerForActivityResult(ScanContract())
    {    result: ScanIntentResult ->
        if (result.contents == null) {/*It doesn't do anything, if it hasn't retrieved anything */ }
        else {
            //Show scanned QR in EditView @+id/textViewReadText
            val txt=binding.textView
            txt.setText(result.contents)//Show scanned value in QR in viewTex

            if (txt.text.isNotEmpty()) {
              val json=Json(result.contents,this) //Instantiate the Json class with the String of the QR reading
                bindingViews(json) //Associate JSON reads value with views
            }
        }
    }

    fun scannerOptions(scanner: ScanOptions){//Configure scanner options
        //options.setDesiredBarcodeFormats(ScanOptions.ONE_D_CODE_TYPES)
        scanner.setPrompt("Scan a barcode")
        //options.setCameraId(0) // Use a specific camera of the device
        scanner.setBeepEnabled(true)
        scanner.setBarcodeImageEnabled(true)
        scanner.setOrientationLocked(false)
        scanner.setTimeout(25000)
        //scanner.setTorchEnabled(true)
    }

    fun bindingViews (json:Json){//Bindind nom,num,ip,port,nmsg
        //Bindings activity_main.xml
        binding.textViewNom.setText(json.getValue("nom"))
        binding.textViewNum.setText(json.getValue("num"))
        binding.textViewIp.setText(json.getValue("ip"))
        binding.textViewPort.setText(json.getValue("port"))
        binding.textViewNmsg.setText(json.getValue("nmsg"))

    }
}