package com.warrioraspirants.app;

import java.util.*;

public class StudyContent {
    public static class Lesson {
        public final String theory, example, trick;
        Lesson(String theory, String example, String trick) {
            this.theory = theory; this.example = example; this.trick = trick;
        }
    }

    private static final Map<String, Lesson> DATA = new HashMap<>();

    static {
        // MATHS
        add("Number System",
            "Number System me Natural Numbers (1,2,3...), Whole Numbers (0 se), Integers (...-2,-1,0,1,2...), Rational Numbers (p/q, q≠0), Irrational Numbers aur Real Numbers aate hain.\n\nDivisibility: 2→last digit even; 3→digits ka sum 3 se divisible; 4→last 2 digits 4 se divisible; 5→last digit 0/5; 6→2 aur 3 dono se divisible; 8→last 3 digits 8 se divisible; 9→digits ka sum 9 se divisible; 10→last digit 0; 11→alternate digit sums ka difference 0 ya 11 ka multiple.\n\nHCF sabse bada common factor hota hai; LCM sabse chhota common multiple. Do positive numbers ke liye HCF × LCM = product of numbers.",
            "738 ko 3 se test karein: 7+3+8=18, aur 18 divisible by 3 hai. Isliye 738 divisible by 3 hai. 12 aur 18 ka HCF=6 aur LCM=36.",
            "3/9 ke liye digit sum, 4 ke liye last 2 digits, 8 ke liye last 3 digits aur 11 ke liye alternate sum yaad rakho. Prime factorisation se HCF/LCM fast nikalo.");

        add("Percentage",
            "Percentage ka matlab per hundred. x% = x/100. Kisi number ka p% = number × p/100. Increase p% ke baad new value = old × (100+p)/100; decrease p% ke baad = old × (100-p)/100.\n\nSuccessive percentage changes ko direct add nahi karna: factors multiply hote hain. Agar value a se b ho, percentage change = (difference/original)×100.\n\nCommon fractions: 1/2=50%, 1/4=25%, 1/5=20%, 1/8=12.5%, 1/10=10%, 1/20=5%.",
            "250 ka 20% = 250×20/100 = 50. 500 me 10% increase = 550. 500 par 10% decrease = 450.",
            "10%=1/10, 5%=1/20, 1%=1/100. Successive +10% aur -10% ka net 0% nahi, 1% decrease hota hai.");

        add("Ratio & Proportion",
            "Ratio A:B same kind ki quantities ka comparison hai. A:B=m:n ho to A=mk, B=nk. Proportion me a:b=c:d ho to ad=bc.\n\nRatio ko simplify karne ke liye common factor se divide karo. Direct proportion me ek quantity badhe to doosri bhi same ratio me badhti hai; inverse proportion me ek badhe to doosri ghatti hai.\n\nSSC questions me partnership, mixture, ages aur distribution me ratio ka use hota hai.",
            "A:B=2:3 aur A=20. 2 parts=20, 1 part=10, isliye B=30. Agar 4:5 = x:20, to 5x=80 aur x=16.",
            "Ratio me units same karo. Distribution me total parts = ratio terms ka sum; ek part = total ÷ total parts.");

        add("Average",
            "Average = total sum ÷ number of observations. Isliye total sum = average × number. Nayi value add/remove hone par pehle old aur new total compare karo.\n\nWeighted average me different groups ke sizes important hote hain: combined average = total of all groups ÷ total number. Equal quantities hone par simple average use hota hai.",
            "10,20,30 ka average = 60/3=20. 5 numbers ka average 18 hai, total=90. Agar ek 30 add ho, new average=(90+30)/6=20.",
            "Average questions me 'average × count = total' ko base formula banao. Replacement questions me total ka change directly use karo.");

        add("Profit & Loss",
            "Cost Price (CP) = kharidne ki price, Selling Price (SP) = bechne ki price. SP>CP to profit; CP>SP to loss. Profit%=Profit/CP×100; Loss%=Loss/CP×100.\n\nMarked Price (MP) par discount diya ja sakta hai: Discount=MP-SP, Discount%=Discount/MP×100. Successive discounts factors se calculate karo.\n\nSP = CP×(100+profit%)/100 aur SP = CP×(100-loss%)/100.",
            "CP=800 aur profit=15%. SP=800×115/100=920. MP=1000 par 20% discount ho to SP=800.",
            "Profit/loss percentage ka denominator CP hota hai. Discount percentage ka denominator MP hota hai—ye distinction pakka yaad rakho.");

        add("Time & Work",
            "Agar A kisi work ko x days me karta hai, one-day work=1/x. A aur B saath kaam karein to rates add hote hain: 1/x+1/y. Total time = 1/(combined rate).\n\nEfficiency aur time inverse relation me hain: efficiency ratio m:n ho to time ratio n:m. Work ko LCM units maan kar questions fast solve kiye ja sakte hain.",
            "A 10 days, B 15 days. Combined rate=1/10+1/15=1/6, isliye saath me work 6 days me.",
            "Days ki jagah LCM ko total work maan lo. 'More efficient' ka matlab less time.");

        add("Time, Speed & Distance",
            "Basic formula: Speed=Distance/Time, Distance=Speed×Time, Time=Distance/Speed. Units conversion: 1 m/s = 18/5 km/h; 1 km/h = 5/18 m/s.\n\nSame direction relative speed = difference; opposite direction = sum. Train questions me platform/crossing ke liye distance = train length + relevant length. Average speed equal distances par 2ab/(a+b) hoti hai.",
            "72 km/h = 72×5/18 = 20 m/s. 200 m train 10 m/s se pole cross karegi to time=200/10=20 sec.",
            "m/s↔km/h ke liye 18/5 aur 5/18 yaad rakho. Relative speed direction ke according add/subtract karo.");

        add("Algebra",
            "Algebra me variables aur equations ka use hota hai. Important identities: (a+b)^2=a^2+2ab+b^2; (a-b)^2=a^2-2ab+b^2; a^2-b^2=(a-b)(a+b); (a+b)^3=a^3+3a^2b+3ab^2+b^3.\n\nLinear equation ax+b=c me x=(c-b)/a. Factorisation se quadratic expressions simplify kiye ja sakte hain.",
            "x+7=19 ⇒ x=12. x^2-25=(x-5)(x+5). Agar a+b=10 aur ab=21, to (a-b)^2=(a+b)^2-4ab=100-84=16.",
            "Identity ko ratne ke saath pattern pehchano: a²-b² dikhe to direct factors; a+b aur ab diye hon to (a-b)² use karo.");

        add("Geometry & Mensuration",
            "Geometry basics: straight angle=180°, full angle=360°, vertically opposite angles equal. Triangle ke angles ka sum 180°. Exterior angle = two opposite interior angles ka sum. Pythagoras right triangle me a²+b²=c².\n\nMensuration: rectangle area=lb, perimeter=2(l+b); square area=a², perimeter=4a; triangle area=1/2×base×height; circle area=πr², circumference=2πr; cuboid volume=lbh; cylinder volume=πr²h.",
            "Triangle base 10 cm aur height 6 cm: area=1/2×10×6=30 cm². Right triangle legs 3,4: hypotenuse=5.",
            "Diagram banao. Area ki unit square aur volume ki unit cube hoti hai. 3-4-5, 5-12-13 jaise Pythagorean triples useful hain.");

        add("Trigonometry",
            "Right triangle me sinθ=Perpendicular/Hypotenuse, cosθ=Base/Hypotenuse, tanθ=Perpendicular/Base. Identities: sin²θ+cos²θ=1; 1+tan²θ=sec²θ; 1+cot²θ=cosec²θ.\n\nStandard values 0°,30°,45°,60°,90° ko table se practice karo. tanθ=sinθ/cosθ.",
            "45° par sin=cos=1/√2 aur tan=1. Agar P=3, B=4, H=5 ho to sinθ=3/5, cosθ=4/5, tanθ=3/4.",
            "SOH-CAH-TOA yaad rakho. Standard values ko daily 2 minute revise karo; identity ko blindly expand karne se pehle simplify dekho.");

        // REASONING
        add("Analogy",
            "Analogy me first pair ka exact relation identify karke second pair par apply karna hota hai. Relation word meaning, part-whole, worker-tool, place-person, number operation, alphabet position ya degree ka ho sakta hai. Pehle pair me jo operation hai wahi second pair me lagao.",
            "Book : Read :: Food : Eat. Number analogy 3:9 :: 4:16 me relation square ka hai.",
            "Answer se pehle relation ko ek short sentence me bolo: 'A ka B se ye relation hai'. Phir options check karo.");

        add("Classification",
            "Classification me 4 ya 5 items me se odd item identify karna hota hai. Common basis: category, property, number pattern, alphabet position, spelling, use ya relationship. Ek consistent rule chuno jo maximum items ko group kare.",
            "Apple, Mango, Banana, Carrot me Carrot odd hai kyunki baaki fruits hain.",
            "Odd-one-out ko random difference se nahi, common category/property se justify karo.");

        add("Series",
            "Number series me difference, second difference, multiplication/division, squares/cubes, prime numbers aur alternating patterns check karo. Alphabet series me A=1...Z=26 helpful hai. Missing-term series me consecutive gaps likhna fast method hai.",
            "2,5,10,17,26 → differences 3,5,7,9; next difference 11, so next=37.",
            "Pehle ×/÷, phir +/− aur differences check karo. Alternating series me odd-even positions alag analyse karo.");

        add("Coding-Decoding",
            "Coding-decoding me word/letter ko kisi rule se code kiya jata hai. Alphabet positions, +/− shifts, reverse alphabet, rearrangement aur word substitution common patterns hain. A=1...Z=26 aur reverse pair A-Z, B-Y useful hai.",
            "CAT me each letter +1 ho to DBU. Agar A=1, B=2... to CODE = 3-15-4-5.",
            "Given example me jo transformation clearly repeat ho raha ho, wahi use karo. Ek letter par rule test karke poore word par lagao.");

        add("Blood Relation",
            "Blood relation questions ko family tree se solve karo. Male/female aur generation mark karo. Father/mother one generation above, son/daughter one below, sibling same generation. 'Mother's brother' = maternal uncle; 'father's sister' = paternal aunt.",
            "Ravi, Sita ka brother hai aur Sita, Mohan ki mother hai. Ravi, Mohan ka maternal uncle hoga.",
            "Question ko diagram me convert karo. 'My mother's only son' jaise phrases ko step-by-step decode karo, guess mat karo.");

        add("Direction & Distance",
            "North ko top, South bottom, East right, West left maan kar coordinate style me solve karo. Opposite directions cancel ho sakti hain. Shortest distance ke liye right triangle/Pythagoras use karo. Turning questions me final facing direction track karo.",
            "3 km East aur 4 km North ka displacement = √(3²+4²)=5 km.",
            "Har move ko arrow/coordinate me likho. 'Right turn' ka meaning current facing direction ke respect me hota hai.");

        add("Syllogism",
            "Syllogism me statements se logically conclusions check kiye jate hain. All A are B ka matlab A, B ke andar hai; No A is B ka overlap nahi; Some A are B me at least one common member. Venn diagram se visualise karna safest hai.",
            "All cats are animals; some animals are black. Isse 'some cats are black' necessarily follow nahi karta, kyunki black animals cats na bhi hon.",
            "Conclusion ko statement se prove karo, real-world knowledge mat jodo. 'Some' ko existence ke roop me samjho.");

        add("Venn Diagram",
            "Venn diagram categories ke overlap ko show karta hai. All A are B me A circle B ke andar; No A is B me separate; Some A are B me overlap. 3-set questions me common intersection ko carefully identify karo.",
            "Students, boys aur athletes me kuch students boys aur athletes dono ho sakte hain; diagram me triple overlap possible hai.",
            "Question ke words ko circles me convert karo. 'All', 'some', 'none' ko exact logical meaning ke saath padho.");

        // ENGLISH
        add("Parts of Speech",
            "Main parts of speech: Noun (name), Pronoun (noun ki jagah), Verb (action/state), Adjective (noun ko describe), Adverb (verb/adjective/adverb ko modify), Preposition (relation), Conjunction (join), Interjection (sudden feeling). Same word context ke hisab se role badal sakta hai.",
            "In 'He runs fast', He=pronoun, runs=verb, fast=adverb. 'A fast car' me fast adjective hai.",
            "Word ko akela nahi, sentence me uska function dekho.");

        add("Tenses",
            "Tense action ka time aur form batata hai. Present/Past/Future ke Simple, Continuous, Perfect aur Perfect Continuous forms hote hain. Examples: I work; I am working; I have worked; I have been working. Past/future me helping verbs aur main verb form carefully check karo.",
            "He goes to school (simple present). He is going (present continuous). He has gone (present perfect). He went yesterday (simple past).",
            "Signal words help karte hain: yesterday→past, now→continuous context, since/for→perfect/continuous patterns. Lekin sentence meaning bhi check karo.");

        add("Subject-Verb Agreement",
            "Singular subject ke saath singular verb aur plural subject ke saath plural verb use hota hai. He/She/It works; I/You/We/They work. 'Each, every, either, neither' generally singular verb lete hain. 'As well as, along with, together with' main subject ko change nahi karte.",
            "Each of the boys is ready. The teacher, along with students, is present.",
            "Verb se pehle actual subject identify karo; beech ke prepositional phrases se confuse mat ho.");

        add("Articles",
            "A/an indefinite article hain; the definite article hai. A consonant sound se pehle, an vowel sound se pehle: a university (yu sound), an hour (silent h). The specific/known noun, unique objects, superlatives aur certain geographical names ke saath use hota hai. General plural/uncountable nouns me article omit ho sakta hai.",
            "He is an honest man because honest ka starting sound vowel hai. She is a university student because university 'yu' sound se start hota hai.",
            "Spelling nahi, sound dekho. 'An' vowel sound ke liye hai, sirf vowel letter ke liye nahi.");

        add("Prepositions",
            "Prepositions relation show karte hain: in, on, at, by, with, for, since, from, between, among etc. Time: at 5 pm, on Monday, in July. Place: at a point, on a surface, in an enclosed area. Since starting point, for duration.",
            "He has lived here for five years. He has lived here since 2021. The book is on the table.",
            "Since = कब से; for = कितने समय से. Between generally two distinct entities, among more than two/group ke context me.");

        add("Active & Passive Voice",
            "Active me subject action karta hai: Ram writes a letter. Passive me object focus banta hai: A letter is written by Ram. Basic process: object ko subject position me lao, tense ke according be-form + V3 use karo, subject ko by-phrase me rakh sakte ho.",
            "They completed the work. → The work was completed by them. Present simple: They write → It is written.",
            "Tense preserve karo. Passive me main verb ka V3 form aata hai; modal ke baad be + V3.");

        add("Narration",
            "Direct speech exact words quotes me hota hai; indirect speech reported form me. Reporting verb aur tense ke according changes hote hain. Pronouns, time/place words aur question structure carefully change karo. Universal truths me tense backshift nahi bhi hota.",
            "He said, 'I am tired.' → He said that he was tired. 'Are you ready?' → He asked if/whether I was ready.",
            "Statement→that, yes/no question→if/whether, WH-question me WH-word retain. Pronoun speaker/listener ke relation se decide karo.");

        add("Error Detection",
            "Error detection me subject-verb agreement, tense, article, preposition, pronoun, modifier, comparison, parallelism aur vocabulary errors common hote hain. Sentence ko parts me todkar grammar rule apply karo.",
            "Each boys are ready. Error: 'boys' nahi, 'Each' main subject hai; correct: Each boy is ready.",
            "Pehle verb-subject agreement check karo, phir tense/articles/prepositions. Har sentence par ek fixed checklist use karo.");

        // GK / GS
        add("History",
            "History ko Ancient, Medieval aur Modern phases me padho. Ancient: Indus Valley, Vedic age, Mahajanapadas, Buddhism/Jainism, Maurya, Gupta. Medieval: Delhi Sultanate, Mughal period, Bhakti-Sufi traditions. Modern: European arrival, British expansion, 1857, social reform, freedom movement, constitutional developments and independence. Dates ko events ke timeline se link karo.",
            "Example timeline: 1857 Revolt → 1885 INC formation → 1905 Bengal partition → 1919 Jallianwala Bagh/Rowlatt context → 1930 Civil Disobedience phase → 1942 Quit India → 1947 Independence.",
            "History ko isolated facts ki list mat banao. 'Event → year → leader/place → result' four-column revision karo.");

        add("Geography",
            "Geography me Physical, Indian aur Economic geography important hain. Earth ke latitude/longitude, motions, atmosphere, rocks, rivers, climate, soils, agriculture, minerals, industries aur population basics padho. India me Himalayas, Northern Plains, Peninsular Plateau, Coastal Plains, Islands aur monsoon system key areas hain.",
            "Indian monsoon ko pressure, winds, Arabian Sea branch aur Bay of Bengal branch ke flow ke saath map par samjho. Latitude equator se north/south angular distance hoti hai.",
            "Map-based learning karo. River→origin→tributary→states→mouth ka chain yaad karo; facts ko map se anchor karo.");

        add("Polity",
            "Indian Polity ke core topics: Constitution, Preamble, Fundamental Rights, Directive Principles, Fundamental Duties, Union/State government, Parliament, President, Prime Minister, Judiciary, Constitutional bodies, elections and local government. Articles ko topic ke saath pair karke padho, random numbers ke roop me nahi.",
            "Example: Fundamental Rights Constitution ke Part III me hain. Parliament ke do Houses Lok Sabha aur Rajya Sabha hain. President constitutional head of Union executive hai, jabki Council of Ministers PM ke leadership me aid and advise karti hai.",
            "Polity me 'body + composition + term + power + constitutional basis' ka template banao. Article numbers ko repeated revision se fix karo.");

        add("Economy",
            "Economy basics: GDP, inflation, unemployment, fiscal policy, monetary policy, taxation, banking, money, budget, balance of payments aur development indicators. RBI monetary policy se liquidity/interest conditions influence karta hai; government fiscal policy me taxation aur expenditure important hain.",
            "Inflation ka simple meaning general price level ka sustained rise hai. GDP ek specified period me final goods/services ki production ka monetary measure hai.",
            "Definition + example + effect ke 3 boxes banao. Terms ko confuse na karo: fiscal→government revenue/spending; monetary→money/credit conditions.");

        add("Physics",
            "Physics basics: units and dimensions, motion, force, work-energy-power, gravitation, heat, sound, light, electricity and magnetism. Newton's laws, Ohm's law V=IR, power P=VI, work W=Fs (same direction case), kinetic energy 1/2 mv² important formulas hain.",
            "If V=12V and R=4Ω, current I=V/R=3A. 10 N force se 5 m same direction displacement ho to work=50 J.",
            "Formula ke saath SI unit yaad karo. Numerical me given→formula→substitution→unit ka four-step method use karo.");

        add("Chemistry",
            "Chemistry basics: matter, atoms, molecules, elements/compounds/mixtures, periodic table, chemical bonding, acids-bases-salts, metals/non-metals, reactions, carbon compounds and everyday chemistry. Atomic number = protons; neutral atom me electrons = protons.",
            "NaCl ek compound hai. Acid blue litmus ko red karta hai; base red litmus ko blue karta hai. HCl + NaOH → NaCl + H2O neutralisation example hai.",
            "Concept ko daily-life example se link karo. Reaction questions me reactants, products aur reaction type identify karo.");

        add("Biology",
            "Biology me cell, tissues, plant physiology, human digestive/respiratory/circulatory/excretory systems, nervous system, hormones, reproduction, genetics, diseases, nutrition and ecology important hain. Cell basic structural and functional unit hai. DNA genetic information carry karta hai.",
            "RBC mainly oxygen transport karta hai through haemoglobin. Photosynthesis me plants light energy ki help se CO2 aur water se glucose banate hain aur oxygen release karte hain.",
            "Human body ko system-wise padho: organ→function→key fact. Vitamins/diseases ke liye deficiency→symptom/source table banao.");
    }

    private static void add(String key, String theory, String example, String trick) {
        DATA.put(key, new Lesson(theory, example, trick));
    }

    public static Lesson get(String topic) {
        Lesson l = DATA.get(topic);
        if (l != null) return l;
        return new Lesson(
            "Is topic ke liye lesson framework available hai: concept → rule/formula → solved example → practice → PYQ → revision. Is screen par topic-specific content add kiya jayega.",
            "Topic ke basic rule ko identify karke ek solved example practice karo.",
            "Pehle accuracy build karo, phir same question type ko timer ke saath solve karo."
        );
    }
}
