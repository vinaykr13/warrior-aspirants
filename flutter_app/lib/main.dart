import 'dart:math' as math;
import 'package:flutter/material.dart';
import 'package:file_picker/file_picker.dart';
import 'package:url_launcher/url_launcher.dart';

void main() => runApp(const WarriorAspirantApp());

class AppColors {
  static const navy = Color(0xFF0A0E17);
  static const navy2 = Color(0xFF0F172A);
  static const surface = Color(0xFF161F30);
  static const border = Color(0xFF334155);
  static const gold = Color(0xFFF59E0B);
  static const goldDark = Color(0xFFD97706);
  static const cyan = Color(0xFF38BDF8);
  static const orange = Color(0xFFF97316);
  static const white = Color(0xFFF8FAFC);
  static const muted = Color(0xFF94A3B8);
}

class WarriorAspirantApp extends StatelessWidget {
  const WarriorAspirantApp({super.key});
  @override
  Widget build(BuildContext context) => MaterialApp(
    title: 'Warrior Aspirant',
    debugShowCheckedModeBanner: false,
    theme: ThemeData(
      brightness: Brightness.dark,
      scaffoldBackgroundColor: AppColors.navy,
      fontFamily: 'Inter',
      useMaterial3: true,
      colorScheme: ColorScheme.fromSeed(seedColor: AppColors.gold, brightness: Brightness.dark),
    ),
    home: const WarriorShell(),
  );
}

class WarriorShell extends StatefulWidget {
  const WarriorShell({super.key});
  @override State<WarriorShell> createState() => _WarriorShellState();
}

class _WarriorShellState extends State<WarriorShell> with SingleTickerProviderStateMixin {
  int index = 0;
  late final AnimationController entry;
  @override void initState() {
    super.initState();
    entry = AnimationController(vsync: this, duration: const Duration(milliseconds: 900))..forward();
  }
  @override void dispose() { entry.dispose(); super.dispose(); }
  @override Widget build(BuildContext context) {
    final pages = <Widget>[
      HomePage(animation: entry),
      const SimplePage(title: 'Arena', subtitle: 'Tests & Mock Battles', icon: Icons.sports_martial_arts_rounded),
      const SimplePage(title: 'Armory', subtitle: 'Notes, formulas & saved material', icon: Icons.inventory_2_rounded),
      const SimplePage(title: 'Leaderboard', subtitle: 'Track your warrior progress', icon: Icons.emoji_events_rounded),
      const SimplePage(title: 'Profile', subtitle: 'Your CGL preparation profile', icon: Icons.person_rounded),
    ];
    return Scaffold(
      body: IndexedStack(index: index, children: pages),
      bottomNavigationBar: PremiumBottomNav(index: index, onChanged: (value) {
        setState(() => index = value);
        entry..reset()..forward();
      }),
    );
  }
}

class HomePage extends StatelessWidget {
  final AnimationController animation;
  const HomePage({super.key, required this.animation});

  Animation<double> fade(int step) => CurvedAnimation(
    parent: animation,
    curve: Interval(
      math.min(.05 + step * .10, .72),
      math.min(.48 + step * .10, .98),
      curve: Curves.easeOutCubic,
    ),
  );

  Widget entry(int step, Widget child) {
    final a = fade(step);
    return FadeTransition(
      opacity: a,
      child: SlideTransition(
        position: Tween(begin: const Offset(0, .08), end: Offset.zero).animate(a),
        child: child,
      ),
    );
  }

  Widget section(Widget child) => SliverPadding(
    padding: const EdgeInsets.fromLTRB(18, 16, 18, 0),
    sliver: SliverToBoxAdapter(child: child),
  );

  @override
  Widget build(BuildContext context) => SafeArea(
    child: CustomScrollView(
      physics: const BouncingScrollPhysics(),
      slivers: [
        section(entry(0, const WarriorTopBar())),
        section(entry(1, const WarriorPassCard())),
        section(entry(2, const SectionHeader(title: 'Strategic Arsenal', subtitle: 'Master the core CGL subjects'))),
        section(entry(3, const ArsenalGrid())),
        section(entry(4, const SectionHeader(title: "Warrior's Training Ground", subtitle: 'Challenge yourself with timed battles'))),
        SliverPadding(
          padding: const EdgeInsets.fromLTRB(18, 0, 18, 28),
          sliver: SliverToBoxAdapter(child: entry(5, const MegaMockCard())),
        ),
      ],
    ),
  );
}

class WarriorTopBar extends StatelessWidget {
  const WarriorTopBar({super.key});
  @override
  Widget build(BuildContext context) => Row(
    children: [
      Container(
        width: 50, height: 50,
        decoration: BoxDecoration(
          gradient: const LinearGradient(colors: [AppColors.gold, AppColors.goldDark]),
          borderRadius: BorderRadius.circular(16),
          boxShadow: [BoxShadow(color: AppColors.gold.withValues(alpha: .22), blurRadius: 18)],
        ),
        child: const Icon(Icons.shield_rounded, color: AppColors.navy, size: 28),
      ),
      const SizedBox(width: 12),
      const Expanded(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text('WARRIOR ASPIRANT', style: TextStyle(color: AppColors.gold, fontSize: 11, fontWeight: FontWeight.w800, letterSpacing: 1.5)),
            SizedBox(height: 4),
            Text('Hello, Aathvik!', style: TextStyle(color: AppColors.white, fontSize: 20, fontWeight: FontWeight.w800)),
            SizedBox(height: 2),
            Text('Target CGL', style: TextStyle(color: AppColors.muted, fontSize: 12)),
          ],
        ),
      ),
      PressableScale(
        onTap: _showNotification,
        child: Container(
          width: 48, height: 48,
          decoration: BoxDecoration(color: AppColors.surface, borderRadius: BorderRadius.circular(15), border: Border.all(color: AppColors.border)),
          child: const Icon(Icons.notifications_none_rounded, color: AppColors.white),
        ),
      ),
    ],
  );

  static void _showNotification() {}
}

class WarriorPassCard extends StatefulWidget {
  const WarriorPassCard({super.key});
  @override State<WarriorPassCard> createState() => _WarriorPassCardState();
}

class _WarriorPassCardState extends State<WarriorPassCard> with SingleTickerProviderStateMixin {
  late final AnimationController pulse;
  @override void initState() {
    super.initState();
    pulse = AnimationController(vsync: this, duration: const Duration(milliseconds: 1900))..repeat(reverse: true);
  }
  @override void dispose() { pulse.dispose(); super.dispose(); }
  @override Widget build(BuildContext context) => AnimatedBuilder(
    animation: pulse,
    builder: (context, child) => Container(
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(22),
        boxShadow: [BoxShadow(color: AppColors.gold.withValues(alpha: .10 + pulse.value * .10), blurRadius: 8 + pulse.value * 12)],
      ),
      child: child,
    ),
    child: Container(
      padding: const EdgeInsets.all(18),
      decoration: BoxDecoration(
        gradient: const LinearGradient(colors: [Color(0xFF202D43), Color(0xFF0D1B32)]),
        borderRadius: BorderRadius.circular(22),
        border: Border.all(color: AppColors.border),
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Row(children: [
            Container(
              padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 7),
              decoration: BoxDecoration(
                color: AppColors.gold.withValues(alpha: .13),
                borderRadius: BorderRadius.circular(11),
                border: Border.all(color: AppColors.gold.withValues(alpha: .30)),
              ),
              child: const Row(mainAxisSize: MainAxisSize.min, children: [
                Icon(Icons.workspace_premium_rounded, color: AppColors.gold, size: 16),
                SizedBox(width: 6),
                Text('WARRIOR OF THE WEEK', style: TextStyle(color: AppColors.gold, fontSize: 9, fontWeight: FontWeight.w900, letterSpacing: .8)),
              ]),
            ),
            const Spacer(),
            const Text('LEVEL 12', style: TextStyle(color: AppColors.cyan, fontSize: 11, fontWeight: FontWeight.w800)),
          ]),
          const SizedBox(height: 18),
          const Row(crossAxisAlignment: CrossAxisAlignment.end, children: [
            Text('78%', style: TextStyle(color: AppColors.white, fontSize: 34, fontWeight: FontWeight.w900)),
            SizedBox(width: 8),
            Padding(padding: EdgeInsets.only(bottom: 6), child: Text('DONE', style: TextStyle(color: AppColors.muted, fontSize: 11, fontWeight: FontWeight.w800, letterSpacing: 1))),
            Spacer(),
            Text('1,240 XP', style: TextStyle(color: AppColors.gold, fontSize: 12, fontWeight: FontWeight.w800)),
          ]),
          const SizedBox(height: 11),
          ClipRRect(
            borderRadius: const BorderRadius.all(Radius.circular(99)),
            child: const LinearProgressIndicator(
              value: .78, minHeight: 8,
              backgroundColor: Color(0xFF263449),
              valueColor: AlwaysStoppedAnimation(AppColors.gold),
            ),
          ),
          const SizedBox(height: 10),
          const Row(children: [
            Icon(Icons.bolt_rounded, color: AppColors.orange, size: 17),
            SizedBox(width: 5),
            Text('Remaining MCQs', style: TextStyle(color: AppColors.muted, fontSize: 12)),
            Spacer(),
            Text('220', style: TextStyle(color: AppColors.white, fontSize: 12, fontWeight: FontWeight.w800)),
          ]),
        ],
      ),
    ),
  );
}

