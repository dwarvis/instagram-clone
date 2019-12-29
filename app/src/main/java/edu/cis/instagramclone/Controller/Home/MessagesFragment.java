package edu.cis.instagramclone.Controller.Home;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.cis.instagramclone.R;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageButton;
import com.scaledrone.lib.Scaledrone;
import com.scaledrone.lib.Room;

import java.util.Random;

import com.scaledrone.lib.RoomListener;
import com.scaledrone.lib.Listener;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import edu.cis.instagramclone.Model.MemberData;
import edu.cis.instagramclone.Model.Message;
import edu.cis.instagramclone.Model.MessageAdapter;

/**
 * Created by User on 5/28/2017.
 */

public class MessagesFragment extends Fragment implements RoomListener {
    private static final String TAG = "MessagesFragment";
    private String channelID = "wQ7jASukzP38NLF0";
    private String roomName = "observable-room";
    private EditText editText;
    private ListView messagesView;
    private ImageButton but;
    private Scaledrone scaledrone;
    private MessageAdapter messageAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        //getActivity().setContentView(R.layout.fragment_messages);

        editText = (EditText)view.findViewById(R.id.editText);
        messagesView = (ListView)view.findViewById(R.id.messages_view);
        messageAdapter = new MessageAdapter(getActivity());
        messagesView.setAdapter(messageAdapter);
        but = (ImageButton)view.findViewById(R.id.sendButton);

        but.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String message = editText.getText().toString();
                Log.d(TAG, message);
                if (message.length() > 0) {
                    scaledrone.publish("observable-room", message);
                    editText.getText().clear();
                }
            }
        });

        MemberData data = new MemberData(getRandomName(), getRandomColor());

        scaledrone = new Scaledrone(channelID, data);
        scaledrone.connect(new Listener() {
            @Override
            public void onOpen() {
                System.out.println("Scaledrone connection open");
                // Since the MainActivity itself already implement RoomListener we can pass it as a target
                scaledrone.subscribe(roomName, MessagesFragment.this);
            }

            @Override
            public void onOpenFailure(Exception ex) {
                System.err.println(ex);
            }

            @Override
            public void onFailure(Exception ex) {
                System.err.println(ex);
            }

            @Override
            public void onClosed(String reason) {
                System.err.println(reason);
            }
        });

        return view;
    }

    // Successfully connected to Scaledrone room
    @Override
    public void onOpen(Room room) {
        System.out.println("Connected to room");
    }

    // Connecting to Scaledrone room failed
    @Override
    public void onOpenFailure(Room room, Exception ex) {
        System.err.println(ex);
    }

    // Received a message from Scaledrone room
    @Override
    public void onMessage(Room room, com.scaledrone.lib.Message receivedMessage) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            // member.clientData is a MemberData object, let's parse it as such
            final MemberData data = mapper.treeToValue(receivedMessage.getMember().getClientData(), MemberData.class);
            // if the clientID of the message sender is the same as our's it was sent by us
            boolean belongsToCurrentUser = receivedMessage.getClientID().equals(scaledrone.getClientID());
            // since the message body is a simple string in our case we can use json.asText() to parse it as such
            // if it was instead an object we could use a similar pattern to data parsing
            final Message message = new Message(receivedMessage.getData().asText(), data, belongsToCurrentUser);
            Log.d(TAG, "received_message "+receivedMessage.toString());
            Log.d(TAG, "parsed_message "+message.getText());
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    messageAdapter.add(message);
                    // scroll the ListView to the last added element
                    messagesView.setSelection(messagesView.getCount() - 1);
                }
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private String getRandomName() {
        String[] adjs = {"autumn", "hidden", "bitter", "misty", "silent", "empty", "dry", "dark", "summer", "icy", "delicate", "quiet", "white", "cool", "spring", "winter", "patient", "twilight", "dawn", "crimson", "wispy", "weathered", "blue", "billowing", "broken", "cold", "damp", "falling", "frosty", "green", "long", "late", "lingering", "bold", "little", "morning", "muddy", "old", "red", "rough", "still", "small", "sparkling", "throbbing", "shy", "wandering", "withered", "wild", "black", "young", "holy", "solitary", "fragrant", "aged", "snowy", "proud", "floral", "restless", "divine", "polished", "ancient", "purple", "lively", "nameless"};
        String[] nouns = {"waterfall", "river", "breeze", "moon", "rain", "wind", "sea", "morning", "snow", "lake", "sunset", "pine", "shadow", "leaf", "dawn", "glitter", "forest", "hill", "cloud", "meadow", "sun", "glade", "bird", "brook", "butterfly", "bush", "dew", "dust", "field", "fire", "flower", "firefly", "feather", "grass", "haze", "mountain", "night", "pond", "darkness", "snowflake", "silence", "sound", "sky", "shape", "surf", "thunder", "violet", "water", "wildflower", "wave", "water", "resonance", "sun", "wood", "dream", "cherry", "tree", "fog", "frost", "voice", "paper", "frog", "smoke", "star"};
        return (
                adjs[(int) Math.floor(Math.random() * adjs.length)] +
                        "_" +
                        nouns[(int) Math.floor(Math.random() * nouns.length)]
        );
    }

    private String getRandomColor() {
        Random r = new Random();
        StringBuffer sb = new StringBuffer("#");
        while(sb.length() < 7){
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, 7);
    }

    public void sendMessage(View view) {
        String message = editText.getText().toString();
        if (message.length() > 0) {
            scaledrone.publish("observable-room", message);
            editText.getText().clear();
        }
    }
}
