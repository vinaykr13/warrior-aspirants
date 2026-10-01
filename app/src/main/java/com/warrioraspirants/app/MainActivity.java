package com.warrioraspirants.app;

import android.app.*;
import android.os.*;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.*;
import android.widget.*;
import java.util.*;

public class MainActivity extends Activity {
    final int NAVY=Color.rgb(7,25,57), NAVY2=Color.rgb(16,44,86), RED=Color.rgb(204,42,50);
    final int BG=Color.rgb(247,249,252), WHITE=Color.WHITE, DARK=Color.rgb(27,35,48), MUTED=Color.rgb(105,114,130);
    LinearLayout root,body; ProgressStore store; ArrayList<String> mistakes=new ArrayList<>();

    public void onCreate(Bundle b){
        super.onCreate(b);
        store=new ProgressStore(this); mistakes=store.mistakes();
        getWindow().setStatusBarColor(WHITE); getWindow().setNavigationBarColor(WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
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
        LinearLayout l=new LinearLayout(this); l.setOrientation(LinearLayout.VERTICAL); l.setGravity(Gravity.CENTER);
        l.setPadding(28,20,28,20); l.setBackgroundColor(NAVY);
        ImageView logo=new ImageView(this); logo.setImageResource(R.drawable.warrior_icon); logo.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        l.addView(logo,new LinearLayout.LayoutParams(-1,220));
        TextView a=text("WARRIOR ASPIRANTS",28,WHITE,true), q=text("Build the Empire.\nThe Queen Will Come.",17,Color.LTGRAY,true);
        a.setGravity(Gravity.CENTER);q.setGravity(Gravity.CENTER);l.addView(a);l.addView(q);setContentView(l);
        logo.setAlpha(0);logo.setScaleX(.8f);logo.setScaleY(.8f);a.setAlpha(0);q.setAlpha(0);
        logo.animate().alpha(1).scaleX(1).scaleY(1).setDuration(650);
        a.animate().alpha(1).setStartDelay(300).setDuration(500);q.animate().alpha(1).setStartDelay(500).setDuration(500);
        new Handler().postDelayed(this::home,1900);
    }

    TextView title(String s,float size){TextView t=text(s,size,DARK,true);t.setPadding(0,0,0,0);return t;}
    TextView muted(String s,float size){return text(s,size,MUTED,false);}

    void home(){
        root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setBackgroundColor(BG);
        body=new LinearLayout(this);body.setOrientation(LinearLayout.VERTICAL);body.setPadding(18,8,18,18);
        ScrollView sc=new ScrollView(this);sc.setBackgroundColor(BG);sc.setOverScrollMode(View.OVER_SCROLL_NEVER);sc.addView(body);
        root.addView(sc,new LinearLayout.LayoutParams(-1,0,1)); root.addView(bottomNav());
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

    void homeContent(){
        LinearLayout top=new LinearLayout(this);top.setOrientation(LinearLayout.HORIZONTAL);top.setGravity(Gravity.CENTER_VERTICAL);
        top.setPadding(4,10,4,8);top.setBackgroundColor(NAVY);
        Button menu=new Button(this);menu.setText("☰");menu.setTextColor(WHITE);menu.setTextSize(22);menu.setAllCaps(false);menu.setBackgroundColor(Color.TRANSPARENT);menu.setOnClickListener(v->telegramMenu());
        top.addView(menu,new LinearLayout.LayoutParams(52,52));
        LinearLayout names=new LinearLayout(this);names.setOrientation(LinearLayout.VERTICAL);names.addView(text("WARRIOR ASPIRANTS",17,WHITE,true));names.addView(text("Study • Practice • Achieve",10,Color.rgb(190,204,228),false));
        top.addView(names,new LinearLayout.LayoutParams(0,54,1));
        TextView search=text("⌕",27,WHITE,false);search.setGravity(Gravity.CENTER);top.addView(search,new LinearLayout.LayoutParams(48,52));
        body.addView(top);
        TextView welcome=text("Welcome, Warrior! ⚔",22,DARK,true);welcome.setPadding(4,16,4,8);body.addView(welcome);
        LinearLayout searchRow=new LinearLayout(this);searchRow.setGravity(Gravity.CENTER_VERTICAL);searchRow.setPadding(12,0,8,0);searchRow.setBackground(shape(WHITE,16));
        searchRow.addView(text("⌕",23,MUTED,false),new LinearLayout.LayoutParams(34,48));searchRow.addView(muted("Search lessons, PYQs, topics...",13),new LinearLayout.LayoutParams(0,48,1));searchRow.addView(text("☷",20,RED,true),new LinearLayout.LayoutParams(38,48));
        LinearLayout.LayoutParams sp=new LinearLayout.LayoutParams(-1,50);sp.setMargins(0,2,0,10);body.addView(searchRow,sp);
        LinearLayout chips=new LinearLayout(this);
        String[] exams={"SSC CGL","Railway","UP SI"};for(int i=0;i<3;i++){TextView c=chip(exams[i],i==0);LinearLayout.LayoutParams cp=new LinearLayout.LayoutParams(-2,36);cp.setMargins(0,0,7,0);chips.addView(c,cp);}
        body.addView(chips);
        section("TODAY");
        listRow("📚","Continue Learning","Maths • Number System","Resume where you stopped",v->lesson("Number System"));
        listRow("⚡","Practice Questions","10 questions","Improve speed & accuracy",v->practice());
        listRow("🏆","Mock Test","SSC CGL Tier 1","Full test • Timer • Analysis",v->mock());
        section("STUDY");
        listRow("📐","Maths","12 topics available","Basic → Advanced",v->topics("Maths"));
        listRow("🧠","Reasoning","8 topics available","Concepts → Practice",v->topics("Reasoning"));
        listRow("🔤","English","8 topics available","Grammar → PYQs",v->topics("English"));
        listRow("🌍","GK / GS","7 topics available","Static + Science",v->topics("GK / GS"));
        section("TOOLS");
        listRow("📚","PYQ Bank","SSC CGL • Railway • UP SI","Year • Shift • Topic",v->pyq());
        listRow("📰","Current Affairs","Daily • Weekly • Monthly","Fresh verified updates",v->page("Current Affairs"));
        listRow("📕","Mistake Book",mistakes.size()+" saved questions","Revise your weak points",v->mistakes());
        listRow("🤖","Fighter AI","Explain • Solve • Quiz","Your study partner",v->fighter());
        TextView p=text("⚔ "+store.xp()+" XP   •   Level "+store.level()+"   •   Accuracy "+store.accuracy()+"%",11,RED,true);p.setGravity(Gravity.CENTER);p.setPadding(0,15,0,15);body.addView(p);
    }

    void listRow(String icon,String titleText,String line1,String line2,View.OnClickListener l){
        LinearLayout c=new LinearLayout(this);c.setGravity(Gravity.CENTER_VERTICAL);c.setPadding(10,6,8,6);c.setBackground(stroke(WHITE,Color.rgb(225,229,236),15));c.setOnClickListener(l);
        TextView ic=text(icon,23,RED,true);ic.setGravity(Gravity.CENTER);c.addView(ic,new LinearLayout.LayoutParams(48,58));
        LinearLayout mid=new LinearLayout(this);mid.setOrientation(LinearLayout.VERTICAL);mid.setPadding(8,0,5,0);mid.addView(title(titleText,15));mid.addView(muted(line1,10));mid.addView(muted(line2,9));c.addView(mid,new LinearLayout.LayoutParams(0,66,1));
        TextView arrow=text("›",28,RED,false);arrow.setGravity(Gravity.CENTER);c.addView(arrow,new LinearLayout.LayoutParams(34,66));
        c.setOnTouchListener((v,e)->{if(e.getAction()==MotionEvent.ACTION_DOWN)press(v);return false;});
        LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,74);p.setMargins(0,3,0,3);body.addView(c,p);animateView(c,body.getChildCount()*12);
    }

    void telegramMenu(){
        body.removeAllViews();
        LinearLayout head=new LinearLayout(this);head.setOrientation(LinearLayout.VERTICAL);head.setPadding(18,20,18,18);head.setBackground(NAVY==0?shape(NAVY,0):shape(NAVY,0));
        head.addView(text("WARRIOR ASPIRANTS",20,WHITE,true));head.addView(text("SSC CGL 2027 • Railway • UP SI",11,Color.rgb(205,215,235),false));body.addView(head);
        section("MENU");
        listRow("⌂","Home","Dashboard","Today's mission",v->page("Home"));
        listRow("📚","Study Room","Maths • Reasoning • English • GK/GS","Learn concepts",v->page("Study"));
        listRow("⚡","Practice Arena","Quick practice • Topic practice","Build speed",v->page("Practice"));
        listRow("📖","PYQ Bank","Previous year questions","Exam • Year • Topic",v->pyq());
        listRow("📰","Current Affairs","Daily • Weekly • Monthly","Stay updated",v->page("Current Affairs"));
        listRow("📕","Mistake Book",mistakes.size()+" saved","Revise mistakes",v->mistakes());
        listRow("🤖","Fighter AI","Explain • Solve • Quiz","Ask your doubt",v->fighter());
        listRow("👤","Profile","Progress • History • Settings","Your account",v->page("Profile"));
        action("←  Back to Home",v->page("Home"));
    }

    void gridRow(LinearLayout parent,String[] labels,View.OnClickListener[] ls){
        LinearLayout r=new LinearLayout(this);r.setWeightSum(3);
        for(int i=0;i<3;i++){LinearLayout c=new LinearLayout(this);c.setOrientation(LinearLayout.VERTICAL);c.setGravity(Gravity.CENTER);c.setPadding(4,8,4,8);c.setBackground(stroke(WHITE,Color.rgb(225,229,236),16));TextView ic=text(labels[i].split("\n")[0],20,RED,true);ic.setGravity(Gravity.CENTER);c.addView(ic);String[] parts=labels[i].split("\n");StringBuilder z=new StringBuilder();for(int k=1;k<parts.length;k++){if(k>1)z.append("\n");z.append(parts[k]);}TextView n=text(z.toString(),12,DARK,true);n.setGravity(Gravity.CENTER);c.addView(n);c.setOnClickListener(ls[i]);c.setOnTouchListener((v,e)->{if(e.getAction()==MotionEvent.ACTION_DOWN)press(v);return false;});LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(0,86,1);p.setMargins(3,3,3,3);r.addView(c,p);animateView(c,70+i*40);}parent.addView(r);
    }

    void newsCard(String s){LinearLayout c=new LinearLayout(this);c.setOrientation(LinearLayout.VERTICAL);c.setPadding(12,10,12,10);c.setBackground(stroke(WHITE,Color.rgb(225,229,236),15));c.addView(text(s,13,DARK,true));c.addView(muted("Today  •  1 min read",9));LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(0,3,0,5);body.addView(c,p);}
    void recCard(LinearLayout row,String s,View.OnClickListener l){LinearLayout c=new LinearLayout(this);c.setOrientation(LinearLayout.VERTICAL);c.setGravity(Gravity.CENTER);c.setPadding(8,8,8,8);c.setBackground(stroke(WHITE,Color.rgb(225,229,236),15));c.addView(text("📚",22,RED,true));TextView n=text(s,11,DARK,true);n.setGravity(Gravity.CENTER);c.addView(n);c.setOnClickListener(l);LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(0,100,1);p.setMargins(3,0,3,0);row.addView(c,p);}
    View floatingFighter(){Button b=new Button(this);b.setText("🤖  Fighter AI");b.setTextColor(WHITE);b.setTextSize(13);b.setAllCaps(false);b.setBackground(shape(NAVY,25));b.setElevation(6);b.setOnClickListener(v->fighter());return b;}

    View bottomNav(){
        LinearLayout nav=new LinearLayout(this);nav.setGravity(Gravity.CENTER);nav.setPadding(5,5,5,5);nav.setBackgroundColor(WHITE);nav.setElevation(12);
        String[] a={"⌂\nHome","▣\nStudy","⚡\nPractice","◷\nCurrent","◉\nProfile"};
        String[] pages={"Home","Study","Practice","Current Affairs","Profile"};
        for(int i=0;i<a.length;i++){final String p=pages[i];TextView b=text(a[i],10,NAVY,true);b.setGravity(Gravity.CENTER);b.setOnClickListener(v->page(p));b.setOnTouchListener((v,e)->{if(e.getAction()==MotionEvent.ACTION_DOWN)press(v);return false;});nav.addView(b,new LinearLayout.LayoutParams(0,58,1));}
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
        } else if(p.equals("Practice")){
            hero("PRACTICE ARENA","Train your speed & accuracy","10 / 20 / 50 questions • Timer • Analysis",v->practice());
            action("⚡  Quick Practice — 10 Questions",v->practice());action("🎯  Topic Practice",v->topics("Practice Topics"));action("📚  PYQ Engine",v->pyq());action("🏆  Mock Test",v->mock());action("📕  Mistake Book",v->mistakes());
        } else if(p.equals("Current Affairs")){
            section("CURRENT AFFAIRS");action("📅  Today",v->ca("Today's Current Affairs"));action("Yesterday",v->ca("Yesterday"));action("📚  Weekly",v->ca("Weekly Revision"));action("🗓  Monthly One-Liners",v->ca("Monthly One-Liners"));action("❓  Current Affairs Quiz",v->ca("Current Affairs Quiz"));
        } else {
            profileHeader();action("📊  Progress",v->progress());action("🕘  History",v->toast("History will show completed lessons and tests."));action("🔖  Bookmarks",v->toast("Bookmarks ready for saved content."));action("⚙️  Settings",v->settings());
        }
    }