class SectionHeader extends StatelessWidget {
  final String title, subtitle;
  const SectionHeader({super.key, required this.title, required this.subtitle});
  @override Widget build(BuildContext context) => Padding(
    padding: const EdgeInsets.only(top: 8, bottom: 10),
    child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
      Text(title, style: const TextStyle(color: AppColors.white, fontSize: 19, fontWeight: FontWeight.w900)),
      const SizedBox(height: 4),
      Text(subtitle, style: const TextStyle(color: AppColors.muted, fontSize: 12)),
    ]),
  );
}

class Subject {
  final String name, chapters;
  final IconData icon;
  final Color color;
  const Subject(this.name, this.chapters, this.icon, this.color);
}

const subjects = [
  Subject('Quantitative Maths', '28 Chapters', Icons.calculate_rounded, AppColors.cyan),
  Subject('Reasoning Ability', '24 Chapters', Icons.psychology_rounded, Color(0xFFA78BFA)),
  Subject('General Awareness', '32 Chapters', Icons.public_rounded, AppColors.orange),
  Subject('English Comprehension', '26 Chapters', Icons.menu_book_rounded, AppColors.gold),
];

class ArsenalGrid extends StatelessWidget {
  const ArsenalGrid({super.key});
  @override Widget build(BuildContext context) => GridView.builder(
    shrinkWrap: true,
    physics: const NeverScrollableScrollPhysics(),
    itemCount: subjects.length,
    gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
      crossAxisCount: 2, crossAxisSpacing: 12, mainAxisSpacing: 12, childAspectRatio: 1.14,
    ),
    itemBuilder: (context, index) {
      final subject = subjects[index];
      return PressableScale(
        onTap: () => Navigator.push(context, MaterialPageRoute(builder: (_) => SimplePage(title: subject.name, subtitle: 'Theory + Practice', icon: subject.icon))),
        child: Container(
          padding: const EdgeInsets.all(15),
          decoration: BoxDecoration(
            color: AppColors.surface,
            borderRadius: BorderRadius.circular(20),
            border: Border.all(color: AppColors.border),
          ),
          child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
            Container(
              width: 43, height: 43,
              decoration: BoxDecoration(
                color: subject.color.withValues(alpha: .12),
                borderRadius: BorderRadius.circular(13),
                border: Border.all(color: subject.color.withValues(alpha: .24)),
              ),
              child: Icon(subject.icon, color: subject.color),
            ),
            const Spacer(),
            Text(subject.name, maxLines: 2, overflow: TextOverflow.ellipsis, style: const TextStyle(color: AppColors.white, fontSize: 14, fontWeight: FontWeight.w800)),
            const SizedBox(height: 5),
            Text(subject.chapters, style: const TextStyle(color: AppColors.muted, fontSize: 10, fontWeight: FontWeight.w600)),
          ]),
        ),
      );
    },
  );
}

class MegaMockCard extends StatelessWidget {
  const MegaMockCard({super.key});
  @override Widget build(BuildContext context) => Container(
    padding: const EdgeInsets.all(18),
    decoration: BoxDecoration(color: AppColors.surface, borderRadius: BorderRadius.circular(22), border: Border.all(color: AppColors.border)),
    child: Column(children: [
      Row(children: [
        Container(
          width: 46, height: 46,
          decoration: BoxDecoration(
            gradient: const LinearGradient(colors: [AppColors.orange, AppColors.goldDark]),
            borderRadius: BorderRadius.circular(14),
          ),
          child: const Icon(Icons.flash_on_rounded, color: AppColors.navy),
        ),
        const SizedBox(width: 12),
        const Expanded(child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
          Row(children: [LiveBadge(), SizedBox(width: 8), Text('CGL TIER-1', style: TextStyle(color: AppColors.muted, fontSize: 9, fontWeight: FontWeight.w900, letterSpacing: 1))]),
          SizedBox(height: 6),
          Text('Mega Mock Battle', style: TextStyle(color: AppColors.white, fontSize: 18, fontWeight: FontWeight.w900)),
        ])),
      ]),
      const SizedBox(height: 18),
      const Row(children: [
        Icon(Icons.timer_outlined, color: AppColors.cyan, size: 17),
        SizedBox(width: 5),
        Text('60 min', style: TextStyle(color: AppColors.muted, fontSize: 11)),
        SizedBox(width: 18),
        Icon(Icons.quiz_outlined, color: AppColors.cyan, size: 17),
        SizedBox(width: 5),
        Text('100 Questions', style: TextStyle(color: AppColors.muted, fontSize: 11)),
        Spacer(),
        Text('+500 XP', style: TextStyle(color: AppColors.gold, fontSize: 11, fontWeight: FontWeight.w900)),
      ]),
      const SizedBox(height: 18),
      GlowingTestButton(onTap: () {
        Navigator.push(context, MaterialPageRoute(builder: (_) => const SimplePage(title: 'CGL Tier-1 Mega Mock', subtitle: '100 Questions • 60 Minutes', icon: Icons.timer)));
      }),
    ]),
  );
}

class LiveBadge extends StatelessWidget {
  const LiveBadge({super.key});
  @override Widget build(BuildContext context) => Container(
    padding: const EdgeInsets.symmetric(horizontal: 7, vertical: 4),
    decoration: BoxDecoration(
      color: AppColors.orange.withValues(alpha: .12),
      borderRadius: BorderRadius.circular(7),
      border: Border.all(color: AppColors.orange.withValues(alpha: .35)),
    ),
    child: const Text('LIVE', style: TextStyle(color: AppColors.orange, fontSize: 8, fontWeight: FontWeight.w900)),
  );
}

class GlowingTestButton extends StatefulWidget {
  final VoidCallback onTap;
  const GlowingTestButton({super.key, required this.onTap});
  @override State<GlowingTestButton> createState() => _GlowingTestButtonState();
}

class _GlowingTestButtonState extends State<GlowingTestButton> with SingleTickerProviderStateMixin {
  late final AnimationController shimmer;
  @override void initState() {
    super.initState();
    shimmer = AnimationController(vsync: this, duration: const Duration(milliseconds: 1800))..repeat();
  }
  @override void dispose() { shimmer.dispose(); super.dispose(); }
  @override Widget build(BuildContext context) => AnimatedBuilder(
    animation: shimmer,
    builder: (context, child) => CustomPaint(painter: GlowBorderPainter(shimmer.value), child: child),
    child: InkWell(
      onTap: widget.onTap,
      borderRadius: BorderRadius.circular(15),
      child: Container(
        height: 50,
        alignment: Alignment.center,
        decoration: BoxDecoration(color: AppColors.navy2, borderRadius: BorderRadius.circular(15)),
        child: const Row(mainAxisAlignment: MainAxisAlignment.center, children: [
          Text('TAKE TEST', style: TextStyle(color: AppColors.gold, fontSize: 12, fontWeight: FontWeight.w900, letterSpacing: 1.2)),
          SizedBox(width: 8),
          Icon(Icons.arrow_forward_rounded, color: AppColors.gold, size: 18),
        ]),
      ),
    ),
  );
}

