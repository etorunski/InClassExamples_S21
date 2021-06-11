package algonquin.cst2335.inclassexamples_s21;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

//import in the manifest
public class ChatRoom extends AppCompatActivity {

    ArrayList<ChatMessage> messages = new ArrayList<>();//hold our typed messages
    ChatAdapter adt  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //load a layout:
        setContentView(R.layout.chatlayout);


        EditText messageTyped = findViewById(R.id.messageEdit);
        Button send = findViewById(R.id.sendbutton);
        RecyclerView chatList = findViewById(R.id.myrecycler);


        //add an adapter object to the RecyclerView
       adt = new ChatAdapter() ;

        //     build the list:
        chatList.setAdapter(adt); //need onCreateView, onBindView, getCount
        //   StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
      LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        chatList.setLayoutManager(layoutManager);


        send.setOnClickListener( click -> {
                    ChatMessage nextMessage = new ChatMessage( messageTyped.getText().toString()  );
                    messages.add(nextMessage);//adds to array list
                    //clear the edittext:
                    messageTyped.setText("");
                    //refresh the list:
                    adt.notifyItemInserted( messages.size() - 1 ); //just insert the new row:
                }
        );
    }

    private class MyRowViews extends RecyclerView.ViewHolder{
        //this should the Widgets on a row , only have a TextView for message
        TextView rowMessage;

        public MyRowViews(View itemView) { //itemView is a ConstraintLayout, that has <TextView> as sub-item
            super(itemView);

            itemView.setOnClickListener(  clik ->
            {
                AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );
                builder.setMessage( "Do you want to delete this?" )
                        .setTitle("Title").setPositiveButton("Yes", (dlg, clic) -> {

                            int row = getAbsoluteAdapterPosition();
                             messages.remove(row);//which message do you delete?
                          //update list view:
                            adt.notifyItemRemoved(row); })
                        .setNegativeButton("No", (dlg, clic) -> {  })
                        .create()
                        .show();

            });

            rowMessage = itemView.findViewById(R.id.message);
        }
    }

    private class ChatAdapter extends RecyclerView.Adapter{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater(); //LayoutInflater is for loading XML layouts
            View constraintLayout =  inflater.inflate(R.layout.sent_message, parent, false);//parent is for how much room does it have?

            return new MyRowViews(  constraintLayout  ); //will initialize the TextView
        }

        @Override               //says ViewHolder, but it's acually MyRowViews object
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) { //position is which row we're building

            MyRowViews thisRowLayout = (MyRowViews)holder;
            thisRowLayout.rowMessage.setText( "This is row "+ position );//sets the text on the row
            //set the date text:
        }

        @Override
        public int getItemCount() {
            return  messages.size(); //how many items to show?
        }
    }

    private class ChatMessage {  //Data model for a message in a row
        public String message;
        public int sendOrReceive;
        public String timeSent;

        public ChatMessage (String s)
        {
            message = s;
        }

        public ChatMessage(String message, int sendOrReceive, String timeSent) {
            this.message = message;
            this.sendOrReceive = sendOrReceive;
            this.timeSent = timeSent;
        }

        public String getMessage() {
            return message;
        }

        public int getSendOrReceive() {
            return sendOrReceive;
        }

        public String getTimeSent() {
            return timeSent;
        }
    }

}
