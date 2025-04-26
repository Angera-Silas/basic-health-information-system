import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '../services/api_service.dart';
import '../models/enrollment_information.dart';

class EnrollmentScreen extends StatefulWidget {
  @override
  _EnrollmentScreenState createState() => _EnrollmentScreenState();
}

class _EnrollmentScreenState extends State<EnrollmentScreen> {
  List<dynamic> enrollments = [];
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
      ); // Assuming doctorId is stored as an integer
    });
    fetchEnrollments();
  }

  Future<void> fetchEnrollments() async {
    setState(() {
      isLoading = true;
    });
    try {
      final data = await ApiService.getData("/enrollments/by-doctor/$doctorId");
      setState(() {
        enrollments = data;
        isLoading = false;
      });
    } catch (e) {
      print("Error fetching enrollments: $e");
      setState(() {
        isLoading = false;
      });
    }
  }

  Future<void> fetchEnrollmentDetails(int enrollmentId) async {
    try {
      final enrollmentDetailsJson = await ApiService.getData(
        "/enrollments/information/$enrollmentId",
      );
      final enrollmentDetails = EnrollmentInformation.fromJson(
        enrollmentDetailsJson,
      );
      showEnrollmentDetailsPopup(enrollmentDetails);
    } catch (e) {
      print("Error fetching enrollment details: $e");
    }
  }

  void showEnrollmentDetailsPopup(EnrollmentInformation enrollmentDetails) {
  showDialog(
    context: context,
    builder: (ctx) {
      return Dialog(
        child: Container(
          width: 600, 
          child: AlertDialog(
            title: Text("Enrollment Details"),
            content: SingleChildScrollView(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text("Client Name: ${enrollmentDetails.clientName}"),
                  SizedBox(height: 10),
                  Text("Program Name: ${enrollmentDetails.programName}"),
                  SizedBox(height: 10),
                  Text("Start Date: ${enrollmentDetails.startDate}"),
                  SizedBox(height: 10),
                  Text("End Date: ${enrollmentDetails.endDate}"),
                  SizedBox(height: 10),
                  Text("Status: ${enrollmentDetails.status}"),
                  SizedBox(height: 10),
                  Text("Progress: ${enrollmentDetails.progress}"),
                  SizedBox(height: 10),
                  Text("Notes: ${enrollmentDetails.notes}"),
                  SizedBox(height: 10),
                  Text("Enrolled At: ${enrollmentDetails.enrolledAt}"),
                  SizedBox(height: 10),
                  Text("Doctor Name: ${enrollmentDetails.doctorName}"),
                  SizedBox(height: 10),
                  Text("Doctor Email: ${enrollmentDetails.doctorEmail}"),
                  SizedBox(height: 10),
                  Text("Doctor Phone: ${enrollmentDetails.doctorPhone}"),
                ],
              ),
            ),
            actions: [
              TextButton(
                onPressed: () {
                  Navigator.of(ctx).pop(); // Close the popup
                },
                child: Text("Close"),
              ),
            ],
          ),
        ),
      );
    },
  );
}
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        children: [
          // Toolbar Row
          Container(
            padding: EdgeInsets.symmetric(vertical: 10, horizontal: 20),
            color: Colors.grey[200],
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Text(
                  "Enrollments",
                  style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
                ),
                
              ],
            ),
          ),
          Expanded(
            child:
                isLoading
                    ? Center(child: CircularProgressIndicator())
                    : enrollments.isEmpty
                    ? Center(child: Text("No enrollments available"))
                    : SingleChildScrollView(
                      child: DataTable(
                        columns: const [
                          DataColumn(label: Text("ID")),
                          DataColumn(label: Text("Client")),
                          DataColumn(label: Text("Program")),
                          DataColumn(label: Text("Status")),
                          DataColumn(label: Text("Actions")),
                        ],
                        rows:
                            enrollments.map((enrollment) {
                              return DataRow(
                                cells: [
                                  DataCell(Text(enrollment['id'].toString())),
                                  DataCell(Text(enrollment['clientName'])),
                                  DataCell(Text(enrollment['programName'])),
                                  DataCell(Text(enrollment['status'])),
                                  DataCell(
                                    ElevatedButton(
                                      onPressed:
                                          () => fetchEnrollmentDetails(
                                            enrollment['id'],
                                          ),
                                      child: Text("View Details"),
                                    ),
                                  ),
                                ],
                              );
                            }).toList(),
                      ),
                    ),
          ),
        ],
      ),
    );
  }
}
