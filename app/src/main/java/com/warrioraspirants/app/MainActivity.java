package com.warrioraspirants.app;

import android.app.*;
import android.os.*;
import android.content.*;
import android.net.Uri;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.*;
import android.widget.*;
import java.util.*;
import java.io.*;
import java.net.*;
import org.json.*;

public class MainActivity extends Activity {
    final int NAVY=Color.rgb(7,25,57), NAVY2=Color.rgb(16,44,86), RED=Color.rgb(204,42,50);
    final int BG=Color.rgb(247,249,252), WHITE=Color.WHITE, DARK=Color.rgb(27,35,48), MUTED=Color.rgb(105,114,130);
    boolean darkMode=false;
    LinearLayout root,body; ProgressStore store; ArrayList<String> mistakes=new ArrayList<>();
    int practiceIndex=0, practiceScore=0;
    String practiceTopicName="Mixed Practice";

    public void onCreate(Bundle b){
        super.onCreate(b);
        store=new ProgressStore(this); mistakes=store.mistakes();
        darkMode=getPreferences(MODE_PRIVATE).getBoolean("dark_mode",false); applyThemeBars();
        splash();
    }

    TextView text(String s,float size,int color,boolean bold){
        TextView t=new TextView(this); t.setText(s); t.setTextSize(size); t.setTextColor(color);
        t.setPadding(0,2,0,2); if(bold)t.setTypeface(Typeface.DEFAULT,Typeface.BOLD); return t;
    }
    GradientDrawable shape(int color,float r){GradientDrawable g=new GradientDrawable();g.setColor(color);g.setCornerRadius(r);return g;}
    GradientDrawable stroke(int fill,int line,float r){GradientDrawable g=shape(fill,r);g.setStroke(1,line);return g;}
    void animateView(View v,int delay){v.setAlpha(0f);v.setTranslationY(14f);v.animate().alpha(1f).translationY(0).setStartDelay(delay).setDuration(320).setInterpolator(new android.view.animation.DecelerateInterpolator()).start();}
    void press(View v){v.animate().scaleX(.97f).scaleY(.97f).setDuration(70).withEndAction(()->v.animate().scaleX(1f).scaleY(1f).setDuration(110).start()).start();}

    void splash(){
        getWindow().setStatusBarColor(NAVY);
        getWindow().setNavigationBarColor(NAVY);
        getWindow().getDecorView().setSystemUiVisibility(0);

        LinearLayout l=new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        l.setGravity(Gravity.CENTER);
        l.setPadding(28,20,28,20);
        l.setBackground(gradient(Color.rgb(4,16,38),NAVY2,0));

        FrameLayout logoWrap=new FrameLayout(this);
        logoWrap.setPadding(18,18,18,18);
        logoWrap.setBackground(shape(Color.rgb(16,44,86),44));

        ImageView glow=new ImageView(this);
        glow.setImageResource(R.drawable.warrior_icon);
        glow.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        glow.setAlpha(.12f);
        logoWrap.addView(glow,new FrameLayout.LayoutParams(-1,-1));

        ImageView logo=new ImageView(this);
        logo.setImageResource(R.drawable.warrior_icon);
        logo.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        logoWrap.addView(logo,new FrameLayout.LayoutParams(-1,-1));

        l.addView(logoWrap,new LinearLayout.LayoutParams(190,190));

        TextView a=text("WARRIOR ASPIRANTS",27,WHITE,true);
        TextView q=text("KNOWLEDGE FOR VICTORY",10,Color.rgb(196,211,233),true);
        TextView line=text("Build the Empire.  •  The Queen Will Come.",14,Color.rgb(220,228,240),true);
        a.setGravity(Gravity.CENTER); q.setGravity(Gravity.CENTER); line.setGravity(Gravity.CENTER);
        a.setPadding(0,20,0,0);
        q.setPadding(0,3,0,0);
        line.setPadding(0,14,0,0);
        l.addView(a); l.addView(q); l.addView(line);
        setContentView(l);

        logoWrap.setAlpha(0f); logoWrap.setScaleX(.55f); logoWrap.setScaleY(.55f); logoWrap.setRotation(-8f);
        glow.setAlpha(0f); logo.setAlpha(0f);
        a.setAlpha(0f); q.setAlpha(0f); line.setAlpha(0f);

        logoWrap.animate().alpha(1f).scaleX(1f).scaleY(1f).rotation(0f)
            .setDuration(700).setInterpolator(new android.view.animation.OvershootInterpolator(1.15f)).start();
        glow.animate().alpha(.18f).setStartDelay(260).setDuration(450).start();
        logo.animate().alpha(1f).setStartDelay(180).setDuration(500).start();
        a.animate().alpha(1f).setStartDelay(430).setDuration(450).start();
        q.animate().alpha(1f).setStartDelay(620).setDuration(420).start();
        line.animate().alpha(1f).setStartDelay(780).setDuration(420).start();

        new Handler().postDelayed(this::home,2300);
    }

    TextView title(String s,float size){TextView t=text(s,size,DARK,true);t.setPadding(0,0,0,0);return t;}
    TextView muted(String s,float size){return text(s,size,MUTED,false);}

    void applyThemeBars(){ getWindow().setStatusBarColor(darkMode?NAVY:WHITE); getWindow().setNavigationBarColor(darkMode?NAVY:WHITE); getWindow().getDecorView().setSystemUiVisibility(darkMode?0:View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR); }
    int themeBg(){return darkMode?Color.rgb(13,19,30):BG;}
    int themeCard(){return darkMode?Color.rgb(25,33,48):WHITE;}
    int themeText(){return darkMode?Color.rgb(238,242,248):DARK;}
    int themeMuted(){return darkMode?Color.rgb(165,176,194):MUTED;}
    void home(){
        applyThemeBars();
        root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setBackgroundColor(themeBg());
        body=new LinearLayout(this);body.setOrientation(LinearLayout.VERTICAL);body.setPadding(18,8,18,124);body.setBackgroundColor(themeBg());
        ScrollView sc=new ScrollView(this);sc.setBackgroundColor(themeBg());sc.setOverScrollMode(View.OVER_SCROLL_NEVER);sc.addView(body);
        FrameLayout frame=new FrameLayout(this);
        frame.addView(sc,new FrameLayout.LayoutParams(-1,-1));
        View nav=bottomNav();
        FrameLayout.LayoutParams np=new FrameLayout.LayoutParams(-1,92,Gravity.BOTTOM);
        np.setMargins(18,0,18,14);
        frame.addView(nav,np);
        root.addView(frame,new LinearLayout.LayoutParams(-1,0,1));
        setContentView(root); page("Home");
    }

    View header(){
        LinearLayout h=new LinearLayout(this);h.setOrientation(LinearLayout.HORIZONTAL);h.setGravity(Gravity.CENTER_VERTICAL);h.setPadding(2,8,2,10);
        ImageView im=new ImageView(this);im.setImageResource(R.drawable.warrior_icon);im.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        h.addView(im,new LinearLayout.LayoutParams(52,52));
        LinearLayout names=new LinearLayout(this);names.setOrientation(LinearLayout.VERTICAL);names.setPadding(10,0,0,0);
        names.addView(text("WARRIOR ASPIRANTS",17,NAVY,true));names.addView(text("KNOWLEDGE FOR VICTORY",9,MUTED,false));
        h.addView(names,new LinearLayout.LayoutParams(0,55,1));
        TextView bell=text("♧",27,NAVY,false);bell.setGravity(Gravity.CENTER);bell.setBackground(shape(WHITE,28));h.addView(bell,new LinearLayout.LayoutParams(46,46));
        return h;
    }

