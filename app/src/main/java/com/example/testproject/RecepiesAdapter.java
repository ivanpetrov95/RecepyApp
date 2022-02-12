package com.example.testproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.testproject.Entities.RecepyEntity;

import java.util.ArrayList;

public class RecepiesAdapter extends ArrayAdapter
{
    private ArrayList<RecepyEntity> recepies = new ArrayList<>();
    private Context context;


    public RecepiesAdapter(Context context, int textViewResourceId, ArrayList<RecepyEntity> objects)
    {
        super(context, textViewResourceId, objects);
        this.context = context;
        recepies = objects;
    }

    private RecepyEntity getObject(int position){
        return recepies.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View newView = convertView;
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        newView = inflater.inflate(R.layout.list_item_recepies, null);
        TextView textView = (TextView)newView.findViewById(R.id.userDataTextField);
        textView.setText(String.format("Recepy name:\n%s",
                recepies.get(position).getRecepyName())
        );
        ImageButton recepyInfoButton = newView.findViewById(R.id.infoRecepyButton);
        ImageButton deleteButton = newView.findViewById(R.id.deleteRecepyButton);
        ImageButton editButton = newView.findViewById(R.id.editRecepyButton);
        DatabaseHelper dbHelper = new DatabaseHelper(this.context);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecepyEntity resultEntity = getObject(position);
                dbHelper.removeRecepy(resultEntity.getId());
                recepies.remove(position);
                notifyDataSetChanged();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                RecepyEntity resultEntity = getObject(position);
                Intent editRecepyIntent = new Intent(context, EditRecepyActivity.class);
                editRecepyIntent.putExtra("resultEntity", resultEntity);
                context.startActivity(editRecepyIntent);
            }
        });


        recepyInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecepyEntity resultEntity = getObject(position);
                Intent infoRecepyIntent = new Intent(context, RecepyInfoActivity.class);
                infoRecepyIntent.putExtra("resultEntity", resultEntity);
                context.startActivity(infoRecepyIntent);
            }
        });
        return newView;
    }
}
