import 'package:flutter/material.dart';
import 'package:health_system_frontend/models/client_information.dart';
import 'package:health_system_frontend/services/api_service.dart';
import 'package:shared_preferences/shared_preferences.dart';

class ClientScreen extends StatefulWidget {
  const ClientScreen({super.key});

  @override
  _ClientScreenState createState() => _ClientScreenState();
}

class _ClientScreenState extends State<ClientScreen> {
  List<dynamic> clients = [];
  List<dynamic> filteredClients = []; // List to hold filtered clients
  List<dynamic> unenrolledPrograms = [];
  bool isLoading = true;
  String searchQuery = "";

  int? doctorId;

  @override
  void initState() {
    super.initState();
    loadDoctorId();
  }

  Future<void> loadDoctorId() async {
    final prefs = await SharedPreferences.getInstance();
    setState(() {
      doctorId = prefs.getInt('doctorId');
    });
    fetchClients();
  }

  Future<void> fetchClients() async {
    setState(() {
      isLoading = true;
    });
    try {
      // Debugging
      final data = await ApiService.getData(
        "/client-details/by-doctor/$doctorId",
      );
      // Debugging
      setState(() {
        clients = data ?? []; // Ensure clients is not null
        isLoading = false;
      });
    } catch (e) {
      setState(() {
        isLoading = false;
      });
    }
  }

  Future<void> fetchClientDetails(int clientId) async {
    try {
      // Debugging
      final clientDetails = await ApiService.getData(
        "/client-details/full-info/$clientId",
      );
      // Debugging
      showClientDetailsPopup(ClientInformation.fromJson(clientDetails));
    } catch (e) {
    }
  }

  Future<void> fetchUnenrolledPrograms(int clientId) async {
    try {
      final data = await ApiService.getData(
        "/client-details/$clientId/unenrolled-programs/$doctorId",
      );
      setState(() {
        unenrolledPrograms = data;
      });
    } catch (e) {
    }
  }

