class EnrollmentInformation {
  final int id;
  final int clientId;
  final int programId;
  final String startDate;
  final String endDate;
  final String status;
  final String progress;
  final String notes;
  final String enrolledAt;
  final int enrolledById;
  final String doctorName;
  final String doctorEmail;
  final String doctorPhone;
  final String doctorSpecialization;
  final String clientName;
  final String clientEmail;
  final String clientPhone;
  final String clientDateOfBirth;
  final String clientGender;
  final String programName;
  final String programDescription;
  final String programCreatedAt;

  EnrollmentInformation({
    required this.id,
    required this.clientId,
    required this.programId,
    required this.startDate,
    required this.endDate,
    required this.status,
    required this.progress,
    required this.notes,
    required this.enrolledAt,
    required this.enrolledById,
    required this.doctorName,
    required this.doctorEmail,
    required this.doctorPhone,
    required this.doctorSpecialization,
    required this.clientName,
    required this.clientEmail,
    required this.clientPhone,
    required this.clientDateOfBirth,
    required this.clientGender,
    required this.programName,
    required this.programDescription,
    required this.programCreatedAt,
  });

  factory EnrollmentInformation.fromJson(Map<String, dynamic> json) {
    return EnrollmentInformation(
      id: json['id'],
      clientId: json['clientId'],
      programId: json['programId'],
      startDate: json['startDate'],
      endDate: json['endDate'],
      status: json['status'],
      progress: json['progress'],
      notes: json['notes'],
      enrolledAt: json['enrolledAt'],
      enrolledById: json['enrolledById'],
      doctorName: json['doctorName'],
      doctorEmail: json['doctorEmail'],
      doctorPhone: json['doctorPhone'],
      doctorSpecialization: json['doctorSpecialization'],
      clientName: json['clientName'],
      clientEmail: json['clientEmail'],
      clientPhone: json['clientPhone'],
      clientDateOfBirth: json['clientDateOfBirth'],
      clientGender: json['clientGender'],
      programName: json['programName'],
      programDescription: json['programDescription'],
      programCreatedAt: json['programCreatedAt'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'clientId': clientId,
      'programId': programId,
      'startDate': startDate,
      'endDate': endDate,
      'status': status,
      'progress': progress,
      'notes': notes,
      'enrolledAt': enrolledAt,
      'enrolledById': enrolledById,
      'doctorName': doctorName,
      'doctorEmail': doctorEmail,
      'doctorPhone': doctorPhone,
      'doctorSpecialization': doctorSpecialization,
      'clientName': clientName,
      'clientEmail': clientEmail,
      'clientPhone': clientPhone,
      'clientDateOfBirth': clientDateOfBirth,
      'clientGender': clientGender,
      'programName': programName,
      'programDescription': programDescription,
      'programCreatedAt': programCreatedAt,
    };
  }
}