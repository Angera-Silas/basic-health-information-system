import 'dart:convert';
import 'package:http/http.dart' as http;

class ApiService {
  static const String baseUrl = "http://localhost:8088/api";

  // GET Request
  static Future<dynamic> getData(String endpoint) async {
    try {
      final response = await http.get(Uri.parse("$baseUrl$endpoint"));
      if (response.statusCode == 200 || response.statusCode == 201) {
        return json.decode(response.body);
      } else {
        print("Error: ${response.statusCode} - ${response.body}");
        throw Exception("Failed to fetch data from $endpoint");
      }
    } catch (e) {
      print("Error fetching data from $endpoint: $e");
      return null; // Return null on error
    }
  }

  // POST Request
  static Future<dynamic> postData(
    String endpoint,
    Map<String, dynamic> data,
  ) async {
    final response = await http.post(
      Uri.parse("$baseUrl$endpoint"),
      headers: {"Content-Type": "application/json"},
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
    final response = await http.put(
      Uri.parse("$baseUrl$endpoint"),
      headers: {"Content-Type": "application/json"},
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
    final response = await http.delete(Uri.parse("$baseUrl$endpoint"));
    if (response.statusCode != 204) {
      throw Exception("Failed to delete data at $endpoint: ${response.body}");
    }
  }

  // PATCH Request
  static Future<dynamic> patchData(
    String endpoint,
    Map<String, dynamic> data,
  ) async {
    final response = await http.patch(
      Uri.parse("$baseUrl$endpoint"),
      headers: {"Content-Type": "application/json"},
      body: json.encode(data),
    );
    if (response.statusCode == 200 || response.statusCode == 201) {
      return json.decode(response.body);
    } else {
      throw Exception("Failed to patch data at $endpoint: ${response.body}");
    }
  }
}
