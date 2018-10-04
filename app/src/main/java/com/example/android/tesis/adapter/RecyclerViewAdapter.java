package com.example.android.tesis.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.tesis.R;
import com.example.android.tesis.model.Ticket;
import com.example.android.tesis.model.TipoBoleto;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String LOG_TAG = RecyclerViewAdapter
            .class.getSimpleName();
    private ArrayList<TipoBoleto> tipoBoletosList = new ArrayList<>();
    private Ticket ticket;
    public static int personas;
    public static int autobuses;
    public static int autos;
    public static int cargas;
    public static int motos;
    public static double totalPersonas;
    public static double totalAutobuses;
    public static double totalAutos;
    public static double  totalCargas;
    public static double totalMotos;


    public RecyclerViewAdapter(Ticket ticket, ArrayList<TipoBoleto> tipoBoletosList) {
        this.tipoBoletosList = tipoBoletosList;
        this.ticket=ticket;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_recyclerview_layout,parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        int i= 0;
        final int y = position;
        holder.nombre.setText(tipoBoletosList.get(position).getNombre());

        if(tipoBoletosList.get(position).getNombre().equals("Adulto") ||tipoBoletosList.get(position).getNombre().equals("Niño")){
            i=ticket.getCapacidadPersonas(); }
        if(tipoBoletosList.get(position).getNombre().equals("Automovil")){
            i=ticket.getCapacidadAutos();}

        if(tipoBoletosList.get(position).getNombre().equals("Autobus")){
            i=ticket.getCapacidadAutobus(); }

        if(tipoBoletosList.get(position).getNombre().equals("Moto")){
            i=ticket.getCapacidadMotos(); }

        if(tipoBoletosList.get(position).getNombre().equals("Carga")){
            i=ticket.getCapacidadCarga(); }

        holder.cantidad.setText(""+i);
        holder.plus.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {

                                               String i = (String) holder.count.getText();
                                               int x = Integer.parseInt(i);
                                               x++;
                                               saveCount(x,tipoBoletosList.get(y).getNombre(),tipoBoletosList.get(y).getPrecio(), holder);
                                               holder.count.setText(""+x);
                                           }
                                       }
        );
        holder.less.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {

                                               String i = (String) holder.count.getText();
                                               int x = Integer.parseInt(i);
                                               if(x>0){
                                               x--;
                                               saveCount(x, tipoBoletosList.get(y).getNombre(),tipoBoletosList.get(y).getPrecio(), holder);
                                               holder.count.setText(""+x);}
                                           }
                                       }
        );

    }

    public void saveCount(int x, String nombre, Double precio, ViewHolder holder){


        if(nombre.equals("Adulto") || nombre.equals("Niño")){
            personas = x;
            totalPersonas= precio*x;
            holder.monto.setText("BsS."+(precio*x));}

        if(nombre.equals("Automovil")){
            autos=x;
            totalAutos= precio*x;
            holder.monto.setText("BsS."+(precio*x));}

        if(nombre.equals("Autobus")){
            autobuses = x;
            totalAutobuses= precio*x;
            holder.monto.setText("BsS."+(precio*x));}

        if(nombre.equals("Moto")){
            motos = x;
            totalMotos= precio*x;
            holder.monto.setText("BsS."+(precio*x));}

        if(nombre.equals("Carga")){
            cargas = x;
            totalCargas= precio*x;
            holder.monto.setText("BsS."+(precio*x));}
    }

    @Override
    public int getItemCount() {
        return tipoBoletosList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombre;
        TextView monto;
        TextView cantidad;
        Button less;
        Button plus;
        TextView count;

        public ViewHolder(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.nombre);
            monto= (TextView) itemView.findViewById(R.id.monto);
            cantidad = (TextView) itemView.findViewById(R.id.cantidad);
            less = (Button) itemView.findViewById(R.id.less);
            plus = (Button) itemView.findViewById(R.id.plus);
            count = (TextView) itemView.findViewById(R.id.count);

        }
    }
}
