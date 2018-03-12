package com.example.ajish.timetable;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.ajish.timetable.db.DbHandler;
import com.example.ajish.timetable.db.Lectures;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DayFragment extends Fragment implements LectureLoaderCallback {

    int pos;
    DbHandler db;
    List<Lectures> lecturesList;
    RecyclerView recyclerView;
    LectureAdapter lectureAdapter;
    boolean isVisible=false;
    boolean isCreated=false;
    String day;

    public DayFragment() {
        // Required empty public constructor
    }

    public static DayFragment newInstance(int pos){
        DayFragment f = new DayFragment();
        Bundle b = new Bundle();
        b.putInt("day",pos);
        f.setArguments(b);
        return f;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            isVisible = true;
            if(isCreated){
                lecturesList = new ArrayList<Lectures>();
                lectureAdapter = new LectureAdapter(new ArrayList<Lectures>(lecturesList));
                new LectureLoader(this).execute();
            }
        }else{
            isVisible = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_day, container, false);

        isCreated = true;

        db = new DbHandler(getContext());
        pos = getArguments().getInt("day");
        recyclerView = v.findViewById(R.id.lecture_recylerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        switch (pos){
            case 0:
                day = "sunday";
                break;
            case 1:
                day = "monday";
                break;
            case 2:
                day = "tuesday";
                break;
            case 3:
                day = "wednesday";
                break;
            case 4:
                day = "thursday";
                break;
            case 5:
                day = "friday";
                break;
            case 6:
                day = "saturday";
                break;

        }
        if(isVisible) {
            lecturesList = new ArrayList<Lectures>();
            lectureAdapter = new LectureAdapter(new ArrayList<Lectures>(lecturesList));
            new LectureLoader(this).execute();
        }

        return v;
    }

    @Override
    public void onLectureLoaded(ArrayList<Lectures> lectures) {

        this.lecturesList = lectures;
        lectureAdapter = new LectureAdapter(lectures);
        lectureAdapter.setOnItemClickListener(new LectureAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(getContext(), lecturesList.get(position).getSubject()+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View v, int position) {
                List<Lectures> l = lecturesList;

                showMenu(position, v);
            }
        });
        recyclerView.setAdapter(lectureAdapter);
    }

    public class LectureLoader extends AsyncTask<Void, Void, ArrayList<Lectures>>{

        private LectureLoaderCallback callback;

        LectureLoader(LectureLoaderCallback callback){
            this.callback = callback;
        }
        @Override
        protected ArrayList<Lectures> doInBackground(Void... voids) {
            lecturesList = new ArrayList<>(db.getDayLectures(day));
            return new ArrayList<>(lecturesList);
        }

        @Override
        protected void onPostExecute(final ArrayList<Lectures> lectures) {
            super.onPostExecute(lectures);
            callback.onLectureLoaded(lectures);

        }
    }

    public void showMenu(final int position12, View v){
        PopupMenu menu = new PopupMenu(getActivity().getApplicationContext(), v);
        menu.inflate(R.menu.lecture_menu);
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.delete_lecture:
                        db.deleteLecture(lecturesList.get(position12));
                        lecturesList.remove(position12);
                        lectureAdapter.notifyDataSetChanged();
                        return true;
                    default:
                        return false;
                }
            }
        });
        menu.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isVisible && isCreated) {
            lecturesList = new ArrayList<Lectures>();
            lectureAdapter = new LectureAdapter(new ArrayList<Lectures>(lecturesList));
            new LectureLoader(this).execute();
        }
    }
}