class GlowBorderPainter extends CustomPainter {
  final double progress;
  GlowBorderPainter(this.progress);
  @override void paint(Canvas canvas, Size size) {
    final rect = RRect.fromRectAndRadius(Offset.zero & size, const Radius.circular(15));
    final paint = Paint()
      ..style = PaintingStyle.stroke
      ..strokeWidth = 1.5
      ..shader = SweepGradient(
        transform: GradientRotation(progress * math.pi * 2),
        colors: const [AppColors.goldDark, AppColors.cyan, AppColors.gold, AppColors.goldDark],
      ).createShader(Offset.zero & size);
    canvas.drawRRect(rect, paint);
  }
  @override bool shouldRepaint(covariant GlowBorderPainter oldDelegate) => oldDelegate.progress != progress;
}

class PressableScale extends StatefulWidget {
  final Widget child;
  final VoidCallback onTap;
  const PressableScale({super.key, required this.child, required this.onTap});
  @override State<PressableScale> createState() => _PressableScaleState();
}

class _PressableScaleState extends State<PressableScale> {
  bool pressed = false;
  @override Widget build(BuildContext context) => GestureDetector(
    behavior: HitTestBehavior.opaque,
    onTapDown: (_) => setState(() => pressed = true),
    onTapCancel: () => setState(() => pressed = false),
    onTapUp: (_) { setState(() => pressed = false); widget.onTap(); },
    child: AnimatedScale(scale: pressed ? .965 : 1, duration: const Duration(milliseconds: 100), child: widget.child),
  );
}

class PremiumBottomNav extends StatelessWidget {
  final int index;
  final ValueChanged<int> onChanged;
  const PremiumBottomNav({super.key, required this.index, required this.onChanged});

  static const labels = ['Home', 'Arena', 'Armory', 'Leaderboard', 'Profile'];
  static const icons = [
    Icons.home_rounded,
    Icons.sports_martial_arts_rounded,
    Icons.inventory_2_rounded,
    Icons.leaderboard_rounded,
    Icons.person_rounded,
  ];

  @override
  Widget build(BuildContext context) => Container(
    decoration: const BoxDecoration(
      color: Color(0xFF0C1320),
      border: Border(top: BorderSide(color: AppColors.border)),
    ),
    child: SafeArea(
      top: false,
      child: Padding(
        padding: const EdgeInsets.all(8),
        child: Row(
          children: List.generate(labels.length, (i) {
            final active = i == index;
            return Expanded(
              child: PressableScale(
                onTap: () => onChanged(i),
                child: AnimatedContainer(
                  duration: const Duration(milliseconds: 260),
                  curve: Curves.easeOutCubic,
                  margin: const EdgeInsets.symmetric(horizontal: 3),
                  padding: const EdgeInsets.symmetric(vertical: 7),
                  decoration: BoxDecoration(
                    color: active ? AppColors.gold.withValues(alpha: .10) : Colors.transparent,
                    borderRadius: BorderRadius.circular(15),
                    border: Border.all(
                      color: active ? AppColors.gold.withValues(alpha: .28) : Colors.transparent,
                    ),
                  ),
                  child: Column(mainAxisSize: MainAxisSize.min, children: [
                    Icon(icons[i], color: active ? AppColors.gold : AppColors.muted, size: 21),
                    const SizedBox(height: 3),
                    Text(
                      labels[i],
                      maxLines: 1,
                      overflow: TextOverflow.ellipsis,
                      style: TextStyle(
                        color: active ? AppColors.gold : AppColors.muted,
                        fontSize: 9,
                        fontWeight: active ? FontWeight.w900 : FontWeight.w600,
                      ),
                    ),
                  ]),
                ),
              ),
            );
          }),
        ),
      ),
    ),
  );
}

