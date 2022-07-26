package com.beyzayuksel.libraryapp;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    // Initialize variable
    // Pull library datalist to db
    private List<MainData> dataList;
    private Activity context;
    // Db instance need for datalist.
    private RoomDB database;


    // Create constructor
    public MainAdapter(Activity context, List<MainData> dataList){
        this.context = context;
        this.dataList = dataList;
        // Notify when data set changed
        notifyDataSetChanged();
    }

    //  Create which layout in using Recyler view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        // Initialize main data
        MainData data = dataList.get(position);
        // Initialize database
        database = RoomDB.getInstance(context);
        // Set text on text view
        holder.textView.setText(data.getText());

        // Edit Button listerner
        holder.btEdit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // Initialize main data
                MainData d = dataList.get(holder.getAdapterPosition());
                // Get ID
                int sID = d.getID();
                // Get text
                String sText = d.getText();

                // Create dialog -- for update
                Dialog dialog = new Dialog(context);
                // Set context view -- layout for update
                dialog.setContentView(R.layout.dialog_update);
                // Initialize width
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                // Initialize height
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                // Set layout
                dialog.getWindow().setLayout(width,height);
                // Show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText = dialog.findViewById(R.id.edit_text);
                Button btUpdate = dialog.findViewById(R.id.bt_update);

                // Set text on edit text
                editText.setText(sText); // book name

                // Listener update btn
                btUpdate.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        // Dismiss dialog
                        dialog.dismiss();
                        // Get updated text from edit text
                        String uText = editText.getText().toString().trim();
                        // Update text in database
                        database.mainDao().update(sID, uText);
                        // Notify when data is updated
                        dataList.clear();
                        dataList.addAll(database.mainDao().getAll());
                        notifyDataSetChanged();
                    }
                });  // end of update button
            }
        }); // end of edit button

        // Delete Button listerner
        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize main data
                MainData d = dataList.get(holder.getAdapterPosition());

                // Create dialog -- for delete
                Dialog dialog = new Dialog(context);
                // Set context view -- layout for update
                dialog.setContentView(R.layout.dialog_delete);
                // Initialize width
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                // Initialize height
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                // Set layout
                dialog.getWindow().setLayout(width,height);
                // Show dialog
                dialog.show();

                // Initialize and assign variable
                TextView textView = dialog.findViewById(R.id.delete_text);
                Button btDeleteYes = dialog.findViewById(R.id.delete_yes);
                Button btDeleteNo = dialog.findViewById(R.id.delete_no);

                // Set text on data for deleting
                textView.setText("Are you sure you want to delete " + d.getText() + " ?"); // book name

                // Listener Yes btn for delete.
                btDeleteYes.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        // Dismiss dialog
                        dialog.dismiss();

                        // Delete text from database
                        database.mainDao().delete(d);
                        // Notify when data is deleted
                        int position = holder.getAdapterPosition();
                        dataList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, dataList.size());

                    }
                });  // end of yes button

                // Listener No btn for delete.
                btDeleteNo.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        // Cancel from dialog
                        dialog.cancel();
                    }
                });  // end of no button
            }
        }); // end of delete(rubbish icon) button

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Initialize variable
        TextView textView;
        ImageView btEdit, btDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Assign variable
            textView = itemView.findViewById(R.id.text_view);
            btEdit = itemView.findViewById(R.id.bt_edit);
            btDelete = itemView.findViewById(R.id.bt_delete);
        }
    }
}
