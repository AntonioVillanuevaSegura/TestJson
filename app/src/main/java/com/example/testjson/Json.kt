package com.example.testjson

import android.content.Context
import android.widget.Toast
import org.json.JSONObject
import org.json.JSONTokener

/*Class to perform operations on JSON
Create a JSON object from a string , use Context for error messages
 */
 class Json   (txt:String,private val context: Context){

    var jsonobject:JSONObject

    init {//child constructor for the JSONObject
        jsonobject=createJsonObject(txt)
    }

    fun testJson ( txt : String):Boolean{
        /*Analyze if the text is compatible JSON , returns true or false
         It does not work with a fixed number of KEY:VALUE elements, can use less values
         structure example {'KEY':"VALUE","KEY2":"VALUE2"}
        */

        //txt is Empty ?
        if (txt.isEmpty()){return false}

        //At the beginning and at the end contains curly braces  {} ?
        if (txt.first()!='{' ){return false}
        if (txt.last()!='}') {return false}

        //Example structure  {'nom': 'DOUCHE', 'num': '0', 'ip': '192.168.6.101', 'port': '31420', 'nmsg': '0'}
        var comas=0
        var puntos=0
        var comillas=0

        //Parse elements  :,'  for this example above 5 double points, 4 commas, 18 quotes
        for (n in txt){ //Count double dot, comma, quote
            if (n==':') {puntos++} //double dot :
            if (n==',') {comas++} //comma ,
            if (n=='\'') {comillas++} //quote '
        }

        //Points are equal to commas +1 ?
        if (puntos != comas+1){ return false }

        //Test ' quotes in the example elements(5)*4=20
        if (comillas != puntos*4){ return false }

        return true

    }

    fun getValue (key:String):String{
    //Returns value from  key , if it exists otherwise empty
        if (jsonobject.has(key)){ return  jsonobject.getString(key) }
        return ""
    }

    fun createJsonObject (jsonString:String): JSONObject {
        //create a json object from a string or empty JSONObject
        if (testJson(jsonString)) { //Test JSON string
            return  JSONTokener(jsonString).nextValue() as JSONObject
        }else{ Toast.makeText (context, " ERROR QR !", Toast.LENGTH_SHORT).show() }
        return JSONObject()
    }


}