class SimplePage extends StatefulWidget {
  final String title, subtitle; final IconData icon;
  const SimplePage({super.key,required this.title,required this.subtitle,required this.icon});
  @override State<SimplePage> createState()=>_SimplePageState();
}
class _SimplePageState extends State<SimplePage>{
  String? fileName; int answer=-1;
  final pet=[
    ['भारतीय इतिहास','5 प्रश्न • 5 अंक','सिन्धु घाटी सभ्यता; वैदिक संस्कृति; बौद्ध धर्म; जैन धर्म; मौर्य वंश; अशोक; गुप्त वंश; समुद्रगुप्त; चन्द्रगुप्त द्वितीय; हर्षवर्धन; राजपूत; सल्तनत; मुगल; मराठा; ब्रिटिश राज; प्रथम स्वतंत्रता संग्राम; सामाजिक एवं आर्थिक प्रभाव।'],
    ['भारतीय राष्ट्रीय आन्दोलन','5 प्रश्न • 5 अंक','प्रारम्भिक आन्दोलन; स्वदेशी; सविनय अवज्ञा; महात्मा गांधी; क्रान्तिकारी आन्दोलन; उग्र राष्ट्रवाद; भारत सरकार अधिनियम 1935; भारत छोड़ो; आजाद हिन्द फौज; सुभाष चन्द्र बोस।'],
    ['भूगोल','5 प्रश्न • 5 अंक','भारत/विश्व का भौतिक व राजनीतिक भूगोल; नदियाँ; नदी घाटियाँ; भूजल; पर्वत; पहाड़ियाँ; हिमनद; मरुस्थल; वन; खनिज; जलवायु; मौसम; टाइम ज़ोन; जनसांख्यिकी; प्रवासन।'],
    ['भारतीय अर्थव्यवस्था','5 प्रश्न • 5 अंक','1947–1991 अर्थव्यवस्था; योजना आयोग; पंचवर्षीय योजनाएँ; मिश्रित अर्थव्यवस्था; हरित क्रान्ति; ऑपरेशन फ्लड; बैंक राष्ट्रीयकरण; 1991 सुधार; 2014 के बाद कृषि, ढांचागत व श्रम सुधार; GST।'],
    ['भारतीय संविधान एवं लोक प्रशासन','5 प्रश्न • 5 अंक','संविधान; नीति-निर्देशक तत्व; मौलिक अधिकार/कर्तव्य; संसदीय प्रणाली; संघीय प्रणाली; संघ/UT; केन्द्र-राज्य सम्बन्ध; सर्वोच्च/उच्च न्यायालय; जिला प्रशासन; स्थानीय निकाय; पंचायती राज।'],
    ['सामान्य विज्ञान','5 प्रश्न • 5 अंक','प्रारम्भिक Physics; Chemistry; Biology।'],
    ['प्रारम्भिक अंकगणित','5 प्रश्न • 5 अंक','पूर्ण संख्याएँ; भिन्न/दशमलव; प्रतिशतता; साधारण समीकरण; वर्ग/वर्गमूल; घातांक/घात; औसत।'],
    ['सामान्य हिन्दी','5 प्रश्न • 5 अंक','संधि; विलोम; पर्यायवाची; एक शब्द; लिंग; समश्रुत भिन्नार्थक शब्द; मुहावरे/लोकोक्तियाँ; अशुद्धियाँ; लेखक एवं रचनाएँ।'],
    ['सामान्य अंग्रेजी','5 प्रश्न • 5 अंक','English Grammar; Unseen Passage।'],
    ['तर्क एवं तर्कशक्ति','5 प्रश्न • 5 अंक','बड़ा/छोटा; क्रम/रैंकिंग; सम्बन्ध; odd-one-out; कैलेंडर/घड़ी; कारण-प्रभाव; Coding-Decoding; कथन विश्लेषण; निष्कर्ष।'],
    ['समसामयिकी','10 प्रश्न • 10 अंक','भारतीय और वैश्विक समसामयिकी; date-wise, weekly और monthly revision।'],
    ['सामान्य जागरूकता','10 प्रश्न • 10 अंक','पड़ोसी देश; देश-राजधानी-मुद्रा; राज्य/UT; संसद; महत्वपूर्ण दिवस; विश्व संगठन/मुख्यालय; पर्यटन; कला-संस्कृति; खेल; अनुसंधान संगठन; पुस्तक-लेखक; पुरस्कार; जलवायु/पर्यावरण।'],
    ['हिन्दी अपठित गद्यांश','10 प्रश्न • 10 अंक','2 अपठित हिन्दी गद्यांश; प्रत्येक पर 5 प्रश्न; आशय, तथ्य, शब्दार्थ और निष्कर्ष।'],
    ['ग्राफ की व्याख्या एवं विश्लेषण','10 प्रश्न • 10 अंक','2 ग्राफ; प्रत्येक पर 5 प्रश्न; data reading, comparison और conclusion।'],
    ['तालिका की व्याख्या एवं विश्लेषण','10 प्रश्न • 10 अंक','2 तालिकाएँ; प्रत्येक पर 5 प्रश्न; rows/columns, comparison और conclusion।']
  ];
  final subjects={
    'Quantitative Maths':['Number System','Percentage','Ratio & Proportion','Profit & Loss','Average','Time & Work','Time Speed Distance','Algebra','Geometry','Mensuration','Trigonometry','Data Interpretation'],
    'Reasoning Ability':['Analogy','Classification','Series','Coding-Decoding','Blood Relation','Direction','Ranking','Syllogism','Venn Diagram','Clock & Calendar','Statement & Conclusion','Non-Verbal Reasoning'],
    'English Comprehension':['Parts of Speech','Tenses','Subject Verb Agreement','Articles','Prepositions','Voice','Narration','Error Detection','Cloze Test','Reading Comprehension','Synonyms & Antonyms','One Word Substitution'],
    'General Awareness':['History','Geography','Polity','Economy','Science','Static GK','Art & Culture','Sports','Books & Authors','Awards','Environment','Current Affairs']
  };
  @override Widget build(BuildContext context)=>Scaffold(appBar:AppBar(title:Text(widget.title),backgroundColor:AppColors.navy),body:body());
  Widget body(){
    if(widget.title=='UPSSSC PET')return petPage();
    if(widget.title=='Notes & PDFs')return notesPage();
    if(widget.title=='Fighter AI')return fighterPage();
    if(widget.title=='Videos')return videosPage();
    if(widget.title=='Current Affairs'||widget.title=='Vacancies & Updates')return updatesPage();
    if(widget.title=='PYQ Bank'||widget.title.contains('Mock')||widget.title.contains('Practice')||widget.title.contains('Challenge')||widget.title=='Subject Test')return quizPage();
    return studyPage();
  }
  Widget studyPage()=>ListView(padding:const EdgeInsets.all(16),children:[
    const Text('Basic → Advanced',style:TextStyle(color:AppColors.gold,fontSize:22,fontWeight:FontWeight.w900)),
    const Text('Theory • formulas • examples • important topics • memory tricks • PYQs',style:TextStyle(color:AppColors.muted)),
    const SizedBox(height:14),
    for(final e in subjects.entries)Card(color:AppColors.surface,child:ExpansionTile(title:Text(e.key,style:const TextStyle(color:AppColors.white,fontWeight:FontWeight.w800)),children:[
      for(final t in e.value)ListTile(title:Text(t,style:const TextStyle(color:AppColors.white)),subtitle:const Text('Theory + example + shortcut + PYQ + practice',style:TextStyle(color:AppColors.muted,fontSize:10)),onTap:()=>lesson(t))
    ]))
  ]);
  void lesson(String t)=>showModalBottomSheet(context:context,backgroundColor:AppColors.surface,builder:(_)=>Padding(padding:const EdgeInsets.all(20),child:Column(mainAxisSize:MainAxisSize.min,crossAxisAlignment:CrossAxisAlignment.start,children:[
    Text(t,style:const TextStyle(color:AppColors.gold,fontSize:22,fontWeight:FontWeight.w900)),const SizedBox(height:12),
    const Text('THEORY',style:TextStyle(color:AppColors.cyan,fontWeight:FontWeight.w900)),
    Text(t+' को basic से advanced तक पढ़ें: definition → rule/formula → solved example → shortcut → PYQ → practice.',style:const TextStyle(color:AppColors.white,height:1.5)),
    const SizedBox(height:12),const Text('IMPORTANT',style:TextStyle(color:AppColors.orange,fontWeight:FontWeight.w900)),
    const Text('Frequently tested concepts, common traps और revision points को mark करके दोबारा करें.',style:TextStyle(color:AppColors.muted)),const SizedBox(height:12)
  ])));
  Widget petPage()=>ListView(padding:const EdgeInsets.all(14),children:[
    const Text('UPSSSC PET — Complete Syllabus',style:TextStyle(color:AppColors.gold,fontSize:22,fontWeight:FontWeight.w900)),
    const Text('15/15 sections • theory • important topics • practice',style:TextStyle(color:AppColors.muted)),const SizedBox(height:10),
    for(int i=0;i<pet.length;i++)Card(color:AppColors.surface,child:ExpansionTile(title:Text((i+1).toString()+'. '+pet[i][0],style:const TextStyle(color:AppColors.white,fontWeight:FontWeight.w800)),subtitle:Text(pet[i][1],style:const TextStyle(color:AppColors.gold,fontSize:10)),children:[
      Padding(padding:const EdgeInsets.all(15),child:Text(pet[i][2],style:const TextStyle(color:AppColors.white,height:1.5)))
    ]))
  ]);
  Widget quizPage(){
    final data=<List<Object>>[
      <Object>['15 का 20% कितना है?',<String>['2','3','4','5'],'3'],
      <Object>['यदि 3x=21, x=?',<String>['5','6','7','8'],'7'],
      <Object>['भारत का संविधान कब लागू हुआ?',<String>['1947','1949','1950','1952'],'1950']
    ];
    final q=data[DateTime.now().second%data.length];
    final question=q[0] as String;
    final options=q[1] as List<String>;
    final correct=q[2] as String;
    return ListView(
      padding:const EdgeInsets.all(18),
      children:[
        Text(widget.title,style:const TextStyle(color:AppColors.gold,fontSize:22,fontWeight:FontWeight.w900)),
        const Text('Practice/PYQ engine • answer • explanation • progress',style:TextStyle(color:AppColors.muted)),
        const SizedBox(height:18),
        Container(
          padding:const EdgeInsets.all(14),
          decoration:BoxDecoration(color:AppColors.surface,borderRadius:BorderRadius.circular(18),border:Border.all(color:AppColors.border)),
          child:Column(
            crossAxisAlignment:CrossAxisAlignment.start,
            children:[
              Text(question,style:const TextStyle(color:AppColors.white,fontSize:19,fontWeight:FontWeight.w800)),
              const SizedBox(height:10),
              for(int i=0;i<options.length;i++)
                ListTile(
                  onTap:()=>setState(()=>answer=i),
                  leading:Icon(answer==i?Icons.radio_button_checked:Icons.radio_button_off,color:answer==i?AppColors.gold:AppColors.muted),
                  title:Text(options[i],style:const TextStyle(color:AppColors.white))
                ),
              if(answer>=0)
                Text(
                  answer==options.indexOf(correct)?'✓ Correct — concept applied correctly.':'✗ Correct answer: '+correct,
                  style:TextStyle(color:answer==options.indexOf(correct)?Colors.green:AppColors.orange,fontWeight:FontWeight.w800)
                ),
              const SizedBox(height:10),
              ElevatedButton(
                onPressed:answer<0?null:()=>setState(()=>answer=-1),
                child:const Text('SUBMIT & CONTINUE')
              )
            ]
          )
        )
      ]
    );
  }
import 'dart:math' as math;
import 'package:flutter/material.dart';
import 'package:file_picker/file_picker.dart';
import 'package:url_launcher/url_launcher.dart';

void main() => runApp(const WarriorAspirantApp());

class AppColors {
  static const navy = Color(0xFF0A0E17);
  static const navy2 = Color(0xFF0F172A);
  static const surface = Color(0xFF161F30);
  static const border = Color(0xFF334155);
  static const gold = Color(0xFFF59E0B);
  static const goldDark = Color(0xFFD97706);
  static const cyan = Color(0xFF38BDF8);
  static const orange = Color(0xFFF97316);
  static const white = Color(0xFFF8FAFC);
  static const muted = Color(0xFF94A3B8);
}

class WarriorAspirantApp extends StatelessWidget {
  const WarriorAspirantApp({super.key});
  @override
  Widget build(BuildContext context) => MaterialApp(
    title: 'Warrior Aspirant',
    debugShowCheckedModeBanner: false,
    theme: ThemeData(
      brightness: Brightness.dark,
      scaffoldBackgroundColor: AppColors.navy,
      fontFamily: 'Inter',
      useMaterial3: true,
      colorScheme: ColorScheme.fromSeed(seedColor: AppColors.gold, brightness: Brightness.dark),
    ),
    home: const WarriorShell(),
  );
}

class WarriorShell extends StatefulWidget {
  const WarriorShell({super.key});
  @override State<WarriorShell> createState() => _WarriorShellState();
}

class _WarriorShellState extends State<WarriorShell> with SingleTickerProviderStateMixin {
  int index = 0;
  late final AnimationController entry;
  @override void initState() {
    super.initState();
    entry = AnimationController(vsync: this, duration: const Duration(milliseconds: 900))..forward();
  }
  @override void dispose() { entry.dispose(); super.dispose(); }
  @override Widget build(BuildContext context) {
    final pages = <Widget>[
      HomePage(animation: entry),
      const SimplePage(title: 'Arena', subtitle: 'Tests & Mock Battles', icon: Icons.sports_martial_arts_rounded),
      const SimplePage(title: 'Armory', subtitle: 'Notes, formulas & saved material', icon: Icons.inventory_2_rounded),
      const SimplePage(title: 'Leaderboard', subtitle: 'Track your warrior progress', icon: Icons.emoji_events_rounded),
      const SimplePage(title: 'Profile', subtitle: 'Your CGL preparation profile', icon: Icons.person_rounded),
    ];
    return Scaffold(
      body: IndexedStack(index: index, children: pages),
      bottomNavigationBar: PremiumBottomNav(index: index, onChanged: (value) {
        setState(() => index = value);
        entry..reset()..forward();
      }),
    );
  }
}

class HomePage extends StatelessWidget {
  final AnimationController animation;
  const HomePage({super.key, required this.animation});

