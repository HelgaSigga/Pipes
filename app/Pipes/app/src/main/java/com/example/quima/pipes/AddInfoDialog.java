package com.example.quima.pipes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by Quima on 25/02/2015.
 * A class for the dialog that comes up when adding a line to a database is requested
 */
public class AddInfoDialog extends DialogFragment implements DialogInterface.OnClickListener{

    AlertDialog alertDialog;
    EditText type;
    EditText area;
    EditText number;
    EditText location;
    EditText source;
    EditText comment;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        AlertDialog.Builder buildMe = new AlertDialog.Builder(getActivity());

        LayoutInflater inflateMe = getActivity().getLayoutInflater();

        buildMe.setView(inflateMe.inflate(R.layout.edit_dialog, null))

                .setPositiveButton(R.string.addDBactivity, this)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int i) {
                        AddInfoDialog.this.getDialog().cancel();
                    }
                });

        alertDialog = buildMe.create();
        return alertDialog;

    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.edit_dialog, container);
        type = (EditText) v.findViewById(R.id.editType);
        area = (EditText) v.findViewById(R.id.editArea);
        number = (EditText) v.findViewById(R.id.editNumber);
        location = (EditText) v.findViewById(R.id.editLocation);
        source = (EditText) v.findViewById(R.id.editSource);
        comment = (EditText) v.findViewById(R.id.editComment);
        return v;
    }*/

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {


        type = (EditText) alertDialog.findViewById(R.id.editType);
        area = (EditText) alertDialog.findViewById(R.id.editArea);
        number = (EditText) alertDialog.findViewById(R.id.editNumber);
        location = (EditText) alertDialog.findViewById(R.id.editLocation);
        source = (EditText) alertDialog.findViewById(R.id.editSource);
        comment = (EditText) alertDialog.findViewById(R.id.editComment);

        String Type = type.getText().toString();
        String Area = area.getText().toString();
        String Number = number.getText().toString();
        String Location = location.getText().toString();
        String Source = source.getText().toString();
        String Comment = comment.getText().toString();

        Database database = new Database(getActivity().getApplicationContext());
        if (database != null) {
            database.open();
            database.createValve(Type, Area, Number, Location, Source, Comment);
            database.close();
        }
    }
}
