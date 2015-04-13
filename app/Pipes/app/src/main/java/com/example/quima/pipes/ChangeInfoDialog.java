package com.example.quima.pipes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Quima on 24/02/2015.
 * A class for the dialog that comes up when changing a line in a database is requested
 */
public class ChangeInfoDialog extends DialogFragment {

    int listPosition;
    static DatabaseAdapter adapter = null;
    View view;

    static final String ARG_LISTPOSITION = "listPosition";

    public static final ChangeInfoDialog newInstance(int listPosition){
        ChangeInfoDialog fragment = new ChangeInfoDialog();
        Bundle bundle = new Bundle(1);
        bundle.putInt(ARG_LISTPOSITION, listPosition);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static void setDatabaseAdapter(DatabaseAdapter databaseAdapter){
        adapter = databaseAdapter;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        listPosition = getArguments().getInt(ARG_LISTPOSITION);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.edit_dialog, null);
        builder.setView(view);
        builder.setPositiveButton(R.string.changeDBactivity, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface d, int i) {
                ValveModel valve = adapter.getItem(listPosition);
                EditText editText = (EditText) view.findViewById(R.id.editArea);
                valve.setArea(Integer.parseInt(editText.getText().toString()));
                editText = (EditText) view.findViewById(R.id.editComment);
                valve.setComment(editText.getText().toString());
                editText = (EditText) view.findViewById(R.id.editLocation);
                valve.setLocation(editText.getText().toString());
                editText = (EditText) view.findViewById(R.id.editNumber);
                valve.setNumber(Integer.parseInt(editText.getText().toString()));
                editText = (EditText) view.findViewById(R.id.editSource);
                valve.setSource(editText.getText().toString());
                editText = (EditText) view.findViewById(R.id.editType);
                valve.setType(editText.getText().toString());
                adapter.editItem(listPosition);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface d, int i) {
                ChangeInfoDialog.this.getDialog().cancel();
            }
        });

        if(adapter != null){
            ValveModel valve = adapter.getItem(listPosition);
            EditText editText = (EditText) view.findViewById(R.id.editArea);
            editText.setText("" + valve.getArea());
            editText = (EditText) view.findViewById(R.id.editComment);
            editText.setText(valve.getComment());
            editText = (EditText) view.findViewById(R.id.editLocation);
            editText.setText(valve.getLocation());
            editText = (EditText) view.findViewById(R.id.editNumber);
            editText.setText("" + valve.getNumber());
            editText = (EditText) view.findViewById(R.id.editSource);
            editText.setText(valve.getSource());
            editText = (EditText) view.findViewById(R.id.editType);
            editText.setText(valve.getType());
        }

        return builder.create();

    }


}
