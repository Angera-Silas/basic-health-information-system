import 'package:flutter/material.dart';
import 'package:health_system_frontend/services/api_service.dart';
import 'package:shared_preferences/shared_preferences.dart';

class ProgramScreen extends StatefulWidget {
  @override
  _ProgramScreenState createState() => _ProgramScreenState();
}

class _ProgramScreenState extends State<ProgramScreen> {
  List<dynamic> programs = [];
  List<dynamic> unenrolledClients = []; // List of unenrolled clients
  bool isLoading = true;
  int? doctorId;

  @override
  void initState() {
    super.initState();
    fetchPrograms();
    loadDoctorId();
  }

  Future<void> loadDoctorId() async {
    final prefs = await SharedPreferences.getInstance();
    setState(() {
      doctorId = prefs.getInt('doctorId');
    });
    fetchPrograms();
  }

  Future<void> fetchPrograms() async {
    setState(() {
      isLoading = true;
    });
    try {
      final data = await ApiService.getData("/programs/doctor/$doctorId");
      setState(() {
        programs = data;
        isLoading = false;
      });
    } catch (e) {
      print("Error fetching programs: $e");
      setState(() {
        isLoading = false;
      });
    }
  }

  Future<void> fetchUnenrolledClients(int programId) async {
    try {
      final data = await ApiService.getData(
        "/programs/$programId/unenrolled-clients/$doctorId",
      );
      setState(() {
        unenrolledClients = data;
      });
    } catch (e) {
      print("Error fetching unenrolled clients: $e");
    }
  }

  Future<void> enrollClients(int programId, List<int> clientIds) async {
    try {
      for (int clientId in clientIds) {
        await ApiService.postData("/enrollments/create", {
          "programId": programId,
          "clientId": clientId,
          "startDate": DateTime.now().toIso8601String(),
          "endDate": DateTime.now().add(Duration(days: 30)).toIso8601String(),
          "status": "active",
          "progress": 0,
          "notes": "...",
          "enrolledAt": DateTime.now().toIso8601String(),
          "enrolledById": doctorId, // Use the doctorId from shared preferences
        });
      }
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(SnackBar(content: Text("Clients enrolled successfully")));
      fetchPrograms(); // Refresh the program list
    } catch (e) {
      print("Error enrolling clients: $e");
    }
  }

