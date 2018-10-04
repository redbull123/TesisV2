package com.example.android.tesis.network;

import android.app.DatePickerDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.tesis.R;
import com.example.android.tesis.adapter.RecyclerViewAdapter;
import com.example.android.tesis.model.Cliente;
import com.example.android.tesis.model.Cuentas;
import com.example.android.tesis.model.TarjetaDebito;

import com.example.android.tesis.utils.BluetoothConnectionService;
import com.example.android.tesis.utils.DatePickerFragment;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import static android.widget.Toast.LENGTH_LONG;

public class MainBluetooth extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener{
    private static final String TAG = "MainBluetooth";
    static String tipoTDD;
    double total;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothConnectionService mBluetoothConnection;
    Button btnStartConnection;
    Button btnContinue;
    StringBuilder message;
    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    BluetoothDevice mBTDevice;

    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (action.equals(mBluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, mBluetoothAdapter.ERROR);

                switch(state){
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG, "onReceive: STATE OFF");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG, "mBroadcastReceiver1: STATE TURNING OFF");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG, "mBroadcastReceiver1: STATE ON");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG, "mBroadcastReceiver1: STATE TURNING ON");
                        break;
                }
            }
        }
    };

    /**
     * Broadcast Receiver for changes made to bluetooth states such as:
     * 1) Discoverability mode on/off or expire.
     */
    private final BroadcastReceiver mBroadcastReceiver2 = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {

                int mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);

                switch (mode) {
                    //Device is in Discoverable Mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Enabled.");
                        break;
                    //Device not in discoverable mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Able to receive connections.");
                        break;
                    case BluetoothAdapter.SCAN_MODE_NONE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Not able to receive connections.");
                        break;
                    case BluetoothAdapter.STATE_CONNECTING:
                        Log.d(TAG, "mBroadcastReceiver2: Connecting....");
                        break;
                    case BluetoothAdapter.STATE_CONNECTED:
                        Log.d(TAG, "mBroadcastReceiver2: Connected.");
                        break;
                }

            }
        }
    };

    /**
     * Broadcast Receiver for listing devices that are not yet paired
     * -Executed by btnDiscover() method.
     */
    private BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d(TAG, "onReceive: ACTION FOUND.");

            if (action.equals(BluetoothDevice.ACTION_FOUND)){
                BluetoothDevice device = intent.getParcelableExtra (BluetoothDevice.EXTRA_DEVICE);
                Log.d(TAG, "onReceive: " + device.getName() + ": " + device.getAddress());
                if(device.getName().equals("TesisBluetooth")){
                mBTDevice=device;
                Log.d(TAG, "onReceive: " + device.getName() + ": " + device.getAddress());
                setPaired(mBTDevice);
                btnContinue = (Button) findViewById(R.id.btnStartConnection);
                btnContinue.setEnabled(true);
                Toast.makeText(MainBluetooth.this,"Se enlazo con el Raspberry", LENGTH_LONG).show();}

            }
        }
    };

    /**
     * Broadcast Receiver that detects bond state changes (Pairing status changes)
     */
    private final BroadcastReceiver mBroadcastReceiver4 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if(action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)){
                BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                //3 cases:
                //case1: bonded already
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDED){
                    Log.d(TAG, "BroadcastReceiver: BOND_BONDED.");
                    //inside BroadcastReceiver4
                    mBTDevice = mDevice;
                }
                //case2: creating a bone
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDING) {
                    Log.d(TAG, "BroadcastReceiver: BOND_BONDING.");
                }
                //case3: breaking a bond
                if (mDevice.getBondState() == BluetoothDevice.BOND_NONE) {
                    Log.d(TAG, "BroadcastReceiver: BOND_NONE.");
                }
            }
        }
    };



    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: called.");
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver1);
        unregisterReceiver(mBroadcastReceiver2);
        unregisterReceiver(mBroadcastReceiver3);
        unregisterReceiver(mBroadcastReceiver4);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_bluetooth);

        btnStartConnection = (Button) findViewById(R.id.btnStartConnection);

        //Broadcasts when bond state changes (ie:pairing)
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mBroadcastReceiver4, filter);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        enableDisableBT();
        btnEnableDisable_Discoverable();

        btnStartConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startConnection();
            }
        });

        ImageView imageView = (ImageView) findViewById(R.id.iv_bluetooth);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDiscover();
            }
        });


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

        message = new StringBuilder();
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("incomingMessage"));
    }

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String text = intent.getStringExtra("TheMessage");

            message.append(text + "\n");

            Log.d(TAG,"enviado por el Raspberry="+ message);
            TextView mbt = (TextView) findViewById(R.id.messagebt);
            mbt.setText(message);
            Toast.makeText(MainBluetooth.this, "enviado por el Raspberry= "+message, Toast.LENGTH_SHORT).show();
        }
    };

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
        final int MESSAGE_READ = 0345;


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


        TarjetaDebito tarjetaDebito = new TarjetaDebito(cvC, cuentas, fecha, getTipoTDD(), numberCount);
        Log.d(TAG, "numero de tarjeta de credito2= "+tarjetaDebito.getNumero());


        // Al servicio Web hay que pasarle el Monto A Pagar y LA informacion de la tarjeta de debito
        //y la fecha de vencimiento.



               String palabra= "@"+numberCount+"@"+fecha+"@"+cvC+"@"+mNombre+"@"+mApellido+"@"+
                       mCedula+"@"+mTipo+"@"+Double.toString(total)+"@Maestro";
                byte[] bytes = palabra.getBytes(Charset.defaultCharset());
                Log.d(TAG,"bytes"+ bytes);
                mBluetoothConnection.write(bytes);
        Log.d(TAG, "InputStream in MainBluetooth: " + palabra);
    }

    private String getTipoTDD(){
        return tipoTDD;
    }


    //create method for starting connection