  Animation<double> fade(int step) => CurvedAnimation(
    parent: animation,
    curve: Interval(
      math.min(.05 + step * .10, .72),
      math.min(.48 + step * .10, .98),
      curve: Curves.easeOutCubic,
    ),
  );

  Widget entry(int step, Widget child) {
    final a = fade(step);
    return FadeTransition(
      opacity: a,
      child: SlideTransition(
        position: Tween(begin: const Offset(0, .08), end: Offset.zero).animate(a),
        child: child,
      ),
    );
  }

  Widget section(Widget child) => SliverPadding(
    padding: const EdgeInsets.fromLTRB(18, 16, 18, 0),
    sliver: SliverToBoxAdapter(child: child),
  );

  @override
  Widget build(BuildContext context) => SafeArea(
    child: CustomScrollView(
      physics: const BouncingScrollPhysics(),
      slivers: [
        section(entry(0, const WarriorTopBar())),
        section(entry(1, const WarriorPassCard())),
        section(entry(2, const SectionHeader(title: 'Strategic Arsenal', subtitle: 'Master the core CGL subjects'))),
        section(entry(3, const ArsenalGrid())),
        section(entry(4, const SectionHeader(title: "Warrior's Training Ground", subtitle: 'Challenge yourself with timed battles'))),
        SliverPadding(
          padding: const EdgeInsets.fromLTRB(18, 0, 18, 28),
          sliver: SliverToBoxAdapter(child: entry(5, const MegaMockCard())),
        ),
      ],
    ),
  );
}

class WarriorTopBar extends StatelessWidget {
  const WarriorTopBar({super.key});
  @override
  Widget build(BuildContext context) => Row(
    children: [
      Container(
        width: 50, height: 50,
        decoration: BoxDecoration(
          gradient: const LinearGradient(colors: [AppColors.gold, AppColors.goldDark]),
          borderRadius: BorderRadius.circular(16),
          boxShadow: [BoxShadow(color: AppColors.gold.withValues(alpha: .22), blurRadius: 18)],
        ),
        child: const Icon(Icons.shield_rounded, color: AppColors.navy, size: 28),
      ),
      const SizedBox(width: 12),
      const Expanded(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text('WARRIOR ASPIRANT', style: TextStyle(color: AppColors.gold, fontSize: 11, fontWeight: FontWeight.w800, letterSpacing: 1.5)),
            SizedBox(height: 4),
            Text('Hello, Aathvik!', style: TextStyle(color: AppColors.white, fontSize: 20, fontWeight: FontWeight.w800)),
            SizedBox(height: 2),
            Text('Target CGL', style: TextStyle(color: AppColors.muted, fontSize: 12)),
          ],
        ),
      ),
      PressableScale(
        onTap: _showNotification,
        child: Container(
          width: 48, height: 48,
          decoration: BoxDecoration(color: AppColors.surface, borderRadius: BorderRadius.circular(15), border: Border.all(color: AppColors.border)),
          child: const Icon(Icons.notifications_none_rounded, color: AppColors.white),
        ),
      ),
    ],
  );

  static void _showNotification() {}
}

class WarriorPassCard extends StatefulWidget {
  const WarriorPassCard({super.key});
  @override State<WarriorPassCard> createState() => _WarriorPassCardState();
}

class _WarriorPassCardState extends State<WarriorPassCard> with SingleTickerProviderStateMixin {
  late final AnimationController pulse;
  @override void initState() {
    super.initState();
    pulse = AnimationController(vsync: this, duration: const Duration(milliseconds: 1900))..repeat(reverse: true);
  }
  @override void dispose() { pulse.dispose(); super.dispose(); }
  @override Widget build(BuildContext context) => AnimatedBuilder(
    animation: pulse,
    builder: (context, child) => Container(
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(22),
        boxShadow: [BoxShadow(color: AppColors.gold.withValues(alpha: .10 + pulse.value * .10), blurRadius: 8 + pulse.value * 12)],
      ),
      child: child,
    ),
    child: Container(
      padding: const EdgeInsets.all(18),
      decoration: BoxDecoration(
        gradient: const LinearGradient(colors: [Color(0xFF202D43), Color(0xFF0D1B32)]),
        borderRadius: BorderRadius.circular(22),
        border: Border.all(color: AppColors.border),
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Row(children: [
            Container(
              padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 7),
              decoration: BoxDecoration(
                color: AppColors.gold.withValues(alpha: .13),
                borderRadius: BorderRadius.circular(11),
                border: Border.all(color: AppColors.gold.withValues(alpha: .30)),
              ),
              child: const Row(mainAxisSize: MainAxisSize.min, children: [
                Icon(Icons.workspace_premium_rounded, color: AppColors.gold, size: 16),
                SizedBox(width: 6),
                Text('WARRIOR OF THE WEEK', style: TextStyle(color: AppColors.gold, fontSize: 9, fontWeight: FontWeight.w900, letterSpacing: .8)),
              ]),
            ),
            const Spacer(),
            const Text('LEVEL 12', style: TextStyle(color: AppColors.cyan, fontSize: 11, fontWeight: FontWeight.w800)),
          ]),
          const SizedBox(height: 18),
          const Row(crossAxisAlignment: CrossAxisAlignment.end, children: [
            Text('78%', style: TextStyle(color: AppColors.white, fontSize: 34, fontWeight: FontWeight.w900)),
            SizedBox(width: 8),
            Padding(padding: EdgeInsets.only(bottom: 6), child: Text('DONE', style: TextStyle(color: AppColors.muted, fontSize: 11, fontWeight: FontWeight.w800, letterSpacing: 1))),
            Spacer(),
            Text('1,240 XP', style: TextStyle(color: AppColors.gold, fontSize: 12, fontWeight: FontWeight.w800)),
          ]),
          const SizedBox(height: 11),
          ClipRRect(
            borderRadius: const BorderRadius.all(Radius.circular(99)),
            child: const LinearProgressIndicator(
              value: .78, minHeight: 8,
              backgroundColor: Color(0xFF263449),
              valueColor: AlwaysStoppedAnimation(AppColors.gold),
            ),
          ),
          const SizedBox(height: 10),
          const Row(children: [
            Icon(Icons.bolt_rounded, color: AppColors.orange, size: 17),
            SizedBox(width: 5),
            Text('Remaining MCQs', style: TextStyle(color: AppColors.muted, fontSize: 12)),
            Spacer(),
            Text('220', style: TextStyle(color: AppColors.white, fontSize: 12, fontWeight: FontWeight.w800)),
          ]),
        ],
      ),
    ),
  );
}

class SectionHeader extends StatelessWidget {
  final String title, subtitle;
  const SectionHeader({super.key, required this.title, required this.subtitle});
  @override Widget build(BuildContext context) => Padding(
    padding: const EdgeInsets.only(top: 8, bottom: 10),
    child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
      Text(title, style: const TextStyle(color: AppColors.white, fontSize: 19, fontWeight: FontWeight.w900)),
      const SizedBox(height: 4),
      Text(subtitle, style: const TextStyle(color: AppColors.muted, fontSize: 12)),
    ]),
  );
}

class Subject {
  final String name, chapters;
  final IconData icon;
  final Color color;
  const Subject(this.name, this.chapters, this.icon, this.color);
}