  void showEnrollClientsPopup(int programId) async {
    await fetchUnenrolledClients(programId);

    if (unenrolledClients.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text("No unenrolled clients available")),
      );
      return;
    }

    List<int> selectedClientIds = [];

    showDialog(
      context: context,
      builder: (ctx) {
        return AlertDialog(
          title: Text("Enroll Clients"),
          content: Container(
            width: 400,
            child: ListView.builder(
              shrinkWrap: true,
              itemCount: unenrolledClients.length,
              itemBuilder: (context, index) {
                final client = unenrolledClients[index];
                return CheckboxListTile(
                  title: Text("${client['name']} (ID: ${client['id']})"),
                  value: selectedClientIds.contains(client['id']),
                  onChanged: (isSelected) {
                    setState(() {
                      if (isSelected == true) {
                        selectedClientIds.add(client['id']);
                      } else {
                        selectedClientIds.remove(client['id']);
                      }
                    });
                  },
                );
              },
            ),
          ),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.of(ctx).pop(); // Close the popup
              },
              child: Text("Cancel"),
            ),
            ElevatedButton(
              onPressed: () async {
                if (selectedClientIds.isEmpty) {
                  ScaffoldMessenger.of(context).showSnackBar(
                    SnackBar(
                      content: Text("Please select at least one client"),
                    ),
                  );
                  return;
                }
                Navigator.of(ctx).pop(); // Close the popup
                await enrollClients(programId, selectedClientIds);
              },
              child: Text("Enroll"),
            ),
          ],
        );
      },
    );
  }

  Future<void> fetchProgramDetails(int programId) async {
    try {
      final programDetails = await ApiService.getData(
        "/programs/information/$programId",
      );
      showProgramDetailsPopup(programDetails);
    } catch (e) {
      print("Error fetching program details: $e");
    }
  }

  Future<void> deleteProgram(int programId) async {
    try {
      await ApiService.deleteData("/programs/delete/$programId");
      fetchPrograms(); // Refresh the list after deletion
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(SnackBar(content: Text("Program deleted successfully")));
    } catch (e) {
      print("Error deleting program: $e");
    }
  }

  Future<void> createProgram(String name, String description) async {
    if (doctorId == null) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text("Doctor ID not found. Please log in again.")),
      );
      return;
    }

    try {
      await ApiService.postData("/programs/create", {
        "name": name,
        "description": description,
        "createdAt": DateTime.now().toIso8601String(),
        "createdById": doctorId, // Use the doctorId from shared preferences
      });
      fetchPrograms(); // Refresh the list after creating a program
    } catch (e) {
      print("Error creating program: $e");
    }
  }

  void showCreateProgramPopup() {
    final TextEditingController nameController = TextEditingController();
    final TextEditingController descriptionController = TextEditingController();

    showDialog(
      context: context,
      builder: (ctx) {
        return AlertDialog(
          title: Text("Add New Program"),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              TextField(
                controller: nameController,
                decoration: InputDecoration(labelText: "Program Name"),
              ),
              SizedBox(height: 10),
              TextField(
                controller: descriptionController,
                decoration: InputDecoration(labelText: "Program Description"),
              ),
            ],
          ),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.of(ctx).pop(); // Close the popup
              },
              child: Text("Cancel"),
            ),
            ElevatedButton(
              onPressed: () async {
                final name = nameController.text.trim();
                final description = descriptionController.text.trim();

                if (name.isEmpty || description.isEmpty) {
                  ScaffoldMessenger.of(context).showSnackBar(
                    SnackBar(content: Text("Please fill in all fields")),
                  );
                  return;
                }

                Navigator.of(ctx).pop(); // Close the popup
                await createProgram(name, description);
              },
              child: Text("Create"),
            ),
          ],
        );
      },
    );
  }

  void showProgramDetailsPopup(Map<String, dynamic> programDetails) {
    showDialog(
      context: context,
      builder: (ctx) {
        return AlertDialog(
          title: Text(programDetails['name']),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text("Description: ${programDetails['description']}"),
              SizedBox(height: 10),

              Text("Created By: ${programDetails['createdByName']}"),
              SizedBox(height: 10),

              Text("Created At: ${programDetails['createdAt']}"),
              SizedBox(height: 10),

              Text("Clients: ${programDetails['clients'].join(', ')}"),
            ],
          ),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.of(ctx).pop(); // Close the popup
              },
              child: Text("Close"),
            ),
            TextButton(
              onPressed: () {
                Navigator.of(ctx).pop(); // Close the popup
                // Add logic to edit the program
              },
              child: Text("Edit"),
            ),
            TextButton(
              onPressed: () async {
                Navigator.of(ctx).pop(); // Close the popup
                await deleteProgram(programDetails['id']);
              },
              child: Text("Delete", style: TextStyle(color: Colors.red)),
            ),
          ],
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        // Toolbar Row
        Container(
          padding: EdgeInsets.symmetric(vertical: 10, horizontal: 20),
          color: Colors.grey[200],
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Text(
                "Programs",
                style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
              ),
              ElevatedButton.icon(
                onPressed: showCreateProgramPopup,
                icon: Icon(Icons.add),
                label: Text("Add Program"),
                style: ElevatedButton.styleFrom(
                  padding: EdgeInsets.symmetric(horizontal: 15, vertical: 10),
                ),
              ),
            ],
          ),
        ),
        Expanded(
          child:
              isLoading
                  ? Center(child: CircularProgressIndicator())
                  : programs.isEmpty
                  ? Center(child: Text("No programs available"))
                  : GridView.builder(
                    padding: EdgeInsets.all(10),
                    gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                      crossAxisCount: 2,
                      crossAxisSpacing: 10,
                      mainAxisSpacing: 10,
                      childAspectRatio: 3 / 2,
                    ),
                    itemCount: programs.length,
                    itemBuilder: (context, index) {
                      final program = programs[index];
                      return Card(
                        elevation: 3,
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(10),
                        ),
                        child: Container(
                          padding: EdgeInsets.all(10),
                          decoration: BoxDecoration(
                            borderRadius: BorderRadius.circular(10),
                            color: Colors.blue[50],
                          ),
                          child: Column(
                            mainAxisAlignment: MainAxisAlignment.center,
                            crossAxisAlignment: CrossAxisAlignment.center,
                            children: [
                              Icon(
                                Icons.local_hospital,
                                size: 40,
                                color: Colors.blue,
                              ),
                              SizedBox(height: 10),
                              Text(
                                program['name'],
                                textAlign: TextAlign.center,
                                style: TextStyle(
                                  fontSize: 16,
                                  fontWeight: FontWeight.bold,
                                ),
                              ),
                              SizedBox(height: 10),
                              ElevatedButton(
                                onPressed:
                                    () => showEnrollClientsPopup(program['id']),
                                child: Text("Enroll"),
                              ),

                              SizedBox(height: 10),
                              ElevatedButton(
                                onPressed:
                                    () => fetchProgramDetails(program['id']),
                                child: Text("View Program"),
                              ),
                            ],
                          ),
                        ),
                      );
                    },
                  ),
        ),
      ],
    );
  }
}
