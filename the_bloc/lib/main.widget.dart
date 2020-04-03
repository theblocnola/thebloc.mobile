import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:the_bloc/devotionals/devotionals-page.widget.dart';
import 'package:the_bloc/podcast/podcast-page.widget.dart';
import 'package:the_bloc/more/more-page.widget.dart';
import 'package:the_bloc/sermons/sermons-page.widget.dart';
import 'package:the_bloc/worship/worship-page.widget.dart';


class MainWidget extends StatefulWidget {
  MainWidget({Key key, this.title}) : super(key: key);

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;

  @override
  _MainWidgetState createState() => _MainWidgetState();
}

class _MainWidgetState extends State<MainWidget> {
  int _selectedIndex = 0;

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  static List<Widget> _widgetOptions = <Widget>[
    new DevotionalsPage(),
    new PodcastPage(),
    new SermonsPage(),
    new WorshipPage(),
    new MorePage(),
  ];

  @override
  Widget build(BuildContext context) {
    // This method is rerun every time setState is called, for instance as done
    // by the _incrementCounter method above.
    //
    // The Flutter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    return DefaultTabController(
        length: 5,
        child: Scaffold(
          appBar: AppBar(
            title: Text('Welcome to the BLOC'),
          ),
          bottomNavigationBar: BottomNavigationBar(
              type: BottomNavigationBarType.fixed,
              items: [
                BottomNavigationBarItem(
                  icon: Icon(Icons.bookmark), 
                  title: Text('Devotionals')
                ),
                BottomNavigationBarItem(
                  icon: Icon(Icons.mic), 
                  title: Text('Podcast')
                ),
                BottomNavigationBarItem(
                  icon: Icon(Icons.video_library), 
                  title: Text('Sermons')
                ),
                BottomNavigationBarItem(
                  icon: Icon(Icons.library_music), 
                  title: Text('Worship')
                ),
                BottomNavigationBarItem(
                  icon: Icon(Icons.more_vert), 
                  title: Text('More')
                )
              ],
              currentIndex: _selectedIndex,
              selectedItemColor: Colors.amber[800],
              onTap: _onItemTapped,
          ),
          body: Center(
            child: _widgetOptions.elementAt(_selectedIndex),
          ),
          
        ),
      );
  }
}