const subjects = [
  Subject('Quantitative Maths', '28 Chapters', Icons.calculate_rounded, AppColors.cyan),
  Subject('Reasoning Ability', '24 Chapters', Icons.psychology_rounded, Color(0xFFA78BFA)),
  Subject('General Awareness', '32 Chapters', Icons.public_rounded, AppColors.orange),
  Subject('English Comprehension', '26 Chapters', Icons.menu_book_rounded, AppColors.gold),
];

class ArsenalGrid extends StatelessWidget {
  const ArsenalGrid({super.key});
  @override Widget build(BuildContext context) => GridView.builder(
    shrinkWrap: true,
    physics: const NeverScrollableScrollPhysics(),
    itemCount: subjects.length,
    gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
      crossAxisCount: 2, crossAxisSpacing: 12, mainAxisSpacing: 12, childAspectRatio: 1.14,
    ),
    itemBuilder: (context, index) {
      final subject = subjects[index];
      return PressableScale(
        onTap: () => Navigator.push(context, MaterialPageRoute(builder: (_) => SimplePage(title: subject.name, subtitle: 'Theory + Practice', icon: subject.icon))),
        child: Container(
          padding: const EdgeInsets.all(15),
          decoration: BoxDecoration(
            color: AppColors.surface,
            borderRadius: BorderRadius.circular(20),
            border: Border.all(color: AppColors.border),
          ),
          child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
            Container(
              width: 43, height: 43,
              decoration: BoxDecoration(
                color: subject.color.withValues(alpha: .12),
                borderRadius: BorderRadius.circular(13),
                border: Border.all(color: subject.color.withValues(alpha: .24)),
              ),
              child: Icon(subject.icon, color: subject.color),
            ),
            const Spacer(),
            Text(subject.name, maxLines: 2, overflow: TextOverflow.ellipsis, style: const TextStyle(color: AppColors.white, fontSize: 14, fontWeight: FontWeight.w800)),
            const SizedBox(height: 5),
            Text(subject.chapters, style: const TextStyle(color: AppColors.muted, fontSize: 10, fontWeight: FontWeight.w600)),
          ]),
        ),
      );
    },
  );
}

class MegaMockCard extends StatelessWidget {
  const MegaMockCard({super.key});
  @override Widget build(BuildContext context) => Container(
    padding: const EdgeInsets.all(18),
    decoration: BoxDecoration(color: AppColors.surface, borderRadius: BorderRadius.circular(22), border: Border.all(color: AppColors.border)),
    child: Column(children: [
      Row(children: [
        Container(
          width: 46, height: 46,
          decoration: BoxDecoration(
            gradient: const LinearGradient(colors: [AppColors.orange, AppColors.goldDark]),
            borderRadius: BorderRadius.circular(14),
          ),
          child: const Icon(Icons.flash_on_rounded, color: AppColors.navy),
        ),
        const SizedBox(width: 12),
        const Expanded(child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
          Row(children: [LiveBadge(), SizedBox(width: 8), Text('CGL TIER-1', style: TextStyle(color: AppColors.muted, fontSize: 9, fontWeight: FontWeight.w900, letterSpacing: 1))]),
          SizedBox(height: 6),
          Text('Mega Mock Battle', style: TextStyle(color: AppColors.white, fontSize: 18, fontWeight: FontWeight.w900)),
        ])),
      ]),
      const SizedBox(height: 18),
      const Row(children: [
        Icon(Icons.timer_outlined, color: AppColors.cyan, size: 17),
        SizedBox(width: 5),
        Text('60 min', style: TextStyle(color: AppColors.muted, fontSize: 11)),
        SizedBox(width: 18),
        Icon(Icons.quiz_outlined, color: AppColors.cyan, size: 17),
        SizedBox(width: 5),
        Text('100 Questions', style: TextStyle(color: AppColors.muted, fontSize: 11)),
        Spacer(),
        Text('+500 XP', style: TextStyle(color: AppColors.gold, fontSize: 11, fontWeight: FontWeight.w900)),
      ]),
      const SizedBox(height: 18),
      GlowingTestButton(onTap: () {
        Navigator.push(context, MaterialPageRoute(builder: (_) => const SimplePage(title: 'CGL Tier-1 Mega Mock', subtitle: '100 Questions • 60 Minutes', icon: Icons.timer)));
      }),
    ]),
  );
}

class LiveBadge extends StatelessWidget {
  const LiveBadge({super.key});
  @override Widget build(BuildContext context) => Container(
    padding: const EdgeInsets.symmetric(horizontal: 7, vertical: 4),
    decoration: BoxDecoration(
      color: AppColors.orange.withValues(alpha: .12),
      borderRadius: BorderRadius.circular(7),
      border: Border.all(color: AppColors.orange.withValues(alpha: .35)),
    ),
    child: const Text('LIVE', style: TextStyle(color: AppColors.orange, fontSize: 8, fontWeight: FontWeight.w900)),
  );
}

class GlowingTestButton extends StatefulWidget {
  final VoidCallback onTap;
  const GlowingTestButton({super.key, required this.onTap});
  @override State<GlowingTestButton> createState() => _GlowingTestButtonState();
}

class _GlowingTestButtonState extends State<GlowingTestButton> with SingleTickerProviderStateMixin {
  late final AnimationController shimmer;
  @override void initState() {
    super.initState();
    shimmer = AnimationController(vsync: this, duration: const Duration(milliseconds: 1800))..repeat();
  }
  @override void dispose() { shimmer.dispose(); super.dispose(); }
  @override Widget build(BuildContext context) => AnimatedBuilder(
    animation: shimmer,
    builder: (context, child) => CustomPaint(painter: GlowBorderPainter(shimmer.value), child: child),
    child: InkWell(
      onTap: widget.onTap,
      borderRadius: BorderRadius.circular(15),
      child: Container(
        height: 50,
        alignment: Alignment.center,
        decoration: BoxDecoration(color: AppColors.navy2, borderRadius: BorderRadius.circular(15)),
        child: const Row(mainAxisAlignment: MainAxisAlignment.center, children: [
          Text('TAKE TEST', style: TextStyle(color: AppColors.gold, fontSize: 12, fontWeight: FontWeight.w900, letterSpacing: 1.2)),
          SizedBox(width: 8),
          Icon(Icons.arrow_forward_rounded, color: AppColors.gold, size: 18),
        ]),
      ),
    ),
  );
}

class GlowBorderPainter extends CustomPainter {
  final double progress;
  GlowBorderPainter(this.progress);
  @override void paint(Canvas canvas, Size size) {
    final rect = RRect.fromRectAndRadius(Offset.zero & size, const Radius.circular(15));
    final paint = Paint()
      ..style = PaintingStyle.stroke
      ..strokeWidth = 1.5
      ..shader = SweepGradient(
        transform: GradientRotation(progress * math.pi * 2),
        colors: const [AppColors.goldDark, AppColors.cyan, AppColors.gold, AppColors.goldDark],
      ).createShader(Offset.zero & size);
    canvas.drawRRect(rect, paint);
  }
  @override bool shouldRepaint(covariant GlowBorderPainter oldDelegate) => oldDelegate.progress != progress;
}

class PressableScale extends StatefulWidget {
  final Widget child;
  final VoidCallback onTap;
  const PressableScale({super.key, required this.child, required this.onTap});
  @override State<PressableScale> createState() => _PressableScaleState();
}

class _PressableScaleState extends State<PressableScale> {
  bool pressed = false;
  @override Widget build(BuildContext context) => GestureDetector(
    behavior: HitTestBehavior.opaque,
    onTapDown: (_) => setState(() => pressed = true),
    onTapCancel: () => setState(() => pressed = false),
    onTapUp: (_) { setState(() => pressed = false); widget.onTap(); },
    child: AnimatedScale(scale: pressed ? .965 : 1, duration: const Duration(milliseconds: 100), child: widget.child),
  );
}

class PremiumBottomNav extends StatelessWidget {
  final int index;
  final ValueChanged<int> onChanged;
  const PremiumBottomNav({super.key, required this.index, required this.onChanged});

  static const labels = ['Home', 'Arena', 'Armory', 'Leaderboard', 'Profile'];
  static const icons = [
    Icons.home_rounded,
    Icons.sports_martial_arts_rounded,
    Icons.inventory_2_rounded,
    Icons.leaderboard_rounded,
    Icons.person_rounded,
  ];

