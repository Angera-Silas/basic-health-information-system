class ClientInformation {
  final int id;
  final int userId;
  final String name;
  final String email;
  final String phone;
  final String dateOfBirth;
  final String gender;
  final String physicalAddress;
  final String street;
  final String town;
  final bool active;
  final int registeredById;
  final String registeredByName;
  final String registeredByEmail;
  final String registeredByPhone;
  final List<String> programs;

  ClientInformation({
    required this.id,
    required this.userId,
    required this.name,
    required this.email,
    required this.phone,
    required this.dateOfBirth,
    required this.gender,
    required this.physicalAddress,
    required this.street,
    required this.town,
    required this.active,
    required this.registeredById,
    required this.registeredByName,
    required this.registeredByEmail,
    required this.registeredByPhone,
    required this.programs,
  });

  factory ClientInformation.fromJson(Map<String, dynamic> json) {
    return ClientInformation(
      id: json['id'],
      userId: json['userId'],
      name: json['name'],
      email: json['email'],
      phone: json['phone'],
      dateOfBirth: json['dateOfBirth'],
      gender: json['gender'],
      physicalAddress: json['physicalAddress'],
      street: json['street'],
      town: json['town'],
      active: json['active'],
      registeredById: json['registeredById'],
      registeredByName: json['registeredByName'],
      registeredByEmail: json['registeredByEmail'],
      registeredByPhone: json['registeredByPhone'],
      programs: List<String>.from(json['programs']),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'userId': userId,
      'name': name,
      'email': email,
      'phone': phone,
      'dateOfBirth': dateOfBirth,
      'gender': gender,
      'physicalAddress': physicalAddress,
      'street': street,
      'town': town,
      'active': active,
      'registeredById': registeredById,
      'registeredByName': registeredByName,
      'registeredByEmail': registeredByEmail,
      'registeredByPhone': registeredByPhone,
      'programs': programs,
    };
  }
}