package health.app.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.apache.commons.lang3.StringEscapeUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import health.app.Model.ChatModal;
import health.app.R;

import static health.app.Model.ChatModal.RECVICE_TYPE;
import static health.app.Model.ChatModal.SENDTYPE;

/**
 * Created by Developer Six on 9/27/2017.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ChatModal> request_fund;
    Context context;
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public ChatAdapter(List<ChatModal> request_fund, Context context) {
        this.request_fund = request_fund;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view;

        switch (i) {
            case SENDTYPE:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.send_message_layout, viewGroup, false);
                return new Sender_view(view);
            case RECVICE_TYPE:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.received_message_layout, viewGroup, false);
                return new Reciver_view(view);
        }
        //    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.class_list_layout, viewGroup, false);
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        // viewHolder.class_name.setText(request_fund.get(i).getClassName());
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d;
        Long sec= Long.valueOf(0);
        try {
            if (request_fund.get(i).getCreatedOn()!=null){
                d = f.parse(request_fund.get(i).getCreatedOn());
                sec=d.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("Eror",e+"");
        }


        String date=  request_fund.get(i).getCreatedOn();


        ChatModal object = request_fund.get(i);
        if (object != null) {
            switch (object.getmType()) {
                case 0:
                    /*String date=  request_fund.get(i).getCreatedOn();
                    String oldFormat = "EEeHH:mm a";
                    String newFormat = "yyyy-MM-dd HH:mm:ss";

                    SimpleDateFormat sdf1 = new SimpleDateFormat(oldFormat);
                    SimpleDateFormat sdf2 = new SimpleDateFormat(newFormat);

                    try {
                        ((Sender_view) viewHolder).sendchat_time.setText(sdf1.format(sdf2.parse(date)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }*/
                    String serverResponse = object.getMsgText();
                    String fromServerUnicodeDecoded = StringEscapeUtils.unescapeJava(serverResponse);
                    ((Sender_view) viewHolder).sendchatmsg.setText(fromServerUnicodeDecoded);
                     ((Sender_view) viewHolder).sendchat_time.setText(getTimeAgo(sec, context));
                    break;
                case 1:
                    String date1=  request_fund.get(i).getCreatedOn();
                    String oldFormat1 = "HH:mm a";
                    String newFormat1 = "yyyy-MM-dd HH:mm:ss";

                    SimpleDateFormat sdf11 = new SimpleDateFormat(oldFormat1);
                    SimpleDateFormat sdf22 = new SimpleDateFormat(newFormat1);

                    try {
                        ((Reciver_view) viewHolder).recchat_time.setText(sdf11.format(sdf22.parse(date1)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String serverResponse1 = object.getMsgText();
                    String fromServerUnicodeDecoded1 = StringEscapeUtils.unescapeJava(serverResponse1);
                    ((Reciver_view) viewHolder).recechatmsg.setText(fromServerUnicodeDecoded1);
                    ((Reciver_view) viewHolder).recchat_time.setText(getTimeAgo(sec, context));

                    //  ((EventViewHolder) holder).mDescription.setText(object.getDescription());
                    break;
            }
        }

    }

    @Override
    public int getItemCount() {
        if (request_fund == null)
            return 0;
        return request_fund.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemViewType(int position) {
        if (request_fund != null) {
            ChatModal object = request_fund.get(position);
            if (object != null) {
                return object.getmType();
            }
        }
        return 0;
    }

    public static class Sender_view extends RecyclerView.ViewHolder {
        private TextView sendchatmsg,sendchat_time;
        public Sender_view(View itemView) {
            super(itemView);
            sendchatmsg = (TextView) itemView.findViewById(R.id.sendchatmsg);
            sendchat_time = (TextView) itemView.findViewById(R.id.sendchat_time);
        }
    }
    public static class Reciver_view extends RecyclerView.ViewHolder {
        private TextView recechatmsg;
        private TextView recchat_time;
        public Reciver_view(View itemView) {
            super(itemView);
            recechatmsg = (TextView) itemView.findViewById(R.id.recechatmsg);
            recchat_time = (TextView) itemView.findViewById(R.id.recchat_time);
        }
    }


    public static String getTimeAgo(long time, Context ctx) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
            Log.e("time","time: "+time);
        }

        long now = System.currentTimeMillis();
        Log.e("time", "now: " + now + " time: "+time);
        if (time > now || time <= 0) {
            return "just now";
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else
        {
            return diff / DAY_MILLIS + " days ago";
        }
    }


   /* public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView class_name;
        private CircleImageView class_image;

        public ViewHolder(View view) {
            super(view);
            class_name = (TextView) view.findViewById(R.id.class_name);

            class_image = (CircleImageView) view.findViewById(R.id.class_image);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putString("Class_id",request_fund.get(getAdapterPosition()).getClassId());
                    edit.commit();

                }
            });
        }
    }*/

}