  Future<void> enrollClients(int clientId, List<int> programIds) async {
    try {
      for (int programId in programIds) {
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
      fetchClients(); // Refresh the clients list
    } catch (e) {
    }
  }

  Future<void> addClient(
    String name,
    String email,
    String phone,
    String dateOfBirth,
    String gender,
    String physicalAddress,
    String street,
    String town,
  ) async {
    try {
      final response = await ApiService.postData("/users/create", {
        "name": name,
        "email": email,
        "phone": phone,
        "password": "password123", // Default password, change as needed
        "role": "CLIENT",
      });

      final userId = response['id'];
      if (userId == null) {
        throw Exception("User ID not found in response.");
      }
      await ApiService.postData("/client-details/create", {
        "userId": userId,
        "dateOfBirth": dateOfBirth,
        "gender": gender,
        "physicalAddress": physicalAddress,
        "street": street,
        "town": town,
        "active": true,
        "registeredById": doctorId,
      });
      fetchClients(); // Refresh the list after adding a client
    } catch (e) {
    }
  }

  void showClientDetailsPopup(ClientInformation clientDetails) {
    showDialog(
      context: context,
      builder: (ctx) {
        return AlertDialog(
          title: Text(clientDetails.name),
          content: SingleChildScrollView(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text("Email: ${clientDetails.email}"),
                SizedBox(height: 10),
                Text("Phone: ${clientDetails.phone}"),
                SizedBox(height: 10),
                Text("Date of Birth: ${clientDetails.dateOfBirth}"),
                SizedBox(height: 10),
                Text("Gender: ${clientDetails.gender}"),
                SizedBox(height: 10),
                Text("Physical Address: ${clientDetails.physicalAddress}"),
                SizedBox(height: 10),
                Text("Street: ${clientDetails.street}"),
                SizedBox(height: 10),
                Text("Town: ${clientDetails.town}"),
                SizedBox(height: 10),
                Text("Active: ${clientDetails.active ? 'Yes' : 'No'}"),
                SizedBox(height: 10),
                Text("Registered By:"),
                Text(" - Name: ${clientDetails.registeredByName}"),
                Text(" - Email: ${clientDetails.registeredByEmail}"),
                Text(" - Phone: ${clientDetails.registeredByPhone}"),
                SizedBox(height: 10),
                Text("Programs Enrolled:"),
                for (var program in clientDetails.programs) Text(" - $program"),
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
        );
      },
    );
  }

  void showAddClientPopup() {
    final TextEditingController nameController = TextEditingController();
    final TextEditingController emailController = TextEditingController();
    final TextEditingController phoneController = TextEditingController();
    final TextEditingController dateOfBirthController = TextEditingController();
    final TextEditingController genderController = TextEditingController();
    final TextEditingController physicalAddressController =
        TextEditingController();
    final TextEditingController streetController = TextEditingController();
    final TextEditingController townController = TextEditingController();

    showDialog(
      context: context,
      builder: (ctx) {
        return Dialog(
          child: Container(
            width: 700, // Set the width of the dialog
            child: AlertDialog(
              title: Text("Add New Client"),
              content: SingleChildScrollView(
                child: Wrap(
                  spacing: 10, // Horizontal spacing
                  runSpacing: 10, // Vertical spacing
                  children: [
                    TextField(
                      controller: nameController,
                      decoration: InputDecoration(labelText: "Name"),
                    ),
                    TextField(
                      controller: emailController,
                      decoration: InputDecoration(labelText: "Email"),
                    ),
                    TextField(
                      controller: phoneController,
                      decoration: InputDecoration(labelText: "Phone"),
                    ),
                    TextField(
                      controller: dateOfBirthController,
                      decoration: InputDecoration(
                        labelText: "Date of Birth (YYYY-MM-DD)",
                      ),
                    ),
                    TextField(
                      controller: genderController,
                      decoration: InputDecoration(
                        labelText: "Gender (Male/Female)",
                      ),
                    ),
                    TextField(
                      controller: physicalAddressController,
                      decoration: InputDecoration(
                        labelText: "Physical Address",
                      ),
                    ),
                    TextField(
                      controller: streetController,
                      decoration: InputDecoration(labelText: "Street"),
                    ),
                    TextField(
                      controller: townController,
                      decoration: InputDecoration(labelText: "Town"),
                    ),
                  ],
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
                    final name = nameController.text.trim();
                    final email = emailController.text.trim();
                    final phone = phoneController.text.trim();
                    final dateOfBirth = dateOfBirthController.text.trim();
                    final gender = genderController.text.trim();
                    final physicalAddress =
                        physicalAddressController.text.trim();
                    final street = streetController.text.trim();
                    final town = townController.text.trim();

                    if (name.isEmpty ||
                        email.isEmpty ||
                        phone.isEmpty ||
                        dateOfBirth.isEmpty ||
                        gender.isEmpty) {
                      ScaffoldMessenger.of(context).showSnackBar(
                        SnackBar(content: Text("Please fill in all fields")),
                      );
                      return;
                    }

                    Navigator.of(ctx).pop(); // Close the popup
                    await addClient(
                      name,
                      email,
                      phone,
                      dateOfBirth,
                      gender,
                      physicalAddress,
                      street,
                      town,
                    );
                  },
                  child: Text("Add"),
                ),
              ],
            ),
          ),
        );
      },
    );
  }

  void showSearchDialog() {
  final TextEditingController searchController = TextEditingController();

  showDialog(
    context: context,
    builder: (ctx) {
      return StatefulBuilder(
        builder: (context, setState) {
          List<dynamic> searchResults = [];
          bool isLoading = false;

          // Fetch clients by name from the backend
          Future<void> fetchClientsByName(String keyword) async {
            setState(() {
              isLoading = true;
            });
            try {
              final data = await ApiService.getData("/client-details/by-name/$keyword");
              setState(() {
                searchResults = data ?? [];
                isLoading = false;
              });
            } catch (e) {
              setState(() {
                isLoading = false;
              });
              ScaffoldMessenger.of(context).showSnackBar(
                SnackBar(content: Text("Error fetching clients: $e")),
              );
            }
          }

          return Dialog(
            child: Container(
              width: 1300, // Set the width of the dialog
              height: 700, // Set the height of the dialog
              padding: EdgeInsets.all(20),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    "Search Clients",
                    style: TextStyle(
                      fontSize: 24,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  SizedBox(height: 20),
                  Row(
                    children: [
                      Expanded(
                        child: TextField(
                          controller: searchController,
                          decoration: InputDecoration(
                            labelText: "Enter Name",
                            prefixIcon: Icon(Icons.search),
                            border: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(10),
                            ),
                          ),
                        ),
                      ),
                      SizedBox(width: 10),
                      ElevatedButton(
                        onPressed: () {
                          final keyword = searchController.text.trim();
                          if (keyword.isNotEmpty) {
                            fetchClientsByName(keyword);
                          } else {
                            ScaffoldMessenger.of(context).showSnackBar(
                              SnackBar(content: Text("Please enter a client name")),
                            );
                          }
                        },
                        child: Text("Submit"),
                      ),
                    ],
                  ),
                  SizedBox(height: 20),
                  Expanded(
                    child: isLoading
                        ? Center(child: CircularProgressIndicator())
                        : searchResults.isEmpty
                            ? Center(child: Text("No results found"))
                            : ListView.builder(
                                itemCount: searchResults.length,
                                itemBuilder: (context, index) {
                                  final client = searchResults[index];
                                  return ListTile(
                                    title: Text(client['name']),
                                    subtitle: Text(
                                      client['programs']?.join(", ") ?? "No Program",
                                    ),
                                    onTap: () {
                                      Navigator.of(ctx).pop(); // Close the dialog
                                      fetchClientDetails(client['id']);
                                    },
                                  );
                                },
                              ),
                  ),
                ],
              ),
            ),
          );
        },
      );
    },
  );
}
  
  void showEnrollProgramsPopup(int clientId) async {
    await fetchUnenrolledPrograms(clientId);

    if (unenrolledPrograms.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text("No unenrolled programs available")),
      );
      return;
    }

    List<int> selectedProgramIds = [];

    showDialog(
      context: context,
      builder: (ctx) {
        return AlertDialog(
          title: Text("Enroll in Programs"),
          content: Container(
            width: 400,
            child: ListView.builder(
              shrinkWrap: true,
              itemCount: unenrolledPrograms.length,
              itemBuilder: (context, index) {
                final program = unenrolledPrograms[index];
                return CheckboxListTile(
                  title: Text(
                    "${program['programName']} (ID: ${program['programId']})",
                  ),
                  value: selectedProgramIds.contains(program['programId']),
                  onChanged: (isSelected) {
                    setState(() {
                      if (isSelected == true) {
                        selectedProgramIds.add(program['programId']);
                      } else {
                        selectedProgramIds.remove(program['programId']);
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
                if (selectedProgramIds.isEmpty) {
                  ScaffoldMessenger.of(context).showSnackBar(
                    SnackBar(
                      content: Text("Please select at least one program"),
                    ),
                  );
                  return;
                }
                Navigator.of(ctx).pop(); // Close the popup
                await enrollPrograms(clientId, selectedProgramIds);
              },
              child: Text("Enroll"),
            ),
          ],
        );
      },
    );
  }

  Future<void> enrollPrograms(int clientId, List<int> programIds) async {
    try {
      for (int programId in programIds) {
        await ApiService.postData("/enrollments/create", {
          "programId": programId,
          "clientId": clientId,
          "startDate": DateTime.now().toIso8601String(),
          "endDate": DateTime.now().add(Duration(days: 30)).toIso8601String(),
          "status": "active",
          "progress": 0,
          "notes": "Enrolled via client selection",
          "enrolledAt": DateTime.now().toIso8601String(),
          "enrolledById": doctorId, // Use the doctorId from shared preferences
        });
      }
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(SnackBar(content: Text("Programs enrolled successfully")));
      fetchClients(); // Refresh the client list
    } catch (e) {
    }
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        // Toolbar Row
        Container(
          padding: EdgeInsets.symmetric(vertical: 10, horizontal: 20),
          color: Colors.grey[200],
          child: Column(
            children: [
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Text(
                    "Clients",
                    style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
                  ),
                  Row(
                    children: [
                      IconButton(
                        icon: Icon(Icons.search),
                        onPressed: showSearchDialog,
                      ),
                      ElevatedButton.icon(
                        onPressed: showAddClientPopup,
                        icon: Icon(Icons.add),
                        label: Text("Add Client"),
                        style: ElevatedButton.styleFrom(
                          padding: EdgeInsets.symmetric(
                            horizontal: 15,
                            vertical: 10,
                          ),
                        ),
                      ),
                    ],
                  ),
                ],
              ),
              SizedBox(height: 10),
            ],
          ),
        ),
        Expanded(
          child:
              isLoading
                  ? Center(child: CircularProgressIndicator())
                  : clients.isEmpty
                  ? Center(child: Text("No clients available"))
                  : GridView.builder(
                    padding: EdgeInsets.all(10),
                    gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                      crossAxisCount: 2,
                      crossAxisSpacing: 10,
                      mainAxisSpacing: 10,
                      childAspectRatio: 3 / 2,
                    ),
                    itemCount: clients.length,
                    itemBuilder: (context, index) {
                      final client = clients[index];
                      return GestureDetector(
                        onTap: () => fetchClientDetails(client['id']),
                        child: Card(
                          elevation: 3,
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(10),
                          ),
                          child: Container(
                            padding: EdgeInsets.all(10),
                            decoration: BoxDecoration(
                              borderRadius: BorderRadius.circular(10),
                              color: Colors.green[50],
                            ),
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              crossAxisAlignment: CrossAxisAlignment.center,
                              children: [
                                Icon(
                                  Icons.person,
                                  size: 40,
                                  color: Colors.green,
                                ),
                                SizedBox(height: 10),
                                Text(
                                  client['name'] ?? "Unknown",
                                  textAlign: TextAlign.center,
                                  style: TextStyle(
                                    fontSize: 16,
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                                SizedBox(height: 5),
                                Text(
                                  client['programs']?.join(", ") ??
                                      "No Program",
                                  textAlign: TextAlign.center,
                                  style: TextStyle(
                                    fontSize: 14,
                                    color: Colors.grey[700],
                                  ),
                                ),
                                SizedBox(height: 10),
                                ElevatedButton(
                                  onPressed:
                                      () =>
                                          showEnrollProgramsPopup(client['id']),
                                  child: Text("Enroll"),
                                ),
                              ],
                            ),
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