  @override
  Widget build(BuildContext context) => Container(
    decoration: const BoxDecoration(
      color: Color(0xFF0C1320),
      border: Border(top: BorderSide(color: AppColors.border)),
    ),
    child: SafeArea(
      top: false,
      child: Padding(
        padding: const EdgeInsets.all(8),
        child: Row(
          children: List.generate(labels.length, (i) {
            final active = i == index;
            return Expanded(
              child: PressableScale(
                onTap: () => onChanged(i),
                child: AnimatedContainer(
                  duration: const Duration(milliseconds: 260),
                  curve: Curves.easeOutCubic,
                  margin: const EdgeInsets.symmetric(horizontal: 3),
                  padding: const EdgeInsets.symmetric(vertical: 7),
                  decoration: BoxDecoration(
                    color: active ? AppColors.gold.withValues(alpha: .10) : Colors.transparent,
                    borderRadius: BorderRadius.circular(15),
                    border: Border.all(
                      color: active ? AppColors.gold.withValues(alpha: .28) : Colors.transparent,
                    ),
                  ),
                  child: Column(mainAxisSize: MainAxisSize.min, children: [
                    Icon(icons[i], color: active ? AppColors.gold : AppColors.muted, size: 21),
                    const SizedBox(height: 3),
                    Text(
                      labels[i],
                      maxLines: 1,
                      overflow: TextOverflow.ellipsis,
                      style: TextStyle(
                        color: active ? AppColors.gold : AppColors.muted,
                        fontSize: 9,
                        fontWeight: active ? FontWeight.w900 : FontWeight.w600,
                      ),
                    ),
                  ]),
                ),
              ),
            );
          }),
        ),
      ),
    ),
  );
}

