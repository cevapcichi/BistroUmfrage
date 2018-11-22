package de.munich.iteratec.bistroumfrage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Question> {

    public CustomAdapter(Context context, ArrayList<Question> list) {
        super(context,0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //inflate a new view out of given layout if convertView is to be found null.
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Question currentQuestion = getItem(position);

        //set text of TextView as text of current question.
        TextView questionList = (TextView) convertView.findViewById(R.id.questionList);
        questionList.setText(currentQuestion.getQuestion());

        return convertView;
    }
}