//***remember the conncction will fail and app will crash if you haven't paired first
    public void startConnection(){
        startBTConnection(mBTDevice,MY_UUID_INSECURE);
    }

    /**
     * starting chat service method
     */
    public void startBTConnection(BluetoothDevice device, UUID uuid){
        Log.d(TAG, "startBTConnection: Initializing RFCOM Bluetooth Connection."+"   nombre del dispositivo"+device.getName()+"codigo uuid"+uuid);

        mBluetoothConnection.startClient(device,uuid);
//        Intent in = new Intent(MainBluetooth.this, PayDebito.class);
//        startActivity(in);
    }


    public void enableDisableBT(){
        if(mBluetoothAdapter == null){
            Log.d(TAG, "enableDisableBT: Does not have BT capabilities.");
        }
        if(!mBluetoothAdapter.isEnabled()){
            Log.d(TAG, "enableDisableBT: enabling BT.");
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBTIntent);

            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver1, BTIntent);
        }
        if(mBluetoothAdapter.isEnabled()){
            Log.d(TAG, "enableDisableBT: disabling BT.");
            mBluetoothAdapter.disable();

            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver1, BTIntent);
        }

    }


    public void btnEnableDisable_Discoverable() {
        Log.d(TAG, "btnEnableDisable_Discoverable: Making device discoverable for 300 seconds.");

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);

        IntentFilter intentFilter = new IntentFilter(mBluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        registerReceiver(mBroadcastReceiver2,intentFilter);

    }

    public void btnDiscover() {
        Log.d(TAG, "btnDiscover: Looking for unpaired devices.");

        if(mBluetoothAdapter.isDiscovering()){
            mBluetoothAdapter.cancelDiscovery();
            Log.d(TAG, "btnDiscover: Canceling discovery.");

            //check BT permissions in manifest
            // checkBTPermissions();

            mBluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
        if(!mBluetoothAdapter.isDiscovering()){

            //check BT permissions in manifest
            // checkBTPermissions();

            mBluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
    }

      public void setPaired(BluetoothDevice device){
        //first cancel discovery because its very memory intensive.
        mBluetoothAdapter.cancelDiscovery();

        //create the bond.
        //NOTE: Requires API 17+? I think this is JellyBean
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2){
            Log.d(TAG, "Trying to pair with " + device.getName());
            if(device.getName().equals("TesisBluetooth")){
            device.createBond();

            mBTDevice = device;
            mBluetoothConnection = new BluetoothConnectionService(MainBluetooth.this);}
            else if(!device.getName().equals("TesisBluetooth")){
                Log.d(TAG, "No se puede conectar este dispositivo: " + device.getName());
            }
        }
    }
}

