package com.example.android.tesis.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.tesis.R;
import com.example.android.tesis.adapter.RecyclerViewAdapter;
import com.example.android.tesis.model.Cliente;
import com.example.android.tesis.model.Cuentas;
import com.example.android.tesis.model.TarjetaDebito;
import com.example.android.tesis.my_interface.APIService;
import com.example.android.tesis.network.ApiUtils;
import com.example.android.tesis.network.MainBluetooth;
import com.example.android.tesis.network.RetrofitInstance;
import com.example.android.tesis.utils.BluetoothConnectionService;
import com.example.android.tesis.utils.DatePickerFragment;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayDebito extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {


    private static final String LOG_TAG = Schedule.class.getSimpleName();
    private APIService apiService;  //OJO
    static String tipoTDD;
    double total;
    TarjetaDebito mTarjetaDebito;
    BluetoothConnectionService mBluetoothConnection = new BluetoothConnectionService(PayDebito.this);;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_debito_layout);

        //get the spinner from the xml.
        Spinner dropdown = (Spinner) findViewById(R.id.marcas);
//create a list of items for the spinner.
        String[] items = new String[]{"Seleccione","Maestro"};
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//set t he spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        total =(RecyclerViewAdapter.totalPersonas+RecyclerViewAdapter.totalAutos+RecyclerViewAdapter.totalAutobuses
                +RecyclerViewAdapter.totalMotos+RecyclerViewAdapter.totalCargas);
        TextView textMonto = (TextView) findViewById(R.id.total_monto);
        textMonto.setText(""+ total);
        Button dateSelector = (Button) findViewById(R.id.fecha_vencimiento_debito);

        dateSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.marcas);
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onBackPressed(){
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                tipoTDD="Maestro";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        SimpleDateFormat formateador = new SimpleDateFormat("MMM d, yyyy");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        Button dateSelector = (Button) findViewById(R.id.fecha_vencimiento_debito);
        dateSelector.setText(formateador.format(c.getTime()));
    }

    public void continuePayDebito(View view){

        String mNombre;
        String mApellido;
        String mCedula;
        int mTipo;
        String numberCount;
        String fecha;
        int cvC;

        EditText number = (EditText) findViewById(R.id.numero_debito);
        Button fechaVencimiento = (Button) findViewById(R.id.fecha_vencimiento_debito);
        EditText cvc = (EditText) findViewById(R.id.codigo_seguridad);
        TextView monto = (TextView) findViewById(R.id.total_monto);
        EditText nombre = (EditText) findViewById(R.id.nombre);
        EditText apellido = (EditText) findViewById(R.id.apellido);
        EditText cedula = (EditText) findViewById(R.id.ci);
        EditText tipo = (EditText) findViewById(R.id.tc);

        numberCount = number.getText().toString();
        fecha = fechaVencimiento.getText().toString();
        cvC = Integer.parseInt(cvc.getText().toString());
        total = Double.parseDouble(monto.getText().toString());
        mNombre = nombre.getText().toString();
        mApellido = apellido.getText().toString();
        mCedula = cedula.getText().toString();
        mTipo = Integer.parseInt(tipo.getText().toString());

        Cliente cliente = new Cliente();
        Cuentas cuentas = new Cuentas();
        cliente.setNombre(mNombre);
        cliente.setApellido(mApellido);
        cliente.setCi(mCedula);
        cuentas.setClienteId(cliente);
        cuentas.setTipo(mTipo);
        Log.d(LOG_TAG, "numero de tarjeta de credito1 ="+numberCount);

        TarjetaDebito tarjetaDebito = new TarjetaDebito(cvC, cuentas, fecha, getTipoTDD(), numberCount);
        Log.d(LOG_TAG, "numero de tarjeta de credito2= "+tarjetaDebito.getNumero());

        setTdD(tarjetaDebito);

//        Call<Boolean> call = apiService.doCheckPayDebito(total,fechaVen,tarjetaDebito);
        // Al servicio Web hay que pasarle el Monto A Pagar y LA informacion de la tarjeta de debito
        //y la fecha de vencimiento.
    }

    private String getTipoTDD(){
        return tipoTDD;
    }

    private void setTdD(TarjetaDebito tarjetaDebito){
        this.mTarjetaDebito = tarjetaDebito;
    }

    private TarjetaDebito getTdD(){
        return mTarjetaDebito;
    }

}
