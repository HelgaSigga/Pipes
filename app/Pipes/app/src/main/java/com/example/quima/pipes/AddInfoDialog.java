package com.example.quima.pipes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;

/**
 * Created by Quima on 25/02/2015.
 */
public class AddInfoDialog extends DialogFragment {

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

                .setPositiveButton(R.string.addDBactivity, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface d, int i) {
                                //add what is in the text fields to the database
                               /*
                                type = (EditText)findViewById(R.id.Type);
                                area = (EditText)findViewById(R.id.Area);
                                number = (EditText)findViewById(R.id.Number);
                                location = (EditText)findViewById(R.id.Location);
                                source = (EditText)findViewById(R.id.Source);
                                comment = (EditText)findViewById(R.id.Comment);

                                String Type = type.toString();
                                String Area = area.toString();
                                String Number = number.toString();
                                String Location = location.toString();
                                String Source = source.toString();
                                String Comment = comment.toString();

                                Database.createValve(Type, Area, Number, Location, Source, Comment);
                                */
                            }
                        }
                )
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int i) {
                        AddInfoDialog.this.getDialog().cancel();
                    }
                });

        return buildMe.create();

    }
}