    View searchBox(){
        LinearLayout s=new LinearLayout(this);s.setGravity(Gravity.CENTER_VERTICAL);s.setPadding(13,0,10,0);s.setBackground(stroke(WHITE,Color.rgb(225,229,236),15));
        TextView q=text("⌕",25,MUTED,false);s.addView(q,new LinearLayout.LayoutParams(35,48));
        TextView hint=muted("Search lessons, PYQs, topics...",14);s.addView(hint,new LinearLayout.LayoutParams(0,48,1));
        TextView filter=text("☷",20,RED,true);filter.setGravity(Gravity.CENTER);filter.setBackground(shape(Color.rgb(255,240,241),12));s.addView(filter,new LinearLayout.LayoutParams(42,38));
        LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,50);p.setMargins(0,2,0,10);s.setLayoutParams(p);return s;
    }

    TextView chip(String s,boolean selected){
        TextView c=text(s,12,selected?WHITE:NAVY,true);c.setGravity(Gravity.CENTER);c.setPadding(14,0,14,0);
        c.setBackground(shape(selected?NAVY:WHITE,22));c.setElevation(selected?2:1);return c;
    }

    View examChips(){
        LinearLayout row=new LinearLayout(this);row.setGravity(Gravity.CENTER_VERTICAL);
        String[] a={"🔴 SSC CGL","🚆 Railway","🟢 UP SI"};
        for(int i=0;i<a.length;i++){TextView c=chip(a[i],i==0);LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-2,38);p.setMargins(0,0,7,0);row.addView(c,p);}
        return row;
    }

    String greeting(){
        Calendar c=Calendar.getInstance();
        int h=c.get(Calendar.HOUR_OF_DAY);
        if(h>=5 && h<12) return "Good morning, Warrior.";
        if(h>=12 && h<17) return "Good afternoon, Warrior.";
        return "Good evening, Warrior.";
    }
    GradientDrawable gradient(int start,int end,float r){
        GradientDrawable g=new GradientDrawable(GradientDrawable.Orientation.TL_BR,new int[]{start,end});
        g.setCornerRadius(r); return g;
    }
    TextView iconBadge(String label,int bg,int fg){
        TextView v=text(label,12,fg,true); v.setGravity(Gravity.CENTER); v.setBackground(shape(bg,18)); return v;
    }

    void homeContent(){
        body.setPadding(20,10,20,30);

        // Premium header
        LinearLayout top=new LinearLayout(this); top.setGravity(Gravity.CENTER_VERTICAL);
        ImageView logo=new ImageView(this); logo.setImageResource(R.drawable.warrior_icon); logo.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        top.addView(logo,new LinearLayout.LayoutParams(50,50));
        LinearLayout brand=new LinearLayout(this); brand.setOrientation(LinearLayout.VERTICAL); brand.setPadding(11,0,0,0);
        brand.addView(text("WARRIOR ASPIRANTS",16,NAVY,true));
        brand.addView(text("KNOWLEDGE FOR VICTORY",8,MUTED,true));
        top.addView(brand,new LinearLayout.LayoutParams(0,52,1));
        TextView search=text("⌕",23,NAVY,true); search.setGravity(Gravity.CENTER); search.setBackground(stroke(WHITE,Color.rgb(226,230,237),18));
        top.addView(search,new LinearLayout.LayoutParams(44,44));
        LinearLayout.LayoutParams tp=new LinearLayout.LayoutParams(-1,54);tp.setMargins(0,0,0,5);body.addView(top,tp);

        TextView hello=text(greeting(),27,DARK,true); hello.setPadding(0,12,0,0); body.addView(hello);
        body.addView(muted("One focused session today. One step closer.",12));

        // Signature hero
        LinearLayout hero=new LinearLayout(this); hero.setOrientation(LinearLayout.HORIZONTAL);
        hero.setPadding(20,20,17,20); hero.setBackground(gradient(Color.rgb(6,22,50),Color.rgb(20,54,100),26)); hero.setElevation(8);
        LinearLayout hc=new LinearLayout(this);hc.setOrientation(LinearLayout.VERTICAL);
        TextView eyebrow=text("TODAY'S MISSION",10,Color.rgb(176,198,229),true);hc.addView(eyebrow);
        hc.addView(text("Master the next topic.",23,WHITE,true));
        hc.addView(text("2h 30m planned  •  13 tasks",11,Color.rgb(208,222,241),false));
        LinearLayout mini=new LinearLayout(this);mini.setGravity(Gravity.CENTER_VERTICAL);mini.setPadding(0,13,0,0);
        TextView live=text("●  ON TRACK",10,Color.rgb(255,205,208),true);live.setGravity(Gravity.CENTER);live.setBackground(shape(Color.rgb(112,34,48),14));
        mini.addView(live,new LinearLayout.LayoutParams(105,29));
        TextView resume=text("  CONTINUE  ›",11,WHITE,true);resume.setGravity(Gravity.CENTER);resume.setOnClickListener(v->lesson("Number System"));mini.addView(resume,new LinearLayout.LayoutParams(0,36,1));hc.addView(mini);
        hero.addView(hc,new LinearLayout.LayoutParams(0,125,1));
        RingView ring=new RingView(this,store.prep()); hero.addView(ring,new LinearLayout.LayoutParams(84,84));
        LinearLayout.LayoutParams hp=new LinearLayout.LayoutParams(-1,165);hp.setMargins(0,18,0,18);body.addView(hero,hp);animateView(hero,40);

        // KPI strip
        LinearLayout kpi=new LinearLayout(this);kpi.setWeightSum(3);
        premiumKpi(kpi,"PREPARATION",store.prep()+"%","of target");
        premiumKpi(kpi,"ACCURACY",store.accuracy()+"%","all attempts");
        premiumKpi(kpi,"WARRIOR XP",String.valueOf(store.xp()),"level "+store.level());
        body.addView(kpi);

        section("YOUR EXAM");
        LinearLayout exams=new LinearLayout(this);exams.setWeightSum(3);
        premiumExam(exams,"SSC CGL","2027 • PRIMARY",true,v->topics("Maths"));
        premiumExam(exams,"RAILWAY","PRACTICE",false,v->topics("Reasoning"));
        premiumExam(exams,"UP SI","PRACTICE",false,v->page("Study"));
        body.addView(exams);

        section("QUICK ACTIONS");
        LinearLayout quick=new LinearLayout(this); quick.setWeightSum(4);
        premiumExam(quick,"LESSONS","THEORY",false,v->page("Study"));
        premiumExam(quick,"PYQs","TOPIC-WISE",false,v->pyq());
        premiumExam(quick,"MOCKS","FULL TEST",false,v->mock());
        premiumExam(quick,"REVISION","MISTAKES",false,v->mistakes());
        body.addView(quick);

        section("LEARNING HUB");
        premiumModule(body,"01","STUDY ROOM","Concepts, formulas & guided lessons",v->page("Study"));
        premiumModule(body,"02","PRACTICE ARENA","Speed, accuracy & smart practice",v->practice());
        premiumModule(body,"03","PYQ BANK","Topic-wise previous year questions",v->pyq());
        premiumModule(body,"04","MOCK TESTS","Full tests with performance analysis",v->mock());

        LinearLayout ai=new LinearLayout(this);ai.setGravity(Gravity.CENTER_VERTICAL);ai.setPadding(18,15,15,15);
        ai.setBackground(gradient(Color.rgb(31,39,61),Color.rgb(8,24,53),22));ai.setElevation(5);
        TextView aiMark=text("F",20,WHITE,true);aiMark.setGravity(Gravity.CENTER);aiMark.setBackground(shape(RED,17));ai.addView(aiMark,new LinearLayout.LayoutParams(42,42));
        LinearLayout ac=new LinearLayout(this);ac.setOrientation(LinearLayout.VERTICAL);ac.setPadding(13,0,8,0);
        ac.addView(text("FIGHTER AI",10,Color.rgb(188,207,233),true));ac.addView(text("Your personal study partner",16,WHITE,true));ac.addView(text("Explain  •  Solve  •  Quiz  •  Revise",9,Color.rgb(176,196,223),false));
        ai.addView(ac,new LinearLayout.LayoutParams(0,60,1));TextView go=text("OPEN",10,WHITE,true);go.setGravity(Gravity.CENTER);go.setBackground(shape(RED,15));go.setOnClickListener(v->fighter());ai.addView(go,new LinearLayout.LayoutParams(60,34));
        LinearLayout.LayoutParams aip=new LinearLayout.LayoutParams(-1,76);aip.setMargins(0,18,0,8);body.addView(ai,aip);

        section("SMART REVISION");
        premiumModule(body,"05","CURRENT AFFAIRS","Daily • Weekly • Monthly updates",v->page("Current Affairs"));
        premiumModule(body,"06","MISTAKE BOOK",mistakes.size()+" saved questions to revise",v->mistakes());

        TextView loop=text("LEARN   →   PRACTICE   →   ANALYZE   →   IMPROVE",9,MUTED,true);loop.setGravity(Gravity.CENTER);loop.setPadding(0,20,0,8);body.addView(loop);
    }

    class RingView extends View{
        android.graphics.Paint p=new android.graphics.Paint(1); int value;
        RingView(android.content.Context c,int v){super(c);value=Math.max(0,Math.min(100,v));p.setStrokeWidth(7);p.setStyle(android.graphics.Paint.Style.STROKE);p.setStrokeCap(android.graphics.Paint.Cap.ROUND);}
        protected void onDraw(android.graphics.Canvas canvas){
            super.onDraw(canvas);float cx=getWidth()/2f,cy=getHeight()/2f,r=Math.min(cx,cy)-8;
            p.setColor(Color.rgb(70,91,124));canvas.drawCircle(cx,cy,r,p);
            p.setColor(RED);canvas.drawArc(cx-r,cy-r,cx+r,cy+r,-90,3.6f*value,false,p);
            p.setStyle(android.graphics.Paint.Style.FILL);p.setColor(WHITE);p.setTextAlign(android.graphics.Paint.Align.CENTER);p.setTypeface(Typeface.DEFAULT_BOLD);p.setTextSize(21);canvas.drawText(value+"%",cx,cy+7,p);
            p.setTextSize(8);p.setTypeface(Typeface.DEFAULT);canvas.drawText("READY",cx,cy+21,p);p.setStyle(android.graphics.Paint.Style.STROKE);
        }
    }

    void premiumKpi(LinearLayout row,String label,String value,String sub){
        LinearLayout c=new LinearLayout(this);c.setOrientation(LinearLayout.VERTICAL);c.setPadding(13,12,8,11);c.setBackground(stroke(WHITE,Color.rgb(226,230,237),18));c.setElevation(2);
        c.addView(text(value,20,NAVY,true));c.addView(text(label,8,RED,true));c.addView(text(sub,8,MUTED,false));
        LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(0,86,1);p.setMargins(3,0,3,0);row.addView(c,p);
    }
    void premiumModule(LinearLayout parent,String no,String name,String desc,View.OnClickListener l){
        LinearLayout c=new LinearLayout(this);c.setGravity(Gravity.CENTER_VERTICAL);c.setPadding(12,10,12,10);c.setBackground(stroke(WHITE,Color.rgb(226,230,237),20));c.setElevation(2);c.setOnClickListener(l);
        TextView n=text(no,10,NAVY,true);n.setGravity(Gravity.CENTER);n.setBackground(shape(Color.rgb(239,243,248),14));c.addView(n,new LinearLayout.LayoutParams(38,38));
        LinearLayout cp=new LinearLayout(this);cp.setOrientation(LinearLayout.VERTICAL);cp.setPadding(12,0,8,0);cp.addView(text(name,14,DARK,true));cp.addView(muted(desc,9));c.addView(cp,new LinearLayout.LayoutParams(0,58,1));
        TextView ar=text("→",20,RED,true);ar.setGravity(Gravity.CENTER);c.addView(ar,new LinearLayout.LayoutParams(34,48));
        LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,70);p.setMargins(0,5,0,5);parent.addView(c,p);animateView(c,parent.getChildCount()*18);
    }

    void premiumStat(LinearLayout row,String label,String value,String sub){
        LinearLayout c=new LinearLayout(this);c.setOrientation(LinearLayout.VERTICAL);c.setPadding(13,11,13,10);c.setBackground(stroke(WHITE,Color.rgb(225,229,236),18));c.setElevation(1);
        c.addView(text(value,19,NAVY,true));c.addView(text(label,8,RED,true));c.addView(text(sub,8,MUTED,false));
        LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(0,82,1);p.setMargins(3,0,3,0);row.addView(c,p);
    }
    void premiumTile(LinearLayout parent,String name,String desc,String tag,View.OnClickListener l){
        LinearLayout c=new LinearLayout(this);c.setGravity(Gravity.CENTER_VERTICAL);c.setPadding(15,10,12,10);c.setBackground(stroke(WHITE,Color.rgb(225,229,236),19));c.setElevation(1);c.setOnClickListener(l);
        TextView mark=iconBadge(tag.substring(0,1),Color.rgb(238,242,248),NAVY);c.addView(mark,new LinearLayout.LayoutParams(38,38));
        LinearLayout cp=new LinearLayout(this);cp.setOrientation(LinearLayout.VERTICAL);cp.setPadding(12,0,0,0);cp.addView(title(name,15));cp.addView(muted(desc,9));c.addView(cp,new LinearLayout.LayoutParams(0,58,1));
        TextView ar=text("›",24,RED,false);ar.setGravity(Gravity.CENTER);c.addView(ar,new LinearLayout.LayoutParams(28,50));
        LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,72);p.setMargins(0,5,0,5);parent.addView(c,p);animateView(c,parent.getChildCount()*18);
    }

    void premiumExam(LinearLayout row,String name,String sub,boolean active,View.OnClickListener l){
        LinearLayout c=new LinearLayout(this); c.setOrientation(LinearLayout.VERTICAL); c.setPadding(14,12,14,11); c.setBackground(active?shape(NAVY,17):stroke(WHITE,Color.rgb(225,229,236),17)); c.setOnClickListener(l);
        c.addView(text(name,14,active?WHITE:DARK,true)); c.addView(text(sub,10,active?Color.rgb(190,205,228):MUTED,false));
        LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(0,72,1); p.setMargins(3,0,3,0); row.addView(c,p);
    }
    void premiumAction(LinearLayout parent,String name,String desc,String tag,View.OnClickListener l){
        LinearLayout c=new LinearLayout(this); c.setGravity(Gravity.CENTER_VERTICAL); c.setPadding(15,10,10,10); c.setBackground(stroke(WHITE,Color.rgb(225,229,236),17)); c.setOnClickListener(l);
        LinearLayout copy=new LinearLayout(this); copy.setOrientation(LinearLayout.VERTICAL); copy.addView(title(name,15)); copy.addView(muted(desc,10)); c.addView(copy,new LinearLayout.LayoutParams(0,58,1));
        TextView t=text(tag,9,RED,true); t.setGravity(Gravity.CENTER); t.setBackground(shape(Color.rgb(255,241,242),14)); c.addView(t,new LinearLayout.LayoutParams(58,30));
        LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,72); p.setMargins(0,4,0,4); parent.addView(c,p); animateView(c,parent.getChildCount()*25);
    }
    void statCard(LinearLayout row,String label,String value){
        LinearLayout c=new LinearLayout(this); c.setOrientation(LinearLayout.VERTICAL); c.setGravity(Gravity.CENTER); c.setPadding(4,10,4,10); c.setBackground(WHITE==0?shape(WHITE,16):stroke(WHITE,Color.rgb(225,229,236),16));
        c.addView(text(value,20,NAVY,true)); c.addView(text(label,8,MUTED,true)); LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(0,78,1);p.setMargins(3,0,3,0);row.addView(c,p);
    }

    void gridRow(LinearLayout parent,String[] labels,View.OnClickListener[] ls){
        LinearLayout r=new LinearLayout(this);r.setWeightSum(3);
        for(int i=0;i<3;i++){LinearLayout c=new LinearLayout(this);c.setOrientation(LinearLayout.VERTICAL);c.setGravity(Gravity.CENTER);c.setPadding(4,8,4,8);c.setBackground(stroke(WHITE,Color.rgb(225,229,236),16));TextView ic=text(labels[i].split("\n")[0],20,RED,true);ic.setGravity(Gravity.CENTER);c.addView(ic);String[] parts=labels[i].split("\n");StringBuilder z=new StringBuilder();for(int k=1;k<parts.length;k++){if(k>1)z.append("\n");z.append(parts[k]);}TextView n=text(z.toString(),12,DARK,true);n.setGravity(Gravity.CENTER);c.addView(n);c.setOnClickListener(ls[i]);c.setOnTouchListener((v,e)->{if(e.getAction()==MotionEvent.ACTION_DOWN)press(v);return false;});LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(0,86,1);p.setMargins(3,3,3,3);r.addView(c,p);animateView(c,70+i*40);}parent.addView(r);
    }

    void newsCard(String s){LinearLayout c=new LinearLayout(this);c.setOrientation(LinearLayout.VERTICAL);c.setPadding(12,10,12,10);c.setBackground(stroke(WHITE,Color.rgb(225,229,236),15));c.addView(text(s,13,DARK,true));c.addView(muted("Today  •  1 min read",9));LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(0,3,0,5);body.addView(c,p);}
    void recCard(LinearLayout row,String s,View.OnClickListener l){LinearLayout c=new LinearLayout(this);c.setOrientation(LinearLayout.VERTICAL);c.setGravity(Gravity.CENTER);c.setPadding(8,8,8,8);c.setBackground(stroke(WHITE,Color.rgb(225,229,236),15));c.addView(text("📚",22,RED,true));TextView n=text(s,11,DARK,true);n.setGravity(Gravity.CENTER);c.addView(n);c.setOnClickListener(l);LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(0,100,1);p.setMargins(3,0,3,0);row.addView(c,p);}
    View floatingFighter(){Button b=new Button(this);b.setText("🤖  Fighter AI");b.setTextColor(WHITE);b.setTextSize(13);b.setAllCaps(false);b.setBackground(shape(NAVY,25));b.setElevation(6);b.setOnClickListener(v->fighter());return b;}

    View bottomNav(){
        LinearLayout nav=new LinearLayout(this);
        nav.setOrientation(LinearLayout.HORIZONTAL);
        nav.setGravity(Gravity.CENTER);
        nav.setPadding(7,6,7,6);
        nav.setBackground(getDrawable(R.drawable.bg_floating_bottom_nav));
        nav.setElevation(22);

        String[] icons={"ic_nav_home","ic_nav_study","ic_nav_practice","ic_nav_current","ic_nav_profile"};
        String[] labels={"Home","Study","Practice","Current","Profile"};
        String[] pages={"Home","Study","Practice","Current Affairs","Profile"};

        for(int i=0;i<labels.length;i++){
            final String pg=pages[i];
            final int index=i;
            LinearLayout item=new LinearLayout(this);
            item.setOrientation(LinearLayout.VERTICAL);
            item.setGravity(Gravity.CENTER);
            item.setPadding(2,2,2,2);

            LinearLayout active=new LinearLayout(this);
            active.setOrientation(LinearLayout.VERTICAL);
            active.setGravity(Gravity.CENTER);
            active.setPadding(8,4,8,4);
            active.setBackground(shape(index==0?Color.rgb(38,48,73):Color.TRANSPARENT,18));

            ImageView ic=new ImageView(this);
            int iconId=getResources().getIdentifier(icons[i],"drawable",getPackageName());
            ic.setImageResource(iconId);
            ic.setColorFilter(index==0?RED:Color.rgb(136,146,176));
            ic.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            TextView tx=text(labels[i],12.5f,index==0?WHITE:Color.rgb(136,146,176),true);
            tx.setGravity(Gravity.CENTER);

            active.addView(ic,new LinearLayout.LayoutParams(30,30));
            active.addView(tx,new LinearLayout.LayoutParams(-1,22));
            item.addView(active,new LinearLayout.LayoutParams(-1,66));

            item.setOnClickListener(v->{
                for(int k=0;k<nav.getChildCount();k++){
                    View child=nav.getChildAt(k);
                    if(child instanceof LinearLayout){
                        LinearLayout box=(LinearLayout)((LinearLayout)child).getChildAt(0);
                        box.setBackground(shape(Color.TRANSPARENT,18));
                        ImageView ii=(ImageView)box.getChildAt(0);
                        TextView tt=(TextView)box.getChildAt(1);
                        ii.setColorFilter(Color.rgb(136,146,176));
                        tt.setTextColor(Color.rgb(136,146,176));
                    }
                }
                active.setBackground(shape(Color.rgb(38,48,73),18));
                ic.setColorFilter(RED);
                tx.setTextColor(WHITE);
                active.animate().scaleX(.94f).scaleY(.94f).setDuration(70)
                    .withEndAction(()->active.animate().scaleX(1f).scaleY(1f).setDuration(140).start()).start();
                page(pg);
            });
            item.setOnTouchListener((v,e)->{
                if(e.getAction()==MotionEvent.ACTION_DOWN) press(v);
                return false;
            });
            LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(0,70,1);
            p.setMargins(1,0,1,0);
            nav.addView(item,p);
        }
        return nav;
    }
    void page(String p){
        body.removeAllViews(); if(p.equals("Home")){homeContent();return;}
        topTitle(p);
        if(p.equals("Study")){
            section("EXAM & SUBJECTS");
            studyCard("📐","Maths","95%","Completed Topics",v->topics("Maths"));
            studyCard("🧠","Reasoning","60%","Completed Topics",v->topics("Reasoning"));
            studyCard("🔤","English","53%","Completed Topics",v->topics("English"));
            studyCard("🌍","GK / GS","56%","Completed Topics",v->topics("GK / GS"));
            studyCard("◉","Current Affairs","Today","Daily Updates",v->page("Current Affairs"));
            studyCard("🟢","UPSSSC PET","100 Questions","Complete 2026 Syllabus",v->petSyllabus());action("▶ Videos & YouTube Learning",v->openUrl("https://www.youtube.com/results?search_query=SSC+CGL+Maths+Reasoning+English"));action("📢 Vacancies & Sarkari Updates",v->vacancies());action("📄 PDF / Notes",v->pickPdf());
        } else if(p.equals("Practice")){
            hero("PRACTICE ARENA","Train your speed & accuracy","10 / 20 / 50 questions • Timer • Analysis",v->practice());
            action("⚡  Quick Practice — 10 Questions",v->practice());action("🎯  Topic Practice",v->topics("Practice Topics"));action("📚  PYQ Engine",v->pyq());action("🏆  Mock Test",v->mock());action("📕  Mistake Book",v->mistakes());
        } else if(p.equals("Current Affairs")){
            section("CURRENT AFFAIRS");action("📅 Today — Live",v->openUrl("https://news.google.com/rss/search?q=India%20current%20affairs%20when:1d"));action("Yesterday — Live",v->openUrl("https://news.google.com/rss/search?q=India%20current%20affairs%20when:2d"));action("📚 Weekly",v->openUrl("https://news.google.com/rss/search?q=India%20current%20affairs%20when:7d"));action("🗓 Monthly",v->openUrl("https://www.google.com/search?q=India+monthly+current+affairs"));action("❓ Current Affairs Quiz",v->practiceTopic("Current Affairs"));
        } else {
            profileHeader();action("📊 Progress",v->progress());action("🕘 History",v->history());action("🔖 Bookmarks",v->bookmarks());action("📤 Share App",v->shareApp());action("📄 Upload Study PDF",v->pickPdf());action("⚙️ Settings",v->settings());action("ℹ️ About Warrior Aspirants",v->about());
        }
    }

    void profileHeader(){LinearLayout c=new LinearLayout(this);c.setOrientation(LinearLayout.VERTICAL);c.setGravity(Gravity.CENTER);c.setPadding(12,14,12,16);c.setBackground(shape(NAVY,20));TextView av=text("👤",40,WHITE,true);av.setGravity(Gravity.CENTER);c.addView(av);c.addView(text("Warrior Profile",18,WHITE,true));c.addView(text("Level "+store.level()+"  •  "+store.xp()+" XP",12,Color.rgb(210,220,238),false));LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(0,0,0,12);body.addView(c,p);}
    void studyCard(String ic,String name,String value,String sub,View.OnClickListener l){
        LinearLayout c=new LinearLayout(this); c.setOrientation(LinearLayout.VERTICAL); c.setPadding(16,14,16,14);
        c.setBackground(stroke(WHITE,Color.rgb(225,229,236),20)); c.setElevation(2); c.setOnClickListener(l);
        LinearLayout top=new LinearLayout(this); top.setGravity(Gravity.CENTER_VERTICAL);
        TextView i=text(ic,12,NAVY,true); i.setGravity(Gravity.CENTER); i.setBackground(shape(Color.rgb(239,243,249),15)); top.addView(i,new LinearLayout.LayoutParams(38,38));
        LinearLayout cp=new LinearLayout(this); cp.setOrientation(LinearLayout.VERTICAL); cp.setPadding(12,0,0,0); cp.addView(title(name,16)); cp.addView(muted(sub,10)); top.addView(cp,new LinearLayout.LayoutParams(0,50,1));
        TextView pct=text(value,13,RED,true); pct.setGravity(Gravity.CENTER); top.addView(pct,new LinearLayout.LayoutParams(52,32)); c.addView(top);
        ProgressBar pb=new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal); int pr=value.equals("Today")?65:Integer.parseInt(value.replace("%","")); pb.setProgress(pr); pb.setProgressDrawable(shape(RED,8)); LinearLayout.LayoutParams pp=new LinearLayout.LayoutParams(-1,7); pp.setMargins(0,13,0,0); c.addView(pb,pp);
        LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,104); p.setMargins(0,5,0,5); body.addView(c,p); animateView(c,body.getChildCount()*25);
    }

    void topTitle(String p){LinearLayout bar=new LinearLayout(this);bar.setGravity(Gravity.CENTER_VERTICAL);bar.setPadding(2,4,2,14);TextView back=text("‹",32,NAVY,false);back.setGravity(Gravity.CENTER);back.setOnClickListener(v->page("Home"));bar.addView(back,new LinearLayout.LayoutParams(38,48));TextView t=title(p,22);bar.addView(t,new LinearLayout.LayoutParams(0,48,1));TextView xp=text("⚔ "+store.xp(),11,RED,true);xp.setGravity(Gravity.CENTER);xp.setBackground(shape(Color.rgb(255,239,240),18));bar.addView(xp,new LinearLayout.LayoutParams(70,36));body.addView(bar);}

    void section(String s){TextView t=text(s,11,RED,true);t.setPadding(3,13,3,7);body.addView(t);}
    View action(String s,View.OnClickListener l){
        String clean=s.replaceAll("^[^A-Za-z0-9]+","").replace("  "," ");
        LinearLayout row=new LinearLayout(this); row.setGravity(Gravity.CENTER_VERTICAL); row.setPadding(14,8,12,8);
        row.setBackground(stroke(WHITE,Color.rgb(225,229,236),18)); row.setElevation(1); row.setOnClickListener(l);
        TextView mark=text(clean.length()>0?clean.substring(0,1).toUpperCase():"•",13,WHITE,true); mark.setGravity(Gravity.CENTER); mark.setBackground(shape(NAVY,13));
        row.addView(mark,new LinearLayout.LayoutParams(38,38));
        TextView label=text(clean,14,DARK,true); label.setPadding(12,0,0,0); row.addView(label,new LinearLayout.LayoutParams(0,54,1));
        TextView arrow=text("›",25,RED,false); arrow.setGravity(Gravity.CENTER); row.addView(arrow,new LinearLayout.LayoutParams(28,50));
        LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,68); p.setMargins(0,5,0,5); body.addView(row,p); animateView(row,body.getChildCount()*16); return row;
    }
    void card(String h,String sub,String val){LinearLayout c=new LinearLayout(this);c.setOrientation(LinearLayout.VERTICAL);c.setPadding(14,12,14,13);c.setBackground(stroke(WHITE,Color.rgb(225,229,236),18));c.addView(text(h,10,RED,true));c.addView(title(sub,16));c.addView(muted(val,11));LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(0,3,0,9);body.addView(c,p);animateView(c,40);}
    void hero(String h,String t,String sub,View.OnClickListener l){LinearLayout c=new LinearLayout(this);c.setOrientation(LinearLayout.VERTICAL);c.setPadding(16,15,16,15);c.setBackground(shape(NAVY,20));c.setElevation(4);c.addView(text(h,10,Color.rgb(190,204,228),true));c.addView(text(t,20,WHITE,true));c.addView(text(sub,11,Color.rgb(218,225,238),false));Button go=new Button(this);go.setText("START  →");go.setTextColor(WHITE);go.setAllCaps(false);go.setBackground(shape(RED,17));go.setOnClickListener(l);c.addView(go,new LinearLayout.LayoutParams(-1,45));LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(0,2,0,10);body.addView(c,p);animateView(c,40);}

    void petSyllabus(){body.removeAllViews();topTitle("UPSSSC PET Syllabus");card("UPSSSC PET 2026","100 Questions • 100 Marks • 120 Minutes","Negative marking: 0.25 per wrong answer • NCERT-based standard");
        section("COMPLETE SYLLABUS • 15 SECTIONS");
        String[][] d=PETContent.sections(); for(int i=0;i<d.length;i++){final int ix=i; topic(String.format("%02d",i+1),d[i][0],d[i][1],v->petDetail(ix));} action("←  Back to Study",v->page("Study"));}
    void petDetail(int i){body.removeAllViews();String[][] d=PETContent.sections();topTitle("PET • "+d[i][0]);card("SYLLABUS","Section "+(i+1)+" • "+d[i][0],d[i][2]);body.addView(text("COMPLETE SYLLABUS TOPICS",11,RED,true));body.addView(text(d[i][3],14,DARK,false));body.addView(text("THEORY",11,RED,true));card("CONCEPTS","Easy Hindi Theory","पहले concept समझें, फिर questions करें.");body.addView(text(PETContent.theory(i),14,DARK,false));body.addView(text("⭐ IMPORTANT TOPICS",11,RED,true));body.addView(text(PETContent.important(i),14,DARK,false));action("🎯  Practice this section",v->practiceTopic("UPSSSC PET • "+d[i][0]));action("📚  PET PYQs",v->pyqTopic("UPSSSC PET • "+d[i][0]));action("🧠  Revise Theory",v->toast("Theory revision marked for this PET section."));action("←  Back to PET Syllabus",v->petSyllabus());}

    void topics(String s){
        body.removeAllViews();topTitle(s+" Topics");
        if(s.equals("Maths")){topic("01","Number System","Basics, divisibility, HCF / LCM",v->lesson("Number System"));topic("02","Simplification","BODMAS and calculations",v->lesson("Percentage"));topic("03","HCF & LCM","Factors and multiples",v->lesson("Number System"));topic("04","Percentage","Increase, decrease, successive",v->lesson("Percentage"));topic("05","Ratio & Proportion","Applications",v->lesson("Ratio & Proportion"));topic("06","Average","Mean and weighted average",v->lesson("Average"));topic("07","Profit & Loss","CP, SP and discount",v->lesson("Profit & Loss"));topic("08","Time & Work","Efficiency and work",v->lesson("Time & Work"));topic("09","TSD","Speed, distance and trains",v->lesson("Time, Speed & Distance"));topic("10","Algebra","Identities and equations",v->lesson("Algebra"));topic("11","Geometry","Lines, angles, triangles",v->lesson("Geometry & Mensuration"));topic("12","Trigonometry","Ratios and standard values",v->lesson("Trigonometry"));}
        else if(s.equals("Reasoning")){topic("01","Analogy","Same relationship",v->lesson("Analogy"));topic("02","Classification","Odd one out",v->lesson("Classification"));topic("03","Series","Number and alphabet series",v->lesson("Series"));topic("04","Coding-Decoding","Patterns",v->lesson("Coding-Decoding"));topic("05","Blood Relation","Family relations",v->lesson("Blood Relation"));topic("06","Direction","Distance and paths",v->lesson("Direction & Distance"));topic("07","Syllogism","Statements and conclusions",v->lesson("Syllogism"));topic("08","Venn Diagram","Set relationships",v->lesson("Venn Diagram"));}
        else if(s.equals("English")){topic("01","Parts of Speech","Grammar basics",v->lesson("Parts of Speech"));topic("02","Tenses","Present, past, future",v->lesson("Tenses"));topic("03","Subject-Verb Agreement","Rules",v->lesson("Subject-Verb Agreement"));topic("04","Articles","A, An, The",v->lesson("Articles"));topic("05","Prepositions","Usage",v->lesson("Prepositions"));topic("06","Voice","Active & Passive",v->lesson("Active & Passive Voice"));topic("07","Narration","Direct & indirect",v->lesson("Narration"));topic("08","Error Detection","Common grammar errors",v->lesson("Error Detection"));}
        else {topic("01","History","Ancient, Medieval, Modern",v->lesson("History"));topic("02","Geography","India and world",v->lesson("Geography"));topic("03","Polity","Constitution",v->lesson("Polity"));topic("04","Economy","Basic concepts",v->lesson("Economy"));topic("05","Physics","Motion, energy, electricity",v->lesson("Physics"));topic("06","Chemistry","Matter and reactions",v->lesson("Chemistry"));topic("07","Biology","Cells, body, plants",v->lesson("Biology"));}
        action("←  Back to Study",v->page("Study"));
    }

    void topic(String no,String name,String sub,View.OnClickListener l){LinearLayout c=new LinearLayout(this);c.setGravity(Gravity.CENTER_VERTICAL);c.setPadding(9,5,9,5);c.setBackground(stroke(WHITE,Color.rgb(225,229,236),15));TextView n=text(no,10,RED,true);n.setGravity(Gravity.CENTER);n.setBackground(shape(Color.rgb(255,240,241),14));c.addView(n,new LinearLayout.LayoutParams(42,52));LinearLayout mid=new LinearLayout(this);mid.setOrientation(LinearLayout.VERTICAL);mid.setPadding(12,0,4,0);mid.addView(title(name,15));mid.addView(muted(sub+"\nProgress • Difficulty • Questions",9));c.addView(mid,new LinearLayout.LayoutParams(0,64,1));TextView go=text("›",29,RED,true);go.setGravity(Gravity.CENTER);c.addView(go,new LinearLayout.LayoutParams(38,64));c.setOnClickListener(l);LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,72);p.setMargins(0,4,0,4);body.addView(c,p);animateView(c,body.getChildCount()*15);}

    void lesson(String t){body.removeAllViews();topTitle(t);card("OFFLINE LESSON","Theory • Concepts • Important Points","पूरा basic study material app में offline उपलब्ध है.");body.addView(text("📚 THEORY",11,RED,true));body.addView(text(theory(t),14,DARK,false));body.addView(text("💡 CONCEPT",11,RED,true));body.addView(text("Concept ko pehle samjho, phir formula/rule apply karo. Upar di gayi theory ko step-by-step padho aur example se verify karo.",14,DARK,false));body.addView(text("⭐ IMPORTANT POINTS",11,RED,true));body.addView(text(trick(t),14,DARK,false));body.addView(text("📝 SOLVED EXAMPLE",11,RED,true));body.addView(text(example(t),14,DARK,false));action("🎯  Practice Offline",v->practiceTopic(t));action("📚  PYQs",v->pyqTopic(t));action("🤖  Ask Fighter AI",v->fighter());}
    StudyContent.Lesson material(String t){return StudyContent.get(t);} String theory(String t){return material(t).theory;}String example(String t){return material(t).example;}String trick(String t){return material(t).trick;}

    void practice(){practiceTopic("Mixed Practice");}
    void practiceTopic(String topic){ practiceTopicName=topic; practiceIndex=0; practiceScore=0; showPracticeQuestion(); }
    void showPracticeQuestion(){
        body.removeAllViews(); topTitle("Practice • "+practiceTopicName);
        String[][] q={
            {"Percentage","250 ka 20% kitna hai?","50","25","75","100","50"},
            {"Number System","12 aur 18 ka HCF kya hai?","6","3","9","12","6"},
            {"Ratio","A:B=2:3 aur A=20, B kya hoga?","30","10","25","40","30"},
            {"Average","10,20,30 ka average kya hai?","20","15","20","25","30"},
            {"Profit & Loss","CP ₹800, profit 15%, SP?","₹920","₹900","₹880","₹940","₹920"},
            {"TSD","72 km/h ko m/s me badlo.","20","18","20","24","36"},
            {"Algebra","x+7=19, x=?","12","10","11","12","13"},
            {"Reasoning","2,5,10,17,26, next?","37","35","36","37","39"},
            {"English","Each of the boys ___ ready.","is","are","were","have","is"},
            {"GK/GS","Fundamental Rights Constitution ke kis Part me hain?","Part III","Part I","Part II","Part IV","Part III"}
        };
        String[] a=q[practiceIndex%q.length];
        card("QUESTION "+(practiceIndex+1)+" / 10",a[0],"Choose the correct answer");
        body.addView(text(a[1],18,themeText(),true));
        String[] opts={a[2],a[3],a[4],a[5]};
        for(String opt:opts){ final String chosen=opt, correct=a[6];
            action(chosen,v->{ boolean ok=chosen.equals(correct);
                if(ok){practiceScore++;store.addResult(true);toast("Correct! +10 XP");}
                else{store.addResult(false);store.saveMistake(practiceTopicName+" — "+a[1]);mistakes=store.mistakes();toast("Wrong • Correct: "+correct);}
                practiceIndex++; if(practiceIndex>=10) showPracticeResult(); else showPracticeQuestion();
            });
        }
        body.addView(muted("Instant analysis • Wrong answers automatically saved to Mistake Book",10));
    }
    void showPracticeResult(){
        body.removeAllViews(); topTitle("Practice Result");
        hero("SESSION COMPLETE",practiceScore+" / 10 correct",(practiceScore*10)+"% accuracy • "+practiceTopicName,v->practiceTopic(practiceTopicName));
        card("ANALYSIS","Accuracy "+(practiceScore*10)+"%","Correct: "+practiceScore+" • Wrong: "+(10-practiceScore));
        action("📕 Open Mistake Book",v->mistakes());
        action("🔄 Retry Set",v->practiceTopic(practiceTopicName));
        action("← Back to Practice",v->page("Practice"));
    }
    void pyq(){body.removeAllViews();topTitle("PYQs");card("QUESTION BANK","SSC CGL • Railway • UP SI","Exam • Year • Shift • Topic • Solution");action("SSC CGL → Maths",v->pyqTopic("Maths"));action("SSC CGL → Reasoning",v->pyqTopic("Reasoning"));action("SSC CGL → English",v->pyqTopic("English"));action("SSC CGL → General Awareness",v->pyqTopic("GK / GS"));}
    void pyqTopic(String t){body.removeAllViews();topTitle("PYQ • "+t);card("PREVIOUS YEAR","Verified question bank","Options • Correct answer • Detailed explanation");action("▶  Start Topic PYQ",v->practiceTopic(t));action("←  Back",v->pyq());}
    void mock(){body.removeAllViews();topTitle("Mock Test");hero("FULL MOCK","SSC CGL Tier 1 & 2","Timer • Section analysis • Accuracy • Mistakes",v->practiceTopic("Mock Test"));action("🏆  Start Mock",v->practiceTopic("Mock Test"));action("←  Back",v->page("Practice"));}
    void mistakes(){body.removeAllViews();topTitle("Mistake Book");if(mistakes.size()==0)card("EMPTY","No mistakes saved yet","Wrong questions will appear here.");else for(String m:mistakes)card("REVISE",m,"Retry → understand → improve");action("←  Back",v->page("Practice"));}
    void fighter(){body.removeAllViews();topTitle("Fighter AI");hero("YOUR STUDY PARTNER","Explain • Solve • Quiz • Revise","Ask in simple Hindi / Hinglish.",v->toast("Type your question below"));EditText e=new EditText(this);e.setHint("Explain this question...");e.setTextSize(14);e.setPadding(15,0,15,0);e.setBackground(stroke(WHITE,Color.rgb(225,229,236),16));body.addView(e,new LinearLayout.LayoutParams(-1,54));action("Send  →",v->sendFighter(e));action("🔑 Set Gemini API Key",v->setGeminiKey());}
    void ca(String s){body.removeAllViews();topTitle(s);card("CURRENT AFFAIRS","Fresh verified updates","Facts should be dated and sourced.");action("←  Back",v->page("Current Affairs"));}
    void about(){body.removeAllViews();topTitle("About");
        LinearLayout hero=new LinearLayout(this);hero.setOrientation(LinearLayout.VERTICAL);hero.setGravity(Gravity.CENTER);hero.setPadding(20,20,20,20);hero.setBackground(gradient(NAVY,NAVY2,24));
        ImageView logo=new ImageView(this);logo.setImageResource(R.drawable.warrior_icon);logo.setScaleType(ImageView.ScaleType.CENTER_INSIDE);hero.addView(logo,new LinearLayout.LayoutParams(-1,130));
        hero.addView(text("WARRIOR ASPIRANTS",24,WHITE,true));hero.addView(text("KNOWLEDGE FOR VICTORY",10,Color.rgb(205,219,239),true));
        LinearLayout.LayoutParams hp=new LinearLayout.LayoutParams(-1,-2);hp.setMargins(0,0,0,14);body.addView(hero,hp);
        card("VERSION","13.1","Personal exam-preparation companion");
        card("PREPARATION","SSC CGL • Railway • UP SI • UPSSSC PET","Theory, concepts, important points, practice, PYQs and revision");
        card("STUDY MATERIAL","Offline","Core study content is bundled inside the app; internet features are used for live/current services and Fighter AI.");
        card("FIGHTER AI","Your study partner","Explain • Solve • Quiz • Revise in simple Hindi / Hinglish");
        action("📤 Share Warrior Aspirants",v->shareApp());action("← Back to Profile",v->page("Profile"));
    }

    void settings(){body.removeAllViews();topTitle("Settings");card("THEME",darkMode?"Dark Mode":"Light Mode","Choose your preferred app appearance."); action(darkMode?"☀️  Switch to Light Mode":"🌙  Switch to Dark Mode",v->{darkMode=!darkMode;getPreferences(MODE_PRIVATE).edit().putBoolean("dark_mode",darkMode).apply();applyThemeBars();home();}); card("TARGET EXAMS","SSC CGL 2027","Railway • UP SI");card("DAILY TARGET","2 hours","Personalised planner can be expanded.");action("🗑  Reset saved progress",v->{new AlertDialog.Builder(this).setTitle("Reset progress?").setMessage("XP, solved questions and Mistake Book will be cleared.").setNegativeButton("Cancel",null).setPositiveButton("Reset",(d,w)->{getSharedPreferences("warrior_progress",MODE_PRIVATE).edit().clear().apply();store=new ProgressStore(this);mistakes=store.mistakes();toast("Progress reset");page("Profile");}).show();});}
    void progress(){body.removeAllViews();topTitle("Progress");card("LEVEL "+store.level(),"XP "+store.xp(),"Preparation "+store.prep()+"% • Accuracy "+store.accuracy()+"% • Streak "+store.streak()+" days");card("LEARNING LOOP","Learn → Practice → Analyze → Improve","Progress is saved on this device.");}
    void openUrl(String u){try{startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(u)));}catch(Exception e){toast("Link open नहीं हुआ.");}}
    void shareApp(){Intent i=new Intent(Intent.ACTION_SEND);i.setType("text/plain");i.putExtra(Intent.EXTRA_TEXT,"Warrior Aspirants — SSC CGL, Railway, UP SI & UPSSSC PET preparation app.");startActivity(Intent.createChooser(i,"Share Warrior Aspirants"));}
    void pickPdf(){Intent i=new Intent(Intent.ACTION_OPEN_DOCUMENT);i.setType("*/*");i.addCategory(Intent.CATEGORY_OPENABLE);try{startActivityForResult(i,7001);}catch(Exception e){toast("File picker unavailable.");}}
    protected void onActivityResult(int requestCode,int resultCode,Intent data){super.onActivityResult(requestCode,resultCode,data);if(requestCode==7001&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){getPreferences(MODE_PRIVATE).edit().putString("last_pdf",data.getData().toString()).apply();toast("Study file selected.");}}
    void history(){body.removeAllViews();topTitle("History");card("LEARNING HISTORY","Saved locally","Solved questions and XP are stored on this device.");card("SOLVED",""+store.solved(),"Correct: "+store.correct()+" • Accuracy: "+store.accuracy()+"%");action("📊 View Progress",v->progress());}
    void bookmarks(){body.removeAllViews();topTitle("Bookmarks");String saved=getPreferences(MODE_PRIVATE).getString("bookmark_topic","");if(saved.length()==0)card("EMPTY","No bookmark yet","Save any topic for quick revision.");else{card("SAVED TOPIC",saved,"Resume this lesson.");action("▶ Open "+saved,v->lesson(saved));}action("📚 Number System",v->saveBookmark("Number System"));action("📚 Percentage",v->saveBookmark("Percentage"));}
    void saveBookmark(String t){getPreferences(MODE_PRIVATE).edit().putString("bookmark_topic",t).apply();toast("Bookmarked: "+t);}
    void setGeminiKey(){final EditText e=new EditText(this);e.setHint("Paste Gemini API key");e.setSingleLine(true);e.setText(getPreferences(MODE_PRIVATE).getString("gemini_key",""));new AlertDialog.Builder(this).setTitle("Fighter AI API Key").setMessage("Key is stored locally on this device.").setView(e).setNegativeButton("Cancel",null).setPositiveButton("Save",(d,w)->{getPreferences(MODE_PRIVATE).edit().putString("gemini_key",e.getText().toString().trim()).apply();toast("API key saved.");}).show();}
    void sendFighter(EditText input){String q=input.getText().toString().trim();if(q.length()==0){toast("Question लिखें.");return;}String key=getPreferences(MODE_PRIVATE).getString("gemini_key","");if(key.length()==0){setGeminiKey();return;}toast("Fighter is thinking…");new Thread(()->{try{URL u=new URL("https://generativelanguage.googleapis.com/v1beta/models/gemini-3.8-flash:generateContent");HttpURLConnection c=(HttpURLConnection)u.openConnection();c.setRequestMethod("POST");c.setRequestProperty("Content-Type","application/json");c.setRequestProperty("x-goog-api-key",key);c.setConnectTimeout(20000);c.setReadTimeout(30000);c.setDoOutput(true);JSONObject part=new JSONObject();part.put("text","You are Fighter AI, an exam tutor. Explain in simple Hindi/Hinglish step by step. User: "+q);JSONObject bodyJ=new JSONObject();bodyJ.put("contents",new JSONArray().put(new JSONObject().put("role","user").put("parts",new JSONArray().put(part))));try(OutputStream os=c.getOutputStream()){os.write(bodyJ.toString().getBytes("UTF-8"));}int code=c.getResponseCode();InputStream is=code>=200&&code<300?c.getInputStream():c.getErrorStream();StringBuilder out=new StringBuilder();if(is!=null){try(BufferedReader br=new BufferedReader(new InputStreamReader(is))){String line;while((line=br.readLine())!=null)out.append(line);}}String ans="Fighter response unavailable.";if(code>=200&&code<300){JSONObject j=new JSONObject(out.toString());JSONArray candidates=j.optJSONArray("candidates");if(candidates!=null&&candidates.length()>0){JSONObject content=candidates.getJSONObject(0).optJSONObject("content");if(content!=null){JSONArray parts=content.optJSONArray("parts");if(parts!=null){for(int k=0;k<parts.length();k++){String t=parts.getJSONObject(k).optString("text","");if(!t.isEmpty()){ans=t;break;}}}}}if(ans.equals("Fighter response unavailable.")){ans="Google returned no text. Response: "+out;}}else{ans="Google API error (HTTP "+code+"): "+out;}final String a=ans;runOnUiThread(()->showAnswer(a));}catch(Exception e){runOnUiThread(()->showAnswer("Fighter connection error: "+e.getClass().getSimpleName()+" — "+e.getMessage()));}}).start();}
    void showAnswer(String a){new AlertDialog.Builder(this).setTitle("Fighter AI").setMessage(a).setPositiveButton("OK",null).show();}
    void vacancies(){body.removeAllViews();topTitle("Vacancies & Updates");card("RECRUITMENT HUB","Official sources","Verify dates and eligibility on the notice.");action("🏛 SSC Official",v->openUrl("https://ssc.gov.in/"));action("🚆 Railway / RRB",v->openUrl("https://www.rrbcdg.gov.in/"));action("🟢 UPSSSC",v->openUrl("https://upsssc.gov.in/"));action("👮 UP Police",v->openUrl("https://uppbpb.gov.in/"));action("← Back",v->page("Home"));}
    void toast(String s){Toast.makeText(this,s,Toast.LENGTH_SHORT).show();}
}
// Build trigger 2026-10-01T17:09:16.983Z