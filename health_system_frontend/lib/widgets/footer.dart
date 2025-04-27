import 'package:flutter/material.dart';

class Footer extends StatelessWidget {
  const Footer({super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.all(10),
      color: Colors.blue,
      child: Center(
        child: Text(
          "© 2025 Health System. All rights reserved.",
          style: TextStyle(color: Colors.white),
        ),
      ),
    );
  }
}