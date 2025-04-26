import 'package:flutter/material.dart';

class Sidebar extends StatelessWidget {
  final Function(String) onMenuSelect;

  Sidebar({required this.onMenuSelect});

  @override
  Widget build(BuildContext context) {
    return Container(
      width: 250,
      color: Colors.blue[800],
      child: Column(
        children: [
          DrawerHeader(
            child: Text(
              "Admin Menu",
              style: TextStyle(color: Colors.white, fontSize: 18),
            ),
          ),
          ListTile(
            leading: Icon(Icons.dashboard, color: Colors.white),
            title: Text("Dashboard", style: TextStyle(color: Colors.white)),
            onTap: () => onMenuSelect("dashboard"),
          ),
          ListTile(
            leading: Icon(Icons.people, color: Colors.white),
            title: Text("Clients", style: TextStyle(color: Colors.white)),
            onTap: () => onMenuSelect("clients"),
          ),
          ListTile(
            leading: Icon(Icons.local_hospital, color: Colors.white),
            title: Text("Programs", style: TextStyle(color: Colors.white)),
            onTap: () => onMenuSelect("programs"),
          ),
          ListTile(
            leading: Icon(Icons.assignment, color: Colors.white),
            title: Text("Enrollments", style: TextStyle(color: Colors.white)),
            onTap: () => onMenuSelect("enrollments"),
          ),

          ListTile(
            leading: Icon(Icons.logout, color: Colors.white),
            title: Text("Logout", style: TextStyle(color: Colors.white)),
            onTap: () => onMenuSelect("logout"),
          ),
        ],
      ),
    );
  }
}