    void profileHeader(){LinearLayout c=new LinearLayout(this);c.setOrientation(LinearLayout.VERTICAL);c.setGravity(Gravity.CENTER);c.setPadding(12,14,12,16);c.setBackground(shape(NAVY,20));TextView av=text("👤",40,WHITE,true);av.setGravity(Gravity.CENTER);c.addView(av);c.addView(text("Warrior Profile",18,WHITE,true));c.addView(text("Level "+store.level()+"  •  "+store.xp()+" XP",12,Color.rgb(210,220,238),false));LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(0,0,0,12);body.addView(c,p);}
    void studyCard(String ic,String name,String value,String sub,View.OnClickListener l){LinearLayout c=new LinearLayout(this);c.setOrientation(LinearLayout.VERTICAL);c.setPadding(12,10,12,10);c.setBackground(stroke(WHITE,Color.rgb(225,229,236),16));TextView i=text(ic,20,RED,true);c.addView(i);c.addView(title(name,16));c.addView(muted(sub,9));ProgressBar pb=new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal);int pr=value.equals("Today")?65:Integer.parseInt(value.replace("%",""));pb.setProgress(pr);pb.setProgressDrawable(shape(RED,8));c.addView(pb,new LinearLayout.LayoutParams(-1,7));TextView go=text("CONTINUE  ›",10,RED,true);go.setGravity(Gravity.RIGHT);c.addView(go);c.setOnClickListener(l);LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,108);p.setMargins(0,4,0,4);body.addView(c,p);animateView(c,body.getChildCount()*30);}

    void topTitle(String p){LinearLayout bar=new LinearLayout(this);bar.setGravity(Gravity.CENTER_VERTICAL);bar.setPadding(2,4,2,14);TextView back=text("‹",32,NAVY,false);back.setGravity(Gravity.CENTER);back.setOnClickListener(v->page("Home"));bar.addView(back,new LinearLayout.LayoutParams(38,48));TextView t=title(p,22);bar.addView(t,new LinearLayout.LayoutParams(0,48,1));TextView xp=text("⚔ "+store.xp(),11,RED,true);xp.setGravity(Gravity.CENTER);xp.setBackground(shape(Color.rgb(255,239,240),18));bar.addView(xp,new LinearLayout.LayoutParams(70,36));body.addView(bar);}

    void section(String s){TextView t=text(s,11,RED,true);t.setPadding(3,13,3,7);body.addView(t);}
    Button action(String s,View.OnClickListener l){Button b=new Button(this);b.setText(s);b.setTextSize(13);b.setTextColor(NAVY);b.setAllCaps(false);b.setGravity(Gravity.CENTER_VERTICAL);b.setPadding(15,0,15,0);b.setBackground(stroke(WHITE,Color.rgb(225,229,236),15));b.setElevation(1);b.setOnClickListener(l);LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,55);p.setMargins(0,4,0,4);body.addView(b,p);animateView(b,body.getChildCount()*18);return b;}
    void card(String h,String sub,String val){LinearLayout c=new LinearLayout(this);c.setOrientation(LinearLayout.VERTICAL);c.setPadding(14,12,14,13);c.setBackground(stroke(WHITE,Color.rgb(225,229,236),18));c.addView(text(h,10,RED,true));c.addView(title(sub,16));c.addView(muted(val,11));LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(0,3,0,9);body.addView(c,p);animateView(c,40);}
    void hero(String h,String t,String sub,View.OnClickListener l){LinearLayout c=new LinearLayout(this);c.setOrientation(LinearLayout.VERTICAL);c.setPadding(16,15,16,15);c.setBackground(shape(NAVY,20));c.setElevation(4);c.addView(text(h,10,Color.rgb(190,204,228),true));c.addView(text(t,20,WHITE,true));c.addView(text(sub,11,Color.rgb(218,225,238),false));Button go=new Button(this);go.setText("START  →");go.setTextColor(WHITE);go.setAllCaps(false);go.setBackground(shape(RED,17));go.setOnClickListener(l);c.addView(go,new LinearLayout.LayoutParams(-1,45));LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(0,2,0,10);body.addView(c,p);animateView(c,40);}

    void topics(String s){
        body.removeAllViews();topTitle(s+" Topics");
        if(s.equals("Maths")){topic("01","Number System","Basics, divisibility, HCF / LCM",v->lesson("Number System"));topic("02","Simplification","BODMAS and calculations",v->lesson("Percentage"));topic("03","HCF & LCM","Factors and multiples",v->lesson("Number System"));topic("04","Percentage","Increase, decrease, successive",v->lesson("Percentage"));topic("05","Ratio & Proportion","Applications",v->lesson("Ratio & Proportion"));topic("06","Average","Mean and weighted average",v->lesson("Average"));topic("07","Profit & Loss","CP, SP and discount",v->lesson("Profit & Loss"));topic("08","Time & Work","Efficiency and work",v->lesson("Time & Work"));topic("09","TSD","Speed, distance and trains",v->lesson("Time, Speed & Distance"));topic("10","Algebra","Identities and equations",v->lesson("Algebra"));topic("11","Geometry","Lines, angles, triangles",v->lesson("Geometry & Mensuration"));topic("12","Trigonometry","Ratios and standard values",v->lesson("Trigonometry"));}
        else if(s.equals("Reasoning")){topic("01","Analogy","Same relationship",v->lesson("Analogy"));topic("02","Classification","Odd one out",v->lesson("Classification"));topic("03","Series","Number and alphabet series",v->lesson("Series"));topic("04","Coding-Decoding","Patterns",v->lesson("Coding-Decoding"));topic("05","Blood Relation","Family relations",v->lesson("Blood Relation"));topic("06","Direction","Distance and paths",v->lesson("Direction & Distance"));topic("07","Syllogism","Statements and conclusions",v->lesson("Syllogism"));topic("08","Venn Diagram","Set relationships",v->lesson("Venn Diagram"));}
        else if(s.equals("English")){topic("01","Parts of Speech","Grammar basics",v->lesson("Parts of Speech"));topic("02","Tenses","Present, past, future",v->lesson("Tenses"));topic("03","Subject-Verb Agreement","Rules",v->lesson("Subject-Verb Agreement"));topic("04","Articles","A, An, The",v->lesson("Articles"));topic("05","Prepositions","Usage",v->lesson("Prepositions"));topic("06","Voice","Active & Passive",v->lesson("Active & Passive Voice"));topic("07","Narration","Direct & indirect",v->lesson("Narration"));topic("08","Error Detection","Common grammar errors",v->lesson("Error Detection"));}
        else {topic("01","History","Ancient, Medieval, Modern",v->lesson("History"));topic("02","Geography","India and world",v->lesson("Geography"));topic("03","Polity","Constitution",v->lesson("Polity"));topic("04","Economy","Basic concepts",v->lesson("Economy"));topic("05","Physics","Motion, energy, electricity",v->lesson("Physics"));topic("06","Chemistry","Matter and reactions",v->lesson("Chemistry"));topic("07","Biology","Cells, body, plants",v->lesson("Biology"));}
        action("←  Back to Study",v->page("Study"));
    }

    void topic(String no,String name,String sub,View.OnClickListener l){LinearLayout c=new LinearLayout(this);c.setGravity(Gravity.CENTER_VERTICAL);c.setPadding(9,5,9,5);c.setBackground(stroke(WHITE,Color.rgb(225,229,236),15));TextView n=text(no,10,RED,true);n.setGravity(Gravity.CENTER);n.setBackground(shape(Color.rgb(255,240,241),14));c.addView(n,new LinearLayout.LayoutParams(42,52));LinearLayout mid=new LinearLayout(this);mid.setOrientation(LinearLayout.VERTICAL);mid.setPadding(12,0,4,0);mid.addView(title(name,15));mid.addView(muted(sub+"\nProgress • Difficulty • Questions",9));c.addView(mid,new LinearLayout.LayoutParams(0,64,1));TextView go=text("›",29,RED,true);go.setGravity(Gravity.CENTER);c.addView(go,new LinearLayout.LayoutParams(38,64));c.setOnClickListener(l);LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,72);p.setMargins(0,4,0,4);body.addView(c,p);animateView(c,body.getChildCount()*15);}

    void lesson(String t){body.removeAllViews();topTitle(t);card("CONCEPT EXPLANATION","Theory • Formula • Short Tricks","Read → understand → solve.");body.addView(text("THEORY",11,RED,true));body.addView(text(theory(t),14,DARK,false));body.addView(text("FORMULAS / SHORT TRICKS",11,RED,true));body.addView(text(trick(t),14,DARK,false));body.addView(text("SOLVED EXAMPLE",11,RED,true));body.addView(text(example(t),14,DARK,false));action("🎯  Practice",v->practiceTopic(t));action("📚  PYQs",v->pyqTopic(t));action("🤖  Ask Fighter AI",v->fighter());}
    StudyContent.Lesson material(String t){return StudyContent.get(t);} String theory(String t){return material(t).theory;}String example(String t){return material(t).example;}String trick(String t){return material(t).trick;}

    void practice(){practiceTopic("Mixed Practice");}
    void practiceTopic(String topic){body.removeAllViews();topTitle("PYQ / Practice");hero("QUANTITATIVE APTITUDE","एक वस्तु का मूल 20% बढ़ा दिया गया। यदि नया मूल्य ₹600 है, तो मूल मूल्य क्या था?","Timer  •  10 questions  •  Instant analysis",v->toast("Choose an option"));String[] o={"₹600","₹500","₹480","₹720"};for(String x:o){final String ans=x;action(ans,v->{if(ans.equals("₹500")){store.addResult(true);toast("Correct! +10 XP");}else{store.addResult(false);store.saveMistake(topic+" — Percentage question");toast("Wrong • Mistake Book में saved");}mistakes=store.mistakes();});}action("Next Question  →",v->toast("Next verified question will load here."));}
    void pyq(){body.removeAllViews();topTitle("PYQs");card("QUESTION BANK","SSC CGL • Railway • UP SI","Exam • Year • Shift • Topic • Solution");action("SSC CGL → Maths",v->pyqTopic("Maths"));action("SSC CGL → Reasoning",v->pyqTopic("Reasoning"));action("SSC CGL → English",v->pyqTopic("English"));action("SSC CGL → General Awareness",v->pyqTopic("GK / GS"));}
    void pyqTopic(String t){body.removeAllViews();topTitle("PYQ • "+t);card("PREVIOUS YEAR","Verified question bank","Options • Correct answer • Detailed explanation");action("▶  Start Topic PYQ",v->practiceTopic(t));action("←  Back",v->pyq());}
    void mock(){body.removeAllViews();topTitle("Mock Test");hero("FULL MOCK","SSC CGL Tier 1 & 2","Timer • Section analysis • Accuracy • Mistakes",v->practiceTopic("Mock Test"));action("🏆  Start Mock",v->practiceTopic("Mock Test"));action("←  Back",v->page("Practice"));}
    void mistakes(){body.removeAllViews();topTitle("Mistake Book");if(mistakes.size()==0)card("EMPTY","No mistakes saved yet","Wrong questions will appear here.");else for(String m:mistakes)card("REVISE",m,"Retry → understand → improve");action("←  Back",v->page("Practice"));}
    void fighter(){body.removeAllViews();topTitle("Fighter AI");hero("YOUR STUDY PARTNER","Explain • Solve • Quiz • Revise","Ask in simple Hindi / Hinglish.",v->toast("Type your question below"));EditText e=new EditText(this);e.setHint("Explain this question...");e.setTextSize(14);e.setPadding(15,0,15,0);e.setBackground(stroke(WHITE,Color.rgb(225,229,236),16));body.addView(e,new LinearLayout.LayoutParams(-1,54));action("Send  →",v->toast("Fighter AI backend can be connected later."));}
    void ca(String s){body.removeAllViews();topTitle(s);card("CURRENT AFFAIRS","Fresh verified updates","Facts should be dated and sourced.");action("←  Back",v->page("Current Affairs"));}
    void settings(){body.removeAllViews();topTitle("Settings");card("TARGET EXAMS","SSC CGL 2027","Railway • UP SI");card("DAILY TARGET","2 hours","Personalised planner can be expanded.");action("🗑  Reset saved progress",v->{new AlertDialog.Builder(this).setTitle("Reset progress?").setMessage("XP, solved questions and Mistake Book will be cleared.").setNegativeButton("Cancel",null).setPositiveButton("Reset",(d,w)->{getSharedPreferences("warrior_progress",MODE_PRIVATE).edit().clear().apply();store=new ProgressStore(this);mistakes=store.mistakes();toast("Progress reset");page("Profile");}).show();});}
    void progress(){body.removeAllViews();topTitle("Progress");card("LEVEL "+store.level(),"XP "+store.xp(),"Preparation "+store.prep()+"% • Accuracy "+store.accuracy()+"% • Streak "+store.streak()+" days");card("LEARNING LOOP","Learn → Practice → Analyze → Improve","Progress is saved on this device.");}
    void toast(String s){Toast.makeText(this,s,Toast.LENGTH_SHORT).show();}
}