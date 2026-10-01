package com.warrioraspirants.app;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.*;

public class ProgressStore {
    private final SharedPreferences p;

    public ProgressStore(Context c) {
        p = c.getSharedPreferences("warrior_progress", Context.MODE_PRIVATE);
    }

    public int xp(){ return p.getInt("xp", 0); }
    public int streak(){ return p.getInt("streak", 0); }
    public int solved(){ return p.getInt("solved", 0); }
    public int correct(){ return p.getInt("correct", 0); }
    public int prep(){ return p.getInt("prep", 0); }

    public void addResult(boolean isCorrect){
        int s = solved() + 1;
        int c = correct() + (isCorrect ? 1 : 0);
        int x = xp() + (isCorrect ? 10 : 2);
        int pr = Math.min(100, (s * 2));
        p.edit().putInt("solved", s).putInt("correct", c).putInt("xp", x).putInt("prep", pr).apply();
    }

    public void addXp(int amount){
        p.edit().putInt("xp", Math.max(0, xp()+amount)).apply();
    }

    public void setStreak(int value){
        p.edit().putInt("streak", Math.max(0,value)).apply();
    }

    public void saveMistake(String item){
        LinkedHashSet<String> set = new LinkedHashSet<>(p.getStringSet("mistakes", new LinkedHashSet<>()));
        set.add(item);
        p.edit().putStringSet("mistakes", set).apply();
    }

    public ArrayList<String> mistakes(){
        return new ArrayList<>(p.getStringSet("mistakes", new LinkedHashSet<>()));
    }

    public void clearMistakes(){
        p.edit().remove("mistakes").apply();
    }

    public int level(){
        return Math.max(1, (xp()/100)+1);
    }

    public int accuracy(){
        if(solved()==0) return 0;
        return Math.round((correct()*100f)/solved());
    }
}
