package com.example.android.tesis.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.tesis.R;
import com.example.android.tesis.adapter.ScheduleModelAdapter;
import com.example.android.tesis.model.Itinerario;
import com.example.android.tesis.my_interface.APIService;
import com.example.android.tesis.network.ApiUtils;
import com.example.android.tesis.network.RetrofitInstance;
import com.example.android.tesis.utils.DatePickerFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rjsan on 5/13/2018.
 */
public class Schedule extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String LOG_TAG = Schedule.class.getSimpleName();
    static int selectedRoute = 0;
    static String route= null;
    static String fecha = null;
    static String hora = null;
    private List<Itinerario> scheduleList = new ArrayList<>();
    private APIService apiService;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    static int callSchedule=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_layout);

        ListView list = (ListView) findViewById(R.id.list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Itinerario x = (Itinerario) adapterView.getItemAtPosition(position);

                selectedRoute = x.getId();
                route = x.getRutaId().getRuta();
                fecha=x.getFecha();
                hora=x.getTime();

                if (HomeUser.ifLogin() == 1) {
                    Intent iti = new Intent(Schedule.this, TicketActivity.class);
                    startActivity(iti);

                } else if (HomeUser.ifLogin() == 0) {
                    callSchedule =1;
                    Intent iti = new Intent(Schedule.this, Login.class);
                    startActivity(iti);
                }
            }
        });

        Button dateSelector = (Button) findViewById(R.id.sort_by_editext_date);

        dateSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        Button searchSchedule = (Button) findViewById(R.id.search_list);

        searchSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncCaller().execute();

                mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
                mSwipeRefreshLayout.setOnRefreshListener(Schedule.this);
                mSwipeRefreshLayout.setVisibility(View.VISIBLE);

            }
        });
    }


    public void checkDate() {
        Button dateSelectorCheck = (Button) findViewById(R.id.sort_by_editext_date);
        Button searchSchedule = (Button) findViewById(R.id.search_list);
        if (!dateSelectorCheck.getText().toString().equals("Seleccione una fecha")) {
            searchSchedule.setEnabled(true);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        SimpleDateFormat formateador = new SimpleDateFormat("MMM d, yyyy");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        Button dateSelector = (Button) findViewById(R.id.sort_by_editext_date);
        dateSelector.setText(formateador.format(c.getTime()));
        checkDate();

    }

    public String obtainDate(String time) {
        String inputPattern = "MMM d, yyyy";
        String outputPattern = "yyyy-MM-dd'T'HH:mm:ssZ";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;
        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return str;
    }

    @Override
    public void onRefresh() {
        new AsyncCaller().execute();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    public String parseDateToddMMyyyy(String timeD) {
        String inputPatternDate = "yyyy-MM-dd'T'HH:mm:ssZ";
        String outputPatternDate = "EEE, MMM d";
        SimpleDateFormat inputFormatDate = new SimpleDateFormat(inputPatternDate);
        SimpleDateFormat outputFormatDate = new SimpleDateFormat(outputPatternDate);

        Date mDate = null;
        String strDate = null;
        try {
            mDate = inputFormatDate.parse(timeD);
            strDate = outputFormatDate.format(mDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d(LOG_TAG, "tiempo de salida" + strDate);

        return strDate;
    }

    public String parseTimeToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ssZ";
        String outputPattern = "h:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;
        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    private class AsyncCaller extends AsyncTask<Void, Void, Void> {
        ProgressDialog pdLoading = new ProgressDialog(Schedule.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (apiService == null) {
                apiService = RetrofitInstance.getRetrofitInstance(ApiUtils.BASE_URL).create(APIService.class);
            } else {
                Log.d(LOG_TAG, "el apiService está inicializado");
            }

            Button dateSelector = (Button) findViewById(R.id.sort_by_editext_date);

            Call<List<Itinerario>> call = apiService.doGetItinerariosList(obtainDate(dateSelector.getText().toString()));
            call.enqueue(new Callback<List<Itinerario>>() {
                @Override
                public void onResponse(Call<List<Itinerario>> call, Response<List<Itinerario>> response) {
                    scheduleList = response.body();
                    Log.d(LOG_TAG, "TAMANO DE LA LISTA" + response.body().size());
                    if (response.body().size() != 0) {
                        int i = 0;
                        for (Itinerario iti : scheduleList) {
                            iti.setFecha(parseDateToddMMyyyy(iti.getFecha()));
                            iti.setTime(parseTimeToddMMyyyy(iti.getTime()));
                            scheduleList.set(i, iti);
                            i++;
                        }
                        ListView listView = (ListView) findViewById(R.id.list);
                        ScheduleModelAdapter adapter = new ScheduleModelAdapter(Schedule.this, 0, scheduleList);
                        listView.setAdapter(adapter);
                    } else {

                        ListView listView = (ListView) findViewById(R.id.list);
                        ScheduleModelAdapter adapter = new ScheduleModelAdapter(Schedule.this, 0, scheduleList);
                        listView.setAdapter(adapter);
                        Toast.makeText(Schedule.this, "No se Encontro rutas para la fecha indicada",
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Itinerario>> call, Throwable t) {
                    call.cancel();
                    Toast.makeText(Schedule.this, "Problemas de Conexión",
                            Toast.LENGTH_LONG).show();
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pdLoading.dismiss();
        }

    }
}
