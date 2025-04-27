// ignore_for_file: file_names

class ProgramInformation {
  final int id;
  final String name;
  final String description;
  final String createdAt;
  final int createdById;
  final String createdByName;
  final String createdByEmail;
  final List<String> clients;

  ProgramInformation({
    required this.id,
    required this.name,
    required this.description,
    required this.createdAt,
    required this.createdById,
    required this.createdByName,
    required this.createdByEmail,
    required this.clients,
  });

  factory ProgramInformation.fromJson(Map<String, dynamic> json) {
    return ProgramInformation(
      id: json['id'],
      name: json['name'],
      description: json['description'],
      createdAt: json['createdAt'],
      createdById: json['createdById'],
      createdByName: json['createdByName'],
      createdByEmail: json['createdByEmail'],
      clients: List<String>.from(json['clients']),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'name': name,
      'description': description,
      'createdAt': createdAt,
      'createdById': createdById,
      'createdByName': createdByName,
      'createdByEmail': createdByEmail,
      'clients': clients,
    };
  }
}