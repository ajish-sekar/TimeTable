package com.example.ajish.timetable;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ajish.timetable.db.Lectures;

import java.util.ArrayList;

/**
 * Created by ajish on 3/8/18.
 */

public class LectureAdapter extends RecyclerView.Adapter<LectureAdapter.MyViewHolder>{
    private ArrayList<Lectures> lecturesList;

    private static ClickListener clickListener;
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.single_lecture, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Lectures lecture = lecturesList.get(position);
        holder.subject.setText(lecture.getSubject());
        holder.time.setText(lecture.getStartTime()+" - "+lecture.getEndTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(view, position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                clickListener.onItemLongClick(view, position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return lecturesList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView subject;
        TextView time;

        public MyViewHolder(View view){
            super(view);
            subject = (TextView) view.findViewById(R.id.subject);
            time = (TextView) view.findViewById(R.id.time);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        LectureAdapter.clickListener = clickListener;
    }

    public LectureAdapter(ArrayList<Lectures> lectures){
        this.lecturesList = lectures;
    }

    public interface ClickListener{
        void onItemClick(View v, int position);
        void onItemLongClick(View v, int position);
    }
}