class SimplePage extends StatefulWidget {
  final String title, subtitle; final IconData icon;
  const SimplePage({super.key,required this.title,required this.subtitle,required this.icon});
  @override State<SimplePage> createState()=>_SimplePageState();
}
class _SimplePageState extends State<SimplePage>{
  String? fileName; int answer=-1;
  final pet=[
    ['भारतीय इतिहास','5 प्रश्न • 5 अंक','सिन्धु घाटी सभ्यता; वैदिक संस्कृति; बौद्ध धर्म; जैन धर्म; मौर्य वंश; अशोक; गुप्त वंश; समुद्रगुप्त; चन्द्रगुप्त द्वितीय; हर्षवर्धन; राजपूत; सल्तनत; मुगल; मराठा; ब्रिटिश राज; प्रथम स्वतंत्रता संग्राम; सामाजिक एवं आर्थिक प्रभाव।'],
    ['भारतीय राष्ट्रीय आन्दोलन','5 प्रश्न • 5 अंक','प्रारम्भिक आन्दोलन; स्वदेशी; सविनय अवज्ञा; महात्मा गांधी; क्रान्तिकारी आन्दोलन; उग्र राष्ट्रवाद; भारत सरकार अधिनियम 1935; भारत छोड़ो; आजाद हिन्द फौज; सुभाष चन्द्र बोस।'],
    ['भूगोल','5 प्रश्न • 5 अंक','भारत/विश्व का भौतिक व राजनीतिक भूगोल; नदियाँ; नदी घाटियाँ; भूजल; पर्वत; पहाड़ियाँ; हिमनद; मरुस्थल; वन; खनिज; जलवायु; मौसम; टाइम ज़ोन; जनसांख्यिकी; प्रवासन।'],
    ['भारतीय अर्थव्यवस्था','5 प्रश्न • 5 अंक','1947–1991 अर्थव्यवस्था; योजना आयोग; पंचवर्षीय योजनाएँ; मिश्रित अर्थव्यवस्था; हरित क्रान्ति; ऑपरेशन फ्लड; बैंक राष्ट्रीयकरण; 1991 सुधार; 2014 के बाद कृषि, ढांचागत व श्रम सुधार; GST।'],
    ['भारतीय संविधान एवं लोक प्रशासन','5 प्रश्न • 5 अंक','संविधान; नीति-निर्देशक तत्व; मौलिक अधिकार/कर्तव्य; संसदीय प्रणाली; संघीय प्रणाली; संघ/UT; केन्द्र-राज्य सम्बन्ध; सर्वोच्च/उच्च न्यायालय; जिला प्रशासन; स्थानीय निकाय; पंचायती राज।'],
    ['सामान्य विज्ञान','5 प्रश्न • 5 अंक','प्रारम्भिक Physics; Chemistry; Biology।'],
    ['प्रारम्भिक अंकगणित','5 प्रश्न • 5 अंक','पूर्ण संख्याएँ; भिन्न/दशमलव; प्रतिशतता; साधारण समीकरण; वर्ग/वर्गमूल; घातांक/घात; औसत।'],
    ['सामान्य हिन्दी','5 प्रश्न • 5 अंक','संधि; विलोम; पर्यायवाची; एक शब्द; लिंग; समश्रुत भिन्नार्थक शब्द; मुहावरे/लोकोक्तियाँ; अशुद्धियाँ; लेखक एवं रचनाएँ।'],
    ['सामान्य अंग्रेजी','5 प्रश्न • 5 अंक','English Grammar; Unseen Passage।'],
    ['तर्क एवं तर्कशक्ति','5 प्रश्न • 5 अंक','बड़ा/छोटा; क्रम/रैंकिंग; सम्बन्ध; odd-one-out; कैलेंडर/घड़ी; कारण-प्रभाव; Coding-Decoding; कथन विश्लेषण; निष्कर्ष।'],
    ['समसामयिकी','10 प्रश्न • 10 अंक','भारतीय और वैश्विक समसामयिकी; date-wise, weekly और monthly revision।'],
    ['सामान्य जागरूकता','10 प्रश्न • 10 अंक','पड़ोसी देश; देश-राजधानी-मुद्रा; राज्य/UT; संसद; महत्वपूर्ण दिवस; विश्व संगठन/मुख्यालय; पर्यटन; कला-संस्कृति; खेल; अनुसंधान संगठन; पुस्तक-लेखक; पुरस्कार; जलवायु/पर्यावरण।'],
    ['हिन्दी अपठित गद्यांश','10 प्रश्न • 10 अंक','2 अपठित हिन्दी गद्यांश; प्रत्येक पर 5 प्रश्न; आशय, तथ्य, शब्दार्थ और निष्कर्ष।'],
    ['ग्राफ की व्याख्या एवं विश्लेषण','10 प्रश्न • 10 अंक','2 ग्राफ; प्रत्येक पर 5 प्रश्न; data reading, comparison और conclusion।'],
    ['तालिका की व्याख्या एवं विश्लेषण','10 प्रश्न • 10 अंक','2 तालिकाएँ; प्रत्येक पर 5 प्रश्न; rows/columns, comparison और conclusion।']
  ];
  final subjects={
    'Quantitative Maths':['Number System','Percentage','Ratio & Proportion','Profit & Loss','Average','Time & Work','Time Speed Distance','Algebra','Geometry','Mensuration','Trigonometry','Data Interpretation'],
    'Reasoning Ability':['Analogy','Classification','Series','Coding-Decoding','Blood Relation','Direction','Ranking','Syllogism','Venn Diagram','Clock & Calendar','Statement & Conclusion','Non-Verbal Reasoning'],
    'English Comprehension':['Parts of Speech','Tenses','Subject Verb Agreement','Articles','Prepositions','Voice','Narration','Error Detection','Cloze Test','Reading Comprehension','Synonyms & Antonyms','One Word Substitution'],
    'General Awareness':['History','Geography','Polity','Economy','Science','Static GK','Art & Culture','Sports','Books & Authors','Awards','Environment','Current Affairs']
  };
  @override Widget build(BuildContext context)=>Scaffold(appBar:AppBar(title:Text(widget.title),backgroundColor:AppColors.navy),body:body());
  Widget body(){
    if(widget.title=='UPSSSC PET')return petPage();
    if(widget.title=='Notes & PDFs')return notesPage();
    if(widget.title=='Fighter AI')return fighterPage();
    if(widget.title=='Videos')return videosPage();
    if(widget.title=='Current Affairs'||widget.title=='Vacancies & Updates')return updatesPage();
    if(widget.title=='PYQ Bank'||widget.title.contains('Mock')||widget.title.contains('Practice')||widget.title.contains('Challenge')||widget.title=='Subject Test')return quizPage();
    return studyPage();
  }
  Widget studyPage()=>ListView(padding:const EdgeInsets.all(16),children:[
    const Text('Basic → Advanced',style:TextStyle(color:AppColors.gold,fontSize:22,fontWeight:FontWeight.w900)),
    const Text('Theory • formulas • examples • important topics • memory tricks • PYQs',style:TextStyle(color:AppColors.muted)),
    const SizedBox(height:14),
    for(final e in subjects.entries)Card(color:AppColors.surface,child:ExpansionTile(title:Text(e.key,style:const TextStyle(color:AppColors.white,fontWeight:FontWeight.w800)),children:[
      for(final t in e.value)ListTile(title:Text(t,style:const TextStyle(color:AppColors.white)),subtitle:const Text('Theory + example + shortcut + PYQ + practice',style:TextStyle(color:AppColors.muted,fontSize:10)),onTap:()=>lesson(t))
    ]))
  ]);
  void lesson(String t)=>showModalBottomSheet(context:context,backgroundColor:AppColors.surface,builder:(_)=>Padding(padding:const EdgeInsets.all(20),child:Column(mainAxisSize:MainAxisSize.min,crossAxisAlignment:CrossAxisAlignment.start,children:[
    Text(t,style:const TextStyle(color:AppColors.gold,fontSize:22,fontWeight:FontWeight.w900)),const SizedBox(height:12),
    const Text('THEORY',style:TextStyle(color:AppColors.cyan,fontWeight:FontWeight.w900)),
    Text(t+' को basic से advanced तक पढ़ें: definition → rule/formula → solved example → shortcut → PYQ → practice.',style:const TextStyle(color:AppColors.white,height:1.5)),
    const SizedBox(height:12),const Text('IMPORTANT',style:TextStyle(color:AppColors.orange,fontWeight:FontWeight.w900)),
    const Text('Frequently tested concepts, common traps और revision points को mark करके दोबारा करें.',style:TextStyle(color:AppColors.muted)),const SizedBox(height:12)
  ])));
  Widget petPage()=>ListView(padding:const EdgeInsets.all(14),children:[
    const Text('UPSSSC PET — Complete Syllabus',style:TextStyle(color:AppColors.gold,fontSize:22,fontWeight:FontWeight.w900)),
    const Text('15/15 sections • theory • important topics • practice',style:TextStyle(color:AppColors.muted)),const SizedBox(height:10),
    for(int i=0;i<pet.length;i++)Card(color:AppColors.surface,child:ExpansionTile(title:Text((i+1).toString()+'. '+pet[i][0],style:const TextStyle(color:AppColors.white,fontWeight:FontWeight.w800)),subtitle:Text(pet[i][1],style:const TextStyle(color:AppColors.gold,fontSize:10)),children:[
      Padding(padding:const EdgeInsets.all(15),child:Text(pet[i][2],style:const TextStyle(color:AppColors.white,height:1.5)))
    ]))
  ]);
  Widget quizPage(){
    final qs=<List<Object>>[
      <Object>['15 का 20% कितना है?',<String>['2','3','4','5'],'3'],
      <Object>['यदि 3x=21, x=?',<String>['5','6','7','8'],'7'],
      <Object>['भारत का संविधान कब लागू हुआ?',<String>['1947','1949','1950','1952'],'1950']
    ];
    final q=qs[DateTime.now().second%qs.length];
    final question=q[0] as String;
    final options=q[1] as List<String>;
    final correct=q[2] as String;
    return ListView(padding:const EdgeInsets.all(18),children:[
      Text(widget.title,style:const TextStyle(color:AppColors.gold,fontSize:22,fontWeight:FontWeight.w900)),
      const Text('Practice/PYQ engine • answer • explanation • progress',style:TextStyle(color:AppColors.muted)),
      const SizedBox(height:18),
      Card(color:AppColors.surface,child:Padding(padding:const EdgeInsets.all(14),child:Column(crossAxisAlignment:CrossAxisAlignment.start,children:[
        Text(question,style:const TextStyle(color:AppColors.white,fontSize:19,fontWeight:FontWeight.w800)),
        for(int i=0;i<options.length;i++)
          RadioListTile<int>(value:i,groupValue:answer,onChanged:(v)=>setState(()=>answer=v ?? -1),activeColor:AppColors.gold,title:Text(options[i],style:const TextStyle(color:AppColors.white))),
        if(answer>=0)
          Text(answer==options.indexOf(correct)?'✓ Correct — concept applied correctly.':'✗ Correct answer: '+correct,style:TextStyle(color:answer==options.indexOf(correct)?Colors.green:AppColors.orange,fontWeight:FontWeight.w800)),
        const SizedBox(height:8),
        ElevatedButton(onPressed:answer<0?null:()=>setState(()=>answer=-1),child:const Text('SUBMIT & CONTINUE'))
      ]))
    ]);
  }
  Widget notesPage()=>ListView(padding:const EdgeInsets.all(18),children:[
    const Text('Notes & PDFs',style:TextStyle(color:AppColors.gold,fontSize:22,fontWeight:FontWeight.w900)),
    const Text('Upload PDF/notes for your study workflow.',style:TextStyle(color:AppColors.muted)),const SizedBox(height:18),
    ElevatedButton.icon(onPressed:pickFile,icon:const Icon(Icons.upload_file),label:const Text('UPLOAD PDF / NOTES')),
    if(fileName!=null)ListTile(leading:const Icon(Icons.picture_as_pdf,color:AppColors.orange),title:Text(fileName!,style:const TextStyle(color:AppColors.white)),subtitle:const Text('Selected in this session',style:TextStyle(color:AppColors.muted)))
  ]);
  Future<void> pickFile()async{final r=await FilePicker.platform.pickFiles(type:FileType.custom,allowedExtensions:['pdf','doc','docx','txt']);if(r!=null)setState(()=>fileName=r.files.single.name);}
  Widget fighterPage()=>ListView(padding:const EdgeInsets.all(18),children:[
    const Text('FIGHTER AI',style:TextStyle(color:AppColors.gold,fontSize:24,fontWeight:FontWeight.w900)),
    const Text('Explain • Solve • Quiz • Revise',style:TextStyle(color:AppColors.muted)),const SizedBox(height:18),
    const Card(color:AppColors.surface,child:Padding(padding:EdgeInsets.all(16),child:Text('Topic या question के साथ Fighter workflow use करें. Live AI answers के लिए Gemini/OpenAI-compatible API configuration connect की जा सकती है.',style:TextStyle(color:AppColors.white,height:1.5)))),
    for(final s in ['Explain Percentage from basic','Solve this PYQ','Make a 10-question quiz','Revise weak topics'])ListTile(title:Text(s,style:const TextStyle(color:AppColors.white)),trailing:const Icon(Icons.arrow_forward,color:AppColors.gold),onTap:()=>showDialog(context:context,builder:(_)=>AlertDialog(title:const Text('Fighter AI'),content:Text('Request ready: '+s),actions:[TextButton(onPressed:()=>Navigator.pop(context),child:const Text('OK'))])))
  ]);
  Widget videosPage()=>ListView(padding:const EdgeInsets.all(18),children:[
    const Text('Videos',style:TextStyle(color:AppColors.gold,fontSize:22,fontWeight:FontWeight.w900)),
    for(final s in ['SSC Maths Basics','Reasoning Concepts','English Grammar','UPSSSC PET Revision'])ListTile(leading:const Icon(Icons.play_circle,color:AppColors.orange),title:Text(s,style:const TextStyle(color:AppColors.white)),subtitle:const Text('Open YouTube search',style:TextStyle(color:AppColors.muted)),onTap:()=>launchUrl(Uri.parse('https://www.youtube.com/results?search_query='+Uri.encodeComponent(s)),mode:LaunchMode.externalApplication))
  ]);
  Widget updatesPage()=>ListView(padding:const EdgeInsets.all(18),children:[
    const Text('Updates Hub',style:TextStyle(color:AppColors.gold,fontSize:22,fontWeight:FontWeight.w900)),
    const Text('Current affairs • vacancies • exam notices',style:TextStyle(color:AppColors.muted)),
    for(final s in ['Daily Current Affairs','Weekly Current Affairs','Monthly Revision','SSC CGL Notifications','Railway Recruitment Updates','UP SI Updates','UPSSSC Updates'])ListTile(title:Text(s,style:const TextStyle(color:AppColors.white)),trailing:const Icon(Icons.chevron_right,color:AppColors.gold),onTap:()=>launchUrl(Uri.parse('https://www.google.com/search?q='+Uri.encodeComponent(s)),mode:LaunchMode.externalApplication))
  ]);
}
