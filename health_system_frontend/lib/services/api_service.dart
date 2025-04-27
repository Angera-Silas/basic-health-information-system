import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

class ApiService {
  static const String baseUrl = "http://localhost:8088/api";

  // Helper method to get the token from SharedPreferences
  static Future<String?> _getToken() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString('token'); // Replace 'authToken' with your token key
  }

  // GET Request
  static Future<dynamic> getData(String endpoint) async {
    try {
      final token = await _getToken();
      final response = await http.get(
        Uri.parse("$baseUrl$endpoint"),
        headers: {
          "Content-Type": "application/json",
          "Authorization": "Bearer $token", // Add the token here
        },
      );
      if (response.statusCode == 200 || response.statusCode == 201) {
        return json.decode(response.body);
      } else {
        throw Exception("Failed to fetch data from $endpoint");
      }
    } catch (e) {
      return null; // Return null on error
    }
  }


   static Future<dynamic> postDataWithoutAuth(
    String endpoint,
    Map<String, dynamic> data,
  ) async {
    final response = await http.post(
      Uri.parse("$baseUrl$endpoint"),
      headers: {
        "Content-Type": "application/json",
      },
      body: json.encode(data),
    );
    if (response.statusCode == 200 || response.statusCode == 201) {
      return json.decode(response.body);
    } else {
      throw Exception("Failed to post data to $endpoint: ${response.body}");
    }
  }

  // POST Request
  static Future<dynamic> postData(
    String endpoint,
    Map<String, dynamic> data,
  ) async {
    final token = await _getToken();
    final response = await http.post(
      Uri.parse("$baseUrl$endpoint"),
      headers: {
        "Content-Type": "application/json",
        "Authorization": "Bearer $token", // Add the token here
      },
      body: json.encode(data),
    );
    if (response.statusCode == 200 || response.statusCode == 201) {
      return json.decode(response.body);
    } else {
      throw Exception("Failed to post data to $endpoint: ${response.body}");
    }
  }

  // PUT Request
  static Future<dynamic> putData(
    String endpoint,
    Map<String, dynamic> data,
  ) async {
    final token = await _getToken();
    final response = await http.put(
      Uri.parse("$baseUrl$endpoint"),
      headers: {
        "Content-Type": "application/json",
        "Authorization": "Bearer $token", // Add the token here
      },
      body: json.encode(data),
    );
    if (response.statusCode == 200 || response.statusCode == 201) {
      return json.decode(response.body);
    } else {
      throw Exception("Failed to update data at $endpoint: ${response.body}");
    }
  }

  // DELETE Request
  static Future<void> deleteData(String endpoint) async {
    final token = await _getToken();
    final response = await http.delete(
      Uri.parse("$baseUrl$endpoint"),
      headers: {
        "Authorization": "Bearer $token", // Add the token here
      },
    );
    if (response.statusCode != 204) {
      throw Exception("Failed to delete data at $endpoint: ${response.body}");
    }
  }

  // PATCH Request
  static Future<dynamic> patchData(
    String endpoint,
    Map<String, dynamic> data,
  ) async {
    final token = await _getToken();
    final response = await http.patch(
      Uri.parse("$baseUrl$endpoint"),
      headers: {
        "Content-Type": "application/json",
        "Authorization": "Bearer $token", // Add the token here
      },
      body: json.encode(data),
    );
    if (response.statusCode == 200 || response.statusCode == 201) {
      return json.decode(response.body);
    } else {
      throw Exception("Failed to patch data at $endpoint: ${response.body}");
    }
  }
}