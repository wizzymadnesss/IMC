package com.example.imc

import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val spinnerSize: Spinner = findViewById(R.id.spinnerSize)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.size,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerSize.adapter = adapter
        }

        val spinnerWeight: Spinner = findViewById(R.id.spinnerWeight)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.weight,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerWeight.adapter = adapter
        }

        val spinnerLanguage: Spinner = findViewById(R.id.spinnerLanguage)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.language,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerLanguage.adapter = adapter
        }


//        spinnerWeight.onItemSelectedListener = this
//        spinnerSize.onItemSelectedListener = this
 //       spinnerLanguage.onItemSelectedListener = this

 //        spinnerLanguage.onItemSelectedListener = SpinnerActivity()




    }

    override fun onResume() {
        super.onResume()

        Toast.makeText(applicationContext, "${Locale.getDefault().toString()}", Toast.LENGTH_SHORT)
            .show()
       // if(Locale.ENGLISH.getDisplayCountry()==)
        if(Locale.getDefault().toString().equals("es_US")){
            Glide.with(imageView.context)
                .load(R.drawable.logo_imc)
                .into(imageView)
        }

        if(Locale.getDefault().toString().equals("en_US")){
            Glide.with(imageView.context)
                .load(R.drawable.logo_bmi)
                .into(imageView)
        }
    }


    /*
    val positiveButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(applicationContext,
            android.R.string.yes, Toast.LENGTH_SHORT).show()
    }
    val negativeButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(applicationContext,
            android.R.string.no, Toast.LENGTH_SHORT).show()
    }
    val neutralButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(applicationContext,
            "Maybe", Toast.LENGTH_SHORT).show()
    }
    */

    fun showDialog(view: View,imc: String,size:String,weight:String){


        var category:String = ""
        var recomendation=""
        if(imc.toDouble()<18.5){
            category = getString(R.string.category1)
            recomendation = getString(R.string.recomendation1)
        } else if(imc.toDouble()>=18.5 && imc.toDouble()<25){
            category = getString(R.string.category2)
            recomendation = getString(R.string.recomendation2)
        } else if(imc.toDouble()>=25 && imc.toDouble()<30){
            category = getString(R.string.category3)
            recomendation = getString(R.string.recomendation3)
        } else if(imc.toDouble()>=30 ) {
            category = getString(R.string.category4)
            recomendation = getString(R.string.recomendation4)
        }

        val builder = AlertDialog.Builder(this)
        with(builder)
        {

            if(Locale.getDefault().toString().equals("es_US")){
                val info="""Para la información que ingresó:

Estatura: $size metros

Peso: $weight kilogramos

Su IMC es $imc, lo que indica que su peso está en la categoría de $category para adultos de su misma estatura.

$recomendation"""

                setTitle(R.string.app_name)
                setMessage(info)

                /*
                setPositiveButton("OK", DialogInterface.OnClickListener(function = positiveButtonClick))
                setNegativeButton(android.R.string.no, negativeButtonClick)
                setNeutralButton("Maybe", neutralButtonClick)
                */

                setPositiveButton("OK", null)
                show()
            }

            if(Locale.getDefault().toString().equals("en_US")){

        val info = """For the information you entered:

Height: $size inches

Weight: $weight pounds

Your BMI is $imc, which indicates that your weight is in the $category for adults of the same height.

$recomendation"""

                setTitle(R.string.app_name)
                setMessage(info)

                /*
                setPositiveButton("OK", DialogInterface.OnClickListener(function = positiveButtonClick))
                setNegativeButton(android.R.string.no, negativeButtonClick)
                setNeutralButton("Maybe", neutralButtonClick)
                */

                setPositiveButton("OK", null)
                show()
            }

        }
    }


    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        Toast.makeText(applicationContext, "Seleccionado ${spinnerLanguage.getSelectedItem()}", Toast.LENGTH_SHORT)
            .show()


    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }

    fun calculateIMC(view: View){
        //Datos IMC
        val etSize=findViewById<EditText>(R.id.etSize)
        val etWeight=findViewById<EditText>(R.id.etWeight)


        if(!etSize.text.toString().isEmpty()){
            if(!etWeight.text.toString().isEmpty()){

                if(spinnerSize.getSelectedItem().equals("m") && spinnerWeight.getSelectedItem().equals("kg")){
                    val size=etSize.text.toString().toDouble()
                    val weight=etWeight.text.toString().toDouble()
                    val imc=weight.div(size.times(size))
                    showDialog(view,String.format("%.2f",imc),String.format("%.2f",size),String.format("%.2f",weight))
                } else if(spinnerSize.getSelectedItem().equals("in") && spinnerWeight.getSelectedItem().equals("lb")){
                    val size=etSize.text.toString().toDouble()
                    val weight=etWeight.text.toString().toDouble()
                    val imc=weight.div(size.times(size)).times(703)
                    showDialog(view,String.format("%.2f",imc),String.format("%.2f",size),String.format("%.2f",weight))
                } else {
                    Toast.makeText(applicationContext, R.string.msgErrorMedition, Toast.LENGTH_SHORT)
                        .show()
                }



            } else {
                Toast.makeText(applicationContext, R.string.msgErrorWeight, Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            Toast.makeText(applicationContext, R.string.msgErrorSize, Toast.LENGTH_SHORT)
            .show()
        }
    }



}