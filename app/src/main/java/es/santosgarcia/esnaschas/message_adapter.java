package es.santosgarcia.esnaschas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by Santos on 05/02/2016.
 */
public class message_adapter extends ArrayAdapter<ParseObject> {

    protected Context mContext;
    protected List<ParseObject> mMessages;

    public message_adapter(Context context, List<ParseObject> messages) {
        super(context, R.layout.message_item, messages);
        mContext=context;
        mMessages=messages;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;

        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.message_item, null);
            holder=new ViewHolder();
            holder.iconImageView=(ImageView)convertView.findViewById(R.id.message_icon);
            holder.nameLabel = (TextView)convertView.findViewById(R.id.sender_label);
        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }
        ParseObject message = mMessages.get(position);

        if(message.getString(ParseConstants.KEY_FILE_TYPE).equals(ParseConstants.TYPE_IMAGE)){
            holder.iconImageView.setImageResource(R.drawable.ic_action_picture);
        }
        else{
            holder.iconImageView.setImageResource(R.drawable.ic_action_play_over_video);
        }
        holder.iconImageView.setImageResource(R.drawable.ic_action_picture);
        holder.nameLabel.setText(message.getString(ParseConstants.KEY_SENDER_NAME));

        return convertView;
    }
    private static class ViewHolder {
        ImageView iconImageView;
        TextView nameLabel;
    }

}
