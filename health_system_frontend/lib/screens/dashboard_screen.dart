import 'package:flutter/material.dart';
import 'package:health_system_frontend/screens/enrollment_list_screen.dart'
    show EnrollmentScreen;
import 'package:shared_preferences/shared_preferences.dart';
import '../widgets/header.dart';
import '../widgets/sidebar.dart';
import '../widgets/footer.dart';
import 'program_screen.dart';
import 'client_list_screen.dart';
import '../services/api_service.dart';

class DashboardScreen extends StatefulWidget {
  const DashboardScreen({super.key});

  @override
  _DashboardScreenState createState() => _DashboardScreenState();
}

class _DashboardScreenState extends State<DashboardScreen> {
  String selectedMenu = "dashboard";
  Map<String, dynamic> dashboardData = {};
  bool isLoading = true;
  int? doctorId;

  @override
  void initState() {
    super.initState();
    loadDoctorId();
  }

  Future<void> loadDoctorId() async {
    final prefs = await SharedPreferences.getInstance();
    setState(() {
      doctorId = prefs.getInt(
        'doctorId',
      ); 
    });
    fetchDashboardData();
  }

  Future<void> fetchDashboardData() async {
    try {
      final data = await ApiService.getData("/dashboard/summary/$doctorId");
      setState(() {
        dashboardData = data ?? {}; // Ensure dashboardData is not null
        isLoading = false;
      });
    } catch (e) {
      setState(() {
        isLoading = false;
      });
    }
  }

  Widget getMainContent() {
    switch (selectedMenu) {
      case "clients":
        return ClientScreen();
      case "programs":
        return ProgramScreen();
      case "enrollments":
        return EnrollmentScreen();
      case "logout":
        return Center(child: Text("Logging out..."));
      default:
        return buildDashboardContent();
    }
  }

  Widget buildDashboardContent() {
    if (isLoading) {
      return Center(child: CircularProgressIndicator());
    }

    // Safely access dashboard data
    final totalClients = dashboardData['totalClients']?.toString() ?? '0';
    final activePrograms = dashboardData['activePrograms']?.toString() ?? '0';
    final totalEnrollments =
        dashboardData['totalEnrollments']?.toString() ?? '0';

    final recentPrograms = dashboardData['recentPrograms'] ?? [];
    final recentClients = dashboardData['recentClients'] ?? [];

    return SingleChildScrollView(
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          // Dashboard Summary Cards in GridView
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: GridView.builder(
              shrinkWrap: true,
              physics: NeverScrollableScrollPhysics(),
              gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                crossAxisCount: 3, // 3 cards per row
                crossAxisSpacing: 16,
                mainAxisSpacing: 16,
                childAspectRatio: 1.5, // Adjust card height
              ),
              itemCount: 3,
              itemBuilder: (context, index) {
                final titles = [
                  "Total Clients",
                  "Active Programs",
                  "Enrollments",
                ];
                final values = [totalClients, activePrograms, totalEnrollments];
                final colors = [Colors.blue, Colors.green, Colors.orange];
                final icons = [
                  Icons.people,
                  Icons.local_hospital,
                  Icons.assignment,
                ];
                return buildSummaryCard(
                  titles[index],
                  values[index],
                  colors[index],
                  icons[index],
                );
              },
            ),
          ),
          SizedBox(height: 20),

          // Recently Added Programs and Clients as Tables
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  "Recently Added Programs",
                  style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                ),
                SizedBox(height: 10),
                buildTable(recentPrograms, ["Name", "Description"]),
                SizedBox(height: 20),
                Text(
                  "Recently Added Clients",
                  style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                ),
                SizedBox(height: 10),
                buildTable(recentClients, ["Name", "Gender", "dateOfBirth"]),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget buildSummaryCard(
    String title,
    String value,
    Color color,
    IconData icon,
  ) {
    return Card(
      elevation: 5,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(10)),
      child: Container(
        padding: EdgeInsets.all(20),
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(10),
          color: color.withOpacity(0.1),
        ),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            CircleAvatar(
              backgroundColor: color.withOpacity(0.2),
              child: Icon(icon, color: color),
            ),
            SizedBox(height: 15),
            Text(
              title,
              style: TextStyle(
                fontSize: 16,
                fontWeight: FontWeight.bold,
                color: color,
              ),
              textAlign: TextAlign.center,
            ),
            SizedBox(height: 5),
            Text(
              value,
              style: TextStyle(
                fontSize: 24,
                fontWeight: FontWeight.bold,
                color: color,
              ),
              textAlign: TextAlign.center,
            ),
          ],
        ),
      ),
    );
  }

  Widget buildTable(List<dynamic> data, List<String> headers) {
    if (data.isEmpty) {
      return Center(child: Text("No data available"));
    }

    return Table(
      border: TableBorder.all(color: Colors.grey),
      columnWidths: {
        for (int i = 0; i < headers.length; i++) i: FlexColumnWidth(1),
      },
      children: [
        // Table Header
        TableRow(
          decoration: BoxDecoration(color: Colors.grey[200]),
          children:
              headers
                  .map(
                    (header) => Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Text(
                        header,
                        style: TextStyle(fontWeight: FontWeight.bold),
                        textAlign: TextAlign.center,
                      ),
                    ),
                  )
                  .toList(),
        ),
        // Table Rows
        ...data.map((row) {
          if (row is Map<String, dynamic>) {
            return TableRow(
              children:
                  headers.map<Widget>((header) {
                    final key = header.toLowerCase().replaceAll(' ', '');
                    return Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Text(
                        row[key]?.toString() ?? '',
                        textAlign: TextAlign.center,
                      ),
                    );
                  }).toList(),
            );
          }
          return TableRow(children: []);
        }).toList(),
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        children: [
          Header(),
          Expanded(
            child: Row(
              children: [
                Sidebar(
                  onMenuSelect: (menu) {
                    setState(() {
                      selectedMenu = menu;
                    });
                  },
                ),
                Expanded(
                  child: Container(
                    padding: EdgeInsets.symmetric(horizontal: 8, vertical: 6),
                    child: getMainContent(),
                  ),
                ),
              ],
            ),
          ),
          Footer(),
        ],
      ),
    );
  }
}
