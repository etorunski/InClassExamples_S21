package algonquin.cst2335.inclassexamples_s21;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import in the manifest
public class ChatRoom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //load a layout:
        setContentView(R.layout.chatlayout);

        RecyclerView chatList = findViewById(R.id.myrecycler);
        chatList.setAdapter( new ChatAdapter()  );
        chatList.setLayoutManager(new LinearLayoutManager(this));
    }

    private class MyRowViews extends RecyclerView.ViewHolder{
        //this should the Widgets on a row , only have a TextView for message
        TextView rowMessage;

        public MyRowViews(View itemView) { //itemView is a ConstraintLayout, that has <TextView> as sub-item
            super(itemView);

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
            thisRowLayout.rowMessage.setText( "This is row"+ position );
        }

        @Override
        public int getItemCount() {
            return 10000; //how many items to show? //row layout is match_parent
        }
    }

    private class ChatMessage {  //Data model for a message in a row
        public String message;
    }

}
