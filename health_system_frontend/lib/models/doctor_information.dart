import 'dart:convert';

class DoctorInformation {
  final int id;
  final String name;
  final String email;
  final String phone;
  final int userId;
  final String specialization;
  final String dateOfBirth;
  final String gender;
  final String physicalAddress;
  final String street;
  final String town;
  final List<String> programs;
  final List<String> clients;

  DoctorInformation({
    required this.id,
    required this.name,
    required this.email,
    required this.phone,
    required this.userId,
    required this.specialization,
    required this.dateOfBirth,
    required this.gender,
    required this.physicalAddress,
    required this.street,
    required this.town,
    required this.programs,
    required this.clients,
  });

  factory DoctorInformation.fromJson(Map<String, dynamic> json) {
    return DoctorInformation(
      id: json['id'],
      name: json['name'],
      email: json['email'],
      phone: json['phone'],
      userId: json['userId'],
      specialization: json['specialization'],
      dateOfBirth: json['dateOfBirth'],
      gender: json['gender'],
      physicalAddress: json['physicalAddress'],
      street: json['street'],
      town: json['town'],
      programs: List<String>.from(json['programs']),
      clients: List<String>.from(json['clients']),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'name': name,
      'email': email,
      'phone': phone,
      'userId': userId,
      'specialization': specialization,
      'dateOfBirth': dateOfBirth,
      'gender': gender,
      'physicalAddress': physicalAddress,
      'street': street,
      'town': town,
      'programs': programs,
      'clients': clients,
    };
  }

  String toJsonString() => json.encode(toJson());
}