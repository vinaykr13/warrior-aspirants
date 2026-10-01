import 'dart:math' as math;
import 'package:flutter/material.dart';

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
        onTap: () => ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text(subject.name), behavior: SnackBarBehavior.floating),
        ),
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
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Mega Mock Battle is ready!'), behavior: SnackBarBehavior.floating),
        );
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

class SimplePage extends StatelessWidget {
  final String title, subtitle;
  final IconData icon;
  const SimplePage({super.key, required this.title, required this.subtitle, required this.icon});

  @override
  Widget build(BuildContext context) => SafeArea(
    child: Center(
      child: Padding(
        padding: const EdgeInsets.all(28),
        child: Container(
          width: double.infinity,
          padding: const EdgeInsets.all(28),
          decoration: BoxDecoration(
            color: AppColors.surface,
            borderRadius: BorderRadius.circular(22),
            border: Border.all(color: AppColors.border),
          ),
          child: Column(mainAxisSize: MainAxisSize.min, children: [
            Container(
              width: 70, height: 70,
              decoration: BoxDecoration(
                color: AppColors.gold.withValues(alpha: .12),
                borderRadius: BorderRadius.circular(20),
              ),
              child: Icon(icon, color: AppColors.gold, size: 34),
            ),
            const SizedBox(height: 18),
            Text(title, style: const TextStyle(color: AppColors.white, fontSize: 24, fontWeight: FontWeight.w900)),
            const SizedBox(height: 7),
            Text(subtitle, textAlign: TextAlign.center, style: const TextStyle(color: AppColors.muted, fontSize: 13)),
          ]),
        ),
      ),
    ),
  );
}
